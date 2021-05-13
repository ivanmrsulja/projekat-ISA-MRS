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

    <div style="width: 50%" v-bind:hidden="narudzbenice.length == 0" v-for="narudzbenica in narudzbenice" 
    v-bind:hidden="(filtriranje == 'Obradjene' && narudzbenica.status != 'OBRADJENA') || (filtriranje == 'Cekanje' && narudzbenica.status != 'CEKA_PONUDE')">
    <table class="table table-hover" >
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
    </tbody>
    </table>
    <br><br><br>
    </div>

    </div>

    `
    , 
    methods: {

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