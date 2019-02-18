

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Listando estadisticas</title>
    </head>
    <body>

        <div class="body">
        	<g:render template="buscador" />
            <h1>Listando estadisticas</h1>
            <!-- <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" action="create">Nueva estadistica</g:link></span>
        	</div> --!>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="request" title="${message(code: 'estadistica.request.label', default: 'Request')}" />
                            <g:sortableColumn property="clave" title="${message(code: 'estadistica.clave.label', default: 'Clave')}" />
                            <g:sortableColumn property="value" title="${message(code: 'estadistica.value.label', default: 'Value')}" />
                            <g:sortableColumn property="fecha_alta" title="${message(code: 'estadistica.fecha_alta.label', default: 'Fecha')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${estadisticaInstanceList}" status="i" var="estadisticaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link class="linkBloque" action="show" id="${estadisticaInstance.id}">${fieldValue(bean: estadisticaInstance, field: "request")}</g:link></td>
                            <td><g:link class="linkBloque" action="show" id="${estadisticaInstance.id}">${fieldValue(bean: estadisticaInstance, field: "clave")}</g:link></td>
                            <td><g:link class="linkBloque" action="show" id="${estadisticaInstance.id}">${fieldValue(bean: estadisticaInstance, field: "value")}</g:link></td>
                            <td><g:link class="linkBloque" action="show" id="${estadisticaInstance.id}">${fieldValue(bean: estadisticaInstance, field: "fecha_alta")}</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${estadisticaInstanceTotal}" />Registros Totales: ${estadisticaInstanceTotal}
            </div>
        </div>
    </body>
</html>
