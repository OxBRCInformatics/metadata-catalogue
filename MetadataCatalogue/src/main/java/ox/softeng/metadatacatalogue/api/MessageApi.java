package ox.softeng.metadatacatalogue.api;

import java.util.List;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Message;

public class MessageApi extends DataModelApi{

 	protected MessageApi() { } // Private constructor as it makes no sense to instantiate this!
 
	public static Message createMessage(ApiContext apiCtx, String label, String description, String author, String organization ) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Message>(){
            @Override
            public Message call(EntityManager em) {
            	try{
            		Message ds = new Message(label, description, apiCtx.getUser(), author, organization);
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
	
	public static List<Message> getAllMessages(ApiContext apiCtx) throws Exception
	{
		return apiCtx.executeQuery(new EMCallable<List<Message>>(){
			 @Override
	         public List<Message> call(EntityManager em) {
				 List<Message> dss = em.createQuery("select d from ox.softeng.metadatacatalogue.domain.core.Message d", Message.class).getResultList();
				 for(Message ds : dss)
				 {
					 ds.getMetadata().size();
				 }
				 return dss;
			 }
		});	
	}
}
