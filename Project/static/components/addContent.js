Vue.component("add-content", {
	data: function() {
		return {
			manager: null,
			building: null,
			trainings: null,
			training: {
				name: null,
				trainingType: null,
				sportsBuilding: null,
				duration: null,
				coach: null,
				description: null,
				picturePath: null,
				price: null
			},
			trainers: null,
			visitors: null,
			
			LoggedUserDTO: { username: null, role: null },
			
			noUsers: false,
			unemployed: false,
			showTable: true,
			showEdit: false,
			showAdd: false
		}
	},
	
	template:`
	<div>
	<div class="encapsulate">
		<h2>Sadrzaj Vaseg objekta</h2>
		
		<h3 v-if="unemployed">Trenutno ne vodite ni jedan objekat</h3><br>

		<div v-if="!unemployed">
		<table class="table table-bordered" v-if="showTable" >
			<tr>
				<th>Ime</th>
				<th>Tip</th>
				<th>Trajanje (min)</th>
				<th>Trener</th>
				<th>Cena (din)</th>
				<th>Opis</th>
			</tr>
			<tr v-for="(t, idx) in trainings" v-on:click="editForm(idx)" :key="training.name+training.trainingType+training.duration+training.description+training.picturePath+training.price">
				<td>{{t.name}}</td>
				<td>{{t.trainingType}}</td>
				<td>{{t.duration}}</td>
				
				<td v-if="t.trainingType === 'Personal' || t.trainingType === 'Group'">{{t.coach.name}} {{t.coach.lastname}}</td>
				<td v-else>Nema</td>
				
				<td>{{t.price}}</td>
				<td>{{t.description}}</td>
			</tr>
		</table>
		<h3 v-if="!showTable">Vas objekat trenutno nema sadrzaj</h3><br>
		
		<br><button class="btn btn-primary mb-2 managerButton" v-on:click="addForm">Forma dodavanja sadrzaja</button><br>
		
		<br><form v-if="showEdit">
			<div class="form-group">
			<input class="form-control" type="text" v-model="training.name" name="name" required />
			</div>
			
			<div class="form-group">
			<select class="form-control" name="type" v-model="training.trainingType" required>
				<option value="" disabled selected>Tip sadrzaja...</option>
				<option value="Personal">Personalni trening</option>
				<option value="Group">Grupni trening</option>
				<option value="Sauna">Sauna</option>
				<option value="Gym">Teretana</option>
				<option value="Massage">Masaza</option>
			</select>
			</div>
			
			<div class="form-group">
			<select class="form-control" v-model="training.coach" v-if="training.trainingType === 'Personal' || training.trainingType === 'Group'">
				<option value="" disabled selected>Odaberi Trenera</option>
				<option v-for="t in trainers" :value="t">{{t.name}} {{t.lastname}}</option>
			</select>
			</div>

			<div class="form-group">
			<input class="form-control" type="text" v-model="training.duration" name="duration" required />
			</div>

			<div class="form-group">
			<input class="form-control" type="text" v-model="training.price" name="price" required />
			</div>

			<div class="form-group">
			<textarea class="form-control" v-model="training.description" name="desc" />
			</div>

			<input class="btn btn-primary mb-2 commentButton" type="submit" value="Izmeni sadrzaj" v-on:click="editContent" />
		</form>
		
		<br><form v-if="showAdd">
			<div class="form-group">
			<input class="form-control" type="text" v-model="training.name" name="name" placeholder="Ime sadrzaja" required />
			</div>
			
			<div class="form-group">
			<select class="form-control" name="type" v-model="training.trainingType" required>
				<option value="" disabled selected>Tip sadrzaja...</option>
				<option value="Personal">Personalni trening</option>
				<option value="Group">Grupni trening</option>
				<option value="Sauna">Sauna</option>
				<option value="Gym">Teretana</option>
				<option value="Massage">Masaza</option>
			</select>
			</div>

			<div class="form-group">
			<select class="form-control" v-model="training.coach" v-if="training.trainingType === 'Personal' || training.trainingType === 'Group'">
				<option value="" disabled selected>Odaberi Trenera</option>
				<option v-for="t in trainers" :value="t">{{t.name}} {{t.lastname}}</option>
			</select>
			</div>

			<div class="form-group">
			<input class="form-control" type="text" v-model="training.duration" name="duration" placeholder="Trajanje" required />
			</div>

			<div class="form-group">
			<input class="form-control" type="text" v-model="training.price" name="price" placeholder="Cena" required />
			</div>

			<div class="form-group">
			<input class="form-control" type="text" v-model="training.picturePath" name="logo" placeholder="Putanja do logoa" required />
			</div>

			<div class="form-group">
			<textarea class="form-control" v-model="training.description" name="desc" placeholder="Opis" />
			</div>

			<input class="btn btn-primary mb-2 commentButton" type="submit" value="Dodaj sadrzaj" v-on:click="addContent" />
		</form>
		
		<table v-if="!noUsers" class="table table-bordered">
			<tr>
				<th>Ime</th>
				<th>Prezime</th>
				<th>Korisnicko ime</th>
			</tr>

			<tr v-for="v in visitors">
				<td>{{v.name}}</td>
				<td>{{v.lastname}}</td>
				<td>{{v.username}}</td>
			</tr>
		</table>
		<h3 v-if="noUsers">Objekat jos nije imao posetilaca</h3>

		</div>
	</div>
	</div>
	`,
	
	mounted() {
		this.jwt = window.localStorage.getItem("JWT");
		let config = {
			headers: {
				Authorization: "Bearer " + this.jwt
			}
		}

		axios.get('rest/users/logged', config)
		.then(response => {
			this.LoggedUserDTO = response.data

			axios.get("rest/users/wholeUser/" + this.LoggedUserDTO.username)
			.then(response => {
				this.manager = response.data

				axios.get("rest/users/trainers")
				.then(response => {
					this.trainers = response.data;

					if (this.manager.sportsBuilding == null) {
						this.unemployed = true;
						this.building = null
						this.showTable = false;
					}
					else {
						axios.get("rest/buildings/" + this.manager.sportsBuilding.name)
						.then(response => {
							this.building = response.data;
							
							axios.get("rest/users/" + this.building.name)
							.then(response => {
								this.visitors = response.data

								if(this.visitors == null){
									this.noUsers = true;
								}
							})

							if(this.building.contentName.length != 0){
								axios.post("rest/buildings/getTrainings", this.building.contentName)
								.then(response => {
									this.trainings = response.data;
								})
							}
							else {
								this.showTable = false;
							}
						})
					}
				})
			})
		})
	},
	
	methods: {
		editForm: function(idx){
			this.showEdit = !this.showEdit;
			this.training = this.trainings[idx];
		},
		
		addForm: function(){
			this.showAdd = !this.showAdd;
		},
		
		addContent: function(){
			this.training.sportsBuilding = this.building;
			axios.post("rest/buildings/addContent/add", this.training);
		},
		
		editContent: function(){
			this.training.sportsBuilding = this.building;
			axios.post("rest/buildings/addContent/edit", this.training);
		}
	}
	
});