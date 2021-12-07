package com.ws.servicios.impl

import com.scor.global.ValorUtils
import com.scortelemed.Company
import com.scortelemed.Domain
import com.scortelemed.Product
import com.scortelemed.Validation
import com.ws.servicios.IValidationService
import grails.transaction.Transactional

@Transactional
class ValidationService implements IValidationService{

    private static final String BENEFIT_SEPARATOR = "\\|\\|"
    private static final String VALIDATION_BENEFITS = "validation.benefits"

    Collection<Validation> obtenerValidaciones(String codigoCompanyiaST, String unidadOrganizativaCompanyia, String codigoProductoCompanyia) {

        Company companyia = Company.findByCodigoStAndOu(codigoCompanyiaST, unidadOrganizativaCompanyia)

        Product product = Product.findByCode(codigoProductoCompanyia)

        Domain domain = Domain.findByCompanyAndProduct(companyia, product)

        List<Validation> validationCollection = Validation.findAllByDomainId(domain.getId().toString())

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
