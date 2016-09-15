package ox.softeng.metadatacatalogue.api;

import java.util.List;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.DataStandard;

public class DataStandardApi extends DataModelApi{

 	protected DataStandardApi() { } // Private constructor as it makes no sense to instantiate this!
 	
 	
	public static DataStandard createDataStandard(ApiContext apiCtx, String label, String description, String author, String organization ) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<DataStandard>(){
            @Override
            public DataStandard call(EntityManager em) {
            	try{
            		DataStandard ds = new DataStandard(label, description, apiCtx.getUser(), author, organization);
					em.persist(ds);
					return ds;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}
	
	public static List<DataStandard> getAllDataStandards(ApiContext apiCtx) throws Exception
	{
		return apiCtx.executeQuery(new EMCallable<List<DataStandard>>(){
			 @Override
	         public List<DataStandard> call(EntityManager em) {
				 List<DataStandard> dss = em.createQuery("select d from ox.softeng.metadatacatalogue.domain.core.DataStandard d", DataStandard.class).getResultList();
				 for(DataStandard ds : dss)
				 {
					 ds.getMetadata().size();
				 }
				 return dss;
			 }
		});	
	}
 	
}
