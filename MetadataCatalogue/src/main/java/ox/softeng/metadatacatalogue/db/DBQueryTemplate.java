package ox.softeng.metadatacatalogue.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DBQueryTemplate {

	ConnectionProvider connectionProvider;
	
	public DBQueryTemplate(ConnectionProvider connectionProvider)
	{
		this.connectionProvider = connectionProvider;
	}

	
    public <T> T executeQuery(EntityManagerFactory emf, EMCallable<T> doInTransaction) throws Exception {

    	if(!emf.isOpen())
		{
    		emf.close();
			emf = connectionProvider.newConnection();
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
