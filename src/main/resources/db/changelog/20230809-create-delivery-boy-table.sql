--liquibase formatted sql

--changeset deepak:20230809

CREATE TABLE delivery_boy (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    reference_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    img_url VARCHAR(255),
    pan_number VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    address TEXT,
    feedback TEXT,
    status VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version INTEGER,
    CONSTRAINT pk_delivery_boy_data PRIMARY KEY (id)
);