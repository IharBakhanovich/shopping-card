CREATE TABLE app_user
(
    id       INTEGER            NOT NULL AUTO_INCREMENT,
    nickName VARCHAR(30) UNIQUE NOT NULL,
    role     INTEGER            NOT NULL,
    password VARCHAR(64),
    PRIMARY KEY (id)
);
CREATE TABLE article
(
    id     INTEGER NOT NULL AUTO_INCREMENT,
    preis  FLOAT NOT NULL,
    amount INTEGER NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE user_ordered_article
(
    userId    INTEGER NOT NULL REFERENCES app_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    articleId INTEGER NOT NULL REFERENCES article (id) ON DELETE CASCADE ON UPDATE CASCADE,
    amount    INTEGER NOT NULL,
    PRIMARY KEY (userId, articleId)
);
