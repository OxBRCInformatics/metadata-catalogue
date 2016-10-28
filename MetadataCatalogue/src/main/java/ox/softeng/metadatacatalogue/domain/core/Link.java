package ox.softeng.metadatacatalogue.domain.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import ox.softeng.projector.annotations.Projectable;
import ox.softeng.projector.annotations.Projection;

@Projectable
@Entity(name="ox.softeng.metadatacatalogue.domain.core.Link")
@Table(schema="\"Core\"", name="\"Link\"")

public class Link extends Sharable {

	public static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="\"Source\"", nullable=false)
	protected DataModelComponent source;

	@Projection(name="datamodel.pageview.id", recurseProjection="datamodel.pageview.link")
	@Projection(name="dataclass.pageview.id", recurseProjection="datamodel.pageview.link")
	@Projection(name="dataelement.pageview.id", recurseProjection="datamodel.pageview.link")
	@Projection(name="datatype.pageview.id", recurseProjection="datamodel.pageview.link")
	@ManyToOne
	@JoinColumn(name="\"Target\"", nullable=false)
	protected DataModelComponent target;

	
	@Projection(name="datamodel.pageview.id")
	@Projection(name="dataclass.pageview.id")
	@Projection(name="dataelement.pageview.id")
	@Projection(name="datatype.pageview.id")
	@Column(name="\"Link Type\"")
	protected LinkType linkType;
	
	public Link()
	{
		
	}
	
	public Link(User createdBy, DataModelComponent source, DataModelComponent target, LinkType linkType)
	{
		super("", "", createdBy);
		this.source = source;
		this.target = target;
		this.linkType = linkType;
	}
	
	@JsonFormat(shape = Shape.OBJECT)
	public enum LinkType {
		SAME_AS,
		NOT_SAME_AS;
	}

	public DataModelComponent getSource() {
		return source;
	}

	public void setSource(DataModelComponent source) {
		this.source = source;
	}

	public DataModelComponent getTarget() {
		return target;
	}

	public void setTarget(DataModelComponent target) {
		this.target = target;
	}

	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}
	
}
