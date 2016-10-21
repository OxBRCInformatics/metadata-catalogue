package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Finalisable")
@Table(schema="\"Core\"", name="\"Finalisable\"")

public class Finalisable extends DataModelComponent {

	private static final long serialVersionUID = 1L;
	
	@Column(name="\"Finalised\"")
	@Projection(name="datamodel.export.0.1.datamodel")
	protected Boolean finalised;
	
	@Column(name="\"Release Label\"")
	@Projection(name="datamodel.export.0.1.datamodel")
	protected String releaseLabel;

	public Finalisable()
	{
		
	}
	
	public Finalisable(String label, String description, User createdBy)
	{
		super(label, description, createdBy);
		finalised = false;
	}

	public Boolean getFinalised() {
		return finalised;
	}

	public void setFinalised(Boolean finalised) {
		this.finalised = finalised;
	}

	public String getReleaseLabel() {
		return releaseLabel;
	}

	public void setReleaseLabel(String releaseLabel) {
		this.releaseLabel = releaseLabel;
	}

	
	
}
