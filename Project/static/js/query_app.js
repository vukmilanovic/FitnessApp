const Query = { template: '<query-buildings></query-buildings>' }

const router = new VueRouter({
	mode: 'hash',
	routes: [
		{path: '/', name: 'home', component: Query}
	]
});

var app = new Vue({
	router,
	el: '#query'
});