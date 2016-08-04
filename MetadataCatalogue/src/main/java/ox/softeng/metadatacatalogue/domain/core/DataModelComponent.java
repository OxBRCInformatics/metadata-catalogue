package ox.softeng.metadatacatalogue.domain.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataModelComponent")
@Table(schema="\"Core\"", name="\"DataModelComponent\"")
public class DataModelComponent extends Sharable {
	
	private static final long serialVersionUID = 1L;

	@ManyToMany (mappedBy = "classifiedComponents", cascade=CascadeType.ALL)
	protected Set<Classifier> classifiers;

	@OneToMany (mappedBy = "annotatedComponent")
	protected List<Annotation> annotations;

	
	@OneToMany (mappedBy = "source")
	protected List<Link> sourceForLinks;

	@OneToMany (mappedBy = "target")
	protected List<Link> targetForLinks;

	public DataModelComponent()
	{
		
	}
	
	public DataModelComponent(String label, String description, User createdBy)
	{
		super(label, description, createdBy);
		classifiers = new HashSet<Classifier>();
		annotations = new ArrayList<Annotation>();
		sourceForLinks = new ArrayList<Link>();
		targetForLinks = new ArrayList<Link>();
	}
	

}
