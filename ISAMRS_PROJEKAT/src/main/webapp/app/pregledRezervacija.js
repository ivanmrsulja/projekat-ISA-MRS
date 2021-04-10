Vue.component("lista-rezervacija", {
	data: function () {
		    return {
				rezervacije : []
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
								<td><input type="button" class="button1" value="Otkazi" v-on:click="otkazi(r)" v-bind:hidden=" (new Date(r.datumPreuzimanja)).getTime() <= Date.now() + 86400000" /><h2 style="color: lightgray" v-bind:hidden="(new Date(r.datumPreuzimanja)).getTime() >= Date.now() + 86400000">ISTEKLO</h2></td>
            	</tr>           
            </tbody>
     	</table>
	
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