dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
	jndiName = "java:jboss/datasources/MySqlscorwsDS"
}

dataSource_CRMDynamics {
    pooled = true
    driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
	jndiName = "java:jboss/datasources/SqlCRMDynamics"
}

beans {
	cacheManager {
		shared = true
	}
}

hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.region.factory_class = 'org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory' // Hibernate 4
    use_outer_join = true
	singleSession = true
}


// environment specific settings
environments {

    local {
        println "Carga configuración datasource 'test' MySQL"
        dataSource {
			jndiName = "" // we set to empty to avoid the default value defined previously.
            dbCreate = "update" // one of 'create', 'create-drop','update'
            username = "root"
            password = "root"
            url = "jdbc:mysql://localhost/scorws-prepro"
            properties {
                removeAbandonedTimeout = "60"
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 300000
            }
        }

        println "Carga configuración datasource 'test' SQL Server"
        dataSource_CRMDynamics {
			jndiName = "" // we set to empty to avoid the default value defined previously.
            username = "sa"
            password = "xY;;#MID!c!572"
            url = "jdbc:sqlserver://172.17.0.36:1433;databaseName=ScorTelemed_MSCRM"
            logSql = true
            readOnly = true
            properties {
                removeAbandonedTimeout = "60"
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 300000
                validationQuery = "SELECT 1"
                validationQueryTimeout = 3
            }
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

    integration {
        // the necessary properties are already defined previously on the begin of this file.
        println "Carga configuración datasource 'integration'"
        println "Carga configuración dataSource_CRMDynamics 'integration'"
    }

    preproduction {
		// the necessary properties are already defined previously on the begin of this file.
        println "Carga configuración datasource 'preproduction'"
        println "Carga configuración dataSource_CRMDynamics 'preproduction'"
    }

    production {
		// the necessary properties are already defined previously on the begin of this file.
        println "Carga configuración datasource 'production'"
        println "Carga configuración dataSource_CRMDynamics 'production'"
    }
}