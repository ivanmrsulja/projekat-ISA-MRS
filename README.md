# projekat-ISA-MRS
Projektni zadatak iz predmeta Internet softverske arhitekture i Metodologije razvoja softvera.

# Projekat rade:
- Baćić Stefan   SW/68-2018 - 2
- Mršulja Ivan   SW/65-2018 - 1
- Marković Petar SW/73-2018 - 4
- Antić Dušan    SW/81-2019 - 3

## Uputstvo za pokretanje:

#### Važno:

* Potrebno је instalirati PostgreSQL bazu podataka. Možete је skinuti sa [linka](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads). Prilikom instaliranja, korisničko ime podesiti kao 'postgres' i izabrati proizvoljnu šifru (OBAVEZNO JE SAČUVATI !!!).   

* Neophodno je imati instaliranu Javu, najmanje verziju 11. Java je dostupna na [sajtu](https://www.oracle.com/java/technologies/javase-downloads.html) Oracle-a.
 
Nakon instaliranja PostgreSQL-a, potrebno je kreirati glavnu bazu koju koristi aplikacija u produkciji i bazu za pokretanje testova (dovoljna je i jedna instanca da bi radilo ali je dobra praksa razdvojiti).

To se radi na sledeći način:

- Pokrenuti PgAdmin, koji je instaliran zajedno sa bazom. Potrebno je uneti šifru kreiranu prilikom instalacije.

- Nakon toga potrebno je kreirati 2 baze sa proizvoljnim imenom.

- To se radi tako što se se desnim klikom klikne na Databases, koji se nalazi u tree view-u sa leve strane.

- Zatim će se otvoriti meni gde je moguće uneti imena baza.

Nakon toga, potrebno je u application.properties fajlovima unutar projekta uneti odgovarajuće kredencijale za bazu. Menjaju se sledeća polja:

- spring.datasource.url=jdbc:postgresql://localhost:5432/`IME BAZE`
- spring.datasource.username=`USERNAME` (ukoliko je username 'postgres' ovo polje ne dirati)
- spring.datasource.password=`PASSWORD`

Faljovi koje treba izmeniti se nalaze na putanjama:
- .\ISAMRS_PROJEKAT\src\test\resources
- .\ISAMRS_PROJEKAT\src\main\resources

Ako je sve dobro podešeno aplikacija je spremna za pokretanje koje možete obaviti na 2 načina:
- importovanjem foldera ISAMRS_PROJEKAT kao Maven projekta u Eclipse ili IntelliJ IDE, moguće je pokrenuti projekat jednim klikom na dugme Run.
- ukoliko imate instaliran Maven, pozicioniranjem u direktorijum ISAMRS_PROJEKAT i pokretanjem redom:
   `mvnw clean install -DskipTests=false -B` i
   `java -jar .\target\Projekat-0.0.1-SNAPSHOT.jar`
  možete pokrenuti aplikaciju.

NAPOMENA: Aplikacija trči na localhost:8080 po default-u.

Ukoliko Vam se ovo ipak ne radi, možete jednostavno poći na https://isamrs2021-tim6.herokuapp.com/ gdje je host-ovana naša aplikacija :) .
