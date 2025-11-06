


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />

        <title>Editando Request: ${requestInstance}</title>
    </head>
    <body>

        <div class="body">
            <h1>Editando Request: ${requestInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${requestInstance}">
            <div class="errors">
                <g:renderErrors bean="${requestInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${requestInstance?.id}" />
                <g:hiddenField name="version" value="${requestInstance?.version}" />
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
                                    <g:textField disabled="true" name="claveProceso" value="${requestInstance?.claveProceso}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="estadisticas"><g:message code="request.estadisticas.label" default="Estadisticas" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: requestInstance, field: 'estadisticas', 'errors')}">
									<ul>
										<g:each in="${requestInstance?.estadisticas?}" var="e">
<%--    										<div class="botonLupa"><li><g:link controller="estadistica" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li></div>--%>
    										<div class="botonLupa"><li><label>${e?.encodeAsHTML()}</label></li></div>
										</g:each>
									</ul>
<%--									<g:link controller="estadistica" action="create" params="['request.id': requestInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'estadistica.label', default: 'Estadistica')])}</g:link>--%>
										<label>${message(code: 'default.add.label', args: [message(code: 'estadistica.label', default: 'Estadistica')])}</label>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="operacion"><g:message code="request.operacion.label" default="Operacion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: requestInstance, field: 'operacion', 'errors')}">
                                    <g:select disabled="true" name="operacion.id" from="${com.scortelemed.Operacion.list()}" optionKey="id" value="${requestInstance?.operacion?.id}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="company"><g:message code="request.company.label" default="Comap&ntilde;ia" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: requestInstance, field: 'company', 'errors')}">
                                    <g:select disabled="true" name="company.id" from="${com.scortelemed.Company.list()}" optionKey="id" value="${requestInstance?.company?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="request"><g:message code="request.request.label" default="Request" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: requestInstance, field: 'request', 'errors')}">
                                    <g:textField disabled="true" name="request" value="${requestInstance?.request}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="descartado"><g:message code="request.descartado.label" default="Descartar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: requestInstance, field: 'descartado', 'errors')}">
                                    <g:checkBox disabled="true" name="descartado" value="${requestInstance?.descartado}" ></g:checkBox>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
<%--                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>--%>
<%--                   	<span class="button"><input class="create" type="button" value="A&ntilde;adir request" onclick="document.location='${createLinkTo(dir:'/request/create')}'"/></span>--%>
<%--					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/request/list')}'"/></span> --%>
               </div>
            </g:form>
        </div>
    </body>
</html>
