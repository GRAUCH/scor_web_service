<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse"> <span class="icon-bar"></span> <span
				class="icon-bar"></span> <span class="icon-bar"></span>
			</a> <a class="brand logo"><asset:image src="scor.png" class="logo" /></a>

			<div class="nav-collapse collapse">

				<ul class="nav pull-right">

					<li class="dropdown"><a href="#" role="button"class="dropdown-toggle" data-toggle="dropdown"> 
						 
						 	<g:if test="${ou.equals('AD')}">
								<asset:image src="usuario.png" style="width:15px;" />
							</g:if> 
							<g:if test="${ou.equals('ES')}">
								<asset:image src="usuario.png" style="width:15px;" />
							</g:if> 
							<g:if test="${ou.equals('FR')}">
								<asset:image src="usuario.png" style="width:15px;" />
							</g:if> 
							<g:if test="${ou.equals('PT')}">
								<asset:image src="usuario.png" style="width:15px;" />
							</g:if> 
							${sec.username()}
					<li><g:link controller="logOut" action="index"><asset:image src="logout.png" style="width:15px;" /> Logout</g:link></li>

				</ul>
				<ul class="nav">
					<sec:ifLoggedIn>
						<sec:ifAnyGranted roles="ROLE_ADMIN">
							<li class="active"><g:link controller="inicio" action="index">Administracion</a></g:link></li>
						</sec:ifAnyGranted>
					</sec:ifLoggedIn>
					<sec:ifLoggedIn>
						<sec:ifAnyGranted roles="ROLE_USER">
							<li class="active"><g:link controller="inicio" action="index">Inicio</a></g:link></li>
						</sec:ifAnyGranted>
					</sec:ifLoggedIn>

					<li><g:remoteLink controller="dashboard" update="contenido"
							action="enviado">Enviados</g:remoteLink></li>
					<li><g:remoteLink controller="dashboard" update="contenido"
							action="recibido">Recibidos</g:remoteLink></li>
				</ul>
			</div>
		</div>
	</div>
</div>