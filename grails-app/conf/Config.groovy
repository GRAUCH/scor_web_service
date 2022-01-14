import org.apache.log4j.DailyRollingFileAppender

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

grails.app.context = '/webservicessoap'

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
                      all          : '*/*', // 'all' maps to '*' or the first available format in withFormat
                      atom         : 'application/atom+xml',
                      css          : 'text/css',
                      csv          : 'text/csv',
                      form         : 'application/x-www-form-urlencoded',
                      html         : ['text/html', 'application/xhtml+xml'],
                      js           : 'text/javascript',
                      json         : ['application/json', 'text/json'],
                      multipartForm: 'multipart/form-data',
                      rss          : 'application/rss+xml',
                      text         : 'text/plain',
                      hal          : ['application/hal+json', 'application/hal+xml'],
                      xml          : ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
grails.scaffolding.templates.domainSuffix = 'Instance'


grails.json.legacy.builder = false
grails.enable.native2ascii = true
grails.spring.bean.packages = []
grails.web.disable.multipart = false
grails.exceptionresolver.params.exclude = ['password']
grails.hibernate.cache.queries = false
grails.hibernate.pass.readonly = false
grails.hibernate.osiv.readonly = false

environments {
    local {
        grails.logging.jul.usebridge = true
        println "Carga configuración entorno 'test'"

        zipPath = "/datos/scorWebservices/MethisLab"

        cxf {
            client {
                soapClientAlptis {
                    wsdl = "http://172.17.0.33:8003/FrontalServiciosCRM/FrontalService?wsdl"
                    namespace = "servicios"
                    clientInterface = "servicios.Frontal"
                    serviceEndpointAddress = "http://172.17.0.33:8003/FrontalServiciosCRM/FrontalService"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }

                soapClientComprimidoAlptis {
                    wsdl = "http://172.17.0.33:8888/orabpel/default/ComprimirDocumentos/ComprimirDocumentos?wsdl"
                    namespace = "com.scor.comprimirdocumentos"
                    clientInterface = "com.scor.comprimirdocumentos.ComprimirDocumentos"
                    serviceEndpointAddress = "http://172.17.0.33:8888/orabpel/default/ComprimirDocumentos/ComprimirDocumentos"
                    connectionTimeout = 240000
                    enableDefaultLoggingInterceptors = false
                }

                soapClientCrearOrabpel {
                    wsdl = "http://172.17.0.33:8888/orabpel/default/CreacionExpedienteAsyncSRP/CreacionExpedienteAsyncSRP?wsdl"
                    namespace = "com.scor.creacionexpedienteasyncsrp"
                    clientInterface = "com.scor.creacionexpedienteasyncsrp.CreacionExpedienteAsyncSRP"
                    serviceEndpointAddress = "http://172.17.0.33:8888/orabpel/default/CreacionExpedienteAsyncSRP/CreacionExpedienteAsyncSRP"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }
            }
        }
    }

    development {
        println "Carga configuración entorno 'development'"

        grails.logging.jul.usebridge = true

        zipPath = "/datos/scorWebservices/MethisLab"

        cxf {
            client {
                soapClientAlptis {
                    wsdl = "http://172.17.0.33:8003/FrontalServiciosCRM/FrontalService?wsdl"
                    namespace = "servicios"
                    clientInterface = "servicios.Frontal"
                    serviceEndpointAddress = "http://172.17.0.33:8003/FrontalServiciosCRM/FrontalService?wsdl"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }

                soapClientComprimidoAlptis {
                    wsdl = "http://172.17.0.33:8888/orabpel/default/ComprimirDocumentos/ComprimirDocumentos?wsdl"
                    namespace = "com.scor.comprimirdocumentos"
                    clientInterface = "com.scor.comprimirdocumentos.ComprimirDocumentos"
                    serviceEndpointAddress = "http://172.17.0.33:8888/orabpel/default/ComprimirDocumentos/1.0"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }

                soapClientCrearOrabpel {
                    wsdl = "http://172.17.0.33:8888/orabpel/default/CreacionExpedienteAsyncSRP/4.0?wsdl"
                    namespace = "com.scor.creacionexpedienteasyncsrp"
                    clientInterface = "com.scor.creacionexpedienteasyncsrp.CreacionExpedienteAsyncSRP"
                    serviceEndpointAddress = "http://172.17.0.33:8888/orabpel/default/CreacionExpedienteAsyncSRP/4.0?wsdl"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }
            }
        }
    }

    integration {
        println "Carga configuración entorno 'integration'"

        grails.logging.jul.usebridge = true

        zipPath = "/datos/scorWebservices/MethisLab"

        cxf {
            client {
                soapClientAlptis {
                    wsdl = "http://172.17.0.33:8003/FrontalServiciosCRM/FrontalService?wsdl"
                    namespace = "servicios"
                    clientInterface = "servicios.Frontal"
                    serviceEndpointAddress = "http://172.17.0.33:8003/FrontalServiciosCRM/FrontalService?wsdl"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }

                soapClientComprimidoAlptis {
                    wsdl = "http://172.17.0.33:8888/orabpel/default/ComprimirDocumentos/ComprimirDocumentos?wsdl"
                    namespace = "com.scor.comprimirdocumentos"
                    clientInterface = "com.scor.comprimirdocumentos.ComprimirDocumentos"
                    serviceEndpointAddress = "http://172.17.0.33:8888/orabpel/default/ComprimirDocumentos/1.0"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }

                soapClientCrearOrabpel {
                    wsdl = "http://172.17.0.33:8888/orabpel/default/CreacionExpedienteAsyncSRP/4.0?wsdl"
                    namespace = "com.scor.creacionexpedienteasyncsrp"
                    clientInterface = "com.scor.creacionexpedienteasyncsrp.CreacionExpedienteAsyncSRP"
                    serviceEndpointAddress = "http://172.17.0.33:8888/orabpel/default/CreacionExpedienteAsyncSRP/4.0?wsdl"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }
            }
        }
    }

    preproduction {
        println "Carga configuración entorno 'preproduction'"
        grails.logging.jul.usebridge = true

        zipPath = "/datos/scorWebservices/MethisLab"

        cxf {
            client {
                soapClientAlptis {
                    wsdl = "http://172.17.0.33:8003/FrontalServiciosCRM/FrontalService?wsdl"
                    namespace = "servicios"
                    clientInterface = "servicios.Frontal"
                    serviceEndpointAddress = "http://172.17.0.33:8003/FrontalServiciosCRM/FrontalService?wsdl"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }

                soapClientComprimidoAlptis {
                    wsdl = "http://172.17.0.33:8888/orabpel/default/ComprimirDocumentos/ComprimirDocumentos?wsdl"
                    namespace = "com.scor.comprimirdocumentos"
                    clientInterface = "com.scor.comprimirdocumentos.ComprimirDocumentos"
                    serviceEndpointAddress = "http://172.17.0.33:8888/orabpel/default/ComprimirDocumentos/1.0"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }

                soapClientCrearOrabpel {
                    wsdl = "http://172.17.0.33:8888/orabpel/default/CreacionExpedienteAsyncSRP/4.0?wsdl"
                    namespace = "com.scor.creacionexpedienteasyncsrp"
                    clientInterface = "com.scor.creacionexpedienteasyncsrp.CreacionExpedienteAsyncSRP"
                    serviceEndpointAddress = "http://172.17.0.33:8888/orabpel/default/CreacionExpedienteAsyncSRP/4.0?wsdl"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }
            }
        }
    }

    production{
        println "Carga configuración entorno 'production'"
        grails.logging.jul.usebridge = false

        zipPath = "/datos/scorWebservices/MethisLab"

        cxf {
            client {
                soapClientAlptis {
                    wsdl = "http://172.26.0.2:8003/FrontalServiciosCRM/FrontalService?wsdl"
                    namespace = "servicios"
                    clientInterface = "servicios.Frontal"
                    serviceEndpointAddress = "http://172.26.0.2:8003/FrontalServiciosCRM/FrontalService"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }

                soapClientComprimidoAlptis {
                    wsdl = "http://172.26.0.2:8888/orabpel/default/ComprimirDocumentos/ComprimirDocumentos?wsdl"
                    namespace = "com.scor.comprimirdocumentos"
                    clientInterface = "com.scor.comprimirdocumentos.ComprimirDocumentos"
                    serviceEndpointAddress = "http://172.26.0.2:8888/orabpel/default/ComprimirDocumentos/2.0"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }

                soapClientCrearOrabpel {
                    wsdl = "http://172.26.0.2:8888/orabpel/default/CreacionExpedienteAsyncSRP/5.0?wsdl"
                    namespace = "com.scor.creacionexpedienteasyncsrp"
                    clientInterface = "com.scor.creacionexpedienteasyncsrp.CreacionExpedienteAsyncSRP"
                    serviceEndpointAddress = "http://172.26.0.2:8888/orabpel/default/CreacionExpedienteAsyncSRP/5.0"
                    connectionTimeout = 99999
                    enableDefaultLoggingInterceptors = false
                }
            }
        }
    }


}

pag.maximo = 25



//grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.scortelemed.Person'
//grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.scortelemed.PersonAuthority'
//grails.plugin.springsecurity.authority.className = 'com.scortelemed.Authority'
//grails.plugin.springsecurity.providerNames = ['daoAuthenticationProvider', 'ldapAuthProvider', 'anonymousAuthenticationProvider']


grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.scortelemed.SecAppUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.scortelemed.SecAppUserSecAppRole'
grails.plugin.springsecurity.authority.className = 'com.scortelemed.SecAppRole'
grails.plugin.springsecurity.providerNames = ['ldapAuthProvider', 'anonymousAuthenticationProvider']

grails.plugin.springsecurity.ldap.search.base = 'DC=scor-telemed,DC=local'
grails.plugin.springsecurity.ldap.context.managerDn = 'admin-qurius'
grails.plugin.springsecurity.ldap.context.managerPassword = 'd!vz#GW7xw9'
grails.plugin.springsecurity.ldap.context.server = 'ldap://172.22.0.3:389'
grails.plugin.springsecurity.ldap.authorities.ignorePartialResultException = true // typically needed for Active Directoryocal'
grails.plugin.springsecurity.ldap.search.filter = "sAMAccountName={0}" // for Active Directory you need this
grails.plugin.springsecurity.ldap.search.searchSubtree = true
grails.plugin.springsecurity.ldap.auth.hideUserNotFoundExceptions = false
grails.plugin.springsecurity.securityConfigType = "Annotation"
grails.plugin.ldap.authentic1ator.useBind = true

//grails.plugin.ldap.ldap.rememberMe.usernameMapper.userDnBase = 'OU=SCOR Users,DC=scor,DC=local'
//grails.plugin.ldap.ldap.rememberMe.usernameMapper.usernameAttribute = 'cn'
//grails.plugin.ldap.ldap.rememberMe.detailsManager.groupSearchBase = 'CN=Users,DC=scor,DC=local'
//grails.plugin.ldap.ldap.rememberMe.detailsManager.groupRoleAttributeName = 'cn'
//grails.plugin.ldap.ldap.rememberMe.detailsManager.groupMemberAttributeName = 'member'
grails.plugin.ldap.search.attributesToReturn = ['mail', 'displayName', "givenName", "cn", "sn", "ou", "name", "memberOf", "sAMAccountName"]
grails.plugin.ldap.ldap.authorities.ignorePartialResultException = true // typically needed for Active Directory
grails.plugin.ldap.ldap.authorities.retrieveGroupRoles = true
grails.plugin.ldap.ldap.authorities.groupSearchBase = 'CN=Users,DC=scor,DC=local'
grails.plugin.ldap.ldap.authorities.groupRoleAttribute = 'cn'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        '/'              : ['permitAll'],
        '/**'            : ['permitAll'],
        '/view/index'    : ['permitAll'],
        '/index.gsp'     : ['permitAll'],
        '/**/js/**'      : ['permitAll'],
        '/**/css/**'     : ['permitAll'],
        '/**/images/**'  : ['permitAll'],
        '/**/favicon.ico': ['permitAll'],
        '/assets/**'     : ['permitAll']
]

log4j = {
    environments {
        test {
            println "Carga configuración log4j 'test'"
            appenders {
                layout:
                pattern(conversionPattern: '%c{2} %m%n')
                appender new DailyRollingFileAppender(
                        name: 'infoAppender',
                        datePattern: "'.'yyyy-MM-dd",
                        threshold: org.apache.log4j.Level.INFO,
                        fileName: './logs/scorWebserviceInfo.log',  //storage path of log file
                        layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
                )
                appender new DailyRollingFileAppender(
                        name: 'errorAppender',
                        datePattern: "'.'yyyy-MM-dd",
                        threshold: org.apache.log4j.Level.ERROR,
                        fileName: './logs/scorWebserviceError.log',  //storage path of log file
                        layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
                )
                appender new DailyRollingFileAppender(
                        name: 'debugAppender',
                        datePattern: "'.'yyyy-MM-dd",
                        threshold: org.apache.log4j.Level.DEBUG,
                        fileName: './logs/scorWebserviceDebug.log',  //storage path of log file
                        layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')

                )
            }

        }

        development {
            println "Carga configuración log4j 'development'"
            appenders {
                layout:
                pattern(conversionPattern: '%c{2} %m%n')
                appender new DailyRollingFileAppender(
                        name: 'infoAppender',
                        datePattern: "'.'yyyy-MM-dd",
                        threshold: org.apache.log4j.Level.INFO,
                        fileName: '/var/log/wildfly/appLogs/webservicessoap/scorWebserviceInfo.log',  //storage path of log file
                        layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
                )
                appender new DailyRollingFileAppender(
                        name: 'errorAppender',
                        datePattern: "'.'yyyy-MM-dd",
                        threshold: org.apache.log4j.Level.ERROR,
                        fileName: '/var/log/wildfly/appLogs/webservicessoap/scorWebserviceError.log',  //storage path of log file
                        layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
                )
            }

        }

        preproduction_wildfly {
            println "Carga configuración log4j 'preproduction_wildfly'"
            appenders {
                layout:
                pattern(conversionPattern: '%c{2} %m%n')
                appender new DailyRollingFileAppender(
                        name: 'infoAppender',
                        datePattern: "'.'yyyy-MM-dd",
                        threshold: org.apache.log4j.Level.INFO,
                        fileName: '/var/log/wildfly/appLogs/webservicessoap/scorWebserviceInfo.log',  //storage path of log file
                        layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
                )
                appender new DailyRollingFileAppender(
                        name: 'errorAppender',
                        datePattern: "'.'yyyy-MM-dd",
                        threshold: org.apache.log4j.Level.ERROR,
                        fileName: '/var/log/wildfly/appLogs/webservicessoap/scorWebserviceError.log',  //storage path of log file
                        layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
                )
            }

        }

        production_wildfly {
            println "Carga configuración log4j 'production_wildfly'"
            appenders {
                layout:
                pattern(conversionPattern: '%c{2} %m%n')
                appender new DailyRollingFileAppender(
                        name: 'infoAppender',
                        datePattern: "'.'yyyy-MM-dd",
                        threshold: org.apache.log4j.Level.INFO,
                        fileName: '/var/log/wildfly/appLogs/webservicessoap/scorWebserviceInfo.log',  //storage path of log file
                        layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
                )
                appender new DailyRollingFileAppender(
                        name: 'errorAppender',
                        datePattern: "'.'yyyy-MM-dd",
                        threshold: org.apache.log4j.Level.ERROR,
                        fileName: '/var/log/wildfly/appLogs/webservicessoap/scorWebserviceError.log',  //storage path of log file
                        layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
                )
            }

        }

    }

    error additivity: false,
            errorAppender: ['org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate']

    warn additivity: false,
            infoAppender: ['org.springframework',
            'org.hibernate',
            'grails.plugins.springsecurity',
            'groovyx.net.http']

    debug additivity: false,
            debugAppender: ["grails.app"]

    root {
        error 'errorAppender'
        info 'infoAppender'
    }




}