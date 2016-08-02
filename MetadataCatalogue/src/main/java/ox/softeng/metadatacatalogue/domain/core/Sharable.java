package ox.softeng.metadatacatalogue.domain.core;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="\"ReadableByUsers\"", schema="\"Core\"",
		joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
		inverseJoinColumns = { @JoinColumn (name="\"User Id\"") })
	protected Set<User> readableByUsers;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="\"ReadableByGroups\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"Group Id\"") })
	protected Set<UserGroup> readableByGroups;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="\"WriteableByUsers\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"User Id\"") })
	protected Set<User> writeableByUsers;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="\"WriteableByGroups\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"Sharable Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"Group Id\"") })
	protected Set<UserGroup> writeableByGroups;


}
