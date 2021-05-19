Vue.component("odabir-ponude", {
    data: function() {
        return {
            admin: {},
            ponude: [],
            narudzbenica: {},
            date_now: "",
            narudzbenica_date: "",
        }
    }, 
    template: `

    <div align = center style="width: 75% sm;">

    <br>

    <h2 v-bind:hidden="ponude.length == 0">Ponude</h2>

    <br><br>

    <table class="table table-hover" style="width: 50%" v-bind:hidden="ponude.length == 0">
	<thead>
		<tr bgcolor="#90a4ae">
			<th>Dobavljac</th>
			<th>Ukupna cena</th>
			<th>Rok isporuke</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="p in ponude">
		<td>{{p.dobavljac}}</td>
		<td>{{p.ukupnaCena}}</td>
		<td>{{p.rokIsporuke}}</td>
		<td><input type="button" class="button1" value="Odaberi" v-bind:hidden="date_now < narudzbenica_date" v-on:click="odaberiPonudu(p)"/></td>
	</tr>
	</tbody>
	</table>

    </div>

    `
    , methods: {
        odaberiPonudu: function(ponuda) {
            for (var i = 0; i < this.ponude.length; i++) {
                if (this.ponude[i].id == ponuda.id) {
                    this.ponude[i].status = 'PRIHVACENA';
                } else {
                    this.ponude[i].status = 'ODBIJENA';
                }
            }
            axios
            .put("api/admin/updateOrderStatus/" + this.narudzbenica.id)
            .then(response => {
                if (response.data != "OK") {
                    alert("Doslo je do greske.");
                    return;
                }
            });
            axios
            .put("api/admin/updateOffersStatus/" + this.narudzbenica.id + "/" + ponuda.id, this.ponude)
            .then(response => {
                if (response.data != "OK") {
                    alert("Doslo je do greske.");
                    return;
                }
            });
            axios
            .put("api/admin/updateStocks/" + this.narudzbenica.id + "/" + this.admin.id)
            .then(response => {
                if (response.data == "OK") {
                    alert("Uspesno azuriranje.");
                    this.$router.push({name: "PregledNarudzbenica"});
                }
            });
        },
    }, mounted: function() {
        axios
        .get("api/admin/orderOffers/" + this.$route.params.id)
        .then(response => {
            this.ponude = response.data;
            if (this.ponude.length == 0) {
                alert("Ne postoje ponude za narudzbinu.");
                this.$router.push({name: "PregledNarudzbenica"});
            }
            axios
            .get("api/users/currentUser")
            .then(response => {
                this.admin = response.data;
                axios
                .get("api/admin/getOrder/" + this.$route.params.id)
                .then(response => {
                    this.narudzbenica = response.data;
                    if (this.narudzbenica.status == 'OBRADJENA') {
                        this.$router.push({name: "PregledNarudzbenica"});
                    }
                    this.date_now = new Date();
                    this.narudzbenica_date = new Date(this.narudzbenica.rok);
                    if (this.admin.id != this.narudzbenica.idAdmina) {
                        alert("Niste kreirali ovu narudzbinu i ne mozete je pregledati.");
                        this.$router.push({name: "PregledNarudzbenica"});
                    }
                });
            });
        });
    }
});