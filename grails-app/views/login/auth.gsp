<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>Signin</title>

<!-- CSS -->
<asset:stylesheet src="bootstrap.min.css" rel="stylesheet" />
<asset:stylesheet src="auth.css" />


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>


	<div class="container">

		<g:if test='${flash.message}'>
			<div class="alert alert-danger" role="alert">${flash.message}</div>
		</g:if>

		<div id="grailsLogo" role="banner" style="text-align: center">
			<a href="http://grails.org">
				<asset:image src="scor.png" alt="Grails" style="width: 400px"/>
			</a>
		</div>
		
		<form action='${postUrl}' method='POST' id='loginForm'
			class='form-signin' autocomplete='off'>

			<h2 class="form-signin-heading">Please sign in</h2>

			<label for="inputEmail" class="sr-only">User</label> <input
				type="text" name='j_username' id='username' class="form-control"
				placeholder="User" required autofocus> <label
				for="inputPassword" class="sr-only">Password</label> <input
				type="password" name='j_password' id='password' class="form-control"
				placeholder="Password" required>


			<div class="checkbox">
				<label> 
				<input type='checkbox'	name='${rememberMeParameter}' id='remember_me' > Remember me
					 
				</label>
			</div>


			<button type='submit' id="submit"
				class="btn btn-lg btn-primary btn-block">Sign in</button>

		</form>
	</div>

	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<asset:javascript src="ie10-viewport-bug-workaround.js" />

</body>
</html>