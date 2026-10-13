"""Pruebas de entradas reales. Requiere Python 3 y clases compiladas en build/."""
import os
from pathlib import Path
import subprocess

ROOT = Path(__file__).resolve().parents[1]
JAVA = os.environ.get("JAVA_COMMAND", "java")
casos = [
    ("salida", "0\n", ["Programa finalizado."]),
    ("vacio", "2\n6\n7\n0\n", ["No hay productos", "Productos registrados: 0", "S/ 0.00"]),
    ("menu invalido", "abc\n8\n-2\n1 2\n0\n", ["Ingrese un entero entre 0 y 7", "Programa finalizado."]),
    ("flujo completo", "1\na01\nArroz\n4.50\n10\n3\n1\nb01\nAceite\n8\n2\n2\n5\na01\n7\n4\na01\n2\n3\na01\n6\n7\n0\n",
     ["Stock actual: 3", "Stock actual: 5", "B01 | Aceite", "Productos registrados: 2", "Unidades totales: 7", "Productos con stock bajo: 1", "S/ 38.50"]),
    ("duplicados", "1\nA01\nArroz\n4.5\n10\n3\n1\na01\n7\n0\n", ["El codigo ya esta registrado", "Productos registrados: 1"]),
    ("producto inexistente", "3\nX\n4\nX\n5\nX\n0\n", ["Producto no encontrado"]),
    ("stock insuficiente", "1\nA\nArroz\n4.5\n10\n3\n5\nA\n11\n3\nA\n0\n", ["Retiro rechazado", "Stock: 10"]),
    ("agotamiento", "1\nA\nArroz\n4.5\n10\n3\n5\nA\n10\n3\nA\n0\n", ["Stock actual: 0", "AGOTADO"]),
    ("validacion de campos", "1\n\nA\n\nArroz\nNaN\nInfinity\n-1\n0\nabc\n4,50\n-1\nfoo\n10\n-1\n3\n7\n0\n",
     ["Escriba entre 1", "Ingrese un precio", "Ingrese un entero", "Producto registrado correctamente", "S/ 45.00"]),
    ("fin de entrada", "", ["Fin de entrada"]),
    ("registro interrumpido", "1\nA\nArroz\n", ["Fin de entrada"]),
    ("movimiento interrumpido", "1\nA\nArroz\n4\n10\n3\n5\nA\n", ["Fin de entrada"]),
    ("stock maximo", "1\nA\nArroz\n1\n1000000\n0\n4\nA\n1\n0\n", ["Ingreso rechazado: excede el stock maximo"]),
    ("entero desbordado", "99999999999999999999999999\n0\n", ["Ingrese un entero", "Programa finalizado."]),
]
for nombre, entrada, esperados in casos:
    resultado = subprocess.run([JAVA, "-cp", str(ROOT / "build"), "Aplicacion"],
                               input=entrada, text=True, capture_output=True, timeout=10, cwd=ROOT)
    assert resultado.returncode == 0, (nombre, resultado.stderr)
    for esperado in esperados:
        assert esperado in resultado.stdout, (nombre, esperado, resultado.stdout)
    if nombre == "registro interrumpido":
        assert "Producto registrado correctamente" not in resultado.stdout
    if nombre == "movimiento interrumpido":
        assert "Operacion realizada" not in resultado.stdout
    print("OK:", nombre)
    if nombre == "flujo completo":
        (ROOT / "docs" / "ejecucion-ejemplo.txt").write_text(resultado.stdout, encoding="utf-8")
print(f"RESULTADO: {len(casos)} escenarios de consola correctos.")
