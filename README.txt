Autor: Hugo Herrera Montecino

Bienvenido al repositorio "Beta" del proyecto de analisis de aire en Chillan y Chillan Viejo mediante uso de drones,

A continuacion se presentaran las INSTRUCCIONES para el levantamiento de este:


1. docker-compose.yml:

    -Primero debes tener instalado Docker Desktop en el ordenador, esto para poder desplegar los contenedores (no es necesario iniciar 
    sesion), es necesario tener esta aplicacion abierta al momento del despliegue.

    -es necesario revisar este archivo que se encuentra en la raiz del proyecto, en este encontraras las configuraciones
    para la construccion y ejecucion de los contenedores Docker que requiere el proyecto.

    -dentro de los tipos de contenedores se encuentran aquellos que utilizan las imagenes de MySQL, en su configuracion es necesario
    que modifiques el usuario y contraseña de la base de datos que utilizara ese contenedor, puedes modificar el nombre del contenedor,
    base de datos, version de la imagen (esto es para todo tipo de contenedor) e incluso el puerto si que lo deseas (no recomendaria el 
    ultimo ya que requiere modificar otros archivos del proyecto).

    -Es posible tambien cambiar el proveedor de la base datos como por ejemplo a POSTGRESQL, en ese caso la distribucion de las variables
    de entorno del contenedor tendran algunas modificaciones (ver documentaciones del propio proveedor para docker).

2. MySQL:

    -Como se menciono anteriormente, algunos contenedores utilizan MySQL, por ende debes tener intalado MySQL Workbench asi como se utilizo
    para el desarrollo de este proyecto.

    -Deberas crear las bases de datos con exactamente el mismo nombre que esta configurado en docker-compose ("ms_registros" por ej.), 
    y asegurarte de que esten en el mismo puerto que expone el contenedor (por default es 3306)

3. Desplegar docker-compose:

    -Luego de asegurarte de configurar correctamente los contendores de MySQL, crear sus DB y tener Docker Desktop abierto puedes 
    desplegar docker-compose con click derecho y seleccinar "Compose Up" (caso para Visual Studio Code)

    -En el terminal que se mostrara, se puede ver la descarga de imagenes y la construccion de los contenedores, al finalizar se
    notifica y se puede cerrar el terminal, con ello en Docker Desktop podras ver las imagenes instaladas y los contendores que se
    estan ejecutando simultaneamente.

4. Keycloak:

    -Keycloak proporciona una capa de seguridad y autentificacion OAUTH2 para el acceso de microservicios. Luego del levantamiento de
    los contenedores, en el navegador, debes hacer las configuraciones de Keycloak en su interfaz alojada en el puerto del
    contenedor Keycloak (localhost:8181 como viene configurado por default), usuario=admin y contraseña=admin.
    
    -Los pasos pueden ser algo detallados y extensos, asi que te recomendaria seguir los pasos del siguiente video:
    https://youtu.be/HycJwRUipwE?si=eG9uPJVxwdTAY8fG&t=310, procura ver hasta la parte donde hace los registros de los usuarios admin
    y usuario basico, deberas actualizar SOLO el aplicacion.properties del microservicio api-gateway, con un nuevo 
    cliente.secret (contaseña encriptada).

    -considera utilizar las siguientes etiquetas para las cofiguraciones (de lo contrario deberas configurar los aplicacion.properties
    de los demas microservicios, como se muestra en el video), realm= fondef-realm, client= fondef_client, las etiquetas de los usuarios
    admin y usuario basico asi como sus contraseñas quedan a tu criterio.
    
    -Luego de tener configurado los usuarios admin y usuario basico, podras hacer las peticiones como usuario a los microservicios.

5. despliegue de microservicios:

    -Despues de haber configurado los usuarios para el ingreso a Keycloak, puedes hacer el despliegue de todos los microservicios.
    
    -Cada microservicio consta con su Application.java, para cada uno de ellos (excepto 'files') debes iniciar esa clase de modo que 
    todos deben estar ejecutandose simultaneamente.

    -Es importante que todos los microservicios esten desplegados para que ocurran fallos en las llamadas de usuario.

6. Postman:

    -Para realizar las peticiones como usuario se recomienda usar la herramienta Postman. En ella debes configurar un folder raiz 
    ("fondef" por ejemplo) y dentro de este, tres folder que pertenecen a registros, ordenes y seguimiento, a continuacion te dejo para 
    cada folder las peticiones implementadas:

        -registros: 
            -de tipo GET, "listar todos los registros", http://localhost:8086/api/registro
            -de tipo POST, "Agregar registro", http://localhost:8086/api/registro
            -Formato JSON de registro para POST: 
                        {
                            "sector": "Chillán Viejo",
                            "dia": 11,
                            "hora": "10:00 PM",
                            "pmDos": 12,
                            "pmDiez": 58,
                            "ozono": 16,
                            "carbono": 20
                        }

        -ordenes: 
            -de tipo POST, "solicitar orden de registro", http://localhost:8086/api/order
            -de tipo GET, "solicitar ordenes de registro",http://localhost:8086/api/order

        -seguimiento:
            -de tipo GET, "existe", http://localhost:8086/api/seguimiento/12
            - *el valor 12 representa el dia de un registro, puedes probar con otros que exiten o no
    
    -Para realizar las consultas en Postman, debes ademas configurar el sistema de token para conectar Keycloak con Postman, para esto
    te sugiero continuar con el video anterior, aqui en el minuto para configurar Postman:
    https://youtu.be/HycJwRUipwE?si=dMAaNyZsO6nLk9YQ&t=1535
    
    -Finalmente podras hacer las consultas en cada request que ya configuraste anteriormente, recuerda primero hacer las llamadas POST
    para insertar los datos a la BD y luego usar las peticiones GET para listar.

----------------------------------------------------------------------------------------------------------------------------------------
     
     
     ##### Funciones e implementaciones a conocer #####

     ZipKin (localhost:9411) :

        -Es un sistema de seguimiento de microservicios, en su interfaz alojada en el puerto 9411 podras ver un apartado de las consultas
        y otro de dependencias, en el primero al "ejecutar consultas" podras ver las todas las peticiones realizadas por los usuarios,
        ademas en cada una de ellas una serie de detalles como los tiempos de respuesta en ms. La seccion de dependencias corresponde
        a una interfaz que ofrece ZipKin para mostrar como interactuan los microservicio frente a las consultas de usuario.

    Eureka server (localhost:8761) :

        -En el puerto alojado de Eureka podras ver aquellos microservicios que estan desplegados y alojados con Eureka server

    Prometheus (localhost:9090) :

        -En la interfaz de Prometheus status, tarjets se visualizan los endpoints de los microservicios

    Kafka:

        -Permite la interacción entre usuarios y aplicación de forma asíncrona mediante mensajes, es decir permite que varios usuarios
         accedan o modifiquen los datos necesarios de la aplicación sin errores de transacciones.

    Keycloak (localhost:8181) :

        -sistema de autentificacion OAUTH2 que implementa una capa de seguridad para el acceso a los microservicios, integra varias 
        implementaciones para pryectos, y cuenta con una interfaz que complementa y aporta a las configuraciones del proyecto.
