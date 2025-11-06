

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Nuevo Dato webservice</title>
    </head>
    <body>

        <div class="body">
            <h1>Nuevo Dato webservice</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${datowebserviceInstance}">
            <div class="errors">
                <g:renderErrors bean="${datowebserviceInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nombre"><g:message code="datowebservice.nombre.label" default="Nombre" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: datowebserviceInstance, field: 'nombre', 'errors')}">
                                    <g:textField name="nombre" value="${datowebserviceInstance?.nombre}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tipo"><g:message code="datowebservice.tipo.label" default="Tipo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: datowebserviceInstance, field: 'tipo', 'errors')}">
                                    <g:textField name="tipo" value="${datowebserviceInstance?.tipo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="operacion"><g:message code="datowebservice.operacion.label" default="Operaci&oacute;n" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: datowebserviceInstance, field: 'operacion', 'errors')}">
                                    <g:select name="operacion.id" from="${com.scortelemed.Operacion.list()}" optionKey="id" value="${params.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                	<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/datowebservice/list')}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
