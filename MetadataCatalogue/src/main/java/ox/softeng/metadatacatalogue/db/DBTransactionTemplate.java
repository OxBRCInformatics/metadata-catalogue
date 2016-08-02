package ox.softeng.metadatacatalogue.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class DBTransactionTemplate {

	ConnectionProvider connectionProvider;
	
	public DBTransactionTemplate(ConnectionProvider connectionProvider)
	{
		this.connectionProvider = connectionProvider;
	}

	public <T> T executeTransaction(EntityManagerFactory emf, EMCallable<T> doInTransaction) throws Exception {
		if(!emf.isOpen())
		{
			emf = connectionProvider.newConnection();
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

}
