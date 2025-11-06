<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Mostrando estadistica: ${estadisticaInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Mostrando estadistica: ${estadisticaInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="estadistica.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: estadisticaInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="estadistica.request.label" default="Request" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: estadisticaInstance, field: "request")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="estadistica.clave.label" default="Clave" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: estadisticaInstance, field: "clave")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="estadistica.value.label" default="Value" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: estadisticaInstance, field: "value")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="estadistica.operacion.label" default="Operaci&oacute;n" /></td>
                            
                            <td valign="top" class="value botonLupa"><g:link controller="operacion" action="show" id="${estadisticaInstance?.operacion?.id}">${estadisticaInstance?.operacion?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="estadistica.fecha_alta.label" default="Fecha Alta" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${estadisticaInstance?.fecha_alta}" /></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${estadisticaInstance?.id}" />
                    <!--
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><input class="create" type="button" value="A&ntilde;adir estadistica" onclick="document.location='${createLinkTo(dir:'/estadistica/create')}'"/></span>
					--!>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/estadistica/list')}'"/></span>
					<span class="button"><input class="list" type="button" value="Mostrar Request" onclick="document.location='${createLinkTo(dir:'/request/show/' + estadisticaInstance.request.id)}'"/></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
