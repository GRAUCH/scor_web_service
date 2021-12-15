dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = org.hibernate.dialect.MySQL5InnoDBDialect
    properties {
        removeAbandonedTimeout = "60"
        testWhileIdle = true
        timeBetweenEvictionRunsMillis = 300000
    }
}

dataSource_CRMDynamics {
    pooled = true
    driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
    dialect = org.hibernate.dialect.SQLServer2008Dialect
    readOnly = true
    properties {
        removeAbandonedTimeout = "60"
        testWhileIdle = true
        timeBetweenEvictionRunsMillis = 300000
        validationQuery = "SELECT 1"
        validationQueryTimeout = 3
    }
}

hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings
environments {
    test {
        println "Carga configuración datasource 'test' MySQL"
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            username = "root"
            password = "root"
            url = "jdbc:mysql://localhost/scorws-prepro"
        }

        println "Carga configuración datasource 'test' SQL Server"
        dataSource_CRMDynamics {
            username = "sa"
            password = "xY;;#MID!c!572"
            url = "jdbc:sqlserver://172.17.0.36:1433;databaseName=ScorTelemed_MSCRM"
            logSql = true
        }
    }

    development {
        println "Carga configuración datasource 'development'"
        dataSource {
            dbCreate = "update"
            username = "root"
            password = "root"
            url = "jdbc:mysql://localhost/scorws-prepro"
        }
    }

    preproduction_wildfly {
        println "Carga configuración datasource 'preproduction_wildfly'"
        dataSource {
            jndiName = "java:jboss/datasources/MySqlscorwsDS"
        }

        println "Carga configuración datasource 'test' SQL Server"
        dataSource_CRMDynamics {
            username = "sa"
            password = "xY;;#MID!c!572"
            url = "jdbc:sqlserver://172.17.0.36:1433;databaseName=ScorTelemed_MSCRM"
            logSql = true
        }
    }

    production_wildfly {
        println "Carga configuración datasource 'production_wildfly'"
        dataSource {
            jndiName = "java:jboss/datasources/MySqlscorwsDS"
        }
    }
}