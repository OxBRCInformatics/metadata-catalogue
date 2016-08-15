package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(Include.ALWAYS)
public class UserGroupDTO {

	protected String id;
	
	protected String groupName;

}
