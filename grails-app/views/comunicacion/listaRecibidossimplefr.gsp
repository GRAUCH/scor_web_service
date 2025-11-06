<div class="block">
	<div class="navbar navbar-inner block-header">
		<div class="pull-right">
			<span class="badge badge-info">
				${elementos.size()}
			</span>
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
								role="columnheader" class="sorting_asc">Name</th>
							<th aria-label="Browser: activate to sort column ascending"
								style="width: 10%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Surname</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Id. Num.</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Birthdate</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 2%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Gender</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Request</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 3%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Record</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Policy</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 10%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Product</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Oblivion</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Profession</th>
							<th aria-label="Platform(s): activate to sort column ascending"
								style="width: 5%;" colspan="1" rowspan="1"
								aria-controls="example" tabindex="0" role="columnheader"
								class="sorting">Sport</th>
						</tr>
					</thead>

					<tbody aria-relevant="all" aria-live="polite" role="alert">
						<g:each in="${elementos}" var="elemento" status="i">
							<tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">

								<td>
									a
								</td>
								<td>
									a
								</td>
								<td>
									a
								</td>
								<td>
									a
								</td>
								<td>
									a
								</td>
								<td>
									a
								</td>

								<td>
									a
								</td>
								<td>
									a
								</td>
								<td>
									a
								</td>
								<td>
									a
								</td>
								<td>
									a
								</td>
								<td>
								a
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>