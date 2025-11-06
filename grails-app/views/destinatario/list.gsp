

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Listando de configuraci&oacute;n de destinatarios</title>
    </head>
    <body>

        <div class="body">
            <h1>Listando de configuraci&oacute;n de destinatarios</h1>
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" action="create">Nueva destinatario</g:link></span>
        	</div>
        	
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="email" title="${message(code: 'destinatario.email.label', default: 'Email')}" />
                            <g:sortableColumn property="nombre" title="${message(code: 'destinatario.nombre.label', default: 'Nombre')}" />
                            <g:sortableColumn property="apellidos" title="${message(code: 'destinatario.apellidos.label', default: 'Apellidos')}" />
                            <g:sortableColumn property="company" title="${message(code: 'destinatario.company.label', default: 'Compa&ntilde;ia')}" />
                       		<g:sortableColumn property="activo" title="${message(code: 'destinatario.activo.label', default: 'Activo')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${destinatarioInstanceList}" status="i" var="destinatarioInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link class="linkBloque" action="show" id="${destinatarioInstance.id}">${fieldValue(bean: destinatarioInstance, field: "email")}</g:link></td>
                            <td><g:link class="linkBloque" action="show" id="${destinatarioInstance.id}">${fieldValue(bean: destinatarioInstance, field: "nombre")}</g:link></td>
                            <td><g:link class="linkBloque" action="show" id="${destinatarioInstance.id}">${fieldValue(bean: destinatarioInstance, field: "apellidos")}</g:link></td>                        
                            <td><g:link class="linkBloque" action="show" id="${destinatarioInstance.id}">${fieldValue(bean: destinatarioInstance, field: "company")}</g:link></td>
							<td class="${destinatarioInstance.activo? 'silist' : 'nolist'}"><g:link class="linkBloque" action="show" id="${destinatarioInstance.id}"><span style="visibility: hidden">${fieldValue(bean:destinatarioInstance, field:'activo')}</span></g:link></td>                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${destinatarioInstanceTotal}" />Registros Totales: ${destinatarioInstanceTotal}
            </div>
        </div>
    </body>
</html>
