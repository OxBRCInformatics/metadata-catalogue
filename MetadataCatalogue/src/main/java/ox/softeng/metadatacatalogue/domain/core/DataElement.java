package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataElement")
@Table(schema="\"Core\"", name="\"DataElement\"")
public class DataElement extends DataModelComponent {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="\"Belongs To Model\"")
	protected DataModel belongsToModel;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="\"DataType\"")
	protected DataType dataType;
	
	@Column(name="\"Path\"")
	protected String path;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"Parent Class\"")
	private DataClass parentDataClass;

	
}
