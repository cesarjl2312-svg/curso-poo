<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultados del Análisis</title>
</head>
<body>
    <h2>Resultados de la Propuesta Mínima</h2>

    <%
        // Capturar parámetros del formulario
        String texto = request.getParameter("texto");
        String sNum1 = request.getParameter("num1");
        String sNum2 = request.getParameter("num2");

        String error = null;
        int longitud = 0;
        String textoMayus = "";
        String textoInvertido = "";
        boolean contienePalabraJava = false;

        double n1 = 0, n2 = 0;
        double potencia = 0, raiz1 = 0, maximo = 0, minimo = 0;

        // Validar que los campos no sean nulos antes de parsear
        if (sNum1 != null && sNum2 != null) {
            try {
                n1 = Double.parseDouble(sNum1);
                n2 = Double.parseDouble(sNum2);

                // --- OPERACIONES CON MATH ---
                potencia = Math.pow(n1, n2);
                raiz1 = Math.sqrt(Math.abs(n1)); // Valor absoluto por seguridad
                maximo = Math.max(n1, n2);
                minimo = Math.min(n1, n2);

                // --- VALIDACIONES Y OPERACIONES CON STRING ---
                if (texto != null) {
                    longitud = texto.length();
                    textoMayus = texto.toUpperCase();
                    textoInvertido = new StringBuilder(texto).reverse().toString();
                    contienePalabraJava = texto.toLowerCase().contains("java");
                }

            } catch (NumberFormatException e) {
                error = "Por favor, ingrese valores numéricos válidos.";
            }
        } else {
            error = "No se recibieron datos del formulario.";
        }
    %>

    <% if (error != null) { %>
        <p style="color:red;"><%= error %></p>
    <% } else { %>
        <h3>1. Resultados de Validación de Cadenas (String)</h3>
        <ul>
            <li><strong>Texto ingresado:</strong> <%= texto %></li>
            <li><strong>Longitud de caracteres:</strong> <%= longitud %></li>
            <li><strong>En mayúsculas:</strong> <%= textoMayus %></li>
            <li><strong>Texto invertido:</strong> <%= textoInvertido %></li>
            <li><strong>¿Contiene la palabra "java"?:</strong> <%= contienePalabraJava ? "Sí" : "No" %></li>
        </ul>

        <h3>2. Resultados de Operaciones Matemáticas (Math)</h3>
        <ul>
            <li><strong>Potencia (<%= n1 %> elevado a la <%= n2 %>):</strong> <%= potencia %></li>
            <li><strong>Raíz cuadrada del primer número:</strong> <%= raiz1 %></li>
            <li><strong>Valor máximo entre ambos:</strong> <%= maximo %></li>
            <li><strong>Valor mínimo entre ambos:</strong> <%= minimo %></li>
        </ul>
    <% } %>

    <br>
    <a href="index.jsp">Realizar otro cálculo</a>
</body>
</html>