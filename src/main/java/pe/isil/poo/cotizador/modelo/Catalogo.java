package pe.isil.poo.cotizador.modelo;

import java.util.ArrayList;
import java.util.List;

/** Catálogo fijo de productos y servicios de la organización. */
public class Catalogo {

    private static final List<Producto> PRODUCTOS = new ArrayList<>();

    static {
        PRODUCTOS.add(new Producto("LAP-001", "Laptop Core i5, 16 GB RAM, 512 GB SSD", "unidad", 2899.00));
        PRODUCTOS.add(new Producto("MON-002", "Monitor LED 24\" Full HD", "unidad", 589.90));
        PRODUCTOS.add(new Producto("IMP-003", "Impresora multifuncional de tinta continua", "unidad", 749.00));
        PRODUCTOS.add(new Producto("KIT-004", "Kit de teclado y mouse inalámbrico", "unidad", 119.90));
        PRODUCTOS.add(new Producto("SOP-005", "Soporte técnico a domicilio", "hora", 85.00));
    }

    private Catalogo() {
    }

    /** Devuelve todos los productos del catálogo. */
    public static List<Producto> listar() {
        return PRODUCTOS;
    }

    /** Busca un producto por su código sin distinguir mayúsculas (equalsIgnoreCase). */
    public static Producto buscar(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (Producto p : PRODUCTOS) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return p;
            }
        }
        return null;
    }
}
