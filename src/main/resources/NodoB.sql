DROP DATABASE IF EXISTS inversiones;

CREATE DATABASE inversiones;

USE inversiones;

CREATE TABLE inversionista (  
    rfcinversionista CHAR(13) NOT NULL,
    nombreinversionista CHAR(50) NOT NULL,
    telefonoinversionista CHAR(10) NOT NULL,
    direccioninversionista CHAR(30) NOT NULL,
    emailinverionista CHAR(50) NOT NULL,
    tipoPersona BOOLEAN NOT NULL,
    informacionsucursal CHAR(1) NOT NULL,
    PRIMARY KEY (rfcinversionista)
);	

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
    clvpagare CHAR (6) NOT NULL,
    CHECK (clvpagare REGEXP '^PB[0-9]{4}$'),
    clvcontrato CHAR (5) NOT NULL,
    CHECK (clvcontrato REGEXP '^CB[0-9]{3}$'),
    tipotasa CHAR (1) NOT NULL,
    fechaemision DATE NOT NULL,
    fechavencimiento DATE NOT NULL,
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

INSERT INTO tasa (tipotasa, interesbruto, issretenido, interesNeto)
VALUES ('A',0.5,0.15,0.20);

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
