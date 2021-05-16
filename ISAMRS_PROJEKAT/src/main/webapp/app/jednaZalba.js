Vue.component("jedna-zalba", {
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
		<br>
		<br>
		<h1>Tekst zalbe</h1>
		<textarea rows="25" cols="100" v-model="zalbe.tekst" disabled>
		</textarea>

	
	
</div>		  
`
	,
	methods: {	
    	pregledajBtn : function(event) {
    		let sifra = event.target.id;
    		var i;
			for (i = 0; i < this.zalbe.length; i++) {
			  if(this.zalbe[i].id == sifra) {
			  	this.indZalba.id = this.zalbe[i].id;
			  	this.indZalba.tekst = this.zalbe[i].tekst;
			  	console.log("BOOYA");
			  }
			}
			console.log(this.indZalba);
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
			.get("/api/users/getZalbe/"+self.user.id+"/"+self.$route.params.zalId)
			.then(function(re){
				self.zalbe = re.data;
				
			});
		});
		console.log(this.user);
	  	
    }
});