<%@ page import="com.scortelemed.Agente" %>



<div class="fieldcontain ${hasErrors(bean: agenteInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="agente.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${agenteInstance?.fecha}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: agenteInstance, field: 'cia', 'error')} required">
	<label for="cia">
		<g:message code="agente.cia.label" default="Cia" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cia" name="cia.id" from="${com.scortelemed.Company.list()}" optionKey="id" required="" value="${agenteInstance?.cia?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: agenteInstance, field: 'valor', 'error')} required">
	<label for="valor">
		<g:message code="agente.valor.label" default="Valor" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="valor" required="" value="${agenteInstance?.valor}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: agenteInstance, field: 'agente', 'error')} required">
	<label for="agente">
		<g:message code="agente.agente.label" default="Agente" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="agente" required="" value="${agenteInstance?.agente}"/>

</div>

