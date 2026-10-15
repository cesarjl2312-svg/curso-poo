# Guion de la exposición | PA2 Cotizador Web

Duración estimada: **9 a 10 minutos**. Presentación: `docs/PA2_Cotizador_Web.pptx` (el mismo texto está en las notas de cada diapositiva).

## Antes de grabar

- [ ] Tomcat iniciado y la app abierta en `http://localhost:8080/CotizadorWeb/`.
- [ ] El IDE abierto con `resultado.jsp`, `ProCadenas.java`, `ProAritmetica.java` y `PruebasCotizador.java` en pestañas.
- [ ] La presentación en modo presentador (para ver las notas).
- [ ] Reunión de Meet, Zoom o Teams grabando, con **todas las cámaras encendidas** durante todo el video.
- [ ] Quien comparte pantalla: César en las partes 1 a 3, Angel en las partes 4 a 6, y el que hace la demo.

| Parte | Diapositivas | Quién | Tiempo |
|---|---|---|---|
| 1. Presentación y caso | 1 y 2 | César y Angel | 1:30 |
| 2. Proyecto web JSP y Tomcat | 3 y 4 | César | 2:00 |
| 3. Clase String | 5 y 6 | Angel | 2:00 |
| 4. Clase Math | 7 y 8 | Angel | 1:45 |
| 5. Demo en vivo y pruebas | 9 y 10 | César y Angel | 2:00 |
| 6. Decisiones y cierre | 11 y 12 | César y Angel | 1:00 |

> Si el equipo tiene más integrantes, repartan así: integrante 3 toma la parte 4 (Math) e integrante 4 hace la demo de la parte 5. Todos deben hablar al menos una vez.

---

## Parte 1 | Presentación y caso

**Diapositiva 1 (Portada)**

**CÉSAR:** Buenos días, profesor. Somos el equipo de la PA2 del curso Programación Orientada a Objetos. Yo soy César Maguiña y me acompaña Angel Reyes.

**ANGEL:** Hola, soy Angel Reyes. En este video vamos a explicar cómo resolvimos la evaluación: un Cotizador Web hecho con JSP y Apache Tomcat que usa métodos de la clase Math y de la clase String.

**CÉSAR:** Primero contamos el caso y la solución, luego mostramos el código y al final hacemos la demostración en vivo y las pruebas.

**Diapositiva 2 (El caso)**

**CÉSAR:** El caso de la evaluación pide una mini aplicación web que reciba datos por formulario, haga cálculos sencillos y valide las cadenas que escribe el usuario. Nosotros lo llevamos a una situación real: una empresa que vende laptops, monitores, impresoras y soporte técnico, y que quiere que sus clientes pidan una cotización por la web.

La solución sigue tres pasos. La entrada es index.jsp, el formulario. El procesamiento lo hacen nuestras clases Java: ValidadorDatos revisa los textos con String y Cotizacion calcula los montos con Math. Y la salida es resultado.jsp, que muestra la cotización o la lista de errores.

## Parte 2 | Proyecto web con JSP y Tomcat

**Diapositiva 3 (Arquitectura)**

**CÉSAR:** Creamos un proyecto Maven de tipo web que se despliega en Apache Tomcat, como vimos en la sesión 5. Tomamos una decisión importante: los JSP solo leen los datos y muestran resultados, y toda la lógica está en clases Java. Así el código es más ordenado y cada clase se puede probar por separado.

Arriba está la capa web: index.jsp, resultado.jsp y dos fragmentos que se incluyen con la directiva include. También dejamos calculadora.jsp, que fue nuestra primera versión. Abajo están los paquetes: modelo, con el producto, el catálogo, el cliente y la cotización; util, con ProCadenas para String, ProAritmetica para Math y el validador; y pruebas, con una clase main que verifica todo.

**Diapositiva 4 (JSP y Tomcat)**

**CÉSAR:** Este es el flujo en código. El formulario de index.jsp envía los datos por POST a resultado.jsp. En resultado.jsp primero ponemos la codificación UTF-8, para que lleguen bien las tildes y la ñ. Luego leemos cada campo con request.getParameter.

Después llamamos a ValidadorDatos.validar, que devuelve una lista de errores. Si la lista está vacía, creamos el objeto Cliente y el objeto Cotizacion, que hace todos los cálculos. Si hay errores, los mostramos y damos un botón para corregir sin perder lo escrito.

Para ejecutarlo: generamos el WAR con mvn package, lo copiamos a la carpeta webapps de Tomcat y entramos a localhost:8080/CotizadorWeb.

> 💡 Opcional: cambiar al IDE y mostrar `resultado.jsp` unos segundos.

## Parte 3 | Clase String

**Diapositiva 5 (Validar)**

**ANGEL:** Ahora la parte de String, de la sesión 7. Antes de calcular, validamos cada campo en el servidor, aunque el formulario ya tenga campos obligatorios, porque esas reglas del navegador se pueden saltar.

Para el nombre recorremos cada carácter con charAt y verificamos que sea letra o espacio, y con split contamos que haya al menos dos palabras. El DNI debe tener exactamente 8 dígitos. El celular debe tener 9 dígitos y empezar con 9: primero quitamos espacios con replace y luego usamos startsWith.

Para el correo usamos contains para que no tenga espacios, contamos que haya una sola arroba, y con indexOf y lastIndexOf verificamos que haya un punto después de la arroba. Y el cupón lo pasamos a mayúsculas, verificamos que empiece con PROMO y con substring sacamos el número. Si algo falla, como en esta captura, no se calcula nada y se muestran todos los errores juntos.

**Diapositiva 6 (Transformar)**

**ANGEL:** String no solo sirve para validar, también para transformar. Esta lógica está en la clase ProCadenas, que hicimos con métodos estáticos como la clase del mismo nombre que vimos en clase.

Si el usuario escribe su nombre con espacios de más y mayúsculas mezcladas, normalizarEspacios quita los espacios con trim y charAt, y capitalizarPalabras separa con split y arma cada palabra con substring, toUpperCase y toLowerCase. El correo se guarda en minúsculas y se muestra oculto: con indexOf ubicamos la arroba, con substring separamos el usuario y con repeat ponemos los asteriscos. El celular se muestra en grupos de tres con substring, y las iniciales se sacan con charAt.

Por último, escaparHtml usa replace para que si alguien escribe código HTML, se vea como texto y no se ejecute.

> 💡 Opcional: mostrar en el IDE el método `capitalizarPalabras` de `ProCadenas.java`.

## Parte 4 | Clase Math

**Diapositiva 7 (Del subtotal a la cuota)**

**ANGEL:** Ahora Math, de la sesión 6. La clase ProAritmetica agrupa los métodos y la clase Cotizacion los usa en orden. Primero el subtotal: precio por cantidad, redondeado a céntimos con Math.round. Luego el descuento: 5 por ciento si se compran 10 o más unidades, más el cupón. Con Math.min y Math.max lo limitamos a un máximo de 20 por ciento. Después el IGV de 18 por ciento y el total, siempre redondeados a dos decimales.

Si el cliente paga en cuotas, usamos la fórmula de cuota fija del sistema francés, con Math.pow para elevar a la menos n. Y la cuota la redondeamos hacia arriba con Math.ceil, para que la empresa no cobre de menos. Además, el código de la cotización tiene una parte aleatoria hecha con Math.random, como el método numeroAleatorio que vimos en clase.

**Diapositiva 8 (Ejemplo numérico)**

**ANGEL:** Para verificar los cálculos hicimos este caso a mano y lo comparamos con el programa. Diez laptops de 2,899 soles dan un subtotal de 28,990. Como son 10 unidades hay 5 por ciento por volumen, y el cupón PROMO10 suma otro 10, en total 15 por ciento de descuento.

El valor de venta queda en 24,641.50, el IGV es 4,435.47 y el total es 29,076.97 soles. En 6 cuotas al 1.5 por ciento mensual, la fórmula da 5,103.74 y con Math.ceil queda en 5,103.75. Los intereses suman 1,545.53 soles. El programa dio exactamente los mismos números.

## Parte 5 | Demo en vivo y pruebas

**Diapositiva 9 (La salida)**

**CÉSAR:** Esta es la salida en resultado.jsp. Arriba aparece el código único de la cotización, que combina las iniciales, los tres últimos dígitos del DNI y un número aleatorio. Luego los datos del cliente ya normalizados, con el correo oculto, y el detalle de montos. A la derecha está una tabla que agregamos para la sustentación: para cada dato muestra lo que escribió el usuario, qué métodos se aplicaron y el resultado.

**▶ DEMO EN VIVO** (cambiar al navegador)

**CÉSAR:** Ahora lo vemos funcionando. *(Llenar el formulario con estos datos:)*

| Campo | Escribir |
|---|---|
| Nombre | `  aNGEL   fernando reyes  MORENO` (con espacios de más) |
| DNI | `75729271` |
| Celular | `987 654 321` |
| Correo | `Angel.Reyes@Gmail.com` |
| Producto | LAP-001 Laptop |
| Cantidad | `10` |
| Forma de pago | 6 cuotas mensuales |
| Cupón | `promo10` |

**CÉSAR:** *(clic en Calcular)* Como ven, el nombre se corrigió solo, el correo aparece oculto, el descuento es 15 por ciento, el total es 29,076.97 y la cuota 5,103.75, igual que en nuestro cálculo a mano.

**ANGEL:** Ahora probamos con datos incorrectos. *(Nueva cotización: DNI `1234`, correo `juan@@mail`, cupón `PROMO25`, clic en Calcular.)* La aplicación no calcula nada y lista cada error. Con el botón Corregir datos volvemos al formulario y no se pierde lo que escribimos.

**ANGEL:** Un último caso: 20 monitores con el cupón PROMO20. *(Calcular.)* El descuento sería 25 por ciento, pero se limita al tope de 20 por ciento, como indica la nota.

**Diapositiva 10 (Pruebas)**

**CÉSAR:** Para asegurarnos de que todo funciona, hicimos dos tipos de pruebas. La primera es por consola: la clase PruebasCotizador tiene un método main, igual que en los ejercicios de clase, y revisa 46 casos de las cuatro clases principales. Los 46 salieron correctos.

> 💡 Opcional: ejecutar `PruebasCotizador` en el IDE y mostrar la última línea: *Resultado: 46 correctas, 0 fallidas.*

**ANGEL:** La segunda son pruebas funcionales en el navegador con Tomcat: un caso al contado, uno con cupón y cuotas, uno donde el descuento pasa del tope, nombres con tildes y ñ, datos inválidos y hasta un intento de meter código HTML. Todos dieron el resultado esperado, y las capturas están en el repositorio.

## Parte 6 | Decisiones y cierre

**Diapositiva 11 (Decisiones técnicas)**

**ANGEL:** Resumimos las decisiones técnicas que tomamos. Primero, separar la lógica en clases y dejar los JSP solo para mostrar. Segundo, validar todo en el servidor. Tercero, escapar el HTML con replace para que nadie pueda inyectar código en la página. Y cuarto, usar Math para redondear bien el dinero: round para los céntimos y ceil para la cuota.

**CÉSAR:** En cuanto al trabajo en equipo, yo creé el proyecto web, las vistas JSP y el flujo de datos, y Angel desarrolló las clases de String y Math, el validador y las pruebas. Coordinamos los cambios en el repositorio de GitHub.

**Diapositiva 12 (Lo que aprendimos)**

**CÉSAR:** Para cerrar, lo que nos llevamos de esta evaluación: entendimos cómo viaja un dato desde el formulario hasta el servidor Tomcat y vuelve convertido en página.

**ANGEL:** Aprendimos a usar los métodos de String para validar y transformar textos, y los de Math para resolver un cálculo real de dinero.

**CÉSAR:** Como mejora, nos gustaría guardar las cotizaciones en una base de datos y enviarlas por correo.

**AMBOS:** Muchas gracias, profesor.

---

## Preguntas que el profesor podría hacer

| Pregunta | Respuesta corta |
|---|---|
| ¿Por qué no pusieron la lógica en el JSP? | Para separar responsabilidades: el JSP muestra y las clases calculan. Así cada clase se prueba con `PruebasCotizador`. |
| ¿Por qué validan en el servidor si el formulario tiene `required`? | Porque el navegador se puede saltar esas reglas; el servidor es el que decide. |
| ¿Por qué `Math.ceil` en la cuota y no `Math.round`? | Con `round` la suma de cuotas podría quedar unos céntimos debajo del total; con `ceil` nunca. |
| ¿Qué hace `request.setCharacterEncoding("UTF-8")`? | Hace que las tildes y la ñ lleguen bien desde el formulario. |
| ¿Cómo se valida el correo sin expresiones regulares? | Con `contains`, `indexOf`, `lastIndexOf` y contando arrobas con `charAt`. |
| ¿Cómo se despliega en Tomcat? | `mvn clean package`, copiar `CotizadorWeb.war` a `webapps` e iniciar con `startup.bat`. |
