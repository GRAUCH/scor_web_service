<html>
<head>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'logo.css')}" type="text/css"></head>
<body>
<g:form>  </g:form>
<g:form method="post" controller="ws" action="caseresult">

        <div id="panelalptis"  style=" display: none; width: 950px; padding: 20px; float: left; border-top-style: solid; border-width: 1px; border-color: #ADADAD;">

            <div style="float:left; margin: auto;  padding-right:5px;"  >
            <g:radio onclick="busquedaPorF('busqFecha0')" name="myGroup" value="fecha" checked="checked"/>
            </div>
            <div style="float:left; margin: auto;  padding-right:20px;"  >
                <label for="myGroup">Envio por Fecha</label>
            </div>
            <div style="float:left; margin: auto;  padding-bottom: 10px; padding-right: 8px;"  >
                <g:radio onclick="busquedaPor('codigoST0')" name="myGroup" value="codigoST"/>
            </div>
            <div style=" margin-left: 15px; padding-bottom: 10px;"  >
                <label for="codigotST">Envio por Numero de Caso</label>
                <div id = "codigoST0" style="display:none;">
                    <g:textField style="width: 100px;" name="codigotST" placeholder="Codigot ST"/>
                </div>
            </div>

            <div id="busqFecha0">
                <div style="float:left; margin: auto;  padding-right:20px;"  >
                   Desde <p> <g:datePicker name="ini" value="${new Date()}" precision="hour"  noSelection="['': '-Choose-']"/> </p>
                </div>
                <div style="float:left; margin: auto;  padding-right:20px;"  >
                    Hasta <p> <g:datePicker name="fin" value="${new Date()}" precision="hour"  noSelection="['': '-Choose-']"/></p>
                </div>
            </div>
            <div style = "margin-top: 18px;">
                <g:submitButton name="create" class="btn btn-blue" value="Forzar Envio ALPTIS"/>
            </div>
        </div>

    </g:form>

    <g:form method="post" controller="ws" action="caseresultAma">
        <div id="panelama" style="display: none;  width: 950px; padding: 20px; float: left; border-top-style: solid; border-width: 1px; border-color: #ADADAD;">

            <div style="float:left; margin: auto;  padding-right:5px;"  >
                <g:radio onclick="busquedaPorF('busqFecha1')" name="myGroup" value="fecha" checked="checked"/>
            </div>
            <div style="float:left; margin: auto;  padding-right:20px;"  >
                <label for="myGroup">Envio por Fecha</label>
            </div>

            <div style="float:left; margin: auto;  padding-bottom: 10px; padding-right: 8px;"  >
                <g:radio onclick="busquedaPor('codigoST1')" name="myGroup" value="codigoST"/>
            </div>
            <div style=" margin-left: 15px; padding-bottom: 10px;"  >
                <label for="codigotST">Envio por Numero de Caso</label>
                <div id = "codigoST1" style="display:none;">
                    <g:textField style="width: 100px;" name="codigotST" placeholder="Codigot ST"/>
                </div>
            </div>
            <div id="busqFecha1">
                <div style="float:left; margin: auto;  padding-right:20px;"  >
                    Desde <p>  <g:datePicker name="ini" value="${new Date()}" precision="hour"  noSelection="['': '-Choose-']"/></p>
                </div>
                <div style="float:left; margin: auto;  padding-right:20px;"  >
                    Hasta  <p><g:datePicker name="fin" value="${new Date()}" precision="hour"  noSelection="['': '-Choose-']"/></p>
                </div>
            </div>
            <div style = "margin-top: 18px;">
                <g:submitButton name="create" class="btn btn-blue" value="Forzar Envio AMA"/>
            </div>
        </div>

    </g:form>


    <g:form method="post" controller="ws" action="caseresultCaser">
        <div id="panelcaser" style="display: none; width: 950px; padding: 20px; float: left; border-top-style: solid; border-width: 1px; border-color: #ADADAD;">

            <div style="float:left; margin: auto;  padding-right:5px;"  >
                <g:radio onclick="busquedaPorF('busqFecha2')" name="myGroup" value="fecha" checked="checked"/>
            </div>
            <div style="float:left; margin: auto;  padding-right:20px;"  >
                <label for="myGroup">Envio por Fecha</label>
            </div>

            <div style="float:left; margin: auto;  padding-bottom: 10px; padding-right: 8px;"  >
                <g:radio onclick="busquedaPor('codigoST2')" name="myGroup" value="codigoST"/>
            </div>
            <div style=" margin-left: 15px; padding-bottom: 10px;"  >
                <label for="codigotST">Envio por Numero de Caso</label>
                <div id = "codigoST2" style="display:none;">
                    <g:textField style="width: 100px;" name="codigotST" placeholder="Codigot ST"/>
                </div>
            </div>
            <div id="busqFecha2">
                <div style="float:left; margin: auto;  padding-right:20px;"  >
                    Desde <p>  <g:datePicker name="ini" value="${new Date()}" precision="hour" noSelection="['': '-Choose-']"/> </p>
                </div>
                <div style="float:left; margin: auto;  padding-right:20px;"  >
                    Hasta  <p> <g:datePicker name="fin" value="${new Date()}" precision="hour" noSelection="['': '-Choose-']"/> </p>
                </div>
            </div>

            <div style = "margin-top: 18px;">
                <g:submitButton name="create" class="btn btn-blue"
                                value="Forzar Envio CASER"/>
            </div>

        </div>
    </g:form>

    <g:form method="post" controller="ws" action="caseresultCM">

        <div id="panelcajamar" style="display: none; width: 950px; padding: 20px; float: left; border-top-style: solid; border-width: 1px; border-color: #ADADAD;">

                <div style="float:left; margin: auto;  padding-right:5px;"  >
                    <g:radio onclick="busquedaPorF('busqFecha3')" name="myGroup" value="fecha" checked="checked"/>
                </div>
                <div style="float:left; margin: auto;  padding-right:20px;"  >
                    <label for="myGroup">Envio por Fecha</label>
                </div>

                <div style="float:left; margin: auto;  padding-bottom: 10px; padding-right: 8px;"  >
                    <g:radio onclick="busquedaPor('codigoST3')" name="myGroup" value="codigoST"/>
                </div>
                <div style=" margin-left: 15px; padding-bottom: 10px;"  >
                    <label for="codigotST">Envio por Numero de Caso</label>
                    <div id = "codigoST3" style="display:none;">
                        <g:textField style="width: 100px;" name="codigotST" placeholder="Codigot ST"/>
                    </div>
                </div>
                <div id="busqFecha3">
                    <div style="float:left; margin: auto;  padding-right:20px;"  >
                        Desde <p>  <g:datePicker name="ini" value="${new Date()}" precision="hour" noSelection="['': '-Choose-']"/> </p>
                    </div>
                    <div style="float:left; margin: auto;  padding-right:20px;"  >
                        Hasta  <p> <g:datePicker name="fin" value="${new Date()}" precision="hour" noSelection="['': '-Choose-']"/> </p>
                    </div>
                </div>
                <div style = "margin-top: 18px;">
                    <g:submitButton name="create" class="btn btn-blue" value="Forzar Envio Cajamar"/>
                </div>

        </div>




        <br/>
        <br/>

    </g:form>

</body>

</html>