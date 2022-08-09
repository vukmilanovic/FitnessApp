Vue.component("usersreview", {
    data : function() {
        return {
            users: null,
            customerType: '',
            visible: '',
            usersByType: null,
            ascSortName: null,
            ascSortUsername: null,
            ascSortLastname: null,
            ascSortPoints: null,
            sortDTO : { sortBy : '', username : '', type: null }
        }
    },

    template:
    `
    <div class="container tableForm">
        <div class="inlineElements">
            <h2>Users</h2>
            <p v-if="visible=='All'">This is the list of all users in system database:</p>
            <p v-if="visible=='Administrator'">This is the list of all admins in system database:</p>
            <p v-if="visible=='Customer'">This is the list of all customers in system database:</p>
            <p v-if="visible=='Coach'">This is the list of all coaches in system database:</p>
            <p v-if="visible=='Manager'">This is the list of all managers in system database:</p>            
        </div>
        <div>
            <div style="margin-top: 1cm;" class="inLine">
                <div class="inLine">
                    <label style="font-size: medium;">Search: </label>
                    <input type="text" class="form-control field" id="userSearch" v-on:keyup="search" placeholder="Search user...">
                </div>
                <div class="comboWorkout inLine" style="margin-left: 1.5cm;">
                    <label for="workouts">Role:</label>
                    <select name="workouts" class="form-control" id="workouts" v-model = "visible">
                        <option class="form-control" value="All">All</option>
                        <option class="form-control" value="Administrator">Admin</option>
                        <option class="form-control" value="Customer">Customer</option>
                        <option class="form-control" value="Coach">Coach</option>
                        <option class="form-control" value="Manager">Manager</option>
                    </select>
                </div>
                <div v-if="visible=='Customer'" class="comboWorkout inLine" style="margin-left: 1.5cm;">
                    <label for="customerT">CustomerType:</label>
                    <select class="form-control" name="customerT" id="customerT" v-model = "customerType">
                        <option class="form-control" value="All">All</option>
                        <option class="form-control" value="Bronze">Bronze</option>
                        <option class="form-control" value="Silver">Silver</option>
                        <option class="form-control" value="Gold">Gold</option>
                    </select>
                </div>
                <div style="clear:both;"></div>
            </div>
        </div>
        <table class="table table-hover" id="myTable" style="margin-top: 1.5cm;">
            <thead>
                <tr>
                    <th @click="sortName('Name')" class="headerElements">Name</th>
                    <th @click="sortLastname('Lastname')" class="headerElements">Lastname</th>
                    <th @click="sortUsername('Username')" class="headerElements">Username</th>
                    <th>Gender</th>
                    <th>Date of birth</th>
                    <th>Role</th>
                    <th @click="sortPoints('Points')" class="headerElements">Points</th>
                </tr>
            </thead>
            <tbody>
                <tr v-if="visible=='All'" v-for="u in users">
                    <td>{{u.name}}</td>
                    <td>{{u.lastname}}</td>
                    <td>{{u.username}}</td>
                    <td>{{u.gender}}</td>
                    <td>{{u.dateOfBirth.day}}.{{u.dateOfBirth.month}}.{{u.dateOfBirth.year}}.</td>
                    <td>{{u.role}}</td>
                    <td>{{u.numberOfCollectedPoints}}</td>
                </tr>
                <tr v-if="visible!='All'" v-for="u in usersByType">
                    <td>{{u.name}}</td>
                    <td>{{u.lastname}}</td>
                    <td>{{u.username}}</td>
                    <td>{{u.gender}}</td>
                    <td>{{u.dateOfBirth.day}}.{{u.dateOfBirth.month}}.{{u.dateOfBirth.year}}.</td>
                    <td>{{u.role}}</td>
                    <td>{{u.numberOfCollectedPoints}}</td>
                </tr>
            </tbody>
        </table>
    </div>

    `,
    methods: {
        sortName: function(column) {
            if(column == 'Name'){

                this.sortDTO.sortBy = 'Name';

                if(this.ascSortName == 0 || this.ascSortName == -1){
                    //asc sorting

                    axios
                    .post("rest/admin/asc", this.sortDTO)
                    .then(response => {

                        this.users = response.data;

                    });

                    this.ascSortName = 1;
                } else if (this.ascSortName == 1) {
                    //desc sorting

                    axios
                    .post("rest/admin/desc", this.sortDTO)
                    .then(response => {

                        this.users = response.data;

                    });

                    this.ascSortName = -1;
                }
            } 
        },
        sortUsername: function(column) {
            if(column == 'Username') {

                this.sortDTO.sortBy = 'Username';

                if(this.ascSortUsername == 0 || this.ascSortUsername == -1){
                    //asc sorting

                    axios
                    .post("rest/admin/asc", this.sortDTO)
                    .then(response => {

                        this.users = response.data;

                    });

                    this.ascSortUsername = 1;
                } else if (this.ascSortUsername == 1) {
                    //desc sorting

                    axios
                    .post("rest/admin/desc", this.sortDTO)
                    .then(response => {

                        this.users = response.data;

                    });

                    this.ascSortUsername = -1;
                }
            }
        },
        sortLastname: function(column) {
            if(column == 'Lastname') {

                this.sortDTO.sortBy = 'Lastname';

                if(this.ascSortLastname == 0 || this.ascSortLastname == -1){
                    //asc sorting

                    axios
                    .post("rest/admin/asc", this.sortDTO)
                    .then(response => {

                        this.users = response.data;

                    });

                    this.ascSortLastname = 1;
                } else if (this.ascSortLastname == 1) {
                    //desc sorting

                    axios
                    .post("rest/admin/desc", this.sortDTO)
                    .then(response => {

                        this.users = response.data;

                    });

                    this.ascSortLastname = -1;
                }
            }
        }, 
        sortPoints: function(column) {
            if(column == 'Points') {

                this.sortDTO.sortBy = 'Points';

                if(this.ascSortPoints == 0 || this.ascSortPoints == -1){
                    //asc sorting

                    axios
                    .post("rest/admin/asc", this.sortDTO)
                    .then(response => {

                        this.users = response.data;

                    });

                    this.ascSortPoints = 1;
                } else if (this.ascSortPoints == 1) {
                    //desc sorting

                    axios
                    .post("rest/admin/desc", this.sortDTO)
                    .then(response => {

                        this.users = response.data;

                    });

                    this.ascSortPoints = -1;
                }
            }
        }
        ,
        search: function() {
            var table = document.getElementById("myTable");
            var input_facility = document.getElementById("userSearch");
            let filter_name = input_facility.value.toUpperCase();
            let tr = table.rows;
            var isHeaderRow = true;

            for(let i = 0; i < tr.length; i++) {
                if(isHeaderRow){
                    isHeaderRow = false;
                    continue;
                }
                td = tr[i].cells;
                td_name = td[0].innerText;
                td_lastname = td[1].innerText;
                td_username = td[2].innerText;
                if (td_name.toUpperCase().indexOf(filter_name) > -1 || td_lastname.toUpperCase().indexOf(filter_name) > -1 || td_username.toUpperCase().indexOf(filter_name) > -1) {
                    tr[i].style.display = "";
                  } else
                    tr[i].style.display = "none";
            } 
        }
    },
    mounted () {
        this.visible = 'All';
        this.customerType = 'All';
        this.ascSortName = 0;
        this.ascSortUsername = 0;
        this.ascSortLastname = 0;
        this.ascSortPoints = 0;
        axios.get('rest/admin/users').then(response => (this.users = response.data))
    }, 
    updated () {
        if(this.customerType != 'All' && this.visible == 'Customer') {
            axios.get('rest/admin/customerType', {
                params: {
                    type: this.customerType
                }
            }).then(response => (this.usersByType = response.data))
        } else {
            axios.get('rest/admin/filter', {
                params: {
                    role: this.visible
                }
            }).then(response => (this.usersByType = response.data))
        }
    }
});