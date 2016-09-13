package ox.softeng.metadatacatalogue.api;

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
}
