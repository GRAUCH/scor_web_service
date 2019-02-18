package com.scortelemed

import grails.plugin.springsecurity.SpringSecurityUtils

class LogOutController {

	/**
	 * Index action. Redirects to the Spring security logout uri.
	 */
	def index() {

		if (!request.post && SpringSecurityUtils.getSecurityConfig().logout.postOnly) {
			session.invalidate()
		}

		redirect(uri: "/")
		response.flushBuffer()
	}	
}
