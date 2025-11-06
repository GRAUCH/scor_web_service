<div class="block">
	<div class="navbar navbar-inner block-header">
		<div class="pull-right">
			<span class="badge badge-info">${elementos.size()}</span>
		</div>
		<div class="muted pull-left">
			Casos enviados
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
								aria-sort="ascending" style="width: 2%;" colspan="1"
								rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting_asc">Causa</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 2%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Motivo</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Estado</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Solicitud</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Cliente</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								style="width: 25%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Error</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Referencia</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 2%;" class="sorting">Producto</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 2%;" class="sorting">Ramo</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 2%;" class="sorting">Mediana</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 2%;" class="sorting">Corta</th>
						</tr>
					</thead>

					<tbody aria-relevant="all" aria-live="polite" role="alert">
						<g:each in="${elementos}" var="elemento" status="i">
							<tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">
								<td>
									${elemento?.causa}
								</td>
								<td>
									${elemento?.motivo}
								</td>
								<td>
									<g:formatDate format="dd/MM/yyyy" date="${elemento?.fechaUltimoCambioEstado}"/>
								</td>
								<td>
									<g:formatDate format="dd/MM/yyyy" date="${elemento?.fechaSolicitud}"/>
								</td>
								<td>
									${elemento?.codigoCliente}
								</td>
								<td>
									${elemento?.error}
								</td>
								<td>
									${elemento?.numeroReferencia}
								</td>
								<td>
									${elemento?.producto}
								</td>
								<td>
									${elemento?.ramo}
								</td>
								<td>
									${elemento?.mediana}
								</td>
								<td>
									${elemento?.corta}
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
