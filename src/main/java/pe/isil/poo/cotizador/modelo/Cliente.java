package pe.isil.poo.cotizador.modelo;

import pe.isil.poo.cotizador.util.ProCadenas;

/**
 * Datos del cliente. Al crearse, el objeto guarda los datos ya normalizados
 * con los métodos de la clase ProCadenas (String).
 */
public class Cliente {

    private final String nombre;
    private final String dni;
    private final String correo;
    private final String celular;

    public Cliente(String nombre, String dni, String correo, String celular) {
        this.nombre = ProCadenas.capitalizarPalabras(nombre);
        this.dni = ProCadenas.quitarEspacios(dni);
        this.correo = correo == null ? "" : correo.trim().toLowerCase();
        this.celular = ProCadenas.quitarEspacios(celular);
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCelular() {
        return celular;
    }

    /** "Angel Reyes Moreno" -> "ARM" */
    public String getIniciales() {
        return ProCadenas.obtenerIniciales(nombre);
    }

    /** Correo con la parte del usuario oculta, para mostrarlo en la cotización. */
    public String getCorreoOculto() {
        return ProCadenas.enmascararCorreo(correo);
    }

    /** Celular con formato "987 654 321" usando substring(). */
    public String getCelularFormateado() {
        if (celular.length() != 9) {
            return celular;
        }
        return celular.substring(0, 3) + " " + celular.substring(3, 6) + " " + celular.substring(6);
    }
}
