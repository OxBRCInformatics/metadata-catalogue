package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity(name="ox.softeng.metadatacatalogue.domain.core.Annotation")
@Table(schema="\"Core\"", name="\"Annotation\"")
public class Annotation extends Sharable {

	public static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="\"Annotated Component\"", nullable=false)
	protected DataModelComponent annotatedComponent;

	public DataModelComponent getAnnotatedComponent() {
		return annotatedComponent;
	}

	
}
