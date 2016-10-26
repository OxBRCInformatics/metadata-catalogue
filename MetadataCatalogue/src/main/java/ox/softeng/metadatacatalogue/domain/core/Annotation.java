package ox.softeng.metadatacatalogue.domain.core;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ox.softeng.projector.annotations.Projectable;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Annotation")
@Table(schema="\"Core\"", name="\"Annotation\"")

public class Annotation extends Sharable {

	public static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="\"Annotated Component\"")
	protected DataModelComponent annotatedComponent;

	@JsonBackReference(value="parentAnnotation")
	@ManyToOne
	@JoinColumn(name="\"Parent Annotation\"", nullable=true)
	private Annotation parentAnnotation;

	@Column(name="\"Path\"")
	private String path;

	@JsonManagedReference(value="parentAnnotation")
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parentAnnotation")
	private List<Annotation> childAnnotations;

	
	
	public DataModelComponent getAnnotatedComponent() {
		return annotatedComponent;
	}
	
	public Annotation()
	{
		
	}

	public Annotation(String label, String description, User createdBy, DataModelComponent dmc)
	{
		super(label, description, createdBy);
		annotatedComponent = dmc;
	}

	public Annotation(String label, String description, User createdBy, Annotation parentAnnotation)
	{
		super(label, description, createdBy);
		this.parentAnnotation = parentAnnotation;
	}

	@PreUpdate
	@PrePersist
	public void setPath() throws Exception 
	{
		if(parentAnnotation != null)
		{
			path = parentAnnotation.getPath() + "/" + parentAnnotation.getId();
		}
		else
		{
			path = "";
		}
	}

	public String getPath() {
		return path;
	}

	
}
