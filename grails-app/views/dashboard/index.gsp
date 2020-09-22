<!DOCTYPE html>
<html lang="en">
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
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div id="content">
        <div class="outer" style="padding: 0px !important;">
            <g:form method="post" action="index">
                <div class="inner" id="datos">

                    <div class="text-center">
                        <h3>Log de control de los casos recibidos por SCOR Telemed</h3>
                        <hr>
                    </div>
                    <g:render template="logos"
                              model="['hasta': hasta, 'desde': desde, 'ciasLog': ciasLog, 'max': max, 'idCia': idCia]"/>
                </div>
            </g:form>
        </div>
    </div>
</div>
</div>
<g:if test="${lista != null}">
    <div class="">
        <g:include view="comunicacion/${lista}"/>
    </div>
</g:if>
<g:else>
    No se ha seleccionado una opción.
</g:else>

</body>
</html>


<script>

    function myFunction(cia) {

        const listaSoporteZip = ["lagunaro", "caser", "methislabCF", "methislab", "netinsurance", "psn", "afiesca", "lifesquare", "cbp-italia", "alptis", "ama"];
        const soporteZip = {
            LAGUN_ARO: 'lagunaro',
            CASER: 'caser',
            CF_LIFE: 'methislabCF',
            METHIS_LAB: 'methislab',
            NET_INSURANCE: 'netinsurance',
            PSN: 'psn',
            AFI_ESCA: 'afiesca',
            ZEN_UP: 'lifesquare',
            CBP_ITALIA: 'cbp-italia',
            ALPTIS: 'alptis',
            AMA: 'ama'
        }


        // Con esto le enviamos al controllador la CIA que queremos consultar
        $('#idCia').val(cia.id);
        //Con esto dejamos el efecto de que se hizo click en la compania
        var elements = document.getElementsByClassName('quick-btn_Selected');
        while (elements.length > 0) {
            elements[0].classList.remove('quick-btn_Selected');
        }
        document.getElementById(cia.id).classList.toggle('quick-btn_Selected');

        //Con esto mostramos o no el panel de envios
        <g:each in="${ciasLog}" status="i" var="cia">
            var element = document.getElementById('panel${cia.name}');
            if (element != null)
                element.style.display = 'none';

            //Generador de zip
            //Busca todos los textos hidden
            var element1 = document.getElementById('txt${cia.name}');
            if (element1 != null)
                element1.parentNode.removeChild(element1);
            ////////////////////////////////////////

        </g:each>
        var ciaattr = cia.id.split('-')
        if (document.getElementById('panel' + ciaattr[1]) !== null)
            document.getElementById('panel' + ciaattr[1]).style.display = 'inline';

        ////////////////////////////////////////
        //Generador de zip
        document.getElementById('panelzip').style.display = 'none';
        for (let valor of listaSoporteZip){
            if (valor.includes(ciaattr[1])){
                var nodo = document.createElement("input");
                nodo.type = "hidden";
                nodo.value = ciaattr[1];
                nodo.id = "txt" + ciaattr[1];
                nodo.name = "companyName";
                document.getElementById('panelzip').appendChild(nodo);
                document.getElementById('panelzip').style.display = 'inline';
                break;
            }
        }
        ////////////////////////////////////////
    }

    function busquedaPor(id) {
        var ultimoCaracter = id.charAt(id.length - 1);
        var panelOculto = "busqFecha" + ultimoCaracter;

        if ((id == "codigoST0") || (id == "codigoST1") || (id == "codigoST2") || (id == "codigoST3")) {
            document.getElementById(id).style.display = 'inline';
            document.getElementById(panelOculto).style.display = 'none';
        }
    }

    function busquedaPorF(id) {
        if ((id == "busqFecha0") || (id == "busqFecha1") || (id == "busqFecha2") || (id == "busqFecha3")) {
            document.getElementById('codigoST' + id.charAt(id.length - 1)).style.display = 'none';
            document.getElementById(id).style.display = 'inline';
        }


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