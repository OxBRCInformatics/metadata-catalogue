package ox.softeng.metadatacatalogue.db;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConnectionProvider {

        private static final Logger logger = LoggerFactory.getLogger(ConnectionProvider.class);
        
        private Properties properties;
        
        public ConnectionProvider(Properties props)
        {
                properties = props;
        }
        
        public EntityManagerFactory newConnection()
        {
                logger.info("Establishing new database connection...");
                
                // Create our JPA database connection, overriding properties if necessary
                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "ox.softeng.metadatacatalogue", properties );
                logger.info(entityManagerFactory.toString());
                
                if(entityManagerFactory == null || !entityManagerFactory.isOpen())
                {
                        logger.error("Cannot establish database connection!");
                        System.exit(0);
                }
                else
                {
                        logger.info("Database connection established!");
                }
                return entityManagerFactory;
        }
        
        
}