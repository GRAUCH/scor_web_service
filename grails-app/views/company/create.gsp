

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Nueva compa&ntilde;ia</title>
</head>
<body>

	<div class="body">
		<h1>Nueva compa&ntilde;ia</h1>

		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:hasErrors bean="${companyInstance}">
			<div class="errors">
				<g:renderErrors bean="${companyInstance}" as="list" />
			</div>
		</g:hasErrors>
		<g:form action="save" method="post">
			<div class="dialog">
				<table>
					<tbody>

						<tr class="prop">
							<td valign="top" class="name"><label for="nombre"><g:message
										code="company.nombre.label" default="Nombre" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: companyInstance, field: 'nombre', 'errors')}">
								<g:textField name="nombre" value="${companyInstance?.nombre}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="codigoSt"><g:message
										code="company.codigoSt.label" default="Codigo St" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: companyInstance, field: 'codigoSt', 'errors')}">
								<g:textField name="codigoSt"
									value="${companyInstance?.codigoSt}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="ipControl">Control
									por Ip:</label></td>
							<td valign="top"
								class="value ${hasErrors(bean:companyInstance,field:'ipControl','errors')}">
								<g:checkBox name="ipControl"
									value="${companyInstance.ipControl}"></g:checkBox>
							</td>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><label for="ipControl">Generación
									automática:</label></td>
							<td valign="top"
								class="value ${hasErrors(bean:companyInstance,field:'generationAutomatic','errors')}">
								<g:checkBox name="generationAutomatic"
									value="${companyInstance.generationAutomatic}"></g:checkBox>
							</td>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><label for="userCrm">Usuario
									CRM:</label></td>
							<td valign="top"
								class="value ${hasErrors(bean:companyInstance,field:'userCrm','errors')}">
								<g:textField name="userCrm" value="${companyInstance?.userCrm}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="passCrm">Password
									CRM:</label></td>
							<td valign="top"
								class="value ${hasErrors(bean:companyInstance,field:'passCrm','errors')}">
								<g:passwordField name="passCrm"
									value="${companyInstance?.passCrm}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="host">Host:</label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean:companyInstance,field:'host','errors')}">
								<g:textField name="host" value="${companyInstance?.host}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="domain">Domain:</label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean:companyInstance,field:'domain','errors')}">
								<g:textField name="domain" value="${companyInstance?.domain}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="orgName">OrgName:</label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean:companyInstance,field:'orgName','errors')}">
								<g:textField name="orgName" value="${companyInstance?.orgName}" />
							</td>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><label for="orgName">Unidad
									Organizativa:</label></td>
							<td valign="top"
								class="value ${hasErrors(bean:companyInstance,field:'ou','errors')}">
								<g:select name="ou" from="${com.ws.enumeration.UnidadOrganizativa.values()}" optionKey="key" />
							</td>
						</tr>
						
					</tbody>
				</table>
			</div>
			<div class="buttons">
				<span class="button"><g:submitButton name="create"
						class="save"
						value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
				<span class="button"><input class="list" type="button"
					value="Listado"
					onclick="document.location='${createLinkTo(dir:'/company/list')}'" /></span>
			</div>
		</g:form>
	</div>
</body>
</html>
