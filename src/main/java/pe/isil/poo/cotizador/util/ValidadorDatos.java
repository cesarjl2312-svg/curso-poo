package pe.isil.poo.cotizador.util;

import java.util.ArrayList;
import java.util.List;
import pe.isil.poo.cotizador.modelo.Catalogo;

/**
 * Valida los datos que llegan del formulario antes de calcular la cotización.
 * Devuelve la lista de mensajes de error; si está vacía, los datos son correctos.
 */
public class ValidadorDatos {

    public static final int CANTIDAD_MINIMA = 1;
    public static final int CANTIDAD_MAXIMA = 100;
    public static final int MAX_OBSERVACIONES = 150;
    public static final int[] CUOTAS_PERMITIDAS = {1, 3, 6, 12};
    public static final String PREFIJO_CUPON = "PROMO";

    private ValidadorDatos() {
    }

    public static List<String> validar(String nombre, String dni, String correo, String celular,
                                       String codigoProducto, String cantidad, String cuotas,
                                       String cupon, String observaciones) {
        List<String> errores = new ArrayList<>();

        // Nombre: obligatorio, solo letras, al menos nombre y apellido
        String nombreLimpio = ProCadenas.normalizarEspacios(nombre);
        if (nombreLimpio.isEmpty()) {
            errores.add("El nombre completo es obligatorio.");
        } else if (!ProCadenas.esSoloLetrasYEspacios(nombreLimpio)) {
            errores.add("El nombre solo puede contener letras y espacios.");
        } else if (ProCadenas.contarPalabras(nombreLimpio) < 2) {
            errores.add("Ingrese al menos un nombre y un apellido.");
        } else if (nombreLimpio.length() > 60) {
            errores.add("El nombre no puede superar los 60 caracteres.");
        }

        // DNI: exactamente 8 dígitos
        String dniLimpio = ProCadenas.quitarEspacios(dni);
        if (dniLimpio.length() != 8 || !ProCadenas.esNumerico(dniLimpio)) {
            errores.add("El DNI debe tener exactamente 8 dígitos.");
        }

        // Correo
        if (!ProCadenas.esCorreoValido(correo)) {
            errores.add("El correo electrónico no tiene un formato válido (ejemplo: nombre@dominio.com).");
        }

        // Celular: 9 dígitos y empieza con 9
        String celularLimpio = ProCadenas.quitarEspacios(celular);
        if (celularLimpio.length() != 9 || !ProCadenas.esNumerico(celularLimpio)
                || !celularLimpio.startsWith("9")) {
            errores.add("El celular debe tener 9 dígitos y empezar con 9.");
        }

        // Producto
        if (Catalogo.buscar(codigoProducto) == null) {
            errores.add("Seleccione un producto válido del catálogo.");
        }

        // Cantidad: número entero entre 1 y 100
        String cantidadLimpia = ProCadenas.quitarEspacios(cantidad);
        if (!ProCadenas.esNumerico(cantidadLimpia) || cantidadLimpia.length() > 3) {
            errores.add("La cantidad debe ser un número entero entre " + CANTIDAD_MINIMA
                    + " y " + CANTIDAD_MAXIMA + ".");
        } else {
            int n = Integer.parseInt(cantidadLimpia);
            if (n < CANTIDAD_MINIMA || n > CANTIDAD_MAXIMA) {
                errores.add("La cantidad debe estar entre " + CANTIDAD_MINIMA + " y " + CANTIDAD_MAXIMA + ".");
            }
        }

        // Cuotas: solo 1, 3, 6 o 12
        if (!esCuotaPermitida(cuotas)) {
            errores.add("El número de cuotas debe ser 1, 3, 6 o 12.");
        }

        // Cupón: opcional
        if (!ProCadenas.estaVacia(cupon) && obtenerPorcentajeCupon(cupon) == 0) {
            errores.add("El cupón no es válido. Formato: PROMO + porcentaje múltiplo de 5 entre 5 y 20 "
                    + "(ejemplo: PROMO10).");
        }

        // Observaciones: opcional, con límite de caracteres
        if (observaciones != null && observaciones.trim().length() > MAX_OBSERVACIONES) {
            errores.add("Las observaciones no pueden superar los " + MAX_OBSERVACIONES + " caracteres.");
        }

        return errores;
    }

    /** Verifica que el número de cuotas sea uno de los permitidos. */
    public static boolean esCuotaPermitida(String cuotas) {
        String c = ProCadenas.quitarEspacios(cuotas);
        if (!ProCadenas.esNumerico(c) || c.length() > 2) {
            return false;
        }
        int n = Integer.parseInt(c);
        for (int permitida : CUOTAS_PERMITIDAS) {
            if (n == permitida) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lee el porcentaje de un cupón con métodos de String.
     * "promo15" -> toUpperCase -> "PROMO15" -> startsWith("PROMO") -> substring(5) -> "15" -> 15.
     * Devuelve 0 si el cupón está vacío o no es válido.
     */
    public static int obtenerPorcentajeCupon(String cupon) {
        if (ProCadenas.estaVacia(cupon)) {
            return 0;
        }
        String c = ProCadenas.quitarEspacios(cupon).toUpperCase();
        if (!c.startsWith(PREFIJO_CUPON)) {
            return 0;
        }
        String numero = c.substring(PREFIJO_CUPON.length());
        if (!ProCadenas.esNumerico(numero) || numero.length() > 2) {
            return 0;
        }
        int porcentaje = Integer.parseInt(numero);
        boolean enRango = porcentaje >= 5 && porcentaje <= 20;
        boolean multiploDeCinco = porcentaje % 5 == 0;
        return enRango && multiploDeCinco ? porcentaje : 0;
    }
}
