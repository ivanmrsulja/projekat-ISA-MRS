Vue.component("radni-kalendar-farmaceut", {
	data: function () {
		    return {
				savetovanja : [],
				korisnik:{}
		    }
	},
	
	template: ` 
<div align = center style="width:75%">
		
		<h1>Radni kalendar</h1>
		<br/>
		<h2 v-bind:hidden="savetovanja.length > 0" >Nemate ni jedan zakazan pregled.</h2>
		<table class="table table-hover" v-bind:hidden="savetovanja.length == 0">
            <thead>
            	<tr>
                <th scope="col">Status</th>
                <th scope="col">Tip</th>
                <th scope="col">Datum</th>
               	<th scope="col">Vrijeme</th>
             	<th scope="col">Trajanje</th>
           		<th scope="col">Cijena</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="p in savetovanja">
                    <td>{{p.status}}</td>
                    <td>{{p.tip}}</td>
                    <td>{{p.datum}}</td>
                    <td>{{p.vrijeme}}</td>
                    <td>{{p.trajanje}}</td>
					<td>{{p.cijena}}</td>
					<td><input type="button" value="Zapocni" v-on:click="zapocniPregled(p)" /></td>
            	</tr>           
            </tbody>
     	</table>


	
</div>		  
`
	,
	methods: {
		zapocniPregled : function(r){
    		this.$router.push({ path: "/pacijenti/zapocniPregled/"+r.id });
		},
	},
	mounted: function() {
		axios.get("/api/users/currentUser").then(response => {
            if(response.data){
            	this.korisnik = response.data;
                axios
				.get("api/farmaceut/savetovanja")
				.then(response => {
					this.savetovanja = response.data;
				});
            }else{
            	this.$router.push({ path: "/" });
            }
        });	
    }
});