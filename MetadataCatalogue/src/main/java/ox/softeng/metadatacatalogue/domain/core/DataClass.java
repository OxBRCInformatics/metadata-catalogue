package ox.softeng.metadatacatalogue.domain.core;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataClass")
@Table(schema="\"Core\"", name="\"DataClass\"")
public class DataClass extends DataModelComponent {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"Belongs To Model\"")
	protected DataModel belongsToModel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"Parent Model\"")
	private DataModel parentDataModel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"Parent Class\"")
	private DataClass parentDataClass;
	
	@Column(name="\"Path\"")
	private String path;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="parentDataClass")
	private List<DataClass> childDataClasses;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="parentDataClass")
	private List<DataElement> childDataElements;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="referenceClass")
	private List<ReferenceType> targetOfReferenceType;
}
