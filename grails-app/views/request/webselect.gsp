<div class="nombre">Webservice</div>
<g:if test="${webserviceListado}">
	<g:select onChange="calculaOperacion('${g.createLink(action:'operationselect')}')"  name="webservice" from="${webserviceListado}" optionKey="clave" value="${session.webservice}" noSelection="['null':'']" />
</g:if>
<g:else>
	La compa&ntilde;ia no tiene webservices.
</g:else>