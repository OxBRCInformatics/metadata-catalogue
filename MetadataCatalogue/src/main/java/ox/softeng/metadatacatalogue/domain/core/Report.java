package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.Report")
@Table(schema="\"Core\"", name="\"Report\"")
public class Report extends DataModel {

	private static final long serialVersionUID = 1L;
}
