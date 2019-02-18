<script type="text/javascript">
function inse(valor) {
	if(valor == "recorrer") {
		CKEDITOR.instances.body.insertHtml(valores);
	}
	else {
		CKEDITOR.instances.body.insertHtml(valor);
	}

}
</script>

<table>
	<tr>
		<td colspan="3" >Ayudantes para generar plantillas</td>
	</tr>
	<tr>
		<td class="ayudanteCaja" >
			<a class="ayudanteLink" onClick="inse('&#36;{proceso}');">Proceso (String)</a>
			<modalbox:createLink controller="plantilla" action="ayuda" title="Ayuda para tipo: Cadena" width="500" params="['ayuda':'cadena']"><span class="ayudanteAyuda">ayuda</span></modalbox:createLink>
		</td>
		<td class="ayudanteCaja" >
			<a class="ayudanteLink" onClick="inse('&#36;{fecha}');" >Fecha actual (Date)</a>
			<modalbox:createLink controller="plantilla" action="ayuda" title="Ayuda para tipo: Date" width="500" params="['ayuda':'fecha']"><span class="ayudanteAyuda">ayuda</span></modalbox:createLink>
		</td>
		<td class="ayudanteCaja">
			<a class="ayudanteLink" onClick="inse('&#36;{operacion}');">Operacion (String)</a>
			<modalbox:createLink controller="plantilla" action="ayuda" title="Ayuda para tipo: Cadena" width="500" params="['ayuda':'cadena']"><span class="ayudanteAyuda">ayuda</span></modalbox:createLink>
		</td>
	</tr>
	<tr>
		<td class="ayudanteCaja"">
			<a class="ayudanteLink" onClick="inse('&#36;{clave_operacion}');">Clave operacion (String)</a>
			<modalbox:createLink controller="plantilla" action="ayuda" title="Ayuda para tipo: Cadena" width="500" params="['ayuda':'cadena']"><span class="ayudanteAyuda">ayuda</span></modalbox:createLink>
		</td>
		<td class="ayudanteCaja">
			<a class="ayudanteLink" onClick="inse('&#36;{destinatarios}');">Destinatarios (PersistentSet)</a>
			<modalbox:createLink controller="plantilla" action="ayuda" title="Ayuda para tipo: PersistentSet" width="500" params="['ayuda':'destinatarios']"><span class="ayudanteAyuda">ayuda</span></modalbox:createLink>
		</td>
	</tr>

	${variables}
	
</table>




