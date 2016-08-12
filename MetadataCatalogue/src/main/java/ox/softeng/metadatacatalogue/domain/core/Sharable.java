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


@Entity(name="ox.softeng.metadatacatalogue.domain.core.Sharable")
@Table(schema="\"Core\"", name="\"Sharable\"")
public abstract class Sharable extends CatalogueItem implements Serializable {

	public static final long serialVersionUID = 1L;
	
	@Column(name="\"Readable By Everyone\"")
	protected Boolean readableByEveryone;
	
	@Column(name="\"Writeable By Everyone\"")
	protected Boolean writeableByEveryone;
	
	@ManyToMany
	@JoinTable(name="\"ReadableByUsers\"", schema="\"Core\"",
		joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
		inverseJoinColumns = { @JoinColumn (name="\"User Id\"") })
	protected Set<User> readableByUsers;
	
	@ManyToMany
	@JoinTable(name="\"ReadableByGroups\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"Group Id\"") })
	protected Set<UserGroup> readableByGroups;
	
	@ManyToMany
	@JoinTable(name="\"WriteableByUsers\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"User Id\"") })
	protected Set<User> writeableByUsers;
	
	@ManyToMany
	@JoinTable(name="\"WriteableByGroups\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"Group Id\"") })
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
	
}
