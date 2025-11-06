<head>
	<meta name="layout" content="main" />
	<title>Mostrando usuario: ${person}</title>
</head>

<body>
	<div class="body">
		<h1>Mostrando usuario: ${person}</h1>
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<div class="dialog">
			<table>
			<tbody>

				<tr class="prop">
					<td valign="top" class="name">Login nombre:</td>
					<td valign="top" class="value">${person.username?.encodeAsHTML()}</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Nombre completo:</td>
					<td valign="top" class="value">${person.userRealName?.encodeAsHTML()}</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Activo:</td>
					<td valign="top" class="${person.enabled? 'si' : 'no'}"><span style="visibility: hidden">${fieldValue(bean:person, field:'enabled')}</span></td> 
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Descripci&oacute;n:</td>
					<td valign="top" class="value">${person.description?.encodeAsHTML()}</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Email:</td>
					<td valign="top" class="value">${person.email?.encodeAsHTML()}</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Mostrar Email:</td>
					<td valign="top" class="${person.emailShow? 'si' : 'no'}"><span style="visibility: hidden">${fieldValue(bean:person, field:'emailShow')}</span></td>
				</tr>
				
				<tr class="prop">
					<td valign="top" class="name">Compa&ntilde;ia:</td>
					<td valign="top" class="value">${person.company}</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Asignaci&oacute;n de roles:</td>
					<td valign="top" class="value">
						<ul>
						<g:each in="${roleNames}" var='name'>
							<li>${name}</li>
						</g:each>
						</ul>
					</td>
				</tr>
				
				<tr class="prop">
					<td valign="top" class="name">Webservices:</td>
					<td valign="top" class="value">
						<ul>
						<g:each in="${webserviceNames}" var='name'>
							<li>${name}</li>
						</g:each>
						</ul>
					</td>
				</tr>

			</tbody>
			</table>
		</div>

		<div class="buttons">
			<g:form>
				<input type="hidden" name="id" value="${person.id}" />
  				<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
			    <span class="button"><input class="create" type="button" value="A&ntilde;adir usuario" onclick="document.location='${createLinkTo(dir:'/person/create')}'"/></span>
				<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/person/list')}'"/></span>
			</g:form>
		</div>

	</div>
</body>
