package ox.softeng.metadatacatalogue.domain.core;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Sharable")
@Table(schema="\"Core\"", name="\"Sharable\"")

public abstract class Sharable extends CatalogueItem implements Serializable {

	public static final long serialVersionUID = 1L;
	
	@Column(name="\"Readable By Everyone\"")
	@Projection(name="datamodel.pageview.id", classes={DataModel.class})
	@Projection(name="dataclass.pageview.datamodel")
	@Projection(name="dataelement.pageview.datamodel")
	@Projection(name="datatype.pageview.datamodel")
	protected Boolean readableByEveryone;

	@Column(name="\"Writeable By Everyone\"")
	@Projection(name="datamodel.pageview.id", classes={DataModel.class})
	@Projection(name="dataclass.pageview.datamodel")
	@Projection(name="dataelement.pageview.datamodel")
	@Projection(name="datatype.pageview.datamodel")
	protected Boolean writeableByEveryone;
	

	@ManyToMany
	@JoinTable(name="\"ReadableByUsers\"", schema="\"Core\"",
		joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
		inverseJoinColumns = { @JoinColumn (name="\"User Id\"") })
	@Projection(name="datamodel.pageview.id", classes={DataModel.class})
	@Projection(name="dataclass.pageview.datamodel")
	@Projection(name="dataelement.pageview.datamodel")
	@Projection(name="datatype.pageview.datamodel")
	protected Set<User> readableByUsers;
	
	@ManyToMany
	@JoinTable(name="\"ReadableByGroups\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"Group Id\"") })
	@Projection(name="datamodel.pageview.id", classes={DataModel.class})
	@Projection(name="dataclass.pageview.datamodel")
	@Projection(name="dataelement.pageview.datamodel")
	@Projection(name="datatype.pageview.datamodel")
	protected Set<UserGroup> readableByGroups;
	
	@ManyToMany
	@JoinTable(name="\"WriteableByUsers\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"User Id\"") })
	@Projection(name="datamodel.pageview.id", classes={DataModel.class})
	@Projection(name="dataclass.pageview.datamodel")
	@Projection(name="dataelement.pageview.datamodel")
	@Projection(name="datatype.pageview.datamodel")
	protected Set<User> writeableByUsers;
	
	@ManyToMany
	@JoinTable(name="\"WriteableByGroups\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"Group Id\"") })
	@Projection(name="datamodel.pageview.id", classes={DataModel.class})
	@Projection(name="dataclass.pageview.datamodel")
	@Projection(name="dataelement.pageview.datamodel")
	@Projection(name="datatype.pageview.datamodel")
	protected Set<UserGroup> writeableByGroups;

	public Sharable()
	{
		
	}
	
	public Sharable(String label, String description, User createdBy)
	{
		super(label, description, createdBy);
		readableByEveryone = false;
		writeableByEveryone = false;
		readableByUsers = new HashSet<User>();
		readableByUsers.add(createdBy);
		writeableByUsers = new HashSet<User>();
		writeableByUsers.add(createdBy);
		readableByGroups = new HashSet<UserGroup>();
		writeableByGroups = new HashSet<UserGroup>();
	}
	
	public void addUserReadable(User u)
	{
		readableByUsers.add(u);
	}

	public void addUserWriteable(User u)
	{
		writeableByUsers.add(u);
	}

	public void addGroupReadable(UserGroup ug)
	{
		readableByGroups.add(ug);
	}

	public void addGroupWriteable(UserGroup ug)
	{
		writeableByGroups.add(ug);
	}

	public void removeUserReadable(User u)
	{
		readableByUsers.remove(u);
	}

	public void removeUserWriteable(User u)
	{
		writeableByUsers.remove(u);
	}

	public void removeGroupReadable(UserGroup ug)
	{
		readableByGroups.remove(ug);
	}

	public void removeGroupWriteable(UserGroup ug)
	{
		writeableByGroups.remove(ug);
	}

	public Boolean getReadableByEveryone() {
		return readableByEveryone;
	}

	public void setReadableByEveryone(Boolean readableByEveryone) {
		this.readableByEveryone = readableByEveryone;
	}

	public Boolean getWriteableByEveryone() {
		return writeableByEveryone;
	}

	public void setWriteableByEveryone(Boolean writeableByEveryone) {
		this.writeableByEveryone = writeableByEveryone;
	}

	public Set<User> getReadableByUsers() {
		return readableByUsers;
	}

	public void setReadableByUsers(Set<User> readableByUsers) {
		this.readableByUsers = readableByUsers;
	}

	public Set<UserGroup> getReadableByGroups() {
		return readableByGroups;
	}

	public void setReadableByGroups(Set<UserGroup> readableByGroups) {
		this.readableByGroups = readableByGroups;
	}

	public Set<User> getWriteableByUsers() {
		return writeableByUsers;
	}

	public void setWriteableByUsers(Set<User> writeableByUsers) {
		this.writeableByUsers = writeableByUsers;
	}

	public Set<UserGroup> getWriteableByGroups() {
		return writeableByGroups;
	}

	public void setWriteableByGroups(Set<UserGroup> writeableByGroups) {
		this.writeableByGroups = writeableByGroups;
	}
	
}
