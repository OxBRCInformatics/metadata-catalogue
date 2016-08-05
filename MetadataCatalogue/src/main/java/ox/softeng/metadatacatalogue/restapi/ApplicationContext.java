package ox.softeng.metadatacatalogue.restapi;

import java.io.InputStream;
import java.security.Key;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.Response;

import org.flywaydb.core.Flyway;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ox.softeng.metadatacatalogue.db.ConnectionProvider;

public class ApplicationContext implements ServletContextListener
{
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("Destroying context...");
		EntityManagerFactory entityManagerFactory = (EntityManagerFactory) event.getServletContext().getAttribute("entityManagerFactory");
		if(entityManagerFactory != null && entityManagerFactory.isOpen())
		{
			entityManagerFactory.close();
		}
		event.getServletContext().removeAttribute("entityManagerFactory");
	}


	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		Properties props = getProperties(event);

		// Migrate the database to the latest version if necessary,
		// Using flyway
		migrateData(props);
		
		// Generate a new token key
		generateTokenKey(event);

		initialiseDatabaseConnection(event, props);
		


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
			props = new Properties();
		}
		event.getServletContext().setAttribute("entityManagerProps", props);
		return props;
	}

	private void initialiseDatabaseConnection(ServletContextEvent event, Properties props)
	{
		System.out.println("Intialising database connection...");
		try{
			ConnectionProvider cp = new ConnectionProvider(props);
			EntityManagerFactory emf = cp.newConnection();
			event.getServletContext().setAttribute("entityManagerFactory", emf);
		}
		catch(Exception e){
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
		System.out.println("Generating new token key...");
		Key key = new AesKey(ByteUtil.randomBytes(16));
		event.getServletContext().setAttribute("tokenKey", key);
	}
	
}