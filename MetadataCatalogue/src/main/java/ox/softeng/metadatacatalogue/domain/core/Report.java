package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Entity;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Report")
@Table(schema="\"Core\"", name="\"Report\"")

public class Report extends DataModel {

	private static final long serialVersionUID = 1L;
	
	public Report() 
	{
		
	}
	
	public Report(String label, String description, User createdBy, String author, String organization)
	{
		super(label, description, createdBy, author, organization, "Report");
	}

}
