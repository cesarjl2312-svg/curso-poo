package pe.isil.poo.cotizador.util;

/**
 * Clase utilitaria con operaciones sobre cadenas (Sesión 7: clase String).
 * Todos los métodos son estáticos, igual que en la clase ProCadenas vista en clase.
 */
public class ProCadenas {

    private ProCadenas() {
        // Clase utilitaria: no se instancia
    }

    /** Devuelve true si la cadena es null, vacía o solo tiene espacios. Usa trim() e isEmpty(). */
    public static boolean estaVacia(String cadena) {
        return cadena == null || cadena.trim().isEmpty();
    }

    /**
     * Quita espacios al inicio y al final (trim) y deja un solo espacio entre palabras.
     * Recorre la cadena carácter por carácter con charAt().
     */
    public static String normalizarEspacios(String cadena) {
        if (cadena == null) {
            return "";
        }
        String texto = cadena.trim();
        String resultado = "";
        for (int i = 0; i < texto.length(); i++) {
            char actual = texto.charAt(i);
            boolean esEspacio = Character.isWhitespace(actual);
            boolean anteriorEsEspacio = i > 0 && Character.isWhitespace(texto.charAt(i - 1));
            if (esEspacio && anteriorEsEspacio) {
                continue; // se omite el espacio repetido
            }
            resultado += esEspacio ? ' ' : actual;
        }
        return resultado;
    }

    /** Elimina todos los espacios de la cadena con replace(). */
    public static String quitarEspacios(String cadena) {
        if (cadena == null) {
            return "";
        }
        return cadena.trim().replace(" ", "");
    }

    /** Devuelve true si todos los caracteres son dígitos (0-9). */
    public static boolean esNumerico(String cadena) {
        if (estaVacia(cadena)) {
            return false;
        }
        for (int i = 0; i < cadena.length(); i++) {
            if (!Character.isDigit(cadena.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /** Devuelve true si la cadena solo contiene letras (incluye tildes y ñ) y espacios. */
    public static boolean esSoloLetrasYEspacios(String cadena) {
        if (estaVacia(cadena)) {
            return false;
        }
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * Convierte "aNGEL   reyes" en "Angel Reyes".
     * Usa split(), substring(), toUpperCase(), toLowerCase() y String.join().
     */
    public static String capitalizarPalabras(String cadena) {
        String texto = normalizarEspacios(cadena);
        if (texto.isEmpty()) {
            return "";
        }
        String[] palabras = texto.split(" ");
        for (int i = 0; i < palabras.length; i++) {
            String p = palabras[i];
            palabras[i] = p.substring(0, 1).toUpperCase() + p.substring(1).toLowerCase();
        }
        return String.join(" ", palabras);
    }

    /** Cuenta las palabras de un texto usando split(). */
    public static int contarPalabras(String cadena) {
        String texto = normalizarEspacios(cadena);
        if (texto.isEmpty()) {
            return 0;
        }
        return texto.split(" ").length;
    }

    /** Obtiene las iniciales en mayúscula: "Angel Reyes Moreno" -> "ARM". Usa charAt(). */
    public static String obtenerIniciales(String cadena) {
        String texto = normalizarEspacios(cadena);
        if (texto.isEmpty()) {
            return "";
        }
        String iniciales = "";
        for (String palabra : texto.split(" ")) {
            iniciales += palabra.charAt(0);
        }
        return iniciales.toUpperCase();
    }

    /** Cuenta cuántas veces aparece un carácter en la cadena (método visto en la Sesión 7). */
    public static int cuentaOcurrenciasCaracter(String cadena, char caracter) {
        if (cadena == null) {
            return 0;
        }
        int contador = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) == caracter) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Valida un correo con métodos de String:
     * sin espacios (contains), una sola arroba (cuentaOcurrenciasCaracter),
     * texto antes de la arroba (indexOf) y un punto después de ella (lastIndexOf)
     * con una extensión de al menos 2 letras.
     */
    public static boolean esCorreoValido(String correo) {
        if (estaVacia(correo)) {
            return false;
        }
        String c = correo.trim().toLowerCase();
        if (c.contains(" ") || cuentaOcurrenciasCaracter(c, '@') != 1) {
            return false;
        }
        int posArroba = c.indexOf('@');
        int posPunto = c.lastIndexOf('.');
        boolean hayUsuario = posArroba > 0;
        boolean hayDominio = posPunto > posArroba + 1;
        boolean hayExtension = posPunto < c.length() - 2;
        return hayUsuario && hayDominio && hayExtension && !c.endsWith(".");
    }

    /**
     * Oculta parte del correo para mostrarlo en pantalla: "angel.reyes@gmail.com" -> "an*********@gmail.com".
     * Usa indexOf(), substring() y repeat().
     */
    public static String enmascararCorreo(String correo) {
        if (!esCorreoValido(correo)) {
            return "";
        }
        String c = correo.trim().toLowerCase();
        int posArroba = c.indexOf('@');
        String usuario = c.substring(0, posArroba);
        String dominio = c.substring(posArroba);
        int visibles = Math.min(2, usuario.length());
        return usuario.substring(0, visibles) + "*".repeat(usuario.length() - visibles) + dominio;
    }

    /**
     * Reemplaza los caracteres especiales de HTML con replace() para mostrar
     * de forma segura en la página lo que escribió el usuario.
     */
    public static String escaparHtml(String cadena) {
        if (cadena == null) {
            return "";
        }
        return cadena.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
