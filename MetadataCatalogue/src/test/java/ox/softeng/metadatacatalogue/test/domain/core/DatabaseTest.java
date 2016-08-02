package ox.softeng.metadatacatalogue.test.domain.core;

import ox.softeng.metadatacatalogue.api.ApiContext;

import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.BeforeClass;
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
	
	@BeforeClass
	public static void BeforeClass()
	{
		apiCtx = new ApiContext(null, null);
	}

	
}
