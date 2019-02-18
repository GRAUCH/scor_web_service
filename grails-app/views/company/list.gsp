

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Listando compa&ntilde;ia</title>
</head>
<body>

	<div class="body">
		<h1>Listando compa&ntilde;ia</h1>
		<div class="opcionesMenu">
			<span class="menuButton"><g:link class="create"
					action="create">Nueva compa&ntilde;ia</g:link></span>
		</div>

		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<div class="list">
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="nombre"
							title="${message(code: 'company.nombre.label', default: 'Nombre')}" />
						<g:sortableColumn property="codigoSt"
							title="${message(code: 'company.codigoSt.label', default: 'Codigo St')}" />
						<g:sortableColumn property="ipControl" title="Control por Ip" />
						<g:sortableColumn property="generationAutomatic"
							title="Generación automática" />
						<g:sortableColumn property="ou" title="Unidad Organizativa" />
					</tr>
				</thead>
				<tbody>
					<g:each in="${companyInstanceList}" status="i"
						var="companyInstance">

						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<td><g:link class="linkBloque" action="show"
									id="${companyInstance.id}">
									${fieldValue(bean: companyInstance, field: "nombre")}
								</g:link></td>
							<td><g:link class="linkBloque" action="show"
									id="${companyInstance.id}">
									${fieldValue(bean: companyInstance, field: "codigoSt")}
								</g:link></td>
							<td class="${companyInstance.ipControl? 'silist' : 'nolist'}"><g:link
									class="linkBloque" action="show" id="${companyInstance.id}">
									<span style="visibility: hidden">
										${fieldValue(bean:companyInstance, field:'ipControl')}
									</span>
								</g:link></td>
							<td
								class="${companyInstance.generationAutomatic? 'silist' : 'nolist'}"><g:link
									class="linkBloque" action="show" id="${companyInstance.id}">
									<span style="visibility: hidden">
										${fieldValue(bean:companyInstance, field:'generationAutomatic')}
									</span>
								</g:link></td>
							<td><g:link class="linkBloque" action="show"
									id="${companyInstance.id}">
									${fieldValue(bean: companyInstance, field: "ou")}
								</g:link></td>
						</tr>

					</g:each>
				</tbody>
			</table>
		</div>
		<div class="paginateButtons">
			<g:paginate total="${companyInstanceTotal}" />
			Registros Totales:
			${companyInstanceTotal}
		</div>
	</div>
</body>
</html>
