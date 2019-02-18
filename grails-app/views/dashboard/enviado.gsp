<style>
.container {
	text-align: center;
	width: 100%;
}

.left {
	float: left;
}

.right {
	float: right;
}

.center {
	background: green;
	display: inline-block
}
</style>
<asset:javascript src="DT_bootstrap.js" />
<div class="spinner" id="spinner" style="display: none;">
	<asset:image src="spinner.gif" alt="Loading....." style="width: 100px;" />
</div>
<body>
	<div id="wrap">
		<div id="top"></div>
		<div id="left"></div>
		<div id="content">
			<div class="outer" style="padding: 0px !important;">
				<div class="inner"
					style="min-height: 0px !important; padding-right: 0px !important; padding-left: 0px important;">
					<div class="text-center">
						<h3>Log de control de los casos enviados por SCOR Telemed</h3>
						<hr>
					</div>
					<g:each in="${ciasLog}" status="i" var="cia">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<g:remoteLink class="quick-btn" style="background: none"
								onComplete="setDatosCia(${cia.id});" controller="dashboard"
								update="contenido" action="enviado"
								params="[direccion:'enviados',idCia:cia.id,nombreCia:cia.name, ou:cia.ou]">
								<i><asset:image style="height: 50px !important;"
										src="logos/${cia.logo}" /></i>
								<span class="label label-default"> ${cia.enviados.size()}
								</span>
							</g:remoteLink>
						</tr>
					</g:each>
					<div class="container">
						<div class="right">
							<button onclick="filtrar();">Filtrar</button>
						</div>
						<div class="right">
							<p style="margin: 0 0 0px important;">
								Hasta: <input type="text" id="datepickerHasta" value="${hasta}"
									style="width: 50%;">
							</p>
						</div>
						<div class="right">
							<p style="margin: 0 0 0px important;">
								Desde: <input type="text" id="datepickerDesde" value="${desde}"
									style="width: 50%;">
							</p>
						</div>
					</div>
					<input type="hidden" id="ciaId" value="${idCia}"/>
				</div>
			</div>
		</div>
	</div>
</body>
<body>
	<div class="row-fluid">
		<g:include view="comunicacion/${enviadoCia}" />
	</div>
</body>
<script>
	$(document).ready(function() {
		var showSpinner = function() {
			$("#spinner").fadeIn('fast');
		};

		// Global handlers for AJAX events
		$(document).on("ajaxSend", showSpinner).on("ajaxStop", function() {
			$("#spinner").fadeOut('fast');
		}).on("ajaxError", function(event, jqxhr, settings, exception) {
			$("#spinner").hide();
		});
		
	});

	 function filtrar() {
	  		${remoteFunction(action:'enviado', controller:'dashboard', update:'contenido',   params:'\'desde=\' + document.getElementById(\'datepickerDesde\').value +  \'&hasta=\' + document.getElementById(\'datepickerHasta\').value +  \'&idCia=\' + document.getElementById(\'ciaId\').value')}
	 }
	   
	 function setDatosCia(id){
		  $("#ciaId").val(id);
		 
	 }
		 
	 $.datepicker.regional['es'] = {
		 closeText: 'Cerrar',
		 prevText: '< Ant',
		 nextText: 'Sig >',
		 currentText: 'Hoy',
		 monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
		 monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
		 dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
		 dayNamesShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
		 dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
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