

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Mostrando restriccion por Ip: ${ipcontrolInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Mostrando restriccion por Ip: ${ipcontrolInstance}</h1>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="ipcontrol.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: ipcontrolInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="ipcontrol.ipAddress.label" default="Ip Address" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: ipcontrolInstance, field: "ipAddress")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="ipcontrol.company.label" default="Company" /></td>
                            
                            <td valign="top" class="value botonLupa"><g:link controller="company" action="show" id="${ipcontrolInstance?.company?.id}">${ipcontrolInstance?.company?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${ipcontrolInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><input class="create" type="button" value="A&ntilde;adir ip" onclick="document.location='${createLinkTo(dir:'/ipcontrol/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Mostrar Compa&ntilde;ia" onclick="document.location='${createLinkTo(dir:'/company/show/' + ipcontrolInstance.company.id)}'"/></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
