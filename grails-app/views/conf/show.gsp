

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Mostrando Configuraci&oacute;n: ${confInstance}</title>
    </head>
    <body>
        <div class="body">          
            <h1>Mostrando Configuraci&oacute;n: ${confInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Clave:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:confInstance, field:'name')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Valor:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:confInstance, field:'value')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Descripci&oacute;n:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:confInstance, field:'description')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${confInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Editar" action="edit"/></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Seguro?');" value="Borrar" action="delete"/></span>
                    <span class="button"><input class="create" type="button" value="A&ntilde;adir opci&oacute;n de configuraci&oacute;n" onclick="document.location='${createLinkTo(dir:'/conf/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/conf/list')}'"/></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
