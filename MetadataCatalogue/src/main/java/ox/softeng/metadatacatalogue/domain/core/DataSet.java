package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataSet")
@Table(schema="\"Core\"", name="\"DataSet\"")
public class DataSet extends DataModel {

	private static final long serialVersionUID = 1L;
}