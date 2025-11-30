Para compilar:

grails -Dgrails.env=development war <ruta destino fichero .war>\webservicessoap.war
grails -Dgrails.env=integration war <ruta destino fichero .war>\webservicessoap.war
grails -Dgrails.env=preproduction war <ruta destino fichero .war>\webservicessoap.war
grails -Dgrails.env=production war <ruta destino fichero .war>\webservicessoap.war


Para levantar en local usar ./run-local.sh
los scripts de base de datos que levanta en local estan en ./scripts/init_data.sql