Vue.component("paying", {
    data: function() {
        return {
            FeeDTO : { type : null, price : null, customerUsername : '', numberOfEntries : null, totalAppereances : null, code : '' },
            date : null,
            monthValidityDate : null,
            yearValidityDate : null,
            LoggedUserDTO : { username : '', role : null },
            answer: null
        }
    }, 
    template: 
    `
    <div class="form"> 
        <div style="margin-left: 3cm;"> 
            <div> 
                <div class="title" style="margin-left: 0.6cm;"> 
                    <h2 >Membership fee payment</h2>
                    <p style="margin-top: 0.6cm;">Buy a fitpass membership fee</p>
                </div> 
                <div>
                    <div class="profileSettingsFields inLine">
                        <label class="labels" for="type">Membership type</label>
                        <select class="form-control field comboBox" id="type" v-model="FeeDTO.type">
                            <option class="form-control" :value="null" disabled>Select type of fee</option>
                            <option class="form-control" value="Monthly">Monthly</option>
                            <option class="form-control" value="Yearly">Yearly</option>
                        </select>
                    </div>
                    <div class="profileSettingsFields inLine" style="margin-left: 2cm;">
                        <div>
                            <label class="labels input-group-text" for="info">Fee info</label>
                        </div>
                        <p>
                        <button class="btn btn-primary commentButton" type="button" id="info" data-toggle="collapse" data-target="#fee" aria-expanded="false" aria-controls="fee">Membership fee info</button>
                        </p>
                        <div class="collapse" id="fee">
                            <div class="card card-body payForm">
                                <div style="margin-top: 1cm;">
                                    <div class="inLine">
                                        <div v-if="FeeDTO.type == 'Monthly'">Price: 2500</div><br>
                                        <div v-if="FeeDTO.type == 'Monthly'">Payment date: {{date}}</div><br>
                                        <div v-if="FeeDTO.type == 'Monthly'">Validity date: {{monthValidityDate}}</div><br>
                                    </div>
                                    <div class="inLine">
                                        <div v-if="FeeDTO.type == 'Monthly'" style="margin-left: 1cm;">Number of daily entries: 1</div><br>
                                        <div v-if="FeeDTO.type == 'Monthly'" style="margin-left: 1cm;">Total appereances: 30</div>
                                    </div>
                                </div>
                                <div style="margin-top: 1cm;">
                                    <div class="inLine">
                                        <div v-if="FeeDTO.type == 'Yearly'">Price: 30000</div><br>
                                        <div v-if="FeeDTO.type == 'Yearly'">Payment date: {{date}}</div><br>
                                        <div v-if="FeeDTO.type == 'Yearly'">Validity date: {{yearValidityDate}}</div><br>
                                    </div>
                                    <div class="inLine">
                                        <div v-if="FeeDTO.type == 'Yearly'" style="margin-left: 1cm;">Number of daily entries: 2</div><br>
                                        <div v-if="FeeDTO.type == 'Yearly'" style="margin-left: 1cm;">Total appereances: 600</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="profileSettingsFields inLine" style="margin-left: 2cm;">
                        <div>
                            <label class="labels input-group-text" for="code">Additional option</label>
                        </div>
                        <p>
                        <button class="btn btn-primary commentButton" type="button" id="code" data-toggle="collapse" data-target="#promo" aria-expanded="false" aria-controls="promo">Promo code</button>
                        </p>
                        <div class="collapse" id="promo" style="margin-top: 1cm;">
                            <div class="card card-body payForm">
                                <input class="form-control field" type="text" placeholder="Enter your promo code" v-model="FeeDTO.code"></input>
                            </div>
                        </div>
                    </div>  
                    <div style="clear:both;"> 
                        <div>
                        <button class="btn btn-primary profile-button payButton" v-on:click = "buyFee" type="button">Buy</button>
                        </div> 
                    </div> 
                </div>
            </div> 
        </div>
    </div>
    `,
    methods: {
        buyFee : function() {

            if(this.FeeDTO.type == 'Monthly'){
                this.FeeDTO.price = 2500;
                this.FeeDTO.numberOfEntries = 1;
                this.FeeDTO.totalAppereances = 30;
            } else if(this.FeeDTO.type == 'Yearly') {
                this.FeeDTO.price = 30000;
                this.FeeDTO.numberOfEntries = 2;
                this.FeeDTO.totalAppereances = 600;
            } else {
                return;
            }

            axios
            .post('rest/customers/createFee', this.FeeDTO)
            .then(response => {

                this.answer = response.data;

                if(this.answer == 2) {
                    alert("Promo kod nije validan.");
                } else if (this.answer == 1) {
                    alert("Doslo je do greske pri placanju clanarine.");
                } else if (this.answer == 0) {
                    alert("Uspesno ste uplatili clanarinu.");
                    this.$router.push('/homepage')
                }

            })
        }
    },
    mounted() {

        this.jwt = window.localStorage.getItem("JWT");
        let config = {
            headers: {
                Authorization: "Bearer " + this.jwt
            }
        }

        axios.get('rest/users/logged', config).then(response => {

            this.LoggedUserDTO = response.data;

            this.FeeDTO.customerUsername = this.LoggedUserDTO.username;
        })

        var d = new Date();
        this.date = d.toLocaleDateString("en-GB");
        
        var dM = new Date(d.setMonth(d.getMonth() + 1));

        d = new Date();
        var dY = new Date(d.setFullYear(d.getFullYear() + 1));
        this.monthValidityDate = dM.toLocaleDateString("en-GB");
        this.yearValidityDate = dY.toLocaleDateString("en-GB");
    }
});