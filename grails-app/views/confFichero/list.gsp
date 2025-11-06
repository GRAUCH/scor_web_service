

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'confFichero.label', default: 'ConfFichero')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
		             
            <h1>Listando Configuraciones de Ficheros</h1>
            <div>
            	Atenci&oacute;n si cambia alg&uacute;n dato cr&iacute;tico para la generaci&oacute;n de ficheros puede que la aplicaci&oacute;n deje de funcionar o lo haga de manera no esperada. Existen campos autogenerados en estas configuraciones que no deberian ser modificados.
            </div>
            <br />
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" action="create">Nueva opci&oacute;n de configuraci&oacute;n de Ficheros</g:link></span>
        	</div>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="id" title="${message(code: 'confFichero.id.label', default: 'Id')}" />
                            <g:sortableColumn property="name" title="${message(code: 'confFichero.name.label', default: 'Name')}" />
                            <g:sortableColumn property="value" title="${message(code: 'confFichero.value.label', default: 'Value')}" />
                            <g:sortableColumn property="description" title="${message(code: 'confFichero.description.label', default: 'Description')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${confFicheroInstanceList}" status="i" var="confFicheroInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${confFicheroInstance.id}">${fieldValue(bean: confFicheroInstance, field: "id")}</g:link></td>
                            <td>${fieldValue(bean: confFicheroInstance, field: "name")}</td>
                            <td>${fieldValue(bean: confFicheroInstance, field: "value")}</td>
                            <td>${fieldValue(bean: confFicheroInstance, field: "description")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${confFicheroInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
