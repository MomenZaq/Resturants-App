package com.resturants.resturantsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.resturants.resturantsapp.R;

public class LoginActivity extends ParentActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
                    linearRegister.setVisibility(View.GONE);
                    linearRecover.setVisibility(View.GONE);
                    linearLogin.setVisibility(View.VISIBLE);
                    btnRegister.setText(getResources().getString(R.string.register));

                } else {
                    linearLogin.setVisibility(View.GONE);
                    linearRecover.setVisibility(View.GONE);
                    linearRegister.setVisibility(View.VISIBLE);
                    btnRegister.setText(getResources().getString(R.string.login));

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

    }
}
