package ox.softeng.metadatacatalogue.test.domain.core;

import ox.softeng.metadatacatalogue.api.ApiContext;
import ox.softeng.metadatacatalogue.api.UserApi;
import ox.softeng.metadatacatalogue.domain.core.User;


import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Assert;
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
		// Get the Bootstrap user
		apiCtx = new ApiContext(null, "admin@metadatacatalogue.com", "password");
	}

	
	
}
