<html>
<head>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'logo.css')}" type="text/css"></head>

<body>
<g:form></g:form>

<g:form method="post" controller="ws" action="generarZip">

    <div id="panelzip"
         style="width: 950px; padding: 20px; float: left; border-top-style: solid; border-width: 1px; border-color: #ADADAD;">
        <div style=" margin-left: 15px; padding-bottom: 10px;">
            <label for="codigoSTzip">Envio por Numero de Caso</label>

            <div id="codigoSTzip">
                <g:textField style="width: 100px;" name="codigoST" placeholder="Codigo ST"/>
            </div>
        </div>
        <g:hiddenField name="companyName" value="${cia.name}"></g:hiddenField>
        <div style="margin-top: 18px;">
            <g:submitButton name="${cia.name}" class="btn btn-blue" value="Generar ZIP"/>
        </div>
    </div>

    <br/>
    <br/>

</g:form>

</body>

</html>