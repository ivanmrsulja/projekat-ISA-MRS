Vue.component("send-qr", {
	data: function () {
		    return {
				iconBase64: ""
		    }
	},
	template: ` 
<div align = center class="col">
	<table>
			<tr>
				<td> <h2>Upload Qr:</h2> </td>
				<td> <input type="file" name="" id="fileId" @change="displayString()"> </td>
			</tr>
			<tr>
				<td align=center colspan="2">
					<input type="button" class="button1" value="Posalji" @click="postString()"/>
				</td>
			</tr>
		</table>


</div>		  
`
	,
	methods: {
		displayString: function(){
			var file = document.getElementById('fileId').files[0]
			  var reader = new FileReader()
	      reader.readAsDataURL(file)
	      reader.onload = () => {
	        //console.log('file to base64 result:' + reader.result)
	        this.iconBase64 = reader.result.substring(22).trim();
	      }
	      console.log(this.iconBase64);
		},
		postString: function() {
			var file = document.getElementById('fileId').files[0]
			  var reader = new FileReader()
	      reader.readAsDataURL(file)
	      reader.onload = () => {
	        //console.log('file to base64 result:' + reader.result)
	        this.iconBase64 = reader.result.substring(22).trim();
	      }
	      console.log(this.iconBase64 + " PRE SLANJA PREKO POSTA");
	      let self = this;
	      axios.post("/api/users/sendQr", self.iconBase64).then(data => {
				console.log(data.data + " ODGOVOR NAKON POSTA");
				var ids = "";
				ids = data.data;
				var putanja = "/tabelaApoteka/"+ids;
				self.$router.push({ path: putanja });
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
    }
});