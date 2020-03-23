<div style="display: flex;align-items: center; background-color: #F0F0F0; ">
    <div style="display: flex;align-items: center; margin-left: auto; margin-right: auto; padding-top: 20px; padding-bottom: 20px;">
        <div style="padding: 20px; float: left; ">
            <% def variable = 0 %>
            <g:each in="${ciasLog}" status="i" var="cia">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <div id=${cia.id} onclick="myFunction(${cia.id});" class="quick-btn " style="background: none">
                        <i><asset:image style="height: 70px !important; width: 70px !important;"
                                        src="logos/${cia.logo}"/></i>
                        %{--<span class="label label-default">${cia.recibidos.size()}</span>--}%
                    </div>
                </tr>
                <% variable++ %>
                <g:if test="${(variable > 4)}">
                    <br>
                    <% variable = 0 %>
                </g:if>
            </g:each>
       </div>

        <div class=""
             style=" width: 330px; padding: 20px; float: left; border-left-style: solid; border-width: 1px; border-color: #ADADAD;">
            <div style="padding-bottom: 20px;">
                <div style="float:left;  padding-right:20px;">
                    Desde <input style="width: 90px; margin-bottom: -0.08px;" name="desde" type="text"
                                 id="datepickerDesde" value="${desde}">
                </div>

                <div>
                    Hasta <input style="width:90px; margin-bottom: -0.08px;" name="hasta" type="text"
                                 id="datepickerHasta" value="${hasta}">
                </div>
            </div>

            <div>
                <p>
                    Valor máximo  <g:textField style="width:50px; margin-bottom: -0.08px;" name="max" required=""
                                               value="${max}" class="form-control"/>
                </p>
            </div>

            <p>Seleccion del tipo</p>

            <div style="padding-left: 10px;">
                <div style="float:left; padding-right: 6px; margin-top: -2px;"><input type="radio" id="enviado"
                                                                                      name="logs" value="enviado"></div>

                <p><label for="enviado">Enviados</label></p>

                <div style="float:left; padding-right: 6px; margin-top: -2px;"><input type="radio" id="recibido"
                                                                                      name="logs" value="recibido">
                </div>

                <p><label for="recibido">Recibidos</label></p>

                <div style="float:left; padding-right: 6px; margin-top: -2px;"><input type="radio" id="error"
                                                                                      name="logs" value="error"></div>

                <p><label for="error">Errores</label></p>
            </div>

            <div style="text-align: center;">
                <g:submitButton name="create" class="btn btn-blue"
                                value="${message(code: 'default.button.find', default: 'Create')}"/>
                <input type="hidden" id="idCia" name="idCia" value="${idCia}"/>
            </div>
        </div>
    </div>
</div>

<g:each in="${ciasLog}" status="i" var="cia">

    <g:render template="enviosWS" model="['cia': cia]"/>

</g:each>

