@echo off

set ERRORLEVEL=0

mariadb -h emmc316 -u monitor -p12345 inversiones -P 3309 < NodoA.sql

if %ERRORLEVEL% equ 0 (
    echo Base de datos creada
) else (
    echo Ha ocurrido un error y el error de la sentencia: %ERRORLEVEL%
)
