package ox.softeng.metadatacatalogue.domain.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import ox.softeng.metadatacatalogue.domain.core.User;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.UserGroup")
@Table(schema="\"Core\"", name="\"UserGroup\"")
public class UserGroup implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	@Column(name = "id", unique = true)
	protected UUID id;
	
	@Column(name="\"Name\"")
	protected String groupName;
	
	@ManyToMany (mappedBy = "groups")
	protected Set<User> groupMembers;
	
	public UserGroup()
	{
		groupMembers = new HashSet<User>();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Set<User> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(Set<User> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public boolean addGroupMember(User e) {
		return groupMembers.add(e);
	}

	public boolean removeGroupMember(User e) {
		return groupMembers.remove(e);
	}

	
}