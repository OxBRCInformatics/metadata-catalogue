package ox.softeng.metadatacatalogue.api;

import java.util.UUID;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.Metadata;

public class CatalogueItemApi extends CatalogueApi{

	protected CatalogueItemApi() {} // Private constructor as it makes no sense to instantiate this!

	public static CatalogueItem addMetadata(ApiContext apiCtx, CatalogueItem catalogueItem, String key, String value) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<CatalogueItem>(){
            @Override
            public CatalogueItem call(EntityManager em) {
            	try{
            		Metadata md = catalogueItem.addMetadata(key, value);
					em.merge(md);
					return catalogueItem;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static CatalogueItem deleteMetadata(ApiContext apiCtx, CatalogueItem catalogueItem, UUID metadataItem) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<CatalogueItem>(){
            @Override
            public CatalogueItem call(EntityManager em) {
            	try{
            		Metadata md = em.find(Metadata.class, metadataItem);
            		em.remove(md);
					return catalogueItem;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static CatalogueItem editDetails(ApiContext apiCtx, CatalogueItem catalogueItem, String label, String description) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<CatalogueItem>(){
            @Override
            public CatalogueItem call(EntityManager em) {
            	try{
            		catalogueItem.setLabel(label);
            		catalogueItem.setDescription(description);
            		em.merge(catalogueItem);
					return catalogueItem;
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
