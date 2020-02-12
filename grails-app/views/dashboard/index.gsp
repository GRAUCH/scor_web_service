
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Dashboard</title>

<!-- CSS -->
<asset:stylesheet src="font-awesome.min.css" rel="stylesheet" />
<asset:stylesheet src="main.min.css" rel="stylesheet" />
<asset:stylesheet src="logo.css" rel="stylesheet" />
<asset:stylesheet src="bootstrap.min.css" rel="stylesheet" />
<asset:stylesheet src="bootstrap-responsive.min.css" rel="stylesheet" />
<asset:stylesheet src="styles.css" rel="stylesheet" />
<asset:stylesheet src="DT_bootstrap.css" rel="stylesheet" />

<!-- js -->
<asset:javascript src="jquery-1.9.1.js" />
<asset:javascript src="jquery.tablesorter.min.js" />
<asset:javascript src="jquery.sparkline.min.js" />
<asset:javascript src="jquery.flot.js" />
<asset:javascript src="jquery.flot.selection.js" />
<asset:javascript src="jquery.flot.resize.js" />
<asset:javascript src="screenfull.js" />
<asset:javascript src="jquery.dataTables.min.js" />
<asset:javascript src="scripts.js" />
<asset:javascript src="DT_bootstrap.js" />
<asset:javascript src="bootstrap-datepicker.js" />
<asset:javascript src="bootstrap.min.js" />
<asset:javascript src="modernizr-2.6.2-respond-1.1.0.min.js" />
<asset:javascript src="scorwebservice.js" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
</head>
<body>
	<div class="spinner" id="spinner" style="display: none;">
		<asset:image src="spinner.gif" alt="Loading....."
			style="width: 100px;" />
	</div>
	<g:include view="menu/menu.gsp" />
	<div class="container-fluid">
		<div id="contenido">
			<g:include view="dashboard/${vista}" />
		</div>
	</div>


</body>
</html>