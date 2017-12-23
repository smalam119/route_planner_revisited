package com.thyme.smalam119.routeplannerapplication.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.thyme.smalam119.routeplannerapplication.Map.InputMap.MainActivity;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Signup.SignUpActivity;
import com.thyme.smalam119.routeplannerapplication.Utils.Alerts;
import com.thyme.smalam119.routeplannerapplication.Utils.BasicProgressBar;
import com.thyme.smalam119.routeplannerapplication.Utils.Facebook.RPAFacebookAccessTokenTracker;
import com.thyme.smalam119.routeplannerapplication.Utils.Facebook.RPAFacebookCallBack;
import com.thyme.smalam119.routeplannerapplication.Utils.HandyFunctions;
import com.thyme.smalam119.routeplannerapplication.Utils.LoginRegistrationManager.LoginManager;
import com.thyme.smalam119.routeplannerapplication.Utils.LoginRegistrationManager.OnLoginListener;
import com.thyme.smalam119.routeplannerapplication.Utils.Permission.RuntimePermissionsActivity;
import com.thyme.smalam119.routeplannerapplication.Utils.Facebook.FBSharedPrefUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.Validations;

public class LoginActivity extends RuntimePermissionsActivity implements OnLoginListener {

    //views
    private LoginButton facebookLoginButton;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button okayButton;
    private TextView mSignUpLinkTV;
    private BasicProgressBar mBasicProgressBar;

    //facebook
    private FacebookCallback<LoginResult> mFacebookCallback;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;

    //utils
    private FBSharedPrefUtils mFBSharedPrefUtils;
    private Validations mValidations;
    private LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prepareFacebookLogin();
        prepareUtils();
        prepareView();
        initializeUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        Toast.makeText(this,"Location Permission Granted",Toast.LENGTH_LONG).show();
    }

    private void prepareView() {
        mBasicProgressBar = new BasicProgressBar(this,"Loading");
        mEmailEditText = (EditText) findViewById(R.id.email_edit_text);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text);
        mSignUpLinkTV = (TextView) findViewById(R.id.sign_up_link);
        mSignUpLinkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        okayButton = (Button) findViewById(R.id.okay_button);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                if(mValidations.isEmpty(email) || mValidations.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"fields can not be empty",Toast.LENGTH_LONG).show();
                } else {
                    if(mValidations.isValidEmail(email)) {
                        mBasicProgressBar.show();
                        mLoginManager.login(email,password);
                    } else {
                        Toast.makeText(LoginActivity.this,"not a valid mail address",Toast.LENGTH_LONG).show();
                        mEmailEditText.setError("not a valid mail address");
                    }
                }
            }
        });
    }

    private void prepareFacebookLogin() {
        facebookLoginButton = (LoginButton) findViewById(R.id.login_button_facebook);
        facebookLoginButton.setReadPermissions("email");
        mFacebookCallback = new RPAFacebookCallBack(this);
        mCallbackManager = CallbackManager.Factory.create();
        facebookLoginButton.registerCallback(mCallbackManager, mFacebookCallback);
        mAccessTokenTracker = new RPAFacebookAccessTokenTracker(this);
    }

    private void prepareUtils() {
        mFBSharedPrefUtils = new FBSharedPrefUtils(this);
        mLoginManager = new LoginManager(this);
        mLoginManager.onLoginListener = this;
        mValidations = new Validations();
    }

    private void checkExistingUser() {

        if(mLoginManager.getAuthKey() == null) {

        } else {
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "welcome" + " " + mLoginManager.getUserMail(),Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initializeUser() {
        if(HandyFunctions.isConnectedWithNetwork(this)) {
            LoginActivity.super.checkPermission();
            checkExistingUser();
        } else {
            Alerts.simpleAlert(this,"No Internet Found!!!","Settings");
        }
    }


    @Override
    public void onLoginSuccess() {
        mBasicProgressBar.hide();
        Toast.makeText(this,"Login Success!!!",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginFailed(String message) {
        mBasicProgressBar.hide();
        Alerts.simpleAlertWithMessage(this, "Login Failed",message,"Retry");
    }
}

