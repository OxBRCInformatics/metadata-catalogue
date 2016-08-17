package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ox.softeng.metadatacatalogue.restapi.transport.CatalogueItemLink;


@XmlRootElement
@JsonInclude(Include.ALWAYS)

public class DataTypeDTO extends DataModelComponentDTO {

	protected CatalogueItemLink belongsToModel;
	
	protected String type;

	public CatalogueItemLink getBelongsToModel() {
		return belongsToModel;
	}

	public void setBelongsToModel(CatalogueItemLink belongsToModel) {
		this.belongsToModel = belongsToModel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}
