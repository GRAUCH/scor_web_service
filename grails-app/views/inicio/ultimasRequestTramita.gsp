<h3>&Uacute;ltimas Peticiones TramitacionReconocimientoMedicoRequest</h3>  
<div class="list">
                <table>
                    <thead>
                        <tr>
                           <th>FechaProcesado</th>
                           <th>Clave proceso</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${ultimasRequest}" status="i" var="requestInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        	<g:if test="${requestInstance.fecha_procesado}">
                            	<td><g:formatDate date="${requestInstance.fecha_procesado}" /></td>
                            </g:if>
                            <g:else>
                            	<td>Sin procesar</td>
                            </g:else>
                            <td><g:link controller="request" action="show" id="${requestInstance.id}">${fieldValue(bean: requestInstance, field: "claveProceso")}</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
</div>
<div class="paginateButtons">
	<g:remotePaginate update="ultimasRequestTramita" action="ultimasRequestTramita" total="${ultimasRequestTotal}"  />
	Registros Totales: ${ultimasRequestTotal} 
</div>