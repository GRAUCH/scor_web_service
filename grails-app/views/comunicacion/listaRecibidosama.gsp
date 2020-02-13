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
								aria-sort="ascending" style="width: 60px;" colspan="1"
								rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting_asc">Nombre</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 120px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Apellidos</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Dni</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Nacimiento</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Genero</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Solicitud</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Agente</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Certificado</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Poliza</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Request</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Servicios</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 150px;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Coberturas</th>
						</tr>
					</thead>

					<tbody aria-relevant="all" aria-live="polite" role="alert">
						<g:each in="${elementos}" var="elemento" status="i">
							<tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">
				
									<td>${elemento?.candidateInformation?.name}</td>
									<td>${elemento?.candidateInformation?.surname}</td>
									<td>${elemento?.candidateInformation?.fiscalIdentificationNumber}</td>
									<td>${elemento?.candidateInformation?.birthDate?.value?.toString()}</td>
									<td>${elemento?.candidateInformation?.gender}</td>
									<td>${elemento?.candidateInformation?.requestDate?.value?.toString()}</td>
									<td>${elemento?.candidateInformation?.agent}</td>
									<td>${elemento?.candidateInformation?.certificateNumber}</td>
									<td>${elemento?.candidateInformation?.policyNumber}</td>
									<td>${elemento?.candidateInformation?.requestNumber}</td>
									<td>
										<g:each in="${elemento?.services}" var="servicio" status="j">
											${servicio.serviceType} - ${servicio.serviceCode}
										</g:each>
									</td>
									<td>
										<g:each in="${elemento?.benefitsType}" var="cobertura" status="j">
											${cobertura.benefictCode} - ${cobertura.benefictCapital}
										</g:each>
									</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>