Vue.component("navigation", {
	data: function() {
		return {
			userType: "",
			userVisibility: [true, false, false, false, false],
			loggedIn: false,
			username: '',
			LoggedUserDTO: { username: '', role: null },
			firstTime: false,
			feeExpired: null
		}
	},
	template: `
    
    <div>
        <nav class="navbar navbar-inverse" id="radius">
            <div class="container-fluid">
                <div class="navbar-header">
                    <div class="navbar-brand listE">Fitness Pro xD</div>
                </div>
                <ul class="nav navbar-nav">
                    <li class="listE" v-on:click = "showFacilities">Sport facilities</li>
                    <li class="listE" v-if="userVisibility[0]===false" v-on:click = "showProfileSettings">Profile</li>
                    <li class="listE" v-if="userVisibility[1]===true" v-on:click = "showCustomerWorkouts">My workouts</li>
                    <li class="listE" v-if="userVisibility[1]===true" v-on:click = "showPayingForm">Membership fee</li>
                    <li class="listE" v-if="userVisibility[1]===true && feeExpired===false" v-on:click = "showBookPracticeForm">Book practice</li>
                    <li class="listE" v-if="userVisibility[3]===true" v-on:click = "showAddContent">Your content</li>
                    <li class="listE" v-if="userVisibility[4]===true" v-on:click = "showAddStuff">Add stuff</li>
                    <li class="listE" v-if="userVisibility[4]===true" v-on:click = "showUsersReview">Users review</li>
                    <li class="listE" v-if="userVisibility[4]===true" v-on:click = "showPromoForm">Create promo code</li>
                    <li class="listE" v-if="userVisibility[4]===true" v-on:click = "showPendingComments">Comments approvement</li>
                    <li class="listE" v-if="userVisibility[4]===true" v-on:click= "showAddFacility">Add sport facilitiy</li>
                    <li class="listE" v-if="userVisibility[2]===true" v-on:click = "showCoachesBookedWorkouts">Workout schedule</li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="listE" v-on:click = "showSignUpForm" v-if="userVisibility[0]===true"><span class="glyphicon glyphicon-user"></span> Sign Up</li>
                    <li class="listE" v-on:click = "showLogInForm" v-if="userVisibility[0]===true"><span class="glyphicon glyphicon-log-in"></span> Log in</li>
                    <li class="listE" v-if="userVisibility[0]===false" value="username"><span class="glyphicon glyphicon-user"></span>   {{username}}</li>
                    <li class="listE" v-on:click = "logOut" v-if="userVisibility[0]===false"><span class="glyphicon glyphicon-log-out"></span> Log out</li>
                </ul>
            </div>
        </nav>
    </div>

    ` ,
    methods: {
        showFacilities : function() {
            this.$router.push('/facilities');
        },
        showSignUpForm : function() {
            this.$router.push('/registration');
        },
        showLogInForm : function() {
            this.$router.push('/');
        },
        logOut : function() {
            window.localStorage.removeItem("JWT");
            this.firstTime = false;
            this.loggedIn = false;
            this.LoggedUserDTO = null;
            this.$router.push('/');
        },
        showProfileSettings : function() {
            this.$router.push('/profile');
        },
        showCustomerWorkouts : function() {
            this.$router.push('/workouts');
        },
        showCoachesBookedWorkouts : function() {
            this.$router.push('/coachesw');
        },
        showUsersReview : function() {
            this.$router.push('/usersreview');
        },
        showAddStuff : function() {
            this.$router.push('/addstuff');
        },
        showPendingComments : function() {
            this.$router.push('/comments');
        },
        showBookPracticeForm : function() {
            this.$router.push('/practice');
        },
        showAddContent: function(){
			this.$router.push('/addContent');
		},
        showPayingForm : function() {
            this.$router.push('/paying');
        },
        showPromoForm : function() {
            this.$router.push('/promoCode');
        },
		showAddFacility: function(){
			this.$router.push('/addFacility')
		}
    },
    watch:{
        $route (to, from){

            if(this.$route.name === 'home' || this.$route.name === 'registration'
            || (this.$route.name === 'facilities' && this.LoggedUserDTO == null) || (this.$route.name ='ShowFacility' && this.LoggedUserDTO == null)) {
                this.loggedIn = false;
            }
            if(this.loggedIn !== true) {
                if(this.$route.name !== 'home' && this.$route.name !== 'registration' && this.$route.name !== 'facilities' && this.$route.name !== 'ShowFacility'){
                    this.loggedIn = true;
                } else {
                    this.userType = "Unregistered";
                    this.userVisibility = [true, false, false, false, false]
                }
            }
            if(from.path == '/profile') {
                this.jwt = window.localStorage.getItem("JWT");
                let config = {
                  headers: {
                    Authorization: "Bearer " + this.jwt
                  }
                }

				axios.get("rest/users/logged", config).then(response => {
					this.LoggedUserDTO = response.data
					this.userType = this.LoggedUserDTO.role;
					this.username = this.LoggedUserDTO.username;
				})
			}
			if (this.loggedIn == true && this.firstTime == false) {
				this.firstTime = true;
				this.jwt = window.localStorage.getItem("JWT");
				let config = {
					headers: {
						Authorization: "Bearer " + this.jwt
					}
				}

				axios.get("rest/users/logged", config).then(response => {
					this.LoggedUserDTO = response.data

					this.userType = this.LoggedUserDTO.role;
					this.username = this.LoggedUserDTO.username;

					if (this.userType === 'Customer') {
						this.userVisibility = [false, true, false, false, false]
					} else if (this.userType === 'Coach') {
						this.userVisibility = [false, false, true, false, false]
					} else if (this.userType === 'Manager') {
						this.userVisibility = [false, false, false, true, false]
					} else if (this.userType === 'Administrator') {
						this.userVisibility = [false, false, false, false, true]
					}


					if (from.path == '/' && this.userType == 'Customer') {

						axios
							.get("rest/payment/check/" + this.username)
							.then(response => {

								this.feeExpired = response.data;

							})

					}
				})
			}
			if (from.path == '/paying' && this.userType == 'Customer') {
				axios
					.get("rest/payment/check/" + this.username)
					.then(response => {

						this.feeExpired = response.data;

					})
			}
			if (from.path == '/registration' && this.userType == 'Customer') {
				this.feeExpired = true;
			}
		}
	},
	mounted() {
		this.userType = "Unregistered";
		this.loggedIn = false;
		this.firstTime = false;
	}
})