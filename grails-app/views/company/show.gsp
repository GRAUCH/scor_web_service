

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:javascript library="prototype" />
<title>Mostrando compa&ntilde;ia: ${companyInstance}</title>
</head>
<body>

	<div class="body">
		<h1>
			Mostrando compa&ntilde;ia:
			${companyInstance}
		</h1>

		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<div class="dialog">
			<table>
				<tbody>
					<!--
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "id")}</td>
                    -->
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="company.nombre.label" default="Nombre" /></td>

						<td valign="top" class="value">
							${fieldValue(bean: companyInstance, field: "nombre")}
						</td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="company.codigoSt.label" default="Codigo St" /></td>

						<td valign="top" class="value">
							${fieldValue(bean: companyInstance, field: "codigoSt")}
						</td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name">Filtro por IP activo:</td>

						<td valign="top" class="${companyInstance.ipControl? 'si' : 'no'}">
							${fieldValue(bean:companyInstance, field:'ipControl')}
						</td>

					</tr>
					<tr class="prop">
						<td valign="top" class="name">Generación automática:</td>

						<td valign="top"
							class="${companyInstance.generationAutomatic? 'si' : 'no'}">
							${fieldValue(bean:companyInstance, field:'generationAutomatic')}
						</td>

					</tr>
					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="company.destinatarios.label" default="Destinatarios" /></td>

						<td valign="top" style="text-align: left;" class="value">
							<ul>
								<g:each in="${companyInstance.destinatarios}" var="d">
									<div class="botonLupa">
										<li><g:link controller="destinatario" action="show"
												id="${d.id}">
												${d?.encodeAsHTML()}
											</g:link></li>
									</div>
								</g:each>
							</ul>
						</td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="company.nombre.label" default="Usuario CRM" /></td>
						<td valign="top" class="value">
							${fieldValue(bean: companyInstance, field: "userCrm")}
						</td>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="company.nombre.label" default="Host" /></td>
						<td valign="top" class="value">
							${fieldValue(bean: companyInstance, field: "host")}
						</td>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="company.nombre.label" default="Domain" /></td>
						<td valign="top" class="value">
							${fieldValue(bean: companyInstance, field: "domain")}
						</td>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="company.nombre.label" default="OrgName" /></td>
						<td valign="top" class="value">
							${fieldValue(bean: companyInstance, field: "orgName")}
						</td>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="company.nombre.label" default="Unidad Organizativa" /></td>
						<td valign="top" class="value">
							${fieldValue(bean: companyInstance, field: "ou")}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="buttons">
			<g:form>
				<g:hiddenField name="id" value="${companyInstance?.id}" />
				<span class="button"><g:actionSubmit class="edit"
						action="edit"
						value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
				<span class="button"><g:actionSubmit class="delete"
						action="delete"
						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
						onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
				<span class="button"><input class="create" type="button"
					value="A&ntilde;adir compa&ntilde;ia"
					onclick="document.location='${createLinkTo(dir:'/company/create')}'" /></span>
				<span class="button"><input class="list" type="button"
					value="Listado"
					onclick="document.location='${createLinkTo(dir:'/company/list')}'" /></span>
			</g:form>
		</div>
		<div id="relacion">
			<g:include controller="company" action="ips"
				id="${companyInstance.id}"></g:include>
		</div>
	</div>
</body>
</html>
