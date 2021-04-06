Vue.component("profil-pacijenti", {
	data: function () {
		    return {
				pacijenti : [],
				selectedPacijent:{}
		    }
	},
	methods: {	
    	pregledajBtn : function(el) {
    		this.selectedPacijent = el;
    	},   
    	pregledajPacijent : function(r){
    		this.$router.push({ path: "/pacijenti/"+r.korisnik.id });
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
			<th>Ime</th>
			<th>Prezime</th>
			<th>Username</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr v-for="pacijent in pacijenti""	>
                <td>{{pacijent.korisnik.id}}</td>
                <td>{{pacijent.korisnik.ime}}</td>
                <td>{{pacijent.korisnik.prezime}}</td> 
                <td>{{pacijent.korisnik.username}}</td>
                <td><input type="button" value="Pregledaj" v-on:click="pregledajPacijent(pacijent)"/></td>                               
		</tr>
	</tbody>
	</table>
	
</div>		  
`
	,
	mounted: function() {		
		axios
		.get("api/users/currentUser")
		.then(response => {
			axios
			.get("api/pacijenti/pregledi/"+response.data.id)
			.then(response => {
				this.pacijenti = response.data
			});
		});
    }
});