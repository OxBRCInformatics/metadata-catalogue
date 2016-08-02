package ox.softeng.metadatacatalogue.test.domain.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.Test;

import ox.softeng.metadatacatalogue.api.UserApi;
import ox.softeng.metadatacatalogue.domain.core.User;

public class UserTest extends DatabaseTest {

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void validatePasswordTest() throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		User u = createTestUser();
		assertTrue(u.validatePassword("password"));
		assertFalse(u.validatePassword("Password"));
		assertFalse(u.validatePassword("password1234"));
		assertFalse(u.validatePassword("password   "));
		assertFalse(u.validatePassword("   password"));
		assertFalse(u.validatePassword("pass   word"));
		
	}
	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void findUserWithEmail() throws Exception
	{
		createTestUser();
		assertTrue(validateTestUser("john.smith@test.com"));
		assertTrue(validateTestUser("  john.smith@test.com"));
		assertTrue(validateTestUser("john.smith@test.com  "));
		assertTrue(validateTestUser("  john.smith@test.com	"));
		assertTrue(validateTestUser("JOHN.SMITH@TEST.COM"));
		assertTrue(validateTestUser("John.Smith@Test.Com"));
		
	}

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void findNoUserWithEmail() throws Exception
	{
		createTestUser();
		assertTrue(matchesNoUser("j.smith@test.com"));
		assertTrue(matchesNoUser(""));
		assertTrue(matchesNoUser("*.*@test.com"));
		assertTrue(matchesNoUser("wohn.smith@test.com"));
		assertTrue(matchesNoUser("john.smith@test.comf"));
	}


	
	private User createTestUser() throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		User u = null;
		try {
			u = UserApi.createUser(apiCtx, "John", "Smith", "john.smith@test.com", "password", User.UserRole.User);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		assertTrue(u!=null);
		return u;
	}
	
	private boolean validateTestUser(String emailAddress) throws Exception
	{
		User u1 = UserApi.getByEmailAddress(apiCtx, emailAddress);
        if(u1 == null)
        {
        	return false;
        }

		assertTrue(u1 != null);
        assertTrue(u1.getFirstName().equals("John"));
        assertTrue(u1.getLastName().equals("Smith"));
        u1.getEmailAddress().equals("john.smith@test.com");
        assertTrue(u1.validatePassword("password"));
        assertTrue(u1.getUserRole() == User.UserRole.User);
        return true;
	}
	
	private boolean matchesNoUser(String emailAddress) throws Exception
	{
		User u1 = UserApi.getByEmailAddress(apiCtx, emailAddress);
        return (u1 == null);
	}
	
}
