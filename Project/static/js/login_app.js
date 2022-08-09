const NavigationBar = { template: '<navigation></navigation>' }
const Login = { template: '<login></login>' }
const Registration = { template: '<registration></registration>' }
const Facilities = { template: '<buildings></buildings>' }
const ShowFacility = {template: '<show-building></show-building>'}
const HomePage = { template: '<homepage></homepage>' }
const Profile = { template: '<profile></profile>' }
const Workouts = { template: '<workouts></workouts>' }
const CoachesW = { template: '<coachesw></coachesw>' }
const Users = { template: '<usersreview></usersreview>' }
const AddStuff = { template: '<addstuff></addstuff>' }
const Comments = { template: '<comments></comments>' }
const Practice = { template: '<practice></practice>' }
const Paying = { template: '<paying></paying>' }
const Code = { template: '<promoCode></promoCode>' }
const AddFacility = { template: '<addFacility></addFacility>' }
const AddContent = { template: '<add-content></add-content>' }

const router = new VueRouter({
	mode: 'hash',
	routes: [
		{
			path: '/',
			name: 'home',
			components: {
				a: NavigationBar,
				b: Login
			}
		},
		{
			path: '/registration',
			name: 'registration',
			components: {
				a: NavigationBar,
				b: Registration
			}
		},
		{
			path: '/homepage',
			name: 'homepage',
			components: {
				a: NavigationBar,
				b: Facilities
			}
		},
		{
			path: '/facilities',
			name: 'facilities',
			components: {
				a: NavigationBar,
				b: Facilities
			}
		},
		{
			path: '/buildings/:name',
			name: 'ShowFacility',
			components: {
				a: NavigationBar,
				b: ShowFacility
			}  
		},
		{
			path: '/profile',
			name: 'profile',
			components: {
				a: NavigationBar,
				b: Profile
			}
		},
		{
			path: '/workouts',
			name: 'workouts',
			components: {
				a: NavigationBar,
				b: Workouts
			}
		},
		{
			path: '/coachesw',
			name: 'coachesw',
			components: {
				a: NavigationBar,
				b: CoachesW
			}
		},
		{
			path: '/usersreview',
			name: 'usersreview',
			components: {
				a: NavigationBar,
				b: Users
			}
		},
		{
			path: '/addstuff',
			name: 'addstuff',
			components: {
				a: NavigationBar,
				b: AddStuff
			}
		},
		{
			path: '/comments',
			name: 'comments',
			components: {
				a: NavigationBar,
				b: Comments
			}
		},
		{
			path: '/practice',
			name: 'practice',
			components: {
				a: NavigationBar,
				b: Practice
			}
		},
		{
			path: '/paying',
			name: 'paying',
			components: {
				a: NavigationBar,
				b: Paying
			}  
		},
		{ 
			path: '/promoCode',
			name: 'promoCode', 
			components: {
				a: NavigationBar,
				b: Code
			}
		},
		{
			path: '/addFacility',
			name: 'addFacility',
			components: {
				a: NavigationBar,
				b: AddFacility
			}
		},
		{
			path: '/addContent',
			name: 'addContent',
			components: {
				a: NavigationBar,
				b: AddContent
			}
		}
	]
});

var app = new Vue({
	router,
	el: '#login'
});