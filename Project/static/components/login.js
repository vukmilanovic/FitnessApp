Vue.component("login", {
    data: function() {
        return {
            username : '',
            password : '',
            loginDTO : { username: '', password: '' },
            jwtDTO : { token: '' },
            feeExpiredDTO: { token : '', comment : '' } 
        }
    },
    template: `

    <div id="page" class="form">
        <h2 class="title">Log in page</h2>
        <form class="form">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" class="form-control field" v-model = "username" id="username" placeholder="Enter username" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control field" v-model = "password" id="password" placeholder="Password" required>
            </div>
            <div>
                <input type="button" class="btn btn-primary button" v-on:click = "login" value="Log in"></input>
            </div>
        </form>
    </div>

    ` ,
    methods : {
        login : function () {
            if(this.username === '' || this.password === '') {
                alert("Niste kompletno uneli podatke za prijavu");
                return;
            }

            this.loginDTO.username = this.username;
            this.loginDTO.password = this.password;

            axios
            .post('rest/users/check', this.loginDTO)
            .then(response => {
                
                this.feeExpiredDTO = response.data;
            
                if(this.feeExpiredDTO.token === ""){
                    alert("Uneti podaci nisu ispravni")
                    return;
                } else {
                    window.localStorage.setItem("JWT", this.feeExpiredDTO.token);
                    this.$router.push('/homepage');
                }
            });  
            
        }
    }
})