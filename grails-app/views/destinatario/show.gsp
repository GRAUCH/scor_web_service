

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Mostrando configuraci&oacute;n de destinatario: ${destinatarioInstance}</title>
    </head>
    <body>
    
        <div class="body">
            <h1>Mostrando configuraci&oacute;n de destinatario: ${destinatarioInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    <!--
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="destinatario.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: destinatarioInstance, field: "id")}</td>
                            
                        </tr>
                    -->
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="destinatario.email.label" default="Email" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: destinatarioInstance, field: "email")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="destinatario.nombre.label" default="Nombre" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: destinatarioInstance, field: "nombre")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="destinatario.apellidos.label" default="Apellidos" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: destinatarioInstance, field: "apellidos")}</td>
                            
                        </tr>
                                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="destinatario.activo.label" default="Activo" /></td>
                            <td valign="top" class="${destinatarioInstance.activo? 'si' : 'no'}"><span style="visibility: hidden">${fieldValue(bean:destinatario, field:'activo')}</span></td>
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="destinatario.fecha_alta.label" default="Fecha Alta" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${destinatarioInstance?.fecha_alta}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="destinatario.fecha_modificacion.label" default="Fecha Modificacion" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${destinatarioInstance?.fecha_modificacion}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="destinatario.avisos.label" default="Avisos" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${destinatarioInstance.avisos}" var="a">
                                    <div class="botonLupa"><li><g:link controller="aviso" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li></div>
                                </g:each>
                                </ul>
                        	</td>
                        </tr>
                        
                       <tr class="prop">
                            <td valign="top" class="name"><g:message code="destinatario.company.label" default="Compa&ntilde;ia" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: destinatarioInstance, field: "company")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${destinatarioInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><input class="create" type="button" value="A&ntilde;adir destinatario" onclick="document.location='${createLinkTo(dir:'/destinatario/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/destinatario/list')}'"/></span> 
                </g:form>
            </div>
        </div>
    </body>
</html>
