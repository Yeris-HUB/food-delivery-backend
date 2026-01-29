j# Script para subir el proyecto a GitHub
# URL del repositorio: https://github.com/Yeris-HUB/food-delivery-backend.git

Write-Host "=== Food Delivery Backend - Setup GitHub ===" -ForegroundColor Green

# Verificar si Git está instalado
Write-Host "`nVerificando instalación de Git..." -ForegroundColor Yellow
$gitPath = Get-Command git -ErrorAction SilentlyContinue

if (-not $gitPath) {
    Write-Host "Git NO está instalado." -ForegroundColor Red
    Write-Host "`nPara instalar Git:" -ForegroundColor Cyan
    Write-Host "1. Ve a: https://git-scm.com/download/win" -ForegroundColor Cyan
    Write-Host "2. Descarga e instala Git for Windows" -ForegroundColor Cyan
    Write-Host "3. Reinicia PowerShell después de instalar" -ForegroundColor Cyan
    Write-Host "4. Ejecuta este script nuevamente" -ForegroundColor Cyan

    $openBrowser = Read-Host "`n¿Quieres abrir el navegador para descargar Git? (S/N)"
    if ($openBrowser -eq 'S' -or $openBrowser -eq 's') {
        Start-Process "https://git-scm.com/download/win"
    }
    exit
}

Write-Host "✓ Git está instalado: $($gitPath.Version)" -ForegroundColor Green

# Paso 1: Configurar usuario de Git
Write-Host "`n=== Paso 1: Configurar Git ===" -ForegroundColor Yellow
$currentUserName = git config --global user.name
$currentUserEmail = git config --global user.email

if ($currentUserName) {
    Write-Host "Usuario actual: $currentUserName <$currentUserEmail>" -ForegroundColor Cyan
    $changeUser = Read-Host "¿Quieres cambiar la configuración? (S/N)"
    if ($changeUser -eq 'S' -or $changeUser -eq 's') {
        $gitUserName = Read-Host "Ingresa tu nombre completo"
        $gitUserEmail = Read-Host "Ingresa tu email de GitHub"
        git config --global user.name "$gitUserName"
        git config --global user.email "$gitUserEmail"
    }
} else {
    $gitUserName = Read-Host "Ingresa tu nombre completo"
    $gitUserEmail = Read-Host "Ingresa tu email de GitHub"
    git config --global user.name "$gitUserName"
    git config --global user.email "$gitUserEmail"
}

Write-Host "✓ Configuración de Git completa" -ForegroundColor Green

# Paso 2: Inicializar repositorio
Write-Host "`n=== Paso 2: Inicializando repositorio Git ===" -ForegroundColor Yellow
if (Test-Path ".git") {
    Write-Host "✓ Repositorio ya inicializado" -ForegroundColor Green
} else {
    git init
    Write-Host "✓ Repositorio inicializado" -ForegroundColor Green
}

# Paso 3: Agregar archivos
Write-Host "`n=== Paso 3: Agregando archivos ===" -ForegroundColor Yellow
git add .
Write-Host "✓ Archivos agregados al staging area" -ForegroundColor Green

# Paso 4: Verificar archivos que se van a subir
Write-Host "`nArchivos que se subirán:" -ForegroundColor Cyan
git status --short

$continue = Read-Host "`n¿Continuar con el commit? (S/N)"
if ($continue -ne 'S' -and $continue -ne 's') {
    Write-Host "Proceso cancelado." -ForegroundColor Red
    exit
}

# Paso 5: Hacer el primer commit
Write-Host "`n=== Paso 4: Creando commit inicial ===" -ForegroundColor Yellow
git commit -m "Initial commit: Spring Boot Food Delivery Backend with Java 21"
Write-Host "✓ Commit creado" -ForegroundColor Green

# Paso 6: Crear rama main
Write-Host "`n=== Paso 5: Configurando rama principal ===" -ForegroundColor Yellow
git branch -M main
Write-Host "✓ Rama 'main' configurada" -ForegroundColor Green

# Paso 7: Agregar repositorio remoto
Write-Host "`n=== Paso 6: Conectando con GitHub ===" -ForegroundColor Yellow
$repoUrl = "https://github.com/Yeris-HUB/food-delivery-backend.git"

# Verificar si ya existe el remote
$remoteExists = git remote | Select-String -Pattern "origin"
if ($remoteExists) {
    git remote set-url origin $repoUrl
    Write-Host "✓ Remote 'origin' actualizado" -ForegroundColor Green
} else {
    git remote add origin $repoUrl
    Write-Host "✓ Remote 'origin' agregado" -ForegroundColor Green
}

Write-Host "`nRepositorio: $repoUrl" -ForegroundColor Cyan

# Paso 8: Subir código
Write-Host "`n=== Paso 7: Subiendo código a GitHub ===" -ForegroundColor Yellow
Write-Host "Esto puede tardar unos segundos..." -ForegroundColor Cyan

try {
    git push -u origin main
    Write-Host "`n✓ ¡Código subido exitosamente! 🎉" -ForegroundColor Green
    Write-Host "`nPuedes ver tu repositorio en:" -ForegroundColor Cyan
    Write-Host "https://github.com/Yeris-HUB/food-delivery-backend" -ForegroundColor White

    $openRepo = Read-Host "`n¿Quieres abrir el repositorio en el navegador? (S/N)"
    if ($openRepo -eq 'S' -or $openRepo -eq 's') {
        Start-Process "https://github.com/Yeris-HUB/food-delivery-backend"
    }
} catch {
    Write-Host "`n✗ Error al subir el código" -ForegroundColor Red
    Write-Host "Puede que necesites autenticarte con GitHub." -ForegroundColor Yellow
    Write-Host "`nOpciones de autenticación:" -ForegroundColor Cyan
    Write-Host "1. GitHub CLI: gh auth login" -ForegroundColor White
    Write-Host "2. Personal Access Token" -ForegroundColor White
    Write-Host "3. SSH Key" -ForegroundColor White
    Write-Host "`nMás info: https://docs.github.com/es/authentication" -ForegroundColor Cyan
}

Write-Host "`n=== Proceso completado ===" -ForegroundColor Green

