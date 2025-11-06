<html>
    <head>
        <title>SCOR Telemed - Panel de control de administraci&oacute;n de Webservices</title>
		<meta name="layout" content="main" />
    </head>
    <body>
	    <div class="body">
	        <h1>Generaci&oacute;n de fichero de carga</h1>
	        <g:if test="${conseguido}">
	        	<div class="correcto">
	        		<h3>Fichero generado correctamente.</h3>  
				</div>
			</g:if>
			<g:else>
				<div class="nocorrecto">
	        		<h3>Fichero no generado correctamente.</h3>  
				</div>
			</g:else>
			<div style="overflow:scroll; border: 1px solid; padding-left: 15px; height: 300px">
				<pre>${datos}</pre>
			</div>
			
			<div>Tamaño total: ${datos.size()} chars en ${datos.size().intdiv(621)} lineas.</div>
			<div>Tiempo de generaci&oacute;n total: ${tiempo/1000} segundos.</div>
			
			<br />
			<g:if test="${errores}" >
				<h3 style="background: #ff6;">Errores de validaci&oacute;n</h3>
				<div>Se han detectado los siguiente errores.</div><br />
				<div>${errores}</div>
			</g:if>
			<g:else>
				<h3 style="background: #ff6;">No se han encontrado errores validaci&oacute;n</h3>
			</g:else>
		</div>
    </body>
</html>