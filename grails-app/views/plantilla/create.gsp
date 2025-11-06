

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Nueva plantilla</title>
        <ckeditor:resources />
    </head>
    <body>

        <div class="body">
            <h1>Nueva plantilla</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${plantillaInstance}">
            <div class="errors">
                <g:renderErrors bean="${plantillaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form name="formulario" action="save" method="post" >
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
                                <td valign="top"class="value ${hasErrors(bean: plantillaInstance, field: 'body', 'errors')}">
                                    <ckeditor:config var="toolbar_mibarra">
									[
    									['Source'], ['Bold', 'Italic', 'Underline', 'Strike'],
    									['Styles', 'Format', 'Font', 'FontSize', 'TextColor', 'BGColor']
    									
									]
									</ckeditor:config>
                                    <ckeditor:editor name="body" toolbar="mibarra">
										${plantillaInstance?.body}
									</ckeditor:editor>
									<g:if test="${params.plan}">
										<g:include controller="plantilla" action="ayudante" params="[plan: [params.plan]]" />
									</g:if>
									<g:else>
										<g:include controller="plantilla" action="ayudante" />
									</g:else>
																		
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
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                	<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/plantilla/list')}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
