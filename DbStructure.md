1. Entity-Relationschip Diagramme (sieh die 'ER_Diagramm.JPG' Datei):
    ![](C:\Studium\Interviews\ShoppingCard\ER_diagramm.JPG)
2. Convertierung ER-Diagramm to Entity-Typen

    a. Konvertierung starker Entity-Typen

        user:[{_userId_: integer, role: integer, nickname: String, password: String}]

        article:[{_articleId_: integer, preis: float, amount: integer}]

    b. Konvertierung schwacher Entity-Typen

        entfällt

    c. Konvertierung der RelationShip-Typen

        orders:[{_userId_: integer, _articleId_: integer, amount: integer}]

    d. Konvertierung mehrwertiger Attribute

        entfällt

    e. Konvertierung der Generalisierung

        entfällt

    f. Verfeinerung des relationalen Schemas

        entfällt

3. Tabelle (sieh die 'tables.jpg' Datei)

    ![](C:\Studium\Interviews\ShoppingCard\tables.jpg)

4. SQL scripts

`CREATE TABLE user
(
id       INTEGER            NOT NULL AUTO_INCREMENT,
nickName VARCHAR(30) UNIQUE NOT NULL,
role     INTEGER            NOT NULL,
password VARCHAR(64),
primary key (id)
);`


`CREATE TABLE article
(
id     INTEGER NOT NULL AUTO_INCREMENT,
preis  FLOAT NOT NULL,
amount INTEGER NOT NULL,
primary key (id)
);`


`CREATE TABLE user_ordered_article
(
userId    INTEGER NOT NULL REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
articleId INTEGER NOT NULL REFERENCES article (id) ON DELETE CASCADE ON UPDATE CASCADE,
amount    INTEGER NOT NULL,
primary key (userId, articleId)
);`


