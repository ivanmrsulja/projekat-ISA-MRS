Vue.component("definicija-akcije-promocije", {
	data: function () {
		    return {
				admin: {
                    id: 0,
                    ime: "",
                    prezime: "",
                },
                teloAkcije: {
                    tekst: "",
                    idAdmina: 0
                },
		    }
	},
	template: ` 
<div align = center style="width: 75% sm;">

<br>
<h2>Definisi novu akciju ili promociju</h2>
<textarea rows="6" cols="25" name="text" v-model="teloAkcije.tekst"></textarea>
<br><br>
<input type="button" class="button1" value="Zavrsi" v-bind:hidden="teloAkcije.tekst == ''" v-on:click="createPromotion()"/>

</div>
`
	,
    methods : {
        createPromotion : function() {
            axios
            .post("api/admin/registerPromo", this.teloAkcije)
            .then(response => {
                if (response.data == "OK"){
                    alert("Uspesno kreiranje.");
                }
            });
        },
    },
    mounted: function() {
        axios
        .get("api/users/currentUser")
        .then(response => {
            this.admin = response.data;
            this.teloAkcije.idAdmina = response.data.id;
        })
    }
});