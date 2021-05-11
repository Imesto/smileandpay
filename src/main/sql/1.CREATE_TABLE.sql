-- Script de création de 4 tables SQL
-- merchant
-- product
-- address
-- merchant_product



-- Création de la table merchant
CREATE TABLE merchant
(
	id INT NOT NULL,
	create_date TIMESTAMP NOT NULL,
	name VARCHAR(200) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	birthdate DATE NOT NULL,

	CONSTRAINT pk_merchant PRIMARY KEY (id)
);

-- Création de la table product
CREATE TABLE product
(
	id INT NOT NULL,
	create_date TIMESTAMP NOT NULL,
	label VARCHAR(200) NOT NULL,
	unit_price NUMERIC NOT NULL,
	currency VARCHAR(50) NOT NULL,
	weight NUMERIC NOT NULL,
	height NUMERIC NOT NULL,

	CONSTRAINT pk_product PRIMARY KEY (id)
);

-- Création de la table product
CREATE TABLE address
(
	id INT NOT NULL,
	number INT NOT NULL,
	street VARCHAR NOT NULL,
	zipcode VARCHAR NOT NULL,
	merchant_id INT NOT NULL,

	CONSTRAINT pk_address PRIMARY KEY (id),
	CONSTRAINT fk_merchant FOREIGN KEY (merchant_id)
						   REFERENCES merchant(id)
);


-- Création de la table de merchant_product
-- Pour l'association merchant n...n product
CREATE TABLE merchant_product
(
	merchant_id INT NOT NULL,
	product_id INT NOT NULL,
	create_date TIMESTAMP NOT NULL,

	CONSTRAINT pk_merchant_product PRIMARY KEY (merchant_id,product_id),
	CONSTRAINT fk_merchant FOREIGN KEY (merchant_id)
						   REFERENCES merchant(id),
	CONSTRAINT fk_product FOREIGN KEY (product_id)
						   REFERENCES product(id)
);


-- Création des sequences
-- On n'utilise INT à la place de SERIAL
-- Donc on va faire des sequences utilisables par hibernate pour l'auto increment
CREATE SEQUENCE merchant_id_seq INCREMENT 1;
CREATE SEQUENCE address_id_seq INCREMENT 1;
CREATE SEQUENCE product_id_seq INCREMENT 1;
