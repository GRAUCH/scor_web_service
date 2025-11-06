

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Editando configuraci&oacute;n de destinatario: ${destinatarioInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Editando configuraci&oacute;n de destinatario: ${destinatarioInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${destinatarioInstance}">
            <div class="errors">
                <g:renderErrors bean="${destinatarioInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${destinatarioInstance?.id}" />
                <g:hiddenField name="version" value="${destinatarioInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="email"><g:message code="destinatario.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: destinatarioInstance, field: 'email', 'errors')}">
                                    <g:textField size="50" name="email" value="${destinatarioInstance?.email}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="nombre"><g:message code="destinatario.nombre.label" default="Nombre" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: destinatarioInstance, field: 'nombre', 'errors')}">
                                    <g:textField name="nombre" value="${destinatarioInstance?.nombre}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="apellidos"><g:message code="destinatario.apellidos.label" default="Apellidos" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: destinatarioInstance, field: 'apellidos', 'errors')}">
                                    <g:textField name="apellidos" value="${destinatarioInstance?.apellidos}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activo"><g:message code="destinatario.activo.label" default="Activo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: destinatarioInstance, field: 'activo', 'errors')}">
                                    <g:checkBox name="activo" value="${destinatarioInstance?.activo}" />
                                </td>
                            </tr>
                                                                          
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="company"><g:message code="destinatario.company.label" default="Compa&ntilde;ia" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: destinatarioInstance, field: '"company"', 'errors')}">
                                    <g:select name="company.id" from="${com.scortelemed.Company.list()}" optionKey="id" value="${destinatarioInstance?.company?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><input class="create" type="button" value="A&ntilde;adir destinatario" onclick="document.location='${createLinkTo(dir:'/destinatario/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/destinatario/list')}'"/></span>                 
                	<span class="button"><input class="cancel" type="button" value="Cancelar" onclick="document.location='${createLinkTo(dir:'/destinatario/show/' +  destinatarioInstance?.id)}'"/></span>                			
                </div>
            </g:form>
        </div>
    </body>
</html>
