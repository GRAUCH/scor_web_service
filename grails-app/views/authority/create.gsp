<head>
	<meta name="layout" content="main" />
	<title>Nuevo rol</title>
</head>

<body>

	<div class="body">

		<h1>Nuevo rol</h1>
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<g:hasErrors bean="${authority}">
		<div class="errors">
		<g:renderErrors bean="${authority}" as="list" />
		</div>
		</g:hasErrors>

		<g:form action="save">
		<div class="dialog">
		<table>
		<tbody>
			<tr class="prop">
				<td valign="top" class="name"><label for="authority">Nombre del rol:</label></td>
				<td valign="top" class="value ${hasErrors(bean:authority,field:'authority','errors')}">
					<input type="text" id="authority" name="authority" value="${authority?.authority?.encodeAsHTML()}"/>
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="description">Descripci&oacute;n:</label></td>
				<td valign="top" class="value ${hasErrors(bean:authority,field:'description','errors')}">
					<input type="text" id="description" name="description" value="${authority?.description?.encodeAsHTML()}"/>
				</td>
			</tr>
		</tbody>
		</table>
		</div>

		<div class="buttons">
			<span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
			<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/authority/list')}'"/></span>
		</div>
		</g:form>
	</div>
</body>
