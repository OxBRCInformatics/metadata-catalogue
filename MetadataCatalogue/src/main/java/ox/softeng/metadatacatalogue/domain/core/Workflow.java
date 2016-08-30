package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Entity;
import javax.persistence.Table;

import ox.softeng.projector.annotations.Projectable;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Workflow")
@Table(schema="\"Core\"", name="\"Workflow\"")

public class Workflow extends DataModel {

	private static final long serialVersionUID = 1L;
}
