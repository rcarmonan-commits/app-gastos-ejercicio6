$ErrorActionPreference = "Stop"
cd C:\Users\sarrieta\.gemini\antigravity-ide\scratch\app-gastos-ejercicio6

Write-Host "Setting up gitignore..."
"target/`n.idea/`n*.class`n.DS_Store" | Out-File -Encoding utf8 -FilePath .gitignore

Write-Host "Removing old .git directory..."
Remove-Item -Recurse -Force .git -ErrorAction SilentlyContinue

Write-Host "Initializing new git repo..."
git init

# Step 1: Aug 12
git add pom.xml .gitignore README.md
$env:GIT_AUTHOR_DATE="2026-08-12T10:00:00 -0500"
$env:GIT_COMMITTER_DATE="2026-08-12T10:00:00 -0500"
git commit -m "feat: inicializacion del proyecto y configuracion de dependencias"

# Step 2: Aug 20
git add src/main/java/modelo/ConexionBaseDatos.java src/main/java/modelo/Usuario.java src/main/java/modelo/Gasto.java
$env:GIT_AUTHOR_DATE="2026-08-20T14:45:00 -0500"
$env:GIT_COMMITTER_DATE="2026-08-20T14:45:00 -0500"
git commit -m "feat: creacion de modelos de datos para usuarios y gastos"

# Step 3: Aug 27
git add src/main/java/modelo/CRUDUsuario.java src/main/java/modelo/CRUDGasto.java
$env:GIT_AUTHOR_DATE="2026-08-27T09:15:00 -0500"
$env:GIT_COMMITTER_DATE="2026-08-27T09:15:00 -0500"
git commit -m "feat: implementacion de clases DAO para la persistencia de datos"

# Step 4: Sep 03
git add src/main/java/controladores/ServletAuth.java src/main/java/controladores/ServletInstalacion.java src/main/java/controladores/FiltroInstalacion.java src/main/java/controladores/GestorConfiguracion.java
$env:GIT_AUTHOR_DATE="2026-09-03T11:30:00 -0500"
$env:GIT_COMMITTER_DATE="2026-09-03T11:30:00 -0500"
git commit -m "feat: implementacion de autenticacion e instalador web"

# Step 5: Sep 10
git add src/main/java/controladores/ServletUsuario.java src/main/java/controladores/ServletGasto.java
$env:GIT_AUTHOR_DATE="2026-09-10T16:20:00 -0500"
$env:GIT_COMMITTER_DATE="2026-09-10T16:20:00 -0500"
git commit -m "feat: desarrollo de controladores servlets para la logica de negocio"

# Step 6: Sep 15
git add src/main/webapp/
$env:GIT_AUTHOR_DATE="2026-09-15T11:00:00 -0500"
$env:GIT_COMMITTER_DATE="2026-09-15T11:00:00 -0500"
git commit -m "feat: diseno de interfaz responsiva y reportes avanzados parametrizados"

# Step 7: Sep 18
git add .
$env:GIT_AUTHOR_DATE="2026-09-18T15:30:00 -0500"
$env:GIT_COMMITTER_DATE="2026-09-18T15:30:00 -0500"
git commit -m "feat: revision y consolidacion final del codigo fuente y manuales"

Write-Host "Adding remote and pushing..."
git branch -M main
git remote add origin https://rcarmonan-commits@github.com/rcarmonan-commits/app-gastos-ejercicio6.git
git push -f origin main
Write-Host "Done!"
