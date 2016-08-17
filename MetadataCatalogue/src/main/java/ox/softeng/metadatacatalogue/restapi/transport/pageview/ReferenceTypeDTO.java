package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@XmlRootElement
@JsonInclude(Include.ALWAYS)

public class ReferenceTypeDTO extends DataTypeDTO {
	

	protected CatalogueItemDTO referenceClass;

	public CatalogueItemDTO getReferenceClass() {
		return referenceClass;
	}

	public void setReferenceClass(CatalogueItemDTO referenceClass) {
		this.referenceClass = referenceClass;
	}
	
		
}
