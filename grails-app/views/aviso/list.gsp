

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Listando Configuraci&oacute;n de Avisos</title>
    </head>
    <body>

        <div class="body">
            
            <h1>Listando Configuraci&oacute;n de Avisos</h1>
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" action="create">Nuevo aviso</g:link></span>
        	</div>
        	
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="nombre" title="${message(code: 'aviso.nombre.label', default: 'Nombre')}" />
                            <th><g:message code="aviso.plantilla.label" default="Plantilla" /></th>
                            <th><g:message code="aviso.operacion.label" default="Operaci&oacute;n" /></th>
                            <g:sortableColumn property="activo" title="${message(code: 'aviso.activo.label', default: 'Activo')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${avisoInstanceList}" status="i" var="avisoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link class="linkBloque" action="show" id="${avisoInstance.id}">${fieldValue(bean: avisoInstance, field: "nombre")}</g:link></td>
                            <td><g:link class="linkBloque" controller="plantilla" action="show" id="${avisoInstance.plantilla.id}">${fieldValue(bean: avisoInstance, field: "plantilla.nombre")}</g:link></td>
                            <td><g:link class="linkBloque" controller="operacion" action="show" id="${avisoInstance.operacion.id}">${fieldValue(bean: avisoInstance, field: "operacion.clave")}</g:link></td>
                            <td class="${avisoInstance.activo? 'silist' : 'nolist'}"><g:link class="linkBloque" controller="operacion" action="show" id="${avisoInstance.operacion.id}"><span style="visibility: hidden">${fieldValue(bean:avisoInstance, field:'activo')}</span></g:link></td>                        
                            
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${avisoInstanceTotal}" />Registros Totales: ${avisoInstanceTotal}
            </div>
        </div>
    </body>
</html>
