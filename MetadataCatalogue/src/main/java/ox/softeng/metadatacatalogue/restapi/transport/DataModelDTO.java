package ox.softeng.metadatacatalogue.restapi.transport;

import java.util.List;


public class DataModelDTO extends CatalogueItemDTO {

	private List<CatalogueItemDTO> childDataClasses;

	public List<CatalogueItemDTO> getChildDataClasses() {
		return childDataClasses;
	}

	public void setChildDataClasses(List<CatalogueItemDTO> childDataClasses) {
		this.childDataClasses = childDataClasses;
	}
	
}
