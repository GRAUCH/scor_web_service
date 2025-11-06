

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Nuevo webservice</title>
    </head>
    <body>

        <div class="body">
            <h1>Nuevo webservice</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${webserviceInstance}">
            <div class="errors">
                <g:renderErrors bean="${webserviceInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
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
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                	<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/webservice/list')}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
