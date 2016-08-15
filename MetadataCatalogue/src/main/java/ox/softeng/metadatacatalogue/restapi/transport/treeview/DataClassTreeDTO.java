package ox.softeng.metadatacatalogue.restapi.transport.treeview;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ox.softeng.metadatacatalogue.restapi.transport.pageview.CatalogueItemDTO;


@XmlRootElement
@JsonInclude(Include.ALWAYS)

public class DataClassTreeDTO extends CatalogueItemDTO {

	
	private String type;
	
	private List<DataClassTreeDTO> childDataClasses;

	public List<DataClassTreeDTO> getChildDataClasses() {
		return childDataClasses;
	}

	public void setChildDataClasses(List<DataClassTreeDTO> childDataClasses) {
		this.childDataClasses = childDataClasses;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}