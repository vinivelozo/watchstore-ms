
DROP TABLE IF EXISTS employees;

create table if not exists employees (
    id SERIAL,
    employee_id VARCHAR(36),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(50),
    salary DECIMAL(19,4),
    job_title VARCHAR(36),
    street VARCHAR (50),
    city VARCHAR (50),
    province VARCHAR (50),
    country VARCHAR (50),
    postal_code VARCHAR (9),
    status VARCHAR(50),
    PRIMARY KEY (id)
    );


DROP TABLE IF EXISTS employee_phonenumbers;

create table if not exists employee_phonenumbers (
    employee_id INTEGER,
    type VARCHAR(50),
    number VARCHAR(50)
    );

