# Guion propuesto de exposición

El [guion PDF completo](../output/pdf/Guion_PA1_Equipo.pdf) incluye el texto para ensayar, los tiempos y la secuencia de consola. La [presentación PowerPoint](../output/presentations/Exposicion_PA1_Equipo.pptx) contiene 10 diapositivas editables y notas del expositor.

Duración orientativa: 8 a 10 minutos. Ajustar a las instrucciones del docente. Cada integrante participa con la cámara encendida. Este archivo prepara la grabación; no sustituye el video requerido.

## Angel Fernando Reyes Moreno: caso y modelo (diapositivas 1 a 3)

Presentar la necesidad de la microempresa y las operaciones del menú. Abrir `Producto.java` y explicar la diferencia entre una clase y un objeto. Mostrar los atributos de instancia, una constante de clase y el constructor parametrizado. Explicar por qué el stock se modifica mediante métodos.

## Flor de Venus Torres Condori: atributos, constructor e inventario (diapositivas 4 y 5)

Mostrar el arreglo de productos y el contador `cantidad`. Explicar cómo se evita duplicar un código y cómo `for` visita solo las posiciones ocupadas. Distinguir el contador de productos del acumulador de unidades y del acumulador de valor monetario. Mencionar la capacidad fija y el almacenamiento temporal.

## Donny Scrach Gaspar Araujo: flujo y demostración (diapositivas 6 y 7)

1. Ejecutar la aplicación y listar el inventario vacío.
2. Registrar A01, Arroz, S/ 4.50, stock 10, mínimo 3.
3. Registrar B01, Aceite, S/ 8.00, stock 2, mínimo 2.
4. Intentar registrar A01 otra vez y explicar el rechazo.
5. Retirar 7 unidades de A01. Consultar su estado de stock bajo.
6. Intentar retirar 4 unidades de A01. Mostrar que el stock sigue en 3.
7. Ingresar 2 unidades de A01 y abrir el resumen: 2 productos, 7 unidades, 1 alerta, S/ 38.50.
8. Introducir una opción con letras y comprobar que el menú permite corregirla.

Relacionar `switch`, `do while`, `while` e `if` con lo observado.

## César Augusto MAGUIÑA ROBLES: validaciones, pruebas y cierre (diapositivas 8 a 10)

Ejecutar `PruebasInventario` y explicar al menos dos pruebas, incluidos un rechazo sin cambios y un cálculo del resumen. Mostrar la documentación del repositorio y comentar sus límites: memoria temporal, capacidad fija y uso educativo de `double`. Explicar qué mejoraría en una evaluación futura si se permitieran persistencia o colecciones.

## Preguntas para preparar la sustentación

- ¿Por qué stock mínimo y stock actual son atributos distintos?
- ¿Qué diferencia hay entre `mostrarInformacion` y `calcularValorStock`?
- ¿Por qué una cantidad negativa no debe modificar existencias?
- ¿En qué se diferencia `cantidad++` de `total += valor`?
- ¿Por qué un stock igual al mínimo activa una alerta?
- ¿Qué ocurre al cerrar el programa y por qué se eligió ese alcance?

Al finalizar, publicar la grabación en YouTube con la visibilidad indicada por el docente y colocar el enlace en el README. Revisar que el docente pueda abrir tanto el video como el repositorio.
