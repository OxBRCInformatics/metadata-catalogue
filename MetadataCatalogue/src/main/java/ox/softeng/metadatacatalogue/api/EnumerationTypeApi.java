package ox.softeng.metadatacatalogue.api;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.EnumerationType;
import ox.softeng.metadatacatalogue.domain.core.EnumerationValue;

public class EnumerationTypeApi extends CatalogueItemApi {

	public static EnumerationValue newEnumerationValue(ApiContext apiCtx, EnumerationType et, String key, String value) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<EnumerationValue>(){
            @Override
            public EnumerationValue call(EntityManager em) {
            	try{
            		EnumerationValue ev = et.addEnumerationValue(key, value, apiCtx.getUser()); 
					em.persist(ev);
					return ev;
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
