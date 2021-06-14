Vue.component("preparati-apoteke", {
	data: function () {
		    return {
                apoteka: {id: 0, naziv: ""},
				preparati: [],
                preparatiVanApoteke: [],
				searchParams: {naziv: "", startCena: 0, endCena: 2000, kriterijumSortiranja: "NAZIV", opadajuce: false},
                dodato: false,
                cena: 0,
                aktuelniPreparat: "",
		    }
	},
	template: ` 
    <div align = center style="width: 75% sm;">

        <div id="mySidebar" class="sidebar">
		  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		  <table>
			  <tr><td colspan=2 ><input type="text" name="naziv" placeholder="Naziv" v-model="searchParams.naziv" /></td></tr>
			  <tr><td style="color:white">Cena od:</td> <td><input type="number" name="startCena" v-model="searchParams.startCena" ></td></tr>
			  <tr><td style="color:white">Cena do:</td> <td><input type="number" name="endCena" v-model="searchParams.endCena" ></td></tr>
			  <tr><td style="color:white">Sortiraj po:</td> 
			  		<td>
				  		<select name="tip" id="kriterijum" v-model="searchParams.kriterijumSortiranja" >
						  <option value="NAZIV">NAZIV</option>
						  <option value="CENA">CENA</option>
						  <option value="KOLICINA">KOLICINA</option>
						</select>
					</td></tr>
			  <tr><td style="color:white">Sortiraj opadajuce:</td> <td><input type="checkbox" name="opadajuce" v-model="searchParams.opadajuce" ></td></tr>
			  <tr><td colspan=2 align=center ><input type="button" name="search" value="Pretrazi preparate" v-on:click="searchProducts()" /></td></tr>
		  </table>
		</div>

    <h2 v-bind:hidden="preparati.length == 0">Preparati apoteke</h2>

        <div id="main">
		  <button class="openbtn" onclick="openNav()">&#9776; Pretraga</button>
		</div>

    <table class="table table-hover" style="width: 50%" v-bind:hidden="preparati.length == 0" >
	<thead>
		<tr bgcolor="#90a4ae">
			<th>Naziv</th>
			<th>Cena</th>
			<th>Kolicina</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="p in preparati">
		<td>{{p.preparat}}</td>
		<td>{{p.cena}}</td>
		<td>{{p.kolicina}}</td>
		<td><input type="button" class="button1" value="Ukloni" v-on:click="removeProduct(p)"/></td>
	</tr>
	</tbody>
	</table>

    <br>
    <br>

    <h2 v-bind:hidden="preparatiVanApoteke.length == 0">Preparati koji nisu u apoteci</h2>

    <table class="table table-hover" style="width: 50%" v-bind:hidden="preparatiVanApoteke.length == 0" >
	<thead>
		<tr bgcolor="#90a4ae">
			<th>Naziv</th>
			<th>Proizvodjac</th>
			<th>Ocena</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="p in preparatiVanApoteke">
		<td>{{p.naziv}}</td>
		<td>{{p.proizvodjac}}</td>
		<td>{{p.ocena.toFixed(2)}}</td>
		<td><input type="button" class="button1" value="Dodaj" v-on:click="addProduct(p)"/></td>
	</tr>
	</tbody>
	</table>

    <div align = center style ="width: 75% sm;" v-bind:hidden="!this.dodato">
		<h2>Cena: </h2>
		<input type="number" v-model="cena"/>
		<br>
        <br>
		<input type="button" class="button1" value="Posalji" v-bind:hidden="cena <= 0" v-on:click="registerProductForPharmacy(aktuelniPreparat)"/>
	</div>

    </div>

    `
    ,
    methods: {
        searchProducts: function() {
			axios
				.get("api/admin/searchPharmacy/" + this.apoteka.id + "/" + this.searchParams.naziv)
				.then(response => {
					this.preparati = response.data;

                    var startCena = this.searchParams.startCena;
                    var endCena = this.searchParams.endCena;
                    this.preparati = this.preparati.filter(function(preparat){
                        return preparat.cena >= startCena && preparat.cena <= endCena;
                    });

                    if (this.searchParams.kriterijumSortiranja == "NAZIV"){
                        this.preparati.sort((a, b) => (a.preparat > b.preparat) ? 1 : -1);
                    }
                    else if (this.searchParams.kriterijumSortiranja == "CENA"){
                        this.preparati.sort((a, b) => (a.cena > b.cena) ? 1 : -1);
                    } 
                    else if (this.searchParams.kriterijumSortiranja == "KOLICINA"){
                        this.preparati.sort((a, b) => (a.kolicina > b.kolicina) ? 1 : -1);
                    }

                    if (this.searchParams.opadajuce)
                        this.preparati = this.preparati.reverse();
				});
		},
        removeProduct: function(preparat) {
            axios
            .delete("api/admin/deleteProduct/" + preparat.id + "/" + this.apoteka.id)
            .then(response => {
                this.preparati = response.data;
                axios
                .get("api/admin/productsOutsidePharmacy/" + this.apoteka.id)
                .then(response => {
                    this.preparatiVanApoteke = response.data;
                });
            });
        },
        addProduct: function(preparat) {
            this.dodato = true;
            this.aktuelniPreparat = preparat;
            toast("Unesite cenu ispod tabele.");
        },
        registerProductForPharmacy: function(product) {
            axios
            .put("/api/admin/addProductToPharmacy/" + this.apoteka.id + "/" + this.cena, this.aktuelniPreparat)
            .then(response => {
                if (response.data == "OK"){
                    toast("Uspesno dodavanje.");
                }
            axios
            .get("api/admin/searchPharmacy/" + this.apoteka.id)
            .then(response => {
                this.preparati = response.data;
            });
            axios
            .get("api/admin/productsOutsidePharmacy/" + this.apoteka.id)
            .then(response => {
                this.preparatiVanApoteke = response.data;
            });
            this.dodato = false;
            this.cena = 0;
            this.aktuelniPreparat = "";
            });
        },
    },
    mounted: function() {
        axios
		.get("api/users/currentUser")
		.then(response => {
		  axios
			.get("api/apoteke/admin/" + response.data.id)
			.then(response => {
				this.apoteka = response.data;
                axios
                .get("api/admin/searchPharmacy/" + response.data.id)
                .then(response => {
                    this.preparati = response.data;
                });
                axios
                .get("api/admin/productsOutsidePharmacy/" + response.data.id)
                .then(response => {
                    this.preparatiVanApoteke = response.data;
                });
		  });
        });
    }
});