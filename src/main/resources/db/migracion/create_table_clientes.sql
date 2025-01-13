CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NULL,
    apellido_paterno VARCHAR(50) NULL,
    apellido_materno VARCHAR(50) NULL,
    edad INT NULL,
    fecha_nacimiento DATE NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);