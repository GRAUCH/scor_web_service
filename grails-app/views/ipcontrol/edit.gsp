

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Editando restriccion por Ip: ${ipcontrolInstance}</title>
        <script type="text/javascript">   
			function testIp() {
				var regex= new RegExp(/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/);
				var ip = document.getElementById("ipAddress").value;
				if (regex.test(ip)) {
				      var parts = ip.split(".");
				      if (parseInt(parseFloat(parts[0])) == 0) {
				    	  alert("Invalid Ip: " + ip);
					      return false; 
					  }
				      for (var i=0; i<parts.length; i++) {
				         if (parseInt(parseFloat(parts[i])) > 255) { 
				        	 alert("Invalid Ip: " + ip);
					         return false; 
					     }
				      }
				      return true;
				   } else {
					  alert("Invalid Ip: " + ip);
				      return false;
				   }
			}
        </script>
    </head>
    <body>

        <div class="body">
            <h1>Editando restriccion por Ip: ${ipcontrolInstance}</h1>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${ipcontrolInstance}">
            <div class="errors">
                <g:renderErrors bean="${ipcontrolInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${ipcontrolInstance?.id}" />
                <g:hiddenField name="version" value="${ipcontrolInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ipAddress"><g:message code="ipcontrol.ipAddress.label" default="Ip Address" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ipcontrolInstance, field: 'ipAddress', 'errors')}">
                                    <g:textField name="ipAddress" value="${ipcontrolInstance?.ipAddress}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="company"><g:message code="ipcontrol.company.label" default="Company" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ipcontrolInstance, field: 'company', 'errors')}">
                                    <g:select name="company.id" from="${com.scortelemed.Company.list()}" optionKey="id" value="${ipcontrolInstance?.company?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" onclick="return testIp();"/></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><input class="list" type="button" value="Mostrar Compa&ntilde;ia" onclick="document.location='${createLinkTo(dir:'/company/show/' + ipcontrolInstance.company.id)}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
