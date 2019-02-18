package com.scortelemed
import grails.util.Holders 
import grails.plugin.springsecurity.annotation.Secured

/**
 * User controller.
 */
@Secured(['ROLE_ADMIN'])
class PersonController {

	def authenticateService

	// the delete, save and update actions only accept POST requests
	//static Map allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		if (!params.max) {
			params.max = Holders.config.pag.maximo
		}
		[personList: Person.list(params)]
	}

	def show = {
		def person = Person.get(params.id)
		if (!person) {
			flash.message = "Usuario no encontrado con id $params.id"
			redirect action: list
			return
		}
		
		def roleNames = PersonAuthority.findAllByPerson(person).authority.authority
		def webserviceNames = WebserviceUsuarios.findAllByPerson(person).webservice.clave

		[person: person, roleNames: roleNames, webserviceNames: webserviceNames]
	}

	/**
	 * Person delete action. Before removing an existing person,
	 * he should be removed from those authorities which he is involved.
	 */
	def delete = {

		def person = Person.get(params.id)
		
		// hack para borrar relaciones
		if(person.webservices) {
			def tmp=[]
			person.webservices.each { tmp << it }
			tmp.each { person.removeFromWebservice(it) }
		}
		
		if (person) {
			def authPrincipal = authenticateService.principal()
			//avoid self-delete if the logged-in user is an admin
			if (!(authPrincipal instanceof String) && authPrincipal.username == person.username) {
				flash.message = "Tu no puede borrarte a ti mismo, porfavor haz login con otro usuario e intentalo de nuevo."
			}
			else {
				//first, delete this person from People_Authorities table.
				Authority.findAll().each { it.removeFromPeople(person) }
				person.delete()
				flash.message = "Usuario $params.id borrado."
			}
		}
		else {
			flash.message = "Usuario no encontrado con id $params.id"
		}

		redirect action: list
	}

	def edit = {
		def person = Person.get(params.id)

		if (!person) {
			flash.message = "Usuario no encontrado con id $params.id"
			redirect action: list
			return
		}

		return buildPersonModel(person)
	}

	/**
	 * Person update action.
	 */
	def update = {
		def person = Person.get(params.id)
		
		def listadoWs = []
		def tam
		params.each {
			if(it.key.contains("WS_") && it.value == "on") {
				tam = it.key.size() - 1
				listadoWs.add(it.key[3..tam])
			}
		}
		
		def listadoObjWs
		if(listadoWs) listadoObjWs = Webservice.getAll(listadoWs)

		// hack para reasignar relaciones webservices
		WebserviceUsuarios.findAllByPerson(person).each { 
			it.delete(flush:true) 
		}

		if (!person) {
			flash.message = "Usuario no encontrado con id $params.id"
			redirect action: edit, id: params.id
			return
		}

		long version = params.version.toLong()
		if (person.version > version) {
			person.errors.rejectValue 'version', "person.optimistic.locking.failure",
				"Otro usuario a actualizado este Usuario mientras estabas editando."
				render view: 'edit', model: buildPersonModel(person)
			return
		}

		def oldPassword = person.password
		person.properties = params

//		if (!params.password.equals(oldPassword)) {
//			person.password = authenticateService.encodePassword(params.password)
//		}

		if (person.save()) {
			def roles = PersonAuthority.findAllByPerson(person)
			PersonAuthority.findAllByPerson(person).each { it.delete(flush:true) }
			addRoles(person)
			listadoObjWs.each {
				new WebserviceUsuarios(webservice:Webservice.findByClave(it),person:person).save(flush:true)
				//Webservice.findByClave(it).addToUsuarios(person)
			}
			redirect action: show, id: person.id
		}
		else {
			render view: 'edit', model: buildPersonModel(person)
		}
	}

	def create = {
		[person: new Person(params), authorityList: Authority.list()]
	}

	/**
	 * Person save action.
	 */
	def save = {
		def person = new Person()
		person.properties = params
		//person.password = authenticateService.encodePassword(params.password)
		if (person.save()) {
			addRoles(person)
			
			params.webservices.each {
				new WebserviceUsuarios(webservice:Webservice.findById(it),person:person).save(flush:true)
			}
			redirect action: show, id: person.id
		}
		else {
			render view: 'create', model: [authorityList: Authority.list(), person: person]
		}
	}

	private void addRoles(person) {
		for (String key in params.keySet()) {
			if (key.startsWith('ROLE_')) {
				new PersonAuthority(person:person,authority:Authority.findByAuthority(key)).save(flush:true)
			}
		}
	}

	private Map buildPersonModel(person) {

		def listaWebservices = person.webservices.id.toArray()
		def listaCompleta = Webservice.list()
		
		def listaFinal = [:]
		listaCompleta.each {
			if(it.id in listaWebservices) listaFinal.put(it, true)
			else listaFinal.put(it, false)
		}

		List roles = Authority.list()
		roles.sort { r1, r2 ->
			r1.authority <=> r2.authority
		}
		Set userRoleNames = []
		for (role in person.authorities) {
			userRoleNames << role.authority
		}
		LinkedHashMap<Authority, Boolean> roleMap = [:]
		for (role in roles) {
			roleMap[(role)] = userRoleNames.contains(role.authority)
		}
		
		return [person: person, roleMap: roleMap, listaFinal: listaFinal]
	}
}
