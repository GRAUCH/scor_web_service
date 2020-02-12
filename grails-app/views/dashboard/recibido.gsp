<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>

    <!-- CSS -->
    <asset:stylesheet src="font-awesome.min.css" rel="stylesheet"/>
    <asset:stylesheet src="main.min.css" rel="stylesheet"/>
    <asset:stylesheet src="logo.css" rel="stylesheet"/>
    <asset:stylesheet src="bootstrap.min.css" rel="stylesheet"/>
    <asset:stylesheet src="bootstrap-responsive.min.css" rel="stylesheet"/>
    <asset:stylesheet src="styles.css" rel="stylesheet"/>
    <asset:stylesheet src="DT_bootstrap.css" rel="stylesheet"/>

    <!-- js -->
    <asset:javascript src="jquery-1.9.1.js"/>
    <asset:javascript src="jquery.tablesorter.min.js"/>
    <asset:javascript src="jquery.sparkline.min.js"/>
    <asset:javascript src="jquery.flot.js"/>
    <asset:javascript src="jquery.flot.selection.js"/>
    <asset:javascript src="jquery.flot.resize.js"/>
    <asset:javascript src="screenfull.js"/>
    <asset:javascript src="jquery.dataTables.min.js"/>
    <asset:javascript src="scripts.js"/>
    <asset:javascript src="DT_bootstrap.js"/>
    <asset:javascript src="bootstrap-datepicker.js"/>
    <asset:javascript src="bootstrap.min.js"/>
    <asset:javascript src="modernizr-2.6.2-respond-1.1.0.min.js"/>
    <asset:javascript src="scorwebservice.js"/>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css"/>
    <script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
</head>

<body>
<g:include view="menu/menu.gsp"/>
<div id="">
    <div id="top"></div>

    <div id="left"></div>

    <div id="content">
        <div class="outer" style="padding: 0px !important;">
            <g:form method="post" action="recibido">
                <div class="inner"  id="datos"    >

                        <div class="text-center">
                            <h3>Log de control de los casos recibidos por SCOR Telemed</h3>
                            <hr>
                        </div>
                <g:each in="${ciasLog}" status="i" var="cia">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <p onclick="myFunction(${cia.id});" class="quick-btn "
                           style="background: none">
                            <i><asset:image style="height: 50px !important;" src="logos/${cia.logo}"/></i>
                            <span class="label label-default">${cia.recibidos.size()}</span>
                        </p>
                    </tr>

                </g:each>
                <div class="container" id="fechas">

                    <div class="right">
                        <p style="margin: 0 0 0px !important;">
                            Hasta: <input name="hasta" type="text" id="datepickerHasta" value="${hasta}"
                                          style="width: 50%;">
                        </p>
                    </div>
                    <br>
                    <br>

                    <div class="right">
                        <p style="margin: 0 0 0px !important;">
                            Desde: <input name="desde" type="text" id="datepickerDesde" value="${desde}"
                                          style="width: 50%;">
                        </p>
                    </div>

                    <div class="right">
                        <p style="margin: 0 0 0px !important;">
                            Max Value: <g:textField name="max" required="" value="${max}"    class="form-control"/>
                        </p>
                    </div>
                    <br>
                    <br>

                    <div class="right">
                        %{--<button onclick="filtrar();">Filtrar</button>--}%

                        <g:submitButton name="create" class="btn btn-blue"
                                        value="${message(code: 'default.button.create.label', default: 'Create')}"/>
                        <input type="hidden" id="idCia" name="idCia" value="${idCia}"/>
                    </div>
                </div>

            </g:form>
        </div>
        </div>
    </div>
</div>
<div class="row-fluid">
    <g:include view="comunicacion/${recibidoCia}"/>
</div>
</body>




<script>

    $(document).ready(function () {
        var showSpinner = function () {
            $("#spinner").fadeIn('fast');
        };

        // Global handlers for AJAX events
        $(document).on("ajaxSend", showSpinner).on("ajaxStop", function () {
            $("#spinner").fadeOut('fast');
        }).on("ajaxError", function (event, jqxhr, settings, exception) {
            $("#spinner").hide();
        });

    });

    function filtrar() {
        ${remoteFunction(action:'recibido', controller:'dashboard', update:'contenido',   params:'\'desde=\' + document.getElementById(\'datepickerDesde\').value +  \'&hasta=\' + document.getElementById(\'datepickerHasta\').value +  \'&idCia=\' + document.getElementById(\'ciaId\').value')}
    }

    function myFunction(id) {
        $('#idCia').val(id);
    }

    function setDatosCia(id) {
        $("#ciaId").val(id);

    }

    $.datepicker.regional['es'] = {
        closeText: 'Cerrar',
        prevText: '< Ant',
        nextText: 'Sig >',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié', 'Juv', 'Vie', 'Sáb'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá'],
        weekHeader: 'Sm',
        dateFormat: 'dd/mm/yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''
    };

    $.datepicker.setDefaults($.datepicker.regional['es']);

    $(function () {
        $("#datepickerHasta").datepicker();
    });

    $(function () {
        $("#datepickerDesde").datepicker();
    });

</script>