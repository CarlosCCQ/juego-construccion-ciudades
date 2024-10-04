# City-Building Game API

Este es el backend de un juego de construcción de ciudades, desarrollado en Java utilizando Spring Boot. La API permite la gestión de recursos, infraestructuras, ciudades y poblaciones dentro del juego.

## Tabla de Contenidos

- [Requisitos](#requisitos)
- [Instalación](#instalación)
  - [Clonar el Repositorio](#clonar-el-repositorio)
  - [Ejecución Local](#ejecución-local)
  - [Ejecución con Docker](#ejecución-con-docker)
- [Uso de la API](#uso-de-la-api)
- [Monitoreo con Prometheus y Grafana](#monitoreo-con-prometheus-y-grafana)
- [Simular gestion de ciudades](#simular-gestion-de-ciudades)

## Requisitos

- Java 17+
- Maven 3.6+
- Docker (opcional)

## Instalación

### Clonar el Repositorio

Primero, clona el repositorio a tu máquina local:

```bash
git clone git@github.com:CarlosCCQ/juego-construccion-ciudades.git
cd juego-construccion-ciudades
```

### Ejecución Local

* Asegúrate de tener Java 17 y Maven instalados en tu sistema.

* Navega al directorio del proyecto y construye el proyecto con Maven:

    ```bash
    mvn clean install
    ```
* Ejecuta la aplicación:
    
    ```bash
    mvn clean install
    ```
La API estará disponible en http://localhost:8081

### Ejecución con Docker

También puedes ejecutar la API utilizando Docker. Para esto, necesitas tener Docker instalado en tu máquina.

* Asegúrese  de tener Docker corriendo.

* En el directorio , donde se encuentra el archivo `Dockerfile` , ejecute el siguiente comando: 
    
    ```bash
    docker build -t <name> .
    ```
*  En el directorio raíz del proyecto, donde se encuentra el archivo `docker-compose.yml`, ejecuta el siguiente comando para iniciar todos los servicios (API, Prometheus y Grafana):

    ```bash
    docker compose up 
    ```
La API estará disponible en http://localhost:8081.


## Uso de la API

Para empezar a utilizar la API, aquí hay algunas rutas disponibles:

### 1. Crear una Ciudad

**Endpoint**: `POST /api/ciudades/create`

**Descripción**: Este request crea una nueva ciudad en el juego con un nombre especificado.


**Cuerpo del request (JSON)**:

```json
{
  "nombre": "Ciudad Nueva",
  "recursoIniciales":{
    "piedra" : 100,
    "oro" : 60,
    "agua": 200
  }
}
```
 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/create_ciudad.jpg)

### 2. Mostrar Ciudad por id

**Endpoint**: `GET /api/ciudades/obtener/{id_ciudad}`

**Descripción**: Este request nos muestra la  ciudad por id..


  ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/obtener_ciudad.jpg)




### 3. Crear un edificio para  un Ciudad

**Endpoint**: `POST /api/edificio/new-edificio`

**Descripción**: Este request crea un edicio para una ciudad determinada en el juego.


**Cuerpo del request (JSON)**:

```json
{
    "name" : "Edificio de prueba",
    "tipoEdificio" : "CLASE_MEDIA",
    "ciudadId" : 1
}
```

 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/crear_edificio.jpg)



### 4. Crear un edificio de clase alta

**Endpoint**: `POST /api/edificio/new-edificio`

**Descripción**: Este request crea un edicio de clase alta , si es que la ciudad tiene los recursos suficientes.


**Cuerpo del request (JSON)**:

```json
{
    "name" : "Edificio nuevo",
    "tipoEdificio" : "CLASE_ALTA",
    "ciudadId" : 1
}
```

 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/crear_edificio_a.jpg)



### 5. Crear recursos

**Endpoint**: `POST /api/recurso/crear`

**Descripción**: Este request crear un recurso , con una cantidad especifica.


**Cuerpo del request (JSON)**:

```json
{
    "tipoRecurso" : "PIEDRA",
    "cantidad" : 10,
    "ciudadId" : 1
}
```

 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/crear_recurso.jpg)

### 6. Mostrar todos los  recursos

**Endpoint**: `GET /api/recurso/all`

**Descripción**: Este request muestra todos los recursos en una ciudad.



 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/mostrar_recursos.jpg)





## Monitoreo con Prometheus y Grafana

### Accediendo a Prometheus

1. Una vez que todos los contenedores estén corriendo, puedes acceder a la interfaz web de Prometheus en:

    ```
    http://localhost:9090
    ```

    ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/prometheus.png)


2. En la interfaz de Prometheus, puedes hacer consultas sobre las métricas recolectadas. Por ejemplo:
   - Consulta todas las métricas recolectadas: `up`
   - Métricas sobre el uso de CPU: `process_cpu_seconds_total`
   - Métricas sobre el estado de tus endpoints: `http_requests_total`

    ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/prometheus_up.png)

3. También puedes ver el estado de las series de tiempo, targets y alertas.

### Accediendo a Grafana

1. Grafana estará disponible en el puerto `3000` por defecto. Accede a Grafana desde tu navegador:

    ```
    http://localhost:3000
    ```

2. Las credenciales por defecto son:
    - **Usuario**: `admin`
    - **Contraseña**: `admin`

   Se te pedirá cambiar la contraseña la primera vez que inicies sesión.

    ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/grafana_login.png)


3. Una vez dentro de Grafana:
    - Ve a la sección de **Configuration** y selecciona **Data Sources**.
    - Agrega **Prometheus** como una fuente de datos usando la siguiente URL para conectarlo:

      ```
      http://prometheus:9090
      ```


    ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/grafana_con.png)



4. Crea un dashboard para visualizar las métricas:
    - Ve a **Create** > **Dashboard**.
    - Selecciona **Add a new panel** y en la sección de **Data source**, selecciona **Prometheus**.
    - Escribe una consulta para mostrar una métrica (por ejemplo, `http_requests_total`).
    - Guarda el panel y agrégalo a un dashboard.

    ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/grafana_http.png)


## Simular gestion de ciudades

### Menú Principal

Al iniciar el programa, el jugador verá el siguiente menú:

 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/menu_consola.png)

El jugador puede ingresar un número para seleccionar una opción y realizar acciones relacionadas con la gestión de la ciudad.

### Funcionalidades del Menú

1. **Crear ciudades**: Permite al jugador crear una nueva ciudad. Se solicita el nombre de la ciudad y se registran los recursos y la población iniciales.

 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/crear_ciudad_consola.png)


   
2. **Ver ciudades**: Muestra una lista de las ciudades que el jugador ha creado. Cada ciudad incluye detalles como el nombre, la población y los recursos disponibles.

 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/mostrar_ciudades_consola.png)



3. **Construir edificios**: Permite al jugador seleccionar una ciudad y construir edificios como granjas, fábricas o rascacielos. Cada edificio tiene un costo de construcción y tiempo de finalización.

 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/construir_edificio.png)


4. **Generar recursos**: Aumenta los recursos de una ciudad a través de la agricultura o el uso de fábricas. Los recursos disponibles incluyen madera, piedra, agua y más.

 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/generar_recurso_consola.png) 

5. **Ver estados de la ciudad**: Muestra el estado actual de una ciudad seleccionada, incluyendo la población, los recursos, el crecimiento económico y los edificios construidos.

 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/estado_ciudad.png) 



6. **Salir**: Finaliza el programa.

 ![](https://github.com/CarlosCCQ/juego-construccion-ciudades/blob/main/src/assets/salir_juego.png) 

