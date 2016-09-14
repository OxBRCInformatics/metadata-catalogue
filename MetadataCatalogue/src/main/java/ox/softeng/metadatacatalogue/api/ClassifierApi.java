package ox.softeng.metadatacatalogue.api;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Classifier;

public class ClassifierApi extends SharableApi{

 	protected ClassifierApi() { } // Private constructor as it makes no sense to instantiate this!
 	
	public static Classifier createClassifier(ApiContext apiCtx, String label, String description ) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Classifier>(){
            @Override
            public Classifier call(EntityManager em) {
            	try{
            		Classifier cl = new Classifier(label, description, apiCtx.getUser());
					em.persist(cl);
					return cl;
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
