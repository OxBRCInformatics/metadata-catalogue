package ox.softeng.metadatacatalogue.api;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.Metadata;

public class CatalogueItemApi extends CatalogueApi{

	public static CatalogueItem addMetadata(ApiContext apiCtx, CatalogueItem ci, String key, String value) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<CatalogueItem>(){
            @Override
            public CatalogueItem call(EntityManager em) {
            	try{
            		Metadata md = ci.addMetadata(key, value);
					em.merge(md);
					return ci;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static CatalogueItem editDetails(ApiContext apiCtx, CatalogueItem ci, String label, String description) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<CatalogueItem>(){
            @Override
            public CatalogueItem call(EntityManager em) {
            	try{
            		ci.setLabel(label);
            		ci.setDescription(description);
            		em.merge(ci);
					return ci;
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
