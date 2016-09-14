package ox.softeng.metadatacatalogue.api;

import java.util.UUID;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.Classifier;
import ox.softeng.metadatacatalogue.domain.core.DataModelComponent;
import ox.softeng.metadatacatalogue.domain.core.Metadata;
import ox.softeng.metadatacatalogue.domain.core.Sharable;

public class DataModelComponentApi extends SharableApi{

 	protected DataModelComponentApi() { } // Private constructor as it makes no sense to instantiate this!

	public static Classifier addClassifier(ApiContext apiCtx, DataModelComponent dmc, Classifier cl) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Classifier>(){
            @Override
            public Classifier call(EntityManager em) {
            	try{
            		// Reattach these objects
            		DataModelComponent dataModelComponent = em.merge(dmc);
            		Classifier attachedCl = em.merge(cl);
            		attachedCl.classifyComponent(dataModelComponent);
            		
            		attachedCl = em.merge(attachedCl);
					return attachedCl;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static Classifier removeClassifier(ApiContext apiCtx, DataModelComponent dmc, Classifier cl) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Classifier>(){
            @Override
            public Classifier call(EntityManager em) {
            	try{
            		// Reattach these objects
            		DataModelComponent dataModelComponent = em.merge(dmc);
            		Classifier attachedCl = em.merge(cl);
            		attachedCl.classifyComponent(dataModelComponent);
            		
            		attachedCl = em.merge(attachedCl);
					return attachedCl;
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
