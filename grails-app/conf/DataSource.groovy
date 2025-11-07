// DataSource.groovy

dataSource {
    pooled = true
    // Por defecto usamos JNDI para entornos donde haya un servidor de aplicaciones (prepro/prod)
    jndiName = "java:jboss/datasources/MySqlscorwsDS"
}

dataSource_CRMDynamics {
    pooled = true
    jndiName = "java:jboss/datasources/SqlCRMDynamics"
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory'
    use_outer_join = true
    singleSession = true
}

// Entornos específicos
environments {

    local {
        println "Configurando datasource LOCAL PostgreSQL"
        dataSource {
            pooled = true
            driverClassName = "org.postgresql.Driver"
            dialect = org.hibernate.dialect.PostgreSQLDialect
            username = "postgres"
            password = "postgres"
            dbCreate = "update"  // 'create', 'create-drop', 'update'
            url = "jdbc:postgresql://localhost:5432/scorws_prepro_local"
            jndiName = "" // <-- forzamos que no use JNDI
            properties {
                removeAbandonedTimeout = 60
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 300000
                validationQuery = "SELECT 1"
                validationQueryTimeout = 3
            }
        }

        println "Configurando datasource LOCAL SQL Server"
        dataSource_CRMDynamics {
            pooled = true
            driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
            username = "sa"
            password = "xY;;#MID!c!572"
            url = "jdbc:sqlserver://172.17.0.36:1433;databaseName=ScorTelemed_MSCRM"
            jndiName = "" // <-- forzamos que no use JNDI
            logSql = true
            readOnly = true
            properties {
                removeAbandonedTimeout = 60
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 300000
                validationQuery = "SELECT 1"
                validationQueryTimeout = 3
            }
        }
    }

    development {
        println "Configuración DEVELOPMENT: usa JNDI si está disponible"
        // Hereda la configuración global con JNDI
    }

    preproduction {
        println "Configuración PREPRODUCTION: usa JNDI en JBoss"
        // Hereda la configuración global con JNDI
    }

    production {
        println "Configuración PRODUCTION: usa JNDI en JBoss"
        // Hereda la configuración global con JNDI
    }
}

beans {
    cacheManager {
        shared = true
    }
}
