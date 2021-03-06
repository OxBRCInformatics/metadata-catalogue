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

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataModelComponent")
@Table(schema="\"Core\"", name="\"DataModelComponent\"")

public class DataModelComponent extends Sharable {
	
	private static final long serialVersionUID = 1L;

	@ManyToMany (mappedBy = "classifiedComponents", cascade=CascadeType.ALL)
	@Projection(name="datamodel.pageview.id")
	@Projection(name="dataclass.pageview.id")
	@Projection(name="dataelement.pageview.id")
	@Projection(name="datatype.pageview.id")
	@Projection(name="datatype.creation")
	protected Set<Classifier> classifiers;

	@OneToMany (mappedBy = "annotatedComponent")
	@Projection(name="datamodel.pageview.id")
	@Projection(name="dataclass.pageview.id")
	@Projection(name="dataelement.pageview.id")
	@Projection(name="datatype.pageview.id")
	@Projection(name="datatype.creation")
	@Projection(name="datamodel.export")
	protected List<Annotation> annotations;

	
	@OneToMany (mappedBy = "source")
	@Projection(name="datamodel.pageview.id")
	@Projection(name="dataclass.pageview.id")
	@Projection(name="dataelement.pageview.id")
	@Projection(name="datatype.pageview.id")
	@Projection(name="datatype.creation")
	@Projection(name="datamodel.export")
	protected Set<Link> sourceForLinks;

	@Projection(name="datamodel.pageview.id")
	@Projection(name="dataclass.pageview.id")
	@Projection(name="dataelement.pageview.id")
	@Projection(name="datatype.pageview.id")
	@OneToMany (mappedBy = "target")
	protected Set<Link> targetForLinks;

	public DataModelComponent()
	{
		
	}
	
	public DataModelComponent(String label, String description, User createdBy)
	{
		super(label, description, createdBy);
		classifiers = new HashSet<Classifier>();
		annotations = new ArrayList<Annotation>();
		sourceForLinks = new HashSet<Link>();
		targetForLinks = new HashSet<Link>();
	}

	public Set<Classifier> getClassifiers() {
		return classifiers;
	}

	public void setClassifiers(Set<Classifier> classifiers) {
		this.classifiers = classifiers;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void addClassifier(Classifier classifier)
	{
		if(classifiers == null)
		{
			classifiers = new HashSet<Classifier>();
		}
		classifiers.add(classifier);
	}

	
	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	public Set<Link> getSourceForLinks() {
		return sourceForLinks;
	}

	public void setSourceForLinks(Set<Link> sourceForLinks) {
		this.sourceForLinks = sourceForLinks;
	}

	public Set<Link> getTargetForLinks() {
		return targetForLinks;
	}

	public void setTargetForLinks(Set<Link> targetForLinks) {
		this.targetForLinks = targetForLinks;
	}
	
	public void addAnnotation(Annotation ann)
	{
		if(annotations == null)
		{
			annotations = new ArrayList<Annotation>();
		}
		annotations.add(ann);
	}

	
	
}
