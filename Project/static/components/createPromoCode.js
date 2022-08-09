Vue.component("promoCode", {
    data: function() {
        return {
            promoCodeDTO : { code : '', startValidityDate : { day : null, month : null, year : null, hours : null, minutes : null }, 
            endValidityDate :  { day : null, month : null, year : null, hours : null, minutes : null }, usageNumber : null, discount : null },
            answer : null,
            dateStart : null,
            dateEnd: null
        }
    },
    template:
    `
    <div class="form"> 
        <div style="margin-left: 3cm;"> 
            <div> 
                <div> 
                    <h2 class="title">Create new promo code</h2>
                </div> 
                <div style="margin-left: 0.5cm;">
                    <div> 
                        <div class="inlineElements profileSettingsFields">
                            <label class="labels" for="code">Code</label>
                            <input type="text" id="code" class="form-control field" v-model = "promoCodeDTO.code" required></input>
                        </div> 
                    </div> 

                    <div>
                        <div class="inlineElements profileSettingsFields"> 
                            <label class="labels" for="start">Start validity date</label>
                            <input type="date" id="start" class="form-control" style="width: 300;" v-model = "dateStart" required></input>
                        </div>   
                        <div class="inlineElements profileSettingsFields"> 
                            <label class="labels" for="end">End validity date</label>
                            <input type="date" id="end" class="form-control" style="width: 300;" v-model = "dateEnd" required></input>
                        </div>
                    </div>
                    <div> 
                        <div class="inlineElements profileSettingsFields">
                            <label class="labels">Usage number</label>
                            <input type="number" class="form-control field" min="0" v-model = "promoCodeDTO.usageNumber" value="1" required></input>
                        </div> 
                        <div class="inlineElements profileSettingsFields">
                            <label class="labels">Discount</label>
                            <input type="number" class="form-control field" value="0" min="0" v-model = "promoCodeDTO.discount" required>
                        </div> 
                    </div>
                    <div > 
                        <div>
                        <button class="btn btn-primary profile-button promoButton" v-on:click = "createPromoCode" type="button">Create</button>
                        </div> 
                    </div> 
                </div>
            </div> 
        </div>
    </div>
    `,
    methods: {
        createPromoCode: function() {

            if(this.dateStart !== null && this.dateEnd !== null){
                const split_dateStart = this.dateStart.split("-");
                this.promoCodeDTO.startValidityDate.year = parseInt(split_dateStart[0]);
                this.promoCodeDTO.startValidityDate.month = parseInt(split_dateStart[1]);
                this.promoCodeDTO.startValidityDate.day = parseInt(split_dateStart[2]);

                const split_dateEnd = this.dateEnd.split("-");
                this.promoCodeDTO.endValidityDate.year = parseInt(split_dateEnd[0]);
                this.promoCodeDTO.endValidityDate.month = parseInt(split_dateEnd[1]);
                this.promoCodeDTO.endValidityDate.day = parseInt(split_dateEnd[2]);
            }

            if(this.promoCodeDTO.code === '') {
                alert("Niste kompletno uneli podatke za kreiranje promo koda");
                return;
            }

            axios
            .post("rest/admin/promo", this.promoCodeDTO)
            .then(response => {

                this.answer = response.data;

                if(this.answer == false) {
                    alert("Doslo je do greske prilikom kreiranja novog promo koda.");
                    return;
                } else if(this.answer == true) {
                    alert("Promo kod je uspesno kreiran.")
                }

            })
        }
    }
})