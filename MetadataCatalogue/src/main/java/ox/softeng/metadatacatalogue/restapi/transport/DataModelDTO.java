package ox.softeng.metadatacatalogue.restapi.transport;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@XmlRootElement
@JsonInclude(Include.ALWAYS)

public class DataModelDTO extends CatalogueItemDTO {

	private String author;
	
	private String organization;
	
	private String type;

	private List<MetadataDTO> metadata;
	
	private List<CatalogueItemDTO> ownedDataTypes;
	
	private List<CatalogueItemDTO> childDataClasses;

	public List<CatalogueItemDTO> getChildDataClasses() {
		return childDataClasses;
	}

	public void setChildDataClasses(List<CatalogueItemDTO> childDataClasses) {
		this.childDataClasses = childDataClasses;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<CatalogueItemDTO> getOwnedDataTypes() {
		return ownedDataTypes;
	}

	public void setOwnedDataTypes(List<CatalogueItemDTO> ownedDataTypes) {
		this.ownedDataTypes = ownedDataTypes;
	}

	public List<MetadataDTO> getMetadata() {
		return metadata;
	}

	public void setMetadata(List<MetadataDTO> metadata) {
		this.metadata = metadata;
	}
	
}
