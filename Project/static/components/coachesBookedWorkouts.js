Vue.component("coachesw" ,{
    data: function() {
        return {
            startDate : null,
            endDate: null,
            allPersonalWorkouts: null,
            allGroupWorkouts: null,
            personalVisible: "true",
            personalWorkouts: null,
            groupWorkouts: null,
            username: '',
            workoutName: '',
            selectedRow: null,
            deletedTHistory : null,
            buildingTypeDTO : { type : null },
            cancelTrainingDTO : { username: '', trainingName: '' },
            SearchCoachDateDTO : { coachUsername : '', startDate : { day: null, month: null, year: null, hours:null, minutes: null }, endDate : { day: null, month: null, year: null, hours:null, minutes: null }, isPersonal : null},
            sortDTO : { sortBy : '', username : '', type: null },
            ascSortFacility : null,
            ascSortDate : null
        }
    },
    template: 
    `
    <div class="container tableForm">
        <div class="inlineElements">
            <h2>Coaches booked workouts</h2>
            <p v-if="personalVisible=='true'">This is the list of booked personal workouts:</p>
            <p v-if="personalVisible=='false'">This is the list of booked group workouts:</p>            
        </div>
        <div style="margin-top: 1cm;">
            <div class="inLine">
                <label style="font-size: medium;">Search: </label>
                <input type="text" class="form-control field" id="facilitySearch" v-on:keyup="search" placeholder="Search workout by facility name...">   
            </div>
            <div class="inLine" style="margin-left: 1.5cm;">
                <label style="font-size: medium;">Start date: </label>
                <input type="date" class="form-control field" style="width: 200;" id="startDateSearch" v-on:change="searchDate" v-model="startDate">
            </div>
            <div class="inLine" style="margin-left: 1.5cm;">
                <label style="font-size: medium;">End date: </label>
                <input type="date" class="form-control field" style="width: 200;" id="endDateSearch" v-on:change="searchDate" v-model="endDate">
            </div>
            <div class="inLine comboWorkout" style="margin-left: 1cm;">
                <label for="workouts">Workout type:</label>
                <select class="form-control" name="workouts" id="workouts" v-model = "personalVisible" v-on:change="changeWorkoutType">
                    <option class="form-control" value="true">Personal</option>
                    <option class="form-control" value="false">Group</option>
                </select>
            </div>
            <div class="inLine comboWorkout" style="margin-left: 1cm;">
                <label for="facilitiesType">Facility type:</label>
                <select class="form-control" name="facilitiesType" id="facilitiesType" v-model = "buildingTypeDTO.type" v-on:change="filterFacilityType(buildingTypeDTO.type)">
                    <option class="form-control" value="ALL">All</option>
                    <option class="form-control" value="GYM">Gym</option>
                    <option class="form-control" value="POOL">Pool</option>
                    <option class="form-control" value="SPORTS_CENTER">Sports center</option>
                    <option class="form-control" value="DANCE_STUDIO">Dance studio</option>
                </select>
            </div>
        </div>
        <table class="table table-hover table" id="myTable">
            <thead>
                <tr>
                    <th>Workout name</th>
                    <th @click="sortFacility('Facility')" class="headerElements">Sport facility</th>
                    <th @click="sortDate('Date')" class="headerElements">Date</th>
                    <th>Client</th>
                    <th>Duration</th>
                </tr>
            </thead>
            <tbody>
                <tr v-if="personalVisible=='true'" v-bind:class="{selectedRow : workoutName == p.training.name }"  @click="getWorkoutName(p.training.name)" v-for="p in personalWorkouts">
                    <td>{{p.training.name}}</td>
                    <td>{{p.training.sportsBuilding.name}}</td>
                    <td>{{p.dateAndTimeOfCheckIn.day}}/{{p.dateAndTimeOfCheckIn.month}}/{{p.dateAndTimeOfCheckIn.year}} {{p.dateAndTimeOfCheckIn.hours}}:{{p.dateAndTimeOfCheckIn.minutes}}</td>
                    <td>{{p.customer.name}} {{p.customer.lastname}}</td>
                    <td>{{p.training.duration}}</td>
                </tr>
                <tr v-if="personalVisible=='false'" v-for="g in groupWorkouts">
                    <td>{{g.training.name}}</td>
                    <td>{{g.training.sportsBuilding.name}}</td>
                    <td>{{g.dateAndTimeOfCheckIn.day}}/{{g.dateAndTimeOfCheckIn.month}}/{{g.dateAndTimeOfCheckIn.year}} {{g.dateAndTimeOfCheckIn.hours}}:{{g.dateAndTimeOfCheckIn.minutes}}</td>
                    <td>{{g.customer.name}} {{g.customer.lastname}}</td>
                    <td>{{g.training.duration}}</td>
                </tr>
            </tbody>
        </table>
        <input v-if="personalVisible=='true'" type="button" value = "Cancel personal workout" v-on:click = "cancelW"></input>
    </div>

    `,
    methods : {
        changeWorkoutType: function() { 

            this.startDate = null;
            this.endDate = null;
            this.buildingTypeDTO.type = 'ALL';
        },
        filterFacilityType: function(value) {
            
            this.startDate = null;
            this.endDate = null;

            if(this.personalVisible == 'true') {

                if(value == 'ALL') {
                    this.personalWorkouts = this.allPersonalWorkouts;
                    return;
                }

                axios
                .get('rest/coaches/filterBuilding', {
                    params: {
                        workoutType: 'Personal',
                        username: this.LoggedUserDTO.username,
                        type: value
                    }
                })
                .then(response => {
                    this.personalWorkouts = response.data
                });

            } else if(this.personalVisible == 'false') {

                if(value == 'ALL') {
                    this.groupWorkouts = this.allGroupWorkouts;
                    return;
                }

                axios
                .get('rest/coaches/filterBuilding', {
                    params: {
                        workoutType: 'Group',
                        username: this.LoggedUserDTO.username,
                        type: value
                    }
                })
                .then(response => {
                    this.groupWorkouts = response.data
                });

            }

        },
        sortFacility: function(column) {
            if(column == 'Facility') {

                this.sortDTO.sortBy = 'Facility';
                this.sortDTO.username = this.LoggedUserDTO.username;

                if(this.ascSortFacility == 0 || this.ascSortFacility == -1) {

                    if(this.personalVisible == 'true'){
                        this.sortDTO.type = "Personal";
    
                        axios
                        .post("rest/coaches/asc", this.sortDTO)
                        .then(response => (this.personalWorkouts = response.data));
    
                    } else if(this.personalVisible == 'false') {
                        this.sortDTO.type = "Group";
    
                        axios
                        .post("rest/coaches/asc", this.sortDTO)
                        .then(response => (this.groupWorkouts = response.data));
    
                    }

                    this.ascSortFacility = 1;
                } else if(this.ascSortFacility == 1) {

                    if(this.personalVisible == 'true'){
                        this.sortDTO.type = "Personal";
    
                        axios
                        .post("rest/coaches/desc", this.sortDTO)
                        .then(response => (this.personalWorkouts = response.data));
    
                    } else if(this.personalVisible == 'false') {
                        this.sortDTO.type = "Group";
    
                        axios
                        .post("rest/coaches/desc", this.sortDTO)
                        .then(response => (this.groupWorkouts = response.data));
    
                    }

                    this.ascSortFacility = -1;
                }

            }
        },
        sortDate: function(column) {
            if(column == 'Date') {

                this.sortDTO.sortBy = 'Date';
                this.sortDTO.username = this.LoggedUserDTO.username;

                if(this.ascSortFacility == 0 || this.ascSortFacility == -1) {

                    if(this.personalVisible == 'true'){
                        this.sortDTO.type = "Personal";
    
                        axios
                        .post("rest/coaches/asc", this.sortDTO)
                        .then(response => (this.personalWorkouts = response.data));
    
                    } else if(this.personalVisible == 'false') {
                        this.sortDTO.type = "Group";
    
                        axios
                        .post("rest/coaches/asc", this.sortDTO)
                        .then(response => (this.groupWorkouts = response.data));
    
                    }

                    this.ascSortFacility = 1;
                } else if(this.ascSortFacility == 1) {

                    if(this.personalVisible == 'true'){
                        this.sortDTO.type = "Personal";
    
                        axios
                        .post("rest/coaches/desc", this.sortDTO)
                        .then(response => (this.personalWorkouts = response.data));
    
                    } else if(this.personalVisible == 'false') {
                        this.sortDTO.type = "Group";
    
                        axios
                        .post("rest/coaches/desc", this.sortDTO)
                        .then(response => (this.groupWorkouts = response.data));
    
                    }

                    this.ascSortFacility = -1;
                }

            }
        },
        search: function() {
            var table = document.getElementById("myTable");
            var input_facility = document.getElementById("facilitySearch");
            let filter_name = input_facility.value.toUpperCase();
            let tr = table.rows;
            var isHeaderRow = true;

            for(let i = 0; i < tr.length; i++) {
                if(isHeaderRow){
                    isHeaderRow = false;
                    continue;
                }
                td = tr[i].cells;
                td_facilityName = td[1].innerText;
                if (td_facilityName.toUpperCase().indexOf(filter_name) > -1) {
                    tr[i].style.display = "";
                  } else
                    tr[i].style.display = "none";
            } 
        },
        searchDate: function() {
            if((this.startDate !== null && this.startDate !== "") && (this.endDate !== null && this.endDate !== "")) {
                const split_startDate = this.startDate.split("-");
                this.SearchCoachDateDTO.startDate.year = parseInt(split_startDate[0]);
                this.SearchCoachDateDTO.startDate.month = parseInt(split_startDate[1]);
                this.SearchCoachDateDTO.startDate.day = parseInt(split_startDate[2]);

                const split_endDate = this.endDate.split("-");
                this.SearchCoachDateDTO.endDate.year = parseInt(split_endDate[0]);
                this.SearchCoachDateDTO.endDate.month = parseInt(split_endDate[1]);
                this.SearchCoachDateDTO.endDate.day = parseInt(split_endDate[2]);

                if(this.SearchCoachDateDTO.startDate.year > this.SearchCoachDateDTO.endDate.year) {
                    alert("Nije moguce da pocetni datum bude nakon krajnjeg!");
                    this.startDate = null;
                    this.endDate = null;
                    return;
                } else if(this.SearchCoachDateDTO.startDate.year === this.SearchCoachDateDTO.endDate.year
                     && this.SearchCoachDateDTO.startDate.month > this.SearchCoachDateDTO.endDate.month){
                    alert("Nije moguce da pocetni datum bude nakon krajnjeg!");
                    this.startDate = null;
                    this.endDate = null;
                    return;
                } else if(this.SearchCoachDateDTO.startDate.year === this.SearchCoachDateDTO.endDate.year
                     && this.SearchCoachDateDTO.startDate.month === this.SearchCoachDateDTO.endDate.month
                    && this.SearchCoachDateDTO.startDate.day > this.SearchCoachDateDTO.endDate.day ) {
                    alert("Nije moguce da pocetni datum bude nakon krajnjeg!");
                    this.startDate = null;
                    this.endDate = null;
                    return;
                }

                if(this.personalVisible == 'true') {
                    this.SearchCoachDateDTO.isPersonal = true;
                } else if(this.personalVisible == 'false') {
                    this.SearchCoachDateDTO.isPersonal = false;
                }
                this.SearchCoachDateDTO.coachUsername = this.LoggedUserDTO.username;

                axios
                .post('rest/coaches/search', this.SearchCoachDateDTO)
                .then(response => {

                    if(this.personalVisible == 'true'){
                        this.personalWorkouts = response.data;
                        this.groupWorkouts = this.allGroupWorkouts;
                    } else if(this.personalVisible == 'false'){
                        this.groupWorkouts = response.data;
                        this.personalWorkouts = this.allPersonalWorkouts;
                    }
                    
                })
            } else {
                this.personalWorkouts = this.allPersonalWorkouts;
                this.groupWorkouts = this.allGroupWorkouts;
            }
        },
        getWorkoutName: function(name, tr) {
            this.workoutName = name;
        }
        ,
        cancelW : function() {

            if(this.workoutName == '') {
                alert("Niste izabrali trening.");
                this.workoutName = '';
                return;
            }

            this.cancelTrainingDTO.username = this.username;
            this.cancelTrainingDTO.trainingName = this.workoutName;

            axios
            .delete('rest/coaches/cancelPersonal', {
                data : this.cancelTrainingDTO
            })
            .then(response => {
                
                this.deletedTHistory = response.data
            
                if(this.deletedTHistory == null) {
                    alert("Nije moguce otkazati ovaj pesonalni trening.")
                    return;
                } else {
                    confirm("Uspesno ste otkazali odabrani personalni trening.")
                    axios.get('rest/coaches/personal/' + this.username).then(response => { 
                        this.personalWorkouts = response.data;
                        this.allPersonalWorkouts = this.personalWorkouts;
                        this.flagP = false;
                    })
                }

            });

            this.workoutName = '';
        }
    },
    mounted () {

        this.ascSortDate = 0;
        this.ascSortFacility = 0;
        this.selectedRow = null;
        this.personalVisible = "true";
        this.flagP = false;
        this.flagG = false;

        this.jwt = window.localStorage.getItem("JWT");
        let config = {
          headers: {
            Authorization: "Bearer " + this.jwt
          }
        }

        axios.get('rest/users/logged', config).then(response => {
            this.LoggedUserDTO = response.data;
            this.username = this.LoggedUserDTO.username;

            axios
            .get('rest/coaches/personal/' + this.username)
            .then(response => {
                
                this.personalWorkouts = response.data
            
                this.personalWorkouts.forEach(workout => {
                    if(parseInt(workout.dateAndTimeOfCheckIn.minutes) < 10) {
                        workout.dateAndTimeOfCheckIn.minutes = "0" + workout.dateAndTimeOfCheckIn.minutes;
                    }
                });

                this.allPersonalWorkouts = this.personalWorkouts;
            });


            axios
            .get('rest/coaches/group/' + this.username)
            .then(response => {
            
                this.groupWorkouts = response.data
            
                this.groupWorkouts.forEach(workout => {
                    if(parseInt(workout.dateAndTimeOfCheckIn.minutes) < 10) {
                        workout.dateAndTimeOfCheckIn.minutes = "0" + workout.dateAndTimeOfCheckIn.minutes;
                    }
                });

                this.allGroupWorkouts = this.groupWorkouts;
            });
        })

    }
});