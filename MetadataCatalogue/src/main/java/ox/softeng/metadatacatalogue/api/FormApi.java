package ox.softeng.metadatacatalogue.api;

import java.util.List;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Form;

public class FormApi extends DataModelApi{

 	protected FormApi() { } // Private constructor as it makes no sense to instantiate this!
 	
	public static Form createForm(ApiContext apiCtx, String label, String description, String author, String organization ) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Form>(){
            @Override
            public Form call(EntityManager em) {
            	try{
            		Form ds = new Form(label, description, apiCtx.getUser(), author, organization);
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
	
	public static List<Form> getAllForms(ApiContext apiCtx) throws Exception
	{
		return apiCtx.executeQuery(new EMCallable<List<Form>>(){
			 @Override
	         public List<Form> call(EntityManager em) {
				 List<Form> dss = em.createQuery("select d from ox.softeng.metadatacatalogue.domain.core.Form d", Form.class).getResultList();
				 for(Form ds : dss)
				 {
					 ds.getMetadata().size();
				 }
				 return dss;
			 }
		});	
	}
}
