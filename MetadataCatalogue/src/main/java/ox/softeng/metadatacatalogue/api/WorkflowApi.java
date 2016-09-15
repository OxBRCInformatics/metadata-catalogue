package ox.softeng.metadatacatalogue.api;

import java.util.List;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Workflow;

public class WorkflowApi extends DataModelApi{

 	protected WorkflowApi() { } // Private constructor as it makes no sense to instantiate this!
 
	public static Workflow createWorkflow(ApiContext apiCtx, String label, String description, String author, String organization ) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Workflow>(){
            @Override
            public Workflow call(EntityManager em) {
            	try{
            		Workflow ds = new Workflow(label, description, apiCtx.getUser(), author, organization);
					em.persist(ds);
					return ds;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}
	
	public static List<Workflow> getAllWorkflows(ApiContext apiCtx) throws Exception
	{
		return apiCtx.executeQuery(new EMCallable<List<Workflow>>(){
			 @Override
	         public List<Workflow> call(EntityManager em) {
				 List<Workflow> dss = em.createQuery("select d from ox.softeng.metadatacatalogue.domain.core.Workflow d", Workflow.class).getResultList();
				 for(Workflow ds : dss)
				 {
					 ds.getMetadata().size();
				 }
				 return dss;
			 }
		});	
	}
}
