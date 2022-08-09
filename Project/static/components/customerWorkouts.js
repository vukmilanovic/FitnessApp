Vue.component("workouts", {
    data: function() {
        return{
            allWorkouts: null,
            workouts: null,
            startDate : null,
            endDate: null,
            LoggedUserDTO : { username : '', role : null },
            SearchDateDTO : { customerUsername : '', startDate : { day: null, month: null, year: null, hours:null, minutes: null }, endDate : { day: null, month: null, year: null, hours:null, minutes: null }},
            sortDTO : { sortBy : '', username : '', type: null },
            ascSortFacility : null,
            ascSortDate : null,
            workoutType: null,
            buildingType: null,
        }
    },
    template:
    `
    <div class="container tableForm">
        <h2>Customer workouts</h2>
        <p>This is the list of all customer workouts in last 30 days:</p>  
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
                <select class="form-control" name="workouts" id="workouts" v-model = "workoutType" v-on:change="filterWorkoutType">
                    <option class="form-control" value="ALL">All</option>
                    <option class="form-control" value="Personal">Personal</option>
                    <option class="form-control" value="Group">Group</option>
                </select>
            </div>
            <div class="inLine comboWorkout" style="margin-left: 1cm;">
                <label for="facilitiesType">Facility type:</label>
                <select class="form-control" name="facilitiesType" id="facilitiesType" v-model = "buildingType" v-on:change="filterFacilityType">
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
                    <th>Coach</th>
                    <th>Workout type</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="w in workouts">
                    <td>{{w.training.name}}</td>
                    <td>{{w.training.sportsBuilding.name}}</td>
                    <td>{{w.dateAndTimeOfCheckIn.day}}/{{w.dateAndTimeOfCheckIn.month}}/{{w.dateAndTimeOfCheckIn.year}}</td>
                    <td>{{w.coach.name}} {{w.coach.lastname}}</td>
                    <td>{{w.training.trainingType}}</td>
                </tr>
            </tbody>
        </table>
    </div>
    `,
    methods: {
        filterFacilityType: function() {

            this.startDate = null;
            this.endDate = null;
            this.workoutType = 'ALL';

            if(this.buildingType == 'ALL') {
                this.workouts = this.allWorkouts;
                return;
            }

            axios
            .get('rest/customers/workouts/filterb', {
                params: {
                    username: this.LoggedUserDTO.username,
                    type: this.buildingType
                }
            })
            .then(response => {
                this.workouts = response.data;
            })
        },

        filterWorkoutType: function() {

            this.startDate = null;
            this.endDate = null;
            this.buildingType = 'ALL';

            if(this.workoutType == 'ALL') {
                this.workouts = this.allWorkouts;
                return;
            }

            axios
            .get('rest/customers/workouts/filtert', {
                params: {
                    username: this.LoggedUserDTO.username,
                    workoutType: this.workoutType
                }
            })
            .then(response => {
                this.workouts = response.data;
            })
        },
        sortFacility: function(column) {
            if(column == 'Facility'){

                this.sortDTO.sortBy = 'Facility';
                this.sortDTO.username = this.LoggedUserDTO.username;

                if(this.ascSortFacility == 0 || this.ascSortFacility == -1) {
                    //asc sorting

                    axios
                    .post("rest/customers/asc", this.sortDTO)
                    .then(response => {

                        this.workouts = response.data;
                        this.workouts.forEach(workout => {
                            if(workout.coach == null) {
                                workout.coach = { name : '', lastname : '' };
                            }
                        });

                    })

                    this.ascSortFacility = 1;
                } else if(this.ascSortFacility == 1) {
                    //desc sorting

                    axios
                    .post("rest/customers/desc", this.sortDTO)
                    .then(response => {

                        this.workouts = response.data;
                        this.workouts.forEach(workout => {
                            if(workout.coach == null) {
                                workout.coach = { name : '', lastname : '' };
                            }
                        });

                    })

                    this.ascSortFacility = -1;
                }
            }
        },
        sortDate: function(column) {
            if(column == 'Date'){

                this.sortDTO.sortBy = 'Date';
                this.sortDTO.username = this.LoggedUserDTO.username;

                if(this.ascSortDate == 0 || this.ascSortDate == -1) {
                    //asc sorting

                    axios
                    .post("rest/customers/asc", this.sortDTO)
                    .then(response => {

                        this.workouts = response.data;
                        this.workouts.forEach(workout => {
                            if(workout.coach == null) {
                                workout.coach = { name : '', lastname : '' };
                            }
                        });

                    })

                    this.ascSortDate = 1;
                } else if(this.ascSortDate == 1) {
                    //desc sorting

                    axios
                    .post("rest/customers/desc", this.sortDTO)
                    .then(response => {

                        this.workouts = response.data;
                        this.workouts.forEach(workout => {
                            if(workout.coach == null) {
                                workout.coach = { name : '', lastname : '' };
                            }
                        });

                    })

                    this.ascSortDate = -1;
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
            this.buildingType = 'ALL';
            this.workoutType = 'ALL';

            if((this.startDate !== null && this.startDate !== "") && (this.endDate !== null && this.endDate !== "")) {
                const split_startDate = this.startDate.split("-");
                this.SearchDateDTO.startDate.year = parseInt(split_startDate[0]);
                this.SearchDateDTO.startDate.month = parseInt(split_startDate[1]);
                this.SearchDateDTO.startDate.day = parseInt(split_startDate[2]);

                const split_endDate = this.endDate.split("-");
                this.SearchDateDTO.endDate.year = parseInt(split_endDate[0]);
                this.SearchDateDTO.endDate.month = parseInt(split_endDate[1]);
                this.SearchDateDTO.endDate.day = parseInt(split_endDate[2]);

                if(this.SearchDateDTO.startDate.year > this.SearchDateDTO.endDate.year) {
                    alert("Nije moguce da pocetni datum bude nakon krajnjeg!");
                    this.startDate = null;
                    this.endDate = null;
                    return;
                } else if(this.SearchDateDTO.startDate.year === this.SearchDateDTO.endDate.year
                     && this.SearchDateDTO.startDate.month > this.SearchDateDTO.endDate.month){
                    alert("Nije moguce da pocetni datum bude nakon krajnjeg!");
                    this.startDate = null;
                    this.endDate = null;
                    return;
                } else if(this.SearchDateDTO.startDate.year === this.SearchDateDTO.endDate.year
                     && this.SearchDateDTO.startDate.month === this.SearchDateDTO.endDate.month
                    && this.SearchDateDTO.startDate.day > this.SearchDateDTO.endDate.day ) {
                    alert("Nije moguce da pocetni datum bude nakon krajnjeg!");
                    this.startDate = null;
                    this.endDate = null;
                    return;
                }

                this.SearchDateDTO.customerUsername = this.LoggedUserDTO.username;

                axios
                .post('rest/customers/searchDate', this.SearchDateDTO)
                .then(response => {
                    this.workouts = null;
                    this.workouts = response.data;
                    this.workouts.forEach(workout => {
                        if(workout.coach == null) {
                            workout.coach = { name : '', lastname : '' };
                        }
                    }); 
                })
            } else {
                this.workouts = null;
                this.workouts = this.allWorkouts;
            }
        }
    },
    mounted () {

        this.ascSortDate = 0;
        this.ascSortFacility = 0;
        this.buildingType = 'ALL';
        this.workoutType = 'ALL';

        this.jwt = window.localStorage.getItem("JWT");
        let config = {
          headers: {
            Authorization: "Bearer " + this.jwt
          }
        }

        axios.get('rest/users/logged', config).then(response => {
            this.LoggedUserDTO = response.data;
          
            axios
            .get('rest/customers/trainings/' + this.LoggedUserDTO.username)
            .then(response => {
                this.workouts = response.data;

                this.workouts.forEach(workout => {
                    if(workout.coach == null) {
                        workout.coach = { name : '', lastname : '' };
                    }
                });

                this.allWorkouts = response.data;
            })
          
        })
    }
});