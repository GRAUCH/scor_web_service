<div>
		<ul class="nav nav-pills">

			<li><g:link controller="inicio"><img src="${createLinkTo(dir:'images',file:'home_icon.png')}" />&nbsp;Inicio</g:link></li>

			<li class="dropdown"><a class="dropdown-toggle"	data-toggle="dropdown" href="#">
				<img src="${createLinkTo(dir:'images',file:'webservices.png')}" />&nbsp;Webservices<b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><g:link controller="webservice">Webservices</g:link></li>
					<li><g:link controller="operacion">Operaciones</g:link></li>
					<li><g:link controller="datowebservice">Datos operaciones</g:link></li>
					<li><g:link controller="generacionFichero">Generar fichero</g:link></li>
				</ul>
			</li>
			
			<li class="dropdown"><a class="dropdown-toggle"	data-toggle="dropdown" href="#">
				<img src="${createLinkTo(dir:'images',file:'group.png')}" />&nbsp;Compa&ntilde;ias<b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><g:link controller="company">Compa&ntilde;ias</g:link></li>
					<li><g:link controller="person">Usuarios</g:link></li>
					<li><g:link controller="authority">Roles</g:link></li>
					<li><g:link controller="agente">Institutos</g:link></li>
				</ul>
			</li>
			
			<li class="dropdown"><a class="dropdown-toggle"	data-toggle="dropdown" href="#">
				<img src="${createLinkTo(dir:'images',file:'alerticon.png')}" />&nbsp;Gestion de Avisos<b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><g:link controller="aviso">Avisos</g:link></li>
					<li><g:link controller="plantilla">Plantillas</g:link></li>
					<li><g:link controller="destinatario">Destinatarios</g:link></li>
				</ul>
			</li>
			
			<li><a class="conf" href="${createLinkTo(dir:'/conf')}"><img src="${createLinkTo(dir:'images',file:'wrench.png')}" />&nbsp;Configuraciones</a></li>
			
			<li><a class="stat" href="${createLinkTo(dir:'/request')}"><img src="${createLinkTo(dir:'images',file:'statsicon.png')}" />&nbsp;Estadisticas</a></li>
			
			<li><g:link controller="dashboard" action="index"><img src="${createLinkTo(dir:'images',file:'helpicon.png')}" />&nbsp;Dashboard</g:link></li>
			
		</ul>
</div>