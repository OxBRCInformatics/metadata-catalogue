package ox.softeng.metadatacatalogue.domain.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;


@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataModel")
@Table(schema="\"Core\"", name="\"DataModel\"")

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "dtype",
		visible = true)
@JsonSubTypes({
	@Type(value = DataSet.class, name = "DataSet"),
	@Type(value = Database.class, name = "Database"),
	@Type(value = DataStandard.class, name = "DataStandard"),
	@Type(value = Form.class, name = "Form"),
	@Type(value = Message.class, name = "Message"),
	@Type(value = Report.class, name = "Report"),
	@Type(value = Workflow.class, name = "Workflow")
})

public abstract class DataModel extends Finalisable {

	private static final long serialVersionUID = 1L;

	@Projection(name="datamodel.pageview.id")
	@Column(name="\"Author\"")
	private String author;

	@Projection(name="datamodel.pageview.id")
	@Column(name="\"Organization\"")
	private String organization;

	@Projection(name="datamodel.pageview.id")
	@Column(name="\"Type\"")
	private String type;

	@ManyToMany
	@JoinTable( name="\"DataModel_ImportsFrom\"", schema="\"Core\"",
	joinColumns = { @JoinColumn (name="\"DataModel Id\"") },
	inverseJoinColumns = { @JoinColumn (name="\"Imported DataModel Id\"") })
	private Set<DataModel> importsFrom;

	@ManyToMany (mappedBy = "importsFrom")
	private Set<DataModel> importedBy;


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parentDataModel")
	@Projection(name="datamodel.pageview.id", recurseProjection="datamodel.pageview.dataclass")
	@Projection(name="datamodel.treeview")
	private List<DataClass> childDataClasses;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "belongsToModel")
	private Set<DataClass> ownedDataClasses;

	@OneToMany(mappedBy = "belongsToModel")
	private Set<DataElement> ownedDataElements;

	@OneToMany(mappedBy = "belongsToModel")
	@Projection(name="datamodel.pageview.id", recurseProjection="datamodel.pageview.dataelement")
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
