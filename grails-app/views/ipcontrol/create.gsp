

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Nueva restriccion por Ip</title>
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
            <h1>Nueva restriccion por Ip</h1>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${ipcontrolInstance}">
            <div class="errors">
                <g:renderErrors bean="${ipcontrolInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
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
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="return testIp();" /></span>
                	<span class="button"><input class="list" type="button" value="Listado" onclick="document.location='${createLinkTo(dir:'/ipcontrol/list')}'"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
