package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.Message")
@Table(schema="\"Core\"", name="\"Message\"")
public class Message extends DataModel {

	private static final long serialVersionUID = 1L;
}