package com.scortelemed

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class SecAppUserSecAppRole implements Serializable {

	private static final long serialVersionUID = 1

	SecAppUser secAppUser
	SecAppRole secAppRole

	SecAppUserSecAppRole(SecAppUser u, SecAppRole r) {
		this()
		secAppUser = u
		secAppRole = r
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof SecAppUserSecAppRole)) {
			return false
		}

		other.secAppUser?.id == secAppUser?.id && other.secAppRole?.id == secAppRole?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (secAppUser) builder.append(secAppUser.id)
		if (secAppRole) builder.append(secAppRole.id)
		builder.toHashCode()
	}

	static SecAppUserSecAppRole get(long secAppUserId, long secAppRoleId) {
		criteriaFor(secAppUserId, secAppRoleId).get()
	}

	static boolean exists(long secAppUserId, long secAppRoleId) {
		criteriaFor(secAppUserId, secAppRoleId).count()
	}

	private static DetachedCriteria criteriaFor(long secAppUserId, long secAppRoleId) {
		SecAppUserSecAppRole.where {
			secAppUser == SecAppUser.load(secAppUserId) &&
			secAppRole == SecAppRole.load(secAppRoleId)
		}
	}

	static SecAppUserSecAppRole create(SecAppUser secAppUser, SecAppRole secAppRole, boolean flush = false) {
		def instance = new SecAppUserSecAppRole(secAppUser, secAppRole)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(SecAppUser u, SecAppRole r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = SecAppUserSecAppRole.where { secAppUser == u && secAppRole == r }.deleteAll()

		if (flush) { SecAppUserSecAppRole.withSession { it.flush() } }

		rowCount
	}

	static void removeAll(SecAppUser u, boolean flush = false) {
		if (u == null) return

		SecAppUserSecAppRole.where { secAppUser == u }.deleteAll()

		if (flush) { SecAppUserSecAppRole.withSession { it.flush() } }
	}

	static void removeAll(SecAppRole r, boolean flush = false) {
		if (r == null) return

		SecAppUserSecAppRole.where { secAppRole == r }.deleteAll()

		if (flush) { SecAppUserSecAppRole.withSession { it.flush() } }
	}

	static constraints = {
		secAppRole validator: { SecAppRole r, SecAppUserSecAppRole ur ->
			if (ur.secAppUser == null || ur.secAppUser.id == null) return
			boolean existing = false
			SecAppUserSecAppRole.withNewSession {
				existing = SecAppUserSecAppRole.exists(ur.secAppUser.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['secAppUser', 'secAppRole']
		version false
	}
}
