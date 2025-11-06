

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Mostrando plantilla: ${plantillaInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Mostrando plantilla: ${plantillaInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    <!--
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="plantilla.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: plantillaInstance, field: "id")}</td>
                            
                        </tr>
                    -->    
                        <tr class="prop">
                            <td valign="top" class="nombre"><g:message code="plantilla.nombre.label" default="Nombre" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: plantillaInstance, field: "nombre")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="plantilla.subject.label" default="Subject" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: plantillaInstance, field: "subject")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="plantilla.body.label" default="Body" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: plantillaInstance, field: "body")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="plantilla.fecha_alta.label" default="Fecha Alta" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${plantillaInstance?.fecha_alta}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="plantilla.fecha_modificacion.label" default="Fecha Modificacion" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${plantillaInstance?.fecha_modificacion}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="plantilla.avisos.label" default="Avisos" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${plantillaInstance.avisos}" var="a">
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
                    <g:hiddenField name="id" value="${plantillaInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><input class="create" type="button" value="A&ntilde;adir plantilla" onclick="document.location='${createLinkTo(dir:'/plantilla/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/plantilla/list')}'"/></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
