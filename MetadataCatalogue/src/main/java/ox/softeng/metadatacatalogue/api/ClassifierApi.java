package ox.softeng.metadatacatalogue.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Classifier;
import ox.softeng.metadatacatalogue.domain.core.DataModelComponent;

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
	
	public static Classifier createClassifier(ApiContext apiCtx, String label, String description, Classifier parentClassifier) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Classifier>(){
            @Override
            public Classifier call(EntityManager em) {
            	try{
            		Classifier cl = new Classifier(label, description, apiCtx.getUser(), parentClassifier);
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
 	
 	public static List<DataModelComponent> getClassifiedComponents(ApiContext apiCtx, Classifier classifier) throws Exception
 	{
 		return apiCtx.executeQuery(new EMCallable<List<DataModelComponent>>(){
			 @Override
	         public List<DataModelComponent> call(EntityManager em) {
				 
				 List<DataModelComponent> dmcs = classifier.getClassifiedComponents();
				 return dmcs;
				 
			 }
		});
 	}

 	public static List<DataModelComponent> getClassifiedComponents(ApiContext apiCtx, UUID classifierID) throws Exception
 	{
 		return apiCtx.executeQuery(new EMCallable<List<DataModelComponent>>(){
			 @Override
	         public List<DataModelComponent> call(EntityManager em) {
				 
				 Classifier classifier = em.find(Classifier.class, classifierID);
				 
				 List<DataModelComponent> dmcs = classifier.getClassifiedComponents();
				 return dmcs;
				 
			 }
		});
 	}
 	
 	public static List<Classifier> getClassifierTree(ApiContext apiCtx) throws Exception
 	{
 		List<Classifier> classifiers = apiCtx.getAll(Classifier.class);

 		List<Classifier> parentClassifiers = new ArrayList<Classifier>();
 		return apiCtx.executeQuery(new EMCallable<List<Classifier>>(){
			 @Override
	         public List<Classifier> call(EntityManager em) {
				 for(Classifier c : classifiers)
				 {
					 // reattach object
					 em.merge(c);
					 if(c.getParentClassifier() == null)
					 {
						 parentClassifiers.add(c);
					 }
					 
				 }
				 
				 return parentClassifiers;
			 }
		});
 	}
 	
 	

	
}
