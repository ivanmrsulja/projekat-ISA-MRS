Vue.component("definicija-cenovnika", {
    data: function() {
        return {
            cenovnik: {id: 0, dostupniProizvodi: [], pocetakVazenja: ""},
            korisnik: {id: 0, ime: "", prezime: ""},
            apoteka: {id: 0, naziv: ""},
        }
    },
    template: `
    
	<div align = center style="width: 75% sm;">

    <br>
    <br>

    <h2>Cenovnik apoteke</h2>

    <br>

    <table class="table table-hover" style="width: 50%">
    <thead>
    <tr bgcolor="#90a4ae">
    <th>Preparat</th>
    <th>Cena</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="dostupanProizvod in cenovnik.dostupniProizvodi">
    <td>{{dostupanProizvod.preparat}}</td>
    <td><input type="number" v-model="dostupanProizvod.cena"/></td>
    </tr>
    <tr>
    <td>Datum pocetka vazenja: </td><td><input type="date" v-model="cenovnik.pocetakVazenja" required /></td>
    </tr>
    </tbody>
    </table>
    <br>
    <br>
    <input type="button" class="button1" value="Sacuvaj" v-on:click="registerCenovnik()"/>

    </div>

    `
    , methods: {
        registerCenovnik: function() {
            now_date = new Date();
            comparison_date = new Date(this.cenovnik.pocetakVazenja);
            if (comparison_date < now_date){
                alert("Pogresna vrednost datuma.");
                return;
            }
            axios
            .post("api/admin/registerCenovnik/" + this.apoteka.id, this.cenovnik)
            .then(response => {
                if (response.data == "OK"){
                    alert("Uspesno azuriranje cenovnika");
                }
            });
        },
    },
    mounted: function() {
        axios
		.get("api/users/currentUser")
		.then(response => {
            this.korisnik = response.data;
            axios
			.get("api/apoteke/admin/" + response.data.id)
			.then(response => {
				this.apoteka = response.data;
                axios
                .get("api/admin/cenovnik/" + response.data.id)
                .then(response => {
                    this.cenovnik = response.data;
                    this.cenovnik.pocetakVazenja = null;
                });
		    });
        });
    }
});