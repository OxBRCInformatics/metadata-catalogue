package ox.softeng.metadatacatalogue.domain.core;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Classifier")
@Table(schema="\"Core\"", name="\"Classifier\"")

public class Classifier extends Sharable {

	public static final long serialVersionUID = 1L;
	
	@ManyToMany
	@JoinTable(name="\"ClassifiedComponents\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"Classifier Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"DataModelComponent Id\"") })
	protected List<DataModelComponent> classifiedComponents;

	public List<DataModelComponent> getClassifiedComponents() {
		return classifiedComponents;
	}
	
	public void classifyComponent(DataModelComponent dmc)
	{
		classifiedComponents.add(dmc);
		dmc.classifiers.add(this);
	}

}
