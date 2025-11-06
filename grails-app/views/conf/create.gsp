

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Nueva opci&oacute;n de configuraci&oacute;n</title>         
    </head>
    <body>
        <div class="body">
		                 
            <h1>Nueva opci&oacute;n de configuraci&oacute;n</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${confInstance}">
            <div class="errors">
                <g:renderErrors bean="${confInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Clave:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:confInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:confInstance,field:'name')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="value">Valor:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:confInstance,field:'value','errors')}">
                                    <input type="text" id="value" name="value" value="${fieldValue(bean:confInstance,field:'value')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Descripci&oacute;n:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:confInstance,field:'description','errors')}">
                                    <input type="text" id="description" name="description" value="${fieldValue(bean:confInstance,field:'description')}"/>
                                </td>
                            </tr> 
                            
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Save" label="Guardar"/></span>
                    <span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/conf/list')}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
