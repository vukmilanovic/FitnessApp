Vue.component("addstuff", {
    data: function() {
        return {
            user : { username: '', password: null, name: null, lastname: null, gender: null, dateOfBirth: { day: null, month: null, year: null, hours:null, minutes: null }, role: null,
            membershipFee: null, visitedSportsBuildings: null, numberOfCollectedPoints: null, customerType: { typeName: null, discount: null, requiredNumberOfPoints: null }, trainingHistory: null, sportsBuilding: null },
            checkDTO : { username: '', password: ''},
            date: null,
            answer: null
        }
    },
    template: 
    `
    <div class="form"> 
        <div style="margin-left: 3cm;"> 
            <div> 
                <div> 
                    <h2 class="title">Create new stuff</h2>
                </div> 
                <div style="margin-left: 0.5cm;">
                    <div> 
                        <div class="inlineElements profileSettingsFields">
                            <label class="labels">Name</label>
                            <input type="text" class="form-control field" v-model = "user.name"></input>
                        </div>
                        <div class="inlineElements profileSettingsFields">
                            <label class="labels">Lastname</label>
                            <input type="text" class="form-control field" v-model = "user.lastname"></input>
                        </div> 
                    </div> 
                    <div> 
                        <div class="inlineElements profileSettingsFields">
                            <label class="labels">Username</label>
                            <input type="text" class="form-control field" v-model = "user.username"></input>
                        </div> 
                        <div class="inlineElements profileSettingsFields">
                            <label class="labels">Password</label>
                            <input type="text" class="form-control readonly" value="fitness" readonly>
                        </div> 
                    </div>
                    <div>
                        <div class="inlineElements profileSettingsFields"> 
                            <label class="labels">Date of birth</label>
                            <input type="date" class="form-control" style="width: 300;" v-model = "date" id="date" required></input>
                        </div>   
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
                    </div>
                    <div class="combo profileSettingsFields">
                        <label class="labels" for="type">Stuff type</label>
                        <select class="form-control field comboBox" id="type" v-model="user.role">
                            <option class="form-control" :value="null" disabled>Select role</option>
                            <option class="form-control" value="Manager">Manager</option>
                            <option class="form-control" value="Coach">Coach</option>
                        </select>
                    </div> 
                    <div > 
                        <div>
                        <button class="btn btn-primary profile-button buttonEdit" v-on:click = "newStuff" type="button">Add</button>
                        </div> 
                    </div> 
                </div>
            </div> 
        </div>
    </div>
    `,
    methods : {
        newStuff : function() {

            if(this.date !== null){
                const split_date = this.date.split("-");
                this.user.dateOfBirth.year = parseInt(split_date[0])
                this.user.dateOfBirth.month = parseInt(split_date[1])
                this.user.dateOfBirth.day = parseInt(split_date[2])
            }

            this.user.password = "fitness";
            
            if(this.user.username === '' || this.user.password === '' || this.user.name === '' || this.user.lastname === '' || this.date === null || this.user.gender === null){
                alert("Niste kompletno uneli podatke za registraciju")
                return;
            }

            var letters = /^[A-Za-z]+$/;
            if(!this.user.name.match(letters) || !this.user.lastname.match(letters)){
                alert("Niste ispravno uneli podatke u poljima forme")
                return;
            }
            
            this.checkDTO.username = this.user.username;
            this.checkDTO.password = this.user.password;

            axios
            .post('rest/admin/check', this.checkDTO)
            .then(response => {
                
                this.answer = response.data;
            
                if(this.answer === "False"){
                    alert("Upisano korisnicko ime vec postoji.")
                    return;
                } else if(this.answer === "True") {
                    axios
                    .post('rest/users/add', this.user)
                    .then(response => (alert("Korisnik je uspesno dodat u sistem.")));
                }
            });
        }
    }
})