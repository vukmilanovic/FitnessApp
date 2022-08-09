Vue.component("comments", {
    data : function() {
        return {
            comments : null,
            commentID : '',
            answer : null,
            commentText : ''
        }
    },
    template: 
    `
        <div class="container tableForm">
            <div>
                <h2>Pending comments</h2><br>
                <p>This is the list of all pending comments:</p>
            </div>
            <div>
                <div class="inLine">
                    <table class="table table-hover tableComment">
                        <thead>
                            <tr>
                                <th>Customer name</th>
                                <th>Sport facility</th>
                                <th>Rating</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-bind:class="{selectedRow : commentID == c.id }"  @click="getCommentID(c.id)" v-for="c in comments">
                                <td>{{c.customer.username}}</td>
                                <td>{{c.sportsBuilding.name}}</td>
                                <td>{{c.rating}}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="form-group inLine textAreaComment">
                    <label for="text">Comment text</label>
                    <textarea class="form-control" placeholder="Choose comment in table" id="text" rows="7" readonly>{{commentText}}</textarea>
                </div>
            </div>
            <br><br>
            <div class="inLine">
                <input type="button" class="btn btn-primary profile-button commentButton" value = "Accept" v-on:click = "acceptComment"></input>
                <input type="button" class="btn btn-primary profile-button commentButton" style="margin-left: 1.5cm;" value = "Reject" v-on:click = "rejectComment"></input>
            </div>
        </div>
    `,
    methods: {
        getCommentID : function(id) {
            this.commentID = id;

            this.comments.forEach(comment => {
                if(this.commentID == comment.id){
                    this.commentText = comment.commentText;
                }
            });
        },
        acceptComment : function() {
            
            if(this.commentID == '') {
                alert('Niste izabrali komentar.')
                return;
            }

            axios
            .put('rest/admin/acceptComment/' + this.commentID)
            .then(response => {

                this.answer = response.data;

                if(this.answer == false) {
                    this.commentID = '';
                    this.commentText = '';
                    alert('Doslo je do greske pri odobravanju komentara.');
                    return;
                } else if (this.answer == true) {

                    axios
                    .get('rest/admin/pendingComments')
                    .then(response => (this.comments = response.data))

                    this.commentID = '';
                    this.commentText = '';
                    confirm('Komentar je uspesno odobren.')
                }

            })

        },
        rejectComment : function() {

            if(this.commentID == '') {
                alert('Niste izabrali komentar.')
                return;
            }

            axios
            .put('rest/admin/rejectComment/' + this.commentID)
            .then(response => {

                this.answer = response.data;

                if(this.answer == false) {
                    this.commentID = '';
                    this.commentText = '';
                    alert('Doslo je do greske pri odbijanju komentara.');
                    return;
                } else if (this.answer == true) {
                    
                    axios
                    .get('rest/admin/pendingComments')
                    .then(response => (this.comments = response.data))

                    this.commentID = '';
                    this.commentText = '';
                    confirm('Komentar je uspesno odbijen.')
                }

            })

        }
    },
    mounted() {
        axios
        .get('rest/admin/pendingComments')
        .then(response => (this.comments = response.data))
    } 

})