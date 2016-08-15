package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ox.softeng.metadatacatalogue.restapi.transport.CatalogueItemLink;


@XmlRootElement
@JsonInclude(Include.ALWAYS)

public class DataModelDTO extends FinalisableDTO {

	private String author;
	
	private String organization;
	
	private String type;

	private List<MetadataDTO> metadata;
	
	private List<CatalogueItemLink> ownedDataTypes;
	
	private List<CatalogueItemLink> childDataClasses;

	public List<CatalogueItemLink> getChildDataClasses() {
		return childDataClasses;
	}

	public void setChildDataClasses(List<CatalogueItemLink> childDataClasses) {
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

	public List<CatalogueItemLink> getOwnedDataTypes() {
		return ownedDataTypes;
	}

	public void setOwnedDataTypes(List<CatalogueItemLink> ownedDataTypes) {
		this.ownedDataTypes = ownedDataTypes;
	}

	public List<MetadataDTO> getMetadata() {
		return metadata;
	}

	public void setMetadata(List<MetadataDTO> metadata) {
		this.metadata = metadata;
	}
	
}
