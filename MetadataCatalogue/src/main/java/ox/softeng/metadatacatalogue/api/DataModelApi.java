package ox.softeng.metadatacatalogue.api;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.domain.core.DataType;
import ox.softeng.metadatacatalogue.domain.core.EnumerationType;
import ox.softeng.metadatacatalogue.domain.core.PrimitiveType;
import ox.softeng.metadatacatalogue.domain.core.ReferenceType;
import ox.softeng.metadatacatalogue.domain.core.User;
import ox.softeng.projector.Projector;

public class DataModelApi extends FinalisableApi {

	protected DataModelApi() {} // Private constructor as it makes no sense to instantiate this!
	
	public static DataClass newDataClass(ApiContext apiCtx, DataModel dm, String label, String description) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<DataClass>(){
            @Override
            public DataClass call(EntityManager em) {
            	try{
            		DataClass dc = new DataClass(label, description, apiCtx.getUser(), dm);
					dc = em.merge(dc);
					return dc;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static DataModel getById(ApiContext apiCtx, UUID uuid) throws Exception
	{
		return apiCtx.getById(DataModel.class, uuid);
	}

	public static List<DataModel> getAll(ApiContext apiCtx) throws Exception
	{
		return apiCtx.getAll(DataModel.class);
	}

	public static PrimitiveType newPrimitiveType(ApiContext apiCtx, DataModel dm, String label, String description, String units) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<PrimitiveType>(){
            @Override
            public PrimitiveType call(EntityManager em) {
            	try{
            		PrimitiveType pt = new PrimitiveType(label, description, apiCtx.getUser(), units, dm);
            		pt = em.merge(pt);
					return pt;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}
	
	public static ReferenceType newReferenceType(ApiContext apiCtx, DataModel dm, String label, String description, DataClass referenceClass) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<ReferenceType>(){
            @Override
            public ReferenceType call(EntityManager em) {
            	try{
            		ReferenceType rt = new ReferenceType(label, description, apiCtx.getUser(), referenceClass, dm);
            		rt = em.merge(rt);
					return rt;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	
	public static EnumerationType newEnumerationType(ApiContext apiCtx, DataModel dm, String label, String description) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<EnumerationType>(){
            @Override
            public EnumerationType call(EntityManager em) {
            	try{
            		EnumerationType et = new EnumerationType(label, description, apiCtx.getUser(), dm);
            		em.persist(et);
					return et;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}


	public static DataModel import0_1(ApiContext apiCtx, InputStream is) throws Exception
	{
		DataModel dm = apiCtx.executeTransaction(new EMCallable<DataModel>(){
			@Override
			public DataModel call(EntityManager em) {
				try{
					ObjectMapper objectMapper = new ObjectMapper();
					JsonNode jn = objectMapper.readTree(is);
					//assert("0.1".equalsIgnoreCase(jn.get("exportVersion").asText()));
					String label = jn.get("dataModel").get("label").asText();
					System.out.println("Importing: " + label);
			
					JsonNode users = jn.get("users");
					if(users.isArray())
					{
						for(JsonNode user : users)
						{
							User newUser = objectMapper.readValue(user.traverse(), User.class);
					            		
		            		User existingUser = em.find(User.class, newUser.getEmailAddress());
		            		if(existingUser == null)
		            		{
		            			em.merge(newUser);
		            		}
						}
					}
					DataModel dm = objectMapper.readValue(jn.get("dataModel").traverse(), DataModel.class);
					List<DataClass> dcs = dm.getChildDataClasses();
					dm.setChildDataClasses(null);
					em.merge(dm);
					
					JsonNode dataTypes = jn.get("dataTypes");
					if(dataTypes.isArray())
					{
						for(JsonNode dataType : dataTypes)
						{
							DataType newDataType = objectMapper.readValue(dataType.traverse(), DataType.class);

							DataType existingDataType = em.find(DataType.class, newDataType.getId());
		            		if(existingDataType == null)
		            		{
		            			newDataType.setBelongsToModel(dm);
		            			em.merge(newDataType);
		            		}
						}
					}
					dm.setChildDataClasses(dcs);
					dm = em.merge(dm);
					return dm;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
			}
		});
		return dm;
				
	}

	public static JsonNode export0_1(ApiContext apiCtx, UUID uuid) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<JsonNode>(){
            @Override
            public JsonNode call(EntityManager em) {
            	try{
            		ObjectNode on = ApiContext.jsonFactory.objectNode();
            		on.put("exportVersion", "0.1");
            		
            		DataModel dm = em.find(DataModel.class, uuid);
            		if(dm==null)
            		{
            			System.err.println("No data model with this ID: " + uuid);
            			return null;
            		}
            		Set<DataType> dts = dm.getOwnedDataTypes();
            		JsonNode dtsNode = Projector.project(dts, "datamodel.export.0.1.datatype");
            		JsonNode dmNode = Projector.project(dm, "datamodel.export.0.1.datamodel");

            		Set<User> users = dm.findAllUsers();
            		JsonNode userNode = Projector.project(users, "datamodel.export.0.1.user");

            		on.set("dataTypes", dtsNode);
            		on.put("dataModelType", dm.getType());
            		on.set("dataModel", dmNode);
            		on.set("users", userNode);
            		return on;
            	}
				catch(Exception e)
				{
					e.printStackTrace();
					System.err.println("Exception in export: " + uuid);
					return null;
				}
            }
		});
	}
	
	 

	
}
