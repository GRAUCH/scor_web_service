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
                            role="columnheader" class="sorting_asc">Código Producto</th>
                        <th aria-label="Browser: activate to sort column ascending"
                            style="width: 120px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Número Petición</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Número Policy</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Numero Certificado</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Fecha Petición</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">NIF</th>
                    </tr>
                    </thead>

                    <tbody aria-relevant="all" aria-live="polite" role="alert">
                    <g:each in="${elementos}" var="elemento" status="i">
                        <tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">
                            <td>${elemento?.candidateInformation?.productCode}</td>
                            <td>${elemento?.candidateInformation?.requestNumber}</td>
                            <td>${elemento?.candidateInformation?.policyNumber}</td>
                            <td>${elemento?.candidateInformation?.certificateNumber}</td>
                            <td>${elemento?.candidateInformation?.requestDate}</td>
                            <td>${elemento?.candidateInformation?.fiscalIdentificationNumber}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>