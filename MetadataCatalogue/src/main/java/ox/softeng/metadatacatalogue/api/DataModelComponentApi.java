package ox.softeng.metadatacatalogue.api;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Sharable;

public class DataModelComponentApi extends SharableApi{

 	protected DataModelComponentApi() { } // Private constructor as it makes no sense to instantiate this!

/*	public static DataModelComponent addClassifier(ApiContext apiCtx, Sharable sharable, Boolean readableByEveryone, Boolean writeableByEveryone) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Sharable>(){
            @Override
            public Sharable call(EntityManager em) {
            	try{
            		sharable.setReadableByEveryone(readableByEveryone);
            		sharable.setWriteableByEveryone(writeableByEveryone);
            		em.merge(sharable);
            		return sharable;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}
*/
 	
}
