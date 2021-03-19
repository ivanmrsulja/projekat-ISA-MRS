Vue.component("pocetna-stranas", {
	data: function () {
		    return {
				offers : []
		    }
	},
	template: ` 
<div align = center>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Id</th>
                <th scope="col">Status</th>
                <th scope="col">Ukupna cena</th>
               	<th scope="col">Rok isporuke</th>
             	<th scope="col">Narudzbenica</th>
           		<th scope="col">Dostavljac</th>

                </tr>
           	</thead>
            <tbody>
                <tr v-for="offer in offers" v-bind:key="offer.id">
                                <th scope="row">{{offer.id}}</th>
                                <td>{{offer.status}}</td>
                                <td>{{offer.ukupnaCena}}</td>
                                <td></td>
                                <td></td>
                                <td>{{offer.dobavljac.username}}</td>
            	</tr>           
            </tbody>
     	</table>

	
	
</div>		  
`
	,
	mounted: function() {
		axios
			.get("api/admin")
			.then(response => {
				this.offers = response.data
				
			});
    }
});