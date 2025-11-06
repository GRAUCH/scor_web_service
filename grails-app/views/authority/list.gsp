<head>
	<meta name="layout" content="main" />
	<title>Listado de roles</title>
</head>

<body>


	<div class="body">
		<h1>Listado de roles</h1>
		<div class="opcionesMenu">
        	<span class="menuButton"><g:link class="create" action="create">Nuevo rol</g:link></span>
        </div>
		
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<div class="list">
			<table>
			<thead>
				<tr>
					<g:sortableColumn property="authority" title="Nombre del rol" />
					<g:sortableColumn property="description" title="Descripci&oacute;n" />
				</tr>
			</thead>
			<tbody>
			<g:each in="${authorityList}" status="i" var="authority">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
					<td><g:link class="linkBloque" action="show" id="${authority.id}">${authority.authority?.encodeAsHTML()}</g:link></td>
					<td><g:link class="linkBloque" action="show" id="${authority.id}">${authority.description?.encodeAsHTML()}</g:link></td>
				</tr>
			</g:each>
			</tbody>
			</table>
		</div>

		<div class="paginateButtons">
			<g:paginate total="${com.scortelemed.Authority.count()}" />Registros Totales: ${com.scortelemed.Authority.count()}
		</div>
	</div>
</body>
