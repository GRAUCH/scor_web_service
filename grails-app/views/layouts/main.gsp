<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Inicio</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
		 <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}" type="text/css">
		 <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-responsive.css')}" type="text/css">
		 <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.css')}" type="text/css">
		 <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		 <link rel="stylesheet" href="${resource(dir: 'css', file: 'menu.css')}" type="text/css">
		 <script src="${resource(dir: 'js', file: 'jquery-1.8.js')}"></script>
		 <script src="${resource(dir: 'js', file: 'jquery-1.8-ui.js')}"></script>
		 <script src="${resource(dir: 'js', file: 'bootstrap.js')}"></script>
		<g:layoutHead />	
	</head>
	<body>
    	<div id="container">
			<div class="nMenu">
				<div class="usuarioMenu">
					<span>
				        <sec:ifLoggedIn>
				        <img src="${createLinkTo(dir:'images',file:'user.gif')}" alt="Usuario" />&nbsp;&nbsp; ${sec.username()} &middot; 
				        <img src="${createLinkTo(dir:'images',file:'door_in.gif')}" alt="Logout" />&nbsp;&nbsp;<g:link controller="logOut">Logout</g:link>
			        	</sec:ifLoggedIn> 				
			        	<sec:ifNotLoggedIn>
			        		Acceso Restringido / <g:link controller="login">Login</g:link>
			        	</sec:ifNotLoggedIn>
					</span>
				</div>
			</div>  
			<div id="logo"></div>
			
			<div class="clear"></div> 
			
			<sec:ifLoggedIn><sec:ifAnyGranted roles="ROLE_ADMIN"> 
				<g:render template="/shared/menu" />
			</sec:ifAnyGranted></sec:ifLoggedIn>
	        <g:layoutBody />
	        
				<div class="pie">
				  	<div class="maCopy">&copy; 2010 &middot; SCOR Telemed </div>
				  	<div class="autoPubli">
				  		Desarrollado por <a href="http://www.helloworldsolutions.com" target="_blank"><b>hello<span class="azul_hello">world</span></b>solutions</a>
				  	</div>
				</div>
		</div>
	</body>
</html>
