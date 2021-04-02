Vue.component("pojedinacni-preparat", {
	data: function () {
		    return {
				spec : {}
		    }
	},
	template: ` 
<div align = center style="width:50%">
		
		<h1>Specifikacija preparata id: {{$route.params.spec}}</h1>
		<br/>
		<table class="table table-hover">
            <thead>
            	<th>
                <tr scope="col">Id:\t{{spec.id}}</tr>
                <tr scope="col">Naziv:\t{{spec.naziv}}</tr>
                <tr scope="col">Kontraindikacije:\t{{spec.kontraindikacije}}</tr>
                <tr scope="col">Sastav:\t{{spec.sastav}}</tr>
                <tr scope="col">Preporuceni unos:\t{{spec.preporuceniUnos}}</tr>
                <tr scope="col">Oblik:\t{{spec.oblik}}</tr>
                <tr scope="col">Proizvodjac:\t{{spec.proizvodjac}}</tr>
                <tr scope="col">Rezim:\t{{spec.rezim}}</tr>
                <tr scope="col">Ocena:\t{{spec.ocena}}</tr>
                </th>
           	</thead>
     	</table>

	
	
</div>		  
`
	,
	mounted: function() {
		axios
			.get("api/preparat/spec/" + this.$route.params.spec)
			.then(response => {
				this.spec = response.data;
			});
    }
});