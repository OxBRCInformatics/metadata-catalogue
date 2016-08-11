package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.PrimitiveType")
@Table(schema="\"Core\"", name="\"PrimitiveType\"")
public class PrimitiveType extends DataType {

	private static final long serialVersionUID = 1L;

	@Column(name="\"Units\"")
	protected String units;
	
	protected PrimitiveType()
	{
		
	}
	
	public PrimitiveType(String label, String description, User createdBy, String units, DataModel belongsToModel)
	{
		super(label, description, createdBy, belongsToModel);
		this.type = "PrimitiveType";
		this.units = units;
	}


}
