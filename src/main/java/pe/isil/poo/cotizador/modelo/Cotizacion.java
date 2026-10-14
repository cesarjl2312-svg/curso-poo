package pe.isil.poo.cotizador.modelo;

import pe.isil.poo.cotizador.util.ProAritmetica;

/**
 * Cotización de un producto para un cliente.
 * Al construirse calcula todos los montos con la clase ProAritmetica (Math).
 */
public class Cotizacion {

    public static final double TASA_IGV = 0.18;            // 18 %
    public static final double TASA_MENSUAL = 0.015;       // 1.5 % mensual para pagos en cuotas
    public static final int CANTIDAD_DESCUENTO_VOLUMEN = 10;
    public static final int PORCENTAJE_DESCUENTO_VOLUMEN = 5;
    public static final int TOPE_DESCUENTO = 20;           // el descuento total nunca supera 20 %

    private final String codigo;
    private final Cliente cliente;
    private final Producto producto;
    private final int cantidad;
    private final int numeroCuotas;
    private final int porcentajeCupon;

    private double subtotal;
    private int porcentajeVolumen;
    private int porcentajeDescuento;
    private double montoDescuento;
    private double valorVenta;
    private double igv;
    private double total;
    private double cuota;
    private double totalFinanciado;
    private double intereses;

    public Cotizacion(Cliente cliente, Producto producto, int cantidad, int numeroCuotas, int porcentajeCupon) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.numeroCuotas = numeroCuotas;
        this.porcentajeCupon = porcentajeCupon;
        calcular();
        this.codigo = generarCodigo();
    }

    /** Proceso de cálculo: subtotal, descuento, IGV, total y cuotas. */
    private void calcular() {
        subtotal = ProAritmetica.redondear(producto.getPrecioUnitario() * cantidad);

        porcentajeVolumen = cantidad >= CANTIDAD_DESCUENTO_VOLUMEN ? PORCENTAJE_DESCUENTO_VOLUMEN : 0;
        // Math.min / Math.max: el descuento acumulado queda entre 0 % y el tope
        porcentajeDescuento = ProAritmetica.limitar(porcentajeVolumen + porcentajeCupon, 0, TOPE_DESCUENTO);
        montoDescuento = ProAritmetica.redondear(ProAritmetica.porcentaje(subtotal, porcentajeDescuento));

        valorVenta = ProAritmetica.redondear(subtotal - montoDescuento);
        igv = ProAritmetica.redondear(valorVenta * TASA_IGV);
        total = ProAritmetica.redondear(valorVenta + igv);

        double tasa = numeroCuotas > 1 ? TASA_MENSUAL : 0;
        // La cuota se redondea hacia arriba (Math.ceil) para no cobrar de menos
        cuota = ProAritmetica.redondearArriba(ProAritmetica.cuotaFija(total, tasa, numeroCuotas));
        totalFinanciado = ProAritmetica.redondear(cuota * numeroCuotas);
        intereses = ProAritmetica.redondear(Math.max(0, totalFinanciado - total));
    }

    /**
     * Código único de la cotización: COT-{iniciales}-{3 últimos dígitos del DNI}-{aleatorio}.
     * Combina String (substring) y Math (Math.random, Math.min).
     */
    private String generarCodigo() {
        String iniciales = cliente.getIniciales();
        iniciales = iniciales.substring(0, Math.min(4, iniciales.length()));
        String dni = cliente.getDni();
        String finDni = dni.substring(Math.max(0, dni.length() - 3));
        int aleatorio = ProAritmetica.numeroAleatorio(1000, 9999);
        return "COT-" + iniciales + "-" + finDni + "-" + aleatorio;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getNumeroCuotas() {
        return numeroCuotas;
    }

    public int getPorcentajeCupon() {
        return porcentajeCupon;
    }

    public int getPorcentajeVolumen() {
        return porcentajeVolumen;
    }

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getMontoDescuento() {
        return montoDescuento;
    }

    public double getValorVenta() {
        return valorVenta;
    }

    public double getIgv() {
        return igv;
    }

    public double getTotal() {
        return total;
    }

    public double getCuota() {
        return cuota;
    }

    public double getTotalFinanciado() {
        return totalFinanciado;
    }

    public double getIntereses() {
        return intereses;
    }

    /** Precio final por unidad (incluye descuento e IGV). */
    public double getPrecioFinalUnitario() {
        return ProAritmetica.redondear(total / cantidad);
    }

    /** true si el descuento acumulado superaba el tope y se tuvo que limitar. */
    public boolean isDescuentoLimitado() {
        return porcentajeVolumen + porcentajeCupon > TOPE_DESCUENTO;
    }
}
