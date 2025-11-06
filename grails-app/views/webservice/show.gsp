

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Mostrando webservice: ${webserviceInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Mostrando webservice: ${webserviceInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="webservice.clave.label" default="Clave" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: webserviceInstance, field: "clave")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="webservice.descripcion.label" default="Descripcion" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: webserviceInstance, field: "descripcion")}</td>
                            
                        </tr>

                <!--    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="webservice.operaciones.label" default="Operaciones" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${webserviceInstance.operaciones}" var="o">
                                    <div class="botonLupa"><li><g:link controller="operacion" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></li></div>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                --!>    
                
                                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="webservice.pass_externo.label" default="Contrase&ntilde;a usuario externo" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: webserviceInstance, field: "pass_externo")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="webservice.usuario_externo.label" default="Usuario externo" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: webserviceInstance, field: "usuario_externo")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="webservice.activo.label" default="Activo" /></td>
                            
                            <td valign="top" class="${webserviceInstance.activo? 'si' : 'no'}" ><g:formatBoolean boolean="${webserviceInstance?.activo}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="webservice.usuarios.label" default="Usuarios" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${webserviceInstance.usuarios}" var="u">
                                    <div class="botonLupa"><li><g:link controller="person" action="show" id="${u.id}">${u?.username.encodeAsHTML()}</g:link></li></div>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${webserviceInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                 	<span class="button"><input class="create" type="button" value="A&ntilde;adir webservice" onclick="document.location='${createLinkTo(dir:'/webservice/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/webservice/list')}'"/></span> 
                </g:form>
            </div>
            <div id="relacion" >
        		<g:include controller="webservice" action="operacion" id="${webserviceInstance.id}"></g:include>
        	</div>
        </div>
    </body>
</html>
