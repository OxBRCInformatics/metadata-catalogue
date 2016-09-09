package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.PrimitiveType")
@Table(schema="\"Core\"", name="\"PrimitiveType\"")

public class PrimitiveType extends DataType {

	private static final long serialVersionUID = 1L;

	@Projection(name="datatype.pageview.id")
	@Projection(name="dataelement.pageview.id")
	@Projection(name="dataclass.pageview.dataelement")
	@Projection(name="datamodel.pageview.dataelement")
	@Projection(name="datatype.creation")
	@Column(name="\"Units\"")
	protected String units;
	
	public PrimitiveType()
	{
		
	}
	
	public PrimitiveType(String label, String description, User createdBy, String units, DataModel belongsToModel)
	{
		super(label, description, createdBy, belongsToModel);
		this.type = "PrimitiveType";
		this.units = units;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}


}
