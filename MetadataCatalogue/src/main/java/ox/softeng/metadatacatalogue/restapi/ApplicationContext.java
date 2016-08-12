package ox.softeng.metadatacatalogue.restapi;

import java.io.InputStream;
import java.security.Key;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.Response;

import org.flywaydb.core.Flyway;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ox.softeng.metadatacatalogue.api.ApiContext;

public class ApplicationContext implements ServletContextListener
{
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("Destroying context...");
		ApiContext apiCtx = (ApiContext) event.getServletContext().getAttribute("masterApiContext");
		if(apiCtx != null)
		{
			apiCtx.close();
		}
		event.getServletContext().removeAttribute("masterApiContext");
		
	}


	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		Properties props = getProperties(event);

		// Migrate the database to the latest version if necessary,
		// Using flyway
		migrateData(props);
		
		// Generate a new token key
		generateTokenKey(event);

		initialiseDatabaseConnection(event.getServletContext(), props);

	}
	
	private Properties getProperties(ServletContextEvent event)
	{
		Properties props = new Properties();
		
		try
		{
			InputStream inputStream = event.getServletContext().getResourceAsStream("/META-INF/persistence.properties");
			props.load(inputStream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			props = new Properties();
		}
		event.getServletContext().setAttribute("entityManagerProps", props);
		return props;
	}

	public void initialiseDatabaseConnection(ServletContext context, Properties props)
	{
		logger.info("Intialising database connection...");
		try{
			ApiContext apiCtx = ApiContext.getMasterApiContext(props);
			context.setAttribute("masterApiContext", apiCtx);
		}
		catch(Exception e){
			logger.error("Unable to connect to system database!");
			e.printStackTrace();
			throw new javax.ws.rs.NotAuthorizedException("Unable to connect to system database", Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
		}

	}
	
	
	private void migrateData(Properties props)
	{
		 logger.info("Migrating database...");
	     
	     Flyway flyway = new Flyway();
	     flyway.setDataSource(props.getProperty("hibernate.connection.url"), props.getProperty("hibernate.connection.username"), props.getProperty("hibernate.connection.password"));
	     //flyway.setBaselineVersionAsString("1_3_1");
	     flyway.setLocations("ox.softeng.metadatacatalogue");
	     flyway.setBaselineOnMigrate(true);
	     flyway.migrate();
	     logger.info("Database migration complete");
	}
	
	private void generateTokenKey(ServletContextEvent event)
	{
		logger.info("Generating new token key...");
		Key key = new AesKey(ByteUtil.randomBytes(16));
		event.getServletContext().setAttribute("tokenKey", key);
	}
	
}