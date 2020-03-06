<g:each in="${ciasLog}" status="i" var="cia">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <p onclick="myFunction(${cia.id});" class="quick-btn "
           style="background: none">
            <i><asset:image style="height: 50px !important;" src="logos/${cia.logo}"/></i>
        </p>
    </tr>
</g:each>
<div class="container">

    <div class="right">
        <p>
            Hasta: <input name="hasta" type="text" id="datepickerHasta" value="${hasta}">
        </p>
    </div>


    <div class="right">
        <p>
            Desde: <input name="desde" type="text" id="datepickerDesde" value="${desde}">
        </p>
    </div>

    <div class="right">
        <p>
            Max Value: <g:textField name="max" required="" value="${max}" class="form-control"/>
        </p>
    </div>

    <p>Seleccion el tipo:</p>
    <input type="radio" id="enviado" name="logs" value="enviado"> <label for="enviado">Enviados</label>
    <input type="radio" id="recibido" name="logs" value="recibido"> <label for="recibido">Recibidos</label>
    <input type="radio" id="error" name="logs" value="error"> <label for="recibido">Errores</label>

    <div class="right">
        <g:submitButton name="create" class="btn btn-blue"
                        value="${message(code: 'default.button.find', default: 'Create')}"/>
        <input type="hidden" id="idCia" name="idCia" value="${idCia}"/>
    </div>
</div>

