--liquibase formatted sql

--changeset deepak:20230518

CREATE TABLE customer_data (
   id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   reference_id VARCHAR(255) NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE,
   updated_at TIMESTAMP WITHOUT TIME ZONE,
   version INTEGER,
   CONSTRAINT pk_demo PRIMARY KEY (id)
);