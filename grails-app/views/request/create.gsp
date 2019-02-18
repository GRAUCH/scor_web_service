


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />

        <title>Nueva Request</title>
    </head>
    <body>

        <div class="body">
            <h1>Nueva Request</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${requestInstance}">
            <div class="errors">
                <g:renderErrors bean="${requestInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fecha_procesado"><g:message code="request.fecha_procesado.label" default="Fechaprocesado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: requestInstance, field: 'fecha_procesado', 'errors')}">
                                    <g:datePicker name="fecha_procesado" precision="day" value="${requestInstance?.fecha_procesado}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="claveProceso"><g:message code="request.claveProceso.label" default="Clave proceso" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: requestInstance, field: 'claveProceso', 'errors')}">
                                    <g:textField name="claveProceso" value="${requestInstance?.claveProceso}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="operacion"><g:message code="request.operacion.label" default="Operacion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: requestInstance, field: 'operacion', 'errors')}">
                                    <g:select name="operacion.id" from="${com.scortelemed.Operacion.list()}" optionKey="id" value="${requestInstance?.operacion?.id}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="company"><g:message code="request.company.label" default="Comap&ntilde;ia" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: requestInstance, field: 'company', 'errors')}">
                                    <g:select name="company.id" from="${com.scortelemed.Company.list()}" optionKey="id" value="${requestInstance?.company?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="request"><g:message code="request.request.label" default="Request" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: requestInstance, field: 'request', 'errors')}">
                                    <g:textField name="request" value="${requestInstance?.request}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                	<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/request/list')}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
