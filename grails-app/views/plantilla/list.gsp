

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Listando Configuraci&oacute;n de Plantillas</title>
    </head>
    <body>

        <div class="body">
            
            <h1>Listando Configuraci&oacute;n de Plantillas</h1>
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" action="create">Nueva plantilla estandar</g:link></span>
        	</div>
        	
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>          
                            <g:sortableColumn property="nombre" title="${message(code: 'plantilla.nombre.label', default: 'Nombre')}" />
                            <g:sortableColumn property="subject" title="${message(code: 'plantilla.subject.label', default: 'Subject')}" />
                            <g:sortableColumn property="body" title="${message(code: 'plantilla.body.label', default: 'Body')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${plantillaInstanceList}" status="i" var="plantillaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link class="linkBloque" action="show" id="${plantillaInstance.id}">${fieldValue(bean: plantillaInstance, field: "nombre")}</g:link></td>
                            <td><g:link class="linkBloque" action="show" id="${plantillaInstance.id}">${fieldValue(bean: plantillaInstance, field: "subject")}</g:link></td>
                            <g:if test="${fieldValue(bean: plantillaInstance, field:'body').size() > 50}">
								<td><g:link class="linkBloque" action="show" id="${plantillaInstance.id}">${fieldValue(bean: plantillaInstance, field: "body")[0..50] + " ..."}</g:link></td>
							</g:if>
							<g:else>
								<td><g:link class="linkBloque" action="show" id="${plantillaInstance.id}">${fieldValue(bean: plantillaInstance, field: "body")}</g:link></td>
							</g:else>                     
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${plantillaInstanceTotal}" />Registros Totales: ${plantillaInstanceTotal}
            </div>
        </div>
    </body>
</html>
