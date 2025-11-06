<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>


<div class="body">
	<h1>Buscador de Request</h1>
	<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
	</g:if>
	<g:form action="listbuscador">
		<div class="dialog">
			<table>
				<tbody>
					<tr class="propVertical">
						<td class="linea">
							<div class="nombre">Compa&ntilde;ia</div>
							<g:select onChange="getOperaciones('${g.createLink(action:'operationget')}')" name="company" from="${com.scortelemed.Company.list()}" optionKey="nombre" value="${params.company}" noSelection="['null':'']" />
						</td>
						<td class="linea" id="operacionId">
							<g:include action="operationget" id="${params.company}" />
						</td>
					</tr>

					<tr class="propVertical">
						<td class="linea">
							<div class="nombre">Request</div>
							<input type="text" id="request" name="request" value="${params.request}"/>
						</td>
						<td class="linea">
							<div class="nombre">Clave proceso</div>
							<input type="text" id="claveProceso" name="claveProceso" value="${params.claveProceso}"/>
						</td>
					</tr>

					<tr class="propVertical">
						<td class="linea">
							<div class="nombre">Fecha de Procesado Inicio</div>
							<g:datePicker name="fecha_procesado" default="none" noSelection="['':'-Elije-']" precision="day" value="${params.fecha_procesado}" />
						</td>
						<td class="linea">
							<div class="nombre">Fecha de Procesado Fin</div>
							<g:datePicker name="fecha_procesadoFin" precision="day" value="${params.fecha_procesadoFin}" />
						</td>
					</tr>

					<tr class="propVertical">
						<td class="linea">
							<div class="nombre">Fecha de Creaci&oacute;n Inicio</div>
							<g:datePicker name="fecha_creacion" default="none" noSelection="['':'-Elije-']" precision="day" value="${session.fecha_creacion}" />
						</td>
						<td class="linea">
							<div class="nombre">Fecha de Creaci&oacute;n Fin</div>
							<g:datePicker name="fecha_creacionFin" precision="day" value="${session.fecha_creacionFin}" />
						</td>
					</tr>


					<tr class="propVertical">
						<td class="linea">
							<div class="nombre">Solo con errores</div>
							<g:checkBox name="errores" value="${params.errores}" />
						</td>
						<td class="linea">
							<div class="nombre">Solo descartados</div>
							<g:checkBox name="descartado" value="${params.descartado}" />
						</td>
					</tr>

					<tr class="propVertical">
						<td class="linea">
							<div class="nombre" style="font-size: 12px"><b>Todas las condiciones</b></div>
							<g:checkBox name="condiciones" value="${params.condiciones == 'on'}" />
						</td>
						<td class="linea"><div class="nombre" style="font-size: 12px"></div></td>
					</tr>

				</tbody>
			</table>
		</div>
		<div class="buttons">
			<span class="button"><input name="input" class="create" type="submit" value="Buscar"/></span>
			<span class="button"><input name="input" class="list" type="submit" value="Ver Todo"/></span>
		</div>
	</g:form>
</div>
<script type="text/javascript">
	function calculaWebs(url) {
		new Ajax.Updater('webserviceId', url + "/" + document.getElementById('company').value);
		submit()
  	}
	function calculaOperacion(url) {
		new Ajax.Updater('operacionId', url + "/" + document.getElementById('webselect').value);
		submit()
  	}
    function getOperaciones(url) {
        new Ajax.Updater('operacionId', url + "/" + document.getElementById('company').value);
        submit()
    }
</script>