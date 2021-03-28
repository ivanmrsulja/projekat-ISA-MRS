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
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Id</th>
                <th scope="col">Status</th>
                <th scope="col">Datum preuzimanja</th>
               	<th scope="col">Preparat</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="r in rezervacije">
                                <th scope="row">{{r.id}}</th>
                                <td>{{r.status}}</td>
                                <td>{{r.datumPreuzimanja}}</td>
                                <td>{{r.preparat}}</td>
								<td><input type="button" value="Otkazi" v-bind:hidden=" (new Date(r.datumPreuzimanja)).getTime() <= Date.now()" /><h2 style="color: lightgray" v-bind:hidden="(new Date(r.datumPreuzimanja)).getTime() >= Date.now()">ISTEKLO</h2></td>
            	</tr>           
            </tbody>
     	</table>
	
</div>		  
`
	,
	mounted: function() {
		axios
			.get("api/users/rezervacije/" + "1")
			.then(response => {
				this.rezervacije = response.data;
			});
    }
});