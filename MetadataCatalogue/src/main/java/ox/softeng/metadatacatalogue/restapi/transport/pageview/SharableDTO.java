package ox.softeng.metadatacatalogue.restapi.transport.pageview;

import java.util.List;
import java.util.Set;


public class SharableDTO extends CatalogueItemDTO {

	protected Boolean readableByEveryone;

	protected Boolean writeableByEveryone;
	
	protected List<UserDTO> readableByUsers;
	
	protected Set<UserGroupDTO> readableByGroups;
	
	protected Set<UserDTO> writeableByUsers;
	
	protected Set<UserGroupDTO> writeableByGroups;

	public Boolean getReadableByEveryone() {
		return readableByEveryone;
	}

	public void setReadableByEveryone(Boolean readableByEveryone) {
		this.readableByEveryone = readableByEveryone;
	}

	public Boolean getWriteableByEveryone() {
		return writeableByEveryone;
	}

	public void setWriteableByEveryone(Boolean writeableByEveryone) {
		this.writeableByEveryone = writeableByEveryone;
	}

	public List<UserDTO> getReadableByUsers() {
		return readableByUsers;
	}

	public void setReadableByUsers(List<UserDTO> readableByUsers) {
		this.readableByUsers = readableByUsers;
	}

	public Set<UserGroupDTO> getReadableByGroups() {
		return readableByGroups;
	}

	public void setReadableByGroups(Set<UserGroupDTO> readableByGroups) {
		this.readableByGroups = readableByGroups;
	}

	public Set<UserDTO> getWriteableByUsers() {
		return writeableByUsers;
	}

	public void setWriteableByUsers(Set<UserDTO> writeableByUsers) {
		this.writeableByUsers = writeableByUsers;
	}

	public Set<UserGroupDTO> getWriteableByGroups() {
		return writeableByGroups;
	}

	public void setWriteableByGroups(Set<UserGroupDTO> writeableByGroups) {
		this.writeableByGroups = writeableByGroups;
	}
	
}
