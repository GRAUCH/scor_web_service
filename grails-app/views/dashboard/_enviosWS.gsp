<g:if test="${cia.name == 'alptis'}">
    <g:form>
    </g:form>
    <g:form method="post" controller="ws" action="caseresult">
        <br/>
        <br/>
        <br/>
        <br/>
        <div style="padding-bottom: 20px;">
            <div style="float:left;  padding-right:20px;">
                Desde  <g:datePicker name="ini" value="${new Date()}" precision="hour"  noSelection="['': '-Choose-']"/>
            </div>

            <div>
                Hasta  <g:datePicker name="fin" value="${new Date()}" precision="hour"  noSelection="['': '-Choose-']"/>
            </div>
        </div>

        <div style="text-align: center;">
            <g:submitButton name="create" class="btn btn-blue" value="Forzar Envio ALPTIS"/>
        </div>

        <br/>
        <br/>
        <br/>
        <br/>
    </g:form>
</g:if>
<g:if test="${cia.name == 'ama'}">
    <g:form method="post" controller="ws" action="caseresultAma">
        <br/>
        <br/>
        <br/>
        <br/>
        <div style="padding-bottom: 20px;">
            <div style="float:left;  padding-right:20px;">
                Desde  <g:datePicker name="ini" value="${new Date()}" precision="hour"  noSelection="['': '-Choose-']"/>
            </div>

            <div>
                Hasta  <g:datePicker name="fin" value="${new Date()}" precision="hour"  noSelection="['': '-Choose-']"/>
            </div>
        </div>

        <div style="text-align: center;">
            <g:submitButton name="create" class="btn btn-blue" value="Forzar Envio AMA"/>
        </div>

        <br/>
        <br/>
        <br/>
        <br/>
    </g:form>

</g:if>
<g:if test="${cia.name == 'caser'}">
    <g:form method="post" controller="ws" action="caseresultCaser">
        <br/>
        <br/>
        <br/>
        <br/>


        <div style="padding-bottom: 20px;">
            <div style="float:left;  padding-right:20px;">
                Desde  <g:datePicker name="ini" value="${new Date()}" precision="hour"
                                     noSelection="['': '-Choose-']"/>
            </div>

            <div>
                Hasta  <g:datePicker name="fin" value="${new Date()}" precision="hour"
                                     noSelection="['': '-Choose-']"/>
            </div>


            <div style="text-align: center;">
                <g:submitButton name="create" class="btn btn-blue"
                                value="Forzar Envio CASER"/>
            </div>
        </div>
        <br/>
        <br/>
        <br/>
        <br/>
    </g:form>
</g:if>
<g:if test="${cia.name == 'cajamar'}">
    <g:form method="post" controller="ws" action="caseresultCM">
        <br/>
        <br/>
        <br/>
        <br/>

        <div style="padding-bottom: 20px;">
            <div style="float:left;  padding-right:20px;">
                Desde  <g:datePicker name="ini" value="${new Date()}" precision="hour"
                                     noSelection="['': '-Choose-']"/>
            </div>

            <div>
                Hasta  <g:datePicker name="fin" value="${new Date()}" precision="hour"
                                     noSelection="['': '-Choose-']"/>
            </div>
        </div>

        <div style="text-align: center;">
            <g:submitButton name="create" class="btn btn-blue" value="Forzar Envio CAJAMAR"/>
        </div>

        <br/>
        <br/>
        <br/>
        <br/>
    </g:form>
</g:if>
