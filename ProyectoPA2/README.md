# PA2 - Evaluación del curso Programación Orientada a Objetos

## Información Institucional
* **Institución:** ISIL
* **Curso:** Programación Orientada a Objetos
* **Código del Curso:** 30626
* **Docente:** Wilder Julio Espinoza Bravo
* **Evaluación:** PA2 (Producto Académico 2)

---

## Integrantes del Equipo
* Angel Fernando Reyes Moreno
* Flor De Venus Torres Condori
* Donny Scrach Gaspar Araujo
* **César Augusto Maguiña Robles**

---

## Descripción del Trabajo
Aplicación web desarrollada con tecnología **Java Servlets y JSP (Java EE / Jakarta EE)** ejecutándose sobre un servidor **Apache Tomcat**. 

El sistema cumple con los requerimientos de una propuesta mínima institucional que permite:
1. **Validación y Manipulación de Cadenas (`String`):** Recibe un texto o frase mediante un formulario web y procesa su longitud, conversión a mayúsculas, inversión de caracteres y validación de palabras clave (como "java").
2. **Operaciones Matemáticas (`Math`):** Recibe datos numéricos desde la interfaz web para calcular potencias, raíces cuadradas (con control de valores absolutos) y determinar valores máximos y mínimos entre los números ingresados.

---

## Instrucciones Mínimas de Ejecución

Sigue estos pasos para desplegar y ejecutar el proyecto localmente:

### 1. Requisitos Previos
* Tener instalado **Java Development Kit (JDK) versión 21** o superior.
* Tener instalado **IntelliJ IDEA**.
* Tener instalado un servidor **Apache Tomcat** (versión 11 compatible con Jakarta EE).

### 2. Compilación del Proyecto (Generar el archivo `.war`)
1. Abre el proyecto en **IntelliJ IDEA**.
2. En la barra lateral derecha, haz clic en la pestaña de **Maven**.
3. Expande el árbol de **Lifecycle** (Ciclo de vida).
4. Haz doble clic primero en **`clean`** y luego en **`package`**.
5. Se generará un archivo empaquetado llamado `ProyectoCalculadoraWeb.war` dentro de la carpeta `target/` del proyecto.

### 3. Despliegue en Apache Tomcat
1. Copia el archivo `ProyectoCalculadoraWeb.war` ubicado en la carpeta `target/`.
2. Pégalo dentro de la carpeta **`webapps/`** de tu servidor Apache Tomcat (ej. `apache-tomcat-xx/webapps/`).
3. Inicia o reinicia el servidor Tomcat ejecutando el archivo `startup.bat` (en Windows) dentro de la carpeta `bin/` de Tomcat.

### 4. Ejecución en el Navegador
1. Abre tu navegador web favorito (Google Chrome, Edge, etc.).
2. Ingresa a la siguiente URL para utilizar la aplicación:
   `http://localhost:8080/ProyectoCalculadoraWeb/`