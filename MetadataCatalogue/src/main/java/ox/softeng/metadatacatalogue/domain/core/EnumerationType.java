package ox.softeng.metadatacatalogue.domain.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.EnumerationType")
@Table(schema="\"Core\"", name="\"EnumerationType\"")

public class EnumerationType extends DataType {

	private static final long serialVersionUID = 1L;

	@Projection(name="datatype.pageview.id")
	@Projection(name="dataelement.pageview.id")
	@Projection(name="dataclass.pageview.dataelement")
	@Projection(name="datamodel.pageview.dataelement")
	@Projection(name="datatype.creation")
	@Projection(name="datamodel.export.0.1.datatype")
	@OneToMany(cascade = CascadeType.ALL, mappedBy="enumerationType")
	@JsonManagedReference(value="enumerationType")
	protected List<EnumerationValue> enumerationValues;

	public EnumerationType()
	{
		
	}
	
	public EnumerationType(String label, String description, User createdBy, DataModel belongsToModel)
	{
		super(label, description, createdBy, belongsToModel);
		this.type = "EnumerationType";
		enumerationValues = new ArrayList<EnumerationValue>();
	}
	
	public Set<User> findAllUsers()
	{
		HashSet<User> users = new HashSet<User>();
		users.add(this.createdBy);
		for(EnumerationValue ev : enumerationValues)
		{
			users.add(ev.createdBy);
		}
		return users;

	}

	
	public EnumerationValue addEnumerationValue(String key, String value, User createdBy)
	{
		EnumerationValue ev = new EnumerationValue(key, value, createdBy, this);
		return ev;
	}

	public List<EnumerationValue> getEnumerationValues() {
		return enumerationValues;
	}

	public void setEnumerationValues(List<EnumerationValue> enumerationValues) {
		this.enumerationValues = enumerationValues;
	}
	
	
}
