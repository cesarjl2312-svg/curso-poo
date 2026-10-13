/** Contenedor de capacidad fija, sin colecciones ni bases de datos. */
public class Inventario {
    public static final int CAPACIDAD = 100;
    private Producto[] productos;
    private int cantidad;

    public Inventario() {
        productos = new Producto[CAPACIDAD];
        cantidad = 0;
    }

    public int getCantidad() { return cantidad; }
    public boolean estaLleno() { return cantidad == CAPACIDAD; }

    public Producto buscar(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (int i = 0; i < cantidad; i++) {
            if (productos[i].getCodigo().equalsIgnoreCase(codigo.trim())) {
                return productos[i];
            }
        }
        return null;
    }

    public boolean registrar(String codigo, String nombre, double precio, int stock, int minimo) {
        if (estaLleno() || codigo == null || nombre == null) {
            return false;
        }
        if (codigo.trim().isEmpty() || nombre.trim().isEmpty()
                || codigo.trim().length() > 20 || nombre.trim().length() > 60
                || buscar(codigo) != null) {
            return false;
        }
        // Esta forma de comparar tambien rechaza NaN e infinitos.
        if (!(precio >= 0.01 && precio <= Producto.PRECIO_MAXIMO)
                || stock < 0 || stock > Producto.STOCK_MAXIMO
                || minimo < 0 || minimo > Producto.STOCK_MAXIMO) {
            return false;
        }
        productos[cantidad] = new Producto(codigo, nombre, precio, stock, minimo);
        cantidad++; // Contador de productos registrados.
        return true;
    }

    public void listar(boolean soloStockBajo) {
        int mostrados = 0;
        for (int i = 0; i < cantidad; i++) {
            if (!soloStockBajo || productos[i].tieneStockBajo()) {
                productos[i].mostrarInformacion();
                mostrados++;
            }
        }
        if (mostrados == 0) {
            System.out.println("No hay productos para mostrar.");
        }
    }

    public int contarUnidades() {
        int unidades = 0;
        for (int i = 0; i < cantidad; i++) {
            unidades += productos[i].getStock(); // Acumulador de unidades.
        }
        return unidades;
    }

    public int contarStockBajo() {
        int bajos = 0;
        for (int i = 0; i < cantidad; i++) {
            if (productos[i].tieneStockBajo()) {
                bajos++;
            }
        }
        return bajos;
    }

    public double calcularValorTotal() {
        double total = 0;
        for (int i = 0; i < cantidad; i++) {
            total += productos[i].calcularValorStock();
        }
        return total;
    }
}
