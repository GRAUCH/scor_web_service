<%@ page import="com.scortelemed.Test" %>



<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="test.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${testInstance?.nombre}"/>

</div>

