Vue.component("send-qr", {
	data: function () {
		    return {
				iconBase64: ""
		    }
	},
	template: ` 
<div align = center class="col">
	<input type="file" name="" id="fileId" @change="displayString()">
    <br><br>
  
    <button @click="postString()">
        Display String
    </button>	

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

    }
});