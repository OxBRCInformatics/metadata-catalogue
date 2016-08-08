package ox.softeng.metadatacatalogue.restapi;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ox.softeng.metadatacatalogue.domain.core.User;


@XmlRootElement
@JsonInclude(Include.NON_EMPTY)
public class AuthenticationToken {

	String firstName;
	String lastName;
	User.UserRole role;
	String token;
	String username;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public User.UserRole getRole() {
		return role;
	}

	public String getToken() {
		return token;
	}

	public String getUsername() {
		return username;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setRole(User.UserRole role) {
		this.role = role;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
}
