# Guion de la exposición (video de YouTube)

Duración sugerida: 8 a 10 minutos. Todos los integrantes con la cámara encendida durante su parte.

## 1. Presentación (1 min) | César

* Saludo, nombre del curso, evaluación PA2 y nombres de los integrantes.
* El caso: una organización necesita una mini aplicación web que reciba datos por formulario, calcule una cotización y valide los textos del cliente.
* Cómo nos repartimos el trabajo (tabla de responsabilidades del README).

## 2. Proyecto web con JSP y Tomcat (2 min) | César

* Estructura del proyecto Maven: `pom.xml`, carpeta `webapp`, paquetes Java.
* `index.jsp`: formulario con método POST hacia `resultado.jsp`.
* `resultado.jsp`: lee los datos con `request.getParameter`, llama a las clases y muestra el resultado o los errores.
* Fragmentos `encabezado.jspf` y `pie.jspf` incluidos con `<%@ include %>`.
* Despliegue: `mvn package`, copiar el WAR a `webapps` de Tomcat, abrir `http://localhost:8080/CotizadorWeb/`.

## 3. Validaciones con String (2 min) | Angel

* Mostrar `ProCadenas`: `normalizarEspacios` (trim + charAt), `capitalizarPalabras` (split + substring + toUpperCase/toLowerCase), `esCorreoValido` (contains, indexOf, lastIndexOf), `enmascararCorreo` (substring + repeat), `escaparHtml` (replace).
* Mostrar `ValidadorDatos`: DNI de 8 dígitos, celular que empieza con 9 (`startsWith`), cupón `PROMO` + número (`toUpperCase`, `startsWith`, `substring`).
* Por qué validamos en el servidor aunque el formulario tenga `required`.

## 4. Cálculos con Math (2 min) | Angel

* Mostrar `ProAritmetica`: `Math.round` para céntimos, `Math.ceil` para la cuota, `Math.pow` en la fórmula de la cuota fija, `Math.min`/`Math.max` para el tope de 20 %, `Math.random` para el código.
* Mostrar `Cotizacion.calcular()`: subtotal → descuento → valor de venta → IGV → total → cuotas.
* Explicar la verificación manual del caso 2 (docs/PRUEBAS.md).

## 5. Demostración en vivo (2 min) | César y Angel

1. Caso correcto: nombre con espacios y mayúsculas mezcladas, 10 laptops, 6 cuotas, cupón `promo10`. Mostrar cómo se normaliza el nombre, el correo oculto, el descuento de 15 % y la cuota.
2. Caso con errores: DNI corto, correo con dos arrobas, cupón `PROMO25`. Mostrar la lista de errores y el botón **Corregir datos**.
3. Caso tope: 20 monitores con `PROMO20`, el descuento se limita a 20 %.
4. Ejecutar `PruebasCotizador` y mostrar **46 correctas, 0 fallidas**.

## 6. Cierre (1 min) | todos

* Qué aprendimos: separar la lógica en clases, usar Math y String para resolver un caso real, probar antes de entregar.
* Qué mejoraríamos: guardar las cotizaciones en una base de datos, enviar la cotización por correo.
