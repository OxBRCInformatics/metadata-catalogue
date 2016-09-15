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
import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.UserGroup")
@Table(schema="\"Core\"", name="\"UserGroup\"")

public class UserGroup implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	@Column(name = "id", unique = true)
	@Projection(name="datamodel.pageview.id")
	@Projection(name="dataclass.pageview.datamodel")
	@Projection(name="dataelement.pageview.datamodel")
	@Projection(name="datatype.pageview.datamodel")
	@Projection(name="usergroup.id")
	protected UUID id;
	
	@Column(name="\"Name\"")
	@Projection(name="datamodel.pageview.id")
	@Projection(name="dataclass.pageview.datamodel")
	@Projection(name="dataelement.pageview.datamodel")
	@Projection(name="datatype.pageview.datamodel")
	@Projection(name="usergroup.id")
	protected String groupName;
	
	@ManyToMany (mappedBy = "groups")
	@Projection(name="usergroup.id")
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

	public boolean addGroupMember(User e) {
		return groupMembers.add(e);
	}

	public boolean removeGroupMember(User e) {
		return groupMembers.remove(e);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setGroupMembers(Set<User> groupMembers) {
		this.groupMembers = groupMembers;
	}

	
}