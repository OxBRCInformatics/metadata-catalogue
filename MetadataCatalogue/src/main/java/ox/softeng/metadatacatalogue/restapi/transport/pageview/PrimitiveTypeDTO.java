package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@XmlRootElement
@JsonInclude(Include.ALWAYS)

public class PrimitiveTypeDTO extends DataTypeDTO {
	

	protected String units;

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
}
