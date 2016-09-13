package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Database;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ox.softeng.metadatacatalogue.api.DatabaseApi;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

@Path("/database")
public class DatabaseService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = Database.class;
	
	@Path("/create")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO createDatabase(Database db) throws Exception
	{		
		Database ret = DatabaseApi.createDatabase( getApiContext(), db.getLabel(), db.getDescription(), db.getAuthor(), db.getOrganization(), db.getDialect());
		ret = (Database) maybeAddMetadata(ret, db);
		return createSuccessfulResponse(ret, "datamodel.pageview.id");
	}


		
}
