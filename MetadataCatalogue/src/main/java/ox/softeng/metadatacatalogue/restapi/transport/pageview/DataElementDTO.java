package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ox.softeng.metadatacatalogue.restapi.transport.CatalogueItemLink;


@XmlRootElement
@JsonInclude(Include.ALWAYS)

public class DataElementDTO extends DataModelComponentDTO {

	protected CatalogueItemLink belongsToModel;

	private CatalogueItemLink parentDataClass;
	
	private DataTypeDTO dataType;
	
	private String path;
	
	private List<CatalogueItemLink> breadcrumbs;


	public CatalogueItemLink getBelongsToModel() {
		return belongsToModel;
	}

	public void setBelongsToModel(CatalogueItemLink belongsToModel) {
		this.belongsToModel = belongsToModel;
	}

	public CatalogueItemLink getParentDataClass() {
		return parentDataClass;
	}

	public void setParentDataClass(CatalogueItemLink parentDataClass) {
		this.parentDataClass = parentDataClass;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<CatalogueItemLink> getBreadcrumbs() {
		return breadcrumbs;
	}

	public void setBreadcrumbs(List<CatalogueItemLink> breadcrumbs) {
		this.breadcrumbs = breadcrumbs;
	}

	public DataTypeDTO getDataType() {
		return dataType;
	}

	public void setDataType(DataTypeDTO dataType) {
		this.dataType = dataType;
	}
}
