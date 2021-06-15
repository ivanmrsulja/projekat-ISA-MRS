Vue.component("profil-farmaceuti", {
	data: function () {
		    return {
				farmaceuti : [],
	        	farmaceut: {},
	        	current:{}
		    }
	},
    methods: {
    	 

    	
    	updateDermatolog : function(der) {
    		var s = {id:der.id, ime:der.ime, prezime:der.prezime, username:der.username, email:der.email, telefon:der.telefon, lokacija:der.lokacija};
    		axios
    		.put("api/farmaceut/"+this.farmaceut.id, s)
    		.then(response => toast('Farmaceut ' + der.ime + " " + der.prezime + " uspešno snimljen."));
			this.farmaceut = response.data
    		
    	},
    	
    },
    filters: {
    	dateFormat: function (value, format) {
    		var parsed = moment(value);
    		return parsed.format(format);
    	}
   	},
	template: ` 
	
<div align = center style="width:75%">

	<label style="width:7%; text-align: right;" >Id:</label>
	<input type="text" v-model="farmaceut.id"  style="width:15%; text-align: center; " /> <br />
	<label style="width:7%;text-align: right;">Ime:</label>
	<input name="ime" type="text" v-model="farmaceut.ime" style="width:15%; text-align: center;" /> <br />
	<label style="width:7%;text-align: right;">Prezime:</label>
	<input type="text" v-model="farmaceut.prezime" style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Username:</label>
	<input type="text" v-model="farmaceut.username"  style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Email:</label>
	<input type="text" v-model="farmaceut.email"  style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Telefon:</label>
	<input type="text" v-model="farmaceut.telefon" style="width:15%; text-align: center;"/> <br />
	
	<td align=center><input type="button" class="button1" v-on:click="updateDermatolog(farmaceut)" value="Save" /></td>
	<br/>
</div>		  
`
	,
	mounted: function() {
		axios
			.get("api/farmaceut")
			.then(response => {
				this.farmaceuti = response.data
				axios.get("/api/users/currentUser").then(response => {
					if(response.data){
						this.farmaceut = response.data;
						if (!response.data.loggedBefore) {
							this.$router.push({ path: "/promeniSifruDerFar" });
							}
					}
			});
    });
	}
});
