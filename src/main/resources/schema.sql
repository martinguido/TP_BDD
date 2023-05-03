DROP TABLE regiones;
-- DROP TABLE mediciones;
-- DROP TABLE feriados;
CREATE TABLE regiones  (
                         id INT PRIMARY KEY,
                         id_elemento INT,
                         id_padre INT,
                         id_rge INT,
                         nombre VARCHAR(255)
);
-- CREATE TABLE mediciones  (
--                            my_id BIGINT PRIMARY KEY,
--                            demanda DOUBLE PRECISION,
--                            fecha TIMESTAMP,
--                            temperatura DOUBLE PRECISION,
--                            id INT NOT NULL,
--                            FOREIGN KEY (id) REFERENCES regiones (id)
-- );
-- CREATE TABLE feriados  (
--                            fecha TIMESTAMP PRIMARY KEY,
--                            esferiado BOOLEAN
-- );