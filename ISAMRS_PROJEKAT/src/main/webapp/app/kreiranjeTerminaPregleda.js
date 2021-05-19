Vue.component("kreiranje-termina-pregleda", {
    data: function() {
        return {
            korisnik: {id: 0, ime: "", prezime: ""},
            apoteka: {id: 0, naziv: ""},
            dermatolozi: [],
            aktuelniDermatolog: 0,
            noviTermin: {},
        }
    },
    template: `
    
	<div align = center style="width: 75% sm;">

    <br><br>

    <h2>Kreiranje termina za preglede kod dermatologa</h2>

    <br><br>

    <table>
    <tr><td><label for="dermatolozi"><h2>Izaberite dermatologa: </h2></label></td>
    <td><select name="dermatolozi" v-model="aktuelniDermatolog">
    <option v-for="d in dermatolozi" :value="d.id">{{d.ime}} {{d.prezime}}</option></select></td></tr>
    <tr><td><h2>Datum pregleda: </h2></td><td><input type="date" v-model="noviTermin.datum"/></td></tr>
    <tr><td><h2>Vreme pocetka: </h2></td><td><input type="time" v-model="noviTermin.vrijeme"/></td></tr>
    <tr><td><h2>Trajanje termina(minuti): </h2></td><td><input type="number" v-model="noviTermin.trajanje"/></td></tr>
    <tr><td><h2>Cena pregleda: </h2></td><td><input type="number" v-model="noviTermin.cijena"/></td></tr>
    </table>

    <br>

    <input type="button" class="button1" value="Kreiraj" v-on:click="createExamination()"/>

    </div>
    `
    , methods: {
        createExamination: function() {
            axios
            .post("api/admin/registerExamination/" + this.aktuelniDermatolog + "/" + this.apoteka.id, this.noviTermin)
            .then(response => {
                if (response.data == "OK") {
                    alert("Uspesno kreiranje termina.");
                } else {
                    alert("Neuspesno kreiranje termina.");
                }
                this.$router.push({name: "ProfilApoteke"});
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
		    });
            axios
            .get("api/dermatolog/apoteka/admin/" + response.data.id)
		  	.then(response => {
			  	this.dermatolozi = response.data;
		  	});
        });
    }
});