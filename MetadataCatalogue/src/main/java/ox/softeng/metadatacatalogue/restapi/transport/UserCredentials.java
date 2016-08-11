package ox.softeng.metadatacatalogue.restapi.transport;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class UserCredentials{

    private String password;
    private String username;

    public String getPassword() {
		return password;
	}
	public String getUsername() {
		return username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}