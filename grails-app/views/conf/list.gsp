

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Listando Configuraciones</title>
    </head>
    <body>
        <div class="body">
		             
            <h1>Listando Configuraciones</h1>
            <div>
            	Atenci&oacute;n si cambia alg&uacute;n dato cr&iacute;tico puede que la aplicaci&oacute;n deje de funcionar o lo haga de manera no esperada. Existen campos OBLIGATORIOS que no deberian ser borrados para el correcto funcionamiento: <b>template_email, admin_email, carpetaFicheros y prefijoFicheros.</b>
            </div>
            <br />
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" action="create">Nueva opci&oacute;n de configuraci&oacute;n</g:link></span>
        	</div>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                   	        <g:sortableColumn property="name" title="Clave" />
                   	        <g:sortableColumn property="value" title="Valor" />
                   	        <g:sortableColumn property="description" title="Descripci&oacute;n" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${confInstanceList}" status="i" var="confInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="edit" id="${confInstance.id}">${fieldValue(bean:confInstance, field:'name')}</g:link></td>
                            <td><g:link action="edit" id="${confInstance.id}">${fieldValue(bean:confInstance, field:'value')}</g:link></td>
                            <td><g:link action="edit" id="${confInstance.id}">${fieldValue(bean:confInstance, field:'description')}</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${confInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
