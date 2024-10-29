DROP TABLE IF EXISTS sales;

create table if not exists sales(
    id SERIAL,
    sale_id VARCHAR(36) UNIQUE,
    inventory_id VARCHAR(36),
    reference_number VARCHAR(36),
    store_id VARCHAR(36),
    employee_id VARCHAR(36),
    sale_status VARCHAR(50),
    PRIMARY KEY (id)
    );