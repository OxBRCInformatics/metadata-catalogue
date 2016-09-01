package ox.softeng.metadatacatalogue.domain.core;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;


@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Metadata")
@Table(schema="\"Core\"", name="\"Metadata\"")

public class Metadata implements Serializable{
	
	public static final long serialVersionUID = 1L;

	@Id
	@Projection(name="datamodel.pageview.id")
	@Projection(name="metadata.basic")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	@Column(name = "id", unique = true)
	protected UUID id;

	@ManyToOne
	@JoinColumn(name="\"Catalogue Item\"", nullable=false)
	protected CatalogueItem belongsToCatalogueItem;
	
	@Column(length=10485760, name="\"Key\"")
	@Projection(name="metadata.basic")
	@Projection(name="datamodel.pageview.id")
	protected String key;
	
	@Column(length=10485760, name="\"Value\"")
	@Projection(name="metadata.basic")
	@Projection(name="datamodel.pageview.id")
	protected String value;

	public Metadata()
	{
		
	}
	
	public Metadata(CatalogueItem owner, String key, String value)
	{
		belongsToCatalogueItem = owner;
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public UUID getId() {
		return id;
	}

	public CatalogueItem getBelongsToCatalogueItem() {
		return belongsToCatalogueItem;
	}
	
	
}
