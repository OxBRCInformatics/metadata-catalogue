package ox.softeng.metadatacatalogue.domain.core;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.User")
@Table(schema="\"Core\"", name="\"User\"")
@NamedQuery(
		  name="User.getUserByEmailAddress",
		  query="SELECT u FROM ox.softeng.metadatacatalogue.domain.core.User u WHERE UPPER(u.emailAddress) = UPPER(:emailAddress)"
		)

public class User implements Serializable, Principal{

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	@Column(name = "id", unique = true)
	@Projection(name="datamodel.pageview.id")
	protected UUID id;

	@Column(name="\"First Name\"")
	@Projection(name="datamodel.pageview.id")
	protected String firstName;
	
	@Column(name="\"Last Name\"")
	@Projection(name="datamodel.pageview.id")
	protected String lastName;

	
	public enum UserRole { 
	    UNREGISTERED, EDITOR, ADMINISTRATOR; 
	} 
	@Column(name="\"Role\"")
	@Enumerated(EnumType.ORDINAL)
	protected UserRole userRole;
	
	@Column(name="\"Email Address\"", unique=true)
	@Projection(name="datamodel.pageview.id")
	protected String emailAddress;

	@Column(name="\"Password\"")
	protected byte[] password;

	@Column(name="\"Salt\"")
	protected byte[] salt;


	@ManyToMany
	@JoinTable( name="\"User_UserGroup\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"User Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"Group Id\"") })
	protected Set<UserGroup> groups;

	@OneToMany(mappedBy = "createdBy")
	protected Set<CatalogueItem> createdItems;

	
	public User()
	{
		groups = new HashSet<UserGroup>();
	}
	
	public User(String firstName, String lastName, String emailAddress, String password, UserRole role) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.userRole = role; 
		
		salt = generateSalt();
		this.password = getHash(password, salt);

		groups = new HashSet<UserGroup>();
	}


	public byte[] getHash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);
		return digest.digest(password.getBytes("UTF-8"));
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


	public String getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	public void setPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		this.password = getHash(password, salt);
	}

	public boolean validatePassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		return new String(this.password).equals(new String(getHash(password, salt)));
	}

	private byte[] generateSalt() throws NoSuchAlgorithmException
	{
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bSalt = new byte[8];
		random.nextBytes(bSalt);
		return bSalt;
	}
	



	public UUID getId() {
		return id;
	}



	public Set<UserGroup> getGroups() {
		return groups;
	}
	
	public void addGroup(UserGroup group)
	{
		groups.add(group);
	}


	public UserRole getUserRole() {
		return userRole;
	}


	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	public static String[] getUserRoles()
	{
		List<String> ret = new ArrayList<String>();
		for(UserRole ur : User.UserRole.values())
		{
			ret.add(ur.toString());
		}
		return ret.toArray(new String[User.UserRole.values().length]);
	}

	@Override
	public String getName() {
		
		return emailAddress;
	}

}