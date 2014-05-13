package se.group3.navigatorslittlehelper.test;

import junit.framework.TestCase;

import se.group3.navigatorslittlehelper.app.LoginHelper;

/**
 * Created by qw4z1 on 5/13/14.
 */
public class LoginHelperTest extends TestCase{

    private static final String NOT_EMPTY_PASSWORD = "NotEmpty";

    public void testValidation_empty_password() {
        boolean result = true;
        result = LoginHelper.isValidUsername("");
        assertFalse(result);
    }

    public void testValidation_not_empty_password() {
        boolean result = true;
        result = LoginHelper.isValidUsername(NOT_EMPTY_PASSWORD);
        assertTrue(result);
    }
    public void testValidation_empty_username() {
        boolean result = true;
        result = LoginHelper.isValidPassword("");
        assertFalse(result);
    }

    public void testValidation_not_empty_username() {
        boolean result = true;
        result = LoginHelper.isValidPassword(NOT_EMPTY_PASSWORD);
        assertTrue(result);
    }

}
