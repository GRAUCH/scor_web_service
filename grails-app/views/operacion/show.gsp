

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Mostrando operaci&oacute;n: ${operacionInstance}</title>
    </head>
    <body>
        <div class="body">
            <h1>Mostrando operaci&oacute;n: ${operacionInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="operacion.descripcion.label" default="Descripcion" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: operacionInstance, field: "descripcion")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="operacion.activo.label" default="Activo" /></td>
                            
                            <td valign="top" class="${operacionInstance.activo? 'si' : 'no'}" ><g:formatBoolean boolean="${operacionInstance?.activo}" /></td>
                            
                        </tr>

                   <!-- 
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="operacion.datowebservice.label" default="Datowebservice" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${operacionInstance.datowebservice}" var="d">
                                    <div class="botonLupa"><li><g:link controller="datowebservice" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></li></div>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="operacion.estadisticas.label" default="Estadisticas" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${operacionInstance.estadisticas}" var="e">
                                    <div class="botonLupa"><li><g:link controller="estadistica" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li></div>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                        
                                            
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="operacion.fecha_alta.label" default="Fechaalta" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${operacionInstance?.fecha_alta}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="operacion.fecha_modificacion.label" default="Fechamodificacion" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${operacionInstance?.fecha_modificacion}" /></td>
                            
                        </tr>
                    --!>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="operacion.webservice.label" default="Webservice" /></td>
                            
                            <td valign="top" class="value botonLupa"><g:link controller="webservice" action="show" id="${operacionInstance?.webservice?.id}">${operacionInstance?.webservice?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="operacion.clave.label" default="Clave" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: operacionInstance, field: "clave")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="operacion.usuario_externo.label" default="Usuarioexterno" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: operacionInstance, field: "usuario_externo")}</td>
                            
                        </tr>
                                            
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="operacion.pass_externo.label" default="Passexterno" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: operacionInstance, field: "pass_externo")}</td>
                            
                        </tr>
                                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="operacion.avisos.label" default="Avisos asociados" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${operacionInstance.avisos}" var="a">
                                    <div class="botonLupa"><li><g:link controller="aviso" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li></div>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${operacionInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><input class="create" type="button" value="A&ntilde;adir operaci&oacute;n" onclick="document.location='${createLinkTo(dir:'/operacion/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/operacion/list')}'"/></span> 
                	<span class="menuButton"><g:link class="create" controller="plantilla" action="create" params="[plan: [operacionInstance.id]]" >Nueva Plantilla para esta operaci&oacute;n</g:link></span>
                </g:form>
            </div>
            <div id="relacion" >
        		<g:include controller="operacion" action="datowebservice" id="${operacionInstance.id}"></g:include>
        	</div>
        </div>
    </body>
</html>
