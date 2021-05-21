Vue.component("lista-zalbiadmin", {
	data: function () {
		    return {
				zalbe : [],
				user: {},
				indZalba: {},
				modalShow: false
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Moje ponude</h1>
		<br/>
	</br>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Naziv korisnika</th>
                <th scope="col">Tekst zalbe (ukratko)</th>
                <th scope="col">Naziv admina</th>
                <th scope="col">Detaljnije</th>

                </tr>
           	</thead>
            <tbody>
                <tr v-for="z in zalbe" v-bind:key="z.id">
                                <td>{{z.nazivKorisnika}}</td>
                                <td>{{z.tekst.substring(0,35)}}...</td>
                                <td>{{z.nazivAdmina}}</td>
                                <td><a :href="'#/jednaZalbaAdmin/'+z.id" class="btn btn-primary">Detaljnije</a></td>
            	</tr>           
            </tbody>
     	</table>
     	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <p>{{indZalba.tekst}}</p>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary">Save changes</button>
		      </div>
		    </div>
		  </div>
		</div>

	
	
</div>		  
`
	,
	methods: {	
    	pregledajBtn : function(event) {
    		let sifra = event.target.id;
    		var i;
			for (i = 0; i < this.zalbe.length; i++) {
			  if(this.zalbe[i].id == sifra) {
			  	this.indZalba.id = this.zalbe[i].id;
			  	this.indZalba.tekst = this.zalbe[i].tekst;
			  	console.log("BOOYA");
			  }
			}
			console.log(this.indZalba);
    	}
    },
	mounted: function() {
		var self = this;
        axios
		.get("/api/users/currentUser")
		.then(function(resp){
			self.user = resp.data;
			axios
			.get("/api/admin/Zalbe/"+self.user.id)
			.then(function(re){
				self.zalbe = re.data;
			});
		});
		console.log(this.user);
	  	
    }
});