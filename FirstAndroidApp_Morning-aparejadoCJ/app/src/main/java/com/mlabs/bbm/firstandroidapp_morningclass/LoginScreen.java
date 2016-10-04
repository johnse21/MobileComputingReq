package com.mlabs.bbm.firstandroidapp_morningclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ChristianJohn on 8/4/2016.
 */
public class LoginScreen extends Activity{

    private Button loginBtn;
    private Login login;
    private EditText emailEdtTxt;
    private EditText passwordEdtTxt;
    private TextView emailTxtView;
    private TextView passwordTxtView;
    private TextView toggleTxt;
    private TextView signUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

       // UsersDataSource usersDataSource = new UsersDataSource(getApplicationContext());
       // usersDataSource.createDatabase();

        loginBtn = (Button)findViewById(R.id.loginBtn);
        emailEdtTxt = (EditText)findViewById(R.id.emailEditTxt);
        passwordEdtTxt = (EditText)findViewById(R.id.passwordEditTxt);
        emailTxtView = (TextView)findViewById(R.id.emailTextView);
        passwordTxtView = (TextView)findViewById(R.id.passwordTextView);
        toggleTxt = (TextView)findViewById(R.id.toggleTxt);
        signUpLink = (TextView)findViewById(R.id.signUpTxt);

        loginBtn.setOnClickListener(new BtnListener());
        emailEdtTxt.setOnTouchListener(new ETextListener());
        passwordEdtTxt.setOnTouchListener(new ETextListener());
        toggleTxt.setOnTouchListener(new ETextListener());
        signUpLink.setOnTouchListener(new ETextListener());
    }

    private class BtnListener implements View.OnClickListener{

        @Override
        public void onClick(View v){

            if(v.equals(loginBtn)){

                login = new Login(getApplicationContext(), emailTxtView, passwordTxtView);

                boolean isLoginValid = login.validateLogin(emailEdtTxt, passwordEdtTxt);

                if(isLoginValid){
                    Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    private class ETextListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.equals(emailEdtTxt))
                emailTxtView.setText("");


            if (v.equals(passwordEdtTxt))
                passwordTxtView.setText("");


            if(v.equals(toggleTxt)) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        passwordEdtTxt.setTransformationMethod(null);
                        passwordEdtTxt.setSelection(passwordEdtTxt.getText().length());
                        return true;
                    case MotionEvent.ACTION_UP:
                        passwordEdtTxt.setTransformationMethod(new PasswordTransformationMethod());
                        passwordEdtTxt.setSelection(passwordEdtTxt.getText().length());
                        return false;
                }
            }

            if(v.equals(signUpLink)){
                Intent intent = new Intent(LoginScreen.this, Registration.class);
                startActivity(intent);
            }

            return false;
        }
    }
}
