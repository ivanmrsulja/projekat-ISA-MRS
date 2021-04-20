Vue.component("zakazivanje-savetovanjaK2", {
	data: function () {
		    return {
			farmaceuti: {},
			datum: {},
			vreme: {}
		    }
	},
	template: ` 
<div align = center style="width:50%">
		
		<h1>Izaberite farmaceuta</h1>
		
		<br/>
		
		<div class="dropdown" >
		  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Sortiraj po:
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		    <a class="dropdown-item"  v-on:click="sortiraj('OCENA')" value="OCENA">OCENA</a>
		    <a class="dropdown-item"  v-on:click="sortiraj('NONE')" value="NONE">BEZ SORTIRANJA</a>
		  </div>
		</div>
		
		<table class="table table-hover">
			<thead>
            	<tr>
                <th scope="col">Ime</th>
                <th scope="col">Prezime</th>
                <th scope="col">Ocena farmaceuta</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="a in farmaceuti">
                    <td>{{a.ime}}</td>
                    <td>{{a.prezime}}</td>
                    <td v-if="a.ocena != 0">{{a.ocena}}</td>
                    <td v-else><h4 style="color: lightgray">NIJE OCENJEN</h4></td>
					<td><input class="button1" type="button" value="Zakazi pregled" v-on:click="zakazi(a)"/></td>
            	</tr>           
            </tbody>
		</table>
</div>		  
`
	,
	methods: {
		sortiraj: function(criteria){
			this.datum = sessionStorage.getItem('datum');
			this.vreme = sessionStorage.getItem('vreme');
			axios
				.get("api/apoteke/slobodniFarmaceuti/" + this.$route.params.apoteka + "?datum=" + sessionStorage.getItem('datum') + "&vreme=" + sessionStorage.getItem('vreme') + "&criteria=" + criteria)
				.then(response => {
					this.farmaceuti = response.data;
				});
		},
		zakazi: function(farmaceut){
			let pregled = { datum: this.datum, vrijeme: this.vreme};
			axios
			.post("api/farmaceut/zakaziSavetovanje/" + farmaceut.id + "/" + farmaceut.apoteka, pregled)
			.then(response => {
				alert(response.data);
				if(response.data.startsWith("Pregled uspesno zakazan.")){
					this.$router.push({ path: "/zakazaniPregledi/0" });
				}
			});
		}
	},
	mounted: function() {
		axios.get("/api/users/currentUser").then(response => {
            if(response.data){
            	this.datum = sessionStorage.getItem('datum');
				this.vreme = sessionStorage.getItem('vreme');
				axios
					.get("api/apoteke/slobodniFarmaceuti/" + this.$route.params.apoteka + "?datum=" + sessionStorage.getItem('datum') + "&vreme=" + sessionStorage.getItem('vreme') + "&criteria=none")
					.then(response => {
						this.farmaceuti = response.data;
					});	
            }else{
            	this.$router.push({ path: "/" });
            }
        });
    }
});