DROP DATABASE IF EXISTS inversiones;

CREATE DATABASE inversiones;
/*
CREATE USER 'dba'@'%' IDENTIFIED BY '12345';
GRANT ALL PRIVILEGES ON *.* TO 'dba'@'%';

--CREATE ROLE ejecutivo;
--GRANT SELECT, INSERT, UPDATE, DELETE ON inversiones.inversionista TO ejecutivo;
--GRANT SELECT, INSERT, UPDATE, DELETE ON inversiones.contrato TO ejecutivo;
--GRANT SELECT, INSERT, UPDATE, DELETE ON inversiones.pagare TO ejecutivo;
--GRANT SELECT, INSERT, UPDATE, DELETE ON inversiones.tasa TO ejecutivo;

/*DELIMITER $$
CREATE FUNCTION crearEjecutivo(rfcejecutivo CHAR(13), password CHAR(5))
RETURNS BOOLEAN
BEGIN
    DECLARE usuario_existe INT;
    SELECT COUNT(*) INTO usuario_existe FROM mysql.user WHERE user = rfcejecutivo;
    IF usuario_existe = 0 THEN
        CREATE USER rfcejecutivo@'%' IDENTIFIED BY password;
        GRANT ejecutivo TO rfcejecutivo@'%';
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END $$
DELIMITER;


CREATE ROLE cliente;
GRANT SELECT ON inversiones.inversionista TO cliente;
GRANT SELECT ON inversiones.contrato TO cliente;
GRANT SELECT ON inversiones.pagare TO cliente;


DELIMITER $$
CREATE FUNCTION crearCliente(rfcinversionista CHAR(13), password CHAR(5))
RETURNS BOOLEAN
BEGIN
    DECLARE usuario_existe INT;
    SELECT COUNT(*) INTO usuario_existe FROM mysql.user WHERE user = rfcinversionista;
    IF usuario_existe = 0 THEN
        CREATE USER rfcinversionista@'%' IDENTIFIED BY password;
        GRANT cliente TO rfcinversionista@'%';
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END $$
DELIMITER ;
*/
USE inversiones;


CREATE TABLE inversionista (  
  rfcinversionista CHAR(13) NOT NULL,
  nombreinversionista CHAR(50) NOT NULL,
  telefonoinversionista CHAR(10) NOT NULL,
  direccioninversionista CHAR(30) NOT NULL,
  emailinverionista CHAR(20) NOT NULL,
  tipoPersona BOOLEAN NOT NULL,
  PRIMARY KEY (rfcinversionista)
);	
 CREATE TABLE surcursal (
clvsucursal CHAR(1) NOT NULL,
nombresucursal CHAR(40) NOT NULL,
ubicacionsucursal CHAR(40) NOT NULL,
encargadosucursal CHAR(40) NOT NULL,
telefonosucursal CHAR(10) NOT NULL,
PRIMARY KEY (clvsucursal)
);

CREATE TABLE contrato (
clvcontrato CHAR(4) NOT NULL,
clvsucursal CHAR(1) NOT NULL,
rfcinversionista CHAR(13) NOT NULL,
montototal DOUBLE NOT NULL,
status BOOLEAN NOT NULL,
PRIMARY KEY (clvcontrato),
CONSTRAINT FK_InversionistaRFC FOREIGN KEY (rfcinversionista)
   REFERENCES inversionista(rfcinversionista),
CONSTRAINT FK_clvSucursal FOREIGN KEY (clvsucursal)
   REFERENCES surcursal(clvsucursal)
);
 

 
CREATE TABLE tasa (
tipotasa CHAR(1) NOT NULL,
interesbruto CHAR NOT NULL,
issretenido DOUBLE NOT NULL,
interesNeto DOUBLE NOT NULL,
PRIMARY KEY (tipotasa)
);
 
CREATE TABLE pagare (
clvpagare CHAR (5) NOT NULL,
clvcontrato CHAR (4) NOT NULL,
clvsucursal CHAR (1) NOT NULL,
tipotasa CHAR (1) NOT NULL,
fechaemision DATE NOT NULL,
fechavencimiento DATE NOT NULL,
PRIMARY KEY (clvpagare),
CONSTRAINT FK_clvContrato FOREIGN KEY (clvcontrato)
    REFERENCES contrato(clvcontrato),
 CONSTRAINT FK_TipoTasa FOREIGN KEY (tipotasa)
    REFERENCES tasa(tipotasa)
);
