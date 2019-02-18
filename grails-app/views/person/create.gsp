<head>
	<meta name="layout" content="main" />
	<title>Nuevo usuario</title>
</head>

<body>
	<div class="body">
		<h1>Nuevo usuario</h1>
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<g:hasErrors bean="${person}">
		<div class="errors">
			<g:renderErrors bean="${person}" as="list" />
		</div>
		</g:hasErrors>
		<g:form action="save">
			<div class="dialog">
				<table>
				<tbody>

					<tr class="prop">
						<td valign="top" class="name"><label for="username">Login nombre:</label></td>
						<td valign="top" class="value ${hasErrors(bean:person,field:'username','errors')}">
							<input type="text" id="username" name="username" value="${person.username?.encodeAsHTML()}"/>
						</td>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><label for="userRealName">Nombre completo:</label></td>
						<td valign="top" class="value ${hasErrors(bean:person,field:'userRealName','errors')}">
							<input type="text" id="userRealName" name="userRealName" value="${person.userRealName?.encodeAsHTML()}"/>
						</td>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><label for="password">Password:</label></td>
						<td valign="top" class="value ${hasErrors(bean:person,field:'password','errors')}">
							<input type="password" id="password" name="password" value="${person.password?.encodeAsHTML()}"/>
						</td>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><label for="enabled">Activo:</label></td>
						<td valign="top" class="value ${hasErrors(bean:person,field:'enabled','errors')}">
							<g:checkBox name="enabled" value="${person.enabled}" ></g:checkBox>
						</td>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><label for="description">Descripci&oacute;n:</label></td>
						<td valign="top" class="value ${hasErrors(bean:person,field:'description','errors')}">
							<input type="text" id="description" name="description" value="${person.description?.encodeAsHTML()}"/>
						</td>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><label for="email">Email:</label></td>
						<td valign="top" class="value ${hasErrors(bean:person,field:'email','errors')}">
							<input type="text" id="email" name="email" value="${person.email?.encodeAsHTML()}"/>
						</td>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><label for="emailShow">Mostrar Email:</label></td>
						<td valign="top" class="value ${hasErrors(bean:person,field:'emailShow','errors')}">
							<g:checkBox name="emailShow" value="${person.emailShow}"/>
						</td>
					</tr>
					
					<tr class="prop">
                     	<td valign="top" class="name">
                        	<label for="company"><g:message code="person.company.label" default="Compa&ntilde;ia" /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: personInstance, field: '"company"', 'errors')}">
                        	<g:select name="company.id" from="${com.scortelemed.Company.list()}" optionKey="id" value="${personInstance?.company?.id}"  />
                        </td>
                    </tr>

					<tr class="prop">
						<td valign="top" class="name" align="left">Asiganci&oacute;n de Roles:</td>
					</tr>

					<g:each in="${authorityList}">
					<tr>
						<td valign="top" class="name" align="left">${it.authority.encodeAsHTML()}</td>
						<td align="left"><g:checkBox name="${it.authority}"/></td>
					</tr>
					</g:each>
					
					<tr class="prop">
                     	<td valign="top" class="name">
                        	<label for="webservices"><g:message code="person.webservices.label" default="Webservices" /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: personInstance, field: '"webservices"', 'errors')}">
                        	<g:select multiple="true" name="webservices" from="${com.scortelemed.Webservice.list()}" optionKey="id" value="${personInstance?.webservices?.id}"  />
                        </td>
                    </tr>

				</tbody>
				</table>
			</div>

			<div class="buttons">
            	<span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
				<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/person/list')}'"/></span>
			</div>

		</g:form>

	</div>
</body>
