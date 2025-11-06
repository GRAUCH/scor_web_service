

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Editando operaci&oacute;n: ${operacionInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Editando operaci&oacute;n: ${operacionInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${operacionInstance}">
            <div class="errors">
                <g:renderErrors bean="${operacionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${operacionInstance?.id}" />
                <g:hiddenField name="version" value="${operacionInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                   <!--     
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fecha_alta"><g:message code="operacion.fecha_alta.label" default="Fechaalta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'fecha_alta', 'errors')}">
                                    <g:datePicker name="fecha_alta" precision="day" value="${operacionInstance?.fecha_alta}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fecha_modificacion"><g:message code="operacion.fecha_modificacion.label" default="Fechamodificacion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'fecha_modificacion', 'errors')}">
                                    <g:datePicker name="fecha_modificacion" precision="day" value="${operacionInstance?.fecha_modificacion}" noSelection="['': '']" />
                                </td>
                            </tr>
                     --!> 
                                             
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="descripcion"><g:message code="operacion.descripcion.label" default="Descripcion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'descripcion', 'errors')}">
                                    <g:textField name="descripcion" value="${operacionInstance?.descripcion}" />
                                </td>
                            </tr>
                            
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activo"><g:message code="operacion.activo.label" default="Activo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'activo', 'errors')}">
                                    <g:checkBox name="activo" value="${operacionInstance?.activo}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="webservice"><g:message code="operacion.webservice.label" default="Webservice" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'webservice', 'errors')}">
                                    <g:select name="webservice.id" from="${com.scortelemed.Webservice.list()}" optionKey="id" value="${operacionInstance?.webservice?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="clave"><g:message code="operacion.clave.label" default="Clave" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'clave', 'errors')}">
                                    <g:textField name="clave" value="${operacionInstance?.clave}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass_externo"><g:message code="operacion.usuario_externo.label" default="Usuario externo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'usuario_externo', 'errors')}">
                                    <g:textField name="usuario_externo" value="${operacionInstance?.usuario_externo}" />
                                </td>
                            </tr>
                              
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass_externo"><g:message code="operacion.pass_externo.label" default="Password externo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'pass_externo', 'errors')}">
                                    <g:textField name="pass_externo" value="${operacionInstance?.pass_externo}" />
                                </td>
                            </tr>

                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="avisos"><g:message code="operacion.avisos.label" default="Avisos asociados" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'avisos', 'errors')}">
									<ul>
										<g:each in="${operacionInstance?.avisos?}" var="a">
    										<div class="botonLupa"><li><g:link controller="aviso" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li></div>
										</g:each>
									</ul>
									<g:link controller="aviso" action="create" params="['operacion.id': operacionInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'aviso.label', default: 'Aviso')])}</g:link>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                 	<span class="button"><input class="create" type="button" value="A&ntilde;adir operaci&oacute;n" onclick="document.location='${createLinkTo(dir:'/operacion/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/operacion/list')}'"/></span> 
                	<span class="button"><input class="cancel" type="button" value="Cancelar" onclick="document.location='${createLinkTo(dir:'/operacion/show/' +  operacionInstance?.id)}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
