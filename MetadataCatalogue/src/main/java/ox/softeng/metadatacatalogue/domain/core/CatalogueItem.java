package ox.softeng.metadatacatalogue.domain.core;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.CatalogueItem")
@DiscriminatorColumn(name = "\"DType\"")
@Table(schema="\"Core\"", name="\"CatalogueItem\"")
@Inheritance(strategy=InheritanceType.JOINED)

public abstract class CatalogueItem implements Serializable {

	public static final long serialVersionUID = 1L;
		
	@Id
	@Projection(always=true)
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	@Column(name = "id", unique = true)
	protected UUID id;

	// The name of the concrete child class
	@Projection(always=true)
	@Column(name="\"DType\"", insertable = false, updatable = false)
	private String dtype;


	@Projection(always=true)
	@Column(length=10485760, name="\"Label\"")
	protected String label;
	
	@Projection(always=true)
	@Column(length=10485760, name="\"Description\"")
	protected String description;
	
	@Projection(name="datamodel.pageview.id")
	@Projection(name="dataclass.pageview.id")
	@Column(name="\"Date/Time Created\"")
	protected OffsetDateTime dateCreated;
	
	@Projection(name="datamodel.pageview.id")
	@Projection(name="dataclass.pageview.id")
	@Column(name="\"Date/Time Last Updated\"")
	protected OffsetDateTime lastUpdated;
	
	@Projection(name="datamodel.pageview.id")
	@Projection(name="dataclass.pageview.id")
	@OneToMany(mappedBy = "belongsToCatalogueItem", cascade = CascadeType.ALL)
	protected List<Metadata> metadata;
		
	@Projection(name="datamodel.pageview.id")
	@Projection(name="dataclass.pageview.id")
	@ManyToOne
	@JoinColumn(name="\"Created By\"", nullable=false)
	protected User createdBy;

	public CatalogueItem()
	{
		//this.metadata = new ArrayList<Metadata>();
	}
	
	public CatalogueItem(String label, String description, User createdBy)
	{
		this.label = label;
		this.description = description;
		this.dateCreated = OffsetDateTime.now();
		this.lastUpdated = OffsetDateTime.now();
		this.metadata = new ArrayList<Metadata>();
		this.createdBy = createdBy;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getId() {
		return id;
	}

	public String getDtype() {
		if(dtype == null)
		{
			return null;
		}
		return dtype.substring(dtype.lastIndexOf(".") + 1);
	}

	public OffsetDateTime getDateCreated() {
		return dateCreated;
	}

	public OffsetDateTime getLastUpdated() {
		return lastUpdated;
	}

	public List<Metadata> getMetadata() {
		return metadata;
	}

	public User getCreatedBy() {
		return createdBy;
	}
	
	@PrePersist
	@PreUpdate
	protected void onUpdate() {
		lastUpdated = OffsetDateTime.now();
	}
	
	public Metadata addMetadata(String key, String value)
	{
		Metadata md = new Metadata(this, key, value);
		//metadata.add(md);
		return md;
	}

	
}
