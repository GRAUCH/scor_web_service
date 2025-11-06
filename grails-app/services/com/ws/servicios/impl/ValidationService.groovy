package com.ws.servicios.impl

import com.scor.global.ExceptionUtils
import com.scor.global.ValorUtils
import com.scortelemed.Company
import com.scortelemed.Domain
import com.scortelemed.Product
import com.scortelemed.Validation
import com.ws.servicios.IValidationService
import grails.transaction.Transactional
import grails.util.Holders
import hwsol.webservices.CorreoUtil

@Transactional
class ValidationService implements IValidationService {

    def logginService = Holders.getGrailsApplication().mainContext.getBean("logginService")
    CorreoUtil correoUtil = new CorreoUtil()

    private static final String BENEFIT_SEPARATOR = "\\|\\|"
    private static final String VALIDATION_BENEFITS = "validation.benefits"

    Collection<Validation> obtenerValidaciones(String codigoCompanyiaST, String unidadOrganizativaCompanyia, String codigoProductoCompanyia) {

        Company companyia
        Product product
        Domain domain
        List<Validation> validationCollection

        try {

            companyia = Company.findByCodigoStAndOu(codigoCompanyiaST, unidadOrganizativaCompanyia)

            if (companyia == null) {
                String errorMessage = "Error al buscar compania " + codigoCompanyiaST + " " + unidadOrganizativaCompanyia;
                throw new Exception(errorMessage.toString())
            }

            product = Product.findByCode(codigoProductoCompanyia)
            if (product == null) {
                String errorMessage = "Error al buscar producto " + codigoProductoCompanyia;
                throw new Exception(errorMessage.toString())
            }

            domain = Domain.findByCompanyAndProduct(companyia, product)
            if (domain == null) {
                String errorMessage = "Error al buscar dominio para compania " + companyia + " y producto " + product;
                throw new Exception(errorMessage.toString())
            }

            validationCollection = Validation.findAllByDomainId(domain.getId().toString())
            if (validationCollection == null || validationCollection.size() == 0) {
                String errorMessage = "Error al buscar validaciones para el dominio de la compañia " + codigoCompanyiaST + " " + unidadOrganizativaCompanyia;
                throw new Exception(errorMessage.toString())
            }
        }
        catch (Exception e) {
            logginService.putError(this.class.getName() + ".validarCoberturas", "Error en la validacion de coberturas para la companyia " + companyia.getNombre() + " y producto " + codigoProductoCompanyia + ": " + ExceptionUtils.composeMessage(null, e))
            correoUtil.envioEmailErrores(this.class.getName() + ".validarCoberturas", "Error en la validacion de coberturas para la companyia " + companyia.getNombre() + " y producto " + codigoProductoCompanyia + ": ", e)
            throw new Exception(e.getMessage(), e)
        }

        return validationCollection
    }

    Set<String> obtenerBenefitCodes(Collection<Validation> validationCollection) {

        String benefitsValidationCodes

        for (Validation validationsIterator : validationCollection) {
            if (validationsIterator.getKey().equals(VALIDATION_BENEFITS)) {
                benefitsValidationCodes = validationsIterator.getValue()
                break
            }
        }

        Set<String> benefitValidationSet

        if (ValorUtils.isValid(benefitsValidationCodes)) {
            benefitValidationSet = new HashSet<>(Arrays.asList((benefitsValidationCodes.split(BENEFIT_SEPARATOR))))
        }

        return benefitValidationSet
    }
}
