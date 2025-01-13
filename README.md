# ms-gestion-clientes
Microservicio que permite manejar el registro, consulta y análisis de datos de clientes utilizando MySQL como base de datos. La implementacion uiliza Spring Data JPA, Spring Security y Flyway.

La aplicacion esta dockerizada, es decir, esta desplegada en contenedores:

Para su ejecucion, ejecutar en lineas de comando:

```sh
docker-compose build

docker-up build
```

Funcionalidades:

●	Crear nuevos clientes mediante un endpoint que permita registrar nombre, apellido, edad y fecha de nacimiento.

●	Consultar un conjunto de métricas sobre los clientes existentes, como el promedio de edad y la desviación estándar de las edades.

●	Listar todos los clientes registrados con sus datos completos y un cálculo derivado, como una fecha estimada para un evento futuro basado en los datos del cliente (por ejemplo, esperanza de vida).
