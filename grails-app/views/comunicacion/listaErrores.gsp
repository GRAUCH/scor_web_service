<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="pull-right">
            <span class="badge badge-info">${elementos.size()}</span>
        </div>

        <div class="muted pull-left">
            Errores de ${company}
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
                        <th aria-label="Browser: activate to sort column ascending"
                            style="width: 120px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Fecha</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Identificador</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Info</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Operacion</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Error</th>

                    </tr>
                    </thead>

                    <tbody aria-relevant="all" aria-live="polite" role="alert">
                    <g:each in="${elementos}" var="error" status="i">
                        <tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">
                            <td>${error?.fecha}</td>
                            <td>${error?.identificador}</td>
                            <td>${error?.info}</td>
                            <td>${error?.operacion}</td>
                            <td>${error?.error}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>