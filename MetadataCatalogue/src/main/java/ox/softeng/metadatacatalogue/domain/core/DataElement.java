package ox.softeng.metadatacatalogue.domain.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataElement")
@Table(schema="\"Core\"", name="\"DataElement\"")

public class DataElement extends DataModelComponent {

	private static final long serialVersionUID = 1L;

	@Projection(name="dataelement.pageview.id", recurseProjection="dataelement.pageview.datamodel")
	@Projection(name="datamodel.export",recurseProjection="datamodel.export.belongsToModel")
	@ManyToOne
	@JoinColumn(name="\"Belongs To Model\"")
	protected DataModel belongsToModel;

	@Projection(name="dataelement.pageview.id")
	@Projection(name="dataclass.pageview.dataelement")
	@Projection(name="datamodel.export.0.1.datamodel")
	@ManyToOne
	@JoinColumn(name="\"DataType\"")
	protected DataType dataType;
	
	@Projection(name="dataelement.pageview.id")
	@Column(name="\"Path\"")
	protected String path;
	
	@JsonBackReference(value="elementParentDataClass")
	@Projection(name="dataelement.pageview.id")
	@ManyToOne
	@JoinColumn(name="\"Parent Class\"")
	private DataClass parentDataClass;

	@Projection(name="dataelement.pageview.id")
	@Projection(name="datamodelcomponent.pageview.id")
	@Transient
	private List<Breadcrumb> breadcrumbs;

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
			belongsToModel = parentDataClass.belongsToModel;
		}
		else
		{
			throw new Exception("No parent for this data element: " + this.label);
		}
	}

	
	public Set<User> findAllUsers()
	{
		HashSet<User> users = new HashSet<User>();
		users.add(this.createdBy);
		users.addAll(this.dataType.findAllUsers());
		return users;

	}


	public DataModel getBelongsToModel() {
		return belongsToModel;
	}


	public void setBelongsToModel(DataModel belongsToModel) {
		this.belongsToModel = belongsToModel;
	}


	public DataType getDataType() {
		return dataType;
	}


	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}


	public String getPath() {
		return path;
	}


	public DataClass getParentDataClass() {
		return parentDataClass;
	}

	
	public List<Breadcrumb> getBreadcrumbs() throws Exception
	{
		if(breadcrumbs == null)
		{
			setBreadcrumbs();
		}
		return breadcrumbs;
	}
	
	public void setBreadcrumbs() throws Exception
	{
		if(parentDataClass != null)
		{
			breadcrumbs = new ArrayList<Breadcrumb>();
			breadcrumbs.addAll(parentDataClass.getBreadcrumbs());
			breadcrumbs.add(new Breadcrumb(
					parentDataClass.getId(), 
					parentDataClass.getLabel(), 
					parentDataClass.getDescription(), 
					parentDataClass.getDtype() ));
		}
		else
		{
			throw new Exception("No parent for this data class: " + this.label);
		}
	}

	
}
