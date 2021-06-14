Vue.component("profil-farmaceuti", {
	data: function () {
		    return {
		    	farmaceuti : [],
				mode: "BROWSE",
				farmaceut: {}
		    }
	},
    methods: {
    	selectedFarmaceut : function(f) {
    		if (this.mode == 'BROWSE') {
    			this.farmaceut = f;
    		}    
    	},
    	editFarmaceut : function() {
    		if (this.farmaceut.id == undefined)
    			return;
    		this.backup = [this.farmaceut.ime, this.farmaceut.prezime, this.farmaceut.username, this.farmaceut.email, this.farmaceut.telefon, this.farmaceut.lokacija];
    		this.mode = 'EDIT';
    	},
    	updateFarmaceut : function(far) {
    		var s = {id:far.id, ime:far.ime, prezime:far.prezime, username:far.username, email:far.email, telefon:far.telefon, lokacija:far.lokacija};
    		axios
    		.put("api/farmaceut/"+this.farmaceut.id, s)
    		.then(response => toast('Farmaceut ' + far.ime + " " + far.prezime + " uspešno snimljen."));
    		this.mode = 'BROWSE';
    	},
    	cancelEditing : function() {
    		this.farmaceut.ime = this.backup[0];
    		this.farmaceut.prezime = this.backup[1];
    		this.farmaceut.username = this.backup[2];
    		this.farmaceut.email = this.backup[3];
    		this.farmaceut.telefon = this.backup[4];
    		this.farmaceut.lokacija = this.backup[5];
    		this.mode = 'BROWSE';
    		this.farmaceut={};
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
	<input type="text" v-model="farmaceut.id" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center; " /> <br />
	<label style="width:7%;text-align: right;">Ime:</label>
	<input name="ime" type="text" v-model="farmaceut.ime" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center;" /> <br />
	<label style="width:7%;text-align: right;">Prezime:</label>
	<input type="text" v-model="farmaceut.prezime" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Username:</label>
	<input type="text" v-model="farmaceut.username" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Email:</label>
	<input type="text" v-model="farmaceut.email" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center;"/> <br />
	<label style="width:7%;text-align: right;">Telefon:</label>
	<input type="text" v-model="farmaceut.telefon" v-bind:disabled="mode=='BROWSE'" style="width:15%; text-align: center;"/> <br />
	
	<button v-on:click="updateFarmaceut(farmaceut)" v-bind:disabled="mode=='BROWSE'">Save</button>
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
	<tr v-for="s in farmaceuti" v-on:click="selectedFarmaceut(s)" v-bind:class="{selected : farmaceut.id===s.id}">
		<td>{{s.id }}</td>
		<td>{{s.ime }}</td>
		<td>{{s.prezime }}</td>
		<td>{{s.username }}</td>
		<td>{{s.email}}</td> 
		<td>{{s.telefon}}</td>
	</tr>
	</tbody>
	</table>
	
	<button v-on:click="editFarmaceut()" v-bind:disabled="farmaceut.id==undefined || mode!='BROWSE'">Edit</button><br />
	
	
</div>		  
`
	,
	mounted: function() {
		axios
			.get("api/farmaceut")
			.then(response => {
				this.farmaceuti = response.data
				
			});
    }
});