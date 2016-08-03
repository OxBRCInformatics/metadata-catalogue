package ox.softeng.metadatacatalogue.domain.core;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataType")
@Table(schema="\"Core\"", name="\"DataType\"")
public abstract class DataType extends DataModelComponent {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"Belongs To Model\"")
	protected DataModel belongsToModel;

	
	@OneToMany(mappedBy="dataType")
	protected List<DataElement> forDataElements;
	
	@Column(name="\"Type\"")
	protected String type;

}
