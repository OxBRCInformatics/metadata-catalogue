package ox.softeng.metadatacatalogue.api;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import ox.softeng.metadatacatalogue.db.ConnectionProvider;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.User;

public class ApiContext {

	
	// These will be passed as references - there should only be one in existence.
	private ConnectionProvider cp;
	private EntityManagerFactory emf;
	

	private User user;
	
	private ApiContext(Properties props)
	{
		cp = new ConnectionProvider(props);
		emf = cp.newConnection();
		user = null;
	}
	
	
	public ApiContext(ConnectionProvider cp, EntityManagerFactory emf, String username, String password) throws Exception
	{
		this.emf = emf;
		this.cp = cp;
		User u = UserApi.getByEmailAddressAndPassword(this, username, password);
		if(u != null)
		{
			this.user = u;
		}
		else
		{
			System.err.println("Cannot get ApiContext: no user found with username '" + username + "' and the provided password.");
		}
	}

	
	public static ApiContext getMasterApiContext(Properties props)
	{
		ApiContext apiCtx = new ApiContext(props);
		return apiCtx;
	}
	
	
	public ConnectionProvider getCp() {
		return cp;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public User getUser() {
		return user;
	}

	public void close()
	{
		if(emf != null && emf.isOpen())
		{
			emf.close();
		}
	}
	
	public <T> T executeTransaction(EMCallable<T> doInTransaction) throws Exception {
		if(!emf.isOpen())
		{
			emf = cp.newConnection();
			System.out.println("Connection dropped... creating new connection.");
		}
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			T result = doInTransaction.call(em);
			transaction.commit();
			return result;
		} catch(Exception e) {
			if(transaction.isActive())
			{
				transaction.rollback();
			}
			e.printStackTrace();
			throw e;
		} finally {
			em.close();
		}
	}
	
    public <T> T executeQuery(EMCallable<T> doInTransaction) throws Exception {

    	if(!emf.isOpen())
		{
    		emf.close();
			emf = cp.newConnection();
		}
		
    	EntityManager em = emf.createEntityManager();

        try {
            T result = doInTransaction.call(em);
            return result;
        } catch(Exception e) {
           throw e;
        } finally {
           em.close();
        }
    }
	
}
