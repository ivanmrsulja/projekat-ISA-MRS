const LoginStrana = { template: '<pocetna-strana></pocetna-strana>' };
const TabelaPonuda = { template: '<pocetna-stranas></pocetna-stranas>' };
const RegistracijaKorisnika = { template: '<register-user></register-user>' };
const PregledApoteka = { template: '<pregled-apoteka></pregled-apoteka>' };
const ProfilApoteke = { template: '<profil-apoteke></profil-apoteke>' };
const dermatoloziTable = { template: '<profil-dermatolozi></profil-dermatolozi>' };
const farmaceutiTable = { template: '<profil-farmaceuti></profil-farmaceuti>' };
const IstorijaPregleda = { template: '<istorija-pregleda></istorija-pregleda>' };
const ZakazaniPregledi = { template: '<zakazani-pregledi></zakazani-pregledi>' };
const ProfilPacijenta = { template: '<profil-pacijenta></profil-pacijenta>' };
const PregledRezervacija = { template: '<lista-rezervacija></lista-rezervacija>' };
const PregledErecepata = { template: '<pregled-erecepata></pregled-erecepata>' };
const PregledStavkiErecepta = { template: '<pojedinacni-erecept></pojedinacni-erecept>' };
const AkcijePromocije = { template: '<akcije-promocije></akcije-promocije>' };
const PregledApoteke = { template: '<pregled-apoteke></pregled-apoteke>' };
const preparatiTable = { template: '<profil-preparati></profil-preparati>' };
const PregledStavkiPreparata = { template: '<pojedinacni-preparat></pojedinacni-preparat>' };
const RegistracijaFarmaceuta = { template: '<register-pharmacist></register-pharmacist>' };
const registracijaDobavljaca = { template: '<register-supplier></register-supplier>' };
const updateDobavljaca = { template: '<update-supplier></update-supplier>' };
const registracijaDermatologa = { template: '<register-dermatolog></register-dermatolog>' };
const StranicaZalbe = { template: '<pisanje-zalbe></pisanje-zalbe>' };
const pacijentTable = { template: '<profil-pacijenti></profil-pacijenti>' };
const PregledPacijenta = { template: '<pojedinacni-pacijent></pojedinacni-pacijent>' };
const promenaSifre = { template: '<promena-sifre></promena-sifre>' };
const ZakazivanjeSavetovanja = { template: '<zakazivanje-savetovanja></zakazivanje-savetovanja>' };
const ZakazivanjeSavetovanjaKorak2 = { template: '<zakazivanje-savetovanjaK2></zakazivanje-savetovanjaK2>' };
const ProfilAdminaApoteke = { template: '<profil-admina-apoteke></profil-admina-apoteke>' };
const PretragaStrucnjaka = { template: '<pretraga-strucnjaka></pretraga-strucnjaka>' };
const PregledZahtevaFarmaceuta = { template: '<pregled-zahteva-farmaceuta></pregled-zahteva-farmaceuta>' };
const DefinicijaAkcijePromocije = { template: '<definicija-akcije-promocije></definicija-akcije-promocije>' };
const DefinicijaCenovnika = { template: '<definicija-cenovnika></definicija-cenovnika>' };
const PregledNarudzbenica = { template: '<pregled-narudzbenica></pregled-narudzbenica>' };
const PreparatiApoteke = { template: '<preparati-apoteke></preparati-apoteke>' };
const PisanjeNarudzbenice = { template: '<pisanje-narudzbenice></pisanje-narudzbenice>' };
const ZapocniPregled = { template: '<pacijent-pregled></pacijent-pregled>' };
const ZapocniNoviPregled = { template: '<zakazivanje-termina></zakazivanje-termina>' };
const RadniKalendar = { template: '<radni-kalendar></radni-kalendar>' };
const RadniKalendarFarmaceut = { template: '<radni-kalendar-farmaceut></radni-kalendar-farmaceut>' };
const dodajLek = { template: '<add-cure></add-cure>' };
const registracijaAdminaSistema = { template: '<register-adminsys></register-adminsys>' };
const OdabirPonude = { template: '<odabir-ponude></odabir-ponude>' };
const registracijaApoteke = { template: '<register-apoteka></register-apoteka>' };
const registracijaAdminaApoteke = { template: '<register-adminphar></register-adminphar>' };
const IzvestajiPregledi = { template: '<izvestaji-pregledi></izvestaji-pregledi>' };
const PregledNotifikacija = { template: '<pregled-notifikacija></pregled-notifikacija>' };
const KreiranjeTerminaPregleda = { template: '<kreiranje-termina-pregleda></kreiranje-termina-pregleda>' };
const GodisnjiOdmor = { template: '<zakazivanje-godisnjegOdmora></zakazivanje-godisnjegOdmora>' };
const DermatoloziAdminApoteke = { template: '<dermatolozi-admin-apoteke></dermatolozi-admin-apoteke>' };
const tabelaApoteka = { template: '<tabela-apoteka></tabela-apoteka>' };


const listaZalbi = { template: '<lista-zalbi></lista-zalbi>' };
const listaNarudzbenica = { template: '<lista-narudzbenica></lista-narudzbenica>' };
const jednaZalba = { template: '<jedna-zalba></jedna-zalba>' };
const jednaNarudzbenica = { template: '<jedna-narudzbenica></jedna-narudzbenica>' };
const listaZalbiAdmin = { template: '<lista-zalbiadmin></lista-zalbiadmin>' };
const jednaZalbaAdmin = { template: '<jedna-zalbaadmin></jedna-zalbaadmin>' };
const registracijaTipa = { template: '<register-type></register-type>' };
const sendQr = { template: '<send-qr></send-qr>' };
const RezervacijaLeka = { template: '<rezervacija-leka></rezervacija-leka>'};
const verifyAccount = {template: '<verify-account></verify-account>'};

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', component: LoginStrana },
        { path: '/tab', component: TabelaPonuda },
        { path: '/dermatolozi', component: dermatoloziTable },
        { path: '/pregledi', component: RadniKalendar },
        { path: '/savetovanja', component: RadniKalendarFarmaceut },
        { path: '/preparati', component: preparatiTable },
        { path: '/preparati/:spec', component: PregledStavkiPreparata },
        { path: '/farmaceuti', component: farmaceutiTable },
        { path: '/register', component: RegistracijaKorisnika },
        { path: '/apoteke/:page', component: PregledApoteka },
        { path: '/profileApoteke', component: ProfilApoteke, name: "ProfilApoteke" },
        { path: '/istorijaPregleda/:page/:criteria', component: IstorijaPregleda },
        { path: '/zakazaniPregledi/:page', component: ZakazaniPregledi },
        { path: '/profilPacijenta', component: ProfilPacijenta },
        { path: '/pregledRezervacija', component: PregledRezervacija },
        { path: '/eRecepti', component: PregledErecepata },
        { path: '/eRecepti/:recept', component: PregledStavkiErecepta },
        { path: '/apoteke/pregled/:id', component: PregledApoteke, name: "PregledApoteke" },
        { path: '/akcijePromocije', component: AkcijePromocije },
        { path: '/registracijaFarmaceuta', component: RegistracijaFarmaceuta },
        { path: '/regSupp', component: registracijaDobavljaca },
        { path: '/regAdminPharm', component: registracijaAdminaApoteke },
        { path: '/regDerm', component: registracijaDermatologa },
        { path: '/updateSupp', component: updateDobavljaca },
        { path: '/pacijenti', component: pacijentTable },
        { path: '/pacijenti/:spec', component: PregledPacijenta },
        { path: '/promeniSifru', component: promenaSifre },
        { path: '/zalbe', component: StranicaZalbe },
        { path: '/zakaziSavetovanje/:page/', component: ZakazivanjeSavetovanja },
        { path: '/zakaziSavetovanje/:page/:apoteka', component: ZakazivanjeSavetovanjaKorak2 },
        { path: '/noviLek', component: dodajLek },
        { path: '/regApoteka', component: registracijaApoteke },
        { path: '/pacijenti/zapocniPregled/:spec', component: ZapocniPregled },
        { path: '/registracijaAdminaSistema', component: registracijaAdminaSistema },
        { path: '/pacijenti/zapocniNoviPregled/:spec', component: ZapocniNoviPregled },
        { path: '/profilAdminaApoteke', component: ProfilAdminaApoteke },
        { path: '/pretragaStrucnjaka', component: PretragaStrucnjaka },
        { path: '/pregledZahtevaFarmaceuta', component: PregledZahtevaFarmaceuta },
        { path: '/definicijaAkcijePromocije', component: DefinicijaAkcijePromocije },
        { path: '/definicijaCenovnika', component: DefinicijaCenovnika },
        { path: '/pregledNarudzbenica', component: PregledNarudzbenica, name: "PregledNarudzbenica" },
        { path: '/preparatiApoteke', component: PreparatiApoteke, name: "PreparatiApoteke" },
        { path: '/pisanjeNarudzbenice', component: PisanjeNarudzbenice },
	    { path: '/tabelaApoteka/:id', component: tabelaApoteka },
        { path: '/odabirPonude/:id', component: OdabirPonude, name: "OdabirPonude" },
        { path: '/listaZalbi', component: listaZalbi },
        { path: '/jednaZalba/:zalId', component: jednaZalba, name: "jednaZalba" },
        { path: '/jednaNarudzbenica/:zalId', component: jednaNarudzbenica, name: "jednaNarudzbenica" },
        { path: '/verifikacija/:id', component: verifyAccount, name: "verifyAccount" },
        { path: '/listaZalbiAdmin', component: listaZalbiAdmin },
        { path: '/jednaZalbaAdmin/:zalId', component: jednaZalbaAdmin, name: "jednaZalbaAdmin" },
        { path: '/listaNarudzbenica', component: listaNarudzbenica },
        { path: '/registracijaTipa', component: registracijaTipa },
        { path: '/sendQr', component: sendQr },
        { path: '/godisnji', component: GodisnjiOdmor },
        { path: '/izvestajiPregledi', component: IzvestajiPregledi },
        { path: '/pregledNotifikacija', component: PregledNotifikacija },
        { path: '/kreiranjeTerminaPregleda', component: KreiranjeTerminaPregleda },
        { path: '/dermatoloziAdminApoteke', component: DermatoloziAdminApoteke },
        { path: '/rezervacijaLeka', component: RezervacijaLeka}
    ]
});




var app = new Vue({
    router,
    el: '#apoteke',
    data: {
        korisnik: { zaposlenjeKorisnika: "GOST" },
    },
    mounted() {
        let self = this;
        axios.get("/api/users/currentUser").then(function(data) {
            if (data.data) {
                self.korisnik = data.data;
            }
        });
        this.$root.$on('sendingUser', (data) => {
            this.korisnik = data;
        });
    },
    methods: {
        logout: function() {
            let self = this;
            axios
                .get("/api/users/logout")
                .then(function(resp) {
                    if (resp.data == "OK") {
                        self.korisnik = { zaposlenjeKorisnika: "GOST" };
                        self.$router.push({ path: "/" });
                        self.$root.$emit('loggingUserOut', self.korisnik);
                    }
                });
        }
    }
});