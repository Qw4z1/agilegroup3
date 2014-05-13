package se.group3.navigatorslittlehelper.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import se.group3.navigatorslittlehelper.app.LoginActivity;
import se.group3.navigatorslittlehelper.app.R;

/**
 * Created by qw4z1 on 5/13/14.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity mActivityInstance;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mActivityInstance = getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @SmallTest
    public void testButtonsNotNull() {
        assertNotNull(mActivityInstance.findViewById(R.id.login_button));
        assertNotNull(mActivityInstance.findViewById(R.id.login_forgot_password_button));
    }

    @SmallTest
    public void testEditTextsNotNull() {
        assertNotNull(mActivityInstance.findViewById(R.id.login_username_edittext));
        assertNotNull(mActivityInstance.findViewById(R.id.login_password_edittext));
    }

    @MediumTest
    public void testClickLogin_empty_password() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityInstance.findViewById(R.id.login_button).performClick();
            }
        });

        getInstrumentation().waitForIdleSync();
        assertNotNull(mActivityInstance);
    }

}
