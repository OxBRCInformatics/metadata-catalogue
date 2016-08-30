package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Entity;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataStandard")
@Table(schema="\"Core\"", name="\"DataStandard\"")

public class DataStandard extends DataModel {

	private static final long serialVersionUID = 1L;
}
