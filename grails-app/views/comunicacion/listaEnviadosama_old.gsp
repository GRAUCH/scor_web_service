<div class="block">
	<div class="navbar navbar-inner block-header">
		<div class="pull-right">
			<span class="badge badge-info"> ${elementos.size()}
			</span>
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
								aria-sort="ascending" style="width: 25%;" colspan="1"
								rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting_asc">Tipo de dato</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 20%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Expediente</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Solicitud</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Suplemento</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Fecha Inicio</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Fecha fin</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 20%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Estado</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 20%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Respuesta</th>
						</tr>
					</thead>

					<tbody aria-relevant="all" aria-live="polite" role="alert">
						<g:each in="${elementos}" var="elemento" status="i">
							<tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">
								<td><g:if test="${elemento?.siniestro != null}">
										SINIESTRO
									</g:if> <g:else>
										EXPEDIENTE
									</g:else></td>
								<td>
									${elemento?.expediente?.numExpediente}
								</td>
								<td>
									${elemento?.expediente?.numSolicitud}
								</td>
								<td>
									${elemento?.expediente?.numSumplemento}
								</td>
								<td>
									${elemento?.siniestro?.dateStart}
								</td>
								<td>
									${elemento?.siniestro?.dateEnd}
								</td>
								<td>
									${elemento?.estado}
								</td>
								<td>
									${elemento?.respuesta}
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>