

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Listando Operaciones de webservices</title>
    </head>
    <body>

        <div class="body">
            <h1>Listando Operaciones de webservices</h1>
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" action="create">Nueva operaci&oacute;n</g:link></span>
        	</div>
        
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="clave" title="${message(code: 'operacion.clave.label', default: 'Clave')}" />
                            <g:sortableColumn property="descripcion" title="${message(code: 'operacion.descripcion.label', default: 'Descripcion')}" />
                            <g:sortableColumn property="activo" title="${message(code: 'operacion.activo.label', default: 'Activo')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${operacionInstanceList}" status="i" var="operacionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        	<td><g:link class="linkBloque" action="show" id="${operacionInstance.id}">${fieldValue(bean: operacionInstance, field: "clave")}</g:link></td>
                            <td><g:link class="linkBloque" action="show" id="${operacionInstance.id}">${fieldValue(bean: operacionInstance, field: "descripcion")}</g:link></td>
                            <td class="${operacionInstance.activo? 'silist' : 'nolist'}"><span style="visibility: hidden"><g:formatBoolean boolean="${operacionInstance.activo}" /></span></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${operacionInstanceTotal}" />Registros Totales: ${operacionInstanceTotal}
            </div>
        </div>
    </body>
</html>
