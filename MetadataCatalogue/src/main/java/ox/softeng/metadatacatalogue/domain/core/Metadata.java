package ox.softeng.metadatacatalogue.domain.core;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity(name="ox.softeng.metadatacatalogue.domain.core.Metadata")
@Table(schema="\"Core\"", name="\"Metadata\"")
public class Metadata implements Serializable{
	
	public static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	@Column(name = "id", unique = true)
	protected UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"Catalogue Item\"", nullable=false)
	protected CatalogueItem belongsToCatalogueItem;
	
	@Column(length=10485760, name="\"Key\"")
	protected String key;
	
	@Column(length=10485760, name="\"Value\"")
	protected String value;

	
}
