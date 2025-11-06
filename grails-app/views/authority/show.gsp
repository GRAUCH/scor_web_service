<head>
	<meta name="layout" content="main" />
	<title>Mostrando rol: ${authority}</title>
</head>

<body>

	<div class="body">
		<h1>Mostrando rol: ${authority}</h1>
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<div class="dialog">
			<table>
			<tbody>

				<tr class="prop">
					<td valign="top" class="name">Nombre del rol:</td>
					<td valign="top" class="value">${authority.authority}</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Descripci&oacute;n:</td>
					<td valign="top" class="value">${authority.description}</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name">Usuarios:</td>
					<td valign="top" class="value">${authority.people}</td>
				</tr>

			</tbody>
			</table>
		</div>

		<div class="buttons">
			<g:form>
				<input type="hidden" name="id" value="${authority?.id}" />
  				<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
			    <span class="button"><input class="create" type="button" value="A&ntilde;adir rol" onclick="document.location='${createLinkTo(dir:'/authority/create')}'"/></span>
				<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/authority/list')}'"/></span>
			</g:form>
		</div>

	</div>

</body>
