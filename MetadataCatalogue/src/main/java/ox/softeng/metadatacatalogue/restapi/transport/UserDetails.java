package ox.softeng.metadatacatalogue.restapi.transport;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ox.softeng.metadatacatalogue.domain.core.User;

@XmlRootElement
@JsonInclude(Include.NON_EMPTY)
public class UserDetails {

	String firstName;
	String lastName;
	User.UserRole userRole;
	String emailAddress;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public User.UserRole getUserRole() {
		return userRole;
	}


	public String getEmailAddress() {
		return emailAddress;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUserRole(User.UserRole userRole) {
		this.userRole = userRole;
	}
	

	public void setEmailAddress(String username) {
		this.emailAddress = username;
	}

	
}
