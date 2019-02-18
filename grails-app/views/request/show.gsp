

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Mostrando Request: ${requestInstance}</title>
</head>
<body>

	<div class="body">
		<h1>
			Mostrando Request:
			${requestInstance}
		</h1>
		<g:if test="${flash.error}">
			<g:if test="${flash.error}">
				<div class="alert alert-error alert-block">
					<a class="close" data-dismiss="alert" href="#">&times;</a>
					${flash.error}
				</div>
			</g:if>
		</g:if>
		<g:if test="${flash.message}">
			<g:if test="${flash.message}">
				<div class="alert alert-info alert-block">
					<a class="close" data-dismiss="alert" href="#">&times;</a>
					${flash.message}
				</div>
			</g:if>
		</g:if>
		<div class="dialog">
			<table>
				<tbody>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="request.fecha_procesado.label" default="Procesado" /></td>

						<td valign="top" class="value"><g:formatDate
								date="${requestInstance?.fecha_procesado}" /></td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="request.claveProceso.label" default="Clave proceso" /></td>

						<td valign="top" class="value">
							${fieldValue(bean: requestInstance, field: "claveProceso")}
						</td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="request.operacion.label" default="Operacion" /></td>

						<td valign="top" class="value botonLupa"><g:link
								controller="operacion" action="show"
								id="${requestInstance?.operacion?.id}">
								${requestInstance?.operacion?.encodeAsHTML()}
							</g:link></td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="request.company.label" default="Compa&ntilde;ia" /></td>

						<td valign="top" class="value botonLupa"><g:link
								controller="company" action="show"
								id="${requestInstance?.company?.id}">
								${requestInstance?.company?.encodeAsHTML()}
							</g:link></td>

					</tr>

					<tr class="prop">
						<g:form>
							<g:hiddenField name="id" value="${requestInstance?.id}" />
							<td valign="top" class="name"><g:message
									code="request.request.label" default="Request" /></td>

							<td valign="top" class="value"><textarea id="request"
									name="request" cols="100" rows="20">
									${requestInstance.request}
								</textarea> <g:actionSubmit class="save" action="guardarRequest"
									value="Guardar Request" /> <g:if
									test="${requestInstance?.company?.encodeAsHTML().equals('lagunaro') || requestInstance?.company?.encodeAsHTML().equals('caser') || requestInstance?.company?.encodeAsHTML().equals('cajamar') || requestInstance?.company?.encodeAsHTML().equals('alptis') || requestInstance?.company?.encodeAsHTML().equals('afiesca') || requestInstance?.company?.encodeAsHTML().equals('ama') || requestInstance?.company?.encodeAsHTML().equals('netinsurance') || requestInstance?.company?.encodeAsHTML().equals('lifesquare')} ">
									<g:actionSubmit class="save" action="procesarRequest"
										value="Procesar Request" />
								</g:if></td>
						</g:form>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="request.fecha_creacion.label" default="Creaci&oacute;n" /></td>

						<td valign="top" class="value"><g:formatDate
								date="${requestInstance?.fecha_creacion}" /></td>

					</tr>

					<tr class="prop">
						<g:form>
							<g:hiddenField name="id" value="${requestInstance?.id}" />
							<td valign="top" class="name"><label for="descartado"><g:message
										code="request.descartado.label" default="Descartar" /></label></td>

							<td valign="top"
								class="value ${hasErrors(bean: requestInstance, field: 'descartado', 'errors')}">
								<g:checkBox name="descartado"
									value="${requestInstance?.descartado}"></g:checkBox> <g:actionSubmit
									class="save" action="salvarDescarte" value="Salvar opcion" />
							</td>
						</g:form>
					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="request.errores.label" default="Errores" /></td>

						<td valign="top" class="value">
							${requestInstance.errores}
						</td>
					</tr>

				</tbody>
			</table>
		</div>
		<div class="buttons">
			<g:form>
				<g:hiddenField name="id" value="${requestInstance?.id}" />
				<span class="button"><input class="list" type="button"
					value="Listado"
					onclick="document.location='${createLinkTo(dir:'/request/list')}'" /></span>
				<span class="button"><g:actionSubmit class="delete"
						action="delete"
						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
						onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Esta Seguro que desea borrar esta peticion?')}');" /></span>
				<span class="button"><g:actionSubmit class="edit"
						action="edit"
						value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
				<!--<span class="button"><input class="create" type="button" value="A&ntilde;adir request" onclick="document.location='${createLinkTo(dir:'/request/create')}'"/></span>--!>					
                </g:form>
            </div>
            <div id="relacion" >
        		<g:include controller="request" action="estadisticas" id="${requestInstance.id}"></g:include>
        	</div>
        </div>
    </body>
</html>
