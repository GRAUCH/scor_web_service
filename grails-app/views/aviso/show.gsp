

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Mostrando aviso: ${avisoInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Mostrando aviso: ${avisoInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="aviso.nombre.label" default="Nombre" /></td>
                            <td valign="top" class="value">${fieldValue(bean: avisoInstance, field: "nombre")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="aviso.fecha_alta.label" default="Fecha Alta" /></td>
                            <td valign="top" class="value"><g:formatDate date="${avisoInstance?.fecha_alta}" /></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="aviso.fecha_modificacion.label" default="Fecha Modificacion" /></td>
                            <td valign="top" class="value"><g:formatDate date="${avisoInstance?.fecha_modificacion}" /></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="aviso.plantilla.label" default="Plantilla" /></td>
                            <td valign="top" class="value botonLupa"><g:link controller="plantilla" action="show" id="${avisoInstance?.plantilla?.id}">${avisoInstance?.plantilla?.encodeAsHTML()}</g:link></td>
                        </tr>              
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="aviso.operacion.label" default="Operaci&oacute;n" /></td>                         
                            <td valign="top" class="value botonLupa"><g:link controller="operacion" action="show" id="${avisoInstance?.operacion?.id}">${avisoInstance?.operacion?.encodeAsHTML()}</g:link></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="aviso.activo.label" default="Activo" /></td>
                            <td valign="top" class="${avisoInstance.activo? 'si' : 'no'}"><span style="visibility: hidden">${fieldValue(bean:aviso, field:'activo')}</span></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="aviso.destinatarios.label" default="Destinatarios" /></td>
                            <td valign="top" class="value">
                            	<g:each in="${avisoInstance.destinatarios}" var="a">
                                	<div class="botonLupa"><li><g:link controller="destinatario" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li></div>
                                </g:each>
                            </td>
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${avisoInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><input class="create" type="button" value="A&ntilde;adir aviso" onclick="document.location='${createLinkTo(dir:'/aviso/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/aviso/list')}'"/></span>  
                </g:form>
            </div>
        </div>
    </body>
</html>
