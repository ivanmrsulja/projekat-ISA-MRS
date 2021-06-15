Vue.component("pisanje-narudzbenice", {
	data: function () {
		    return {
                admin: {},
                apoteka: {id: 0, naziv: ""},
				preparati: [],
                preparatiVanApoteke: [],
                preparatiNarudzbenice: [],
                dodato: false,
                cena: 0,
                aktuelniPreparat: "",
                rok: "",
                narudzbenica: {},
		    }
	},
	template: ` 
    <div align = center style="width: 75% sm;">

    <h2 v-bind:hidden="preparati.length == 0">Preparati apoteke</h2>

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
		<td><input type="button" class="button1" value="Odaberi" v-on:click="addExistingToOrder(p)"/></td>
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

    <br>
    <br>

    <h2 v-bind:hidden="preparatiNarudzbenice.length == 0">Preparati narudzbenice</h2>

    <table class="table table-hover" style="width: 50%" v-bind:hidden="preparatiNarudzbenice.length == 0" >
	<thead>
		<tr bgcolor="#90a4ae">
			<th>Naziv</th>
			<th>Kolicina</th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="p in preparatiNarudzbenice">
		<td>{{p.preparat}}</td>
		<td><input type="number" v-model="p.kolicina"/></td>
	</tr>
    <tr><td>Rok: </td><td><input type="date" v-model="rok" required /></td></tr>
	</tbody>
	</table>

    <br>
    <br>

    <input type="button" class="button1" value="Posalji" v-bind:hidden="preparatiNarudzbenice.some((preparat) => preparat.kolicina <= 0) || preparatiNarudzbenice.length == 0" v-on:click="confirmOrder()"/>

    </div>

    `
    ,
    methods: {
        confirmOrder: function() {
            now_date = new Date();
            comparison_date = new Date(this.rok);
            if (comparison_date < now_date) {
                toast("Pogresna vrednost za datum roka.");
                return;
            }

            this.narudzbenica.rok = this.rok;
            this.narudzbenica.naruceniProizvodi = this.preparatiNarudzbenice;
            this.narudzbenica.idAdmina = this.admin.id;
            this.narudzbenica.status = 'CEKA_PONUDE';

            axios
            .post("api/admin/registerOrder/" + this.admin.id, this.narudzbenica)
            .then(response => {
                if (response.data == "OK") {
                    toast("Uspesna registracija narudzbenice.");
                }
            });
        },
        addExistingToOrder: function(dostupanProizvod) {
            this.preparatiNarudzbenice.push({preparat: dostupanProizvod.preparat, kolicina: 0});
            for (var i = 0; i < this.preparati.length; i++) {
                if (this.preparati[i].id == dostupanProizvod.id) {
                    this.preparati.splice(i, 1);
                    i--;
                }
            }
        },
        addProduct: function(preparat) {
            this.dodato = true;
            this.aktuelniPreparat = preparat;
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
                var elementsToRemove = [];
                for (var i = 0; i < this.preparati.length; i++) {
                    for (var p2 of this.preparatiNarudzbenice) {
                        if (this.preparati[i].preparat == p2.preparat) {
                            elementsToRemove.push(this.preparati[i]);
                        }
                    }
                }
                for (var element of elementsToRemove) {
                    var index = this.preparati.indexOf(element);
                    this.preparati.splice(index, 1);
                }
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
            this.admin = response.data;
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