package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@XmlRootElement
@JsonInclude(Include.ALWAYS)
public class CatalogueItemDTO {

	private String id;
	
	private String label;
	private String description;
	private String dtype;
	
	private String dateCreated;
	
	private String lastUpdated;
	
	private List<MetadataDTO> metadata;
		
	private UserDTO createdBy;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public List<MetadataDTO> getMetadata() {
		return metadata;
	}
	public void setMetadata(List<MetadataDTO> metadata) {
		this.metadata = metadata;
	}
	public UserDTO getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserDTO createdBy) {
		this.createdBy = createdBy;
	}
	
	
	
}
