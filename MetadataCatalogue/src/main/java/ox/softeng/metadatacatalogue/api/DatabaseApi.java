package ox.softeng.metadatacatalogue.api;

import java.util.List;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Database;

public class DatabaseApi extends DataModelApi{

 	protected DatabaseApi() { } // Private constructor as it makes no sense to instantiate this!
 	
	
	public static Database createDatabase(ApiContext apiCtx, String label, String description, String author, String organization, String dialect ) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Database>(){
            @Override
            public Database call(EntityManager em) {
            	try{
            		Database db = new Database(label, description, apiCtx.getUser(), author, organization, dialect);
					em.persist(db);
					return db;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}
	
	public static List<Database> getAllDatabases(ApiContext apiCtx) throws Exception
	{
		return apiCtx.executeQuery(new EMCallable<List<Database>>(){
			 @Override
	         public List<Database> call(EntityManager em) {
				 List<Database> dbs = em.createQuery("select d from ox.softeng.metadatacatalogue.domain.core.Database d", Database.class).getResultList();
				 for(Database db : dbs)
				 {
					 db.getMetadata().size();
				 }
				 return dbs;
			 }
		});	
	}
}
