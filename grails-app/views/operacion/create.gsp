

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Nueva Operaci&oacute;n</title>
    </head>
    <body>

        <div class="body">
            <h1>Nueva Operaci&oacute;n</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${operacionInstance}">
            <div class="errors">
                <g:renderErrors bean="${operacionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="descripcion"><g:message code="operacion.descripcion.label" default="Descripcion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'descripcion', 'errors')}">
                                    <g:textField size="50" name="descripcion" value="${operacionInstance?.descripcion}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="activo"><g:message code="operacion.activo.label" default="Activo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'activo', 'errors')}">
                                    <g:checkBox name="activo" value="${operacionInstance?.activo}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="webservice"><g:message code="operacion.webservice.label" default="Webservice" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'webservice', 'errors')}">
                                    <g:select name="webservice.id" from="${com.scortelemed.Webservice.list()}" optionKey="id" value="${operacionInstance?.webservice?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="clave"><g:message code="operacion.clave.label" default="Clave" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'clave', 'errors')}">
                                    <g:textField size="50" name="clave" value="${operacionInstance?.clave}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass_externo"><g:message code="operacion.usuario_externo.label" default="Usuario externo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'usuario_externo', 'errors')}">
                                    <g:textField name="usuario_externo" value="${operacionInstance?.usuario_externo}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pass_externo"><g:message code="operacion.pass_externo.label" default="Password externo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'pass_externo', 'errors')}">
                                    <g:textField name="pass_externo" value="${operacionInstance?.pass_externo}" />
                                </td>
                            </tr>
                                                    
                            <!--                
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fecha_alta"><g:message code="operacion.fecha_alta.label" default="Fechaalta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'fecha_alta', 'errors')}">
                                    <g:datePicker name="fecha_alta" precision="day" value="${operacionInstance?.fecha_alta}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fecha_modificacion"><g:message code="operacion.fecha_modificacion.label" default="Fechamodificacion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: operacionInstance, field: 'fecha_modificacion', 'errors')}">
                                    <g:datePicker name="fecha_modificacion" precision="day" value="${operacionInstance?.fecha_modificacion}" noSelection="['': '']" />
                                </td>
                            </tr> 
                            -->                      
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                	<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/operacion/list')}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
