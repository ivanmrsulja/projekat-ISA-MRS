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
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Status</th>
                <th scope="col">Tip</th>
                <th scope="col">Datum</th>
               	<th scope="col">Vrijeme</th>
             	<th scope="col">Trajanje</th>
           		<th scope="col">Cijena</th>

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
            	</tr>           
            </tbody>
     	</table>

		<div class="pagination" v-for="i in numPages+1" :key="i" >
		  <a :href="'#/zakazaniPregledi/'+(i-1)" v-on:click="loadNext(i-1)">{{i}}</a>
		</div>
	
</div>		  
`
	,
	methods: {
		loadNext: function(p){
			axios
			.get("api/users/pregledi/" + "1" + "/" + p)
			.then(response => {
				this.pregledi = response.data.content;
			});
		}
	},
	mounted: function() {
		axios
			.get("api/users/pregledi/" + "1" + "/" + this.$route.params.page)
			.then(response => {
				this.pregledi = response.data.content;
				this.numPages = response.data.totalPages - 1;
			});
    }
});