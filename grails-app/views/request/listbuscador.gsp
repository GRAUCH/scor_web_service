<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Listando Request</title>
    </head>
    <body>

        <div class="body">
	    	<g:render template="buscador" />
            <h1>Listando Request</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${requestInstanceList}">
	            <div class="list">
	
	                <table>
	                    <thead>
	                        <tr>
	                            <g:sortableColumn action="listbuscador" params="['pag': 'on']" property="fecha_procesado" title="${message(code: 'request.fecha_procesado.label', default: 'Procesado')}" />
	                            <g:sortableColumn action="listbuscador" params="['pag': 'on']" property="fecha_creacion" title="${message(code: 'request.fecha_creacion.label', default: 'Creaci&oacute;n')}" />
	                            <g:sortableColumn action="listbuscador" params="['pag': 'on']" property="claveProceso" title="${message(code: 'request.claveProceso.label', default: 'Clave proceso')}" />
	                            <th><g:message code="request.operacion.label" default="Operacion" /></th>
	                            <g:sortableColumn action="listbuscador" params="['pag': 'on']" property="company" title="${message(code: 'request.company.label', default: 'Compa&ntilde;ia')}" />
	                        	<g:sortableColumn action="listbuscador" params="['pag': 'on']" property="descartado" title="${message(code: 'request.descartado.label', default: 'Descartado')}" />
	                        </tr>
	                    </thead>
	                    <tbody>
	                    <g:each in="${requestInstanceList}" status="i" var="requestInstance">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                            <td><g:formatDate date="${requestInstance.fecha_procesado}" /></td>
	                            <td><g:formatDate date="${requestInstance.fecha_creacion}" /></td>
	                            <g:if test="${requestInstance.errores == null || requestInstance.errores == ''}">
	                            	<td><g:link action="show" id="${requestInstance.id}">${requestInstance?.claveProceso.replace("#", " ")}</g:link></td>
	                            </g:if>
	                            <g:else>
	                            	<td><g:link style="color:red" action="show" id="${requestInstance.id}">${requestInstance?.claveProceso.replace("#", " ")}</g:link></td>
	                            </g:else>                            
	                            <td>${fieldValue(bean: requestInstance, field: "operacion")}</td>
	                            <td>${fieldValue(bean: requestInstance, field: "company")}</td>
	                            <td class="${requestInstance?.descartado? 'silist' : 'nolist'}"><span style="visibility: hidden">${requestInstance?.descartado}</span></td>
	                        </tr>
	                    </g:each>
	                    </tbody>
	                </table>
	
	
	            </div>
	            <div class="paginateButtons">
	                <g:paginate total="${requestInstanceTotal}" params="['pag': 'on']" />
	            </div>
            </g:if>
            <g:else>
            	<b>No existen datos para esa consulta</b>
            </g:else>
        </div>
        
    </body>
</html>

