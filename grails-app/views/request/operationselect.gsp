<div class="nombre">Operaci&oacute;n</div>
<g:if test="${operacionListado}">
	<g:select name="operacion" from="${operacionListado}" optionKey="clave" value="${session.operacion}" noSelection="['null':'']" />
</g:if>
<g:else>
	El webservice no tiene operaciones.
</g:else>