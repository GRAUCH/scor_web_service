        <div class="body">
            <h1>Datos webservice</h1>
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" controller="datowebservice" action="create" id="${webserviceInstance.id}">Nuevo dato webservice</g:link></span>
        	</div>

            <div class="list">
                <table>
                    <thead>
                        <tr>
                        	<g:remoteSortableColumn property="nombre" title="${message(code: 'datowebservice.nombre.label', default: 'Nombre')}" action="updateRelacion" update="relacion" />
                        	<g:remoteSortableColumn property="tipo" title="${message(code: 'datowebservice.tipo.label', default: 'Tipo')}" action="updateRelacion" update="relacion" />
                            <th><g:message code="datowebservice.webservice.label" default="Webservice" /></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${datowebserviceInstanceList}" status="i" var="datowebserviceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link controller="datowebservice" action="show" id="${datowebserviceInstance.id}">${fieldValue(bean: datowebserviceInstance, field: "nombre")}</g:link></td>
                            <td>${fieldValue(bean: datowebserviceInstance, field: "tipo")}</td>
                            <td>${fieldValue(bean: datowebserviceInstance, field: "webservice")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:remotePaginate update="relacion" action="updateRelacion" total="${total}" id="${webserviceInstance.id}" />Registros Totales: ${total} 
            </div>
        </div>