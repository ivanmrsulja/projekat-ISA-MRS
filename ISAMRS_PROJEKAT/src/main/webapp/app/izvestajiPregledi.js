Vue.component("izvestaji-pregledi", {
	data: function () {
		    return {
				admin: {},
				apoteka: {},
				periodPregledi: "godisnji",
				examination_data: [],
				yearPregledi: 2021,
				quarterPregledi: 1,
				monthPregledi: 1,
				periodPrihodi: "godisnji",
				income_data: [],
				yearPrihodi: 2021,
				quarterPrihodi: 1,
				monthPrihodi: 1,
				periodPotrosnja: "godisnji",
				usage_data: [],
				yearPotrosnja: 2021,
				quarterPotrosnja: 1,
				monthPotrosnja: 1,
		    }
	},
	template: ` 
	<div align = center style="width: 75% sm;">

    <br>

	<h2>Izvestaji o pregledima</h2>

	<br>

	<label for="izvestajni_period_pregledi">Izvestajni period: </label>

	<select name="izvestajni_period_pregledi" v-model="periodPregledi">
	<option value="godisnji">Godisnji nivo</option>
	<option value="kvartalni">Kvartalni nivo</option>
	<option value="mesecni">Mesecni nivo</option>
	</select>

	<div v-bind:hidden="periodPregledi == ''">
	<label>Godina: </label>
	<input type="number" placeholder="2021" v-model="yearPregledi">
	</div>

	<div v-bind:hidden="periodPregledi != 'kvartalni'">
	<label>Kvartal: </label>
	<input type="number" min="1" max="4" v-model="quarterPregledi"/>
	</div>

	<div v-bind:hidden="periodPregledi != 'mesecni'">
	<label>Mesec: </label>
	<input type="number" min="1" max="12" v-model="monthPregledi"/>
	</div>

	<br>

	<div id="graph">
    <div id="my_dataviz" v-bind:hidden="examination_data.length == 0"></div>
	</div>

	<input type="button" class="button1" value="Ucitaj" v-on:click="deleteAndLoadData()"/>

    <br>
	<br>
	<br>
	<br>

	<h2>Izvestaji o prihodima</h2>

	<br>

	<label for="izvestajni_period_prihodi">Izvestajni period: </label>

	<select name="izvestajni_period_prihodi" v-model="periodPrihodi">
	<option value="godisnji">Godisnji nivo</option>
	<option value="kvartalni">Kvartalni nivo</option>
	<option value="mesecni">Mesecni nivo</option>
	</select>

	<div v-bind:hidden="periodPrihodi == ''">
	<label>Godina: </label>
	<input type="number" placeholder="2021" v-model="yearPrihodi">
	</div>

	<div v-bind:hidden="periodPrihodi != 'kvartalni'">
	<label>Kvartal: </label>
	<input type="number" min="1" max="4" v-model="quarterPrihodi"/>
	</div>

	<div v-bind:hidden="periodPrihodi != 'mesecni'">
	<label>Mesec: </label>
	<input type="number" min="1" max="12" v-model="monthPrihodi"/>
	</div>

	<br>

	<div id="graph2">
    <div id="my_dataviz2" v-bind:hidden="income_data.length == 0"></div>
	</div>

	<input type="button" class="button1" value="Ucitaj" v-on:click="deleteAndLoadData()"/>

    <br>
	<br>
	<br>
	<br>

	<h2>Izvestaji o potrosnji lekova</h2>

	<br>

	<label for="izvestajni_period_potrosnja">Izvestajni period: </label>

	<select name="izvestajni_period_potrosnja" v-model="periodPotrosnja">
	<option value="godisnji">Godisnji nivo</option>
	<option value="kvartalni">Kvartalni nivo</option>
	<option value="mesecni">Mesecni nivo</option>
	</select>

	<div v-bind:hidden="periodPotrosnja == ''">
	<label>Godina: </label>
	<input type="number" placeholder="2021" v-model="yearPotrosnja">
	</div>

	<div v-bind:hidden="periodPotrosnja != 'kvartalni'">
	<label>Kvartal: </label>
	<input type="number" min="1" max="4" v-model="quarterPotrosnja"/>
	</div>

	<div v-bind:hidden="periodPotrosnja != 'mesecni'">
	<label>Mesec: </label>
	<input type="number" min="1" max="12" v-model="monthPotrosnja"/>
	</div>

	<br>

	<div id="graph3">
    <div id="my_dataviz3" v-bind:hidden="usage_data.length == 0"></div>
	</div>

	<input type="button" class="button1" value="Ucitaj" v-on:click="deleteAndLoadData()"/>

	</div>
	`
	,
    methods : {
		deleteAndLoadData: function() {
			{var to_remove = document.getElementById("my_dataviz");
			to_remove.remove();

			var parent = document.getElementById("graph");

			var to_append = document.createElement("div");
			var attribute1 = document.createAttribute("id");
			attribute1.value = "my_dataviz";

			var attribute2 = document.createAttribute("v-bind:hidden");
			attribute2.value = "examination_data.length == 0";

			to_append.setAttributeNode(attribute1);
			to_append.setAttributeNode(attribute2);
			parent.appendChild(to_append);}

			// ==========================================================

			{var to_remove = document.getElementById("my_dataviz2");
			to_remove.remove();

			var parent = document.getElementById("graph2");

			var to_append = document.createElement("div");
			var attribute1 = document.createAttribute("id");
			attribute1.value = "my_dataviz2";

			var attribute2 = document.createAttribute("v-bind:hidden");
			attribute2.value = "income_data.length == 0";

			to_append.setAttributeNode(attribute1);
			to_append.setAttributeNode(attribute2);
			parent.appendChild(to_append);}

			// ===========================================================

			{var to_remove = document.getElementById("my_dataviz3");
			to_remove.remove();

			var parent = document.getElementById("graph3");

			var to_append = document.createElement("div");
			var attribute1 = document.createAttribute("id");
			attribute1.value = "my_dataviz3";

			var attribute2 = document.createAttribute("v-bind:hidden");
			attribute2.value = "usage_data.length == 0";

			to_append.setAttributeNode(attribute1);
			to_append.setAttributeNode(attribute2);
			parent.appendChild(to_append);}

			this.loadData();
		},
        loadData: function() {
			if (this.periodPregledi == "godisnji") {
				axios
				.get("api/admin/yearlyExaminations/" + this.yearPregledi + "/" + this.apoteka.id)
				.then(response => {
					this.examination_data = response.data;
					this.initializeExaminations();
				});
			} else if (this.periodPregledi == "kvartalni") {
				axios
				.get("api/admin/quarterlyExaminations/" + this.yearPregledi + "/" + this.quarterPregledi + "/" + this.apoteka.id)
				.then(response => {
					this.examination_data = response.data;
					this.initializeExaminations();
				});
			} else if (this.periodPregledi == "mesecni") {
				axios
				.get("api/admin/monthlyExaminations/" + this.yearPregledi + "/" + this.monthPregledi + "/" + this.apoteka.id)
				.then(response => {
					this.examination_data = response.data;
					this.initializeExaminations();
				});
			}

			// ======================================================================================================================

			if (this.periodPrihodi == "godisnji") {
				axios
				.get("api/admin/yearlyIncome/" + this.yearPrihodi + "/" + this.apoteka.id)
				.then(response => {
					this.income_data = response.data;
					this.initializeIncomes();
				});
			} else if (this.periodPrihodi == "kvartalni") {
				axios
				.get("api/admin/quarterlyIncome/" + this.yearPrihodi + "/" + this.quarterPrihodi + "/" + this.apoteka.id)
				.then(response => {
					this.income_data = response.data;
					this.initializeIncomes();
				});
			} else if (this.periodPrihodi == "mesecni") {
				axios
				.get("api/admin/monthlyIncome/" + this.yearPrihodi + "/" + this.monthPrihodi + "/" + this.apoteka.id)
				.then(response => {
					this.income_data = response.data;
					this.initializeIncomes();
				});
			}

			// ======================================================================================================================

			if (this.periodPotrosnja == "godisnji") {
				axios
				.get("api/admin/yearlyDrugsUsage/" + this.yearPotrosnja + "/" + this.apoteka.id)
				.then(response => {
					this.usage_data = response.data;
					this.initializeUsage();
				});
			} else if (this.periodPotrosnja == "kvartalni") {
				axios
				.get("api/admin/quarterlyDrugsUsage/" + this.yearPotrosnja + "/" + this.quarterPotrosnja + "/" + this.apoteka.id)
				.then(response => {
					this.usage_data = response.data;
					this.initializeUsage();
				});
			} else if (this.periodPotrosnja == "mesecni") {
				axios
				.get("api/admin/monthlyDrugsUsage/" + this.yearPotrosnja + "/" + this.monthPotrosnja + "/" + this.apoteka.id)
				.then(response => {
					this.usage_data = response.data;
					this.initializeUsage();
				});
			}
		},
		initializeExaminations: function() {
			var margin = {top: 30, right: 30, bottom: 70, left: 60},
		    width = 460 - margin.left - margin.right,
		    height = 400 - margin.top - margin.bottom;

			// append the svg object to the body of the page
			var svg = d3.select("#my_dataviz")
			  .append("svg")
			    .attr("width", width + margin.left + margin.right)
			    .attr("height", height + margin.top + margin.bottom)
			  .append("g")
			    .attr("transform",
			          "translate(" + margin.left + "," + margin.top + ")");
			
			// X axis
			var x = d3.scaleBand()
			  .range([ 0, width ])
			  .domain(this.examination_data.map(function(d) { return d.timePeriod; }))
			  .padding(0.2);
			svg.append("g")
			  .attr("transform", "translate(0," + height + ")")
			  .call(d3.axisBottom(x))
			  .selectAll("text")
			    .attr("transform", "translate(-10,0)rotate(-45)")
			    .style("text-anchor", "end");
			
				var max_element = Math.max(...this.examination_data.map(item => item.value));
				var upper_limit = (max_element > 10) ? max_element : 10;
	
			// Add Y axis
			var y = d3.scaleLinear()
			  .domain([0, upper_limit])
			  .range([height, 0]);
			svg.append("g")
			  .call(d3.axisLeft(y));
	
			
			// Bars
			svg.selectAll("mybar")
			  .data(this.examination_data)
			  .enter()
			  .append("rect")
			    .attr("x", function(d) { return x(d.timePeriod); })
			    .attr("y", function(d) { return y(d.value); })
			    .attr("width", x.bandwidth())
			    .attr("height", function(d) { return height - y(d.value); })
			    .attr("fill", "#69b3a2")
		},
		initializeIncomes: function() {
			var margin = {top: 30, right: 30, bottom: 70, left: 60},
		    width = 460 - margin.left - margin.right,
		    height = 400 - margin.top - margin.bottom;

			// append the svg object to the body of the page
			var svg = d3.select("#my_dataviz2")
			  .append("svg")
			    .attr("width", width + margin.left + margin.right)
			    .attr("height", height + margin.top + margin.bottom)
			  .append("g")
			    .attr("transform",
			          "translate(" + margin.left + "," + margin.top + ")");
			
			// X axis
			var x = d3.scaleBand()
			  .range([ 0, width ])
			  .domain(this.income_data.map(function(d) { return d.timePeriod; }))
			  .padding(0.2);
			svg.append("g")
			  .attr("transform", "translate(0," + height + ")")
			  .call(d3.axisBottom(x))
			  .selectAll("text")
			    .attr("transform", "translate(-10,0)rotate(-45)")
			    .style("text-anchor", "end");
			
				var max_element = Math.max(...this.income_data.map(item => item.value));
				var upper_limit = (max_element > 10) ? max_element : 10;
	
			// Add Y axis
			var y = d3.scaleLinear()
			  .domain([0, upper_limit])
			  .range([height, 0]);
			svg.append("g")
			  .call(d3.axisLeft(y));
	
			
			// Bars
			svg.selectAll("mybar")
			  .data(this.income_data)
			  .enter()
			  .append("rect")
			    .attr("x", function(d) { return x(d.timePeriod); })
			    .attr("y", function(d) { return y(d.value); })
			    .attr("width", x.bandwidth())
			    .attr("height", function(d) { return height - y(d.value); })
			    .attr("fill", "#69b3a2")
		},
		initializeUsage: function() {
			var margin = {top: 30, right: 30, bottom: 70, left: 60},
		    width = 460 - margin.left - margin.right,
		    height = 400 - margin.top - margin.bottom;

			// append the svg object to the body of the page
			var svg = d3.select("#my_dataviz3")
			  .append("svg")
			    .attr("width", width + margin.left + margin.right)
			    .attr("height", height + margin.top + margin.bottom)
			  .append("g")
			    .attr("transform",
			          "translate(" + margin.left + "," + margin.top + ")");
			
			// X axis
			var x = d3.scaleBand()
			  .range([ 0, width ])
			  .domain(this.usage_data.map(function(d) { return d.timePeriod; }))
			  .padding(0.2);
			svg.append("g")
			  .attr("transform", "translate(0," + height + ")")
			  .call(d3.axisBottom(x))
			  .selectAll("text")
			    .attr("transform", "translate(-10,0)rotate(-45)")
			    .style("text-anchor", "end");
			
				var max_element = Math.max(...this.usage_data.map(item => item.value));
				var upper_limit = (max_element > 10) ? max_element : 10;
	
			// Add Y axis
			var y = d3.scaleLinear()
			  .domain([0, upper_limit])
			  .range([height, 0]);
			svg.append("g")
			  .call(d3.axisLeft(y));
	
			
			// Bars
			svg.selectAll("mybar")
			  .data(this.usage_data)
			  .enter()
			  .append("rect")
			    .attr("x", function(d) { return x(d.timePeriod); })
			    .attr("y", function(d) { return y(d.value); })
			    .attr("width", x.bandwidth())
			    .attr("height", function(d) { return height - y(d.value); })
			    .attr("fill", "#69b3a2")
		},
	},
	mounted: function() {
		axios
		.get("api/users/currentUser")
		.then(response => {
            this.admin = response.data;
            axios
			.get("api/apoteke/admin/" + response.data.id)
			.then(response => {
				this.apoteka = response.data;
				this.loadData();
		  	});
        });
    }
});