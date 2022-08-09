const Buildings = { template: '<buildings></buildings>' }

const router = new VueRouter({
	mode: 'hash',
	routes: [
		{ path: '/', name: 'home', component: Buildings }
	]
});


var app = new Vue({
	router,
	el: '#buildings'
});