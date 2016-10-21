package ox.softeng.metadatacatalogue.test.domain.core;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.ConnectionProvider;

import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/context/simple_applicationContext.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, 
                         FlywayTestExecutionListener.class })

public abstract class DatabaseTest {

	static ApiContext apiCtx;
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseTest.class);
	
	@Before
	public void Before() throws Exception
	{
		ConnectionProvider cp = new ConnectionProvider(null);
		// Get the Bootstrap user
		logger.info("Creating new database connection!!");
		apiCtx = new ApiContext(cp, cp.newConnection(), "admin@metadatacatalogue.com", "password");
	}

	
	
}
