package ox.softeng.metadatacatalogue.domain.core;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Classifier")
@Table(schema="\"Core\"", name="\"Classifier\"")

public class Classifier extends Sharable {

	public static final long serialVersionUID = 1L;
	
	@Projection(name="classifier.all")
	@ManyToMany
	@JoinTable(name="\"ClassifiedComponents\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"Classifier Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"DataModelComponent Id\"") })
	protected List<DataModelComponent> classifiedComponents;

	@JsonBackReference(value="parentClassifier")
	@ManyToOne
	@JoinColumn(name="\"Parent Classifier\"")
	private Classifier parentClassifier;

	@Column(name="\"Path\"")
	private String path;

	@JsonManagedReference(value="parentClassifier")
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parentClassifier")
	private List<Classifier> childClassifiers;

	
	public Classifier()
	{
		
	}
	
	public Classifier(String label, String description, User createdBy)
	{
		super(label, description, createdBy);
	}

	public Classifier(String label, String description, User createdBy, Classifier parentClassifier)
	{
		super(label, description, createdBy);
		this.parentClassifier = parentClassifier;
	}

	
	public List<DataModelComponent> getClassifiedComponents() {
		return classifiedComponents;
	}
	
	public void classifyComponent(DataModelComponent dmc)
	{
		classifiedComponents.add(dmc);
		//dmc.classifiers.add(this);
	}

	public void unclassifyComponent(DataModelComponent dmc)
	{
		classifiedComponents.remove(dmc);
		//dmc.classifiers.add(this);
	}

	public void setClassifiedComponents(List<DataModelComponent> classifiedComponents) {
		this.classifiedComponents = classifiedComponents;
	}
	
	@PreUpdate
	@PrePersist
	public void setPath() throws Exception 
	{
		if(parentClassifier != null)
		{
			path = parentClassifier.getPath() + "/" + parentClassifier.getId();
		}
		else
		{
			path = "";
		}
	}
	
	public String getPath() {
		return path;
	}

	public Classifier getParentClassifier() {
		return parentClassifier;
	}

	public List<Classifier> getChildClassifiers() {
		return childClassifiers;
	}



}
