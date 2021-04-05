Vue.component("istorija-pregleda", {
	data: function () {
		    return {
				pregledi : [],
				numPages: 1,
				korisnik: {}
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Istorija pregleda</h1>
		<br/>
		
		<h2 v-bind:hidden="pregledi.length > 0" >Nemate ni jedan obavljen pregled/savetovanje.</h2>
		
		<div class="dropdown" v-bind:hidden="pregledi.length == 0" >
		  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Sortiraj po:
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		    <a class="dropdown-item" :href="'#/istorijaPregleda/'+$route.params.page+'/CENA'" v-on:click="loadNext($route.params.page, 1)">CENA</a>
		    <a class="dropdown-item" :href="'#/istorijaPregleda/'+$route.params.page+'/DATUM'" v-on:click="loadNext($route.params.page, 2)">DATUM</a>
		    <a class="dropdown-item" :href="'#/istorijaPregleda/'+$route.params.page+'/TRAJANJE'" v-on:click="loadNext($route.params.page, 3)">TRAJANJE</a>
			<a class="dropdown-item" :href="'#/istorijaPregleda/'+$route.params.page+'/PLAIN'" v-on:click="loadNext($route.params.page, 4)">BEZ_SORTIRANJA</a>
		  </div>
		</div>
		
		<br/>
		<table class="table table-hover" v-bind:hidden="pregledi.length == 0" >
            <thead>
            	<tr>
                <th scope="col">Status</th>
                <th scope="col">Tip</th>
                <th scope="col">Datum</th>
               	<th scope="col">Vrijeme</th>
             	<th scope="col">Trajanje</th>
           		<th scope="col">Cijena</th>

                </tr>
           	</thead>
            <tbody>
                <tr v-for="p in pregledi">
                    <td>{{p.status}}</td>
                    <td>{{p.tip}}</td>
                    <td>{{p.datum}}</td>
                    <td>{{p.vrijeme}}</td>
                    <td>{{p.trajanje}}</td>
					<td>{{p.cijena}}</td>
            	</tr>           
            </tbody>
     	</table>

		<div class="pagination" v-for="i in numPages+1" :key="i" >
		  <a :href="'#/istorijaPregleda/'+(i-1)+'/'+$route.params.criteria" v-on:click="loadNext(i-1, 0)">{{i}}</a>
		</div>
	
</div>		  
`
	,
	methods: {
		loadNext: function(p, crit){
			let criteria = this.$route.params.criteria;	
			switch(crit) {
			  case 1:
			    criteria = "CENA";
			    break;
			  case 3:
			    criteria = "TRAJANJE";
			    break;
			  case 2:
			    criteria = "DATUM";
			  	break;
			  case 4:
				criteria = "PLAIN";
				break;
			}
			
			axios
			.get("api/users/istorijaPregleda/" + this.korisnik.id + "/" + p + "/" + criteria)
			.then(response => {
				this.pregledi = response.data.content;
			});
		}
	},
	mounted: function() {
		axios
			.get("/api/users/currentUser").then(response => {
	            if(response.data){
	            	this.korisnik = response.data;
	                axios
						.get("api/users/istorijaPregleda/" + response.data.id + "/" + this.$route.params.page + "/" + this.$route.params.criteria)
						.then(response => {
							this.pregledi = response.data.content;
							this.numPages = response.data.totalPages - 1;
						});
	            }else{
            	this.$router.push({ path: "/" });
            }
        });
    }
});