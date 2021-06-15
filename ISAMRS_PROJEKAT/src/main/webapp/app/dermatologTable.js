Vue.component("profil-dermatolozi", {
	data: function () {
		    return {
				dermatolozi : [],
	        	dermatolog: {},
	        	current:{}
		    }
	},
    methods: {
    	 

    	
    	updateDermatolog : function(der) {
    		var s = {id:der.id, ime:der.ime, prezime:der.prezime, username:der.username, email:der.email, telefon:der.telefon, lokacija:der.lokacija};
    		axios
    		.put("api/dermatolog/"+this.dermatolog.id, s)
    		.then(response => toast('Dermatolog ' + der.ime + " " + der.prezime + " uspešno snimljen."));
    		
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
	<input type="text" v-model="dermatolog.id"  style="width:15%; text-align: center; " /> <br />
	<label style="width:7%;text-align: right;">Ime:</label>
	<input name="ime" type="text" v-model="dermatolog.ime" style="width:15%; text-align: center;" /> <br />
	<label style="width:7%;text-align: right;">Prezime:</label>
	<input type="text" v-model="dermatolog.prezime" style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Username:</label>
	<input type="text" v-model="dermatolog.username"  style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Email:</label>
	<input type="text" v-model="dermatolog.email"  style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Telefon:</label>
	<input type="text" v-model="dermatolog.telefon" style="width:15%; text-align: center;"/> <br />
	
	<td align=center><input type="button" class="button1" v-on:click="updateDermatolog(dermatolog)" value="Save" /></td>
	<br/>
</div>		  
`
	,
	mounted: function() {
		axios
			.get("api/dermatolog")
			.then(response => {
				this.dermatolozi = response.data
				axios.get("/api/users/currentUser").then(response => {
		            if(response.data){
		            	this.dermatolog = response.data;
		            }				
			});
    });
	}
});