package com.resturants.resturantsapp.activities;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.utils.Methods;
import com.resturants.resturantsapp.utils.SharedPreferensessClass;

public class SettingActivity extends ParentActivity {

    private static final String TAG = "SettingActivity";
    private CardView cardviewProfile;
    private EditText edtRegisterName;
    private EditText edtRegisterEmail;
    private EditText edtRegisterPassword;
    private Button btnSave;
    private Button btnCancel;
    private Button btnSaveDistance;
    private Button btnCancelDistance;
    private ConstraintLayout constraintLayout;
    private ImageView imgLogout;
    private ImageView imgBack;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        cardviewProfile = (CardView) findViewById(R.id.cardview_profile);
        edtRegisterName = (EditText) findViewById(R.id.edt_register_name);
        edtRegisterEmail = (EditText) findViewById(R.id.edt_register_email);
        edtRegisterPassword = (EditText) findViewById(R.id.edt_register_password);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSaveDistance = (Button) findViewById(R.id.btn_save_distance);
        btnCancelDistance = (Button) findViewById(R.id.btn_cancel_distance);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        imgLogout = (ImageView) findViewById(R.id.img_logout);
        imgBack = (ImageView) findViewById(R.id.img_back);
        progress = (ProgressBar) findViewById(R.id.progress);


        if (!SharedPreferensessClass.getInstance(getBaseContext()).getUserEmail().equals("")) {
            edtRegisterName.setText(SharedPreferensessClass.getInstance(getBaseContext()).getUserName());
            edtRegisterEmail.setText(SharedPreferensessClass.getInstance(getBaseContext()).getUserEmail());
            edtRegisterPassword.setText(SharedPreferensessClass.getInstance(getBaseContext()).getUserPass());
            btnCancel.setEnabled(true);
            btnSave.setEnabled(true);


            btnSave.setOnClickListener(v -> {

                dataValidation();
                closeKeyboard();
                showProcess();
            });
            btnCancel.setOnClickListener(v -> closeKeyboard());


        } else {
            btnCancel.setEnabled(false);
            btnSave.setEnabled(false);


        }


    }


    private void showProcess() {
        progress.setVisibility(View.VISIBLE);
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);
    }

    private void hideProcess() {
        progress.setVisibility(View.GONE);
        btnSave.setEnabled(true);
        btnCancel.setEnabled(true);
    }

    public void closeKeyboard() {
        try {
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(edtRegisterEmail.getWindowToken(), 0);

        } catch (Exception e) {
        }
    }

    private void dataValidation() {
        String email = edtRegisterEmail.getText().toString();
        String name = edtRegisterName.getText().toString();
        String password = edtRegisterPassword.getText().toString();

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


            signInByUserData(this, email, name, password);
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_valid_data), Toast.LENGTH_SHORT).show();
        }
    }

    public void signInByUserData(Context context, String email, String name, String password) {
        try {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            mAuth.signInWithEmailAndPassword(SharedPreferensessClass.getInstance(getBaseContext()).getUserEmail(), SharedPreferensessClass.getInstance(getBaseContext()).getUserPass())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            changeUserData(context, email, name, password);
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.error_in_registration), Toast.LENGTH_SHORT).show();
                            hideProcess();
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {

                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Log.d("SIGNINEMAIL", "email :" + email);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Log.w("SIGNINEMAIL", "signInWithEmail:failed", task.getException());


                        }
                    });


        } catch (Exception e) {

            hideProcess();
            e.printStackTrace();
            Toast.makeText(context, getResources().getString(R.string.error_in_registration), Toast.LENGTH_SHORT).show();

        }
    }

    public void changeUserData(Context context, String email, String name, String password) {
        try {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            //Now update email
            mAuth.getCurrentUser().updateEmail(email)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {

                            //Now update password
                            mAuth.getCurrentUser().updatePassword(password)
                                    .addOnCompleteListener(this, task2 -> {
                                        if (task2.isSuccessful()) {
                                            updateUI(email, name, password);
                                        } else {
                                            handleError(task2);
                                        }

                                        // ...

                                    });

                        } else {
                            handleError(task);
                        }

                        // ...

                    });


        } catch (Exception e) {
            hideProcess();
            e.printStackTrace();
            Toast.makeText(context, getResources().getString(R.string.error_in_registration), Toast.LENGTH_SHORT).show();

        }
    }

    private void handleError(Task<Void> task) {
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
        hideProcess();
        Toast.makeText(this, getResources().getString(R.string.error_in_registration), Toast.LENGTH_SHORT).show();
    }

    private void updateUI(String email, String name, String password) {
        System.out.println("THEACCOUNT: " + name);
        System.out.println("THEACCOUNT2: " + email);
        if (name == null) {
            name = email.split("@")[0];
        }
        SharedPreferensessClass.getInstance(this).setUserName(name);
        SharedPreferensessClass.getInstance(this).setUserEmail(email);
        SharedPreferensessClass.getInstance(this).setUserPass(password);
        Toast.makeText(this, getString(R.string.successfully_update_user_data), Toast.LENGTH_SHORT).show();
        hideProcess();
    }

    public void logout(View view) {
        logout();
    }

    public void back(View view) {
        onBackPressed();
    }
}
