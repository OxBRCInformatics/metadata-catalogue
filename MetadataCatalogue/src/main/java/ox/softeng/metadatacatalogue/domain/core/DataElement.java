package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataElement")
@Table(schema="\"Core\"", name="\"DataElement\"")
public class DataElement extends DataModelComponent {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="\"Belongs To Model\"")
	protected DataModel belongsToModel;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="\"DataType\"")
	protected DataType dataType;
	
	@Column(name="\"Path\"")
	protected String path;
	
	@ManyToOne
	@JoinColumn(name="\"Parent Class\"")
	private DataClass parentDataClass;

	protected DataElement()
	{
		
	}
	
	
	public DataElement(String label, String description, User createdBy, DataClass parentClass, DataType dt)
	{
		super(label, description, createdBy);
		this.parentDataClass = parentClass;
		belongsToModel = parentClass.belongsToModel;
		this.dataType = dt;
		//dt.forDataElements.add(this);
		
	}

	
	@PreUpdate
	@PrePersist
	public void setPath() throws Exception 
	{
		if(parentDataClass != null)
		{
			path = parentDataClass.getPath() + "/" + parentDataClass.getId();
		}
		else
		{
			throw new Exception("No parent for this data element: " + this.label);
		}
	}

	
	
}
