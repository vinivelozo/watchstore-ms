

DROP TABLE IF EXISTS inventories;

create table if not exists inventories (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    inventory_id VARCHAR(36),
    type VARCHAR(50)
    );

DROP TABLE IF EXISTS watches;

create table if not exists watches(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    reference_number VARCHAR(36),
    inventory_id VARCHAR(36),
    brand VARCHAR(36),
    model VARCHAR(36),
    color VARCHAR(36),
    year INTEGER,
    status VARCHAR(36)

    );

DROP TABLE IF EXISTS watch_features;

create table if not exists watch_features(
    reference_number VARCHAR(36),
    bracelet VARCHAR(36),
    diameter INTEGER,
    battery_type VARCHAR(36),
    price DECIMAL(19,4)
    );