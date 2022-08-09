Vue.component("query-buildings", {
	data: function() {
		return {
			buildings: null,
			query: {name: null, type: null, location: null, averageRating: null},
			visible: false
		}
	},
	template:
	`
	<div>
	<h3>Pretraga objekata</h3>
		<form>
			<table>
				<tr>
					<td><label>Ime objekta</label></td>
					<td><input type="text" v-model="query.name" name="name"></td>
				</tr>
				
				<tr>
				<td><label>Tip objekta</label></td>
					<td><select name="type" v-model="query.type">
						<option value="Gym">Teretana</option>
						<option value="Pool">Bazen</option>
						<option value="Sports Center">Sportski centar</option>
						<option value="Dance Studio">Plesni studio</option>
					</select></td>
				</tr>
				
				<tr>
					<td><label>Lokacija objekta</label></td>
					<td><input type="text" v-model="query.location" name="loc"></td>
				</tr>
				
				<tr>
					<td><label>Prosecna ocena</label></td>
					<td><input type="text" v-model="query.averageRating" name="avg"></td>
				</tr>
				
				<tr>
					<td><input type="submit" v-on:click="queryBuildings" value="Pretrazi"></td>
				</tr>
			</table>
		</form><br><br>
		
		<div v-if="visible===true">
			<h3>Pronadjeni objekti</h3><br>
			<div v-for="b in buildings">
			<div class="box">
	            <div class="inner">
	                <img :src="b.logoPath" v-bind:alt="b.name" />
	            </div>
	            <div class="inner">
	                <p class="street">{{b.location.address.street}}, {{b.location.address.number}}</p>
	                <p>{{b.location.address.city}}, {{b.location.address.postalCode}}</p>
	                <p class="geoloc">{{b.location.geoWidth}}, {{b.location.geoLen}}</p>
	            </div>
	        </div><br>
        </div>
		</div>
	</div>
	`,
	methods: {
		queryBuildings: function(){
			event.preventDefault();
			axios.get('rest/buildings/query', {
				params: {
					name: this.query.name,
					type: this.query.type,
					loc: this.query.location,
					avg: this.query.averageRating
				}
			}).then(response => (this.buildings = response.data));
			this.visible = true;
		}
	}
});