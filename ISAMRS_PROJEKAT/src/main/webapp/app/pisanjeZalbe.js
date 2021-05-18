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
		<input value="Posalji" class="btn btn-primary" type="button" name="zalBtn" v-on:click="sendZalba()"/>  

	
	
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
            	alert("Morate popuniti tekst");
            	return;
            }
            axios.post("/api/pacijenti/sendZalba", newUser).then(data => {
                if (data.data == "OK") {
                    alert("Uspesno ste poslali zalbu");
                    self.$router.push({ path: "/listaZalbi" });
                }
            });
    	}
    },
	mounted: function() {
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