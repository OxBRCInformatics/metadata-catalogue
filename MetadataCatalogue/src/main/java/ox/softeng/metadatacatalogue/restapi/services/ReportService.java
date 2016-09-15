package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Report;

import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


@Path("/report")
public class ReportService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = Report.class;
	

		
}
