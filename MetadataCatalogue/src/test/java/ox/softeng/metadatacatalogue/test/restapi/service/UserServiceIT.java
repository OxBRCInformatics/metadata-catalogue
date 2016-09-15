package ox.softeng.metadatacatalogue.test.restapi.service;

import static org.junit.Assert.assertTrue;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import ox.softeng.metadatacatalogue.domain.core.User;
import ox.softeng.metadatacatalogue.test.restapi.APITest;

public class UserServiceIT extends APITest<User> {

	
	
	/* These methods are not really needed for this service - we'll give some default implementations */
	
	@Override
	protected User getInstance() throws Exception {
		return null;
	}


	@Override
	protected String getServicePath() {
		return "/user";
	}


	@Override
	protected Class<? extends User> getClazz() {
		return User.class;
	}
	
	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void createUser() throws Exception {
		
		LoginResponse lr = doLogin();
		
		User newUser = new User();
		objectMapper.writeValue(System.err, newUser);
		newUser.setFirstName("Test User First Name");
		objectMapper.writeValue(System.err, newUser);
		newUser.setLastName("Test User Last Name");
		objectMapper.writeValue(System.err, newUser);
		newUser.setEmailAddress("test@test2.com");
		objectMapper.writeValue(System.err, newUser);
		newUser.setPassword("My Password");
		objectMapper.writeValue(System.err, newUser);
		User uReturned = assertSuccessfulPost("/user/create", lr.cookie, newUser, User.class);

		assertTrue(uReturned.getFirstName().equalsIgnoreCase("Test User First Name"));
		assertTrue(uReturned.getLastName().equalsIgnoreCase("Test User Last Name"));

	}
	
}
