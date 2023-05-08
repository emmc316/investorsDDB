DROP DATABASE IF EXISTS inversiones;

CREATE DATABASE inversiones;

USE inversiones;

CREATE TABLE sucursal (
clvsucursal CHAR(1) NOT NULL,
nombresucursal CHAR(40) NOT NULL,
ubicacionsucursal CHAR(40) NOT NULL,
encargadosucursal CHAR(40) NOT NULL,
telefonosucursal CHAR(10) NOT NULL,
PRIMARY KEY (clvsucursal)
);

CREATE TABLE contrato (
clvcontrato CHAR(5) NOT NULL,
CHECK (clvcontrato REGEXP '^CC[0-9]{3}$'),
clvsucursal CHAR(1) NOT NULL,
rfcinversionista CHAR(13) NOT NULL,
montototal DOUBLE NOT NULL,
status BOOLEAN NOT NULL,
PRIMARY KEY (clvcontrato),
CONSTRAINT FK_clvSucursal FOREIGN KEY (clvsucursal)
   REFERENCES sucursal(clvsucursal)
);

CREATE TABLE pagare (
clvpagare CHAR (6) NOT NULL,
CHECK (clvpagare REGEXP '^PC[0-9]{4}$'),
clvcontrato CHAR (5) NOT NULL,
CHECK (clvcontrato REGEXP '^CC[0-9]{3}$'),
tipotasa CHAR (1) NOT NULL,
fechaemision DATE NOT NULL,
fechavencimiento DATE NOT NULL,
PRIMARY KEY (clvpagare),
CONSTRAINT FK_clvContrato FOREIGN KEY (clvcontrato)
    REFERENCES contrato(clvcontrato)
);

INSERT INTO sucursal (clvsucursal, nombresucursal, ubicacionsucursal, encargadosucursal, telefonosucursal)
VALUES ('C', 'Sucursal-C','Calle Violetas #701, Flores', 'Antonio Parra Chaparro', '2291380193');

INSERT INTO contrato (clvcontrato, clvsucursal, rfcinversionista, montototal, status)
VALUES  ('CC001','C','NAPI990201N21',8765.00,true),
        ('CC002','C','NAPI990201N22',9312.00,true),
        ('CC003','C','NAPI990201N23',7532.00,true),
        ('CC004','C','NAPI990201N24',8123.00,true),
        ('CC005','C','NAPI990201N25',9420.00,true),
        ('CC006','C','NAPI990201N26',7290.00,true),
        ('CC007','C','NAPI990201N27',8541.00,true),
        ('CC008','C','NAPI990201N28',7061.00,true),
        ('CC009','C','NAPI990201N29',7777.00,true),
        ('CC010','C','NAPI990201N30',8100.00,true);

INSERT INTO pagare (clvpagare, clvcontrato, tipotasa, fechaemision, fechavencimiento)
VALUES  ('PC0001','CC001','A','2022-09-01','2023-08-31'),
        ('PC0002','CC002','A','2022-09-15','2023-09-14'),
        ('PC0003','CC003','A','2022-10-01','2023-09-30'),
        ('PC0004','CC004','A','2022-10-15','2023-10-14'),
        ('PC0005','CC005','A','2022-11-01','2023-10-31'),
        ('PC0006','CC006','A','2022-11-15','2023-11-14'),
        ('PC0007','CC007','A','2022-12-01','2023-11-30'),
        ('PC0008','CC008','A','2022-12-15','2023-12-14'),
        ('PC0009','CC009','A','2023-01-01','2023-12-31'),
        ('PC0010','CC010','A','2023-01-15','2024-01-14'),
        ('PC0011','CC007','A','2023-02-01','2024-01-31'),
        ('PC0012','CC001','A','2023-02-15','2024-02-14'),
        ('PC0013','CC004','A','2023-03-01','2024-02-29'),
        ('PC0014','CC005','A','2023-03-15','2024-03-14'),
        ('PC0015','CC001','A','2023-04-01','2024-03-31');
