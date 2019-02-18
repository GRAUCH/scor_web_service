

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <g:javascript library="prototype" />
        <title>Editando opci&oacute;n de configuraci&oacute;n: ${confInstance}</title>
    </head>
    <body>
        <div class="body">       
            <h1>Editando opci&oacute;n de configuraci&oacute;n: ${confInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${confInstance}">
            <div class="errors">
                <g:renderErrors bean="${confInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" action="update">
                <input type="hidden" name="id" value="${confInstance?.id}" />
                <input type="hidden" name="version" value="${confInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Clave:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:confInstance,field:'name','errors')}">
                                
                                <g:if test="${confInstance.name.contains('webclientes')}">
                                    <label for="name">${fieldValue(bean:confInstance,field:'name')}</label>
                                </g:if>
                                <g:else>
                                	<input type="text" id="name" name="name" size="100" value="${fieldValue(bean:confInstance,field:'name')}"/>
                                </g:else>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="value">Valor:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:confInstance,field:'value','errors')}">
                                    <textarea id="value" name="value" cols="100" rows="20">${fieldValue(bean:confInstance,field:'value')}</textarea>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Descripci&oacute;n:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:confInstance,field:'description','errors')}">
                                    <input type="text" id="description" name="description" size="100" value="${fieldValue(bean:confInstance,field:'description')}"/>
                                </td>
                            </tr> 

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Guardar" action="update"/></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Seguro?');" value="Borrar" action="delete"/></span>
                    <span class="button"><input class="create" type="button" value="A&ntilde;adir opci&oacute;n de configuraci&oacute;n" onclick="document.location='${createLinkTo(dir:'/conf/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/conf/list')}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
