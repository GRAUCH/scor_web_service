<head>
	<meta name="layout" content="main" />
	<title>Listando usuarios</title>
</head>

<body>
	<div class="body">
		<h1>Listando usuarios</h1>
		<div class="opcionesMenu">
        	<span class="menuButton"><g:link class="create" action="create">Nuevo usuario</g:link></span>
        </div>
		
		
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<div class="list">
			<table>
			<thead>
				<tr>
					<g:sortableColumn property="username" title="Login nombre" />
					<g:sortableColumn property="userRealName" title="Nombre completo" />
					<g:sortableColumn property="enabled" title="Activo" />
					<g:sortableColumn property="description" title="Descripci&oacute;n" />
					<g:sortableColumn property="company" title="Compa&ntilde;ia" />
				</tr>
			</thead>
			<tbody>
			<g:each in="${personList}" status="i" var="person">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
					<td><g:link class="linkBloque" action="show" id="${person.id}">${person.username?.encodeAsHTML()}</g:link></td>
					<td><g:link class="linkBloque" action="show" id="${person.id}">${person.userRealName?.encodeAsHTML()}</g:link></td>
					<td class="${person.enabled? 'silist' : 'nolist'}"><g:link class="linkBloque" action="show" id="${person.id}"><span style="visibility: hidden">${person.enabled?.encodeAsHTML()}</span></g:link></td>
					<td><g:link class="linkBloque" action="show" id="${person.id}">${person.description?.encodeAsHTML()}</g:link></td>
					<td><g:link class="linkBloque" action="show" id="${person.id}">${person.company?.encodeAsHTML()}</g:link></td>
				</tr>
			</g:each>
			</tbody>
			</table>
		</div>

		<div class="paginateButtons">
			<g:paginate total="${com.scortelemed.Person.count()}" />Registros Totales: ${com.scortelemed.Person.count()}
		</div>

	</div>
</body>
