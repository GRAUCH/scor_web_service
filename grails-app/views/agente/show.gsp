
<%@ page import="com.scortelemed.Agente" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'agente.label', default: 'Agente')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-agente" class="content scaffold-show" role="main">
			<h1>Mostrar Instituto</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list agente">
			
				<g:if test="${agenteInstance?.fecha}">
				<li class="fieldcontain">
					<span id="fecha-label" class="property-label"><g:message code="agente.fecha.label" default="Fecha" /></span>
					
						<span class="property-value" aria-labelledby="fecha-label"><g:formatDate date="${agenteInstance?.fecha}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${agenteInstance?.cia}">
				<li class="fieldcontain">
					<span id="cia-label" class="property-label"><g:message code="agente.cia.label" default="Cia" /></span>
					
						<span class="property-value" aria-labelledby="cia-label"><g:link controller="company" action="show" id="${agenteInstance?.cia?.id}">${agenteInstance?.cia?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${agenteInstance?.valor}">
				<li class="fieldcontain">
					<span id="valor-label" class="property-label"><g:message code="agente.valor.label" default="Valor" /></span>
					
						<span class="property-value" aria-labelledby="valor-label"><g:fieldValue bean="${agenteInstance}" field="valor"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${agenteInstance?.agente}">
				<li class="fieldcontain">
					<span id="agente-label" class="property-label"><g:message code="agente.agente.label" default="Agente" /></span>
					
						<span class="property-value" aria-labelledby="agente-label"><g:fieldValue bean="${agenteInstance}" field="agente"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:agenteInstance]">
				<fieldset class="buttons">
					<g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/agente/index')}'"/></span>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
