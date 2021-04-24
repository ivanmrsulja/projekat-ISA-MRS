Vue.component("zakazivanje-savetovanja", {
	data: function () {
		    return {
				apoteke: [],
				datum: undefined,
				vreme: undefined,
				numPages: 0,
				criteria: "none"
		    }
	},
	template: ` 
<div align = center style="width:50%">
		
		<h1>Zakazi savetovanje</h1>
		
		
		<table>
			<tr><td><h4>Unesite termin:</h4></td><td><input type="date" v-model="datum" /></td><td><h4>u</h4></td><td><input type="time" v-model="vreme" /></td><td><h4>h</h4></td></tr>
			<tr><td></td><td align=center ><input type="button" class="button1" v-on:click="pretrazi('NONE')" value="Pretrazi" /></td><td></td></tr>
		</table>
		<br/>
		
		<div class="dropdown" v-bind:hidden="apoteke.length == 0" >
		  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Sortiraj po:
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		    <a class="dropdown-item"  v-on:click="pretrazi('CENA')" value="CENA" >CENA</a>
		    <a class="dropdown-item"  v-on:click="pretrazi('OCENA')" value="OCENA">OCENA</a>
		    <a class="dropdown-item"  v-on:click="pretrazi('NONE')" value="NONE">BEZ SORTIRANJA</a>
		  </div>
		</div>
		
		<h2 v-bind:hidden="apoteke.length == 0" >Mozete zakazati u:</h2>
		<table class="table table-hover" v-bind:hidden="apoteke.length == 0">
			<thead>
            	<tr>
                <th scope="col">Naziv</th>
                <th scope="col">Adresa</th>
                <th scope="col">Ocena apoteke</th>
               	<th scope="col">Cena savetovanja</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="a in apoteke">
                    <td>{{a.naziv}}</td>
                    <td>{{a.lokacija.ulica}}</td>
                    <td v-if="a.ocena != 0">{{a.ocena.toFixed(2)}}</td>
                    <td v-else ><h4 style="color: lightgray">NIJE OCENJENA</h4></td>
                    <td>{{a.cena}}</td>
					<td><input class="button1" type="button" value="Zakazi" v-on:click="korak2(a)"/></td>
            	</tr>           
            </tbody>
		</table>
		
		<div class="pagination" v-for="i in numPages+1" :key="i" v-bind:hidden="apoteke.length == 0" >
		  <a :href="'#/zakaziSavetovanje/'+(i-1)" v-on:click="loadNext(i-1)">{{i}}</a>
		</div>
</div>		  
`
	,
	methods: {
		pretrazi: function(criteria){
			if(!this.datum || !this.vreme){
				alert("Popunite sva polja.");
				return;
			}
			sessionStorage.setItem('criteria', criteria);
			axios
				.get("api/apoteke/kandidati/" + this.$route.params.page + "?datum=" + this.datum + "&vreme=" + this.vreme + "&criteria=" + criteria)
				.then(response => {
					if(response.data){
						this.apoteke = response.data.content;
						this.numPages = response.data.totalPages - 1;
					}
					else{
						alert("Savetovanje mora biti u buducnosti.");
					}
				});
		},
		loadNext: function(p){
			axios
			.get("api/apoteke/kandidati/" + p + "?datum=" + this.datum + "&vreme=" + this.vreme + "&criteria=" + sessionStorage.getItem('criteria'))
			.then(response => {
				this.apoteke = response.data.content;
				this.numPages = response.data.totalPages - 1;
			});
		},
		korak2: function(a){
			sessionStorage.setItem('datum', this.datum);
			sessionStorage.setItem('vreme', this.vreme);
			this.$router.push({ path: "/zakaziSavetovanje/" + this.$route.params.page + "/" + a.id });
		}
	},
	mounted: function() {
		axios.get("/api/users/currentUser").then(response => {
            if(response.data){
            	this.datum = sessionStorage.getItem('datum');
				this.vreme = sessionStorage.getItem('vreme');
				if(this.datum){
					axios
					.get("api/apoteke/kandidati/" + this.$route.params.page + "?datum=" + this.datum + "&vreme=" + this.vreme + "&criteria=none")
					.then(response => {
						this.apoteke = response.data.content;
						this.numPages = response.data.totalPages - 1;
					});
				}
            }else{
            	this.$router.push({ path: "/" });
            }
        });
    }
});