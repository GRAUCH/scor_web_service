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
								aria-sort="ascending" style="width: 10%;" colspan="1"
								rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting_asc">Nombre</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 10%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Apellidos</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Dni</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Nacimiento</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 3%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Genero</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Solicitud</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 1%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Igp</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Agente</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 3%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Certificado</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Poliza</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Request</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 4%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Servicio</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Cobertura</th>
								
						</tr>
					</thead>

					<tbody aria-relevant="all" aria-live="polite" role="alert">
						<g:each in="${elementos}" var="elemento" status="i">
							<tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">
				
									<td>${elemento?.candidateInformation?.name}</td>
									<td>${elemento?.candidateInformation?.surname}</td>
									<td>${elemento?.candidateInformation?.fiscalIdentificationNumber}</td>
									<td><g:if test="${elemento?.candidateInformation?.birthDate?.value != null}">
										<g:formatDate date="${Date.parse("YYYY-MM-dd'T'hh:mm:ss", elemento?.candidateInformation?.birthDate?.value?.toString())}" format="yyyy-MM-dd"/>
									</g:if></td>
									<td>${elemento?.candidateInformation?.gender}</td>
									<td><g:if test="${elemento?.policyHolderInformation?.requestDate != null}">
										<g:formatDate date="${Date.parse("YYYY-MM-dd'T'hh:mm:ss", elemento?.policyHolderInformation?.requestDate.toString())}" format="yyyy-MM-dd"/>
									</g:if></td>
									<td>${elemento?.candidateInformation?.igp}</td>
									<td>${elemento?.candidateInformation?.agente}</td>
									<td>${elemento?.policyHolderInformation?.certificateNumber}</td>
									<td>${elemento?.policyHolderInformation?.policyNumber}</td>
									<td>${elemento?.policyHolderInformation?.requestNumber}</td>
									<td>
										<g:each in="${elemento?.serviceInformation}" var="servicio" status="j">
											${servicio.serviceType} - ${servicio.serviceCode}
										</g:each>
									</td>
									<td>
										<g:each in="${elemento?.benefitsType}" var="cobertura" status="j">
											${cobertura.benefictCode}
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