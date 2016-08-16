package ox.softeng.metadatacatalogue.db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

public class DatabaseQueryHelper {

	// These will be passed as references - there should only be one in existence.
	protected ConnectionProvider cp;
	protected EntityManagerFactory emf;

	static Mapper dozerMapper = new DozerBeanMapper();

	public <DomainClass> List<DomainClass> getAll(Class<DomainClass> domainClass) throws Exception
	{
		return executeQuery(new EMCallable<List<DomainClass>>(){
            @Override
            public List<DomainClass> call(EntityManager em) {
            	try{
            		String queryStr = "SELECT distinct res FROM " + domainClass.getName() + " res";
            		TypedQuery<DomainClass> query = em.createQuery(queryStr, domainClass);
            		List<DomainClass> queryResults = query.getResultList();
            		return queryResults;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public <DomainClass, DTOClass> List<DTOClass> getAllMap(Class<DomainClass> domainClass, Class<DTOClass> dtoClass) throws Exception
	{
		return executeQuery(new EMCallable<List<DTOClass>>(){
            @Override
            public List<DTOClass> call(EntityManager em) {
            	try{
            		String queryStr = "SELECT distinct res FROM " + domainClass.getName() + " res";
            		TypedQuery<DomainClass> query = em.createQuery(queryStr, domainClass);
            		List<DomainClass> queryResults = query.getResultList();
            		List<DTOClass> ret = new ArrayList<DTOClass>();
            		//System.out.println(queryResults.size());
            		for(DomainClass domainObj : queryResults)
            		{
                		if(domainObj == null)
                		{
                			ret.add(null);
                		}
                		else
                		{
                			DTOClass destObject =  dozerMapper.map(domainObj, dtoClass);
                			ret.add(destObject);
                		}
            		}
            		return ret;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public <DomainClass extends CatalogueItem> List<DomainClass> search(Class<DomainClass> domainClass, String searchTerm, Integer offset, Integer limit) throws Exception
	{
		return executeQuery(new EMCallable<List<DomainClass>>(){
            @Override
            public List<DomainClass> call(EntityManager em) {
            	try{
            		String queryStr = "SELECT distinct res FROM " + domainClass.getName() + " res where "
						+ "lower(res.label) 		like lower(concat('%',:searchTerm,'%')) or " 
						+ "lower(res.description) 	like lower(concat('%',:searchTerm,'%'))";
            		TypedQuery<DomainClass> query = em.createQuery(queryStr, domainClass);
            		query.setParameter("searchTerm", searchTerm);
            		query.setFirstResult(offset);
            		query.setMaxResults(limit);
            		List<DomainClass> queryResults = query.getResultList();
            		return queryResults;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}
	
	public <DomainClass extends CatalogueItem, DTOClass> List<DTOClass> searchMap(Class<DomainClass> domainClass, Class<DTOClass> dtoClass, String searchTerm, Integer offset, Integer limit) throws Exception
	{
		return executeQuery(new EMCallable<List<DTOClass>>(){
            @Override
            public List<DTOClass> call(EntityManager em) {
            	try{
            		String queryStr = "SELECT distinct res FROM " + domainClass.getName() + " res where "
						+ "lower(res.label) 		like lower(concat('%',:searchTerm,'%')) or " 
						+ "lower(res.description) 	like lower(concat('%',:searchTerm,'%'))";
            		TypedQuery<DomainClass> query = em.createQuery(queryStr, domainClass);
            		query.setParameter("searchTerm", searchTerm);
            		query.setFirstResult(offset);
            		query.setMaxResults(limit);
            		List<DomainClass> queryResults = query.getResultList();
            		List<DTOClass> ret = new ArrayList<DTOClass>();
            		//System.out.println(queryResults.size());
            		for(DomainClass domainObj : queryResults)
            		{
                		if(domainObj == null)
                		{
                			ret.add(null);
                		}
                		else
                		{
                			DTOClass destObject =  dozerMapper.map(domainObj, dtoClass);
                			ret.add(destObject);
                		}
            		}
            		return ret;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}
	
	public <DomainClass> DomainClass getById(Class<DomainClass> domainClass, UUID uuid) throws Exception
	{
		return executeQuery(new EMCallable<DomainClass>(){
            @Override
            public DomainClass call(EntityManager em) {
            	try{
            		DomainClass result = em.find(domainClass, uuid);
            		return result;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public <DomainClass, DTOClass> DTOClass getByIdMap(Class<DomainClass> domainClass, Class<DTOClass> dtoClass, UUID uuid) throws Exception
	{
		return executeQuery(new EMCallable<DTOClass>(){
            @Override
            public DTOClass call(EntityManager em) {
            	try{
            		DomainClass result = em.find(domainClass, uuid);
            		if(result == null)
            		{
            			return null;
            		}
            		DTOClass destObject =  dozerMapper.map(result, dtoClass);
            		return destObject;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
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

	public ConnectionProvider getCp() {
		return cp;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

}
