package ox.softeng.metadatacatalogue.api;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.domain.core.EnumerationType;
import ox.softeng.metadatacatalogue.domain.core.PrimitiveType;
import ox.softeng.metadatacatalogue.domain.core.ReferenceType;

public class DataModelApi extends FinalisableApi {

	protected DataModelApi() {} // Private constructor as it makes no sense to instantiate this!
	
	public static DataClass newDataClass(ApiContext apiCtx, DataModel dm, String label, String description) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<DataClass>(){
            @Override
            public DataClass call(EntityManager em) {
            	try{
            		DataClass dc = new DataClass(label, description, apiCtx.getUser(), dm);
					dc = em.merge(dc);
					return dc;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static DataModel getById(ApiContext apiCtx, UUID uuid) throws Exception
	{
		return apiCtx.getById(DataModel.class, uuid);
	}

	public static List<DataModel> getAll(ApiContext apiCtx) throws Exception
	{
		return apiCtx.getAll(DataModel.class);
	}

	public static PrimitiveType newPrimitiveType(ApiContext apiCtx, DataModel dm, String label, String description, String units) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<PrimitiveType>(){
            @Override
            public PrimitiveType call(EntityManager em) {
            	try{
            		PrimitiveType pt = new PrimitiveType(label, description, apiCtx.getUser(), units, dm);
            		pt = em.merge(pt);
					return pt;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}
	
	public static ReferenceType newReferenceType(ApiContext apiCtx, DataModel dm, String label, String description, DataClass referenceClass) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<ReferenceType>(){
            @Override
            public ReferenceType call(EntityManager em) {
            	try{
            		ReferenceType rt = new ReferenceType(label, description, apiCtx.getUser(), referenceClass, dm);
            		rt = em.merge(rt);
					return rt;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	
	public static EnumerationType newEnumerationType(ApiContext apiCtx, DataModel dm, String label, String description) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<EnumerationType>(){
            @Override
            public EnumerationType call(EntityManager em) {
            	try{
            		EnumerationType et = new EnumerationType(label, description, apiCtx.getUser(), dm);
            		em.persist(et);
					return et;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}


	public static DataModel importDataModel(ApiContext apiCtx, InputStream is) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<DataModel>(){
            @Override
            public DataModel call(EntityManager em) {
            	try{
            		DataModel dm = apiCtx.createObjectFromInputStream(is, DataModel.class);
            		dm = em.merge(dm);
            		return dm;
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
