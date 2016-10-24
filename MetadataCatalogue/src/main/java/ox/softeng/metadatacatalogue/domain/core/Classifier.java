package ox.softeng.metadatacatalogue.domain.core;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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

	public Classifier()
	{
		
	}
	
	public Classifier(String label, String description, User createdBy)
	{
		super(label, description, createdBy);
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

}
