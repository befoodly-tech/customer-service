--liquibase formatted sql

--changeset deepak:20230810

ALTER TABLE product
    ADD CONSTRAINT fk_product_data
    FOREIGN KEY (vendor_id)
    REFERENCES vendor(id);