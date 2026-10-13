# Evidencia de pruebas

Verificación realizada el 23 de septiembre de 2026 con OpenJDK 21, compilando para compatibilidad con Java 8 mediante `--release 8`.

## Resultado observado

- Compilación completada correctamente.
- **142 verificaciones de dominio correctas**, ejecutadas con `PruebasInventario`.
- **14 escenarios de consola correctos**, ejecutados con `tests/test_consola.py`.
- El compilador avisa que el destino Java 8 es antiguo. Son avisos sobre la versión de destino, sin errores de compilación.

## Cobertura

| Área | Casos comprobados | Resultado |
| --- | --- | --- |
| Inventario vacío | Consulta inexistente, cantidades y valorización inicial | Correcto |
| Registro | Código/nombre vacíos, nulos, duplicados, normalización y registro válido | Correcto |
| Precio | Negativo, cero, NaN, infinito, coma decimal y redondeo | Correcto |
| Existencias | Stock inicial negativo, ingresos/retiros cero o negativos y operación válida | Correcto |
| Protección del estado | Retiro excesivo, ingreso que supera el máximo, cantidad desbordante | Correcto |
| Alertas | Disponible, igualdad al mínimo, agotado y reposición | Correcto |
| Resumen | Varios productos, conteo de alertas, unidades y valorización | Correcto |
| Capacidad | Registro de 100 productos y rechazo del producto 101 | Correcto |
| Consola | Menú erróneo, letras, valores fuera de rango y varios valores en una línea | Correcto |
| Fin de entrada | Salida sin datos, registro incompleto y movimiento incompleto | Correcto |

## Demostración reproducible

1. A01: Arroz, precio 4.50, stock inicial 10, mínimo 3.
2. B01: Aceite, precio 8.00, stock inicial 2, mínimo 2.
3. Retirar 7 unidades de A01: queda en 3.
4. Ingresar 2 unidades de A01: queda en 5.
5. Resumen obtenido: 2 productos, 7 unidades, 1 producto con stock bajo y S/ 38.50.

La transcripción real se conserva en [ejecucion-ejemplo.txt](ejecucion-ejemplo.txt). Los comandos para repetir las pruebas están en el README. Las pruebas verifican el programa; no acreditan participación grupal, asistencia ni una grabación de exposición.
