package ox.softeng.metadatacatalogue.test.domain.core;

import ox.softeng.metadatacatalogue.api.ApiContext;
import ox.softeng.metadatacatalogue.db.ConnectionProvider;


import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Before;
import org.junit.runner.RunWith;
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
	
	@Before
	public void Before() throws Exception
	{
		ConnectionProvider cp = new ConnectionProvider(null);
		// Get the Bootstrap user
		apiCtx = new ApiContext(cp, cp.newConnection(), "admin@metadatacatalogue.com", "password");
	}

	
	
}
