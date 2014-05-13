package se.group3.navigatorslittlehelper.app;

import android.text.TextUtils;

/**
 * LoginHelper contains a set of business rules in the form of helper methods for validating login.
 * Created by qw4z1 on 5/13/14.
 */
public class LoginHelper {

    private static final int PASSWORD_MIN_LENGTH = 4;

    public static boolean isValidUsername(String password) {
        return !TextUtils.isEmpty(password) && password.length() > PASSWORD_MIN_LENGTH;
    }

    public static boolean isValidPassword(String username) {
        return !TextUtils.isEmpty(username);
    }

}
