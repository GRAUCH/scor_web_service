package com.ws.servicios

import com.scortelemed.TipoCompany

class CompanyFactory {

    static ICompanyService getCompanyImpl(TipoCompany company) {
        ICompanyService servicio
        switch(company) {
            case TipoCompany.AMA:
                servicio = new AmaService()
                break
            case TipoCompany.CAJAMAR:
                servicio = new CajamarService()
                break
            case TipoCompany.CASER:
                servicio = new CaserService()
                break
            case TipoCompany.CBP_ITALIA:
                servicio = new CbpitaService()
                break
            case TipoCompany.ENGINYERS:
                servicio = new EnginyersService()
                break
            case TipoCompany.AFI_ESCA:
            case TipoCompany.ALPTIS:
            case TipoCompany.ZEN_UP:
                servicio = new FrancesasService()
                break
            case TipoCompany.LAGUN_ARO:
                servicio = new LagunaroService()
                break
            case TipoCompany.CF_LIFE:
                servicio = new MethislabCFService()
                break
            case TipoCompany.METHIS_LAB:
                servicio = new MethislabService()
                break
            case TipoCompany.NET_INSURANCE:
                servicio = new NetinsuranceService()
                break
            case TipoCompany.NATIONALE_NETHERLANDEN:
                servicio = new NnService()
                break
            case TipoCompany.PSN:
                servicio = new PsnService()
                break
            case TipoCompany.MALAKOFF_MEDERIC:
                servicio = new SimplefrService()
                break
            case TipoCompany.SOCIETE_GENERALE:
                servicio = new SocieteGeneraleService()
                break
        }
        return servicio
    }
}
