package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Database")
@Table(schema="\"Core\"", name="\"Database\"")

public class Database extends DataModel {

	private static final long serialVersionUID = 1L;

	@Projection(name="datamodel.pageview.id")
	@Projection(name="datamodel.export.0.1.datamodel")
	@Column(name="\"Dialect\"")
	private String dialect;

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	
	public Database()
	{
		
	}

	public Database(String label, String description, User createdBy, String author, String dialect, String organization)
	{
		super(label, description, createdBy, author, organization, "DataSet");
		this.dialect = dialect;
	}

	
}
