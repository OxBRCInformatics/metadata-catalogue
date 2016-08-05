package ox.softeng.metadatacatalogue.restapi;

import java.security.Principal;
import java.util.UUID;

import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ox.softeng.metadatacatalogue.domain.core.User;

public class CatalogueSecurityContext implements SecurityContext {

	
	CatalogueSecurityPrincipal principal;
	
	public CatalogueSecurityContext(UUID userId, String username, User.UserRole userRole, String firstName, String lastName)
	{
		principal = new CatalogueSecurityPrincipal(userId, username, userRole, firstName, lastName);
	}

	public CatalogueSecurityContext(User u)
	{
		principal = new CatalogueSecurityPrincipal(u.getId(), u.getEmailAddress(), u.getUserRole(), u.getFirstName(), u.getLastName());
	}

	
	public CatalogueSecurityContext()
	{
		principal = new CatalogueSecurityPrincipal();
	}

	
	@Override
	public String getAuthenticationScheme() {
		return null;
	}

	@Override
	public Principal getUserPrincipal() {
        return principal;
	}

	@Override
	public boolean isSecure() {
		
		return false;
	}

	@Override
	public boolean isUserInRole(String role)
	{
		// Go through the list of all roles in increasing order of privilege
		// If we reach the user's role first, then it's lower than the role requested (and so false)
		// Otherwise, we'll reach the parameter role, and therefore that's lower than the user's role (so return true)
		User.UserRole userRole = User.UserRole.valueOf(role);
		
		for(User.UserRole u : User.UserRole.values())
		{
			if(u == principal.userRole)
			{
				return false;
			}
			else if(u == userRole)
			{
				return true;
			}
		}
		return false;
	}
	
	public UUID getUserId() {
		return principal.userId;
	}

	public JsonNode toJSON(){
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jNode = mapper.createObjectNode();
		
		jNode.put("username", principal.username);
		jNode.put("userRole", principal.userRole.toString());
		jNode.put("firstName", principal.firstName);
		jNode.put("lastName", principal.lastName);
		jNode.put("userId", principal.userId.toString());
		return jNode;
	}

	public static CatalogueSecurityContext fromJSON(JsonNode json){
		String username = json.get("username").asText();
		UUID userId = UUID.fromString(json.get("userId").asText());
		User.UserRole userRole = User.UserRole.valueOf(json.get("userRole").asText());
		String firstName = json.get("firstName").asText();
		String lastName = json.get("lastName").asText();
		
		CatalogueSecurityContext mcsc = new CatalogueSecurityContext(userId, username, userRole, firstName, lastName);
		return mcsc;
	}
	
	public class CatalogueSecurityPrincipal implements Principal{

		UUID userId;
		String username;
		User.UserRole userRole;
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public User.UserRole getUserRole() {
			return userRole;
		}

		public void setUserRole(User.UserRole userRole) {
			this.userRole = userRole;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		String firstName;
		String lastName;
		
		CatalogueSecurityPrincipal(UUID userId, String username, User.UserRole userRole, String firstName, String lastName){
			this.username = username;
			this.userRole = userRole;
			this.userId = userId;
			this.firstName = firstName;
			this.lastName = lastName;
		}
		
		CatalogueSecurityPrincipal(){
			this.username = null;
			this.userRole = null;
			this.userId = null;
			this.firstName = null;
			this.lastName = null;
		}
		
		@Override
		public String getName() {
			return username;
		}

		public UUID getUserId() {
			return userId;
		}

		
	}

}
