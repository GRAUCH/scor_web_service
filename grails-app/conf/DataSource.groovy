dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    properties {
        removeAbandonedTimeout = "60"
        testWhileIdle = true
        timeBetweenEvictionRunsMillis = 300000
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
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            username = "root"
            password = "root"
            url = "jdbc:mysql://localhost/scorws-prepro"
        }
    }

    development {
        println "COMPILA PARA DEVELOPMENT JDNI : MySqlscorwsDS"
        dataSource {
            dbCreate = "update"
            username = "root"
            password = "root"
            url = "jdbc:mysql://localhost/scorws-prepro"
        }
    }


    preproduction_wildfly {
        println "COMPILA PARA PREPRODUCCION con el JNDI : MySqlscorwsDS"
        dataSource {
            jndiName = "java:jboss/datasources/MySqlscorwsDS"
        }
    }

    production_wildfly {
        println "COMPILA PARA PRODUCCION WILDFLY DATA SOURCE"
        dataSource {
            jndiName = "java:jboss/datasources/MySqlscorwsDS"
        }
    }
}