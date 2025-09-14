@echo off
set PROJECT_DIR=D:\Aniket\Automation\Project-2\WhatsappAI
set SRC_DIR=%PROJECT_DIR%\src\test\java
set BIN_DIR=%PROJECT_DIR%\bin

:: 🔹 Add Selenium JARs (adjust path if jars are elsewhere)
set SELENIUM_LIB=%PROJECT_DIR%\lib\*

echo Compiling Java files...
javac -cp "%SELENIUM_LIB%;%SRC_DIR%" -d "%BIN_DIR%" %SRC_DIR%\RunFile\mainClass.java

if %ERRORLEVEL% neq 0 (
    echo ❌ Compilation failed!
    pause
    exit /b
)

echo Running mainClass...
java -cp "%SELENIUM_LIB%;%BIN_DIR%" RunFile.mainClass

pause
