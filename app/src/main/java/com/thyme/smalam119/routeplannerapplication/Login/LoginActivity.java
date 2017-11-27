package com.thyme.smalam119.routeplannerapplication.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.thyme.smalam119.routeplannerapplication.MainActivity;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Signup.SignUpActivity;
import com.thyme.smalam119.routeplannerapplication.Utils.Facebook.RPAFacebookAccessTokenTracker;
import com.thyme.smalam119.routeplannerapplication.Utils.Facebook.RPAFacebookCallBack;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FirebaseUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.SharedPrefUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.Validations;

public class LoginActivity extends AppCompatActivity {

    //views
    private LoginButton facebookLoginButton;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button okayButton;
    private TextView mSignUpLinkTV;

    //facebook
    private FacebookCallback<LoginResult> mFacebookCallback;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;

    //utils
    private SharedPrefUtils mSharedPrefUtils;
    private FirebaseUtils mFirebaseUtils;
    private Validations mValidations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prepareView();
        checkExistingUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void prepareView() {
        prepareFacebookLogin();
        prepareUtils();
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
                        mFirebaseUtils.signInUser(email,password);
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
        mSharedPrefUtils = new SharedPrefUtils(this);
        mFirebaseUtils = new FirebaseUtils(this);
        mValidations = new Validations();
    }

    private void checkExistingUser() {
        if(mSharedPrefUtils.getToken() == null) {

        } else {
            startActivity(new Intent(this, MainActivity.class));
        }

        if(mFirebaseUtils.getCurrentUser() == null) {

        } else {
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "welcome" + mFirebaseUtils.getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();
        }
    }

}
