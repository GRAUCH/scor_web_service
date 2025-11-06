

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Editando Dato webservice: ${datowebserviceInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Editando Dato webservice: ${datowebserviceInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${datowebserviceInstance}">
            <div class="errors">
                <g:renderErrors bean="${datowebserviceInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${datowebserviceInstance?.id}" />
                <g:hiddenField name="version" value="${datowebserviceInstance?.version}" />
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
                                    <g:select name="operacion.id" from="${com.scortelemed.Operacion.list()}" optionKey="id" value="${datowebserviceInstance?.operacion?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                 	<span class="button"><input class="create" type="button" value="A&ntilde;adir dato webservice" onclick="document.location='${createLinkTo(dir:'/datowebservice/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/datowebservice/list')}'"/></span>
                	<span class="button"><input class="cancel" type="button" value="Cancelar" onclick="document.location='${createLinkTo(dir:'/datowebservice/show/' +  datowebserviceInstance?.id)}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
