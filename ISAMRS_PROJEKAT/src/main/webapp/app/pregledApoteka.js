Vue.component("pregled-apoteka", {
	data: function () {
		    return {
				apoteke : {},
				numPages: 1,
				searchParams: {naziv : "", lokacija: "", startOcena: 0, endOcena: 1000000, rastojanje: 50000, kriterijumSortiranja: "NAZIV", opadajuce: false},
				ulogovan: false,
				imageMap: new Map()
		    }
	},
	template: ` 
<div align = center style="width: 75%">
		
		<h1>Pregled apoteka</h1>
		
		<div id="mySidebar" class="sidebar">
		  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		  <table>
			  <tr><td colspan=2 ><input type="text" name="naziv" placeholder="Unesite naziv" v-model="searchParams.naziv" /></td></tr>
			  <tr><td colspan=2 ><input type="text" name="lokacija" placeholder="Unesite adresu" v-model="searchParams.lokacija" /></td></tr>
			  <tr><td style="color:white">Ocena od:</td> <td><input type="number" name="startOcena" v-model="searchParams.startOcena" ></td></tr>
			  <tr><td style="color:white">Ocena do:</td> <td><input type="number" name="endOcena" v-model="searchParams.endOcena" ></td></tr>
			  <tr v-bind:hidden="!ulogovan" ><td style="color:white">Rastojanje do(km):</td> <td><input type="number" name="rastojanje" v-model="searchParams.rastojanje" ></td></tr>
			  <tr><td style="color:white">Sortiraj po:</td> 
			  		<td>
				  		<select name="tip" id="kriterijum" v-model="searchParams.kriterijumSortiranja" >
						  <option value="NAZIV">NAZIV</option>
						  <option value="LOKACIJA.ULICA">GRAD</option>
						  <option value="OCENA">OCENA</option>
						</select>
					</td></tr>
			  <tr><td style="color:white">Sortiraj opadajuce:</td> <td><input type="checkbox" name="opadajuce" v-model="searchParams.opadajuce" ></td></tr>
			  <tr><td colspan=2 align=center ><input type="button" name="search" value="Pretrazi" v-on:click="loadNext($route.params.page)" /></td></tr>
		  </table>
		</div>
		
		<div id="main">
		  <button class="openbtn" onclick="openNav()">&#9776; Pretraga</button>
		</div>
		
		<br/>
		
		<div class="card row-md-2" v-for="a in this.apoteke">
		  <div class="post-container">
	      <div class="post-thumb"><img :src="imageMap[a.id]" style="height:200px;"></img></div>
		  <div class="post-content">
	      <h2 style="margin-bottom:6px">{{a.naziv}}</h2>
	      <p>{{a.lokacija.ulica}}</p>
	      
	      <p>{{a.opis}}</p>
	      
	      <table>
	      	<tr><td><input type="button" class="button1" value="Vise informacija" v-on:click="pregledaj(a)" /></td><td style="padding = 0px; margin = 0px;" v-bind:hidden=" a.ocena == 0" ><strong>Ocena: {{a.ocena.toFixed(2)}}</strong></td></tr>
	      </table>
	      </div></div>
	    </div>
		
		<div class="pagination" v-for="i in numPages+1" :key="i">
		  <a :href="'#/apoteke/'+(i-1)" v-on:click="loadNext(i-1)">{{i}}</a>
		</div>
		
</div>		  
`
	,
	methods: {
		loadNext: function(p){
			axios
			.get("api/apoteke/all/" + p + "/?naziv=" + this.searchParams.naziv + "&adresa=" + this.searchParams.lokacija + "&dOcena=" + this.searchParams.startOcena + "&gOcena=" + this.searchParams.endOcena + "&rastojanje=" + this.searchParams.rastojanje + "&kriterijum=" + this.searchParams.kriterijumSortiranja + "&smer=" + this.searchParams.opadajuce)
			.then(response => {
				this.apoteke = response.data.content;
				for(a of this.apoteke){
					this.imageMap[a.id] = this.randomItem();
				}
				this.numPages = response.data.totalPages - 1;
			});
		},
		pregledaj: function(a){
			this.$router.push({ name: "PregledApoteke", params: {id: a.id}});
		},
		randomItem: function() {
	    	return 'https://picsum.photos/900/500?random=' + Math.floor(Math.random() * 200);
	    }
	},
	mounted: function() {
		axios
			.get("api/apoteke/all/" + this.$route.params.page + "?naziv=" + this.searchParams.naziv + "&adresa=" + this.searchParams.lokacija + "&dOcena=" + this.searchParams.startOcena + "&gOcena=" + this.searchParams.endOcena + "&rastojanje=" + this.searchParams.rastojanje + "&kriterijum=" + this.searchParams.kriterijumSortiranja + "&smer=" + this.searchParams.opadajuce)
			.then(response => {
				this.apoteke = response.data.content;
				this.numPages = response.data.totalPages - 1;
				for(a of this.apoteke){
					this.imageMap[a.id] = this.randomItem();
				}
			});
		axios
			.get("/api/users/currentUser")
			.then(response => { 
			 if(response.data == ""){
			 	this.ulogovan = false; 
			 } else { 
			 	this.ulogovan = true;
			 }
			 });
    }
});