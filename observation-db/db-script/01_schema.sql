-- Public schema
CREATE TABLE measurement (
    measurement_id SERIAL PRIMARY KEY,
    type VARCHAR(50),
    unit VARCHAR(50),
    CONSTRAINT type_unit_unique UNIQUE (type, unit)
);

CREATE TABLE observation (
    id SERIAL PRIMARY KEY,
    measurement_id INTEGER,
    date TIMESTAMP,
    patient INTEGER,
    value NUMERIC(8, 2),
    FOREIGN KEY(measurement_id) REFERENCES measurement(measurement_id)
);


-- Test schema
CREATE SCHEMA IF NOT EXISTS test;

CREATE TABLE test.measurement (
    measurement_id SERIAL PRIMARY KEY,
    type VARCHAR(50),
    unit VARCHAR(50),
    CONSTRAINT type_unit_unique UNIQUE (type, unit)
);

CREATE TABLE test.observation (
    id SERIAL PRIMARY KEY,
    measurement_id INTEGER,
    date TIMESTAMP,
    patient INTEGER,
    value NUMERIC(8, 2),
    FOREIGN KEY(measurement_id) REFERENCES test.measurement(measurement_id)
);