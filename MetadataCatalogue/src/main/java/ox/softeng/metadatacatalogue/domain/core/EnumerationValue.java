package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.EnumerationValue")
@Table(schema="\"Core\"", name="\"EnumerationValue\"")

public class EnumerationValue extends DataModelComponent {

	private static final long serialVersionUID = 1L;

	@Projection(always=true)
	@Column(length=10485760, name="\"Key\"")
	protected String key;

	@Projection(always=true)
	@Column(length=10485760, name="\"Value\"")
	protected String value;

	
	@ManyToOne
	@JoinColumn(name="\"Enumeration Type\"")
	protected EnumerationType enumerationType;
	
	protected EnumerationValue()
	{
		
	}
	
	public EnumerationValue(String key, String value, User createdBy, EnumerationType enumerationType)
	{
		super("", "", createdBy);
		this.key = key;
		this.value = value;
		this.enumerationType = enumerationType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public EnumerationType getEnumerationType() {
		return enumerationType;
	}

	public void setEnumerationType(EnumerationType enumerationType) {
		this.enumerationType = enumerationType;
	}
	
	
}
