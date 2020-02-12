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
                            class="">Apellidos</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">NIF</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Nacimiento</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Genero</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Solicitud</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Producto</th>

                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Risk Value</th>
                        <th aria-label="Platform(s): activate to sort column ascending"
                            style="width: 150px;" colspan="1" rowspan="1"
                            aria-controls="example" tabindex="0" role="columnheader"
                            class="">Aditional Field</th>

                    </tr>
                    </thead>

                    <tbody aria-relevant="all" aria-live="polite" role="alert">
                    <g:each in="${elementos}" var="elemento" status="i">
                        <tr class="\${(i % 2) == 0 ? 'gradeA even' : 'gradeA odd'}">

                            <td>${elemento?.d?.name}</td>
                            <td>${elemento?.d?.surname}</td>
                            <td>${elemento?.d?.nif}</td>
                            <td>${elemento?.d?.birthDate}</td>
                            <td>${elemento?.d?.gender}</td>
                            <td>${elemento?.d?.policyNumber}</td>
                            <td>${elemento?.gen?.product}</td>
                        <td>
                            <g:each in="${elemento?.risks}" var="risk">
                                <g:each in="${risk.riskTypeElement}" var="element">

                                      ${element?.riskValue} |

                                </g:each>
                                </td>
                            </g:each>
                            <td>
                                <g:each in="${elemento?.additional}" var="adicional">

                                    <g:each in="${adicional.additionalField}" var="ad">

                                         ${ad?.name} |

                                    </g:each>

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