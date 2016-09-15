package ox.softeng.metadatacatalogue.test.restapi.service;

import static org.junit.Assert.assertTrue;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import ox.softeng.metadatacatalogue.domain.core.UserGroup;
import ox.softeng.metadatacatalogue.test.restapi.APITest;

public class UserGroupServiceIT extends APITest<UserGroup> {

	/* These methods are not really needed for this service - we'll give some default implementations */
	
	@Override
	protected UserGroup getInstance() throws Exception {
		return null;
	}


	@Override
	protected String getServicePath() {
		return "/usergroup";
	}


	@Override
	protected Class<? extends UserGroup> getClazz() {
		return UserGroup.class;
	}
	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void createUserGroup() throws Exception {
		
		LoginResponse lr = doLogin();
		
		UserGroup newUserGroup = new UserGroup();
		newUserGroup.setGroupName("Test UserGroup");

		UserGroup ugReturned = assertSuccessfulPost("/usergroup/create", lr.cookie, newUserGroup, UserGroup.class);

		assertTrue(ugReturned.getGroupName().equalsIgnoreCase("Test UserGroup"));
		doLogout(lr.cookie);
	}
	
}
