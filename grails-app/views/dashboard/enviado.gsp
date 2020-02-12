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
<div>


    <div id="content">
        <div class="outer">
<g:form method="post" action="enviado">
    <div class="inner"
         style="min-height: 0px !important; padding-right: 0px !important; padding-left: 0px !important;">
        <div class="text-center">
            <h3>Log de control de los casos enviados por SCOR Telemed</h3>
            <hr>
        </div>

        <g:render template="logos"    model="['hasta': hasta, 'desde': desde, 'ciasLog': ciasLog, 'max': max, 'idCia': idCia]"/>

    </div>

</g:form>
</div>
</div>
</div>
</div>
</body>

<body>
<div class="row-fluid">
    <g:include view="comunicacion/${enviadoCia}"/>
</div>
</body>
<script>

    function myFunction(id) {
        $('#idCia').val(id);
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