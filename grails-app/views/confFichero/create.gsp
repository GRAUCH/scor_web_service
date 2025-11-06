


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'confFichero.label', default: 'ConfFichero')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
		                 
            <h1>Nueva opci&oacute;n de configuraci&oacute;n de Ficheros</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${confFicheroInstance}">
            <div class="errors">
                <g:renderErrors bean="${confFicheroInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="confFichero.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: confFicheroInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${confFicheroInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="value"><g:message code="confFichero.value.label" default="Value" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: confFicheroInstance, field: 'value', 'errors')}">
                                    <g:textArea name="value" cols="40" rows="5" value="${confFicheroInstance?.value}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="confFichero.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: confFicheroInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${confFicheroInstance?.description}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/confFichero/list')}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
