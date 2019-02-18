	<div class="body">
            <h1>Estadisticas</h1>

            <div class="list">
                <table>
                    <thead>
                        <tr>
							<g:remoteSortableColumn property="clave" title="${message(code: 'estadistica.clave.label', default: 'Clave')}" action="updateRelacion" update="relacion"  />
         					<g:remoteSortableColumn property="value" title="${message(code: 'estadistica.value.label', default: 'Value')}" action="updateRelacion" update="relacion"  />
         					<g:remoteSortableColumn property="fecha_alta" title="${message(code: 'estadistica.fecha_alta.label', default: 'Fecha')}" action="updateRelacion" update="relacion"  />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${estadisticasInstanceList}" status="i" var="estadisticaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link controller="estadistica" action="show" id="${estadisticaInstance.id}">${fieldValue(bean: estadisticaInstance, field: "clave")}</g:link></td>
                            <td>${fieldValue(bean: estadisticaInstance, field: "value")}</td>
                            <td>${fieldValue(bean: estadisticaInstance, field: "fecha_alta")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:remotePaginate update="relacion" action="updateRelacion" total="${total}" id="${requestInstance.id}" />Registros Totales: ${total} 
            </div>
        </div>
    </body>