<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Evaluador y Calculadora Web</title>
</head>
<body>
    <h2>Formulario de Operaciones y Validaciones</h2>
    <form action="resultado.jsp" method="POST">
        <label>Ingrese un texto o frase:</label><br>
        <input type="text" name="texto" required style="width: 300px;"><br><br>

        <label>Número base (para potencia / raíz):</label><br>
        <input type="number" step="any" name="num1" required><br><br>

        <label>Exponente / Segundo número:</label><br>
        <input type="number" step="any" name="num2" required><br><br>

        <input type="submit" value="Calcular y Validar">
    </form>
</body>
</html>