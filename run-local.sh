#!/bin/bash

# --- CONFIGURACIÓN ---
DB_CONTAINER_NAME="postgres-local"
DB_NAME="scorws_prepro_local"
DB_USER="postgres"
DB_PASS="postgres"
INIT_SCRIPT_PATH="./scripts/init_data.sql"
POSTGRES_IMAGE="postgres:16"

# --- ARRANQUE DEL CONTENEDOR ---
if [ "$(docker ps -q -f name=$DB_CONTAINER_NAME)" ]; then
  echo "🟢 PostgreSQL ya está corriendo."
elif [ "$(docker ps -aq -f status=exited -f name=$DB_CONTAINER_NAME)" ]; then
  echo "🔁 Reiniciando contenedor PostgreSQL..."
  docker start $DB_CONTAINER_NAME
else
  echo "🚀 Iniciando contenedor PostgreSQL..."
  docker run --name $DB_CONTAINER_NAME \
    -e POSTGRES_USER=$DB_USER \
    -e POSTGRES_PASSWORD=$DB_PASS \
    -e POSTGRES_DB=$DB_NAME \
    -p 5432:5432 \
    -d $POSTGRES_IMAGE
fi

# --- ESPERA A QUE POSTGRES ESTÉ LISTO ---
echo "⏳ Esperando a que PostgreSQL esté listo..."
until docker exec $DB_CONTAINER_NAME pg_isready -U $DB_USER > /dev/null 2>&1; do
  sleep 2
done
echo "✅ PostgreSQL está listo."

# --- VACIAR TODAS LAS TABLAS ---
echo "🧹 Vaciando todas las tablas en la base de datos $DB_NAME..."

docker exec -i $DB_CONTAINER_NAME psql -U $DB_USER -d $DB_NAME <<EOF
DO \$\$
DECLARE
    stmt text;
BEGIN
    FOR stmt IN
        SELECT 'TRUNCATE TABLE "' || tablename || '" RESTART IDENTITY CASCADE;'
        FROM pg_tables
        WHERE schemaname = 'public'
    LOOP
        EXECUTE stmt;
    END LOOP;
END
\$\$;
EOF

echo "✅ Todas las tablas fueron vaciadas (sin eliminar estructura)."

# --- CARGA DE DATOS INICIALES ---
if [ -f "$INIT_SCRIPT_PATH" ]; then
  echo "📦 Cargando datos iniciales desde $INIT_SCRIPT_PATH..."
  cat "$INIT_SCRIPT_PATH" | docker exec -i $DB_CONTAINER_NAME psql -U $DB_USER -d $DB_NAME
  echo "✅ Datos iniciales cargados correctamente."
else
  echo "⚠️ No se encontró el script SQL en $INIT_SCRIPT_PATH. Se omite carga de datos."
fi

# --- LEVANTAR GRAILS ---
grails run-app -Dgrails.env=local
