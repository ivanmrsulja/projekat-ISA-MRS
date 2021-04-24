Vue.component("akcije-promocije", {
	data: function () {
		    return {
				akcijePromocije : []
		    }
	},
	template: ` 
<div align = center style="width:60%">
		
		<h1>Moje pretplate</h1>
		<br/>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Naziv apoteke</th>
                <th scope="col">Adresa</th>
                <th scope="col">Ocena</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="a in akcijePromocije" v-bind:key="a.id">
                    <td>{{a.naziv}}</td>
                    <td>{{a.lokacija.ulica}}</td>
                    <td v-if="a.ocena != 0">{{a.ocena.toFixed(2)}}</td>
                    <td v-else ><h3 style="color: lightgray">Nije ocenjeno</h3></td>
                    <td><input type="button" value="Otkazi pretplatu"></td>
            	</tr>           
            </tbody>
     	</table>

	
	
</div>		  
`
	,
	mounted: function() {
		axios.get("/api/users/currentUser").then( response => {
            if(response.data){
                axios
				.get("api/users/akcije/" + response.data.id)
				.then(response => {
					this.akcijePromocije = response.data
				});
            }else{
            	this.$router.push({ path: "/" });
            }
        });
    }
    
});