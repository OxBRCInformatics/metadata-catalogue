package ox.softeng.metadatacatalogue.db;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.projector.Projector;

public class DatabaseQueryHelper {

	// These will be passed as references - there should only be one in existence.
	protected ConnectionProvider cp;
	protected EntityManagerFactory emf;

	public static final JsonNodeFactory jsonFactory = JsonNodeFactory.instance;

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

	public <DomainClass> ArrayNode getAllMap(Class<DomainClass> domainClass, String projectionName) throws Exception
	{
		return executeQuery(new EMCallable<ArrayNode>(){
            @Override
            public ArrayNode call(EntityManager em) {
            	try{
            		String queryStr = "SELECT distinct res FROM " + domainClass.getName() + " res";
            		TypedQuery<DomainClass> query = em.createQuery(queryStr, domainClass);
            		List<DomainClass> queryResults = query.getResultList();
            		ArrayNode ret = Projector.project(queryResults, projectionName);
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
	
	public <DomainClass extends CatalogueItem> ArrayNode searchMap(Class<DomainClass> domainClass, String projectionName, String searchTerm, Integer offset, Integer limit) throws Exception
	{
		return executeQuery(new EMCallable<ArrayNode>(){
            @Override
            public ArrayNode call(EntityManager em) {
            	try{
            		String queryStr = "SELECT distinct res FROM " + domainClass.getName() + " res where "
						+ "lower(res.label) 		like lower(concat('%',:searchTerm,'%')) or " 
						+ "lower(res.description) 	like lower(concat('%',:searchTerm,'%'))";
            		TypedQuery<DomainClass> query = em.createQuery(queryStr, domainClass);
            		query.setParameter("searchTerm", searchTerm);
            		query.setFirstResult(offset);
            		query.setMaxResults(limit);
            		List<DomainClass> queryResults = query.getResultList();
            		ArrayNode ret = Projector.project(queryResults, projectionName);
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

	public <DomainClass> JsonNode getByIdMap(Class<DomainClass> domainClass, String projectionName, UUID uuid) throws Exception
	{
		return executeQuery(new EMCallable<JsonNode>(){
            @Override
            public JsonNode call(EntityManager em) {
            	try{
            		DomainClass result = em.find(domainClass, uuid);
            		if(result == null)
            		{
            			return null;
            		}
            		JsonNode destObject =  Projector.project(result, projectionName);
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
	
	public JsonNode project(Object obj, String projectionName) throws Exception
	{
		return executeQuery(new EMCallable<JsonNode>(){
            @Override
            public JsonNode call(EntityManager em) {
            	try{
            		Object o = em.merge(obj);
            		JsonNode destObject =  Projector.project(o, projectionName);
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
	
	public <T> T refresh(T o) throws Exception
	{
		return executeQuery(new EMCallable<T>(){
			@Override
			public T call(EntityManager em) {
				try{
					T managedO = em.merge(o);
					em.refresh(managedO);
					return (T) managedO;
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
