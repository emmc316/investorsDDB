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
CHECK (clvcontrato REGEXP '^CB[0-9]{3}$'),
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
CHECK (clvpagare REGEXP '^PB[0-9]{4}$'),
clvcontrato CHAR (5) NOT NULL,
CHECK (clvcontrato REGEXP '^CB[0-9]{3}$'),
tipotasa CHAR (1) NOT NULL,
fechaemision DATE NOT NULL,
fechavencimiento DATE NOT NULL,
PRIMARY KEY (clvpagare),
CONSTRAINT FK_clvContrato FOREIGN KEY (clvcontrato)
    REFERENCES contrato(clvcontrato)
);

INSERT INTO sucursal (clvsucursal, nombresucursal, ubicacionsucursal, encargadosucursal, telefonosucursal)
VALUES ('B', 'Sucursal-B','Calle Girasol #601, Flores', 'Irving Naranjo Paredes', '2294523853');

INSERT INTO contrato (clvcontrato, clvsucursal, rfcinversionista, montototal, status)
VALUES  ('CB001','B','NAPI990201N11',6789.00,true),
        ('CB002','B','NAPI990201N12',7890.00,true),
        ('CB003','B','NAPI990201N13',8901.00,true),
        ('CB004','B','NAPI990201N14',9012.00,true),
        ('CB005','B','NAPI990201N15',10123.00,true),
        ('CB006','B','NAPI990201N16',11111.00,true),
        ('CB007','B','NAPI990201N17',12345.00,true),
        ('CB008','B','NAPI990201N18',13579.00,true),
        ('CB009','B','NAPI990201N19',14785.00,true),
        ('CB010','B','NAPI990201N20',15987.00,true);

INSERT INTO pagare (clvpagare, clvcontrato, tipotasa, fechaemision, fechavencimiento)
VALUES  ('PB0001','CB001','A','2022-01-16','2022-12-16'),
        ('PB0002','CB002','A','2022-01-17','2022-12-17'),
        ('PB0003','CB003','A','2022-01-18','2022-12-18'),
        ('PB0004','CB004','A','2022-01-19','2022-12-19'),
        ('PB0005','CB005','A','2022-01-20','2022-12-20'),
        ('PB0006','CB006','A','2022-01-21','2022-12-21'),
        ('PB0007','CB007','A','2022-01-22','2022-12-22'),
        ('PB0008','CB008','A','2022-01-23','2022-12-23'),
        ('PB0009','CB009','A','2022-01-24','2022-12-24'),
        ('PB0010','CB010','A','2022-01-25','2022-12-25'),
        ('PB0011','CB007','A','2022-01-26','2022-12-26'),
        ('PB0012','CB001','A','2022-01-27','2022-12-27'),
        ('PB0013','CB004','A','2022-01-28','2022-12-28'),
        ('PB0014','CB005','A','2022-01-29','2022-12-29'),
        ('PB0015','CB001','A','2022-01-30','2022-12-30');
