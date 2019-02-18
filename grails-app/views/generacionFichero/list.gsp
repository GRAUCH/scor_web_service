<html>
    <head>
        <title>SCOR Telemed - Panel de control de administraci&oacute;n de Webservices</title>
		<meta name="layout" content="main" />
    </head>
    <body>
	    <div class="body">
	        <h1>Generaci&oacute;n de fichero de carga</h1>
	        		
			<div>
				Elija una compa&ntilde;ia
			</div>
			
			<g:form>
				<g:select name="company" from="${com.scortelemed.Company.list()}" optionKey="id" value="${company}" noSelection="['null': '-- ninguna --']"  />
				<g:actionSubmit class="save" action="list" value="Aceptar" />      
			</g:form>
	        
	        <div>
				Se generar&aacute; un fichero con las peticiones (request) que no hayan sido procesadas previamente y que no est&eacute;n descartadas. Son las siguientes:</div>
			<br />
			            
			<g:if test="${geneRequest}">
			<div class="list">
                <table>
                    <thead>
                        <tr>
                           <th>Clave proceso</th>
                           <th>Descartado</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${geneRequest}" status="i" var="requestInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link controller="request" action="show" id="${requestInstance.id}">${requestInstance?.claveProceso}</g:link></td>
                        	<td class="${requestInstance.descartado? 'silist' : 'nolist'}"><span style="visibility: hidden">${requestInstance?.descartado}</span></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
			</div>
			<div class="paginateButtons">
                <g:paginate total="${geneRequestTotal}" params="[company: company]"/> Registros totales: ${geneRequestTotal}
            </div>
				<div class="buttons">
					<br />
	                <span class="button"><g:link class="edit" action="generar" params="[company: company]" >Generar fichero ahora</g:link></span>
	            	<br /><br />
	            </div>
	        </g:if>
	        <g:else><h3>No existen request pendientes.</h3></g:else>
	         
		</div>
    </body>
</html>