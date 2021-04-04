Vue.component("profil-preparati", {
	data: function () {
		    return {
				preparati : [],
				selectedPreparat:{}
		    }
	},
	methods: {	
    	pregledajBtn : function(el) {
    		this.selectedPreparat = el;
    	},   
    	pregledajPreparat : function(r){
			window.location.href = "#/preparati/" + r.id;
		}
    },
    filters: {
    	dateFormat: function (value, format) {
    		var parsed = moment(value);
    		return parsed.format(format);
    	}
   	},
	
	template: ` 

	
<div align = center style="width:75%">
	<table class="table table-hover">
	 <thead>
		<tr bgcolor="lightgrey">
			<th>Id</th>
			<th>Naziv</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr v-for="preparat in preparati""	>
                <td>{{preparat.id}}</td>
                <td>{{preparat.naziv}}</td> 
                <td><input type="button" value="Pregledaj" v-on:click="pregledajPreparat(preparat)"/></td>                               
		</tr>
	</tbody>
	</table>
	
</div>		  
`
	,
	mounted: function() {
		axios
			.get("api/preparat")
			.then(response => {
				this.preparati = response.data
			});
    }
});