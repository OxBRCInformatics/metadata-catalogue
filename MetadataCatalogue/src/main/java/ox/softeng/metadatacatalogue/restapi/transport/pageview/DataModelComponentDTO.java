package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ox.softeng.metadatacatalogue.restapi.transport.CatalogueItemLink;

@XmlRootElement
@JsonInclude(Include.ALWAYS)
public class DataModelComponentDTO extends SharableDTO {

	protected Set<CatalogueItemLink> classifiers;

	protected List<CatalogueItemLink> annotations;

	protected List<CatalogueItemLink> sourceForLinks;

	protected List<CatalogueItemLink> targetForLinks;

	public Set<CatalogueItemLink> getClassifiers() {
		return classifiers;
	}

	public void setClassifiers(Set<CatalogueItemLink> classifiers) {
		this.classifiers = classifiers;
	}

	public List<CatalogueItemLink> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<CatalogueItemLink> annotations) {
		this.annotations = annotations;
	}

	public List<CatalogueItemLink> getSourceForLinks() {
		return sourceForLinks;
	}

	public void setSourceForLinks(List<CatalogueItemLink> sourceForLinks) {
		this.sourceForLinks = sourceForLinks;
	}

	public List<CatalogueItemLink> getTargetForLinks() {
		return targetForLinks;
	}

	public void setTargetForLinks(List<CatalogueItemLink> targetForLinks) {
		this.targetForLinks = targetForLinks;
	}
	
}
