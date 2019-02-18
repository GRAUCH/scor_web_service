

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Mostrando dato de webservice: ${datowebserviceInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Mostrando dato de webservice: ${datowebserviceInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    <!--
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="datowebservice.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: datowebserviceInstance, field: "id")}</td>
                            
                        </tr>
                    -->
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="datowebservice.nombre.label" default="Nombre" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: datowebserviceInstance, field: "nombre")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="datowebservice.tipo.label" default="Tipo" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: datowebserviceInstance, field: "tipo")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="datowebservice.operacion.label" default="Operaci&oacute;n" /></td>
                            
                            <td valign="top" class="value botonLupa"><g:link controller="operacion" action="show" id="${datowebserviceInstance?.operacion?.id}">${datowebserviceInstance?.operacion?.encodeAsHTML()}</g:link> [${datowebserviceInstance.operacion.webservice}]</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${datowebserviceInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                	<span class="button"><input class="create" type="button" value="A&ntilde;adir dato webservice" onclick="document.location='${createLinkTo(dir:'/datowebservice/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/datowebservice/list')}'"/></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
