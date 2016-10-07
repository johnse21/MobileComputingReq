package com.mlabs.bbm.firstandroidapp_morningclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Registration extends AppCompatActivity {

    private EditText fnameEdit, lnameEdit, unameEdit, emailEdit, passwordEdit, cpasswordEdit;
    private Button regBtn;
    private TextView alertPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fnameEdit = (EditText)findViewById(R.id.fnameEditTxt);
        lnameEdit = (EditText)findViewById(R.id.lnameEditTxt);
        unameEdit = (EditText)findViewById(R.id.unameEditTxt);
        emailEdit = (EditText)findViewById(R.id.emailEditTxt);
        alertPassword = (TextView)findViewById(R.id.alertPassword);
        passwordEdit = (EditText)findViewById(R.id.passwordEditTxt);
        cpasswordEdit = (EditText)findViewById(R.id.cpasswordEditTxt);
        regBtn = (Button)findViewById(R.id.regBtn);

        regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Register register = new Register(getApplicationContext(), fnameEdit, lnameEdit, unameEdit, emailEdit, passwordEdit, cpasswordEdit, alertPassword);

                if(register.isRegistrationSuccessful()){
                    Intent intent = new Intent(Registration.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
