

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Editando plantilla: ${plantillaInstance}</title>
        <ckeditor:resources />
    </head>
    <body>

        <div class="body">
            <h1>Editando plantilla: ${plantillaInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${plantillaInstance}">
            <div class="errors">
                <g:renderErrors bean="${plantillaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${plantillaInstance?.id}" />
                <g:hiddenField name="version" value="${plantillaInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                           <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="nombre"><g:message code="plantilla.nombre.label" default="Nombre" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: plantillaInstance, field: 'nombre', 'errors')}">
                                    <g:textField name="nombre" value="${plantillaInstance?.nombre}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="subject"><g:message code="plantilla.subject.label" default="Subject" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: plantillaInstance, field: 'subject', 'errors')}">
                                    <g:textField size="100" name="subject" value="${plantillaInstance?.subject}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="body"><g:message code="plantilla.body.label" default="Body" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: plantillaInstance, field: 'body', 'errors')}">
                                    <ckeditor:config var="toolbar_mibarra">
									[
    									['Source'], ['Bold', 'Italic', 'Underline', 'Strike'],
    									['Styles', 'Format', 'Font', 'FontSize', 'TextColor', 'BGColor']
    									
									]
									</ckeditor:config>
                                    <ckeditor:editor name="body" toolbar="mibarra">
										${plantillaInstance?.body}
									</ckeditor:editor>
                                    <g:include controller="plantilla" action="ayudante" params="[plan: plantillaInstance.avisos.operacion]" />
                                </td>
                            </tr>
                        <!--
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fecha_alta"><g:message code="plantilla.fecha_alta.label" default="Fechaalta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: plantillaInstance, field: 'fecha_alta', 'errors')}">
                                    <g:datePicker name="fecha_alta" precision="day" value="${plantillaInstance?.fecha_alta}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fecha_modificacion"><g:message code="plantilla.fecha_modificacion.label" default="Fechamodificacion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: plantillaInstance, field: 'fecha_modificacion', 'errors')}">
                                    <g:datePicker name="fecha_modificacion" precision="day" value="${plantillaInstance?.fecha_modificacion}"  />
                                </td>
                            </tr>
                        --!>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="avisos"><g:message code="plantilla.avisos.label" default="Avisos" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: plantillaInstance, field: 'avisos', 'errors')}">
                                    
							<ul>
								<g:each in="${plantillaInstance?.avisos?}" var="a">
    								<div class="botonLupa"><li><g:link controller="aviso" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li></div>
								</g:each>
							</ul>
							<g:link controller="aviso" action="create" params="['plantilla.id': plantillaInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'aviso.label', default: 'Aviso')])}</g:link>

                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                	<span class="button"><input class="create" type="button" value="A&ntilde;adir plantilla" onclick="document.location='${createLinkTo(dir:'/plantilla/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/plantilla/list')}'"/></span>
                	<span class="button"><input class="cancel" type="button" value="Cancelar" onclick="document.location='${createLinkTo(dir:'/plantilla/show/' +  plantillaInstance?.id)}'"/></span>                             
                </div>
            </g:form>
        </div>
    </body>
</html>
