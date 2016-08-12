package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity(name="ox.softeng.metadatacatalogue.domain.core.ReferenceType")
@Table(schema="\"Core\"", name="\"ReferenceType\"")
public class ReferenceType extends DataType {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="\"Referenced Class\"")
	protected DataClass referenceClass;

}
