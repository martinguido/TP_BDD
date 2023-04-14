# Trabajo Practico de Base de Datos Avanzada y Big Data
## Profesores:
#### Ricardo Di Pascuale
#### Sebastian Calderone
## Alumnos:
##### Baccari Carla,
####  Guido Martin,
####  Herrmann Karen,
####  Klix Felicitas.
### Año: 2023

## INSTRUCCIONES PARA WINDOWS
1. Descargar e instalar Java SDK 17, se consigue en [Java SDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. Abrir el proyecto en algun IDE (Visual Studio Code, Intellij IDEA, Eclipse,...)
3. En este caso, se utilizo Intellij IDEA, se puede descargar en [Intellij IDEA](https://www.jetbrains.com/idea/download/#section=windows) :
   - Clonar el repositorio publico o descargar el archivo zip que lo contiene
   - Entrar a File > Project Structure y seleccionar en las pestañas Project, Modules and SDK, Java SDK 17 y tambien como nivel de lenguaje
   - Posteriormente, entrar a File > Settings > Build, Execution, Deployment > Build Tools > Maven y seleccionar en Maven Home Path: Bundled (Maven 3)
   - Luego, entrar a File > Settings > Build, Execution, Deployment > Compiler > Java Compiler y seleccionar en Target bytecode Version: 17
4. Descargar e instalar Docker Desktop en [Docker Desktop](https://docs.docker.com/desktop/install/windows-install/) y se recomienda instalar la actualizacion del kernel de Linux. En caso de no ternerla, se descarga en [WSL2](https://wslstorestorage.blob.core.windows.net/wslblob/wsl_update_x64.msi)
5. Abrir una terminal en la carpeta del proyecto e insertar el siguiente comando: `./mvn clean`, cuando termine ejecutar: `./mvn package`.
6. Una vez finalizada la descarga de dependencias y el build del proyecto, se debe crear la imagen que luego sera utilizada para correr el contenedor de la aplicacion. Teniendo Docker Desktop abierto, ingresar en la terminal `docker build -t springboot .` donde -t indica el nombre
7. Escribir en la terminal `docker-compose up` para crear el contenedor con el backend, la base de datos y pgAdmin4 (un gestor grafico de la base de datos)
8. Finalmente, para configurar el gestor grafico, debemos dirigirnos a Docker Desktop, en la pestaña Containers, expandir el paquete llamado __tp_bdd__  y buscar __pgadmin__. En los 3 puntos de la derecha, tocar Open with Browser.
- Una vez en la pagina de PgAdmin4, ingresar las credenciales name@example.com como usuario y admin como contraseña. Una vez adentro, hacer click en "Add New Server".
- En la primera pestaña, General, solo ingresar en Name: "postgres"
- En la segunda pestaña, Connection, ingresar en Host name/address: "192.168.55.11", en Username: "postgres" y en Password: "postgres"

De esta forma, se configura por unica vez el proyecto, en un futuro solo se requiere entrar a Docker Desktop y correr el paquete de contenedor denominado __tp_bdd__

## INSTRUCCIONES PARA MAC OS
1. Descargar e instalar Java SDK 17, se consigue en [Java SDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. Abrir el proyecto en algun IDE (Visual Studio Code, Intellij IDEA, Eclipse,...)
3. En este caso, se utilizo Intellij IDEA, se puede descargar en [Intellij IDEA](https://www.jetbrains.com/idea/download/#section=windows) :
   - Clonar el repositorio publico o descargar el archivo zip que lo contiene
   - Entrar a File > Project Structure y seleccionar en las pestañas Project, Modules and SDK, Java SDK 17 y tambien como nivel de lenguaje
   - Posteriormente, entrar a File > Settings > Build, Execution, Deployment > Build Tools > Maven y seleccionar en Maven Home Path: Bundled (Maven 3)
   - Luego, entrar a File > Settings > Build, Execution, Deployment > Compiler > Java Compiler y seleccionar en Target bytecode Version: 17
4. Descargar e instalar Docker Desktop en [Docker Desktop](https://docs.docker.com/desktop/install/mac-install/)
5. Abrir una terminal en la carpeta del proyecto e insertar el siguiente comando: `./mvn clean`, cuando termine ejecutar: `./mvn package`.
6. Una vez finalizada la descarga de dependencias y el build del proyecto, se debe crear la imagen que luego sera utilizada para correr el contenedor de la aplicacion. Teniendo Docker Desktop abierto, ingresar en la terminal `docker build -t springboot .` donde -t indica el nombre
7. Escribir en la terminal `docker-compose up` para crear el contenedor con el backend, la base de datos y pgAdmin4 (un gestor grafico de la base de datos)
8. Finalmente, para configurar el gestor grafico, debemos dirigirnos a Docker Desktop, en la pestaña Containers, expandir el paquete llamado __tp_bdd__  y buscar __pgadmin__. En los 3 puntos de la derecha, tocar Open with Browser.
- Una vez en la pagina de PgAdmin4, ingresar las credenciales name@example.com como usuario y admin como contraseña. Una vez adentro, hacer click en "Add New Server".
- En la primera pestaña, General, solo ingresar en Name: "postgres"
- En la segunda pestaña, Connection, ingresar en Host name/address: "192.168.55.11", en Username: "postgres" y en Password: "postgres"

De esta forma, se configura por unica vez el proyecto, en un futuro solo se requiere entrar a Docker Desktop y correr el paquete de contenedor denominado __tp_bdd__