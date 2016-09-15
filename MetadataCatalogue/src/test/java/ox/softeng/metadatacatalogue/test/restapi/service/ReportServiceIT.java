package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.ReportApi;
import ox.softeng.metadatacatalogue.domain.core.Report;

public class ReportServiceIT extends DataModelServiceIT<Report> {

	@Override
	protected Report getInstance() throws Exception
	{
		return ReportApi.createReport(apiCtx, "My test Report", "Report description", "Test Author", "Test Organization");
	}

	@Override
	protected String getServicePath()
	{
		return "/report";
	}

	@Override
	protected Class<? extends Report> getClazz()
	{
		return Report.class;
	}


}
