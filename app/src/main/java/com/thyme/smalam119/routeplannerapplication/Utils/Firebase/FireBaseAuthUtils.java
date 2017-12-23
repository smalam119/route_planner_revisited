package com.thyme.smalam119.routeplannerapplication.Utils.Firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thyme.smalam119.routeplannerapplication.Utils.LoginRegistrationManager.LoginManager;
import com.thyme.smalam119.routeplannerapplication.Utils.LoginRegistrationManager.RegistrationManager;


/**
 * Created by smalam119 on 11/25/17.
 */

public class FireBaseAuthUtils {

    public FirebaseAuth mAuth;
    private Activity mActivity;
    private LoginManager mLoginManager;
    private RegistrationManager mRegistrationManager;

    public FireBaseAuthUtils(Activity activity) {
        mAuth = FirebaseAuth.getInstance();
    }

    public FireBaseAuthUtils(LoginManager loginManager, Activity activity) {
        this.mLoginManager = loginManager;
        this.mActivity = activity;
        mAuth = FirebaseAuth.getInstance();
    }

    public FireBaseAuthUtils(RegistrationManager registrationManager, Activity activity) {
        this.mRegistrationManager = registrationManager;
        this.mActivity = activity;
        mAuth = FirebaseAuth.getInstance();
    }

    public void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mRegistrationManager.onRegistrationListener.onRegistrationFailed(e.getMessage());

                    }
                })
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            mRegistrationManager.onRegistrationListener.onRegistrationSuccess();
                        }
                    }
                });
    }

    public void signInUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mLoginManager.onLoginListener.onLoginFailed(e.getMessage());
                    }
                })
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            mLoginManager.onLoginListener.onLoginSuccess();
                        }
                    }
                });
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public void logOut() {
        mAuth.signOut();
    }
}
