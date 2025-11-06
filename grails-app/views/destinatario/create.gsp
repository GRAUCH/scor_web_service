

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Nueva configuraci&oacute;n de destinatarios</title>
    </head>
    <body>

        <div class="body">
            <h1>Nueva configuraci&oacute;n de destinatarios</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${destinatarioInstance}">
            <div class="errors">
                <g:renderErrors bean="${destinatarioInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email"><g:message code="destinatario.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: destinatarioInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${destinatarioInstance?.email}" />
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
                            
                   <!--                                 
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fecha_alta"><g:message code="destinatario.fecha_alta.label" default="Fechaalta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: destinatarioInstance, field: 'fecha_alta', 'errors')}">
                                    <g:datePicker name="fecha_alta" precision="day" value="${destinatarioInstance?.fecha_alta}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fecha_modificacion"><g:message code="destinatario.fecha_modificacion.label" default="Fechamodificacion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: destinatarioInstance, field: 'fecha_modificacion', 'errors')}">
                                    <g:datePicker name="fecha_modificacion" precision="day" value="${destinatarioInstance?.fecha_modificacion}"  />
                                </td>
                            </tr>
                     --!>       
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
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/destinatario/list')}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
