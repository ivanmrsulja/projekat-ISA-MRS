Vue.component("profil-dermatolozi", {
	data: function () {
		    return {
				dermatolozi : [],
				mode: "BROWSE",
	        	dermatolog: {}
		    }
	},
    methods: {
    	selectedDermatolog : function(d) {
    		if (this.mode == 'BROWSE') {
    			this.dermatolog = d;
    		}    
    	},
    	editDermatolog : function() {
    		if (this.dermatolog.id == undefined)
    			return;
    		this.backup = [this.dermatolog.ime, this.dermatolog.prezime, this.dermatolog.username, this.dermatolog.email, this.dermatolog.telefon, this.dermatolog.lokacija];
    		this.mode = 'EDIT';
    	},
    	updateDermatolog : function(der) {
    		var s = {id:der.id, ime:der.ime, prezime:der.prezime, username:der.username, email:der.email, telefon:der.telefon, lokacija:der.lokacija};
    		axios
    		.put("api/dermatolog/"+this.dermatolog.id, s)
    		.then(response => toast('Dermatolog ' + der.ime + " " + der.prezime + " uspešno snimljen."));
    		this.mode = 'BROWSE';
    	},
    	cancelEditing : function() {
    		this.dermatolog.ime = this.backup[0];
    		this.dermatolog.prezime = this.backup[1];
    		this.dermatolog.username = this.backup[2];
    		this.dermatolog.email = this.backup[3];
    		this.dermatolog.telefon = this.backup[4];
    		this.dermatolog.lokacija = this.backup[5];
    		this.mode = 'BROWSE';
    		this.dermatolog={};
    	}
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
	<input type="text" v-model="dermatolog.id" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center; " /> <br />
	<label style="width:7%;text-align: right;">Ime:</label>
	<input name="ime" type="text" v-model="dermatolog.ime" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center;" /> <br />
	<label style="width:7%;text-align: right;">Prezime:</label>
	<input type="text" v-model="dermatolog.prezime" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Username:</label>
	<input type="text" v-model="dermatolog.username" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Email:</label>
	<input type="text" v-model="dermatolog.email" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Telefon:</label>
	<input type="text" v-model="dermatolog.telefon" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center;"/> <br />
	
	<button v-on:click="updateDermatolog(dermatolog)" v-bind:disabled="mode=='BROWSE'">Save</button>
	<button v-on:click="cancelEditing" v-bind:disabled="mode=='BROWSE'">Cancel</button> <br />
	
	<br/>
	
	<table class="table table-hover">
	 <thead>
		<tr bgcolor="lightgrey">
			<th>Id</th>
			<th>Ime</th>
			<th>Prezime</th>
			<th>Username</th>
			<th>Email</th>
			<th>Telefon</th>
		</tr>
	</thead>
	<tbody>
	<tr v-for="s in dermatolozi" v-on:click="selectedDermatolog(s)" v-bind:class="{selected : dermatolog.id===s.id}">
		<td>{{s.id }}</td>
		<td>{{s.ime }}</td>
		<td>{{s.prezime }}</td>
		<td>{{s.username }}</td>
		<td>{{s.email}}</td> 
		<td>{{s.telefon}}</td>
	</tr>
	</tbody>
	</table>
	
	<button v-on:click="editDermatolog()" v-bind:disabled="dermatolog.id==undefined || mode!='BROWSE'">Edit</button><br />
	
	
</div>		  
`
	,
	mounted: function() {
		axios
			.get("api/dermatolog")
			.then(response => {
				this.dermatolozi = response.data
				
			});
    }
});