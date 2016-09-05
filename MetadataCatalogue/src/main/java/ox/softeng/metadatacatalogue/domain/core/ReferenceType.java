package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.ReferenceType")
@Table(schema="\"Core\"", name="\"ReferenceType\"")

public class ReferenceType extends DataType {

	private static final long serialVersionUID = 1L;

	@Projection(name="dataelement.pageview.id")
	@ManyToOne
	@JoinColumn(name="\"Referenced Class\"")
	protected DataClass referenceClass;

	public DataClass getReferenceClass() {
		return referenceClass;
	}

	public void setReferenceClass(DataClass referenceClass) {
		this.referenceClass = referenceClass;
	}

}
