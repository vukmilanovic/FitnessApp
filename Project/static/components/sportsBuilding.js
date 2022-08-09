Vue.component("buildings", {
	data: function () {
		return{
			buildings: null,
			query: {name: null, type: null, location: null, averageRating: null, open: null},
			nameSortFlag: false,
			locSortFlag: false,
			ratingSortFlag: false
		}
	},
	template:
	`
	<div>
		<div class="encapsulate">
			<div>
			<form class="form-inline">
				<input class="form-control mb-2 mr-sm-2" type="text" v-model="query.name" id="name" name="name" placeholder="Ime objekta">
					
				<select class="form-control mb-2 mr-sm-2" name="type" v-model="query.type">
					<option value="" disabled selected>Tip objekta...</option>
					<option value="All">Svi tipovi</option>
					<option value="Gym">Teretana</option>
					<option value="Pool">Bazen</option>
					<option value="Sports Center">Sportski centar</option>
					<option value="Dance Studio">Plesni studio</option>
				</select>

				<input class="form-control mb-2 mr-sm-2" type="text" v-model="query.location" name="loc" placeholder="Lokacija objekta">

				<input class="form-control mb-2 mr-sm-2" type="text" v-model="query.averageRating" name="avg" placeholder="Rejting objekta">
					
				<div class="form-check mb-2 mr-sm-2">
				<input type="radio" class="form-radio-input" v-model="query.open" name="open" value="open" id="open" >
				<label class="form-check-label" for="open" style="padding-right: 10px"> Otvoreni </label>

				<input type="radio" class="form-radio-input" v-model="query.open" name="open" value="all" id="all" >
				<label class="form-check-label" for="all"> Svi </label>
				</div><br>

				
				<input type="submit" class="btn btn-primary mb-2 commentButton" v-on:click="queryBuildings" value="Pretrazi" style="margin-right: 10px">
				<input type="submit" class="btn btn-primary mb-2 commentButton" v-on:click="sortName" value="Sortiraj imena" style="margin-right: 10px" >
				<input type="submit" class="btn btn-primary mb-2 commentButton" v-on:click="sortLoc" value="Sortiraj lokacije" style="margin-right: 10px" >
				<input type="submit" class="btn btn-primary mb-2 commentButton" v-on:click="sortRating" value="Sortiraj ocene" >
			</form>

			<br>
			</div>
			
			<div>
			<ul class="list-unstyled">
				<li class="media" v-for="b in buildings" :key="query.name+query.type+query.location+query.averageRating" style="border: 3px solid #ddd" v-on:click="showBuilding(b.name)">
					<img class="mr-3" :src="b.logoPath" v-bind:alt="b.name" style="margin-right: 20px" />
					<div class="media-body">
						<h5><b>{{b.name}}, {{parseType(b.type)}}</b></h5>
						<h5><b>{{b.location.address.street}}, {{b.location.address.number}}</b></h5>
						<p class="geoloc">{{b.location.address.city}}, {{b.location.address.postalCode}}</p>
						<p>Prosecna ocena: <b>{{b.averageRating}}/5</b></p>
						<p>Radno vreme <b>PON-SUB {{b.startWorkingTime.hours}}-{{b.endWorkingTime.hours}}h</b></p>
					</div>
				</li>
			</ul>
			</div>
		</div>
	</div>
	`,
	mounted(){
		axios.get('rest/buildings/').then(response => (this.buildings = response.data))
	},
	methods: {
		queryBuildings: function(){
			event.preventDefault();
			axios.get('rest/buildings/query', {
				params: {
					name: this.query.name,
					type: this.query.type,
					loc: this.query.location,
					avg: this.query.averageRating,
					open: this.query.open
				}
			}).then(response => (this.buildings = response.data));
		},
		
		showBuilding: function(name){
			router.push(`/buildings/${name}`);
		},
		
		sortName: function(){
			event.preventDefault();
			this.sortNameFlag = !this.sortNameFlag;
			if(this.sortNameFlag == false){
				axios.post('rest/buildings/sort/name/asc', this.buildings)
				.then(response => (this.buildings = response.data))
			}
			else {
				axios.post('rest/buildings/sort/name/desc', this.buildings)
				.then(response => (this.buildings = response.data))
			}
		},
		
		sortLoc: function(){
			event.preventDefault();
			this.sortNameFlag = !this.sortNameFlag;
			if(this.sortNameFlag == false){
				axios.post('rest/buildings/sort/loc/asc', this.buildings)
				.then(response => (this.buildings = response.data))
			}
			else {
				axios.post('rest/buildings/sort/loc/desc', this.buildings)
				.then(response => (this.buildings = response.data))
			}
		},
		
		sortRating: function(){
			event.preventDefault();
			this.sortNameFlag = !this.sortNameFlag;
			if(this.sortNameFlag == false){
				axios.post('rest/buildings/sort/rating/asc', this.buildings)
				.then(response => (this.buildings = response.data))
			}
			else {
				axios.post('rest/buildings/sort/rating/desc', this.buildings)
				.then(response => (this.buildings = response.data))
			}
		},
		
		parseType: function(type){
			retval = "";
			
			switch(type){
				case 'GYM':
					retval = 'Teretana';
					break;
				case 'POOL':
					retval = 'Bazen';
					break;
				case 'SPORTS_CENTER':
					retval = 'Sportski centar';
					break;
				case 'DANCE_STUDIO':
					retval = 'Plesni studio';
					break;
				default:
					retval = "Ne treba da budes ovde";
					break;
			}
			
			return retval;
		}
	}
});