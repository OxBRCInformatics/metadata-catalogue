package ox.softeng.metadatacatalogue.domain.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataModel")
@Table(schema="\"Core\"", name="\"DataModel\"")

@NamedEntityGraph(
		name="DataModel",
		attributeNodes = {
				@NamedAttributeNode(value = "id"),
				@NamedAttributeNode(value = "label"),
				@NamedAttributeNode(value = "description"),
				@NamedAttributeNode(value = "author"),
				@NamedAttributeNode(value = "organization"),
				@NamedAttributeNode(value = "type"),
				@NamedAttributeNode(value = "childDataClasses")
		}
		
		
		
)

public abstract class DataModel extends Finalisable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="\"Author\"")
	private String author;
	
	@Column(name="\"Organization\"")
	private String organization;
	
	@Column(name="\"Type\"")
	private String type;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable( name="\"DataModel_ImportsFrom\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"DataModel Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"Imported DataModel Id\"") })
	private Set<DataModel> importsFrom;

	@ManyToMany (mappedBy = "importsFrom")
	private Set<DataModel> importedBy;

	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parentDataModel")
	private List<DataClass> childDataClasses;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "belongsToModel")
	private Set<DataClass> ownedDataClasses;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "belongsToModel")
	private Set<DataElement> ownedDataElements;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "belongsToModel")
	private Set<DataType> ownedDataTypes;

	public DataModel()
	{
		
	}
	
	public DataModel(String label, String description, User createdBy, String author, String organization, String type)
	{
		super(label, description, createdBy);
		this.author = author;
		this.organization = organization;
		this.type = type;
		importsFrom = new HashSet<DataModel>();
		importedBy = new HashSet<DataModel>();

		childDataClasses = new ArrayList<DataClass>();
		ownedDataClasses = new HashSet<DataClass>();
		ownedDataElements = new HashSet<DataElement>();
		ownedDataTypes = new HashSet<DataType>();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getType() {
		return type;
	}

	public Set<DataModel> getImportsFrom() {
		return importsFrom;
	}

	public Set<DataModel> getImportedBy() {
		return importedBy;
	}

	public List<DataClass> getChildDataClasses() {
		return childDataClasses;
	}

	public Set<DataClass> getOwnedDataClasses() {
		return ownedDataClasses;
	}

	public Set<DataElement> getOwnedDataElements() {
		return ownedDataElements;
	}

	public Set<DataType> getOwnedDataTypes() {
		return ownedDataTypes;
	}
	
	
}
