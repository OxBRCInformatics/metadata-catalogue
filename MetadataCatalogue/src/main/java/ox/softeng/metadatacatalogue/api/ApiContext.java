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

	private User u;
	
	public ApiContext(Properties props, User u)
	{
		cp = new ConnectionProvider(props);
		dbTransactionTemplate = new DBTransactionTemplate(cp);
		dbQueryTemplate = new DBQueryTemplate(cp);
		emf = cp.newConnection();
		this.u = u;
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

	public User getU() {
		return u;
	}

    public <T> T executeQuery(EMCallable<T> doInTransaction) throws Exception {
    	return dbQueryTemplate.executeQuery(emf, doInTransaction);
    }

    public <T> T executeTransaction(EMCallable<T> doInTransaction) throws Exception {
    	return dbTransactionTemplate.executeTransaction(emf, doInTransaction);
    }

	
}
