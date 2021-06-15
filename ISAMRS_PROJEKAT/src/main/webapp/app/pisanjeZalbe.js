Vue.component("pisanje-zalbe", {
	data: function () {
		    return {
				zalbe : [],
				user: {},
				indZalba: {},
				modalShow: false
		    }
	},
	template: ` 
<div align = center style="width:75%">

		<table>
			<tr>
				<td> <h2>Zalba upucena:</h2> </td> <td> <select id="pharmas">
			<option v-for="z in zalbe">
				{{ z }}
			</option>
		</select> </td>
			</tr>
		</table>
		<textarea rows="25" cols="100" id="tekst">
		</textarea>
		<br>
		<input value="Posalji" class="button1" type="button" name="zalBtn" v-on:click="sendZalba()"/>  

	
	
</div>		  
`
	,
	methods: {	
    	sendZalba : function() {
    		let self = this;
    		let tekst = $("#tekst").val();
    		let zalUpucen = $("#pharmas").children("option:selected").val();
    		console.log(tekst + zalUpucen);
    		let pismo = this.user.username + " za " + zalUpucen + "\n" + tekst;
    		 let newUser = { nazivKorisnika: this.user.username, nazivAdmina: "", tekst: pismo, answered: false};

            if(tekst.trim() == "") {
            	toast("Morate popuniti tekst");
            	return;
            }
            axios.post("/api/pacijenti/sendZalba", newUser).then(data => {
                if (data.data == "OK") {
                    toast("Uspesno ste poslali zalbu");
                    self.$router.push({ path: "/listaZalbi" });
                }
            });
    	}
    },
	mounted: function() {
		let temp = this;
	
		axios
			.get("/api/users/currentUser")
			.then(function(resp){
				if(resp.data.zaposlenjeKorisnika == "ADMIN_APOTEKE"){
							if (resp.data.loggedBefore) {
								temp.$router.push({ path: "/profileApoteke" });
							} else {
								temp.$router.push({ path: "/promeniSifru" });
							}
						}else if(resp.data.zaposlenjeKorisnika == "FARMACEUT"){
							temp.$router.push({ path: "/farmaceuti" });
						}else if(resp.data.zaposlenjeKorisnika == "DOBAVLJAC"){
							if(resp.data.loggedBefore) {
								temp.$router.push({ path: "/tab" });
							} else {
								temp.$router.push({ path: "/promeniSifru" });
							}
						}else if(resp.data.zaposlenjeKorisnika == "DERMATOLOG"){
							temp.$router.push({ path: "/dermatolozi" });
						}else if(resp.data.zaposlenjeKorisnika == "PACIJENT"){
							
						}else if(resp.data.zaposlenjeKorisnika == "ADMIN_SISTEMA") {
							if(resp.data.loggedBefore) {
								temp.$router.push({ path: "/regDerm" });
							} else {
								temp.$router.push({ path: "/promeniSifru" });
							}
						}else {
							temp.$router.push({ path: "/" });
						}
						
					});
		var self = this;
        axios
		.get("/api/users/currentUser")
		.then(function(resp){
			self.user = resp.data;
			axios
			.get("/api/users/getZaljivo/"+self.user.id)
			.then(function(re){
				self.zalbe = re.data;
				console.log(re.data);
			});
		});
		console.log(this.user);
	  	
    }
});