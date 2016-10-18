package ox.softeng.metadatacatalogue.restapi;


import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.internal.MultiPartReaderClientSide;
import org.glassfish.jersey.media.multipart.internal.MultiPartReaderServerSide;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import ox.softeng.metadatacatalogue.restapi.filters.AuthenticationFilter;
import ox.softeng.metadatacatalogue.restapi.filters.DBConnectionFilter;
import ox.softeng.metadatacatalogue.restapi.filters.ProfilingListener;
import ox.softeng.metadatacatalogue.restapi.filters.ResponseCorsFilter;
import ox.softeng.metadatacatalogue.restapi.services.AnnotationService;
import ox.softeng.metadatacatalogue.restapi.services.AuthenticationService;
import ox.softeng.metadatacatalogue.restapi.services.CatalogueItemService;
import ox.softeng.metadatacatalogue.restapi.services.ClassifierService;
import ox.softeng.metadatacatalogue.restapi.services.DataClassService;
import ox.softeng.metadatacatalogue.restapi.services.DataElementService;
import ox.softeng.metadatacatalogue.restapi.services.DataModelComponentService;
import ox.softeng.metadatacatalogue.restapi.services.DataModelService;
import ox.softeng.metadatacatalogue.restapi.services.DataSetService;
import ox.softeng.metadatacatalogue.restapi.services.DataStandardService;
import ox.softeng.metadatacatalogue.restapi.services.DataTypeService;
import ox.softeng.metadatacatalogue.restapi.services.DatabaseService;
import ox.softeng.metadatacatalogue.restapi.services.EnumerationTypeService;
import ox.softeng.metadatacatalogue.restapi.services.EnumerationValueService;
import ox.softeng.metadatacatalogue.restapi.services.FinalisableService;
import ox.softeng.metadatacatalogue.restapi.services.FormService;
import ox.softeng.metadatacatalogue.restapi.services.LinkService;
import ox.softeng.metadatacatalogue.restapi.services.MessageService;
import ox.softeng.metadatacatalogue.restapi.services.MetadataService;
import ox.softeng.metadatacatalogue.restapi.services.PrimitiveTypeService;
import ox.softeng.metadatacatalogue.restapi.services.ReferenceTypeService;
import ox.softeng.metadatacatalogue.restapi.services.ReportService;
import ox.softeng.metadatacatalogue.restapi.services.SharableService;
import ox.softeng.metadatacatalogue.restapi.services.TestService;
import ox.softeng.metadatacatalogue.restapi.services.UserGroupService;
import ox.softeng.metadatacatalogue.restapi.services.UserService;
import ox.softeng.metadatacatalogue.restapi.services.WorkflowService;


@ApplicationPath("api")
@DeclareRoles({"Administrator","Editor", "Unregistered"})
public class ApplicationConfig extends Application {

    public ApplicationConfig()
    {
    	
    }
	
	
    
    @Override
    public Set<Class<?>> getClasses() {
    	
        Set<Class<?>> resources = new HashSet<Class<?>>();

        // First we add the filters
        
        // The filter that provides user authentication
        resources.add(AuthenticationFilter.class);

        // The filter that ensures a connection to the database
        resources.add(DBConnectionFilter.class);

        // The listener that does basic profiling
        resources.add(ProfilingListener.class);
      
        // The filter that adds CORS to responses
        resources.add(ResponseCorsFilter.class);

        resources.add(MultiPartFeature.class);
        resources.add(MultiPartReaderServerSide.class);
        
        resources.add(RolesAllowedDynamicFeature.class);
        

        // Now we add the services
        
        /* Test / ping service */
        resources.add(TestService.class);
        
        /* Domain services */
        resources.add(AnnotationService.class);
        resources.add(AuthenticationService.class);
        resources.add(CatalogueItemService.class);
        resources.add(ClassifierService.class);
        resources.add(DatabaseService.class);
        resources.add(DataClassService.class);
        resources.add(DataElementService.class);
        resources.add(DataModelComponentService.class);
        resources.add(DataModelService.class);
        resources.add(DataSetService.class);
        resources.add(DataStandardService.class);
        resources.add(DataTypeService.class);
        resources.add(EnumerationTypeService.class);
        resources.add(EnumerationValueService.class);
        resources.add(FinalisableService.class);
        resources.add(FormService.class);
        resources.add(LinkService.class);
        resources.add(MessageService.class);
        resources.add(MetadataService.class);
        resources.add(PrimitiveTypeService.class);
        resources.add(ReferenceTypeService.class);
        resources.add(ReportService.class);
        resources.add(SharableService.class);
        resources.add(UserGroupService.class);
        resources.add(UserService.class);
        resources.add(WorkflowService.class);
                
        

        return resources;
    }
	
}