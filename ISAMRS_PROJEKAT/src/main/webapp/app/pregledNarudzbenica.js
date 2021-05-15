Vue.component("pregled-narudzbenica", {
    data: function() {
        return {
            admin: {id: 0, ime: "", prezime: ""},
            narudzbenice: [],
            filtriranje: "",
        }
    },
    template: `
    
	<div align = center style="width: 75% sm;">

    <br>
    
    <h2>Narudzbenice</h2>

    <label for="status">Filtriranje: </label>
    <select name="status" id="status" v-model="filtriranje">
    <option value="Sve">Sve</option>
    <option value="Obradjene">Obradjene</option>
    <option value="Cekanje">Cekanje</option>
    </select>

    <br>
    <br>

    <div style="width: 50%" v-for="narudzbenica in narudzbenice" 
    v-bind:hidden="(filtriranje == 'Obradjene' && narudzbenica.status != 'OBRADJENA') || (filtriranje == 'Cekanje' && narudzbenica.status != 'CEKA_PONUDE') || narudzbenice.length == 0">
    <table class="table table-hover">
    <thead>
    <tr bgcolor="#90a4ae">
    <th>Preparat</th>
    <th>Kolicina</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="naruceniProizvod in narudzbenica.naruceniProizvodi">
    <td>{{naruceniProizvod.preparat}}</td>
    <td>{{naruceniProizvod.kolicina}}</td>
    </tr>
    <tr bgcolor="#90a4ae"><td></td><td></td></tr>
    <tr><td>Status</td><td>{{narudzbenica.status}}</td></tr>
    <tr><td>Rok</td><td>{{narudzbenica.rok}}</td></tr>
    <tr>
    <td><input type="button" class="button1" value="Pregledaj ponude" v-bind:hidden="narudzbenica.status != 'CEKA_PONUDE'" v-on:click="pregledajPonude(narudzbenica)"/></td>
    <td><input type="button" class="button1" value="Obrisi narudzbenicu" v-bind:hidden="narudzbenica.status != 'CEKA_PONUDE'" v-on:click="ukloniNarudzbenicu(narudzbenica)"/></td>
    </tr>
    </tbody>
    </table>
    <br><br><br>
    </div>

    </div>

    `
    , 
    methods: {
        pregledajPonude: function(narudzbenica) {
            this.$router.push({name: "OdabirPonude", params: {id: narudzbenica.id}});
        },
        ukloniNarudzbenicu: function(narduzbenica) {
            axios
            .delete("api/admin/deleteOrder/" + narduzbenica.id + "/" + this.admin.id)
            .then(response => {
                if (response.data == "OK") {
                    alert("Uspesno brisanje narudzbine.");
                } else {
                    alert("Nemoguce obrisati narudzbenicu jer vec postoje ponude ili niste kreirali istu.");
                }
                axios
                .get("api/admin/narudzbenice/" + this.admin.id)
                .then(response => {
                    this.narudzbenice = response.data;
                });
            });
        },
    },
    mounted: function(){
        axios
        .get("api/users/currentUser")
        .then(response => {
            this.admin = response.data;
            axios
            .get("api/admin/narudzbenice/" + response.data.id)
            .then(response => {
                this.narudzbenice = response.data;
            });
        });
    }
});