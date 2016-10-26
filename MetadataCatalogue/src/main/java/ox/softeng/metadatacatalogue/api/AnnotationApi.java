package ox.softeng.metadatacatalogue.api;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Annotation;

public class AnnotationApi extends SharableApi{

 	protected AnnotationApi() { } // Private constructor as it makes no sense to instantiate this!
 	
	public static Annotation newAnnotation(ApiContext apiCtx, Annotation parentAnnotation, String label, String description) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Annotation>(){
            @Override
            public Annotation call(EntityManager em) {
            	try{
            		// Reattach these objects
            		Annotation pA = em.merge(parentAnnotation);
            		Annotation childAnnotation = new Annotation(label, description, apiCtx.getUser(), pA);
            		childAnnotation = em.merge(childAnnotation);
					return childAnnotation;
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
