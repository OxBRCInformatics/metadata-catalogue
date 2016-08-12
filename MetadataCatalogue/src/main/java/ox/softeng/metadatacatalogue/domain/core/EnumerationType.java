package ox.softeng.metadatacatalogue.domain.core;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.EnumerationType")
@Table(schema="\"Core\"", name="\"EnumerationType\"")
public class EnumerationType extends DataType {

	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="enumerationType")
	protected List<EnumerationValue> enumerationValues;

	protected EnumerationType()
	{
		
	}
	
	public EnumerationType(String label, String description, User createdBy, DataModel belongsToModel)
	{
		super(label, description, createdBy, belongsToModel);
		this.type = "EnumerationType";
		enumerationValues = new ArrayList<EnumerationValue>();
	}
	
	public EnumerationValue addEnumerationValue(String key, String value, User createdBy)
	{
		EnumerationValue ev = new EnumerationValue(key, value, createdBy, this);
		return ev;
	}
	
	
}
