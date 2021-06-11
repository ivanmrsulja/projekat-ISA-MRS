Vue.component("jedna-zalbaadmin", {
	data: function () {
		    return {
				zalbe : {},
				user: {},
				indZalba: {},
				modalShow: false
		    }
	},
	template: ` 
<div align = center style="width:75%">

		<h1> Pacijent: {{zalbe.nazivKorisnika}}</h1>
		<h1> Admin (Ukoliko je odgovor na zalbu): {{zalbe.nazivAdmina}}</h1>
		<h1 v-if="zalbe.answered">Admin je odgovorio na ovu zalbu</h1>
		<br>
		<br>
		<h1>Tekst zalbe</h1>
		<textarea rows="25" cols="100" v-model="zalbe.tekst" id="napisano" disabled>
		</textarea>
		<br>
		<br>
		<br>
		<div v-if="!zalbe.answered">
			<h1> Odgovor </h1>
			<textarea rows="25" cols="100" id="tekst">
			</textarea>
			<br>
			<input value="Posalji" class="button1" type="button" name="zalBtn" v-on:click="sendZalba()"/>  
		</div>

	
	
</div>		  
`
	,
	methods: {	
    	sendZalba : function() {
    		let self = this;
    		let tekst = "RE: "+$("#napisano").val();
    		let konTekst = tekst + "\n==================\nODGOVOR:\n" + $("#tekst").val()
    		console.log(konTekst);
    		let newUser = { nazivKorisnika: this.zalbe.nazivKorisnika, nazivAdmina: this.user.username, tekst: konTekst, answered: true};
            axios.post("/api/pacijenti/sendZalbaa", newUser).then(data => {
                if (data.data == "OK") {
                    toast("Uspesno ste poslali zalbu");
                    
                }
            });
            axios.put("/api/admin/ZalbeUpdate/"+self.$route.params.zalId, {
    headers: {
    'Content-Type': 'text/plain'
    }}).then(data => {
                if (data.data == "OK") {
                    //toast("Uspesno ste poslali zalbu");
                    self.$router.push({ path: "/listaZalbiAdmin" });
                    
                }
            });
    	}
    },
	mounted: function() {
		console.log(this.$route.params.zalId);
		var self = this;
        axios
		.get("/api/users/currentUser")
		.then(function(resp){
			self.user = resp.data;
			axios
			.get("/api/admin/getZalba/"+self.$route.params.zalId)
			.then(function(re){
				self.zalbe = re.data;
				
			});
		});
		console.log(this.user);
	  	
    }
});