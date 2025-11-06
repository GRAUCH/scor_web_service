

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Listando Datos webservice</title>
    </head>
    <body>

        <div class="body">
            <h1>Listando Datos webservice</h1>
            <div>
            	Los datos de operaciones para webservices no son obligatorios para el funcionamiento de la plataforma. Su funci&oacute;n reside en facilitar la creaci&oacute;n de plantillas personalizadas.<br /> Si se necesitan, los datos que aqui se expongan estar&aacute;n disponibles en el m&oacute;dulo de creaci&oacute;n de plantillas accediendo a trav&eacute;s del modulo Operaciones pulsando el boton: Nueva Plantilla para esta operaci&oacute;n.
            </div>
            <br />
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" action="create">Nuevo dato webservice</g:link></span>
        	</div>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="nombre" title="${message(code: 'datowebservice.nombre.label', default: 'Nombre')}" />
                            <g:sortableColumn property="tipo" title="${message(code: 'datowebservice.tipo.label', default: 'Tipo')}" />
                            <th><g:message code="datowebservice.operacion.label" default="Operaci&oacute;n" /></th>
                            <th><g:message code="webservice.clave.label" default="Webservice" /></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${listDatos}" status="i" var="datowebserviceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${datowebserviceInstance[0].id}">${datowebserviceInstance[0].nombre}</g:link></td>
                            <td>${datowebserviceInstance[0].tipo}</td>
                            <td>${datowebserviceInstance[1]}</td>
                            <td>${datowebserviceInstance[3]}</td>
                           
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${datowebserviceInstanceTotal}" />Registros Totales: ${datowebserviceInstanceTotal}
            </div>
        </div>
    </body>
</html>
