package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Link")
@Table(schema="\"Core\"", name="\"Link\"")

public class Link extends Sharable {

	public static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="\"Source\"", nullable=false)
	protected DataModelComponent source;

	@ManyToOne
	@JoinColumn(name="\"Target\"", nullable=false)
	protected DataModelComponent target;

	public Link()
	{
		
	}
	
	public Link(String label, String description, User createdBy, DataModelComponent source, DataModelComponent target)
	{
		super(label, description, createdBy);
		this.source = source;
		this.target = target;
	}
	
}
