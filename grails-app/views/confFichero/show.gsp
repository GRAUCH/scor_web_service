

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'confFichero.label', default: 'ConfFichero')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">          
            <h1>Mostrando Configuraci&oacute;n de Fichero: ${confFicheroInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="confFichero.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: confFicheroInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="confFichero.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: confFicheroInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="confFichero.value.label" default="Value" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: confFicheroInstance, field: "value")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="confFichero.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: confFicheroInstance, field: "description")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${confFicheroInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                  <span class="button"><input class="create" type="button" value="A&ntilde;adir opci&oacute;n de configuraci&oacute;n de Ficheros" onclick="document.location='${createLinkTo(dir:'/confFichero/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/confFichero/list')}'"/></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
