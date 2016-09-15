package ox.softeng.metadatacatalogue.api;

import java.util.List;

import javax.persistence.EntityManager;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.EMCallable;
import ox.softeng.metadatacatalogue.domain.core.Report;

public class ReportApi extends DataModelApi{

 	protected ReportApi() { } // Private constructor as it makes no sense to instantiate this!
 	
	public static Report createReport(ApiContext apiCtx, String label, String description, String author, String organization ) throws Exception
	{
		return apiCtx.executeTransaction(new EMCallable<Report>(){
            @Override
            public Report call(EntityManager em) {
            	try{
            		Report ds = new Report(label, description, apiCtx.getUser(), author, organization);
					em.persist(ds);
					return ds;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
            }
		});
	}
	
	public static List<Report> getAllReports(ApiContext apiCtx) throws Exception
	{
		return apiCtx.executeQuery(new EMCallable<List<Report>>(){
			 @Override
	         public List<Report> call(EntityManager em) {
				 List<Report> dss = em.createQuery("select d from ox.softeng.metadatacatalogue.domain.core.Report d", Report.class).getResultList();
				 for(Report ds : dss)
				 {
					 ds.getMetadata().size();
				 }
				 return dss;
			 }
		});	
	}
}
