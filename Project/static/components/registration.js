Vue.component("registration", {
    data: function() {
        return {
            user : { username: '', password: null, name: null, lastname: null, gender: null, dateOfBirth: { day: null, month: null, year: null, hours:null, minutes: null }, role: null,
            membershipFee: null, visitedSportsBuildings: null, numberOfCollectedPoints: null, customerType: { typeName: null, discount: null, requiredNumberOfPoints: null }, trainingHistory: null, sportsBuilding: null },
            loginDTO : { username: '', password: '' },
            date: null,
            jwtDTO : { token: '' }
        }
    },
    template: `

    <div id="page" class="form">
        <h2 class="title">Registration page</h2>
        <form class="form">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control field" v-model = "user.name" id="name" required></input>
            </div>
            <div class="form-group">
                <label for="lastname">Lastname:</label>
                <input type="text" class="form-control field" v-model = "user.lastname" id="lastname"required></input>
            </div>
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" class="form-control field" v-model = "user.username" id="username" required></input>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control field" v-model = "user.password" id="password" required></input>
            </div>

            <div>
                <label>Gender:</label>
                <div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="radios" v-model = "user.gender" value="Male" id="male" checked></input>
                        <label class="form-check-label radioField" for="male">Male</label>
                        <input class="form-check-input" type="radio" name="radios" v-model = "user.gender" value="Female" id="female"></input>
                        <label class="form-check-label" for="female">Female</label>  
                    </div>
                </div>
            </div>

            <div class="form-group form">
                <label for="date">Date of birth:</label>
                <input type="date" class="form-control field" v-model = "date" id="date" required></input>
            </div>

            <input type = "button" class="btn btn-primary button" v-on:click = "registration" value = "Register"></input>
        </form>
    </div>

    ` ,
    methods : {
        registration : function (event) {

            this.user.role = "Customer"
            this.user.customerType.typeName = "Bronze"
            this.user.customerType.discount = 300;
            this.user.customerType.requiredNumberOfPoints = 150;
            console.log(this.date)
            if(this.date !== null){
                const split_date = this.date.split("-");
                this.user.dateOfBirth.year = parseInt(split_date[0])
                this.user.dateOfBirth.month = parseInt(split_date[1])
                this.user.dateOfBirth.day = parseInt(split_date[2])
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

            this.loginDTO.username = this.user.username;
            this.loginDTO.password = this.user.password;

            axios
            .post('rest/users/checkReg', this.loginDTO)
            .then(response => {
                
                this.jwtDTO.token = response.data;
            
                if(this.jwtDTO.token === ""){
                    alert("Upisano korisnicko ime vec postoji.")
                    return;
                } else {
                    window.localStorage.setItem("JWT", this.jwtDTO.token);
                    axios
                    .post('rest/users/add', this.user)
                    .then(response => (router.push(`/homepage`)));
                }
            });
        }
    }
})