package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@XmlRootElement
@JsonInclude(Include.ALWAYS)

public class EnumerationTypeDTO extends DataTypeDTO {
	

	protected List<EnumerationValueDTO> enumerationValues;

	public List<EnumerationValueDTO> getEnumerationValues() {
		return enumerationValues;
	}

	public void setEnumerationValues(List<EnumerationValueDTO> enumerationValues) {
		this.enumerationValues = enumerationValues;
	}
	
}
