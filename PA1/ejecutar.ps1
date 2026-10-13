$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
$javacCommand = Get-Command javac -ErrorAction SilentlyContinue
if ($javacCommand) {
    $javaBin = Split-Path $javacCommand.Source
} else {
    $portable = Get-ChildItem -Path '.tools' -Filter javac.exe -Recurse -ErrorAction SilentlyContinue | Select-Object -First 1
    if (-not $portable) { throw 'Instala un JDK 8 o superior y agrega su carpeta bin al PATH.' }
    $javaBin = $portable.DirectoryName
}
New-Item -ItemType Directory -Force build | Out-Null
& (Join-Path $javaBin 'javac.exe') -encoding UTF-8 -d build src/Producto.java src/Inventario.java src/Aplicacion.java
if ($LASTEXITCODE -ne 0) { throw 'No se pudo compilar el proyecto.' }
& (Join-Path $javaBin 'java.exe') -cp build Aplicacion
exit $LASTEXITCODE
