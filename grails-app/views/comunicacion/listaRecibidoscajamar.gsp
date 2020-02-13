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
								aria-sort="ascending" style="width: 10%;" colspan="1"
								rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" class="sorting_asc">Nombre</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 10%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Apellido</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 10%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Documento</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Genero</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 10%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Nacimiento</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								style="width: 10%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Referencia</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								style="width: 10%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Solicitud</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 10%;" class="sorting">Cliente</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 10%;" class="sorting">Producto</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 10%;" class="sorting">Ramo</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 50%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Coberturas</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 10%;" class="sorting">Mediana</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 10%;" class="sorting">Corta</th>
							<th
								aria-label="Engine version: activate to sort column ascending"
								colspan="1" rowspan="1" aria-controls="example" tabindex="0"
								role="columnheader" style="width: 10%;" class="sorting">Pruebas</th>
						</tr>
					</thead>

					<tbody aria-relevant="all" aria-live="polite" role="alert">
						<g:each in="${elementos}" var="elemento" status="i">
							<tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">
								<td>
									${elemento?.regScor?.nmbase}
								</td>
								<td>
									${elemento?.regScor?.ap1Ase} ${elemento?.regScor?.ap2Ase}
								</td>
								<td>
									${elemento?.regScor?.dniase}
								</td>
								<td>
									${elemento?.regScor?.ysexcl}
								</td>
								<td>
									${elemento?.regScor?.ffnaci?.aanccl}${elemento?.regScor?.ffnaci?.mmnccl}${elemento?.regScor?.ffnaci?.ddnccl}
								</td>
								<td>
									${elemento?.regScor?.numref}
								</td>
								<td>
									${elemento?.regScor?.fechso}
								</td>
								<td>
									${elemento?.regScor?.wpers}
								</td>
								<td>
									${elemento?.regScor?.yprodu}
								</td>
								<td>
									${elemento?.regScor?.yramex}
								</td>
								<td>
									<g:each in="${elemento?.regScor?.cobert?.cobertType}" var="cobertura"
										status="j">
										${cobertura?.cobern}
									</g:each>
								</td>
								<td><g:if test="${elemento?.regScor?.tlcom?.value == 1}">
										SI
									</g:if> <g:else>
										NO
									</g:else>
								</td>
								<td><g:if test="${elemento?.regScor?.tlabr?.value == 1}">
										SI
									</g:if> <g:else>
										NO
									</g:else>
								</td>
								%{--<td><g:if test="${elemento?.regScor?.zprumd?. == "B"}">--}%
										%{--637--}%
									%{--</g:if> --}%
									%{--<g:if test="${elemento?.regScor?.zprumd?.name?.equals("C")}">--}%
										%{--638--}%
									%{--</g:if>--}%
									%{--<g:if test="${elemento?.regScor?.zprumd?.name?.equals("D")}">--}%
										%{--639--}%
									%{--</g:if>  --}%
									%{--<g:if test="${elemento?.regScor?.zprumd?.name?.equals("E")}">--}%
										%{--640--}%
									%{--</g:if>--}%
									%{--<g:if test="${elemento?.regScor?.zprumd?.name?.equals("F")}">--}%
										%{--641--}%
									%{--</g:if>--}%
								%{--</td>--}%
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
