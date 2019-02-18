        <div class="body">
         	<h3>Restricciones por Ip</h3>
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" controller="ipcontrol" action="create" id="${companyInstance.id}">A&ntilde;adir restricci&oacute;n por ip</g:link></span>
        	</div>
            <g:if test="${flash.messageIps}">
            <div class="message">${flash.messageIps}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>         	    
                       		<g:remoteSortableColumn property="ipAddress" title="${message(code: 'ipcontrol.ipAddress.label', default: 'Ip Address')}" action="updateRelacion" update="relacion" />
                        	<th colspan="2"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${ipcontrolInstanceList}" status="i" var="ipcontrolInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${fieldValue(bean: ipcontrolInstance, field: "ipAddress")}</td>
                            <td><span class="buttons"><g:link class="edit" controller="ipcontrol" action="edit" id="${ipcontrolInstance.id}">Editar</g:link></span></td>
		                    <td><span class="buttons"><g:remoteLink update="relacion" class="delete"  action="deleteRelacion" id="${ipcontrolInstance.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Borrar</g:remoteLink></span></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
	            <div class="paginateButtons">
					<g:remotePaginate update="relacion" action="updateRelacion" total="${ipcontrolInstanceTotal}" id="${companyInstance.id}" />Registros Totales: ${ipcontrolInstanceTotal} 
	            </div>
            </div>
       </div>