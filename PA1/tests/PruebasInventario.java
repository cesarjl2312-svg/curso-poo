/** Pruebas sin bibliotecas externas: ejecutar despues de compilar src y tests. */
public class PruebasInventario {
    private static int verificaciones = 0;

    private static void comprobar(boolean condicion, String caso) {
        if (!condicion) {
            System.err.println("FALLO: " + caso);
            System.exit(1);
        }
        verificaciones++;
        System.out.println("OK: " + caso);
    }

    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        comprobar(inventario.getCantidad() == 0, "Inventario inicialmente vacio");
        comprobar(inventario.calcularValorTotal() == 0, "Valor inicial cero");
        comprobar(inventario.buscar("NO") == null, "Busqueda inexistente");
        comprobar(inventario.buscar(null) == null, "Busqueda nula");
        comprobar(!inventario.registrar("", "Arroz", 4.5, 10, 3), "Codigo vacio rechazado");
        comprobar(!inventario.registrar("A", " ", 4.5, 10, 3), "Nombre vacio rechazado");
        comprobar(!inventario.registrar(null, "Arroz", 4.5, 10, 3), "Codigo nulo rechazado");
        comprobar(!inventario.registrar("A", "Arroz", -1, 10, 3), "Precio negativo rechazado");
        comprobar(!inventario.registrar("A", "Arroz", 0, 10, 3), "Precio cero rechazado");
        comprobar(!inventario.registrar("A", "Arroz", Double.NaN, 10, 3), "NaN rechazado");
        comprobar(!inventario.registrar("A", "Arroz", Double.POSITIVE_INFINITY, 10, 3), "Infinito rechazado");
        comprobar(!inventario.registrar("A", "Arroz", 4.5, -1, 3), "Stock negativo rechazado");
        comprobar(!inventario.registrar("A", "Arroz", 4.5, 10, -1), "Minimo negativo rechazado");
        comprobar(inventario.registrar(" a01 ", "Arroz", 4.5, 10, 3), "Registro valido");
        comprobar(!inventario.registrar("A01", "Duplicado", 9, 5, 1), "Duplicado rechazado");
        comprobar(inventario.getCantidad() == 1, "Rechazos no alteran el contador");
        Producto producto = inventario.buscar("a01");
        comprobar(producto != null && producto.getCodigo().equals("A01"), "Codigo normalizado");
        comprobar(producto.obtenerEstado().equals("DISPONIBLE"), "Estado disponible");
        comprobar(!producto.retirarStock(11) && producto.getStock() == 10, "Stock insuficiente conserva estado");
        comprobar(!producto.retirarStock(0), "Retiro cero rechazado");
        comprobar(!producto.retirarStock(-1), "Retiro negativo rechazado");
        comprobar(!producto.ingresarStock(0), "Ingreso cero rechazado");
        comprobar(!producto.ingresarStock(-1), "Ingreso negativo rechazado");
        comprobar(producto.retirarStock(7) && producto.getStock() == 3, "Retiro valido");
        comprobar(producto.tieneStockBajo(), "Igual al minimo activa alerta");
        comprobar(producto.obtenerEstado().equals("STOCK BAJO"), "Estado stock bajo");
        comprobar(producto.retirarStock(3) && producto.getStock() == 0, "Retiro exacto agota stock");
        comprobar(producto.obtenerEstado().equals("AGOTADO"), "Estado agotado");
        comprobar(!producto.tieneStock(1), "Sin disponibilidad al estar agotado");
        comprobar(producto.ingresarStock(5) && producto.getStock() == 5, "Reposicion valida");
        comprobar(!producto.ingresarStock(Integer.MAX_VALUE), "Ingreso desbordante rechazado");
        comprobar(producto.getStock() == 5, "Rechazo conserva stock");
        comprobar(inventario.registrar("B01", "Aceite", 8.0, 2, 2), "Segundo producto independiente");
        comprobar(inventario.contarUnidades() == 7, "Acumulador de unidades");
        comprobar(inventario.contarStockBajo() == 1, "Contador de stock bajo");
        comprobar(Math.abs(inventario.calcularValorTotal() - 38.5) < 0.001, "Valorizacion de varios productos");
        comprobar(inventario.registrar("C01", "Sal", 1.235, 0, 0), "Registro sin existencias");
        comprobar(Math.abs(inventario.buscar("C01").getPrecio() - 1.24) < 0.001, "Precio redondeado a centimos");
        Inventario lleno = new Inventario();
        for (int i = 0; i < Inventario.CAPACIDAD; i++) {
            comprobar(lleno.registrar("P" + i, "Producto " + i, 1, Producto.STOCK_MAXIMO, 0),
                    "Registro en posicion " + i);
        }
        comprobar(lleno.estaLleno(), "Capacidad completa detectada");
        comprobar(!lleno.registrar("EXTRA", "Extra", 1, 1, 0), "Registro 101 rechazado");
        comprobar(lleno.contarUnidades() == 100000000, "Acumulador dentro del rango entero");
        comprobar(!lleno.buscar("P0").ingresarStock(1), "Stock maximo protegido");
        System.out.println("RESULTADO: " + verificaciones + " verificaciones correctas.");
    }
}
