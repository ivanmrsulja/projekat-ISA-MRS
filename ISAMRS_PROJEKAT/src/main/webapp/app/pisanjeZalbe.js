Vue.component("pisanje-zalbe", {
	data: function () {
		    return {
				i: 0
		    }
	},
	template: ` 
<div align = center style="width:75%">
		
		<h1>Stranica za pisanje zalbe</h1>
		
		Look! A button! Quick! Click on it!
	    <form name="asd">
	      <input type="button" value="Pritisni moju malenkost, sta moze da se desi..." id="butt" v-on:click="foo(2)">
	    </form>
	    <p id="txt"></p>
		
		<h4 style="color: lightgray">To be continued...</h4>	  
`
    ,
    methods: {
        foo (a){
			document.asd.butt.value=++this.i;
		  document.body.innerHTML="Muhahha, tremble at my skills in the arcane arts, puny human! Your precious website is now no more!";
		}
    },
    mounted: function(){
        
    }
});