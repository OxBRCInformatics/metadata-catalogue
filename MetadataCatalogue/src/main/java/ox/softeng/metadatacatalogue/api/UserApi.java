package ox.softeng.metadatacatalogue.api;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.User;
import ox.softeng.metadatacatalogue.domain.core.User.UserRole;

public final class UserApi extends CatalogueApi {

 	protected UserApi() { } // Private constructor as it makes no sense to instantiate this!

	
	public static User createUser(ApiContext apiCtx, String firstName, String lastName, String emailAddress, String password, UserRole role ) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<User>(){
            @Override
            public User call(EntityManager em) {
            	try{
            		User u = new User(firstName, lastName, emailAddress, password, role);
					u = em.merge(u);
					return u;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static User findOrCreateUser(ApiContext apiCtx, String firstName, String lastName, String emailAddress, String password, UserRole role ) throws Exception
	{
		User existingUser = getByEmailAddress(apiCtx, emailAddress);
		if(existingUser != null)
		{
			return existingUser;
		}
		return createUser(apiCtx, firstName, lastName, emailAddress, password, role);
	}
	
	public static User getByEmailAddress(ApiContext apiCtx, String emailAddress) throws Exception
	{
		String trimmedEmailAddress = emailAddress.toLowerCase().trim();
		User u = apiCtx.executeQuery(new EMCallable<User>(){
            @Override
            public User call(EntityManager em) {
				User user = em.find(User.class, trimmedEmailAddress);
				return user;
            }
            
		});
		return u;
	}

	public static User getByEmailAddressAndPassword(ApiContext apiCtx, String emailAddress, String password) throws Exception
	{
		User u = apiCtx.executeQuery(new EMCallable<User>(){
            @Override
            public User call(EntityManager em) {

				User u;
				try {
					u = getByEmailAddress(apiCtx, emailAddress);
				} catch (Exception e1) {
					e1.printStackTrace();
					return null;
				}
				if(u==null)
				{
					return null;
				}
				try {
					if(u.validatePassword(password))
					{
						return u;
					}
				} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
					e.printStackTrace();
					return null;
				}
				return null;
			}
		});
		return u;
	}
}
