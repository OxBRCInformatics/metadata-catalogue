package ox.softeng.metadatacatalogue.db;

import javax.persistence.EntityManager;

public abstract class EMCallable<T> {

	
	public abstract T call(EntityManager em);
	
}
