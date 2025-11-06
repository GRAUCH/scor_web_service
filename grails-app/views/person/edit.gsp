<head>
	<meta name="layout" content="main" />
	<title>Editando usuario: ${person}</title>
</head>

<body>
	<div class="body">
		<h1>Editando usuario: ${person}</h1>
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<g:hasErrors bean="${person}">
		<div class="errors">
			<g:renderErrors bean="${person}" as="list" />
		</div>
		</g:hasErrors>

		<g:form>
			<input type="hidden" name="id" value="${person.id}" />
			<input type="hidden" name="version" value="${person.version}" />
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
							<g:checkBox name="enabled" value="${person.enabled}"/>
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
							<input type="text" id="email" name="email" value="${person?.email?.encodeAsHTML()}"/>
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
                        	<label for="company"><g:message code="person.company.label" default="Compa&ntilde;ia" /> </label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: person, field: '"company"', 'errors')}">
                        	<g:select name="company.id" from="${com.scortelemed.Company.list()}" optionKey="id" value="${person?.company?.id}"  />
                        </td>
                    </tr>

					<tr class="prop">
						<td valign="top" class="name"><label for="authorities">Asignaci&oacute;n de roles:</label></td>
						<td valign="top" class="value ${hasErrors(bean:person,field:'authorities','errors')}">
							<ul>
							<g:each var="entry" in="${roleMap}">
								<li>${entry.key.authority.encodeAsHTML()}
									<g:checkBox name="${entry.key.authority}" value="${entry.value}"/>
								</li>
							</g:each>
							</ul>
						</td>
					</tr>
					
					<tr class="prop">
                     	<td valign="top" class="name">
                        	<label for="webservices"><g:message code="person.webservices.label" default="Webservices asociados" /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: person, field: '"webservices"', 'errors')}">
                        	<!-- <g:select multiple="true" name="webservices" from="${com.scortelemed.Webservice.list()}" optionKey="id" value="${person?.webservices?.id}"  />
                            -->
                            <ul>
								<g:each var="persona" in="${listaFinal}">
									<li>${persona.key.encodeAsHTML()}
										<g:checkBox name="WS_${persona.key.id}" value="${persona.value}"/>
									</li>
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
                <span class="button"><input class="create" type="button" value="A&ntilde;adir usuario" onclick="document.location='${createLinkTo(dir:'/person/create')}'"/></span>
				<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/person/list')}'"/></span>
                <span class="button"><input class="cancel" type="button" value="Cancelar" onclick="document.location='${createLinkTo(dir:'/person/show/' +  person?.id)}'"/></span>                
			</div>

		</g:form>

	</div>
</body>
