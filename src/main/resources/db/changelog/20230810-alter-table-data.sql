--liquibase formatted sql

--changeset deepak:20230810

ALTER TABLE product_data
    ADD CONSTRAINT fk_product_data
    FOREIGN KEY (vendor_id)
    REFERENCES vendor_data(id);