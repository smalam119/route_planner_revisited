package com.thyme.smalam119.routeplannerapplication.Utils.Firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.thyme.smalam119.routeplannerapplication.Utils.BasicProgressBar;


/**
 * Created by smalam119 on 11/25/17.
 */

public class FirebaseUtils {

    public FirebaseAuth mAuth;
    private Activity mActivity;
    private BasicProgressBar mBasicProgressBar;

    public FirebaseUtils(Activity activity) {
        this.mActivity = activity;
        mAuth = FirebaseAuth.getInstance();
        mBasicProgressBar = new BasicProgressBar(activity,"Loading");
    }

    public void createUser(String email, String password){
        mBasicProgressBar.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mBasicProgressBar.hide();
                        Toast.makeText(mActivity,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mBasicProgressBar.hide();
                        if(task.isSuccessful()) {
                            Toast.makeText(mActivity,"New User Created!!!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void signInUser(String email, String password){
        mBasicProgressBar.show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mBasicProgressBar.hide();
                        Toast.makeText(mActivity,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mBasicProgressBar.hide();
                        if(task.isSuccessful()) {
                            Toast.makeText(mActivity,"Login Success!!!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
