DROP DATABASE IF EXISTS inversionesr;

CREATE DATABASE inversionesr;

USE inversionesr;

CREATE TABLE inversionista (  
   rfcinversionista CHAR(13),
   nombreinversionista CHAR(80),
   telefonoinversionista CHAR(10),
   CHECK (telefonoinversionista REGEXP '^[0-9]{10}$'),
   direccioninversionista CHAR(80),
   emailinverionista CHAR(80),
   tipoPersona BOOLEAN,
   informacionsucursal CHAR(1),
   CHECK (informacionsucursal REGEXP '^[A-C]{1}$'),
   PRIMARY KEY (rfcinversionista)
);	

CREATE TABLE sucursal (
   clvsucursal CHAR(1),
   CHECK (clvsucursal REGEXP '^[A]{1}$'),
   nombresucursal CHAR(80),
   ubicacionsucursal CHAR(80),
   encargadosucursal CHAR(80),
   telefonosucursal CHAR(10),
   CHECK (telefonosucursal REGEXP '^[0-9]{10}$'),
   PRIMARY KEY (clvsucursal)
);

CREATE TABLE contrato (
   clvcontrato INT,
   clvsucursal CHAR(1),
   CHECK (clvsucursal REGEXP '^[A]{1}$'),
   rfcinversionista CHAR(13),
   montototal DOUBLE,
   status BOOLEAN,
   PRIMARY KEY (clvcontrato),
   CONSTRAINT FK_InversionistaRFC FOREIGN KEY (rfcinversionista)
      REFERENCES inversionista(rfcinversionista),
   CONSTRAINT FK_clvSucursal FOREIGN KEY (clvsucursal)
      REFERENCES sucursal(clvsucursal)
);
 
CREATE TABLE tasa (
   tipotasa CHAR(1) NOT NULL,
   interesbruto DOUBLE NOT NULL,
   issretenido DOUBLE NOT NULL,
   interesNeto DOUBLE NOT NULL,
   PRIMARY KEY (tipotasa)
);
 
CREATE TABLE pagare (
   clvpagare INT,
   clvcontrato INT,
   tipotasa CHAR (1),
   fechaemision DATE,
   fechavencimiento DATE,
   PRIMARY KEY (clvpagare),
   CONSTRAINT FK_clvContrato FOREIGN KEY (clvcontrato)
       REFERENCES contrato(clvcontrato),
    CONSTRAINT FK_TipoTasa FOREIGN KEY (tipotasa)
       REFERENCES tasa(tipotasa)
);

INSERT INTO inversionista (rfcinversionista, nombreinversionista, telefonoinversionista, direccioninversionista, emailinverionista, tipoPersona, informacionsucursal)
VALUES  ('NAPI990201N01','Sofía García López','5551234567','Calle Manzana #123, Jardines','sofia.garcia@ejemplo.com',true,'A'),
        ('NAPI990201N02','Alejandro González Torres','5559876543','Calle Pera #456, Jardines','alejandro.gonzalez@ejemplo.com',true,'A'),
        ('NAPI990201N03','Valentina Hernández Pérez','5555551212','Calle Durazno #789, Jardines','valentina.hernandez@ejemplo.com',true,'A'),
        ('NAPI990201N04','Carlos Aguilar González','5552223333','Calle Limón #1011, Jardines','carlos.aguilar@ejemplo.com',true,'A'),
        ('NAPI990201N05','Natalia Ramírez Cruz','5554445555','Calle Uva #1213, Jardines','natalia.ramirez@ejemplo.com',true,'A'),
        ('NAPI990201N06','Diego Torres Díaz','5557778888','Calle Sandía #1415, Jardines','diego.torres@ejemplo.com',true,'A'),
        ('NAPI990201N07','Isabella Ortiz Fernández','5556667777','Calle Naranja #1617, Jardines','isabella.ortiz@ejemplo.com',true,'A'),
        ('NAPI990201N08','Juan Pérez Rodríguez','5553334444','Calle Mango #1819, Jardines','juan.perez@ejemplo.com',true,'A'),
        ('NAPI990201N09','Camila Gómez Sánchez','5551112222','Calle Fresa #2021, Jardines','camila.gomez@ejemplo.com',true,'A'),
        ('NAPI990201N10','Gabriel Soto Mendoza','5558889999','Calle Kiwi #2223, Jardines','gabriel.soto@ejemplo.com',true,'A'),
        ('NAPI990201N11','Lucía Fernández Vázquez','5550001111','Calle Plátano #2425, Jardines','lucia.fernandez@ejemplo.com',true,'B'),
        ('NAPI990201N12','Miguel Ángel Flores García','5556665555','Calle Papaya #2627, Jardines','miguel.flores@ejemplo.com',true,'B'),
        ('NAPI990201N13','Ana María Díaz Rodríguez','5554443333','Calle Melón #2829, Jardines','ana.diaz@ejemplo.com',true,'B'),
        ('NAPI990201N14','Daniel Pérez Moreno','5557779999','Calle Guayaba #3031, Jardines','daniel.perez@ejemplo.com',true,'B'),
        ('NAPI990201N15','Mariana Salazar Martínez','5551117777','Calle Piña #3233, Jardines','mariana.salazar@ejemplo.com',true,'B'),
        ('NAPI990201N16','Francisco Jiménez Sánchez','5558884444','Calle Toronja #3435, Jardines','francisco.jimenez@ejemplo.com',true,'B'),
        ('NAPI990201N17','Andrea Méndez Ruiz','5556662222','Calle Granada #3637, Jardines','andrea.mendez@ejemplo.com',true,'B'),
        ('NAPI990201N18','José Antonio García Ortiz','5553330000','Calle Mandarina #3839, Jardines','jose.garcia@ejemplo.com',true,'B'),
        ('NAPI990201N19','Alejandra López Castro','5552227777','Calle Cereza #4041, Jardines','alejandra.lopez@ejemplo.com',true,'B'),
        ('NAPI990201N20','Eduardo Torres Cruz','5554441111','Calle Ciruela #4243, Jardines','eduardo.torres@ejemplo.com',true,'B'),
        ('NAPI990201N21','Gabriela Vargas García','5557774444','Calle Zarzamora #4445, Jardines','gabriela.vargas@ejemplo.com',true,'C'),
        ('NAPI990201N22','Luis García Torres','5551113333','Calle Guanábana #4647, Jardines','luis.garcia@ejemplo.com',true,'C'),
        ('NAPI990201N23','Marisol Aguilar Sánchez','5552229999','Calle Mamey #4849, Jardines','marisol.aguilar@ejemplo.com',true,'C'),
        ('NAPI990201N24','Roberto Hernández Morales','5556664444','Calle Guayabo #5051, Jardines','roberto.hernandez@ejemplo.com',true,'C'),
        ('NAPI990201N25','Ana Karen Rodríguez González','5558881111','Calle Limonada #5253, Jardines','ana.rodriguez@ejemplo.com',true,'C'),
        ('NAPI990201N26','Gustavo Pérez Hernández','5553337777','Calle Níspero #5455, Jardines','gustavo.perez@ejemplo.com',true,'C'),
        ('NAPI990201N27','Mariana García Flores','5554448888','Calle Breva #5657, Jardines','mariana.garcia@ejemplo.com',true,'C'),
        ('NAPI990201N28','Adrián Martínez Vázquez','5557773333','Calle Pepino #5859, Jardines','adrian.martinez@ejemplo.com',true,'C'),
        ('NAPI990201N29','Fernanda González Ruiz','5552220000','Calle Grosella #6061, Jardines','fernanda.gonzalez@ejemplo.com',true,'C'),
        ('NAPI990201N30','David Torres González','5551118888','Calle Carambola #6263, Jardines','david.torres@ejemplo.com',true,'C');

INSERT INTO sucursal (clvsucursal, nombresucursal, ubicacionsucursal, encargadosucursal, telefonosucursal)
VALUES ('A', 'Sucursal-A','Calle Rosas #501, Flores', 'Eric Montalvo Cruz', '2294057624');

INSERT INTO contrato (clvcontrato, clvsucursal, rfcinversionista, montototal, status)
VALUES  (1,'A','NAPI990201N01',3500.00,true),
        (2,'A','NAPI990201N02',2801.00,true),
        (3,'A','NAPI990201N03',3900.00,true),
        (4,'A','NAPI990201N04',3205.00,true),
        (5,'A','NAPI990201N05',2100.00,true),
        (6,'A','NAPI990201N06',4500.00,true),
        (7,'A','NAPI990201N07',2600.00,true),
        (8,'A','NAPI990201N08',3700.00,true),
        (9,'A','NAPI990201N09',2201.00,true),
        (10,'A','NAPI990201N10',2300.00,true);

INSERT INTO tasa (tipotasa, interesbruto, issretenido, interesNeto)
VALUES ('A',0.5,0.15,0.20);

INSERT INTO pagare (clvpagare, clvcontrato, tipotasa, fechaemision, fechavencimiento)
VALUES  (1,1,'A','2022-01-01','2022-12-01'),
        (2,2,'A','2022-01-02','2022-12-02'),
        (3,3,'A','2022-01-03','2022-12-03'),
        (4,4,'A','2022-01-04','2022-12-04'),
        (5,5,'A','2022-01-05','2022-12-05'),
        (6,6,'A','2022-01-06','2022-12-06'),
        (7,7,'A','2022-01-07','2022-12-07'),
        (8,8,'A','2022-01-08','2022-12-08'),
        (9,9,'A','2022-01-09','2022-12-09'),
        (10,10,'A','2022-01-10','2022-12-10'),
        (11,7,'A','2022-01-11','2022-12-11'),
        (12,1,'A','2022-01-12','2022-12-12'),
        (13,4,'A','2022-01-13','2022-12-13'),
        (14,5,'A','2022-01-14','2022-12-14'),
        (15,1,'A','2022-01-15','2022-12-15');

DROP USER IF EXISTS nodoRA;
CREATE USER 'nodoRA'@'%' identified by '1234';
GRANT ALL PRIVILEGES ON inversionesr.* TO 'nodoRA'@'%';
FLUSH PRIVILEGES;
