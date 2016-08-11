package ox.softeng.metadatacatalogue.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.ws.rs.core.SecurityContext;

import ox.softeng.metadatacatalogue.db.ConnectionProvider;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.User;

public class ApiContext implements SecurityContext {


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

	public ApiContext(String propertiesFilename) throws Exception
	{
		Properties props = readPropertiesFile(propertiesFilename);
		String username = props.getProperty("apiUser.username");
		String password = props.getProperty("apiUser.password");
		getUser(username, password);
	}

	public ApiContext(String properiesFilename, String username, String password) throws Exception
	{
		Properties props = new Properties();
		props.load(new FileInputStream(properiesFilename));
		cp = new ConnectionProvider(props);
		emf = cp.newConnection();
		getUser(username, password);
	}

	private Properties readPropertiesFile(String propertiesFilename) throws FileNotFoundException, IOException
	{
		Properties props = new Properties();
		props.load(new FileInputStream(propertiesFilename));
		cp = new ConnectionProvider(props);
		emf = cp.newConnection();
		return props;
	}
	
	public ApiContext(ConnectionProvider cp, EntityManagerFactory emf, String username, String password) throws Exception
	{
		this.emf = emf;
		this.cp = cp;
		getUser(username, password);
	}

	private ApiContext(ConnectionProvider cp, EntityManagerFactory emf, User u) throws Exception
	{
		this.emf = emf;
		this.cp = cp;
		this.user = u;
	}


	public static ApiContext getMasterApiContext(Properties props)
	{
		ApiContext apiCtx = new ApiContext(props);
		return apiCtx;
	}
	
	public static ApiContext getSessionContext(ApiContext masterContext, User u) throws Exception
	{
		ApiContext apiCtx = new ApiContext(masterContext.getCp(), masterContext.getEmf(), u);
		return apiCtx;
	}

	private void getUser(String username, String password) throws Exception
	{
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
			//emf.getCache().evictAll();
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
	@Override
	public String getAuthenticationScheme() {
		return null;
	}

	@Override
	public Principal getUserPrincipal() {
		return user;
	}

	@Override
	public final boolean isSecure() {
		return false;
	}

	@Override
	public boolean isUserInRole(String role) {
		User.UserRole userRole = User.UserRole.valueOf(role);

		for(User.UserRole u : User.UserRole.values())
		{
			if(u == user.getUserRole())
			{
				return false;
			}
			else if(u == userRole)
			{
				return true;
			}
		}
		return false;
	}

}
