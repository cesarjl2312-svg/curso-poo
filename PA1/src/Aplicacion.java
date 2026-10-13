import java.util.Locale;
import java.util.Scanner;

/** Clase ejecutora: menu, lectura y validacion de entradas por consola. */
public class Aplicacion {
    private static Scanner teclado = new Scanner(System.in);
    private static boolean finEntrada = false;
    private static Inventario inventario = new Inventario();

    public static void main(String[] args) {
        int opcion;
        System.out.println("PA1 - CONTROL DE PRODUCTOS");
        System.out.println("Los datos se mantienen durante esta ejecucion.");
        do {
            mostrarMenu();
            opcion = leerEntero("Opcion: ", 0, 7);
            if (finEntrada) {
                break;
            }
            switch (opcion) {
                case 1: registrarProducto(); break;
                case 2: inventario.listar(false); break;
                case 3: consultarProducto(); break;
                case 4: moverStock(true); break;
                case 5: moverStock(false); break;
                case 6: inventario.listar(true); break;
                case 7: mostrarResumen(); break;
                case 0: System.out.println("Programa finalizado."); break;
                default: System.out.println("Opcion no valida.");
            }
        } while (opcion != 0 && !finEntrada);
        if (finEntrada) {
            System.out.println("Fin de entrada. Programa finalizado.");
        }
        teclado.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n1. Registrar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Consultar producto y stock");
        System.out.println("4. Ingresar stock");
        System.out.println("5. Retirar stock");
        System.out.println("6. Mostrar productos con stock bajo");
        System.out.println("7. Mostrar resumen del inventario");
        System.out.println("0. Salir");
    }

    private static String leerLinea(String mensaje) {
        if (finEntrada) {
            return null;
        }
        System.out.print(mensaje);
        if (!teclado.hasNextLine()) {
            finEntrada = true;
            return null;
        }
        return teclado.nextLine().trim();
    }

    private static String leerTexto(String mensaje, int longitudMaxima) {
        while (!finEntrada) {
            String texto = leerLinea(mensaje);
            if (texto == null) {
                return null;
            }
            if (!texto.isEmpty() && texto.length() <= longitudMaxima) {
                return texto;
            }
            System.out.println("Escriba entre 1 y " + longitudMaxima + " caracteres.");
        }
        return null;
    }

    private static int leerEntero(String mensaje, int minimo, int maximo) {
        while (!finEntrada) {
            String linea = leerLinea(mensaje);
            if (linea == null) {
                return -1;
            }
            Scanner lector = new Scanner(linea);
            if (lector.hasNextInt()) {
                int valor = lector.nextInt();
                boolean sobraTexto = lector.hasNext();
                lector.close();
                if (!sobraTexto && valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } else {
                lector.close();
            }
            System.out.println("Ingrese un entero entre " + minimo + " y " + maximo + ".");
        }
        return -1;
    }

    private static double leerPrecio() {
        while (!finEntrada) {
            String linea = leerLinea("Precio unitario en soles (ej. 12.50): ");
            if (linea == null) {
                return -1;
            }
            // Coma y punto se interpretan como separador decimal, nunca de miles.
            Scanner lector = new Scanner(linea.replace(',', '.')).useLocale(Locale.US);
            if (lector.hasNextDouble()) {
                double valor = lector.nextDouble();
                boolean sobraTexto = lector.hasNext();
                lector.close();
                if (!sobraTexto && valor >= 0.01 && valor <= Producto.PRECIO_MAXIMO) {
                    return valor;
                }
            } else {
                lector.close();
            }
            System.out.println("Ingrese un precio entre 0.01 y 1000000.00.");
        }
        return -1;
    }

    private static void registrarProducto() {
        if (inventario.estaLleno()) {
            System.out.println("Inventario lleno. Capacidad: " + Inventario.CAPACIDAD);
            return;
        }
        String codigo = leerTexto("Codigo: ", 20);
        if (finEntrada) { return; }
        if (inventario.buscar(codigo) != null) {
            System.out.println("El codigo ya esta registrado.");
            return;
        }
        String nombre = leerTexto("Nombre: ", 60);
        double precio = leerPrecio();
        int stock = leerEntero("Stock inicial: ", 0, Producto.STOCK_MAXIMO);
        int minimo = leerEntero("Stock minimo: ", 0, Producto.STOCK_MAXIMO);
        if (finEntrada) { return; }
        if (inventario.registrar(codigo, nombre, precio, stock, minimo)) {
            System.out.println("Producto registrado correctamente.");
            inventario.buscar(codigo).mostrarInformacion();
        } else {
            System.out.println("No se pudo registrar el producto. Revise los datos.");
        }
    }

    private static Producto seleccionarProducto() {
        String codigo = leerTexto("Codigo del producto: ", 20);
        if (finEntrada) { return null; }
        Producto producto = inventario.buscar(codigo);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
        }
        return producto;
    }

    private static void consultarProducto() {
        Producto producto = seleccionarProducto();
        if (producto != null) {
            producto.mostrarInformacion();
        }
    }

    private static void moverStock(boolean esIngreso) {
        Producto producto = seleccionarProducto();
        if (producto == null) { return; }
        int cantidad = leerEntero("Cantidad: ", 1, Producto.STOCK_MAXIMO);
        if (finEntrada) { return; }
        boolean realizado;
        if (esIngreso) {
            realizado = producto.ingresarStock(cantidad);
        } else {
            realizado = producto.retirarStock(cantidad);
        }
        if (realizado) {
            System.out.println("Operacion realizada. Stock actual: " + producto.getStock());
        } else {
            if (esIngreso) {
                System.out.println("Ingreso rechazado: excede el stock maximo.");
            } else {
                System.out.println("Retiro rechazado: stock insuficiente.");
            }
        }
    }

    private static void mostrarResumen() {
        System.out.println("Productos registrados: " + inventario.getCantidad());
        System.out.println("Unidades totales: " + inventario.contarUnidades());
        System.out.println("Productos con stock bajo: " + inventario.contarStockBajo());
        System.out.printf(Locale.US, "Valor total del inventario: S/ %.2f%n",
                inventario.calcularValorTotal());
    }
}
