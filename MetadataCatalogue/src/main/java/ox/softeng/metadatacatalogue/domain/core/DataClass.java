package ox.softeng.metadatacatalogue.domain.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataClass")
@Table(schema="\"Core\"", name="\"DataClass\"")

public class DataClass extends DataModelComponent {

	private static final long serialVersionUID = 1L;

	@Projection(name="dataclass.pageview.id", recurseProjection="dataclass.pageview.datamodel")
	@ManyToOne
	@JoinColumn(name="\"Belongs To Model\"")
	protected DataModel belongsToModel;

	@JsonBackReference(value="parentDataModel")
	@ManyToOne
	@JoinColumn(name="\"Parent Model\"")
	private DataModel parentDataModel;

	
	@JsonBackReference(value="parentDataClass")
	@ManyToOne
	@JoinColumn(name="\"Parent Class\"")
	private DataClass parentDataClass;

	@Projection(name="dataclass.pageview.id")
	@Column(name="\"Path\"")
	private String path;

	@JsonManagedReference(value="parentDataClass")
	@Projection(name="dataclass.pageview.id", recurseProjection="dataclass.pageview.subclass")
	@Projection(name="datamodel.treeview")
	@Projection(name="datamodel.export.0.1.datamodel")
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parentDataClass")
	private List<DataClass> childDataClasses;

	@JsonManagedReference(value="elementParentDataClass")
	@Projection(name="dataclass.pageview.id", recurseProjection="dataclass.pageview.dataelement")
	@Projection(name="datamodel.export.0.1.datamodel")
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parentDataClass")
	private List<DataElement> childDataElements;

	@Projection(name="dataclass.pageview.referenceType")
	@OneToMany(cascade = CascadeType.ALL, mappedBy="referenceClass")
	private List<ReferenceType> targetOfReferenceType;

	@Transient
	@Projection(name="dataclass.pageview.id")
	@Projection(name="datamodelcomponent.pageview.id")
	private List<Breadcrumb> breadcrumbs;
	
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
	
	public Set<User> findAllUsers()
	{
		HashSet<User> users = new HashSet<User>();
		users.add(this.createdBy);
		if(childDataClasses != null)
		{
			for(DataClass dc : childDataClasses)
			{
				users.addAll(dc.findAllUsers());
			}
		}
		if(childDataElements != null)
		{
			for(DataElement de : childDataElements)
			{
				users.addAll(de.findAllUsers());
			}
		}
		return users;

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
			if(belongsToModel == null)
			{
				belongsToModel = parentDataModel;
			}
		}
		else if(parentDataClass != null)
		{
			path = parentDataClass.getPath() + "/" + parentDataClass.getId();
			if(belongsToModel == null)
			{
				belongsToModel = parentDataClass.belongsToModel;
			}
		}
		else
		{
			throw new Exception("No parent for this data class: " + this.label);
		}
	}

	public List<Breadcrumb> getBreadcrumbs() throws Exception
	{
		if(breadcrumbs == null)
		{
			setBreadcrumbs();
		}
		return breadcrumbs;
	}
	
	//@PostLoad
	public void setBreadcrumbs() throws Exception
	{
		if(getParentDataModel() != null)
		{
			breadcrumbs = new ArrayList<Breadcrumb>();
			breadcrumbs.add(new Breadcrumb(
					parentDataModel.getId(), 
					parentDataModel.getLabel(), 
					parentDataModel.getDescription(), 
					parentDataModel.getDtype() ));

		}
		else if(getParentDataClass() != null)
		{
			breadcrumbs = new ArrayList<Breadcrumb>();
			if(parentDataClass.getBreadcrumbs() == null)
			{
				parentDataClass.setBreadcrumbs();
				if(parentDataClass.getBreadcrumbs() == null)
				{
					System.out.println("Breadcrumbs are still null!");
				}
			}
		
			breadcrumbs.addAll(parentDataClass.getBreadcrumbs());
			breadcrumbs.add(new Breadcrumb(
					parentDataClass.getId(), 
					parentDataClass.getLabel(), 
					parentDataClass.getDescription(), 
					parentDataClass.getDtype() ));
		}
		else
		{
			//throw new Exception("No parent for this data class: " + this.label);
//			return null;
		}
	}
	
}
