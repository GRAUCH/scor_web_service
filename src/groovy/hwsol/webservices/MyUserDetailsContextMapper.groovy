package hwsol.webservices

import org.springframework.ldap.core.DirContextAdapter
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import grails.plugin.springsecurity.SpringSecurityUtils
import javax.naming.NamingEnumeration;

class MyUserDetailsContextMapper implements UserDetailsContextMapper {
	
	UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
		
		String email = ""
		
		String fullname =  ctx.attributes['name'].values[0]
		
		if (ctx.attributes['mail'] != null){
			email = ctx.attributes['mail'].values[0].toString().toLowerCase()
		}
		
		String displayName = ctx.attributes['displayName'].values[0].toString().toLowerCase()
		String givenName = ctx.attributes['givenName'].values[0].toString().toLowerCase()
		String cn = ctx.attributes['cn'].values[0].toString().toLowerCase()
		String sn = ctx.attributes['sn'].values[0].toString().toLowerCase()
		String ou = devolverOu(ctx.getDn().get(3).substring(3,5))
		String sAMAccountName = ctx.attributes['sAMAccountName'].values[0].toString().toLowerCase()
		def title = ctx.attributes['title']

		if (isAdmin(ctx)){
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"))
		}

		if (isUser(ctx)){
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"))
			authorities.add(new SimpleGrantedAuthority("ROLE_USER_"+ou))
		}
		
		new MyUserDetails(username, '', true, true, true, true,
				authorities, fullname, email, title == null ? '' : title.values[0]) {
				}
	}

	private boolean isAdmin(DirContextOperations ctx){

		NamingEnumeration memberOf = ctx.attributes['memberOf'].getAll()

		Iterator itr = memberOf.iterator();

		while(itr.hasNext()) {
			Object element = itr.next();
			if (element.equals("CN=webservices-admin,OU=Sistemas,OU=SCOR-TELEMED Users,DC=scor-telemed,DC=local")){
				return true;
			}
		}
	}

	private boolean isUser(DirContextOperations ctx){

		NamingEnumeration memberOf = ctx.attributes['memberOf'].getAll()

		Iterator itr = memberOf.iterator();

		while(itr.hasNext()) {
			Object element = itr.next();
			if (element.equals("CN=webservices-log,OU=Sistemas,OU=SCOR-TELEMED Users,DC=scor-telemed,DC=local")){
				return true;
			}
		}
	}

	private String devolverOu(String ou){
		
		def unidad = null;
		
		switch (ou) {
			case "Es":
				unidad = "ES"
				break
			case "Fr":
				unidad = "FR"
				break
			case "Pt":
				unidad = "PT"
				break
			case "It":
				unidad = "IT"
				break
			default:
				unidad = "AD"
				break
		}
		
		return unidad
	}

	void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
		throw new IllegalStateException("Only retrieving data from AD is currently supported")
	}
}