-- Script de rollback de création de 4 tables SQL
-- merchant
-- product
-- address

-- Suppression de la table merchant_product

DROP TABLE IF EXISTS merchant_product;

-- Création de la table address

DROP TABLE IF EXISTS address;

-- Suppression de la table product

DROP TABLE IF EXISTS product;

-- Suppression de la table merchant

DROP TABLE IF EXISTS merchant;

-- Suppression des sequences

DROP SEQUENCE IF EXISTS address_id_seq;
DROP SEQUENCE IF EXISTS merchant_id_seq;
DROP SEQUENCE IF EXISTS product_id_seq;







