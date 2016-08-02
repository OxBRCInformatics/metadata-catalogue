package ox.softeng.metadatacatalogue.domain.core;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="ox.softeng.metadatacatalogue.domain.core.CatalogueItem")
@DiscriminatorColumn(name = "\"DType\"")
@Table(schema="\"Core\"", name="\"CatalogueItem\"")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class CatalogueItem implements Serializable {

	public static final long serialVersionUID = 1L;
		
	@Id
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	@Column(name = "id", unique = true)
	protected UUID id;

	// The name of the concrete child class
	@Column(name="\"DType\"", insertable = false, updatable = false) 
	private String dtype;


	@Column(length=10485760, name="\"Label\"")
	protected String label;
	
	@Column(length=10485760, name="\"Description\"")
	protected String description;
	
	@Column(name="\"Date/Time Created\"")
	protected OffsetDateTime dateCreated;
	
	@Column(name="\"Date/Time Last Updated\"")
	protected OffsetDateTime lastUpdated;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "belongsToCatalogueItem", cascade = CascadeType.ALL)
	protected List<Metadata> metadata;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"Created By\"", nullable=false)
	protected User createdBy;

	
}
