Vue.component("pregled-erecepata", {
	data: function () {
		    return {
				recepti : []
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Moji eRecepti</h1>
		<br/>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Id</th>
                <th scope="col">Status</th>
                <th scope="col">Datum izdavanja</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="r in recepti">
                    <th>{{r.id}}</th>
                    <td>{{r.status}}</td>
                    <td>{{r.datumIzdavanja}}</td>
                    <td><input type="button" value="Pregledaj"/></td>
            	</tr>           
            </tbody>
     	</table>

	
	
</div>		  
`
	,
	mounted: function() {
		axios
			.get("api/eRecept/all/" + "1" + "?sort=false&descending=false&status=SVI")
			.then(response => {
				this.recepti = response.data;
			});
    }
});