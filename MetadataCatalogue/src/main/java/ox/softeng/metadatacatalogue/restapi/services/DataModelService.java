package ox.softeng.metadatacatalogue.restapi.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import ox.softeng.metadatacatalogue.api.DataModelApi;
import ox.softeng.metadatacatalogue.api.EnumerationTypeApi;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.domain.core.EnumerationType;
import ox.softeng.metadatacatalogue.domain.core.EnumerationValue;
import ox.softeng.metadatacatalogue.domain.core.PrimitiveType;
import ox.softeng.metadatacatalogue.domain.core.ReferenceType;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;


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

/*	@Path("/search")
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
*/

	@Path("/newChildDataClass/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO newChildDataClass(@PathParam("id") UUID dataModelId, DataClass dc) throws Exception
	{		
		DataModel dm = getApiContext().getById(DataModel.class, dataModelId);

		DataClass ret = DataModelApi.newDataClass(getApiContext(), dm, dc.getLabel(), dc.getDescription());
		ret = (DataClass) maybeAddMetadata(ret, dc);
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
		ret = (PrimitiveType) maybeAddMetadata(ret, pt);

		return createSuccessfulResponse(ret, "datatype.creation");
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
		ret = (ReferenceType) maybeAddMetadata(ret, rt);
		System.err.println("Returned DType: " + ret.getDtype());
		return createSuccessfulResponse(ret, "datatype.creation");
	}

	@Path("/newEnumerationDataType/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO newEnumerationDataType(@PathParam("id") UUID dataModelId, EnumerationType et) throws Exception
	{		
		DataModel dm = getApiContext().getById(DataModel.class, dataModelId);
		EnumerationType ret = DataModelApi.newEnumerationType(getApiContext(), dm, et.getLabel(), et.getDescription());
		if(et.getEnumerationValues() != null)
		{
			for(EnumerationValue ev : et.getEnumerationValues())
			{
				EnumerationTypeApi.newEnumerationValue(getApiContext(), ret, ev.getKey(), ev.getValue());
			}
			ret = getApiContext().refresh(ret);
		}

		ret = (EnumerationType) maybeAddMetadata(ret, et);
		return createSuccessfulResponse(ret, "datatype.creation");
	}

	@Path("/export/0.1/{id}")
	@GET
	@Produces({"application/mc"})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public Response exportModel(@PathParam("id") UUID dataModelId) throws Exception
	{
	    StreamingOutput stream = new StreamingOutput() {
	        public void write(OutputStream output) throws IOException, WebApplicationException {
	            try {
	            	JsonNode jn = DataModelApi.export0_1(getApiContext(), dataModelId);
	            	System.out.println(jn);
	            	if(jn == null)
	            	{
	            		output.write("No Data Model with this identifier".getBytes());
	            	}
	            	else
	            	{
	            		output.write(jn.toString().getBytes());
	            	}
	            	//output.flush();
	            } catch (Exception e) {
	            	e.printStackTrace();
	                throw new WebApplicationException(e);
	            }
	        }
	    };
	    
	    return Response.ok(stream).header("content-disposition","attachment; filename = dataModelExport.json").build();
	}

	@POST
	@Path("/import/0.1")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response importModel(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception 
	{

		DataModelApi.import0_1(getApiContext(), uploadedInputStream);
		
		return Response.status(200).build();

	}

	


}
