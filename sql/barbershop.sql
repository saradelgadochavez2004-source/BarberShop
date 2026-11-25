-- BarberShop: script de la base de datos (OPCIONAL).
-- El proyecto ya se conecta a la base en Aiven (ver Conexion.java), así que NO hace falta ejecutarlo para usar el sistema.
-- Sirve como respaldo o para montar la base en otro servidor MySQL (en ese caso cambia URL, usuario y clave en Conexion.java).
--
-- ATENCIÓN: este script BORRA y vuelve a crear las tablas usuarios, servicios y reservas.
-- En un MySQL propio, descomenta las dos líneas siguientes antes de ejecutarlo:
-- CREATE DATABASE IF NOT EXISTS barbershop CHARACTER SET utf8mb4;
-- USE barbershop;

DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS servicios;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
  id_usuario INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombre     VARCHAR(50)  NOT NULL,
  apellido   VARCHAR(50)  NOT NULL,
  correo     VARCHAR(100) NOT NULL UNIQUE,
  password   VARCHAR(255) NOT NULL,
  telefono   VARCHAR(15)  DEFAULT NULL,
  rol        VARCHAR(20)  NOT NULL DEFAULT 'BARBERO'
);

CREATE TABLE servicios (
  id_servicio  INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombre       VARCHAR(80)  NOT NULL,
  descripcion  VARCHAR(200) DEFAULT NULL,
  precio       DECIMAL(8,2) NOT NULL,
  duracion_min INT NOT NULL
);

CREATE TABLE reservas (
  id_reserva  INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  id_servicio INT NOT NULL,
  cliente     VARCHAR(100) NOT NULL,
  telefono    VARCHAR(20)  NOT NULL,
  fecha       DATE NOT NULL,
  hora        TIME NOT NULL,
  estado      VARCHAR(15)  NOT NULL DEFAULT 'PENDIENTE',
  FOREIGN KEY (id_servicio) REFERENCES servicios(id_servicio)
);

INSERT INTO servicios (id_servicio, nombre, descripcion, precio, duracion_min) VALUES
(1, 'Corte clásico',    'Corte a tijera o máquina con acabado a navaja', 25.00, 30),
(2, 'Arreglo de barba', 'Perfilado, recorte y toalla caliente',          20.00, 25),
(3, 'Corte + barba',    'El combo completo',                             40.00, 55),
(4, 'Afeitado clásico', 'Navaja y toalla caliente',                      22.00, 30);

-- Trabajadores (rol ADMIN); las contraseñas están guardadas con hash
INSERT INTO usuarios (id_usuario, nombre, apellido, correo, password, telefono, rol) VALUES
(2, 'Administrador', 'BarberShop', 'admin@barbershop.com', 'pbkdf2$120000$DQNZgXnbIUoSX5XtRWqHYA==$itlsQpbjLoykOso+gL/uBFmxz7hKY+clbWqEoxiWx3o=', '999888777', 'ADMIN'),
(3, 'Sara Nahomi', 'Delgado Chavez', 'saradc@barbershop.com', 'pbkdf2$120000$r19J1rQCaICyX0HTcOl/9Q==$rRZHT7QbxLw52ODfWERZGrB/JrLFmVMGz2P29KTOfGA=', NULL, 'ADMIN');