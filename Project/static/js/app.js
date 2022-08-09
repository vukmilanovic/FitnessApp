const Product = { template: '<edit-product></edit-product>' }
const Products = { template: '<products></products>' }
const Buildings = {template: '<buildings></buildings>'}


const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: Buildings},
	    { path: '/products', component: Products}
	  ]
});

var app = new Vue({
	router,
	el: '#buildings'
});
