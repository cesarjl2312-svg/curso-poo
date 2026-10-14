package pe.isil.poo.cotizador.pruebas;

import java.util.List;
import pe.isil.poo.cotizador.modelo.Catalogo;
import pe.isil.poo.cotizador.modelo.Cliente;
import pe.isil.poo.cotizador.modelo.Cotizacion;
import pe.isil.poo.cotizador.util.ProAritmetica;
import pe.isil.poo.cotizador.util.ProCadenas;
import pe.isil.poo.cotizador.util.ValidadorDatos;

/**
 * Pruebas por consola de las clases del proyecto (igual que el método main
 * de ProAritmetica y ProCadenas en las sesiones 6 y 7).
 * Se ejecuta con el botón "Run" sobre esta clase en el IDE.
 */
public class PruebasCotizador {

    private static int correctas = 0;
    private static int fallidas = 0;

    public static void main(String[] args) {
        System.out.println("===== PRUEBAS DE ProCadenas (String) =====");
        verificar("normalizarEspacios", ProCadenas.normalizarEspacios("  ana   maría  ").equals("ana maría"));
        verificar("capitalizarPalabras", ProCadenas.capitalizarPalabras("aNGEL   fernando REYES").equals("Angel Fernando Reyes"));
        verificar("capitalizarPalabras con tildes", ProCadenas.capitalizarPalabras("ÁNGEL muñoz").equals("Ángel Muñoz"));
        verificar("obtenerIniciales", ProCadenas.obtenerIniciales("Angel Fernando Reyes Moreno").equals("AFRM"));
        verificar("contarPalabras", ProCadenas.contarPalabras("  uno  dos   tres ") == 3);
        verificar("esNumerico verdadero", ProCadenas.esNumerico("75729271"));
        verificar("esNumerico falso", !ProCadenas.esNumerico("7572A271"));
        verificar("esSoloLetrasYEspacios con ñ", ProCadenas.esSoloLetrasYEspacios("Ñañez Peña"));
        verificar("esSoloLetrasYEspacios con número", !ProCadenas.esSoloLetrasYEspacios("Juan2 Perez"));
        verificar("cuentaOcurrenciasCaracter", ProCadenas.cuentaOcurrenciasCaracter("a@b@c", '@') == 2);
        verificar("correo válido", ProCadenas.esCorreoValido("Angel.Reyes@gmail.com"));
        verificar("correo sin arroba", !ProCadenas.esCorreoValido("angel.gmail.com"));
        verificar("correo con dos arrobas", !ProCadenas.esCorreoValido("an@gel@gmail.com"));
        verificar("correo sin extensión", !ProCadenas.esCorreoValido("angel@gmail"));
        verificar("correo con espacio", !ProCadenas.esCorreoValido("angel reyes@gmail.com"));
        verificar("enmascararCorreo", ProCadenas.enmascararCorreo("angel@gmail.com").equals("an***@gmail.com"));
        verificar("escaparHtml", ProCadenas.escaparHtml("<b>\"x\"</b>").equals("&lt;b&gt;&quot;x&quot;&lt;/b&gt;"));

        System.out.println();
        System.out.println("===== PRUEBAS DE ProAritmetica (Math) =====");
        verificar("redondear (Math.round)", ProAritmetica.redondear(43.164) == 43.16);
        verificar("redondearArriba (Math.ceil)", ProAritmetica.redondearArriba(5103.7414) == 5103.75);
        verificar("redondearArriba sin error de double", ProAritmetica.redondearArriba(50.000000001) == 50.00);
        verificar("porcentaje", ProAritmetica.porcentaje(200, 15) == 30);
        verificar("limitar por arriba (Math.min)", ProAritmetica.limitar(25, 0, 20) == 20);
        verificar("limitar por abajo (Math.max)", ProAritmetica.limitar(-3, 0, 20) == 0);
        verificar("diferencia (Math.abs)", ProAritmetica.diferencia(10, 25) == 15);
        verificar("cuotaFija con 1 cuota", ProAritmetica.cuotaFija(300, 0.015, 1) == 300);
        boolean aleatorioEnRango = true;
        for (int i = 0; i < 1000; i++) {
            int n = ProAritmetica.numeroAleatorio(1000, 9999);
            if (n < 1000 || n > 9999) {
                aleatorioEnRango = false;
            }
        }
        verificar("numeroAleatorio dentro del rango (1000 intentos)", aleatorioEnRango);

        System.out.println();
        System.out.println("===== PRUEBAS DE ValidadorDatos =====");
        List<String> sinErrores = ValidadorDatos.validar("angel reyes", "75729271", "angel@gmail.com",
                "987 654 321", "LAP-001", "2", "3", "promo10", "");
        verificar("datos correctos no generan errores", sinErrores.isEmpty());
        List<String> conErrores = ValidadorDatos.validar("Angel", "1234", "angel@", "812345678",
                "XXX-999", "0", "5", "DESCUENTO", "");
        verificar("datos incorrectos generan 8 errores", conErrores.size() == 8);
        verificar("cupón PROMO15 -> 15", ValidadorDatos.obtenerPorcentajeCupon("promo15") == 15);
        verificar("cupón PROMO25 no válido", ValidadorDatos.obtenerPorcentajeCupon("PROMO25") == 0);
        verificar("cupón PROMO7 no válido", ValidadorDatos.obtenerPorcentajeCupon("PROMO7") == 0);

        System.out.println();
        System.out.println("===== PRUEBAS DE Cotizacion =====");
        Cliente cliente = new Cliente("angel  fernando reyes moreno", "75729271", "Angel@Gmail.com", "987654321");
        verificar("cliente normalizado", cliente.getNombre().equals("Angel Fernando Reyes Moreno")
                && cliente.getCorreo().equals("angel@gmail.com"));
        verificar("celular con formato", cliente.getCelularFormateado().equals("987 654 321"));

        // Caso 1: 2 kits, sin descuento, al contado
        Cotizacion c1 = new Cotizacion(cliente, Catalogo.buscar("KIT-004"), 2, 1, 0);
        verificar("caso 1 subtotal 239.80", c1.getSubtotal() == 239.80);
        verificar("caso 1 IGV 43.16", c1.getIgv() == 43.16);
        verificar("caso 1 total 282.96", c1.getTotal() == 282.96);
        verificar("caso 1 sin intereses", c1.getIntereses() == 0);

        // Caso 2: 10 laptops, volumen 5 % + cupón 10 %, en 6 cuotas
        Cotizacion c2 = new Cotizacion(cliente, Catalogo.buscar("LAP-001"), 10, 6, 10);
        verificar("caso 2 descuento 15 %", c2.getPorcentajeDescuento() == 15);
        verificar("caso 2 descuento 4348.50", c2.getMontoDescuento() == 4348.50);
        verificar("caso 2 total 29076.97", c2.getTotal() == 29076.97);
        verificar("caso 2 cuota 5103.75", c2.getCuota() == 5103.75);
        verificar("caso 2 intereses 1545.53", c2.getIntereses() == 1545.53);

        // Caso 3: 20 monitores, volumen 5 % + cupón 20 % = 25 % -> tope 20 %, en 12 cuotas
        Cotizacion c3 = new Cotizacion(cliente, Catalogo.buscar("MON-002"), 20, 12, 20);
        verificar("caso 3 descuento limitado a 20 %", c3.getPorcentajeDescuento() == 20 && c3.isDescuentoLimitado());
        verificar("caso 3 total 11137.31", c3.getTotal() == 11137.31);
        verificar("caso 3 cuota 1021.07", c3.getCuota() == 1021.07);
        verificar("código de cotización", c3.getCodigo().startsWith("COT-AFRM-271-") && c3.getCodigo().length() == 17);

        System.out.println();
        System.out.println("Resultado: " + correctas + " correctas, " + fallidas + " fallidas.");
    }

    private static void verificar(String descripcion, boolean condicion) {
        if (condicion) {
            correctas++;
            System.out.println("[OK]    " + descripcion);
        } else {
            fallidas++;
            System.out.println("[FALLA] " + descripcion);
        }
    }
}
