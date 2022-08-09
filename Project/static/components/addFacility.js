Vue.component("addFacility", {
	data: function() {
		return {
			buildingDTO: {
				name: null,
				type: null,
				geoWidth: null,
				geoLen: null,
				street: null,
				number: null,
				city: null,
				postalCode: null,
				logoPath: null,
				startHours: null,
				endHours: null
			},
			building: null,
			
			date: null,
			managers: null,
			existingManager: null,			
			newManager: {
				username: null,
				password: null,
				name: null,
				lastname: null,
				gender: null,
				dob: {day: null, month: null, year: null, hours: 0, minutes: 0},
				building: null
			},
			
			map: null,
			
			show: false
		}
	},
	
	template:
	`
	<div>
		<div class="encapsulate">
		
		<div class="basic_info">
			<h2>Dodavanje novog objekta</h2>
			<form>
				<div class="form-group">
				<input type="text" class="form-control" v-model="buildingDTO.name" name="name" placeholder="Ime objekta" size="33" required />
				</div>

				<div class="form-group">
				<select class="form-control" name="type" v-model="buildingDTO.type" required>
					<option value="" disabled selected>Tip objekta...</option>
					<option value="Gym">Teretana</option>
					<option value="Pool">Bazen</option>
					<option value="Sports Center">Sportski centar</option>
					<option value="Dance Studio">Plesni studio</option>
				</select>
				</div>

				<div class="form-group">
				<input type="text" class="form-control" v-model="buildingDTO.street" name="street" placeholder="Ulica" size="33" required/>
				</div>

				<div class="form-group">
				<input type="text" class="form-control" v-model="buildingDTO.number" name="number" placeholder="Broj" size="33" required/>
				</div>

				<div class="form-group">
				<input type="text" class="form-control" v-model="buildingDTO.city" name="city" placeholder="Grad" size="33" required/>
				</div>

				<div class="form-group">
				<input type="text" class="form-control" v-model="buildingDTO.postalCode" name="street" placeholder="Postanski broj" size="33" required/>
				</div>

				<div class="form-group">
				<input type="text" class="form-control" v-model="buildingDTO.logoPath" name="logoPath" placeholder="Putanja do logoa" size="33" required/>
				</div>

				<div class="form-group">
					<form class="form-inline">
						<label class="form-label">Radno vreme od</label>
						<input type="text" class="form-control mb-2 mr-sm-2" size="2" v-model="buildingDTO.startHours" name="startHours" required/>

						<label class="form-label">h do</label>

						<input type="text" class="form-control mb-2 mr-sm-2" size="2" v-model="buildingDTO.endHours" name="endHours" required/>
						<label class="form-label">h</label>
					</form>
				</div>
			</form>
			
			<div class="map_stuff">			
				<div id="map" class="map"></div>
			</div>
			
			<div class="clear"></div><br/>
			
			<div class="manager_stuff form-group">
				<div class="manager_droplist">
					<label>Dostupni menadzeri</label>
					<select class="form-control" name="manager" v-model="existingManager" >
						<option value="" disabled selected>Dostupni menadzeri...</option>
						<option v-for="m in managers" :value="m">{{m.name}} {{m.lastname}}</option>
					</select>
				</div><br>
				
				<div class="manager_registration">
				
					<button class="btn btn-primary mb-2 commentButton" v-on:click="showForm">Registruj menadzera</button>
					<div class="clear"></div><br/>
					<div v-if="show">
						<form>
				            <div class="form-group">
				                <input class="form-control" type="text" v-model="newManager.name" id="name" placeholder="Ime" required>
				            </div>
				            <div class="form-group">
				                <input class="form-control" type="text" v-model="newManager.lastname" id="lastname" placeholder="Prezime" required>
				            </div>
				            <div class="form-group">
				                <input class="form-control" type="text" v-model = "newManager.username" id="username" placeholder="Username" required>
				            </div>
				            <div class="form-group">
				                <input class="form-control" type="password" v-model = "newManager.password" id="password" placeholder="Password" required>
				            </div>
				
				            <div>
				                <div>
				                    <div class="form-check">
				                        <input class="form-check-input" type="radio" name="radios" v-model="newManager.gender" value="Male" id="male" checked></input>
				                        <label class="form-check-label radioField" for="male">Male</label>
				                        <input class="form-check-input" type="radio" name="radios" v-model = "newManager.gender" value="Female" id="female"></input>
				                        <label class="form-check-label" for="female">Female</label>  
				                    </div>
				                </div>
				            </div>
				
				            <div>
				                <label for="date">Date of birth:</label>
				                <input class="form-control" type="date" v-model = "date" id="date" required></input>
				            </div>
				        </form>
					</div>
					
				</div>
				
				<button class="btn btn-primary mb-2 commentButton" v-on:click ="addBuilding">Dodaj</button>
			</div>
			
		</div>
		
		</div>
	</div>
	`,
	
	mounted(){
		axios.get('rest/users/managers').then(response => (this.managers = response.data))
		console.log(!(this.managers == null))
		
		this.map = new ol.Map({
			target: 'map',
			layers: [
				new ol.layer.Tile({
					source: new ol.source.OSM()
				})
			],
			view: new ol.View({
				center: ol.proj.fromLonLat([19.842273632527927, 45.25422889334797]),
				zoom: 13
			})
		});
		
		// this => arrow is 1.5h of wanting to die
		this.map.on('singleclick', (evt) => {
			// convert coordinate to EPSG-4326 (returns a list, 0 is len 1 is width)
			coords = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326')
			
			this.buildingDTO.geoLen = coords[0]
			this.buildingDTO.geoWidth = coords[1]
			
			alert("Koordinate izabrane");
		});	
	},
	
	methods: {
		showForm: function(){
			this.show = !this.show
		},
		
		addBuilding: function(){
			console.log(this.buildingDTO)
			axios.post('rest/buildings/add', this.buildingDTO)
			.then(response => {
				this.building = response.data
				
				if (this.existingManager == null) {
					let ymd = this.date.split("-")
					this.newManager.dob.year = parseInt(ymd[0])
					this.newManager.dob.month = parseInt(ymd[1])
					this.newManager.dob.day = parseInt(ymd[2])
					this.newManager.building = this.building.name

					axios.post('rest/users/add/manager', this.newManager)
					.then(response => {
						this.$router.push('/homepage')
					})
				}
				else {
					this.existingManager.sportsBuilding = this.building;
				
					axios.put('rest/users/edit/' + this.existingManager.username, this.existingManager)
					.then(response => {
						this.$router.push('/homepage')
					})
				}	
			})
			
			
			
		}
		
	}
});