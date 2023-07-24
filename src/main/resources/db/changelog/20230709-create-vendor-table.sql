--liquibase formatted sql

--changeset deepak:20230709

CREATE TABLE vendor_data (
   id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   reference_id VARCHAR(255) NOT NULL,
   name VARCHAR(255) NOT NULL,
   description TEXT,
   img_url VARCHAR(255),
   phone_number VARCHAR(255) UNIQUE NOT NULL,
   email VARCHAR(255),
   address TEXT NOT NULL,
   status VARCHAR(255),
   cook_list TEXT,
   feedback TEXT,
   created_at TIMESTAMP WITHOUT TIME ZONE,
   updated_at TIMESTAMP WITHOUT TIME ZONE,
   version INTEGER,
   CONSTRAINT pk_vendor_data PRIMARY KEY (id)
);