Vue.component("add-cure", {
	data: function () {
		    return {
		    	currentPosition: {lat: 45.252600, lon: 19.830002, adresa: "Cirpanova 51, Novi Sad"}
		    }
	},
	template: ` 
<div>
		<h1>Dodavanje leka: </h1>
		
		
		<div style="display: inline-block; margin-right: 50px">
		<table>
			<tr>
				<td> <h2>Naziv:</h2> </td> <td> <input type="text" name="naziv"/> </td>
			</tr>
			<tr>
				<td> <h2>Kontraindikacije:</h2> </td> <td> <input type="text" name="kontraind"/> </td>
			</tr>
			<tr>
				<td> <h2>Sastav:</h2> </td> <td> <input type="text" name="sastav"/> </td>
			</tr>
			<tr>
				<td> <h2>Preporuceni unos:</h2> </td> <td> <input type="number" name="prep"/> </td>
			</tr>
			<tr>
				<td> <h2>Oblik:</h2> </td> <td> <input type="text" name="oblik"/> </td>
			</tr>
			<tr>
				<td> <h2>Proizvodjac:</h2> </td> <td> <input type="text" name="proizvodjac"/> </td>
			</tr>
			<tr>
				<td> <h2>Rezim izdavanja:</h2> </td> <td> <select id="rezim">
														  <option value="BEZ_RECEPTA">Bez recepta</option>
														  <option value="NA_RECEPT">Na recept</option>
														  </select> 
													 </td>
			</tr>
			<tr>
				<td> <h2>Tip Leka:</h2> </td> <td> <select id="tip">
														  <option value="ANTIBIOTIK">Antibiotik</option>
														  <option value="ANESTETIK">Anestetik</option>
														  <option value="ANTIHISTAMINIK">Antihistaminik</option>
														  </select> 
													 </td>
			</tr>
			<tr>
				<td> <h2>Bodovi:</h2> </td> <td> <input type="number" name="bodovi"/> </td>
			</tr>
			<tr>
				<td align=center colspan=2> 
					<input value="Registruj se" type="button" name="regBtn" v-on:click="registerUser()"/> 
				</td>
			</tr>
		</table>
		</div>
		
		
</div>		  
`
	, 
	methods : {
		registerUser : function () {
			let naziv = $("input[name=naziv]").val();
			let bodovi = $("input[name=bodovi]").val();
			let kontraindikacije = $("input[name=kontraind]").val();
			let sastav = $("input[name=sastav]").val();
			let preporuceniUnos = $("input[name=prep]").val();
			let oblik = $("input[name=oblik]").val();
			let proizvodjac = $('input[name=proizvodjac]').val();
			let rezim = $("#rezim").children("option:selected").val();
			let tip = $("#tip").children("option:selected").val();
			toast(rezim + tip);
			if (naziv.trim() == "" || kontraindikacije.trim() == "" || sastav.trim() == "" || preporuceniUnos.trim() == "" || oblik.trim() == "" || proizvodjac.trim() == ""){
				toast("Popunite sva polja.");
				return;
			}
			let newCure = {"naziv": naziv, poeni:parseInt(bodovi), "kontraindikacije": kontraindikacije, "sastav": sastav, "preporuceniUnos" : preporuceniUnos, "oblik": oblik, "proizvodjac": proizvodjac, "rezim": rezim, "tip": tip, "ocena": 0.0};
			axios.post("/api/preparat/addCure", newCure).then(data => {
				if(data.data == "OK") {
					toast("Uspesno ste kreirali lek!");
				}
			});
		}
	},
	mounted () {
    
    }
});