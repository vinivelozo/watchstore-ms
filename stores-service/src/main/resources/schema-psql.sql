

DROP TABLE IF EXISTS stores;

create table if not exists stores (
    id SERIAL,
    store_id VARCHAR(36),
    email VARCHAR(50),
    street VARCHAR(50),
    city VARCHAR(50),
    province VARCHAR(50),
    country VARCHAR(50),
    postal_code VARCHAR(9),
    PRIMARY KEY (id)
    );

DROP TABLE IF EXISTS store_phonenumbers;

create table if not exists store_phonenumbers (
    store_id INTEGER,
    type VARCHAR(50),
    number VARCHAR(50)
    );
