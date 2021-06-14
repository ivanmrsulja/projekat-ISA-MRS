Vue.component("pojedinacni-erecept", {
	data: function () {
		    return {
				stavke : []
		    }
	},
	template: ` 
<div align = center style="width:50%">
		
		<h1>Stavke eRecepta id: {{$route.params.recept}}</h1>
		<br/>
		<table class="table table-hover">
            <thead>
            	<tr>
                <th scope="col">Id</th>
                <th scope="col">Kolicina</th>
                <th scope="col">Preparat</th>
                </tr>
           	</thead>
            <tbody>
                <tr v-for="s in stavke">
                    <td>{{s.id}}</td>
                    <td>{{s.kolicina}}</td>
                    <td>{{s.preparat}}</td>
            	</tr>           
            </tbody>
     	</table>

	
	
</div>		  
`
	,
	mounted: function() {
		axios.get("/api/users/currentUser").then(data => {
            if(data.data){
            	axios
					.get("api/eRecept/stavke/" + this.$route.params.recept)
					.then(response => {
						this.stavke = response.data;
						console.log(this.$route.params.recept);
					});
           }else{
            	this.$router.push({ path: "/" });
            } 
        });
    }
});