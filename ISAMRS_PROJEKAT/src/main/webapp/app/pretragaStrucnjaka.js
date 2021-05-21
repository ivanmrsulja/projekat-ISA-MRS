Vue.component("pretraga-strucnjaka", {
	data: function () {
		    return {
				farmaceuti: [],
				dermatolozi: [],
				searchParams: {ime : "", prezime: "", startOcena: 1, endOcena: 5, kriterijumSortiranja: "IME", opadajuce: false},
		    }
	},
	template: ` 
	<div align = center style="width: 75% sm;">

        <div id="mySidebar" class="sidebar">
		  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		  <table>
			  <tr><td colspan=2 ><input type="text" name="naziv" placeholder="Ime" v-model="searchParams.ime" /></td></tr>
			  <tr><td colspan=2 ><input type="text" name="lokacija" placeholder="Prezime" v-model="searchParams.prezime" /></td></tr>
			  <tr><td style="color:white">Ocena od:</td> <td><input type="number" min="0" max="5" name="startOcena" v-model="searchParams.startOcena" ></td></tr>
			  <tr><td style="color:white">Ocena do:</td> <td><input type="number" min="1" max="5" name="endOcena" v-model="searchParams.endOcena" ></td></tr>
			  <tr><td style="color:white">Sortiraj po:</td> 
			  		<td>
				  		<select name="tip" id="kriterijum" v-model="searchParams.kriterijumSortiranja" >
						  <option value="IME">IME</option>
						  <option value="PREZIME">PREZIME</option>
						  <option value="OCENA">OCENA</option>
						</select>
					</td></tr>
			  <tr><td style="color:white">Sortiraj opadajuce:</td> <td><input type="checkbox" name="opadajuce" v-model="searchParams.opadajuce" ></td></tr>
			  <tr><td colspan=2 align=center ><input type="button" name="search" value="Pretrazi farmaceute" v-on:click="searchPharmacists()" /></td></tr>
			  <tr><td colspan=2 align=center ><input type="button" name="search" value="Pretrazi dermatologe" v-on:click="searchDermatologists()" /></td></tr>
		  </table>
		</div>

    <h2>Farmaceuti</h2>

        <div id="main">
		  <button class="openbtn" onclick="openNav()">&#9776; Pretraga</button>
		</div>

    <table class="table table-hover" style="width: 50%" v-bind:hidden="farmaceuti.length == 0" >
	<thead>
		<tr bgcolor="lightgrey">
			<th>Ime</th>
			<th>Prezime</th>
			<th>Ocena</th>
			<th>Apoteka</th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="s in farmaceuti">
		<td>{{s.ime}}</td>
		<td>{{s.prezime}}</td>
		<td>{{s.ocena}}</td>
		<td>{{s.nazivApoteke}}</td>
	</tr>
	</tbody>
	</table>

    <h2>Dermatolozi</h2>

        <div id="main">
		  <button class="openbtn" onclick="openNav()">&#9776; Pretraga</button>
		</div>

	<table class="table table-hover" style="width: 50%" v-bind:hidden="dermatolozi.length == 0" >
	 <thead>
		<tr bgcolor="lightgrey">
			<th>Ime</th>
			<th>Prezime</th>
			<th>Ocena</th>
            <th>Apoteke</th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="s in dermatolozi">
		<td>{{s.ime}}</td>
		<td>{{s.prezime}}</td>
		<td>{{s.ocjena.toFixed(2)}}</td>
        <td>{{s.naziviApoteka.join(', ')}}</td>
	</tr>
	</tbody>
	</table>

</div>		  
`
    ,
    methods: {
        searchPharmacists : function(){
			axios
				.get("api/farmaceut/searchUser" + "/?ime=" + this.searchParams.ime + "&prezime=" + this.searchParams.prezime 
				+ "&startOcena=" + this.searchParams.startOcena + "&endOcena=" + this.searchParams.endOcena + "&kriterijumSortiranja=" + this.searchParams.kriterijumSortiranja +
				 "&opadajuce=" + this.searchParams.opadajuce)
				.then(response => {
					this.farmaceuti = response.data;
				});
		},
        searchDermatologists: function(){
			axios
				.get("api/dermatolog/searchUser" + "/?ime=" + this.searchParams.ime + "&prezime=" + this.searchParams.prezime 
				+ "&startOcena=" + this.searchParams.startOcena + "&endOcena=" + this.searchParams.endOcena + "&kriterijumSortiranja=" + this.searchParams.kriterijumSortiranja +
				 "&opadajuce=" + this.searchParams.opadajuce)
				.then(response => {
					this.dermatolozi = response.data;
				});
        },
    },
    mounted: function(){
        axios
            .get("api/farmaceut")
            .then(response => {
                this.farmaceuti = response.data;
            });
        axios
            .get("api/dermatolog/findAll")
            .then(response => {
                this.dermatolozi = response.data;
            });
    }
});