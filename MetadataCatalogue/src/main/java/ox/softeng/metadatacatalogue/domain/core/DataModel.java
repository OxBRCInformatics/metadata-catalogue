package ox.softeng.metadatacatalogue.domain.core;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity(name="ox.softeng.metadatacatalogue.domain.core.DataModel")
@Table(schema="\"Core\"", name="\"DataModel\"")

public abstract class DataModel extends Finalisable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="\"Author\"")
	private String author;
	
	@Column(name="\"Organization\"")
	private String organization;
	
	@Column(name="\"Type\"")
	private String type;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable( name="\"DataModel_ImportsFrom\"", schema="\"Core\"",
			joinColumns = { @JoinColumn (name="\"DataModel Id\"") },
			inverseJoinColumns = { @JoinColumn (name="\"Imported DataModel Id\"") })
	private Set<DataModel> importsFrom;

	@ManyToMany (mappedBy = "importsFrom")
	private Set<DataModel> importedBy;

	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parentDataModel")
	private List<DataClass> childDataClasses;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "belongsToModel")
	private Set<DataClass> ownedDataClasses;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "belongsToModel")
	private Set<DataElement> ownedDataElements;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "belongsToModel")
	private Set<DataType> ownedDataTypes;

}
