package ox.softeng.metadatacatalogue.domain.core;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataClass")
@Table(schema="\"Core\"", name="\"DataClass\"")
public class DataClass extends DataModelComponent {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="\"Belongs To Model\"")
	protected DataModel belongsToModel;

	@ManyToOne
	@JoinColumn(name="\"Parent Model\"")
	private DataModel parentDataModel;

	@ManyToOne
	@JoinColumn(name="\"Parent Class\"")
	private DataClass parentDataClass;
	
	@Column(name="\"Path\"")
	private String path;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="parentDataClass")
	private List<DataClass> childDataClasses;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="parentDataClass")
	private List<DataElement> childDataElements;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="referenceClass")
	private List<ReferenceType> targetOfReferenceType;

	public DataClass()
	{
		
	}
	
	public DataClass(String label, String description, User createdBy, DataModel parentModel)
	{
		super(label, description, createdBy);
		belongsToModel = parentModel;
		this.parentDataModel = parentModel;
		childDataClasses = new ArrayList<DataClass>();
		childDataElements = new ArrayList<DataElement>();
		targetOfReferenceType = new ArrayList<ReferenceType>();
	}

	public DataClass(String label, String description, User createdBy, DataClass parentClass)
	{
		super(label, description, createdBy);
		this.parentDataClass = parentClass;
		belongsToModel = parentClass.belongsToModel;
		childDataClasses = new ArrayList<DataClass>();
		childDataElements = new ArrayList<DataElement>();
		targetOfReferenceType = new ArrayList<ReferenceType>();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DataModel getBelongsToModel() {
		return belongsToModel;
	}

	public DataModel getParentDataModel() {
		return parentDataModel;
	}

	public DataClass getParentDataClass() {
		return parentDataClass;
	}

	public String getPath() {
		return path;
	}

	public List<DataClass> getChildDataClasses() {
		return childDataClasses;
	}

	public List<DataElement> getChildDataElements() {
		return childDataElements;
	}

	public List<ReferenceType> getTargetOfReferenceType() {
		return targetOfReferenceType;
	}
	
	@PreUpdate
	@PrePersist
	public void setPath() throws Exception 
	{
		if(parentDataModel != null)
		{
			path = "" + parentDataModel.getId();
		}
		else if(parentDataClass != null)
		{
			path = parentDataClass.getPath() + "/" + parentDataClass.getId();
		}
		else
		{
			throw new Exception("No parent for this data class: " + this.label);
		}
	}

}
