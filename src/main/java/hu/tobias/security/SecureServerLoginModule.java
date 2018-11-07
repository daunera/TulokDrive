package hu.tobias.security;

import org.jboss.security.auth.spi.DatabaseServerLoginModule;
import org.mindrot.jbcrypt.BCrypt;

public class SecureServerLoginModule extends DatabaseServerLoginModule {
		
	@Override
	protected boolean validatePassword(String inputPassword, String expectedPassword) {
		if (inputPassword == null || expectedPassword == null)
			return false;
		return BCrypt.checkpw(inputPassword, expectedPassword);
	}

}
