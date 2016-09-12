package ox.softeng.metadatacatalogue.domain.core;

import java.util.UUID;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable

public class Breadcrumb {

	@Projection(always=true)
	private UUID id;
	
	@Projection(always=true)
	private String label;

	@Projection(always=true)
	private String description;

	@Projection(always=true)
	private String dtype;
	
	public Breadcrumb()
	{
		
	}
	
	public Breadcrumb(UUID id, String label, String description, String dtype)
	{
		this.id = id;
		this.label = label;
		this.description = description;
		setDtype(dtype);
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDtype() {
		return dtype;
	}
	public void setDtype(String dtype) {
		this.dtype = dtype.substring(dtype.lastIndexOf(".") + 1);
	}
	
	
	
}
