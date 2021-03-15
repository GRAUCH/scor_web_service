
<%@ page import="com.scortelemed.Agente" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'agente.label', default: 'Agente')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<h1>Listado de Institutos</h1>
		<div class="opcionesMenu">
			<span class="menuButton"><g:link class="create" action="create">Nuevo Instituto</g:link></span>
		</div>
		<div id="list-agente" class="content scaffold-list" role="main">
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>

						<th><g:message code="agente.cia.label" default="Cia" /></th>
					
						<g:sortableColumn property="valor" title="${message(code: 'agente.valor.label', default: 'Valor')}" />
					
						<g:sortableColumn property="agente" title="${message(code: 'agente.agente.label', default: 'Agente')}" />

						<g:sortableColumn property="fecha" title="${message(code: 'agente.fecha.label', default: 'Fecha')}" />

					</tr>
				</thead>
				<tbody>
				<g:each in="${agenteInstanceList}" status="i" var="agenteInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link action="show" id="${agenteInstance.id}">${fieldValue(bean: agenteInstance, field: "cia")}</g:link></td>
					
						<td><g:link action="show" id="${agenteInstance.id}">${fieldValue(bean: agenteInstance, field: "valor")}</g:link></td>
					
						<td><g:link action="show" id="${agenteInstance.id}">${fieldValue(bean: agenteInstance, field: "agente")}</g:link></td>

						<td><g:link action="show" id="${agenteInstance.id}">${fieldValue(bean: agenteInstance, field: "fecha")}</g:link></td>

					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${agenteInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
