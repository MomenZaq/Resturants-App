package com.resturants.resturantsapp.activities;

import androidx.core.app.ActivityOptionsCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.utils.Methods;
import com.resturants.resturantsapp.utils.SharedPreferensessClass;

public class LoginActivity extends ParentActivity {
    private static final String TAG = "LoginActivity";
    private FrameLayout frameLoginData;
    private LinearLayout linearLogin;
    private EditText edtLoginEmail;
    private EditText edtLoginPassword;
    private Button btnLogin;
    private Button btnForgetPassword;
    private LinearLayout linearRegister;
    private EditText edtRegisterName;
    private EditText edtRegisterEmail;
    private EditText edtRegisterPassword;
    private EditText edtRegisterPasswordConfirm;
    private Button btnRegisterNow;
    private EditText edtRecoverEmail;
    private Button btnSend;
    private ImageView imageView;
    private Button btnRegister;
    private Button btnSkip;
    private LinearLayout linearRecover;
    ProgressBar progress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //check if the user already signed go to main.
        if (!SharedPreferensessClass.getInstance(getBaseContext()).getUserEmail().equals("")) {

            Intent intent = new Intent(this, MainActivity.class);
            // create fade animation
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(this,
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            startActivity(intent, bundle);
            finish();
        }


        frameLoginData = (FrameLayout) findViewById(R.id.frame_login_data);
        linearLogin = (LinearLayout) findViewById(R.id.linear_login);
        edtLoginEmail = (EditText) findViewById(R.id.edt_login_email);
        edtLoginPassword = (EditText) findViewById(R.id.edt_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnForgetPassword = (Button) findViewById(R.id.btn_forget_password);
        linearRegister = (LinearLayout) findViewById(R.id.linear_register);
        edtRegisterName = (EditText) findViewById(R.id.edt_register_name);
        edtRegisterEmail = (EditText) findViewById(R.id.edt_register_email);
        edtRegisterPassword = (EditText) findViewById(R.id.edt_register_password);
        edtRegisterPasswordConfirm = (EditText) findViewById(R.id.edt_register_password_confirm);
        btnRegisterNow = (Button) findViewById(R.id.btn_register_now);
        edtRecoverEmail = (EditText) findViewById(R.id.edt_recover_email);
        btnSend = (Button) findViewById(R.id.btn_send);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        linearRecover = (LinearLayout) findViewById(R.id.linear_recover);
        progress = (ProgressBar) findViewById(R.id.progress);


        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLogin.setVisibility(View.GONE);
                linearRegister.setVisibility(View.GONE);
                linearRecover.setVisibility(View.VISIBLE);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnRegister.getText().equals(getResources().getString(R.string.login))) {
                    displayLogin();
                } else {
                    displayRegistrations();

                }
            }
        });


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                // create fade animation
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(LoginActivity.this,
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
                finish();
            }
        });


        btnLogin.setOnClickListener(v -> signInValidation());
        btnRegisterNow.setOnClickListener(v -> signUpValidation());
        btnSend.setOnClickListener(v -> forgetValidation());

    }

    private void displayRegistrations() {
        linearLogin.setVisibility(View.GONE);
        linearRecover.setVisibility(View.GONE);
        linearRegister.setVisibility(View.VISIBLE);
        btnRegister.setText(getResources().getString(R.string.login));
    }

    private void displayLogin() {
        linearRegister.setVisibility(View.GONE);
        linearRecover.setVisibility(View.GONE);
        linearLogin.setVisibility(View.VISIBLE);
        btnRegister.setText(getResources().getString(R.string.register));
    }

    private void forgetValidation() {

        String email = edtRecoverEmail.getText().toString();

        if (email.isEmpty() || !Methods.isValidEmail(email)) {
            edtRecoverEmail.setError(getResources().getString(R.string.enter_valid_email_address));
            return;
        }


        recoverPassword(email);
    }


    private void signUpValidation() {
        String email = edtRegisterEmail.getText().toString();
        String name = edtRegisterName.getText().toString();
        String password = edtRegisterPassword.getText().toString();
        String passwordConfirm = edtRegisterPasswordConfirm.getText().toString();

        try {

            if (email.isEmpty() || !Methods.isValidEmail(email)) {
                edtRegisterEmail.setError(getResources().getString(R.string.enter_valid_email_address));
                return;
            }


            if (password.length() < 5) {
                edtRegisterPassword.setError(getResources().getString(R.string.enter_password_more_than_five_letters));
                return;

            }
            if (name.length() < 4) {

                edtRegisterName.setError(getResources().getString(R.string.enter_name_more_than_four_letters));
                return;

            }

            if (!password.equals(passwordConfirm)) {
                edtRegisterPasswordConfirm.setError(getResources().getString(R.string.enter_same_password));
                return;
            }

            signUpEmail(this, email, name, password);
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_valid_data), Toast.LENGTH_SHORT).show();
        }
    }

    private void signInValidation() {

        String email = edtLoginEmail.getText().toString();
        String password = edtLoginPassword.getText().toString();

        if (email.isEmpty() || !Methods.isValidEmail(email)) {
            edtLoginEmail.setError(getResources().getString(R.string.enter_valid_email_address));
            return;
        }

        if (password.length() < 5) {
            edtLoginPassword.setError(getResources().getString(R.string.enter_password_more_than_five_letters));
            return;
        }

        signInEmail(email, password);

    }


    private void updateUI(FirebaseUser account) {
        SharedPreferensessClass.getInstance(this).setUserName(account.getDisplayName());
        SharedPreferensessClass.getInstance(this).setUserEmail(account.getEmail());


        Toast.makeText(this, getString(R.string.successfully_sign_in), Toast.LENGTH_SHORT).show();
        btnSkip.callOnClick();
    }


    private void showProcess() {
        progress.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);
        btnRegisterNow.setEnabled(false);
        btnRegister.setEnabled(false);
        btnForgetPassword.setEnabled(false);
        btnSend.setEnabled(false);
    }

    private void hideProcess() {
        progress.setVisibility(View.GONE);
        btnLogin.setEnabled(true);
        btnRegisterNow.setEnabled(true);
        btnRegister.setEnabled(true);
        btnForgetPassword.setEnabled(true);
        btnSend.setEnabled(true);
    }

    private void signInEmail(String email, String password) {
        showProcess();

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        hideProcess();
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            edtLoginEmail.setError(getResources().getString(R.string.email_is_not_registered_you_can_sign_up));

                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Log.d("SIGNINEMAIL", "email :" + email);
                            edtLoginPassword.setError(getResources().getString(R.string.wrong_password));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.w("SIGNINEMAIL", "signInWithEmail:failed", task.getException());


                    }
                });

    }

    private void recoverPassword(String email) {
        showProcess();

        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, task -> {
                    hideProcess();
                    if (task.isSuccessful()) {
                        Toast.makeText(this, getResources().getString(R.string.send_done), Toast.LENGTH_SHORT).show();
                        displayLogin();
                    } else {
                        Log.w("SIGNINEMAIL", "signInWithEmail:failed", task.getException());
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException weakPassword) {
                            Log.d(TAG, "onComplete: weak_password");

                            edtRecoverEmail.setError(getResources().getString(R.string.email_not_found));

                        } catch (Exception e) {
                            Toast.makeText(this, getResources().getString(R.string.send_done_error), Toast.LENGTH_SHORT).show();
                        }
//

                    }
                });
    }

    public void signUpEmail(Context context, String email, String name, String password) {
        try {
            showProcess();
            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            hideProcess();
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                Log.d(TAG, "onComplete: weak_password");

                                edtRegisterPassword.setError(getResources().getString(R.string.please_enter_strong_password));

                            } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                Log.d(TAG, "onComplete: malformed_email");

                                // TODO: Take your action
                                edtRegisterEmail.setError(getResources().getString(R.string.enter_valid_email_address));

                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                Log.d(TAG, "onComplete: exist_email");
                                edtRegisterEmail.setError(getResources().getString(R.string.the_email_is_already_registered_you_can_sign_in));
                            } catch (Exception e) {
                                Log.d(TAG, "onComplete: " + e.getMessage());
                            }
                        }

                        // ...

                    });

        } catch (Exception e) {
            Toast.makeText(context, getResources().getString(R.string.error_in_registration), Toast.LENGTH_SHORT).show();
            hideProcess();

        }
    }

}
