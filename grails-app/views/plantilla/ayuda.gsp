<g:if test="${ayuda == 'fecha'}">
	<p>
		<p>Este ayudante es un objeto de tipo Date. Se pueden utilizar todos los metodos propios de este objeto.</p>
		<p>Ademas de los siguientes metodos especificos: </p>
		<li>&#36;{fecha.dia()}	  		-	  Obtener dia</li>
		<li>&#36;{fecha.diaSemana()}	-	  Obtener dia</li>
		<li>&#36;{fecha.mes()}  		-	  Obtener mes</li>
		<li>&#36;{fecha.anio()} 		-	  Obtener a&ntilde;o</li>
		<li>&#36;{fecha.tiempo()}  		-	  Obtener hora:minutos:segundos</li>
		<li>&#36;{fecha.mostrar()} 		-	  Mostrar todo</li>
	</p>
</g:if>

<g:if test="${ayuda == 'destinatarios'}">
	<p>
		<p>Se puede acceder a todos los metodos propios de la clase org.hibernate.collection.PersistentSet</p>
		<p>Ademas se puede acceder a cada uno de los atributos del objeto Destinatario.</p>
		<li>String email				-	&#36;{destinatario.email}</li>
		<li>String nombre				-	&#36;{destinatario.nombre}</li>
		<li>String apellidos			-	&#36;{destinatario.apellidos}</li>
		<li>boolean activo				-	&#36;{destinatario.activo}</li>
		<li>Date fecha_alta				-	&#36;{destinatario.fechaAlta}</li>
		<li>Date fecha_modificacion		-	&#36;{destinatario.fechaModificacion}</li>
		<li>Company company				-	&#36;{destinatario.company}</li>
		<p>Tambien puede acceder a los metodos especificos:</p>
		<p>Ejemplos para recorrer:</p>
		<p>Lista separada por comas.<pre>&#36;{destinatarios.lista}</pre></p>
		<p>Lista en forma de listado html.<pre>&#36;{destinatarios.listaHtml}</pre></p>
	</p>
</g:if>

<g:if test="${ayuda == 'cadena'}">
	<p>
		<p>Se puede acceder a todos los metodos propios de la clase java.util.String</p>
		<p>Tambien se puede acceder a todos los metodos groovy para Strings y GStrings</p>
	</p>
</g:if>