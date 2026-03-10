# Aplicație Airways Route Management System

Airways Route Management System

Această aplicație este un sistem de management al punctelor de trecere aeriene (Waypoints) și al rutelor de zbor. Permite crearea, salvarea, listarea și vizualizarea pe hartă a punctelor geografice, folosind un sistem de stocare bazat pe fișiere JSON.

Arhitectură și Design Patterns

Proiectul respectă bunele practici de programare orientată pe obiecte și separarea responsabilităților:

    Model Layer (Waypoint): Definește entitatea de bază cu atribute precum latitudine, longitudine și altitudine.

    Repository Layer (IWaypointRepository): Gestionează persistența datelor. Implementarea WaypointFileJsonRepository transformă obiectele Java în fișiere .json folosind biblioteca Jackson.

    Service Layer (WaypointService): Conține logica de business și face legătura între interfață și baza de date (fișiere).

    Manager Layer (RouteManager): Specializat în operațiuni complexe pe rute, cum ar fi încărcarea unei rute întregi dintr-un folder și calcularea distanțelor.

Funcționalități Principale

    Gestiune Rute: Organizarea waypoint-urilor în subfoldere specifice fiecărei rute (ex: LRCL-TASOD).

    Persistență JSON: Salvarea automată a punctelor în format JSON pentru interoperabilitate.

    Calcul Distanțe (Haversine): Implementarea formulei Haversine pentru a calcula distanța reală în kilometri între coordonate geografice, ținând cont de curbura Pământului.

    Vizualizare Map: Integrare cu JXMapViewer pentru a afișa rutele de zbor pe o hartă interactivă.

    Tratarea Excepțiilor: Sistem personalizat de erori (WaypointNotFoundException) pentru căutări eșuate.

Cum funcționează?

    Adăugare: Se creează puncte noi folosind WaypointService. Acestea sunt salvate imediat pe disk sub formă de fișier .json.

    Încărcare: RouteManager scanează folderele de rute și reconstruiește lista de obiecte Waypoint din fișierele găsite.

    Calcul: Sistemul parcurge lista de puncte și calculează distanța totală a zborului.

    Afișare: Aplicația deschide o fereastră grafică unde punctele sunt marcate pe coordonatele GPS corespunzătoare.

Tehnologii utilizate

    Java 8+

    Jackson Databind: Pentru procesarea formatului JSON.

    Lombok: Pentru reducerea codului boilerplate (getters/setters).

    JXMapViewer2: Pentru suportul de hărți OpenStreetMap.
