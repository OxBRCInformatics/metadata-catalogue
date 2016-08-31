package ox.softeng.metadatacatalogue.api;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Sharable;
import ox.softeng.metadatacatalogue.domain.core.User;
import ox.softeng.metadatacatalogue.domain.core.UserGroup;

public class SharableApi extends CatalogueItemApi{

 	protected SharableApi() { } // Private constructor as it makes no sense to instantiate this!

	public static Sharable editSharing(ApiContext apiCtx, Sharable sharable, Boolean readableByEveryone, Boolean writeableByEveryone) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Sharable>(){
            @Override
            public Sharable call(EntityManager em) {
            	try{
            		sharable.setReadableByEveryone(readableByEveryone);
            		sharable.setWriteableByEveryone(writeableByEveryone);
            		em.merge(sharable);
            		return sharable;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

 	
 	
	public static Sharable addShareReadWithUser(ApiContext apiCtx, Sharable sharable, User user) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Sharable>(){
            @Override
            public Sharable call(EntityManager em) {
            	try{
            		sharable.addUserReadable(user);
            		em.merge(user);
            		return sharable;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static Sharable addShareWriteWithUser(ApiContext apiCtx, Sharable sharable, User user) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Sharable>(){
            @Override
            public Sharable call(EntityManager em) {
            	try{
            		sharable.addUserWriteable(user);
            		em.merge(user);
            		return sharable;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static Sharable addShareReadWithGroup(ApiContext apiCtx, Sharable sharable, UserGroup userGroup) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Sharable>(){
            @Override
            public Sharable call(EntityManager em) {
            	try{
            		sharable.addGroupReadable(userGroup);
            		em.merge(userGroup);
            		return sharable;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static Sharable addShareWriteWithGroup(ApiContext apiCtx, Sharable sharable, UserGroup userGroup) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Sharable>(){
            @Override
            public Sharable call(EntityManager em) {
            	try{
            		sharable.addGroupWriteable(userGroup);
            		em.merge(userGroup);
            		return sharable;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static Sharable removeShareReadWithUser(ApiContext apiCtx, Sharable sharable, User user) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Sharable>(){
            @Override
            public Sharable call(EntityManager em) {
            	try{
            		sharable.removeUserReadable(user);
            		em.merge(user);
            		return sharable;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static Sharable removeShareWriteWithUser(ApiContext apiCtx, Sharable sharable, User user) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Sharable>(){
            @Override
            public Sharable call(EntityManager em) {
            	try{
            		sharable.removeUserWriteable(user);
            		em.merge(user);
            		return sharable;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static Sharable removeShareReadWithGroup(ApiContext apiCtx, Sharable sharable, UserGroup userGroup) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Sharable>(){
            @Override
            public Sharable call(EntityManager em) {
            	try{
            		sharable.removeGroupReadable(userGroup);
            		em.merge(userGroup);
            		return sharable;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static Sharable removeShareWriteWithGroup(ApiContext apiCtx, Sharable sharable, UserGroup userGroup) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Sharable>(){
            @Override
            public Sharable call(EntityManager em) {
            	try{
            		sharable.removeGroupWriteable(userGroup);
            		em.merge(userGroup);
            		return sharable;
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
