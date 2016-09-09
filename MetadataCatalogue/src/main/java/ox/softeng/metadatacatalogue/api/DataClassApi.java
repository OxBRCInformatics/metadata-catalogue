package ox.softeng.metadatacatalogue.api;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataElement;
import ox.softeng.metadatacatalogue.domain.core.DataType;

public class DataClassApi extends DataModelComponentApi {

 	protected DataClassApi() { } // Private constructor as it makes no sense to instantiate this!

	public static DataClass newChildDataClass(ApiContext apiCtx, DataClass dc, String label, String description) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<DataClass>(){
            @Override
            public DataClass call(EntityManager em) {
            	try{
            		DataClass childDC = new DataClass(label, description, apiCtx.getUser(), dc);
					childDC = em.merge(childDC);
					//System.out.println("class id : " + childDC.getId());
					return childDC;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}

	public static DataElement newChildDataElement(ApiContext apiCtx, DataClass dc, String label, String description, DataType dt) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<DataElement>(){
            @Override
            public DataElement call(EntityManager em) {
            	try{
            		DataElement childDE = new DataElement(label, description, apiCtx.getUser(), dc, dt);
					childDE = em.merge(childDE);
					//System.out.println("element id : " + childDE.getId());
					return childDE;
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
