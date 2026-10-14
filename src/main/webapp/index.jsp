<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="pe.isil.poo.cotizador.modelo.Catalogo" %>
<%@ page import="pe.isil.poo.cotizador.modelo.Producto" %>
<%@ page import="pe.isil.poo.cotizador.modelo.Cotizacion" %>
<%@ page import="pe.isil.poo.cotizador.util.ProCadenas" %>
<%@ page import="pe.isil.poo.cotizador.util.ValidadorDatos" %>
<%
    // ENTRADA: si el usuario vuelve desde resultado.jsp para corregir, se recuperan sus datos.
    request.setCharacterEncoding("UTF-8");
    String nombre = ProCadenas.escaparHtml(request.getParameter("nombre"));
    String dni = ProCadenas.escaparHtml(request.getParameter("dni"));
    String correo = ProCadenas.escaparHtml(request.getParameter("correo"));
    String celular = ProCadenas.escaparHtml(request.getParameter("celular"));
    String productoSel = ProCadenas.escaparHtml(request.getParameter("producto"));
    String cantidad = ProCadenas.escaparHtml(request.getParameter("cantidad"));
    String cuotasSel = ProCadenas.escaparHtml(request.getParameter("cuotas"));
    String cupon = ProCadenas.escaparHtml(request.getParameter("cupon"));
    String observaciones = ProCadenas.escaparHtml(request.getParameter("observaciones"));
    if (cantidad.isEmpty()) {
        cantidad = "1";
    }
    if (cuotasSel.isEmpty()) {
        cuotasSel = "1";
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cotizador Web | Solicitud</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<%@ include file="WEB-INF/jspf/encabezado.jspf" %>

<main class="contenedor">
    <section class="tarjeta">
        <h2>Solicitar cotización</h2>
        <p class="ayuda">Complete el formulario. Los campos con <span class="obligatorio">*</span> son obligatorios.</p>

        <form action="resultado.jsp" method="post" class="formulario">
            <fieldset>
                <legend>Datos del cliente</legend>

                <label for="nombre">Nombre completo <span class="obligatorio">*</span></label>
                <input type="text" id="nombre" name="nombre" maxlength="80" value="<%= nombre %>"
                       placeholder="Ejemplo: Ana María Torres Ruiz" required>

                <div class="fila">
                    <div>
                        <label for="dni">DNI <span class="obligatorio">*</span></label>
                        <input type="text" id="dni" name="dni" maxlength="10" value="<%= dni %>"
                               placeholder="8 dígitos" required>
                    </div>
                    <div>
                        <label for="celular">Celular <span class="obligatorio">*</span></label>
                        <input type="text" id="celular" name="celular" maxlength="12" value="<%= celular %>"
                               placeholder="987 654 321" required>
                    </div>
                </div>

                <label for="correo">Correo electrónico <span class="obligatorio">*</span></label>
                <input type="text" id="correo" name="correo" maxlength="80" value="<%= correo %>"
                       placeholder="nombre@dominio.com" required>
            </fieldset>

            <fieldset>
                <legend>Datos de la cotización</legend>

                <label for="producto">Producto o servicio <span class="obligatorio">*</span></label>
                <select id="producto" name="producto" required>
                    <option value="">-- Seleccione --</option>
                    <% for (Producto p : Catalogo.listar()) { %>
                    <option value="<%= p.getCodigo() %>" <%= p.getCodigo().equals(productoSel) ? "selected" : "" %>>
                        <%= p.getCodigo() %> | <%= p.getNombre() %> | S/ <%= String.format(java.util.Locale.US, "%,.2f", p.getPrecioUnitario()) %> / <%= p.getUnidad() %>
                    </option>
                    <% } %>
                </select>

                <div class="fila">
                    <div>
                        <label for="cantidad">Cantidad <span class="obligatorio">*</span></label>
                        <input type="number" id="cantidad" name="cantidad" min="<%= ValidadorDatos.CANTIDAD_MINIMA %>"
                               max="<%= ValidadorDatos.CANTIDAD_MAXIMA %>" value="<%= cantidad %>" required>
                    </div>
                    <div>
                        <label for="cuotas">Forma de pago <span class="obligatorio">*</span></label>
                        <select id="cuotas" name="cuotas">
                            <% for (int n : ValidadorDatos.CUOTAS_PERMITIDAS) { %>
                            <option value="<%= n %>" <%= String.valueOf(n).equals(cuotasSel) ? "selected" : "" %>>
                                <%= n == 1 ? "Al contado (1 pago)" : n + " cuotas mensuales" %>
                            </option>
                            <% } %>
                        </select>
                    </div>
                </div>

                <label for="cupon">Cupón de descuento (opcional)</label>
                <input type="text" id="cupon" name="cupon" maxlength="10" value="<%= cupon %>"
                       placeholder="Ejemplo: PROMO10">

                <label for="observaciones">Observaciones (opcional, máximo <%= ValidadorDatos.MAX_OBSERVACIONES %> caracteres)</label>
                <textarea id="observaciones" name="observaciones" rows="3"><%= observaciones %></textarea>
            </fieldset>

            <button type="submit" class="boton">Calcular cotización</button>
        </form>
    </section>

    <aside class="tarjeta reglas">
        <h3>Condiciones comerciales</h3>
        <ul>
            <li>Precios sin IGV; se agrega IGV de <%= (int) (Cotizacion.TASA_IGV * 100) %> %.</li>
            <li>Descuento por volumen de <%= Cotizacion.PORCENTAJE_DESCUENTO_VOLUMEN %> % desde
                <%= Cotizacion.CANTIDAD_DESCUENTO_VOLUMEN %> unidades.</li>
            <li>Cupones <strong>PROMO5</strong>, <strong>PROMO10</strong>, <strong>PROMO15</strong> y
                <strong>PROMO20</strong>.</li>
            <li>El descuento total no supera el <%= Cotizacion.TOPE_DESCUENTO %> %.</li>
            <li>Pago en 3, 6 o 12 cuotas con interés de <%= Cotizacion.TASA_MENSUAL * 100 %> % mensual.</li>
        </ul>
    </aside>
</main>

<%@ include file="WEB-INF/jspf/pie.jspf" %>
</body>
</html>
