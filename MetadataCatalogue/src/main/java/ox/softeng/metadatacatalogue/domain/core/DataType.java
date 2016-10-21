package ox.softeng.metadatacatalogue.domain.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataType")
@Table(schema="\"Core\"", name="\"DataType\"")

@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME,
	    include = JsonTypeInfo.As.PROPERTY,
	    property = "dtype",
	    visible = true)
	@JsonSubTypes({
	    @Type(value = PrimitiveType.class, name = "PrimitiveType"),
	    @Type(value = ReferenceType.class, name = "ReferenceType"),
	    @Type(value = EnumerationType.class, name="EnumerationType")})


public abstract class DataType extends DataModelComponent {

	private static final long serialVersionUID = 1L;

	@Projection(name="datatype.pageview.id", recurseProjection="datatype.pageview.datamodel")
	@Projection(name="datatype.creation")
	@Projection(name="datamodel.export",recurseProjection="datamodel.export.belongsToModel")
	@ManyToOne
	@JoinColumn(name="\"Belongs To Model\"")
	protected DataModel belongsToModel;

	@Projection(name="datatype.pageview.id")
	@OneToMany(mappedBy="dataType")
	protected Set<DataElement> forDataElements;
	

	@Projection(name="datamodel.export")
	@Column(name="\"Type\"")
	protected String type;

	protected DataType()
	{
		
	}
	
	public DataType(String label, String description, User createdBy, DataModel belongsToModel)
	{
		super(label, description, createdBy);
		this.belongsToModel = belongsToModel;
		this.forDataElements = new HashSet<DataElement>();
	}
	
	public Set<User> findAllUsers()
	{
		HashSet<User> users = new HashSet<User>();
		users.add(this.createdBy);
		return users;
	}


	public DataModel getBelongsToModel() {
		return belongsToModel;
	}

	public void setBelongsToModel(DataModel belongsToModel) {
		this.belongsToModel = belongsToModel;
	}

	public Set<DataElement> getForDataElements() {
		return forDataElements;
	}

	public void setForDataElements(Set<DataElement> forDataElements) {
		this.forDataElements = forDataElements;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
