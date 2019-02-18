

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Nuevo aviso</title>
    </head>
    <body>

        <div class="body">
            <h1>Nuevo aviso</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${avisoInstance}">
            <div class="errors">
                <g:renderErrors bean="${avisoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nombre"><g:message code="aviso.nombre.label" default="Nombre" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: avisoInstance, field: 'nombre', 'errors')}">
                                    <g:textField name="nombre" value="${avisoInstance?.nombre}" />
                                </td>
                            </tr>
					<!--
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fecha_alta"><g:message code="aviso.fecha_alta.label" default="Fechaalta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: avisoInstance, field: 'fecha_alta', 'errors')}">
                                    <g:datePicker name="fecha_alta" precision="day" value="${avisoInstance?.fecha_alta}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fecha_modificacion"><g:message code="aviso.fecha_modificacion.label" default="Fechamodificacion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: avisoInstance, field: 'fecha_modificacion', 'errors')}">
                                    <g:datePicker name="fecha_modificacion" precision="day" value="${avisoInstance?.fecha_modificacion}"  />
                                </td>
                            </tr>
                     --!>   
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="plantilla"><g:message code="aviso.plantilla.label" default="Plantilla" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: avisoInstance, field: 'plantilla', 'errors')}">
                                    <g:select name="plantilla.id" from="${com.scortelemed.Plantilla.list()}" optionKey="id" value="${avisoInstance?.plantilla?.id}"  />
                                </td>
                            </tr>
                            
                             <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="operacion"><g:message code="aviso.operacion.label" default="Operaci&oacute;n" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: avisoInstance, field: '"operacion"', 'errors')}">
                                    <g:select name="operacion.id" from="${com.scortelemed.Operacion.list()}" optionKey="id" value="${avisoInstance?.operacion?.id}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="destinatarios"><g:message code="aviso.destinatarios.label" default="Destinatarios" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: avisoInstance, field: '"destinatarios"', 'errors')}">
                                    <g:select multiple="true" name="destinatarios.id" from="${com.scortelemed.Destinatario.list()}" optionKey="id" value="${avisoInstance?.destinatarios?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activo"><g:message code="aviso.activo.label" default="Activo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: avisoInstance, field: 'activo', 'errors')}">
                                    <g:checkBox name="activo" value="${avisoInstance?.activo}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                	<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/aviso/list')}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
