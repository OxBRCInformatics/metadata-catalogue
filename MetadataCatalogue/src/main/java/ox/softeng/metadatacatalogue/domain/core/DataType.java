package ox.softeng.metadatacatalogue.domain.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataType")
@Table(schema="\"Core\"", name="\"DataType\"")

public abstract class DataType extends DataModelComponent {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="\"Belongs To Model\"")
	protected DataModel belongsToModel;

	
	@OneToMany(mappedBy="dataType")
	protected Set<DataElement> forDataElements;
	

	@Column(name="\"Type\"")
	protected String type;

	protected DataType()
	{
		
	}
	
	public DataType(String label, String description, User createdBy, DataModel belongsToModel)
	{
		super(label, description, createdBy);
		this.belongsToModel = belongsToModel;
		this.forDataElements = new HashSet<DataElement>();
	}
}
