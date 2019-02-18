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
			dbCreate = "" // one of 'create', 'create-drop','update'
			username = "root"
			password = "Wcbhjfod!"
			url = "jdbc:mysql://localhost/scorws-prepro"
		}
	}

	development {
		dataSource {
			dbCreate = "" // one of 'create', 'create-drop','update'
			username = "root"
			password = "1FaQ=V:=4yD"
			url = "jdbc:mysql://localhost/scorws-prepro"
		}
	}

	production {
		dataSource {
			dbCreate = ""		
			username = "scorws"
			password = "Sc0rw3bS"
			url = "jdbc:mysql://localhost/scorws"
		}
	}

	preproduction_wildfly {
		println "COMPILA PARA PREPRODUCCION"
		dataSource {
			dbCreate = "" // one of 'create', 'create-drop','update'
			username = "root"
			password = "1FaQ=V:=4yD"
			url = "jdbc:mysql://localhost/scorws-prepro"
		}
	}

	production_wildfly {
		println "COMPILA PARA PRODUCCION Wildfly"
		dataSource {
			dbCreate = ""		
			username = "scorws"
			password = "Sc0rw3bS"
			url = "jdbc:mysql://localhost/scorws"
		}
	}
}