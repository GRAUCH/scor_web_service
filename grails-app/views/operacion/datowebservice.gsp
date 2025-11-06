        <div class="body">
            <h1>Datos operaci&oacute;n</h1>
            <div>
            	Los datos de operaciones para webservices no son obligatorios para el funcionamiento de la plataforma. Su funci&oacute;n reside en facilitar la creaci&oacute;n de plantillas personalizadas.<br /> Si se necesitan, los datos que aqui se expongan estar&aacute;n disponibles en el m&oacute;dulo de creaci&oacute;n de plantillas accediendo a trav&eacute;s del modulo Operaciones pulsando el boton: Nueva Plantilla para esta operaci&oacute;n.
            </div>
            <br />
            <div class="opcionesMenu">
        		<span class="menuButton"><g:link class="create" controller="datowebservice" action="create" id="${operacionInstance.id}">Nuevo dato de operaci&oacute;n webservice</g:link></span>
        	</div>

            <div class="list">
                <table>
                    <thead>
                        <tr>
                        	<g:remoteSortableColumn property="nombre" title="${message(code: 'datowebservice.nombre.label', default: 'Nombre')}" action="updateRelacion" update="relacion" />
                        	<g:remoteSortableColumn property="tipo" title="${message(code: 'datowebservice.tipo.label', default: 'Tipo')}" action="updateRelacion" update="relacion" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${datowebserviceInstanceList}" status="i" var="datowebserviceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link controller="datowebservice" action="show" id="${datowebserviceInstance.id}">${fieldValue(bean: datowebserviceInstance, field: "nombre")}</g:link></td>
                            <td>${fieldValue(bean: datowebserviceInstance, field: "tipo")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:remotePaginate update="relacion" action="updateRelacion" total="${total}" id="${operacionInstance.id}" />Registros Totales: ${total} 
            </div>
        </div>