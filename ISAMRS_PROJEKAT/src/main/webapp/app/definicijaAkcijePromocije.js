Vue.component("definicija-akcije-promocije", {
	data: function () {
		    return {
                cenovnik: {id: 0, dostupniProizvodi: [], pocetakVazenja: "", krajVazenja: "", promoTekst: ""},
				admin: {
                    id: 0,
                    ime: "",
                    prezime: "",
                },
		    }
	},
	template: ` 
<div align = center style="width: 75% sm;">

<br>
<h2>Definisi novu akciju ili promociju</h2>

<br>
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
<td>Datum isteka vazenja: </td><td><input type="date" v-model="cenovnik.krajVazenja" required /></td>
</tr>
</tbody>
</table>
<br>
<br>

<h4>Telo akcije/promocije: </h4>
<br>
<textarea rows="6" style="width: 50%" name="text" v-model="cenovnik.promoTekst"></textarea>
<br><br>
<input type="button" class="button1" value="Zavrsi" v-bind:hidden="cenovnik.promoTekst == ''" v-on:click="createPromotion()"/>

</div>
`
	,
    methods : {
        createPromotion : function() {
            axios
            .post("api/admin/registerPromo/" + this.admin.id, this.cenovnik)
            .then(response => {
                if (response.data == "OK"){
                    alert("Uspesno kreiranje.");
                }
            }).catch(response => {
                alert("Pogresna vrednost datuma isteka promocije.");
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
                .get("api/admin/cenovnik/" + response.data.id)
                .then(response => {
                    this.cenovnik = response.data;
                    this.cenovnik.krajVazenja = null;
                    this.cenovnik.promoTekst = '';
                });
		    });
        });
    }
});