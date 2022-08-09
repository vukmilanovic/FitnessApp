Vue.component("homepage", {
    data: function() {
        return {
            username: ''
        }
    },
    template: `

    <div>
    <h2 class="title">Registration page</h2>
        <div>
            <input type = "button" class="btn btn-primary button" v-on:click = "logout" value = "Log out"></input>
        </div>
    </div>

    ` ,
    methods : {
        logout : function () {
            this.$router.push('/');
        }
    },
    mounted () {
        
    }
})