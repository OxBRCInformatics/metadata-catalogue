package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Database")
@Table(schema="\"Core\"", name="\"Database\"")

public class Database extends DataModel {

	private static final long serialVersionUID = 1L;

	@Column(name="\"Dialect\"")
	private String dialect;

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	
	
}
