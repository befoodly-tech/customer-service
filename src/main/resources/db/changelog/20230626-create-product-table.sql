--liquibase formatted sql

--changeset deepak:20230619

CREATE TABLE product (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    reference_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    img_url TEXT,
    description TEXT,
    order_no INTEGER NOT NULL,
    price DOUBLE PRECISION,
    accepting_time TIMESTAMP NOT NULL,
    delivery_time TIMESTAMP NOT NULL,
    status VARCHAR(255),
    feedback VARCHAR(255),
    vendor_id BIGINT NOT NULL,
    provider_data VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version INTEGER,
    CONSTRAINT pk_product_data PRIMARY KEY (id)
);