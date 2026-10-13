# PA1 - Sistema de registro y control de productos

Trabajo del curso **Programación Orientada a Objetos (30626)**. Aplicación Java por consola para una microempresa. Permite registrar productos, consultar información y disponibilidad, ingresar y retirar existencias, identificar stock bajo y calcular el valor del inventario.

## Datos de la entrega

| Dato | Estado |
| --- | --- |
| Institución | Pendiente de completar por el equipo |
| Docente | Pendiente de completar por el equipo |
| Sección | Pendiente de completar por el equipo |
| Integrante 1 | Angel Fernando Reyes Moreno |
| Integrante 2 | Flor de Venus Torres Condori |
| Integrante 3 | Donny Scrach Gaspar Araujo |
| Integrante 4 | César Augusto MAGUIÑA ROBLES |
| Video de YouTube | **Pendiente de grabación/publicación y enlace** |

La consigna establece cuatro integrantes, o cinco con autorización del docente. Los datos institucionales pendientes y el video deben completarse antes de la entrega académica. La participación y la asistencia deben corresponder a hechos reales.

## Ejecución

Requisito: **JDK 8 o superior**, con `java` y `javac` disponibles. No requiere Maven, librerías externas ni base de datos.

Desde la carpeta del proyecto:

```sh
mkdir build
javac -encoding UTF-8 -d build src/Producto.java src/Inventario.java src/Aplicacion.java
java -cp build Aplicacion
```

Si `build` ya existe, omitir la primera línea. En Windows también se puede abrir **ejecutar.bat**. El ejecutor utiliza el JDK del PATH o el JDK portátil local de `.tools`, si existe; este último no se incluye en GitHub.

En un IDE, crear un proyecto Java, agregar los tres archivos de `src` y ejecutar `Aplicacion.main`. Los archivos no declaran un paquete para facilitar su uso inicial.

## Menú

| Opción | Operación |
| --- | --- |
| 1 | Registrar código único, nombre, precio, stock inicial y stock mínimo |
| 2 | Listar todos los productos |
| 3 | Consultar un producto por código y ver su stock |
| 4 | Ingresar existencias |
| 5 | Retirar existencias si hay stock suficiente |
| 6 | Mostrar productos con stock igual o inferior al mínimo |
| 7 | Mostrar cantidad de productos, unidades, alertas y valorización |
| 0 | Salir |

Los códigos ignoran diferencias entre mayúsculas y minúsculas. El precio acepta punto o coma decimal, sin separador de miles, y se redondea a dos decimales. Las entradas inválidas se solicitan nuevamente. Una operación rechazada conserva el stock anterior.

## Modelo y decisiones

- **Producto** concentra los atributos de cada artículo y las reglas de stock. Su constructor parametrizado inicializa el objeto. `mostrarInformacion` es un método `void`; `retirarStock`, `tieneStock` y `calcularValorStock` devuelven resultados.
- **Inventario** reúne hasta 100 objetos `Producto` en un arreglo fijo y evita códigos duplicados. Valida los datos antes de llamar al constructor. El contenedor se relaciona con la introducción de contenedores y arreglos de la sesión 1, diapositiva 33.
- **Aplicacion** contiene `main`, el menú y la lectura por consola. Separar la interacción de las reglas permite entender y probar cada responsabilidad.
- Las constantes `static final` son compartidas por todos los objetos. El stock y el precio pertenecen a cada instancia. Los acumuladores e índices se declaran dentro de los métodos que los utilizan.
- `switch` selecciona opciones; `do while` repite el menú; `while` repite las validaciones; `for` recorre los productos. Los condicionales simples y anidados validan operaciones y determinan el estado del stock.
- La solución usa los fundamentos de las sesiones 1 a 4. No emplea herencia, interfaces, streams, frameworks ni persistencia. `Scanner`, `String`, `Math` y `Locale` sirven como utilidades básicas de entrada, texto y formato.

El [análisis del caso](docs/analisis.md) detalla las clases, atributos y métodos. El [guion de exposición](docs/guion-exposicion.md) propone una demostración y preguntas para sustentar las decisiones.

## Ejemplo de comprobación

Registrar `A01 / Arroz / 4.50 / 10 / 3` y `B01 / Aceite / 8.00 / 2 / 2`. Retirar 7 unidades de A01 y luego ingresar 2. El resumen debe mostrar:

```text
Productos registrados: 2
Unidades totales: 7
Productos con stock bajo: 1
Valor total del inventario: S/ 38.50
```

La [transcripción de ejecución](docs/ejecucion-ejemplo.txt) se genera al ejecutar las pruebas de consola.

## Pruebas

```sh
javac -encoding UTF-8 -d build src/Producto.java src/Inventario.java src/Aplicacion.java tests/PruebasInventario.java
java -cp build PruebasInventario
```

Opcionalmente, con Python 3 disponible y Java en el PATH:

```sh
python tests/test_consola.py
```

Las pruebas comprueban registros, duplicados, capacidad, estados del stock, cantidades inválidas, retiros excesivos, acumuladores y entradas de consola. Consultar [resultados y alcance](docs/pruebas.md).

## Límites explícitos

Los datos se guardan en memoria y se pierden al cerrar el programa. La capacidad es de 100 productos; stock y stock mínimo están entre 0 y 1 000 000. El precio está entre S/ 0.01 y S/ 1 000 000.00. Se usa `double` para practicar tipos básicos, con redondeo y presentación a dos decimales; no constituye un sistema contable de precisión decimal exacta.

## Organización del equipo y video

La exposición está preparada para una duración orientativa de 8 a 10 minutos:

- [Guion en PDF, con intervención de cada integrante y demostración paso a paso](output/pdf/Guion_PA1_Equipo.pdf).
- [PowerPoint editable, con 10 diapositivas y notas del expositor](output/presentations/Exposicion_PA1_Equipo.pptx).

Completar el [registro de responsabilidades](docs/trabajo-equipo.md) con los nombres, acuerdos y aportes reales. Grabar la exposición con participación de todos y cámaras encendidas, publicar el video en YouTube según indique el docente y reemplazar el pendiente de la tabla por su enlace. Compartir el acceso al repositorio con el docente.

## Material de referencia

- `PA1_Programacion_Orientada_a_Objetos.pdf`, páginas 1 a 3: caso, actividades, entregables y rúbrica.
- `30626-S01-PPT.pptx`: clases, objetos, estructura Java y contenedores.
- `30626-S02-PPT.pptx`: atributos, constructores y métodos.
- `30626-S03-PPT.pptx`: constructores y métodos con/sin retorno.
- `30626-S04-PPT.pptx`: estructuras selectivas y repetitivas, contadores y acumuladores.

Los materiales docentes originales no se redistribuyen dentro del repositorio.
