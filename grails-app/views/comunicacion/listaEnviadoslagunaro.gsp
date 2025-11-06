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
	<div class="block-content collapse in" >
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
								aria-sort="ascending" style="width: 60px;" colspan="1"
								rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting_asc">Corta</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 120px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Abreviada</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Fecha</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Identificador</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 150px;" class="sorting">Operacion</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">DNI</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">F. Nacicmiento</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">Telefonos</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">Pruebas</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">Horario</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">Dias</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting">Sida</th>
						</tr>
					</thead>

					<tbody aria-relevant="all" aria-live="polite" role="alert">
						<g:each in="${elementos}" var="elemento" status="i">
							<tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">
				
									<td>Sí</td>
									<td>Sí</td>
									<td>Sí</td>
									<td>Sí</td>
									<td>Sí</td>
									<td>Sí</td>
									<td>Sí</td>
									<td>Sí</td>
									<td>Sí</td>
									<td>Sí</td>
									<td>Sí</td>
									<td>Sí</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>