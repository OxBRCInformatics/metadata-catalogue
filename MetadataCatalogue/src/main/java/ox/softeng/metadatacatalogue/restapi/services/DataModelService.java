package ox.softeng.metadatacatalogue.restapi.services;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import ox.softeng.metadatacatalogue.api.DataClassApi;
import ox.softeng.metadatacatalogue.api.DataModelApi;
import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.PrimitiveType;
import ox.softeng.metadatacatalogue.domain.core.ReferenceType;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;
import ox.softeng.metadatacatalogue.restapi.transport.SearchParamsDTO;


@Path("/datamodel")
public class DataModelService extends FinalisableService{

	public static Class<?> type = DataModel.class;
	
	@Path("/pageView/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public JsonNode getDataModel(@PathParam("id") UUID dataModelId) throws Exception
	{
		return getApiContext().getByIdMap(DataModel.class, "datamodel.pageview.id", dataModelId);
		
	}

	@Path("/all")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public ArrayNode getAllDataModels() throws Exception
	{
		ArrayNode dms = getApiContext().getAllMap(DataModel.class, "datamodel.pageview.id");
		return dms;
	}
	
	

	@Path("/tree")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public ArrayNode getDataModelTrees() throws Exception
	{
		return getApiContext().getAllMap(DataModel.class, "datamodel.treeview");
	}

	@Path("/search")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public ArrayNode searchDataModel(SearchParamsDTO searchParams) throws Exception
	{
		
		return getApiContext().searchMap(
				DataModel.class, "datamodel.pageview.id", 
				searchParams.getSearchTerm(), searchParams.getOffset(), searchParams.getLimit());
	}


	@Path("/newChildDataClass/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO newChildDataClass(@PathParam("id") UUID dataModelId, DataClass dc) throws Exception
	{		
		DataModel dm = getApiContext().getById(DataModel.class, dataModelId);

		DataClass ret = DataModelApi.newDataClass(getApiContext(), dm, dc.getLabel(), dc.getDescription());
		return createSuccessfulResponse(ret, "dataclass.pageview.id");
	}
	
	@Path("/newPrimitiveDataType/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO newPrimitiveDataType(@PathParam("id") UUID dataModelId, PrimitiveType pt) throws Exception
	{		
		DataModel dm = getApiContext().getById(DataModel.class, dataModelId);

		PrimitiveType ret = DataModelApi.newPrimitiveType(getApiContext(), dm, pt.getLabel(), pt.getDescription(), pt.getUnits());
		return createSuccessfulResponse(ret, "datatype.pageview.id");
	}

	@Path("/newReferenceDataType/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO newReferenceDataType(@PathParam("id") UUID dataModelId, ReferenceType rt) throws Exception
	{		
		DataModel dm = getApiContext().getById(DataModel.class, dataModelId);
		DataClass referenceClass = getApiContext().getById(DataClass.class, rt.getReferenceClass().getId());
		ReferenceType ret = DataModelApi.newReferenceType(getApiContext(), dm, rt.getLabel(), rt.getDescription(), referenceClass);
		return createSuccessfulResponse(ret, "datatype.pageview.id");
	}

	


}
