Vue.component("profile", {
    data: function() {
        return {
          newUser : { username: '', password: null, name: null, lastname: null, gender: null, dateOfBirth: { day: null, month: null, year: null, hours:null, minutes: null }, role: null,
          membershipFee: null, visitedSportsBuildings: null, numberOfCollectedPoints: null, customerType: null, trainingHistory: null, sportsBuilding: null },
          user : { username: '', name: null, lastname: null, gender: null, dateOfBirth: { day: null, month: null, year: null, hours:null, minutes: null }, role: null },
          loginDTO : { username: '', password: '' },
          date: null,
          oldUsername: '',
          newPassword: '',
          jwt: '',
          LoggedUserDTO : { username: '', role: null }
        }
    },
    template: `

    <div class="form"> 
    <div style="margin-left: 3cm;"> 
      <div> 
        <div> 
          <h2 class="title">Profile Settings</h2>
          <h4 class="title" style="margin-top: 0.5cm;">User role: {{user.role}}</h4> 
        </div> 
        <div style="margin-left: 0.5cm;">
          <div> 
            <div class="inlineElements profileSettingsFields">
              <label class="labels">Name</label>
              <input type="text" class="form-control field" v-model = "user.name">
            </div>
            <div class="inlineElements profileSettingsFields">
              <label class="labels">Lastname</label>
              <input type="text" class="form-control field" v-model = "user.lastname">
            </div> 
          </div> 
          <div> 
            <div class="inlineElements profileSettingsFields">
              <label class="labels">Username</label>
              <input type="text" class="form-control field" v-model = "user.username">
            </div> 
            <div class="inlineElements profileSettingsFields">
              <label class="labels">Password</label>
              <input type="password" class="form-control field" v-model = "newPassword">
            </div> 
          </div>
          <div> 
            <div class="inlineElements profileSettingsFields">
              <label class="labels">Gender</label>
              <div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="radios" v-model = "user.gender" value="Male" id="male" checked></input>
                    <label class="form-check-label radioField" for="male">Male</label>
                    <input class="form-check-input" type="radio" name="radios" v-model = "user.gender" value="Female" id="female"></input>
                    <label class="form-check-label" for="female">Female</label>  
                </div>
              </div>        
            </div> 
            <div class="inlineElements profileSettingsFields" style="margin-left: 4.4cm;"> 
              <label class="labels">Date of birth</label>
              <input type="date" class="form-control" style="width: 300;" v-model = "date" id="date" required></input>
            </div> 
          </div> 
          <div > 
            <div>
              <button class="btn btn-primary profile-button buttonEdit" v-on:click = "editProfile" type="button">Save Profile</button>
            </div> 
          </div> 
        </div>
      </div> 
    </div>
  </div>

    `,
    methods : {
        editProfile : function () {
            if(this.date !== null) {
                const split_date = this.date.split("-");
                this.newUser.dateOfBirth.year = parseInt(split_date[0])
                this.newUser.dateOfBirth.month = parseInt(split_date[1])
                this.newUser.dateOfBirth.day = parseInt(split_date[2])
            }

            if(this.user.username === '' || this.user.password === '' || this.user.name === '' || this.user.lastname === '' || this.date === null || this.user.gender === null){
                alert("Niste kompletno uneli podatke za registraciju")
                return;
            }

            var letters = /^[A-Za-z]+$/;
            if(!this.user.name.match(letters) || !this.user.lastname.match(letters)){
                alert("Niste ispravno uneli podatke u poljima forme")
                return;
            }

            this.newUser.name = this.user.name;
            this.newUser.lastname = this.user.lastname;
            this.newUser.username = this.user.username;
            this.newUser.gender = this.user.gender;
            this.newUser.role = this.user.role;

            if(this.newPassword !== '') {
              this.newUser.password = this.newPassword;
            } else if(this.newPassword === '') {
              this.newUser.password = "";
            }

            if(this.oldUsername !== this.newUser.username) {

                this.loginDTO.username = this.newUser.username;
                this.loginDTO.password = this.newUser.password;

                axios
                .post('rest/users/checkReg', this.loginDTO)
                .then(response => {
                
                    let object = response.data
                
                    if(object === ""){
                        alert("Upisano korisnicko ime vec postoji.")
                        return;
                    } else {

                      window.localStorage.removeItem("JWT");
                      alert("Uspesno ste izmenili profil.")
                      axios
                      .put('rest/users/edit/' + this.oldUsername, this.newUser)
                      .then(response => {
                        
                        this.jwt = response.data;
                        window.localStorage.setItem("JWT", this.jwt);
                        this.$router.push('/homepage')
                      });
                    }
                });
                return;
            }

            axios
            .put('rest/users/edit/' + this.newUser.username, this.newUser)
            .then(response => alert("Uspesno ste izmenili profil."));
            this.$router.push('/homepage')
        }
    },
    mounted () {

        this.jwt = window.localStorage.getItem("JWT");
        let config = {
          headers: {
            Authorization: "Bearer " + this.jwt
          }
        }

        axios.get('rest/users/logged', config).then(response => {
          this.LoggedUserDTO = response.data;
        
          axios
          .get('rest/users/oneUser/' + this.LoggedUserDTO.username)
          .then((response) => (this.user = response.data))

          this.oldUsername = this.LoggedUserDTO.username;
        
        })    

        

    },
    updated () {
        if(this.date === null){
            let day = this.user.dateOfBirth.day;
            let month = this.user.dateOfBirth.month;
            let year = this.user.dateOfBirth.year;

            this.date = new Date(year, month, day).toISOString().substring(0, 10)
        }
    }
})