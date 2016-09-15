package ox.softeng.metadatacatalogue.api;


import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Annotation;
import ox.softeng.metadatacatalogue.domain.core.Classifier;
import ox.softeng.metadatacatalogue.domain.core.DataModelComponent;

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

	public static Annotation newAnnotation(ApiContext apiCtx, DataModelComponent dmc, String label, String description) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Annotation>(){
            @Override
            public Annotation call(EntityManager em) {
            	try{
            		// Reattach these objects
            		DataModelComponent dataModelComponent = em.merge(dmc);
            		Annotation ann = new Annotation(label, description, apiCtx.getUser(), dataModelComponent);
            		dataModelComponent = em.merge(dataModelComponent);
            		ann = em.merge(ann);
					return ann;
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
