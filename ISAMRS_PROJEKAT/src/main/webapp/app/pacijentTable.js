Vue.component("profil-pacijenti", {
	data: function () {
		    return {
				pacijenti : [],
				selectedPacijent:{},
				searchParam: ""
		    }
	},
	methods: {	
    	pregledajBtn : function(el) {
    		this.selectedPacijent = el;
    	},   
    	pregledajPacijent : function(r){
    		this.$router.push({ path: "/pacijenti/"+r.korisnik.id });
		},
		searchSort: function(){
			axios
			.get("api/users/currentUser")
			.then(response => {
				axios
				.get("api/pacijenti/pregledi/"+response.data.id + "?search=" + this.searchParam + "&criteria=none")
				.then(response => {
					this.pacijenti = response.data
				});
			});
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

	
	<div class="container">
    <br/>
	<div class="row justify-content-center">
        <div class="col-12 col-md-10 col-lg-8">
            <form class="card card-sm">
                <div class="card-body row no-gutters align-items-center">
                    <div class="col-auto">
                        <i class="fas fa-search h4 text-body"></i>
                    </div>
                    <!--end of col-->
                    <div class="col">
                        <input class="form-control form-control-lg form-control-borderless" type="search" placeholder="Pretraga" v-model="searchParam">
                    </div>
                    <!--end of col-->
                    <div class="col-auto">
                        <button class="btn btn-lg button1" type="submit" v-on:click="searchSort()">Search</button>
                    </div>
                    <!--end of col-->
                </div>
            </form>
        </div>
        <!--end of col-->
    </div>
	</div>


	<table class="table table-hover">
	 <thead>
		<tr bgcolor="#455a64">
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
                <td><input type="button" class="button1" value="Pregledaj" v-on:click="pregledajPacijent(pacijent)"/></td>                               
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
			.get("api/pacijenti/pregledi/"+response.data.id + "?search= " + "&criteria=none")
			.then(response => {
				this.pacijenti = response.data
			});
		});
    }
});