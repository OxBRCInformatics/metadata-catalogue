package ox.softeng.metadatacatalogue.api;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.User;
import ox.softeng.metadatacatalogue.domain.core.User.UserRole;

public final class UserApi extends CatalogueApi {

	
	public static User createUser(ApiContext apiCtx, String firstName, String lastName, String emailAddress, String password, UserRole role ) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<User>(){
            @Override
            public User call(EntityManager em) {
            	try{
            		User u = new User(firstName, lastName, emailAddress, password, role);
					em.persist(u);
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

	
	public static User getByEmailAddress(ApiContext apiCtx, String emailAddress) throws Exception
	{
		User u = apiCtx.executeQuery(new EMCallable<User>(){
            @Override
            public User call(EntityManager em) {
				Query query = em.createNamedQuery("User.getUserByEmailAddress");
				query.setParameter("emailAddress", emailAddress.trim());
				User user;
				try{
					user = (User) query.getSingleResult();
				}catch(NoResultException nre)
				{
					return null;
				}
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