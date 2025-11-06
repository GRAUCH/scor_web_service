

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Editando webservice: ${webserviceInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Editando webservice: ${webserviceInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${webserviceInstance}">
            <div class="errors">
                <g:renderErrors bean="${webserviceInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${webserviceInstance?.id}" />
                <g:hiddenField name="version" value="${webserviceInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="clave"><g:message code="webservice.nombre.label" default="Clave" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: webserviceInstance, field: 'clave', 'errors')}">
                                    <g:textField size="50" name="clave" value="${webserviceInstance?.clave}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="descripcion"><g:message code="webservice.nombre.label" default="Descripcion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: webserviceInstance, field: 'descripcion', 'errors')}">
                                    <g:textField size="50" name="descripcion" value="${webserviceInstance?.descripcion}" />
                                </td>
                            </tr>

                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="operaciones"><g:message code="webservice.operaciones.label" default="Operaciones" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: webserviceInstance, field: 'operaciones', 'errors')}">
                                    
<ul>
<g:each in="${webserviceInstance?.operaciones?}" var="o">
    <div class="botonLupa"><li><g:link controller="operacion" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></li></div>
</g:each>
</ul>
<g:link controller="operacion" action="create" params="['webservice.id': webserviceInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'operacion.label', default: 'Operacion')])}</g:link>

                                </td>
                            </tr>
                                         
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass_externo"><g:message code="webservice.pass_externo.label" default="Contrase&ntilde;a usuario externo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: webserviceInstance, field: 'pass_externo', 'errors')}">
                                    <g:textField name="pass_externo" value="${webserviceInstance?.pass_externo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="usuario_externo"><g:message code="webservice.usuario_externo.label" default="Usuario externo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: webserviceInstance, field: 'usuario_externo', 'errors')}">
                                    <g:textField name="usuario_externo" value="${webserviceInstance?.usuario_externo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="activo"><g:message code="webservice.activo.label" default="Activo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: webserviceInstance, field: 'activo', 'errors')}">
                                    <g:checkBox name="activo" value="${webserviceInstance?.activo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="usuarios"><g:message code="webservice.usuarios.label" default="Usuarios" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: webserviceInstance, field: 'usuarios', 'errors')}">
                                   <!--  <g:select name="usuarios" from="${com.scortelemed.Person.list()}" multiple="yes" optionKey="id" size="5" value="${webserviceInstance?.usuarios*.id}" />
                                	-->
                                	<ul>
										<g:each var="persona" in="${listaFinal}">
											<li>${persona.key.encodeAsHTML()}
												<g:checkBox name="US_${persona.key.id}" value="${persona.value}"/>
											</li>
										</g:each>
									</ul>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                	<span class="button"><input class="create" type="button" value="A&ntilde;adir webservice" onclick="document.location='${createLinkTo(dir:'/webservice/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/webservice/list')}'"/></span>
                	<span class="button"><input class="cancel" type="button" value="Cancelar" onclick="document.location='${createLinkTo(dir:'/webservice/show/' +  webserviceInstance?.id)}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
