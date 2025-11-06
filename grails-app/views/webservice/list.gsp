

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Listando webservices</title>
    </head>
    <body>

        <div class="body">
            <h1>Listando webservices</h1>
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" action="create">Nuevo webservice</g:link></span>
        	</div>
        	
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>                       
                            <g:sortableColumn property="clave" title="${message(code: 'webservice.nombre.label', default: 'Clave')}" />
                            <g:sortableColumn property="descripcion" title="${message(code: 'webservice.nombre.label', default: 'Descripcion')}" />
                            <g:sortableColumn property="activo" title="${message(code: 'webservice.activo.label', default: 'Activo')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${webserviceInstanceList}" status="i" var="webserviceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link class="linkBloque" action="show" id="${webserviceInstance.id}">${fieldValue(bean: webserviceInstance, field: "clave")}</g:link></td>
                            <td><g:link class="linkBloque" action="show" id="${webserviceInstance.id}">${fieldValue(bean: webserviceInstance, field: "descripcion")}</g:link></td>
                            <td class="${webserviceInstance.activo? 'silist' : 'nolist'}"><span style="visibility: hidden"><g:formatBoolean boolean="${webserviceInstance.activo}" /></span></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${webserviceInstanceTotal}" />Registros Totales: ${webserviceInstanceTotal}
            </div>
        </div>
    </body>
</html>
