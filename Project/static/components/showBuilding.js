Vue.component("show-building", {
	data: function() {
		return {
			name: "",
			map: "",
			Comments : null,
			customerComments : null,
			commentDTO : { customerUsername : '', commentText : '', rating : null, facilityName : '' },
			customerLogged: null,
			LoggedUserDTO : { username : '', role : null },
			answer: null,
			building: {
				name: "",
				type: "",
				contentName: [],
				location: {
					geoWidth: 0.,
					geoLen: 0.,
					address: { street: "", number: "", city: "", postalCode: "", deleted: false }
				},
				logoPath: "",
				averageRating: 0.,
				startWorkingTime: {
					day: 0,
					month: 0,
					year: 0,
					hours: 0,
					minutes: 0
				},
				endWorkingTime: {
					day: 0,
					month: 0,
					year: 0,
					hours: 0,
					minutes: 0
				},
				deleted: false
			},
			// logic to get comments for specific facility
			comments: [],
			trainings: null
		}
	},
	template: `
    <div>
		<div>
			<div class="inLine spInfo">
				<div>
					<fieldset>
						<legend>Facility info:</legend>
						<div class="card" style="border: 3px solid #ddd">
							<img :src="building.logoPath" v-bind:alt="building.name" class="card-img-top" style="width: 100%" />
							<div class="clear"></div><br/>

							<div class="card-body" style="text-align: center">
								<h2 class="card-title">{{parseType(building.type)}} {{building.name}}</h2>
								<h5>Objekat trenutno <b>{{isOpen()}}</b></h5>
								<h5>{{building.location.address.street}} {{building.location.address.number}}, {{building.location.address.postalCode}} {{building.location.address.city}}</h5>
								<div name="extra_info">
									<p>Prosecna ocena objekta <b>{{building.averageRating}}</b></p>
									<p>Radno vreme <b>PON-SUB od {{building.startWorkingTime.hours}}h do {{building.endWorkingTime.hours}}h</b></p>
								</div>
								<div class="clear"></div><br/>

								<table class="table table-striped">
									<tr>
										<th colspan="6" style="text-align: center">Sadrzaj objekta</th>
									</tr>
									<tr>
										<td>Ime</td>
										<td>Tip</td>
										<td>Trajanje</td>
										<td>Trener</td>
										<td>Cena</td>
										<td>Opis</td>
									</tr>
									<tr v-for="(t, idx) in trainings" >
										<td>{{t.name}}</td>
										<td>{{t.trainingType}}</td>
										<td>{{t.duration}}</td>
										<td>{{t.coach.name}} + " " + {{t.coach.name}}</td>
										<td>{{t.price}}</td>
										<td>{{t.description}}</td>
									</tr>
								</table>

							</div>
						</div>
					</fieldset>

					<div v-if="customerLogged == true" class="form-group">
						<textarea class="form-control textAreaSP"  placeholder="Comment sports facility" id="text" rows="7" v-model="commentDTO.commentText"></textarea>
						<div style="margin-top: 0.5cm;">
							<label class="labels">Discount</label>
							<input type="number" class="form-control field" value="1" min="1" v-model="commentDTO.rating" max="5" required>
						</div>
						<input class="btn btn-primary profile-button commentButton" type="button" style="margin-top: 0.5cm;" value="Comment and rate" v-on:click = "commentFacility"></input>			
					</div>
				</div>		
			</div>
			<div class="inLine map">
				<button v-on:click="centerMap(); addPin();">Pokazi na mapi</button><div id="map"></div>
				<div id="popup" class="ol-popup">
					<a href="#" id="popup-closer" class="ol-popup-closer"></a>
					<div id="popup-content"></div>
				</div>
			</div><br/>
			<div class="clear"></div><br/>
      
      	
		
    </div>
    
      <div class="clear"></div><br/>
			<div class="list-group commentDiv">
				<div>
					<li v-if="customerLogged == true" class="list-group-item list-group-item-action flex-column align-items-start comment " v-for="c in customerComments">
						<div class="d-flex w-100 justify-content-between">
							<h5 class="mb-1">Rating: {{c.rating}}</h5>
						</div>
						<p class="mb-1">{{c.commentText}}</p>
						<small>{{c.customer.username}}</small>
					</li>
					<li v-if="customerLogged == false && LoggedUserDTO.role != null" class="list-group-item list-group-item-action flex-column align-items-start comment " v-for="c in comments">
						<div class="d-flex w-100 justify-content-between">
							<h5 class="mb-1">Rating: {{c.rating}}</h5>
						</div>
						<p class="mb-1">{{c.commentText}}</p>
						<small>Customer: {{c.customer.username}}</small>
						<small>Status: {{c.status}}</small>
					</li>
				</div>
			</div>
		</div>
    </div>
    `,

	mounted() {
		this.customerLogged = false;
		this.name = this.$route.params.name;
		axios.get('rest/buildings/' + this.name)
			.then(response => (this.building = response.data));

		axios.post("rest/buildings/getTrainings", this.building.contentName)
			.then(response => ( this.trainings = response.data));

		this.map = new ol.Map({
			target: 'map',
			layers: [
				new ol.layer.Tile({
					source: new ol.source.OSM()
				})
			],
			view: new ol.View({
				center: ol.proj.fromLonLat([0, 0]),
				zoom: 1
			})
		});

		this.jwt = window.localStorage.getItem("JWT");
        let config = {
          headers: {
            Authorization: "Bearer " + this.jwt
          }
        }

		axios.get('rest/users/logged', config).then(response => {
            this.LoggedUserDTO = response.data;
          
            if(this.LoggedUserDTO.role == 'Customer') {
				this.customerLogged = true;
			}
			
			axios
			.get('rest/customers/acceptedComments/' + this.name)
			.then(response => {
				this.customerComments = response.data;
			})

			axios
			.get('rest/admin/checkedComments/' + this.name)
			.then(response => {
				this.comments = response.data;
			})
        })
	},

	methods: {
		commentFacility: function() {

			if(this.LoggedUserDTO.role != 'Customer') {
				alert("Greska pri dodavanju komentara.");
			}

			this.commentDTO.customerUsername = this.LoggedUserDTO.username;
			this.commentDTO.facilityName = this.name;

			axios
			.post('rest/customers/comment', this.commentDTO)
			.then(response => {

				this.answer = response.data;

				if(this.answer == false) {
					alert("Doslo je do greske pri dodavanju komentara.");
					return;
				} else if(this.answer == true) {
					alert("Uspesno ste poslali komentar.")
				}

			})
		},
		addPin: function() {
			var layer = new ol.layer.Vector({
				source: new ol.source.Vector({
					features: [
						new ol.Feature({
							geometry: new ol.geom.Point(ol.proj.fromLonLat([this.building.location.geoLen, this.building.location.geoWidth]))
						})
					]
				})
			});
			this.map.addLayer(layer);
		},

		centerMap: function() {
			this.map.getView().setZoom(17)
			this.map.getView().setCenter(ol.proj.fromLonLat([this.building.location.geoLen, this.building.location.geoWidth]))
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
		},
		
		isOpen: function() {
			const d = new Date();
			let day = d.getDay();
			if ((d.getHours() >= this.building.startWorkingTime.hours) && (d.getHours() <= this.building.endWorkingTime.hours) && (day != 0)) {
				return "otvoren";
			}
			return "zatvoren";
		}
	}

});