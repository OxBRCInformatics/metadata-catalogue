package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ox.softeng.metadatacatalogue.restapi.transport.CatalogueItemLink;


@XmlRootElement
@JsonInclude(Include.ALWAYS)

public class DataClassDTO extends DataModelComponentDTO {

	protected CatalogueItemLink belongsToModel;

	private CatalogueItemLink parentDataModel;

	private CatalogueItemLink parentDataClass;
	
	private String path;

	private List<CatalogueItemLink> childDataClasses;

	private List<CatalogueItemLink> childDataElements;

	private List<CatalogueItemLink> targetOfReferenceType;

	public CatalogueItemLink getBelongsToModel() {
		return belongsToModel;
	}

	public void setBelongsToModel(CatalogueItemLink belongsToModel) {
		this.belongsToModel = belongsToModel;
	}

	public CatalogueItemLink getParentDataModel() {
		return parentDataModel;
	}

	public void setParentDataModel(CatalogueItemLink parentDataModel) {
		this.parentDataModel = parentDataModel;
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

	public List<CatalogueItemLink> getChildDataClasses() {
		return childDataClasses;
	}

	public void setChildDataClasses(List<CatalogueItemLink> childDataClasses) {
		this.childDataClasses = childDataClasses;
	}

	public List<CatalogueItemLink> getChildDataElements() {
		return childDataElements;
	}

	public void setChildDataElements(List<CatalogueItemLink> childDataElements) {
		this.childDataElements = childDataElements;
	}

	public List<CatalogueItemLink> getTargetOfReferenceType() {
		return targetOfReferenceType;
	}

	public void setTargetOfReferenceType(List<CatalogueItemLink> targetOfReferenceType) {
		this.targetOfReferenceType = targetOfReferenceType;
	}
}
