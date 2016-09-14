package ox.softeng.metadatacatalogue.api;

import java.util.UUID;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.Classifier;
import ox.softeng.metadatacatalogue.domain.core.Metadata;

public class CatalogueItemApi extends CatalogueApi{

	protected CatalogueItemApi() {} // Private constructor as it makes no sense to instantiate this!

	public static Metadata addMetadata(ApiContext apiCtx, CatalogueItem catalogueItem, String key, String value) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Metadata>(){
            @Override
            public Metadata call(EntityManager em) {
            	try{
            		//CatalogueItem ci = em.find(CatalogueItem.class, catalogueItem.getId());
            		CatalogueItem ci = em.merge(catalogueItem);
            		Metadata md = ci.addMetadata(key, value);
					md = em.merge(md);
					return md;
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
            		CatalogueItem retCI = em.merge(catalogueItem);
					return retCI;
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
