<head>
	<meta name="layout" content="main" />
	<title>Editando rol: ${authority}</title>
</head>

<body>

	<div class="body">
		<h1>Editando rol: ${authority}</h1>
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<g:hasErrors bean="${authority}">
		<div class="errors">
			<g:renderErrors bean="${authority}" as="list" />
		</div>
		</g:hasErrors>

		<g:form>
			<input type="hidden" name="id" value="${authority.id}" />
			<input type="hidden" name="version" value="${authority.version}" />
			<div class="dialog">
			<table>
			<tbody>
				<tr class="prop">
					<td valign="top" class="name"><label for="authority">Nombre del rol:</label></td>
					<td valign="top" class="value ${hasErrors(bean:authority,field:'authority','errors')}">
						<input type="text" id="authority" name="authority" value="${authority.authority?.encodeAsHTML()}"/>
					</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name"><label for="description">Descripci&oacute;n:</label></td>
					<td valign="top" class="value ${hasErrors(bean:authority,field:'description','errors')}">
						<input type="text" id="description" name="description" value="${authority.description?.encodeAsHTML()}"/>
					</td>
				</tr>

				<tr class="prop">
					<td valign="top" class="name"><label for="people">Usuarios:</label></td>
					<td valign="top" class="value ${hasErrors(bean:authority,field:'people','errors')}">
						<ul>
						<g:each var="p" in="${authority.people?}">
							<li>${p}</li>
						</g:each>
						</ul>
					</td>
				</tr>
			</tbody>
			</table>
			</div>

			<div class="buttons">
				<span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
			    <span class="button"><input class="create" type="button" value="A&ntilde;adir rol" onclick="document.location='${createLinkTo(dir:'/authority/create')}'"/></span>
				<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/authority/list')}'"/></span>
                <span class="button"><input class="cancel" type="button" value="Cancelar" onclick="document.location='${createLinkTo(dir:'/authority/show/' +  authority?.id)}'"/></span>                			
			</div>

		</g:form>
	</div>
</body>
