CREATE TABLE SPASAVATELJ (
  ID            INT PRIMARY KEY AUTO_INCREMENT,
  IME           VARCHAR(50),
  BROJ_TELEFONA VARCHAR(15),
  SPECIJALNOST  INT,
  ISKUSTVO      INT,
  LOKACIJA_LAT  VARCHAR(15),
  LOKACIJA_LNG  VARCHAR(15),
  AKTIVAN       BOOL,
  USERNAME      VARCHAR(25),
  PWORD      VARCHAR(25),
  FB_TOKEN VARCHAR(100)
);

CREATE TABLE AKCIJA (
  ID            INT PRIMARY KEY AUTO_INCREMENT,
  VODITELJ_BROJ VARCHAR(15),
  LOKACIJA_LAT  VARCHAR(15),
  LOKACIJA_LNG  VARCHAR(15),
  RADIUS        DOUBLE,
  OPIS          VARCHAR(512),
  AKTIVNA       BOOL
);

CREATE TABLE SPASAVATELJ_AKCIJA (
  ID_SPASAVATELJ INT,
  ID_AKCIJA      INT,
  PRIHVATIO      BOOL,
  FOREIGN KEY (ID_SPASAVATELJ) REFERENCES PUBLIC.SPASAVATELJ (ID),
  FOREIGN KEY (ID_AKCIJA) REFERENCES PUBLIC.AKCIJA (ID)
);

INSERT INTO SPASAVATELJ
VALUES (1, 'Ante Filakovic', '0993246333', 1, 3, '45.815399', '15.966568', TRUE, 'afilakovic', '1234', '123'
);

INSERT INTO SPASAVATELJ
VALUES (2, 'Stefano Kliba', '0993246444', 2, 2, '45.815399', '15.966568', FALSE, 'skliba','1234', '123');

INSERT INTO SPASAVATELJ
VALUES (3, 'Mak Muftic', '0993246555', 3, 1, '45.815399', '15.966568', TRUE, 'mmuftic','1234', '123');

INSERT INTO AKCIJA
VALUES (1, '0993246333', '45.815399', '15.966568', 10, 'Super kul akcija, spasili smo 10 ljudi', TRUE);

INSERT INTO SPASAVATELJ_AKCIJA
VALUES (1, 1, TRUE);