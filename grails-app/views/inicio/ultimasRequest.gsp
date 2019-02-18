<h3>&Uacute;ltimas Peticiones GestionReconocimientoMedicoRequest</h3>    
<div class="list">
                <table>
                    <thead>
                        <tr>
                        	<g:remoteSortableColumn property="fecha_creacion" title="Fecha creación" update="ultimasRequest" action="ultimasRequest"/>
                        	<g:remoteSortableColumn property="fecha_procesado" title="Fecha preocesado" update="ultimasRequest" action="ultimasRequest"/>
                        	<g:remoteSortableColumn property="operacion" title="Operación" update="ultimasRequest" action="ultimasRequest"/>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${ultimasRequest}" status="i" var="requestInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        	<td>
	                        	<g:link controller="request" action="show" id="${requestInstance.id}">
	                        		<g:formatDate date="${requestInstance.fecha_creacion}" />
	                        	</g:link>
                        	</td>
                        	<td>
                        		<g:link controller="request" action="show" id="${requestInstance.id}">
		                        	<g:if test="${requestInstance.fecha_procesado}">
		                            	<g:formatDate date="${requestInstance.fecha_procesado}" />
		                            </g:if>
		                            <g:else>
		                            	<b>Sin procesar</b>   	
		                            </g:else>   
                            	</g:link>	
                            </td>                      
                            <td><g:link controller="request" action="show" id="${requestInstance.id}">${requestInstance.operacion.descripcion}</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
</div>
<div class="paginateButtons">
	<g:remotePaginate update="ultimasRequest" action="ultimasRequest" total="${ultimasRequestTotal}"  />
	Registros Totales: ${ultimasRequestTotal} 
</div>