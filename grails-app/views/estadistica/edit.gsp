

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Editando estadistica: ${estadisticaInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Editando estadistica: ${estadisticaInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${estadisticaInstance}">
            <div class="errors">
                <g:renderErrors bean="${estadisticaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${estadisticaInstance?.id}" />
                <g:hiddenField name="version" value="${estadisticaInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="request"><g:message code="estadistica.request.label" default="Request" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: estadisticaInstance, field: 'request', 'errors')}">
                                    <g:textField name="request" value="${estadisticaInstance?.request}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="clave"><g:message code="estadistica.clave.label" default="Clave" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: estadisticaInstance, field: 'clave', 'errors')}">
                                    <g:textField name="clave" value="${estadisticaInstance?.clave}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="value"><g:message code="estadistica.value.label" default="Value" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: estadisticaInstance, field: 'value', 'errors')}">
                                    <g:textField name="value" value="${estadisticaInstance?.value}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="operacion"><g:message code="estadistica.operacion.label" default="Operaci&oacute;n" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: estadisticaInstance, field: 'operacion', 'errors')}">
                                    <g:select name="operacion.id" from="${com.scortelemed.Operacion.list()}" optionKey="id" value="${estadisticaInstance?.operacion?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><input class="create" type="button" value="A&ntilde;adir estadistica" onclick="document.location='${createLinkTo(dir:'/estadistica/create')}'"/></span>
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/estadistica/list')}'"/></span> 
                </div>
            </g:form>
        </div>
    </body>
</html>
