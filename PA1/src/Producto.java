import java.util.Locale;

/** Representa un producto y controla las operaciones sobre su stock. */
public class Producto {
    public static final int STOCK_MAXIMO = 1000000;
    public static final double PRECIO_MAXIMO = 1000000.0;

    private String codigo;
    private String nombre;
    private double precio;
    private int stock;
    private int stockMinimo;

    public Producto(String codigo, String nombre, double precio, int stock, int stockMinimo) {
        // El registro valida los datos antes de construir el objeto.
        this.codigo = codigo.trim().toUpperCase(Locale.ROOT);
        this.nombre = nombre.trim();
        this.precio = Math.round(precio * 100.0) / 100.0;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public int getStockMinimo() { return stockMinimo; }

    public boolean tieneStock(int cantidad) {
        return cantidad > 0 && cantidad <= stock;
    }

    public boolean ingresarStock(int cantidad) {
        if (cantidad > 0 && cantidad <= STOCK_MAXIMO - stock) {
            stock += cantidad;
            return true;
        }
        return false;
    }

    public boolean retirarStock(int cantidad) {
        if (tieneStock(cantidad)) {
            stock -= cantidad;
            return true;
        }
        return false;
    }

    public boolean tieneStockBajo() {
        return stock <= stockMinimo;
    }

    public double calcularValorStock() {
        return precio * stock;
    }

    public String obtenerEstado() {
        if (stock == 0) {
            return "AGOTADO";
        } else {
            if (tieneStockBajo()) {
                return "STOCK BAJO";
            } else {
                return "DISPONIBLE";
            }
        }
    }

    public void mostrarInformacion() {
        System.out.printf(Locale.US,
                "%s | %s | Precio: S/ %.2f | Stock: %d | Minimo: %d | %s%n",
                codigo, nombre, precio, stock, stockMinimo, obtenerEstado());
    }
}
