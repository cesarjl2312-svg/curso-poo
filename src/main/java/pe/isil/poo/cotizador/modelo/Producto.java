package pe.isil.poo.cotizador.modelo;

/** Producto o servicio que se puede cotizar. */
public class Producto {

    private final String codigo;
    private final String nombre;
    private final String unidad;
    private final double precioUnitario; // valor de venta sin IGV, en soles

    public Producto(String codigo, String nombre, String unidad, double precioUnitario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.unidad = unidad;
        this.precioUnitario = precioUnitario;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }
}
