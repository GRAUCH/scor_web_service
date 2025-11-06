<html>
    <head>
        <title>SCOR Telemed - Panel de control de administraci&oacute;n de Webservices</title>
		<meta name="layout" content="main" />
    </head>
    <body>
    <sec:ifLoggedIn>
	    <div class="body">
	        <h1>Panel de administraci&oacute;n de Webservices</h1>
	        
	        <div id="ultimasRequest">
				<g:include action="ultimasRequest" />
			</div>


<%--			<g:ifAnyGranted role="ROLE_ADMIN">--%>
<%--	        <h2>Webservices</h2>--%>
<%--	        <div class="menuGrande"><a class="wservice" href="${createLinkTo(dir:'/webservice/create')}">Nuevo Webservice</a></div>--%>
<%--	        <div class="menuGrande"><a class="wservice" href="${createLinkTo(dir:'/webservice/list')}">Listado Webservices</a></div>--%>
<%--	        <div class="menuGrande"><a class="wservice" href="${createLinkTo(dir:'/operacion/create')}">Nueva Operaci&oacute;n</a></div>--%>
<%--	        <div class="menuGrande"><a class="wservice" href="${createLinkTo(dir:'/operacion/list')}">Listado Operaciones</a></div>--%>
<%--	        <div class="menuGrande"><a class="wservice" href="${createLinkTo(dir:'/datowebservice/create')}">Nuevo Configuraci&oacute;n</a></div>--%>
<%--	        <div class="menuGrande"><a class="wservice" href="${createLinkTo(dir:'/datowebservice/list')}">Listado Configuraci&oacute;n</a></div>--%>
<%--	        <div class="menuGrande"><a class="wservice" href="${createLinkTo(dir:'/generacionFichero/index')}">Generar fichero</a></div>--%>
<%--	        <div class="clear"></div>--%>
<%--	        --%>
<%--	        <h2>Compa&ntilde;ia</h2>--%>
<%--	        <div class="menuGrande"><a class="company" href="${createLinkTo(dir:'/company/create')}">Nuevo Compa&ntilde;ia</a></div>--%>
<%--	        <div class="menuGrande"><a class="user" href="${createLinkTo(dir:'/person/create')}">Nuevo Usuario</a></div>--%>
<%--	        <div class="menuGrande"><a class="roles" href="${createLinkTo(dir:'/authority/create')}">Nuevo Rol</a></div>--%>
<%--	        <div class="menuGrande"><a class="company" href="${createLinkTo(dir:'/company/list')}">Listado Compa&ntilde;ia</a></div>--%>
<%--	        <div class="menuGrande"><a class="user" href="${createLinkTo(dir:'/person/list')}">Listado Usuarios</a></div>--%>
<%--	        <div class="menuGrande"><a class="roles" href="${createLinkTo(dir:'/authority/list')}">Listado Roles</a></div>--%>
<%--	        <div class="clear"></div>--%>
<%----%>
<%--	        <h2>Avisos</h2>--%>
<%--	        <div class="menuGrande"><a class="alert" href="${createLinkTo(dir:'/aviso/create')}">Nuevo Aviso</a></div>--%>
<%--	        <div class="menuGrande"><a class="template" href="${createLinkTo(dir:'/plantilla/create')}">Nuevo Plantilla</a></div>--%>
<%--	        <div class="menuGrande"><a class="user" href="${createLinkTo(dir:'/destinatario/create')}">Nuevo Destinatario</a></div>--%>
<%--	        	        <div class="menuGrande"><a class="alert" href="${createLinkTo(dir:'/aviso/list')}">Listado Avisos</a></div>--%>
<%--	        <div class="menuGrande"><a class="template" href="${createLinkTo(dir:'/plantilla/list')}">Listado Plantillas</a></div>--%>
<%--	        <div class="menuGrande"><a class="user" href="${createLinkTo(dir:'/destinatario/list')}">Listado Destinatarios</a></div>--%>
<%--	        <div class="clear"></div>--%>
<%--	        --%>
<%--	        <h2>Configuraciones</h2>--%>
<%--	        <div class="menuGrande"><a class="conf" href="${createLinkTo(dir:'/conf/create')}">Nuevo Configuraciones</a></div>--%>
<%--	        <div class="menuGrande"><a class="conf" href="${createLinkTo(dir:'/conf/list')}">Listado Configuraciones</a></div>--%>
<%--	        <div class="clear"></div>--%>
<%--	        	        --%>
<%--	        <h2>Estadisticas</h2>--%>
<%--	    	<div class="menuGrande"><a class="stat" href="${createLinkTo(dir:'/estadistica/list')}">Listado Estadisticas</a></div>--%>
<%--	    	<div class="menuGrande"><a class="stat" href="${createLinkTo(dir:'/request/list')}">Listado Request</a></div>--%>
<%--			</g:ifAnyGranted>--%>

	    </div>
	</sec:ifLoggedIn>
	<sec:ifNotLoggedIn>
		<g:include controller="login" action="auth" />
	</sec:ifNotLoggedIn>
    </body>
</html>