class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

		"/"(controller:"inicio",action:"index")
		"500"(controller:"errors", action:"serverError")
		"404"(controller:"errors", action:"notFound")
	}
}
