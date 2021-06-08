Vue.component("zakazani-pregledi", {
	data: function () {
		    return {
				pregledi : [],
				numPages: 1
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Zakazani pregledi</h1>
		<br/>
		<h2 v-bind:hidden="pregledi.length > 0" >Nemate ni jedan zakazan pregled.</h2>
		<table class="table table-hover" v-bind:hidden="pregledi.length == 0">
            <thead>
            	<tr>
                <th scope="col">Status</th>
                <th scope="col">Tip</th>
                <th scope="col">Datum</th>
               	<th scope="col">Vrijeme</th>
             	<th scope="col">Trajanje</th>
           		<th scope="col">Cijena</th>
				<th scope="col">Akcija</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="p in pregledi">
                    <td>{{p.status}}</td>
                    <td>{{p.tip}}</td>
                    <td>{{p.datum}}</td>
                    <td>{{p.vrijeme}}</td>
                    <td>{{p.trajanje}}</td>
					<td>{{p.cijena}}</td>
					<td v-if="!isValid(p)"><input type="button" class="button1" value="Otkazi" v-on:click="otkazi(p)" /></td>
					<td v-else ><h4 style="color: lightgray" >ISTEKLO</h4></td>
            	</tr>           
            </tbody>
     	</table>

		<div class="pagination" v-for="i in numPages+1" :key="i" v-bind:hidden="pregledi.length == 0" >
		  <a :href="'#/zakazaniPregledi/'+(i-1)" v-on:click="loadNext(i-1)">{{i}}</a>
		</div>
	
</div>		  
`
	,
	methods: {
		loadNext: function(p){
			axios
			.get("api/users/pregledi/" + this.korisnik.id + "/" + p)
			.then(response => {
				this.pregledi = response.data.content;
			});
		},
		otkazi: function(p){
			axios
			.patch("api/apoteke/otkazi/" + p.id)
			.then(response => {
				if (response.data == "OK"){
					this.loadNext(this.$route.params.page);
				}else{
					toast(response.data);
				}
			});
		},
		isValid: function(p){
			let d = new Date(p.datum);
			console.log((d.getTime()) + parseInt(p.vrijeme.split(':')[0] - 2) * 3600000);
			console.log(Date.now() + 86400000);
			return (d.getTime()) + parseInt(p.vrijeme.split(':')[0]) * 3600000 <= Date.now() + 8640000;
		}
	},
	mounted: function() {
		axios.get("/api/users/currentUser").then(response => {
            if(response.data){
            	this.korisnik = response.data;
                axios
				.get("api/users/pregledi/" + response.data.id + "/" + this.$route.params.page)
				.then(response => {
					this.pregledi = response.data.content;
					this.numPages = response.data.totalPages - 1;
				});
            }else{
            	this.$router.push({ path: "/" });
            }
        });
			
    }
});