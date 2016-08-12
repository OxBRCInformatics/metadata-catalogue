package ox.softeng.metadatacatalogue.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;


import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.domain.core.EnumerationType;
import ox.softeng.metadatacatalogue.domain.core.PrimitiveType;

public class DataModelApi extends CatalogueItemApi {

	public static DataClass newDataClass(ApiContext apiCtx, DataModel dm, String label, String description) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<DataClass>(){
            @Override
            public DataClass call(EntityManager em) {
            	try{
            		DataClass dc = new DataClass(label, description, apiCtx.getUser(), dm);
					em.persist(dc);
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
		return apiCtx.executeQuery(new EMCallable<DataModel>(){
            @Override
            public DataModel call(EntityManager em) {
            	try{
            		
            		EntityGraph<?> entityGraph = em.getEntityGraph("DataModel");
            		Map<String, Object> props = new HashMap<String, Object>();
            		props.put("javax.persistence.fetchgraph", entityGraph);
            		DataModel dm = em.find(DataModel.class, uuid, props);
            		return dm;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static PrimitiveType newPrimitiveType(ApiContext apiCtx, DataModel dm, String label, String description, String units) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<PrimitiveType>(){
            @Override
            public PrimitiveType call(EntityManager em) {
            	try{
            		PrimitiveType pt = new PrimitiveType(label, description, apiCtx.getUser(), units, dm);
            		em.persist(pt);
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


	
}
