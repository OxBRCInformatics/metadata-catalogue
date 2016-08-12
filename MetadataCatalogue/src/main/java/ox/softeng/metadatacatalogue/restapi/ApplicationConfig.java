package ox.softeng.metadatacatalogue.restapi;


import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import ox.softeng.metadatacatalogue.restapi.filters.AuthenticationFilter;
import ox.softeng.metadatacatalogue.restapi.filters.DBConnectionFilter;
import ox.softeng.metadatacatalogue.restapi.filters.ProfilingListener;
import ox.softeng.metadatacatalogue.restapi.services.AuthenticationService;
import ox.softeng.metadatacatalogue.restapi.services.DataModelService;
import ox.softeng.metadatacatalogue.restapi.services.TestService;

//import io.swagger.jaxrs.listing.ApiListingResource;
//import io.swagger.jaxrs.listing.SwaggerSerializers;


@ApplicationPath("api")
@DeclareRoles({"Administrator","Editor", "Unregistered"})
public class ApplicationConfig extends Application {

    
    
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
        
        resources.add(RolesAllowedDynamicFeature.class);
        

        // Now we add the services
        
        resources.add(TestService.class);
        
        resources.add(DataModelService.class);
        
        resources.add(AuthenticationService.class);

        /*
        resources.add(ClassifierService.class);
        resources.add(DataClassService.class);
        resources.add(DataElementService.class);
        resources.add(DataTypeService.class);
        resources.add(DataSetService.class);
        resources.add(SelectionService.class);
        resources.add(UserService.class);
        
        resources.add(HibernateMapperProvider.class);
        



        // The filter that adds CORS to responses
        resources.add(ResponseCorsFilter.class);
        
        // Swagger classes
        resources.add(ApiListingResource.class);
        resources.add(SwaggerSerializers.class);
*/
        return resources;
    }
	
}