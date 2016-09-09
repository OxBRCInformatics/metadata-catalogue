package ox.softeng.metadatacatalogue.restapi.services;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.api.CatalogueItemApi;
import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.Metadata;
import ox.softeng.metadatacatalogue.domain.core.User;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

public class BasicCatalogueService {

	
	@Context HttpServletRequest request;
	@Context SecurityContext securityContext;

	@Context
    HttpServletRequest webRequest;

	public static Class<?> type;

	
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
		ApiContext apiCtx = (ApiContext) request.getSession().getAttribute("apiContext");
		if(apiCtx != null)
		{
			return apiCtx;
		}
		else {
			return (ApiContext) webRequest.getServletContext().getAttribute("masterApiContext");
		}
		
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
	
	CatalogueItem maybeAddMetadata(CatalogueItem newObject, CatalogueItem param) throws Exception
	{
		if(param.getMetadata() == null)
		{
			return newObject;
		}
		else
		{
			for(Metadata md : param.getMetadata())
			{
				CatalogueItemApi.addMetadata(getApiContext(), newObject, md.getKey(), md.getValue());
			}
			newObject = getApiContext().refresh(newObject);
			return newObject;
		}
	}

}
