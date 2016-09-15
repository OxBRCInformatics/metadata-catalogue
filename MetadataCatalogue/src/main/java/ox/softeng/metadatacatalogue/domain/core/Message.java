package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Entity;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Message")
@Table(schema="\"Core\"", name="\"Message\"")

public class Message extends DataModel {

	private static final long serialVersionUID = 1L;
	
	public Message() 
	{
		
	}
	
	public Message(String label, String description, User createdBy, String author, String organization)
	{
		super(label, description, createdBy, author, organization, "Message");
	}

}
