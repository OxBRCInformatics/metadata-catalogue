package ox.softeng.metadatacatalogue.api;

import java.util.List;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.DataSet;

public class DataSetApi extends DataModelApi {

	protected DataSetApi() { } // Private constructor as it makes no sense to instantiate this!
	
	public static DataSet createDataSet(ApiContext apiCtx, String label, String description, String author, String organization ) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<DataSet>(){
            @Override
            public DataSet call(EntityManager em) {
            	try{
            		DataSet ds = new DataSet(label, description, apiCtx.getUser(), author, organization);
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
	
	public static List<DataSet> getAllDataSets(ApiContext apiCtx) throws Exception
	{
		return apiCtx.executeQuery(new EMCallable<List<DataSet>>(){
			 @Override
	         public List<DataSet> call(EntityManager em) {
				 List<DataSet> dss = em.createQuery("select d from ox.softeng.metadatacatalogue.domain.core.DataSet d", DataSet.class).getResultList();
				 for(DataSet ds : dss)
				 {
					 ds.getMetadata().size();
				 }
				 return dss;
			 }
		});	
	}
	
	
	
	
}
