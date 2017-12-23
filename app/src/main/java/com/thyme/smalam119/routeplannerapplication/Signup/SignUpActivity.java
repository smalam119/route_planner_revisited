package com.thyme.smalam119.routeplannerapplication.Signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thyme.smalam119.routeplannerapplication.Map.InputMap.MainActivity;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.Alerts;
import com.thyme.smalam119.routeplannerapplication.Utils.BasicProgressBar;
import com.thyme.smalam119.routeplannerapplication.Utils.LoginRegistrationManager.OnRegistrationListener;
import com.thyme.smalam119.routeplannerapplication.Utils.LoginRegistrationManager.RegistrationManager;
import com.thyme.smalam119.routeplannerapplication.Utils.Validations;

public class SignUpActivity extends AppCompatActivity implements OnRegistrationListener {
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSignUpButton;
    private Validations mValidations;
    private BasicProgressBar mBasicProgressBar;
    private RegistrationManager mRegistrationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        prepareView();
    }

    private void prepareView() {
        prepareUtils();
        mBasicProgressBar = new BasicProgressBar(this,"Loading");
        mEmailEditText = (EditText) findViewById(R.id.email_edit_text_sign_up);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text_sign_up);
        mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                if(mValidations.isEmpty(email) || mValidations.isEmpty(password)){
                    Toast.makeText(SignUpActivity.this,"fields can not be empty",Toast.LENGTH_LONG).show();
                } else {
                    if(mValidations.isValidEmail(email)) {
                        mRegistrationManager.register(email,password);
                    } else {
                        Toast.makeText(SignUpActivity.this,"not a valid mail address",Toast.LENGTH_LONG).show();
                        mEmailEditText.setError("not a valid mail address");
                    }
                }
            }
        });
    }

    public void prepareUtils() {
        mRegistrationManager = new RegistrationManager(this);
        mRegistrationManager.onRegistrationListener = this;
        mValidations = new Validations();
    }

    @Override
    public void onRegistrationSuccess() {
        mBasicProgressBar.hide();
        Toast.makeText(this,"New User Created!!!",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onRegistrationFailed(String message) {
        mBasicProgressBar.hide();
        Alerts.simpleAlertWithMessage(this, "Sign up Failed",message,"Retry");
    }
}
