		<div class="body">
            <h1>Operaciones</h1>
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" controller="operacion" action="create" id="${webserviceInstance.id}">Nueva operaci&oacute;n</g:link></span>
        	</div>
        
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        	<g:remoteSortableColumn property="clave" title="${message(code: 'operacion.clave.label', default: 'Clave')}" action="updateRelacion" update="relacion" />
                         	<g:remoteSortableColumn property="descripcion" title="${message(code: 'operacion.descripcion.label', default: 'Descripcion')}" action="updateRelacion" update="relacion" />
                         	<g:remoteSortableColumn property="activo" title="${message(code: 'operacion.activo.label', default: 'Activo')}" action="updateRelacion" update="relacion" />   
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${operacionInstanceList}" status="i" var="operacionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        	<td><g:link controller="operacion" action="show" id="${operacionInstance.id}">${fieldValue(bean: operacionInstance, field: "clave")}</g:link></td>
                            <td>${fieldValue(bean: operacionInstance, field: "descripcion")}</td>
                            <td class="${operacionInstance.activo? 'silist' : 'nolist'}"><span style="visibility: hidden"><g:formatBoolean boolean="${operacionInstance.activo}" /></span></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                 <g:remotePaginate update="relacion" action="updateRelacion" total="${total}" id="${webserviceInstance.id}" />Registros Totales: ${total} 
            </div>
        </div>