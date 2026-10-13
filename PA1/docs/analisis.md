# Análisis del caso

## Necesidad y alcance

La microempresa necesita registrar varios productos y controlar sus existencias desde un menú. Cada código identifica un solo producto. Los ingresos suman unidades y los retiros solo se realizan cuando hay unidades suficientes. Las consultas y el resumen permiten revisar el estado actual.

Se adopta una capacidad de 100 productos para usar un contenedor sencillo. No se necesita editar o eliminar artículos, gestionar usuarios ni conservar datos entre ejecuciones según el alcance planteado. El stock mínimo se registra por producto, porque distintos artículos pueden requerir diferentes niveles de reposición.

## Clases y atributos

| Clase | Atributos | Responsabilidad |
| --- | --- | --- |
| Producto | `codigo: String`, `nombre: String`, `precio: double`, `stock: int`, `stockMinimo: int` | Conservar datos de una instancia y controlar sus existencias |
| Producto | `STOCK_MAXIMO: static final int`, `PRECIO_MAXIMO: static final double` | Límites compartidos por todos los productos |
| Inventario | `productos: Producto[]`, `cantidad: int`, `CAPACIDAD: static final int` | Almacenar productos y evitar duplicados |
| Aplicacion | `teclado: static Scanner`, `finEntrada: static boolean`, `inventario: static Inventario` | Coordinar una sesión por consola desde `main` |

Los atributos de los objetos se mantienen privados. Los métodos de consulta exponen únicamente la información necesaria. La aplicación solicita los datos y `Inventario.registrar` los valida antes de crear un `Producto`. El constructor de `Producto` tiene como precondición recibir datos validados; no se emplean excepciones personalizadas, tema fuera del alcance de esta evaluación.

## Constructores y métodos

| Clase | Método | Resultado o efecto |
| --- | --- | --- |
| Producto | Constructor con cinco parámetros | Inicializa el artículo y normaliza código y precio |
| Producto | `mostrarInformacion()` | Imprime datos, stock y estado; no retorna valor |
| Producto | `tieneStock(cantidad)` | Devuelve si puede atender una cantidad positiva |
| Producto | `ingresarStock(cantidad)` | Suma existencias válidas y devuelve éxito o rechazo |
| Producto | `retirarStock(cantidad)` | Resta existencias disponibles y devuelve éxito o rechazo |
| Producto | `tieneStockBajo()`, `obtenerEstado()` | Determinan alerta y estado descriptivo |
| Producto | `calcularValorStock()` | Calcula precio por unidades disponibles |
| Inventario | Constructor sin argumentos | Inicializa el arreglo y el contador |
| Inventario | `registrar(...)`, `buscar(codigo)` | Validan/registran y localizan productos |
| Inventario | `listar(soloStockBajo)` | Recorre e imprime productos según el filtro |
| Inventario | `contarUnidades()`, `contarStockBajo()`, `calcularValorTotal()` | Elaboran el resumen mediante recorridos |
| Aplicacion | `main`, `mostrarMenu`, `registrarProducto`, `moverStock` | Ejecutan el flujo principal |
| Aplicacion | `leerTexto`, `leerEntero`, `leerPrecio` | Repiten solicitudes hasta obtener entradas válidas o fin de entrada |

## Reglas de validación

1. Código de 1 a 20 caracteres y nombre de 1 a 60, sin contar espacios exteriores.
2. Código único, comparado sin distinguir mayúsculas y minúsculas.
3. Precio positivo dentro del límite, sin NaN ni infinito. Redondeo a dos decimales al registrar.
4. Stock inicial y mínimo no negativos. El mínimo puede ser mayor que el stock inicial: representa una necesidad de reposición inmediata.
5. Movimiento estrictamente positivo. Retiro máximo igual al stock actual. Ingreso máximo igual a la capacidad restante del stock.
6. Stock bajo cuando `stock <= stockMinimo`. Si el stock es cero se presenta el estado `AGOTADO` y también se incluye en las alertas.
7. Las operaciones inválidas no cambian datos ni contadores. El fin de entrada termina la sesión sin registrar parcialmente un producto ni ejecutar un movimiento incompleto.

## Control del flujo

`main` muestra el menú al menos una vez con `do while`. La opción se evalúa en un `switch`. Los lectores usan `while` para solicitar nuevamente datos erróneos sin terminar el programa. `Inventario` usa `for` hasta `cantidad`, de modo que no accede a posiciones vacías. `Producto.obtenerEstado` utiliza condiciones anidadas.

`cantidad` y `bajos` son contadores. `unidades` y `total` son acumuladores. Con 100 productos y hasta un millón de unidades por producto, el acumulador de unidades llega como máximo a 100 millones, dentro del rango de `int`.
