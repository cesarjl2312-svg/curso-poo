# Pruebas realizadas

## 1. Pruebas por consola (`PruebasCotizador`)

Se ejecutó la clase `pe.isil.poo.cotizador.pruebas.PruebasCotizador`. Resultado: **46 correctas, 0 fallidas**.

```
===== PRUEBAS DE ProCadenas (String) =====
[OK]    normalizarEspacios
[OK]    capitalizarPalabras
[OK]    capitalizarPalabras con tildes
[OK]    obtenerIniciales
[OK]    contarPalabras
[OK]    esNumerico verdadero
[OK]    esNumerico falso
[OK]    esSoloLetrasYEspacios con ñ
[OK]    esSoloLetrasYEspacios con número
[OK]    cuentaOcurrenciasCaracter
[OK]    correo válido
[OK]    correo sin arroba
[OK]    correo con dos arrobas
[OK]    correo sin extensión
[OK]    correo con espacio
[OK]    enmascararCorreo
[OK]    escaparHtml

===== PRUEBAS DE ProAritmetica (Math) =====
[OK]    redondear (Math.round)
[OK]    redondearArriba (Math.ceil)
[OK]    redondearArriba sin error de double
[OK]    porcentaje
[OK]    limitar por arriba (Math.min)
[OK]    limitar por abajo (Math.max)
[OK]    diferencia (Math.abs)
[OK]    cuotaFija con 1 cuota
[OK]    numeroAleatorio dentro del rango (1000 intentos)

===== PRUEBAS DE ValidadorDatos =====
[OK]    datos correctos no generan errores
[OK]    datos incorrectos generan 8 errores
[OK]    cupón PROMO15 -> 15
[OK]    cupón PROMO25 no válido
[OK]    cupón PROMO7 no válido

===== PRUEBAS DE Cotizacion =====
[OK]    cliente normalizado
[OK]    celular con formato
[OK]    caso 1 subtotal 239.80
[OK]    caso 1 IGV 43.16
[OK]    caso 1 total 282.96
[OK]    caso 1 sin intereses
[OK]    caso 2 descuento 15 %
[OK]    caso 2 descuento 4348.50
[OK]    caso 2 total 29076.97
[OK]    caso 2 cuota 5103.75
[OK]    caso 2 intereses 1545.53
[OK]    caso 3 descuento limitado a 20 %
[OK]    caso 3 total 11137.31
[OK]    caso 3 cuota 1021.07
[OK]    código de cotización

Resultado: 46 correctas, 0 fallidas.
```

## 2. Pruebas funcionales en el navegador (Tomcat)

| # | Entrada | Resultado esperado | Resultado obtenido |
|---|---|---|---|
| 1 | 2 × `KIT-004`, al contado, sin cupón | Subtotal S/ 239.80, IGV S/ 43.16, total S/ 282.96 | ✅ Correcto |
| 2 | 10 × `LAP-001`, 6 cuotas, cupón `promo10` | Descuento 15 % (5 % volumen + 10 % cupón), total S/ 29,076.97, 6 cuotas de S/ 5,103.75, intereses S/ 1,545.53 | ✅ Correcto |
| 3 | 20 × `MON-002`, 12 cuotas, cupón `PROMO20` | Descuento limitado al 20 % (5 + 20 = 25), total S/ 11,137.31, 12 cuotas de S/ 1,021.07 | ✅ Correcto |
| 4 | Nombre `  aNGEL   fernando reyes  MORENO` | Se muestra `Angel Fernando Reyes Moreno`, iniciales `AFRM` | ✅ Correcto |
| 5 | Nombre `ÁNGEL   muñoz peña` | Se muestra `Ángel Muñoz Peña` (tildes y ñ correctas) | ✅ Correcto |
| 6 | Correo `Angel.Reyes@Gmail.com` | Se guarda en minúsculas y se muestra `an*********@gmail.com` | ✅ Correcto |
| 7 | Nombre `Juan2`, DNI `1234`, correo `juan@@mail`, celular `812345678`, producto inválido, cantidad `0`, cuotas `5`, cupón `PROMO25` | 8 mensajes de error, no se calcula la cotización | ✅ Correcto |
| 8 | Botón **Corregir datos** después de un error | Vuelve al formulario con los datos ingresados y la opción seleccionada | ✅ Correcto |
| 9 | Observaciones `<script>alert(1)</script>` | Se muestra como texto (`&lt;script&gt;`), no se ejecuta | ✅ Correcto |
| 10 | Vista en celular (390 px de ancho) | El formulario se ve en una sola columna | ✅ Correcto |

Las capturas de estas pruebas están en la carpeta [`evidencias/`](evidencias/).

## 3. Verificación manual del caso 2

```
Subtotal         = 2,899.00 × 10                     = 28,990.00
Descuento 15 %   = 28,990.00 × 15 / 100              =  4,348.50
Valor de venta   = 28,990.00 - 4,348.50              = 24,641.50
IGV 18 %         = 24,641.50 × 0.18                  =  4,435.47
Total            = 24,641.50 + 4,435.47              = 29,076.97
Cuota (i = 0.015, n = 6)
                 = 29,076.97 × 0.015 / (1 - 1.015^-6) =  5,103.7414 → Math.ceil → 5,103.75
Total financiado = 5,103.75 × 6                      = 30,622.50
Intereses        = 30,622.50 - 29,076.97             =  1,545.53
```
