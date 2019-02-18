<div class="block">
	<div class="navbar navbar-inner block-header">
		<div class="pull-right">
			<span class="badge badge-info">${elementos.size()}</span>
		</div>
		<div class="muted pull-left">
			Casos recibidos
			${company}
		</div>
	</div>
	<div class="block-content collapse in">
		<div class="span12">
			<div id="example_wrapper" class="dataTables_wrapper form-inline"
				role="grid">
				<table aria-describedby="example_info"
					class="table table-striped table-bordered dataTable" id="example"
					border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr role="row">
							<th
								aria-label="Rendering engine: activate to sort column descending"
								aria-sort="ascending" style="width: 120px;" colspan="1"
								rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting_asc">Asegurado</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 120px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Nif</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Cobertura</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Fecha</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 150px;" class="sorting">Operacion</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">Certificado</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">Poliza</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">Movimiento</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">Producto</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">Tipo documento</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">Tipo reconocimiento</th>
						</tr>
					</thead>

					<tbody aria-relevant="all" aria-live="polite" role="alert">
						<g:each in="${elementos}" var="elemento" status="i">
							<tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">
				
									<td>${elemento?.asegurado?.nombre} ${elemento?.asegurado?.apellido1} ${elemento?.asegurado?.apellido2}</td>
									<td>${elemento?.asegurado?.nif}</td>
									
									<td>
										<g:each in="${elemento?.datos_capital}" var="garantia" status="j">
											${garantia.codigo}
										</g:each>
									</td>
									
									<td>
									<g:if test="${elemento?.datos_envio?.fecha_envio != null}">
										<g:formatDate format="yyyy-MM-dd"
											date="${elemento?.datos_envio?.fecha_envio}" />
										</g:if>
									</td>
									<td>${elemento?.datos_envio?.movimiento}</td>
									<td>${elemento?.poliza?.certificado}</td>
									<td>${elemento?.poliza?.cod_poliza}</td>
									<td>${elemento?.poliza?.movimiento}</td>
									<td>${elemento?.poliza?.producto}</td>
									<td>${elemento?.poliza?.tipo_documento}</td>
									<td>${elemento?.reconocimiento_medico?.tipo_reconocimiento_medico}</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>