Vue.component("practice", {
    data: function() {
        return {
            sportBuildings: null,
            coaches : null,
            LoggedUserDTO : { username : '', role : null },
            training : { dateAndTimeOfCheckIn : { day : null, month : null, year: null, hours : null, minutes : null }
            , customerUsername : '', coachUsername : '', trainingType : null, description : '', sportsBuildingName : '', workoutName : ''},
            date : null,
            facilityName: '',
            facilityType: null,
            facilityRating: null,
            facilityStart: { day : null, month : null, year: null, hours : null, minutes : null },
            facilityEnd: { day : null, month : null, year: null, hours : null, minutes : null },
            facilityLocation: { address : { street: '', number : '', city : '', postalCode: '' }, geoWidth : null, geoLen : null}
        }
    },
    template: 
    `
        <div class="container">
            <div>
                <h2>Application for practice</h2><br>
                <p>Fill out the form to sign up for the practice:</p>
            </div>
            <div>
                <div class="inline">      
                    <div class="profileSettingsFields" style="margin-left: 0;">
                        <label for="datetime" class="labels">Date and time</label>
                        <input type="datetime-local" class="form-control" style="width: 300;" id="datetime" name="datetime" v-model="date">
                    </div>
                    <div class="profileSettingsFields" style="margin-left: 0;">
                        <label for="text">Description</label>
                        <textarea class="form-control textArea" placeholder="Type description..." id="text" rows="7" v-model="training.description"></textarea>
                    </div>
                </div>           
                <div class="inline">
                    <div class="profileSettingsFields">
                        <label for="wname">Workout name</label>
                        <input type="text" class="form-control" style="width: 300;" id="wname" name="wname" v-model="training.workoutName">
                    </div>
                    <div class="combo input-group profileSettingsFields">
                        <div class="input-group-prepend">
                            <label class="labels input-group-text" for="type">Practice type</label>
                        </div>
                        <select class="form-control field comboBox custom-select" style="width: 300;" id="type" v-model="training.trainingType">
                            <option class="form-control" :value="null" disabled>Select type</option>
                            <option class="form-control" value="Personal">Personal</option>
                            <option class="form-control" value="Group">Group</option>
                            <option class="form-control" value="Gym">Gym</option>
                        </select>
                    </div>
                    <div class="combo input-group profileSettingsFields">
                        <div class="input-group-prepend">
                            <label class="labels input-group-text" for="coaches">Coaches</label>
                        </div>
                        <select class="form-control field comboBox custom-select" style="width: 300;" id="coaches" v-model="training.coachUsername">
                            <option class="form-control" :value="null" disabled>Select coach</option>
                            <option class="form-control" :value="c.username" v-for="c in coaches">{{c.name}} {{c.lastname}}</option>
                        </select>
                    </div>
                </div>
                <div class="inline">
                    <div class="combo input-group profileSettingsFields">
                        <div class="input-group-prepend">
                            <label class="labels input-group-text" for="facilities">Sport facilities</label>
                        </div>
                        <select class="form-control field comboBox custom-select" style="width: 300;" v-on:change="insertData()" id="facilities" v-model="facilityName">
                            <option class="form-control" :value="null" disabled>Select facility</option>
                            <option class="form-control" :value="f.name" v-for="f in sportBuildings">{{f.type}} - {{f.name}}</option>
                        </select>
                    </div>
                    <div class="profileSettingsFields">
                        <div class="input-group-prepend">
                            <label class="labels input-group-text" for="info">Info</label>
                        </div>
                        <p>
                        <button class="btn btn-primary commentButton" type="button" id="info" data-toggle="collapse" data-target="#facility" aria-expanded="false" aria-controls="facility">Sport facility info</button>
                        </p>
                        <div class="collapse" id="facility">
                            <div class="card card-body">
                                <div class="inlineElements">
                                    <div>{{facilityType}}</div>
                                    <address>
                                    Address: <br>
                                    {{facilityLocation.address.street}} {{facilityLocation.address.number}} <br>
                                    {{facilityLocation.address.city}}, {{facilityLocation.address.postalCode}} <br>
                                    {{facilityLocation.geoWidth}}, {{facilityLocation.geoLen}}     
                                    </address>
                                </div>
                                <div class="inlineElements">
                                    <div style="margin-left: 1cm;">Average rating: {{facilityRating}}</div>
                                    <div style="margin-left: 1cm;">Working hours : {{facilityStart.hours}}:{{facilityStart.minutes}} - {{facilityEnd.hours}}:{{facilityEnd.minutes}}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="addPractice">
                <input type="button" style="margin-top: 2cm;" class="btn btn-primary profile-button commentButton" value = "Sign up for practice" v-on:click = "signUpForPractice"></input>
            </div>
        </div>
    `,
    methods: {
        signUpForPractice: function() {
            
            var now = new Date().toJSON().slice(0, 10);
            if(this.date < now) {
                alert("Ne mozete da zakazete trening nakon datuma koji je prosao.")
                return;
            }

            if(this.date !== null) {
                const split_date = this.date.split("-");
                this.training.dateAndTimeOfCheckIn.year = parseInt(split_date[0])
                this.training.dateAndTimeOfCheckIn.month = parseInt(split_date[1])
                var prom = split_date[2]
                this.training.dateAndTimeOfCheckIn.day = parseInt(prom.substring(0,2));
                this.training.dateAndTimeOfCheckIn.hours = parseInt(prom.substring(3,5));
                this.training.dateAndTimeOfCheckIn.minutes = parseInt(prom.substring(6,8));
            }

            this.training.customerUsername = this.LoggedUserDTO.username;
            this.training.sportsBuildingName = this.facilityName;

            if(this.training.coachUsername == '' && this.training.trainingType != 'Gym'){
                alert("Izaberite trenera, ako zelite personalni ili grupni trening");
                return;
            } 

            if(this.facilityName == null) {
                alert("Izaberite sportski objekat.");
                return;
            }

            axios
            .post('rest/trainings/add', this.training)
            .then(response => {

                this.answer = response.data;

                if(this.answer == false) {
                    alert("Trening nije uspesno prijavljen.")
                } else if(this.answer == true) {
                    alert("Uspesno ste prijavili trening.")
                }

            })
        },
        insertData: function() {

            this.sportBuildings.forEach(sb => {
                if(sb.name == this.facilityName) {
                    this.facilityRating = sb.averageRating;
                    this.facilityLocation.geoWidth = sb.location.geoWidth;
                    this.facilityLocation.geoLen = sb.location.geoLen;
                    this.facilityLocation.address.street = sb.location.address.street;
                    this.facilityLocation.address.number = sb.location.address.number;
                    this.facilityLocation.address.city = sb.location.address.city;
                    this.facilityLocation.address.postalCode = sb.location.address.postalCode;

                    this.facilityStart.hours = sb.startWorkingTime.hours;
                    this.facilityStart.minutes = sb.startWorkingTime.minutes;
                    this.facilityEnd.hours = sb.endWorikingTime.hours;
                    this.facilityEnd.minutes = sb.endWorikingTime.minutes;

                    if(parseInt(this.facilityStart.hours) < 10) {
                        this.facilityStart.hours = "0" + this.facilityStart.hours;
                    } 
                    if(parseInt(this.facilityStart.minutes) < 10) {
                        this.facilityStart.minutes = "0" + this.facilityStart.minutes;
                    }
                    if(parseInt(this.facilityEnd.hours) < 10) {
                        this.facilityEnd.hours = "0" + this.facilityEnd.hours;
                    }
                    if(parseInt(this.facilityEnd.minutes) < 10) {
                        this.facilityEnd.minutes = "0" + this.facilityEnd.minutes;
                    }     
                
                    if('SPORTS_CENTER' == sb.type){
                        this.facilityType = 'Sports center';
                    } else if('POOL' == sb.type) {
                        this.facilityType = 'Pool';
                    } else if('GYM' == sb.type) {
                        this.facilityType = 'Gym';
                    } else if('DANCE_STUDIO' == sb.type) { 
                        this.facilityType = 'Dance studio';
                    }
                }
            });
        }
    }, 
    mounted() {

        this.jwt = window.localStorage.getItem("JWT");
        let config = {
            headers: {
                Authorization: "Bearer " + this.jwt
            }
        }

        axios.get('rest/users/logged', config).then(response => {

            this.LoggedUserDTO = response.data;

            axios
            .get('rest/trainings/facilities')
            .then(response => (this.sportBuildings = response.data))

            axios
            .get('rest/trainings/coaches')
            .then(response => (this.coaches = response.data))
        })

    }
})