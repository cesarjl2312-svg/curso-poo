<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="pe.isil.poo.cotizador.modelo.Catalogo" %>
<%@ page import="pe.isil.poo.cotizador.modelo.Cliente" %>
<%@ page import="pe.isil.poo.cotizador.modelo.Cotizacion" %>
<%@ page import="pe.isil.poo.cotizador.modelo.Producto" %>
<%@ page import="pe.isil.poo.cotizador.util.ProCadenas" %>
<%@ page import="pe.isil.poo.cotizador.util.ValidadorDatos" %>
<%!
    // Da formato de moneda: 28990.5 -> "S/ 28,990.50" (String.format)
    private String soles(double monto) {
        return "S/ " + String.format(Locale.US, "%,.2f", monto);
    }

    // Evita null al leer parámetros
    private String texto(String valor) {
        return valor == null ? "" : valor;
    }
%>
<%
    // 1. ENTRADA: lectura de los parámetros enviados por el formulario
    request.setCharacterEncoding("UTF-8");
    String nombre = texto(request.getParameter("nombre"));
    String dni = texto(request.getParameter("dni"));
    String correo = texto(request.getParameter("correo"));
    String celular = texto(request.getParameter("celular"));
    String codigoProducto = texto(request.getParameter("producto"));
    String cantidadTexto = texto(request.getParameter("cantidad"));
    String cuotasTexto = texto(request.getParameter("cuotas"));
    String cupon = texto(request.getParameter("cupon"));
    String observaciones = texto(request.getParameter("observaciones"));

    // 2. PROCESAMIENTO: validación con String y cálculo con Math
    List<String> errores = ValidadorDatos.validar(nombre, dni, correo, celular, codigoProducto,
            cantidadTexto, cuotasTexto, cupon, observaciones);

    Cotizacion cotizacion = null;
    if (errores.isEmpty()) {
        Cliente cliente = new Cliente(nombre, dni, correo, celular);
        Producto producto = Catalogo.buscar(codigoProducto);
        int cantidad = Integer.parseInt(ProCadenas.quitarEspacios(cantidadTexto));
        int cuotas = Integer.parseInt(ProCadenas.quitarEspacios(cuotasTexto));
        int porcentajeCupon = ValidadorDatos.obtenerPorcentajeCupon(cupon);
        cotizacion = new Cotizacion(cliente, producto, cantidad, cuotas, porcentajeCupon);
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cotizador Web | Resultado</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<%@ include file="WEB-INF/jspf/encabezado.jspf" %>

<main class="contenedor una-columna">
<% if (cotizacion == null) { %>
    <%-- 3. SALIDA con errores: se listan y se ofrece volver con los datos ingresados --%>
    <section class="tarjeta alerta">
        <h2>Revise los datos ingresados</h2>
        <p>Se encontraron <strong><%= errores.size() %></strong> observación(es):</p>
        <ul class="lista-errores">
            <% for (String error : errores) { %>
            <li><%= error %></li>
            <% } %>
        </ul>
        <form action="index.jsp" method="post">
            <input type="hidden" name="nombre" value="<%= ProCadenas.escaparHtml(nombre) %>">
            <input type="hidden" name="dni" value="<%= ProCadenas.escaparHtml(dni) %>">
            <input type="hidden" name="correo" value="<%= ProCadenas.escaparHtml(correo) %>">
            <input type="hidden" name="celular" value="<%= ProCadenas.escaparHtml(celular) %>">
            <input type="hidden" name="producto" value="<%= ProCadenas.escaparHtml(codigoProducto) %>">
            <input type="hidden" name="cantidad" value="<%= ProCadenas.escaparHtml(cantidadTexto) %>">
            <input type="hidden" name="cuotas" value="<%= ProCadenas.escaparHtml(cuotasTexto) %>">
            <input type="hidden" name="cupon" value="<%= ProCadenas.escaparHtml(cupon) %>">
            <input type="hidden" name="observaciones" value="<%= ProCadenas.escaparHtml(observaciones) %>">
            <button type="submit" class="boton">Corregir datos</button>
        </form>
    </section>
<% } else {
       Cliente cli = cotizacion.getCliente();
       Producto prod = cotizacion.getProducto();
       String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       String obsLimpias = ProCadenas.normalizarEspacios(observaciones);
%>
    <%-- 3. SALIDA correcta: cotización calculada --%>
    <section class="tarjeta">
        <div class="cabecera-cotizacion">
            <div>
                <p class="etiqueta">Cotización</p>
                <h2 class="codigo"><%= cotizacion.getCodigo() %></h2>
            </div>
            <p class="fecha">Fecha: <%= fecha %><br>Validez: 7 días</p>
        </div>

        <h3>Cliente</h3>
        <table class="tabla-datos">
            <tr><th>Nombre</th><td><%= ProCadenas.escaparHtml(cli.getNombre()) %></td></tr>
            <tr><th>DNI</th><td><%= cli.getDni() %></td></tr>
            <tr><th>Correo</th><td><%= ProCadenas.escaparHtml(cli.getCorreoOculto()) %></td></tr>
            <tr><th>Celular</th><td><%= cli.getCelularFormateado() %></td></tr>
        </table>

        <h3>Detalle</h3>
        <table class="tabla-montos">
            <tr>
                <th>Producto</th>
                <td><%= prod.getCodigo() %> | <%= prod.getNombre() %></td>
            </tr>
            <tr>
                <th>Precio unitario (sin IGV)</th>
                <td><%= soles(prod.getPrecioUnitario()) %> por <%= prod.getUnidad() %></td>
            </tr>
            <tr>
                <th>Cantidad</th>
                <td><%= cotizacion.getCantidad() %></td>
            </tr>
            <tr>
                <th>Subtotal</th>
                <td><%= soles(cotizacion.getSubtotal()) %></td>
            </tr>
            <tr>
                <th>Descuento (<%= cotizacion.getPorcentajeDescuento() %> %)</th>
                <td>
                    - <%= soles(cotizacion.getMontoDescuento()) %>
                    <span class="nota">
                        Volumen <%= cotizacion.getPorcentajeVolumen() %> % + cupón <%= cotizacion.getPorcentajeCupon() %> %
                        <%= cotizacion.isDescuentoLimitado() ? " (limitado al tope de " + Cotizacion.TOPE_DESCUENTO + " %)" : "" %>
                    </span>
                </td>
            </tr>
            <tr>
                <th>Valor de venta</th>
                <td><%= soles(cotizacion.getValorVenta()) %></td>
            </tr>
            <tr>
                <th>IGV (<%= (int) (Cotizacion.TASA_IGV * 100) %> %)</th>
                <td><%= soles(cotizacion.getIgv()) %></td>
            </tr>
            <tr class="total">
                <th>Total</th>
                <td><%= soles(cotizacion.getTotal()) %></td>
            </tr>
        </table>

        <h3>Forma de pago</h3>
        <% if (cotizacion.getNumeroCuotas() == 1) { %>
        <p class="pago">Al contado: un pago de <strong><%= soles(cotizacion.getCuota()) %></strong>.</p>
        <% } else { %>
        <p class="pago">
            <strong><%= cotizacion.getNumeroCuotas() %> cuotas</strong> mensuales de
            <strong><%= soles(cotizacion.getCuota()) %></strong>
            (interés de <%= Cotizacion.TASA_MENSUAL * 100 %> % mensual).<br>
            Total financiado: <%= soles(cotizacion.getTotalFinanciado()) %>
            | Intereses: <%= soles(cotizacion.getIntereses()) %>
        </p>
        <% } %>
        <p class="nota">Precio final por <%= prod.getUnidad() %>, con descuento e IGV:
            <%= soles(cotizacion.getPrecioFinalUnitario()) %></p>

        <% if (!obsLimpias.isEmpty()) { %>
        <h3>Observaciones</h3>
        <p class="observaciones"><%= ProCadenas.escaparHtml(obsLimpias) %></p>
        <p class="nota"><%= ProCadenas.contarPalabras(obsLimpias) %> palabra(s), <%= obsLimpias.length() %> caracteres.</p>
        <% } %>
    </section>

    <%-- Resumen del procesamiento para la sustentación --%>
    <section class="tarjeta">
        <h3>¿Cómo se procesaron los datos?</h3>
        <table class="tabla-proceso">
            <thead>
            <tr><th>Dato</th><th>Ingresado</th><th>Métodos aplicados</th><th>Resultado</th></tr>
            </thead>
            <tbody>
            <tr>
                <td>Nombre</td>
                <td><code><%= ProCadenas.escaparHtml(nombre) %></code></td>
                <td>String: trim, charAt, split, substring, toUpperCase, toLowerCase</td>
                <td><%= ProCadenas.escaparHtml(cli.getNombre()) %></td>
            </tr>
            <tr>
                <td>Iniciales</td>
                <td><%= ProCadenas.escaparHtml(cli.getNombre()) %></td>
                <td>String: split, charAt, toUpperCase</td>
                <td><%= cli.getIniciales() %></td>
            </tr>
            <tr>
                <td>Correo</td>
                <td><code><%= ProCadenas.escaparHtml(correo) %></code></td>
                <td>String: toLowerCase, contains, indexOf, lastIndexOf, substring, repeat</td>
                <td><%= ProCadenas.escaparHtml(cli.getCorreoOculto()) %></td>
            </tr>
            <tr>
                <td>Celular</td>
                <td><code><%= ProCadenas.escaparHtml(celular) %></code></td>
                <td>String: replace, length, startsWith, substring</td>
                <td><%= cli.getCelularFormateado() %></td>
            </tr>
            <tr>
                <td>Cupón</td>
                <td><code><%= cupon.isEmpty() ? "(vacío)" : ProCadenas.escaparHtml(cupon) %></code></td>
                <td>String: toUpperCase, startsWith, substring</td>
                <td><%= cotizacion.getPorcentajeCupon() %> %</td>
            </tr>
            <tr>
                <td>Montos</td>
                <td><%= cotizacion.getCantidad() %> x <%= soles(prod.getPrecioUnitario()) %></td>
                <td>Math: round (céntimos), min y max (tope de descuento)</td>
                <td><%= soles(cotizacion.getTotal()) %></td>
            </tr>
            <tr>
                <td>Cuota</td>
                <td><%= cotizacion.getNumeroCuotas() %> cuota(s)</td>
                <td>Math: pow (cuota fija), ceil (redondeo hacia arriba)</td>
                <td><%= soles(cotizacion.getCuota()) %></td>
            </tr>
            <tr>
                <td>Código</td>
                <td>Iniciales + DNI</td>
                <td>Math: random, floor | String: substring</td>
                <td><%= cotizacion.getCodigo() %></td>
            </tr>
            </tbody>
        </table>
        <a href="index.jsp" class="boton secundario">Nueva cotización</a>
    </section>
<% } %>
</main>

<%@ include file="WEB-INF/jspf/pie.jspf" %>
</body>
</html>
