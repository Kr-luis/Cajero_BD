create database cajero 
use cajero
drop table usuarios
drop table transacciones
create table usuarios(
ID_CLI char(12) primary key,
NomCli char(30),
UserCli varchar(30),
ContraCli varchar(30),
SaldoCli Decimal(5,2)
);

create table transacciones(
ID_Transac char(12) primary key,
Tipo_Transac char(20),
Monto_Transac decimal(5,2),
Fecha_Transac date,
Hora_Transac time,
ID_CLI_fk char(7) references usuarios(ID_CLI)
);

INSERT INTO usuarios (ID_CLI, NomCli, UserCli, ContraCli, SaldoCli) 
VALUES 
('1750833681','Luis', 'Kairos', '123456','300'),
('1637846251','Felipe', 'White', '345678','300'),
('1128466532','Juan', 'Walter', '234567','300');

