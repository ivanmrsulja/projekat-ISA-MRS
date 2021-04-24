Vue.component("lista-rezervacija", {
	data: function () {
		    return {
				rezervacije : [],
				selected: {}
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Moje rezervacije</h1>
		<br/>
		
		<h2 v-bind:hidden="rezervacije.length > 0" >Nemate aktuelne rezervacije.</h2>
		
		<table class="table table-hover" v-bind:hidden="rezervacije.length == 0" >
            <thead>
            	<tr>
                <th scope="col">Id</th>
                <th scope="col">Status</th>
                <th scope="col">Datum preuzimanja</th>
               	<th scope="col">Preparat</th>
               	<th scope="col">Apoteka</th>
               	<th scope="col">Cena</th>
				<th scope="col">Otkazivanje</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="r in rezervacije">
                                <th scope="row">{{r.id}}</th>
                                <td>{{r.status}}</td>
                                <td>{{r.datumPreuzimanja}}</td>
                                <td>{{r.preparat}}</td>
                                <td>{{r.apoteka}}</td>
                                <td>{{r.cena}}</td>
								<td><input type="button" class="button1" value="Otkazi" v-on:click="otkazi(r)" v-bind:hidden=" (new Date(r.datumPreuzimanja)).getTime() <= Date.now() + 86400000" /><h3 style="color: lightgray" v-bind:hidden="(new Date(r.datumPreuzimanja)).getTime() >= Date.now() + 86400000">ISTEKLO</h3></td>
            					<td><input type="button" class="button1" value="Detaljnije" v-on:click="detaljnije(r)" data-toggle="modal" data-target="#exampleModalCenter" v-bind:hidden="r.status=='REZERVISANO'"/><h3 style="color: lightgray" v-bind:hidden="r.status=='PODIGNUTO'">NIJE PODIGNUTO</h3></td>
            	</tr>           
            </tbody>
     	</table>
		
		<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLongTitle">Rezervacija broj {{selected.id}}</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <table>
		        <tr>
		        <td>Apoteka:</td>
		        <td>{{selected.apoteka}}</td>
		        </tr>
		        <tr>
		        <td>Preparat:</td>
		        <td>{{selected.preparat}}</td>
		        </tr>
		        </table>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Nazad</button>
		      </div>
		    </div>
		  </div>
		</div>
		
</div>		  
`
	,
	methods:{
		otkazi: function(r){
			axios
	        .patch("/api/preparat/otkazi/" + r.id)
	        .then(response => {
	            if(response.data != "OK"){
	            	alert(response.data);
	            }
	            axios.get("/api/users/currentUser").then(data =>{
		            axios
					.get("api/users/rezervacije/" + data.data.id)
					.then(response => {
						this.rezervacije = response.data;
					});
		        });
	        });
		},
		detaljnije: function(r){
			this.selected = r;
		}
	},
	mounted: function() {
		axios.get("/api/users/currentUser").then(data =>{
            if(data.data){
            	axios
				.get("api/users/rezervacije/" + data.data.id)
				.then(response => {
					this.rezervacije = response.data;
				});
            }else{
            	this.$router.push({ path: "/" });
            }
        });
    }
});