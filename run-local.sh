#!/bin/bash

# Arranca contenedor MySQL si no está ya corriendo
if [ "$(docker ps -q -f name=mysql-local)" ]; then
  echo "🟢 MySQL ya está corriendo."
elif [ "$(docker ps -aq -f status=exited -f name=mysql-local)" ]; then
  echo "🔁 Reiniciando contenedor MySQL..."
  docker start mysql-local
else
  echo "🚀 Iniciando contenedor MySQL..."
  docker run --name mysql-local \
    -e MYSQL_ROOT_PASSWORD=root \
    -e MYSQL_DATABASE=scorws_prepro \
    -p 3306:3306 \
    -d mysql:8.0
fi

# Espera unos segundos a que MySQL esté listo
echo "⏳ Esperando a que MySQL arranque..."
sleep 8

# Lanza Grails en entorno local
grails run-app -Dgrails.env=local