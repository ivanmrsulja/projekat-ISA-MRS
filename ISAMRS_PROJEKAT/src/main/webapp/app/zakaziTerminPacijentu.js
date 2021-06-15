Vue.component("zakazi-termin-pacijentu", {
    data: function() {
        return {
            korisnik: {id: 0, ime: "", prezime: ""},
            apoteke: [],
            dermatolog: {},
            aktuelnaApoteka: 0,
            noviTermin: {},
        }
    },
    template: `
    
	<div align = center style="width: 75% sm;">	

    <br><br>

    <h2>Kreiranje termina za preglede kod dermatologa</h2>

    <br><br>

    <table>
    <tr><td><label for="apotekee"><h2>Izaberite apoteku: </h2></label></td>
    <td><select name="apotekee" v-model="aktuelnaApoteka">
    <option v-for="d in apoteke" :value="d">{{d}}</option></select></td></tr>
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
            .post("api/dermatolog/registerExamination/" +this.korisnik.id+ "/" + 1, this.noviTermin)
            .then(response => {
                if (response.data == "OK") {
                    toast("Uspesno kreiranje termina.");
                } else {
                    toast("Neuspesno kreiranje termina.");
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
            .get("api/dermatolog/listaApoteka/"+this.korisnik.id)
            .then(response => {
               this.apoteke=response.data;
               
        });
    });
    }
});