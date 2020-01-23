package com.ws.servicios

import com.scor.global.Uri
import com.scortelemed.Company
import com.scortelemed.Ipcontrol
import grails.transaction.Transactional

@Transactional
class InterceptorEventosService {

    boolean validIp(String nombre, ip) {
//  Esto siempre devuelve false, porque enginyers no sabe usar usuarios y password en la cabecera del request. Solamente devolvemos true cuando existe la ip.
        def company = Company.findByNombre(nombre)
        boolean flag = false
        if (company.ipControl) {
            def listaIpsCompany = Ipcontrol.findAllByCompany(company)

            for (int i = 0; i < listaIpsCompany.size(); i++) {
                if (listaIpsCompany.get(i).getIpAddress().equals(ip)) {
                    flag = true
                }
            }
        } else {
            //devolvemos false porque enginyers no puede enviar usuario y password. Siempre vamos a tener que usar el filtrado.
            flag = false
        }

        return flag

    }


    Uri getComapnyFromRequest(String request) {

        String[] parts = request.split("/")

        Uri uri = new Uri(parts[1], parts[2], parts[3], parts[4])

    }

    /**String getCompanyFromHeader() {//Map<String, List<String>> headers = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));

     if (headers != null) {List<String> sa = headers.get("SOAPAction");
     if (sa != null && sa.size() > 0) {action = sa.get(0).replace("\"", "");
     if (action.equals("http://www.scortelemed.com/schemas/enginyers/addExp")) {companyName = "enginyers"}}}}**/

}
