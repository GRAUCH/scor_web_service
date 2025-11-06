

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Listando restricci&oacute;n por Ip</title>
    </head>
    <body>

        <div class="body">
            <h1>Listando restricci&oacute;n por Ip</h1>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'ipcontrol.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="ipAddress" title="${message(code: 'ipcontrol.ipAddress.label', default: 'Ip Address')}" />
                        
                            <th><g:message code="ipcontrol.company.label" default="Company" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${ipcontrolInstanceList}" status="i" var="ipcontrolInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${ipcontrolInstance.id}">${fieldValue(bean: ipcontrolInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: ipcontrolInstance, field: "ipAddress")}</td>
                        
                            <td>${fieldValue(bean: ipcontrolInstance, field: "company")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${ipcontrolInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
