Vue.component("pregled-notifikacija", {
    data: function() {
        return {
            korisnik: {id: 0, ime: "", prezime: ""},
            apoteka: {id: 0, naziv: ""},
            notifikacije: [],
        }
    },
    template: `
    
	<div align = center style="width: 75% sm;" v-bind:hidden="notifikacije.length == 0">

    <br>
    <br>

    <h2>Notifikacije apoteke</h2>

    <br>

    <table class="table table-hover" style="width: 50%">
	<thead>
		<tr bgcolor="#90a4ae">
			<th>Preparat</th>
			<th>Korisnik</th>
			<th>Datum</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="n in notifikacije">
		<td>{{n.preparat}}</td>
		<td>{{n.korisnik.username}}</td>
		<td>{{n.datum}}</td>
        <td>{{n.status}}</td>
	</tr>
	</tbody>
	</table>

    </div>
    `
    , methods: {

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
                .get("api/admin/notifications/" + response.data.id)
                .then(response => {
                    this.notifikacije = response.data;
                    if (this.notifikacije.length == 0) {
                        toast("Nemate notifikacije.");
                        this.$router.push({name: "ProfilApoteke"});
                    }
                });
                axios
                .put("api/admin/updateNotifications/" + response.data.id)
                .then(response => {
                    ;
                });
		  });
        });
    }
});