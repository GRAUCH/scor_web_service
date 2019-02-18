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
								role="columnheader" class="sorting_asc">Nombre</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Gender</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Nacimiento</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Expediente</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Producto</th>
							<th aria-label="Platform(s): activate to sort column ascending"
									style="width: 20%;" colspan="1" rowspan="1"
									aria-controls="example" tabindex="0" role="columnheader"
									class="sorting">Coberturas</th>
						</tr>
					</thead>

					<tbody aria-relevant="all" aria-live="polite" role="alert">
						<g:each in="${elementos}" var="elemento" status="i">
							<tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">
								<td>
									${elemento?.localSaveDossierResults?.localDossierResultsIN?.localDossier?.localCandidate?.localFullName}
								</td>
								<td><g:if
										test="${elemento?.localSaveDossierResults?.localDossierResultsIN?.localDossier?.localCandidate?.localGender == 2}">
										MUJER
									</g:if> <g:else>
										HOMBRE
									</g:else></td>
								<td><g:if
										test="${elemento?.localSaveDossierResults?.localDossierResultsIN?.localDossier?.localCandidate?.localBirthDate?.time != null}">
										<g:formatDate format="yyyy-MM-dd"
											date="${elemento?.localSaveDossierResults?.localDossierResultsIN?.localDossier?.localCandidate?.localBirthDate?.time}" />
									</g:if></td>
								<td>
									${elemento?.localSaveDossierResults?.localDossierResultsIN?.localDossier?.localDossierCode}
								</td>
								<td>
									${elemento?.localSaveDossierResults?.localDossierResultsIN?.localDossier?.localProduct?.localProductCode}
								</td>
								<td>
									<g:each
									in="${elemento?.localSaveDossierResults?.localDossierResultsIN?.localBenefitsInformationsList}"
									var="cobertura" status="j">
										${cobertura.localBenefitCode} - ${cobertura.localBenefitResultType} 
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