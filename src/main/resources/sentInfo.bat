@echo off

mariadb -h emmcdev -u monitor -p12345 inversiones -P 3306 < NodoB.sql

echo Base de datos creada
