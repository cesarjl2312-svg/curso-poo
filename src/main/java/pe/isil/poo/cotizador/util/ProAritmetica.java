package pe.isil.poo.cotizador.util;

/**
 * Clase utilitaria con operaciones numéricas (Sesión 6: clase Math).
 * Todos los métodos son estáticos, igual que en la clase ProAritmetica vista en clase.
 */
public class ProAritmetica {

    private ProAritmetica() {
        // Clase utilitaria: no se instancia
    }

    /** Redondea un monto a 2 decimales (céntimos) con Math.round(). */
    public static double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    /**
     * Redondea hacia arriba al céntimo con Math.ceil().
     * Primero se redondea a 4 decimales para evitar errores de precisión de double
     * (por ejemplo 50.000000001 no debe convertirse en 50.01).
     */
    public static double redondearArriba(double valor) {
        double cuatroDecimales = Math.round(valor * 10000.0) / 100.0;
        return Math.ceil(cuatroDecimales) / 100.0;
    }

    /** Calcula el porcentaje de un monto: porcentaje(200, 15) = 30. */
    public static double porcentaje(double monto, double porcentaje) {
        return monto * porcentaje / 100.0;
    }

    /** Mantiene un valor dentro de un rango usando Math.max() y Math.min(). */
    public static int limitar(int valor, int minimo, int maximo) {
        return Math.max(minimo, Math.min(maximo, valor));
    }

    /**
     * Genera un número entero aleatorio entre min y max (incluidos)
     * con Math.random() y Math.floor(), como el método numeroAleatorio de la Sesión 6.
     */
    public static int numeroAleatorio(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }

    /**
     * Cuota fija mensual (sistema francés):
     *   cuota = monto * i / (1 - (1 + i)^-n)
     * Usa Math.pow(). Si no hay interés o es una sola cuota, se divide el monto entre n.
     */
    public static double cuotaFija(double monto, double tasaMensual, int numeroCuotas) {
        if (numeroCuotas <= 1 || tasaMensual <= 0) {
            return monto / Math.max(1, numeroCuotas);
        }
        return monto * tasaMensual / (1 - Math.pow(1 + tasaMensual, -numeroCuotas));
    }

    /** Diferencia absoluta entre dos montos con Math.abs(). */
    public static double diferencia(double a, double b) {
        return Math.abs(a - b);
    }
}
