package ox.softeng.metadatacatalogue.api;

import java.util.UUID;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Metadata;

public class MetadataApi extends CatalogueApi{

	private MetadataApi() {} // Private constructor as it makes no sense to instantiate this!

	public static Metadata edit(ApiContext apiCtx, Metadata md, String key, String value) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Metadata>(){
            @Override
            public Metadata call(EntityManager em) {
            	try{
            		md.setKey(key);
            		md.setValue(value);
					Metadata newMD = em.merge(md);
					return newMD;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}
	
	public static Metadata getById(ApiContext apiCtx, UUID id) throws Exception
	{
		return apiCtx.getById(Metadata.class, id);
	}
	
}
