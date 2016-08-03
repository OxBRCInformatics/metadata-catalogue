package ox.softeng.metadatacatalogue.domain.core;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.EnumerationType")
@Table(schema="\"Core\"", name="\"EnumerationType\"")
public class EnumerationType extends DataType {

	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="enumerationType")
	protected List<EnumerationValue> enumerationValues;

}
