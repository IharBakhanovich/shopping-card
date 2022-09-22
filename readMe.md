Für die Aufgabe können beliebige Technologien verwendet werden. Favorisiert werden fürs Backend Java und NodeJS.
Warenkorb

Aufgabe ist es, einen rudimentären Warenkorb zu implementieren. Es reicht aus, wenn die Funktionalitäten über eine API angesprochen werden.

Folgende User Stories repräsentieren die Anforderungen an das Produkt:

Als Kunde möchte ich einen Artikel in einer freien Anzahl (Ganzzahl) in den Warenkorb legen, um diesen später bestellen zu können.

- Ist Anzahl > Bestand des Artikels, wird der Artikel nicht in den Warenkorb gelegt

- Ist Anzahl < Mindestanzahl des Artikels, wird der Artikel in der Mindestanzahl in den Warenkorb gelegt
- Wird ein Artikel in den Warenkorb gelegt, der sich bereits im Warenkorb befindet, wird die Anzahl des Artikels im Warenkorb um die
hinzugefügte Anzahl, wenn möglich, erhöht

Als Kunde möchte ich meinen Warenkorb anhand der Warenkorb-ID anzeigen (ausgeben), um den Inhalt überprüfen zu können.
- Die Ausgabe enthält alle Positionen (Artikel, Anzahl, Preis)
- Die Ausgabe enthält einen Gesamtpreis. Dieser wird aus der Summer der Positionspreisen berechnet

Optional:
- Als Kunde möchte ich einen Artikel aus dem Warenkorb löschen
- Als Kunde möchte ich den kompletten Warenkorb leeren können