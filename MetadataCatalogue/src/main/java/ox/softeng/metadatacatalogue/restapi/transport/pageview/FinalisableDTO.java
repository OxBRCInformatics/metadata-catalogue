package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(Include.ALWAYS)
public class FinalisableDTO extends DataModelComponentDTO {

	protected Boolean finalised;
	
	protected String releaseLabel;

	public Boolean getFinalised() {
		return finalised;
	}

	public void setFinalised(Boolean finalised) {
		this.finalised = finalised;
	}

	public String getReleaseLabel() {
		return releaseLabel;
	}

	public void setReleaseLabel(String releaseLabel) {
		this.releaseLabel = releaseLabel;
	}
	
}
