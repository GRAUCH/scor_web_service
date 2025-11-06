        <div class="body">
            <h1>Buscador de Estadisticas</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form action="listbuscador">
	            <div class="dialog">
	                <table>
	                    <tbody>
	                                            
	                        <tr class="propVertical">
	                            <td class="linea">
	                            	<div class="nombre">Request</div>
	                            	<input type="text" id="claveProcesoRequest" name="claveProcesoRequest" value="${params.claveProcesoRequest}"/>
	                            </td> 
	                            <td class="linea">
									<div class="nombre">Operaci&oacute;n</div>
									<input type="text" id="operacion" name="operacion" value="${params.operacion}"/></td> 
	                        </tr>
	                    
	                        <tr class="propVertical">
	                            <td class="linea">
	                            	<div class="nombre">Clave</div>
	                            	<input type="text" id="clave" name="clave" value="${params.clave}"/>
	                        	</td>
	                            <td class="linea">
	                            	<div class="nombre">Valor</div>
	                            	<input type="text" id="valor" name="valor" value="${params.valor}"/>
	                            </td>
	                        </tr>
	                        
	                        <tr class="propVertical">
	                            <td class="linea">
	                            	<div class="nombre">Fecha de Generaci&oacute;n Inicio</div>
	                            	<g:datePicker name="fecha_procesado" default="none" noSelection="['':'-Elije-']" precision="day" value="${params.fecha_procesado}" />
	                            </td> 

	                            <td class="linea">
	                            	<div class="nombre">Fecha de Generaci&oacute;n Fin</div>
	                            	<g:datePicker name="fecha_procesadoFin" precision="day" value="${params.fecha_procesadoFin}" />
	                            </td> 
	                        </tr>
	                        
	                        <tr class="propVertical">
	                            <td class="name">Todas las condiciones</td>
	                            	<td class="value">
		                            	<g:if test="${params.condiciones == 'on'}">
											<g:checkBox name="condiciones" value="${true}" />
			                        	</g:if>
			                        	<g:else>
			                        		<g:checkBox name="condiciones" value="${false}" />
			                        	</g:else>
	                            	</td>
	                        </tr>
	                    
	                    </tbody>
	                </table>
	            </div>
				<div class="buttons">
					<span class="button"><input name="input" class="create" type="submit" value="Buscar"/></span
					<span class="button"><input name="input" class="list" type="submit" value="Ver Todo"/></span>
				</div>
			</g:form>
        </div>
