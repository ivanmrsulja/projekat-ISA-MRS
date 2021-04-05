Vue.component("pregled-erecepata", {
	data: function () {
		    return {
				recepti : [],
				descending: undefined,
				korisnik: {}
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Moji eRecepti</h1>
		<br/>
		
		<h5 style="display: inline-block">Sortiraj po datumu:</h5>
		<div class="form-check form-check-inline">
		  <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="false" v-model="descending" />
		  <label class="form-check-label" for="inlineRadio1">Opadajuce</label>
		</div>
		<div class="form-check form-check-inline">
		  <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="true" v-model="descending" />
		  <label class="form-check-label" for="inlineRadio2">Rastuce</label>
		</div>
		
		<div class="dropdown">
		  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Filtriraj po:
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		    <a class="dropdown-item" v-on:click="filtriraj('NOV')" >NOV</a>
		    <a class="dropdown-item" v-on:click="filtriraj('OBRADJEN')" >OBRADJEN</a>
		    <a class="dropdown-item" v-on:click="filtriraj('ODBIJEN')" >ODBIJEN</a>
		    <a class="dropdown-item" v-on:click="filtriraj('SVI')" >SVI</a>
		  </div>
		</div>
		
		<br/>
		<h2 v-bind:hidden="recepti.length > 0" >Nema rezultata pretrage.</h2>
		<table class="table table-hover" v-bind:hidden="recepti.length == 0" >
            <thead>
            	<tr>
                <th scope="col">Id</th>
                <th scope="col">Status</th>
                <th scope="col">Datum izdavanja</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="r in recepti">
                    <th>{{r.id}}</th>
                    <td>{{r.status}}</td>
                    <td>{{r.datumIzdavanja}}</td>
                    <td><input type="button" value="Pregledaj" v-on:click="pregledajRecept(r)"/></td>
            	</tr>           
            </tbody>
     	</table>

	
	
</div>		  
`
	,
	methods: {
		filtriraj: function(filter){
			let sort, descending;
			if(this.descending == undefined){
				sort = 'false';
				descending = 'false';
			}else{
				sort = 'true';
				descending = this.descending;
			}
			
			axios
			.get("api/eRecept/all/" + this.korisnik.id + "?sort=" + sort + "&descending=" + descending + "&status=" + filter)
			.then(response => {
				this.recepti = response.data;
			});
		},
		pregledajRecept : function(r){
			//window.location.href = "#/eRecepti/" + r.id;
			setTimeout(function () {window.location.href = "#/eRecepti/"+r.id;},0);
		}
	},
	mounted: function() {
		axios.get("/api/users/currentUser").then(data => {
            if(data.data){
            	this.korisnik = data.data;
                axios
				.get("api/eRecept/all/" + data.data.id + "?sort=false&descending=false&status=SVI")
				.then(response => {
					this.recepti = response.data;
				});
            }else{
            	this.$router.push({ path: "/" });
            }
        });
		
    }
});