package ox.softeng.metadatacatalogue.restapi.services;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.api.DataClassApi;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataElement;
import ox.softeng.metadatacatalogue.domain.core.DataType;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;


@Path("/dataclass")
public class DataClassService extends DataModelComponentService{


	@Path("/pageView/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public JsonNode getDataClass(@PathParam("id") UUID dataClassId) throws Exception
	{
		
		return getApiContext().getByIdMap(DataClass.class, "dataclass.pageview.id", dataClassId);
		//return getApiContext().getByIdMap(DataClass.class, DataClassDTO.class, dataClassId);
		
	}
/*
	@Path("/search")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public ArrayNode searchDataClass(SearchParamsDTO searchParams) throws Exception
	{
		
		return getApiContext().searchMap(
				DataClass.class, "dataclass.pageview.id", 
				searchParams.getSearchTerm(), searchParams.getOffset(), searchParams.getLimit());
	}
*/
	
	@Path("/newChildDataClass/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO newChildDataClass(@PathParam("id") UUID dataClassId, DataClass dc) throws Exception
	{		
		DataClass parentDC = getApiContext().getById(DataClass.class, dataClassId);

		DataClass ret = DataClassApi.newChildDataClass(getApiContext(), parentDC, dc.getLabel(), dc.getDescription());
		ret = (DataClass) maybeAddMetadata(ret, dc);
		return createSuccessfulResponse(ret, "dataclass.pageview.id");
	}

	@Path("/newChildDataElement/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO newChildDataElement(@PathParam("id") UUID dataClassId, NewDataElementDTO de) throws Exception
	{		
		DataClass parentDC = getApiContext().getById(DataClass.class, dataClassId);
		DataType dataType = getApiContext().getById(DataType.class, de.dataType.id);
		
		DataElement ret = DataClassApi.newChildDataElement(getApiContext(), parentDC, de.getLabel(), de.getDescription(), dataType);
		ret = (DataElement) maybeAddMetadata(ret, de);
		return createSuccessfulResponse(ret, "dataelement.pageview.id");
	}

	@JsonAutoDetect(creatorVisibility = Visibility.NONE, fieldVisibility = Visibility.ANY)
	public static class NewDataElementDTO extends CatalogueItem {
		private static final long serialVersionUID = 1L;
		public DataTypeDTO dataType;
		public static class DataTypeDTO
		{
			public UUID id;
		}
		
	}


}
