package ox.softeng.metadatacatalogue.api;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.DataModelComponent;
import ox.softeng.metadatacatalogue.domain.core.Link;

public class LinkApi extends SharableApi{

 	protected LinkApi() { } // Private constructor as it makes no sense to instantiate this!
 	
 	
	public static Link createLink(ApiContext apiCtx, DataModelComponent source, DataModelComponent target, Link.LinkType linkType) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Link>(){
            @Override
            public Link call(EntityManager em) {
            	try{
            		Link l = new Link(apiCtx.getUser(), source, target, linkType);
					em.persist(l);
					return l;
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
