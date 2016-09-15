package ox.softeng.metadatacatalogue.api;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.UserGroup;

public final class UserGroupApi extends CatalogueApi {

 	protected UserGroupApi() { } // Private constructor as it makes no sense to instantiate this!

 	
	public static UserGroup createUserGroup(ApiContext apiCtx, String name) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<UserGroup>(){
            @Override
            public UserGroup call(EntityManager em) {
            	try{
            		UserGroup ug = new UserGroup();
            		ug.setGroupName(name);
					ug = em.merge(ug);
					System.err.println("Creating usergroup: " + ug.getGroupName());
					return ug;
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
