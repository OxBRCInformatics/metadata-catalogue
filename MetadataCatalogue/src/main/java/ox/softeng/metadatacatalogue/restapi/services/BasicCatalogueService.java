package ox.softeng.metadatacatalogue.restapi.services;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.User;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

public class BasicCatalogueService {

	
	@Context HttpServletRequest request;
	@Context SecurityContext securityContext;

	public static Class<?> type = CatalogueItem.class;

	
	protected UUID getUserId()
	{
		if(securityContext.getUserPrincipal() != null)
		{
			return ((User) securityContext.getUserPrincipal()).getId();
		}
		else return null;
	}
	
	protected ApiContext getApiContext()
	{
		
		return (ApiContext) request.getSession().getAttribute("apiContext");
	}

	ResponseDTO createSuccessfulResponse(JsonNode obj, String type)
	{
		ResponseDTO response = new ResponseDTO();
		response.setSuccess(true);
		response.setErrorMessages(new ArrayList<String>());
		response.setReturnObject(obj);
		response.setReturnObjectType(type);
		return response;
	}

	ResponseDTO createSuccessfulResponse(Object obj, String projectionName) throws Exception
	{
		ResponseDTO response = new ResponseDTO();
		response.setSuccess(true);
		response.setErrorMessages(new ArrayList<String>());
		response.setReturnObject(getApiContext().project(obj, projectionName));
		response.setReturnObjectType(obj.getClass().getName());
		return response;
	}

}
