package ox.softeng.metadatacatalogue.api;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;


import ox.softeng.metadatacatalogue.db.ConnectionProvider;
import ox.softeng.metadatacatalogue.db.DBQueryTemplate;
import ox.softeng.metadatacatalogue.db.DBTransactionTemplate;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.User;

public class ApiContext {

	private ConnectionProvider cp;
	
	private DBTransactionTemplate dbTransactionTemplate;
	private DBQueryTemplate dbQueryTemplate;

	private EntityManagerFactory emf;

	private User user;
	
	public ApiContext(Properties props, String username, String password) throws Exception
	{
		cp = new ConnectionProvider(props);
		dbTransactionTemplate = new DBTransactionTemplate(cp);
		dbQueryTemplate = new DBQueryTemplate(cp);
		emf = cp.newConnection();
		User u = UserApi.getByEmailAddressAndPassword(this, username, password);
		if(u != null)
		{
			this.user = u;
		}
		else
		{
			System.err.println("Cannot get ApiContext: no user found with username '" + username +"' and the provided password.");
		}
	}

	public ConnectionProvider getCp() {
		return cp;
	}

	public DBTransactionTemplate getDbTransactionTemplate() {
		return dbTransactionTemplate;
	}

	public DBQueryTemplate getDbQueryTemplate() {
		return dbQueryTemplate;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public User getUser() {
		return user;
	}

    public <T> T executeQuery(EMCallable<T> doInTransaction) throws Exception {
    	return dbQueryTemplate.executeQuery(emf, doInTransaction);
    }

    public <T> T executeTransaction(EMCallable<T> doInTransaction) throws Exception {
    	return dbTransactionTemplate.executeTransaction(emf, doInTransaction);
    }

	
}
