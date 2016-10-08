package com.mlabs.bbm.firstandroidapp_morningclass;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Creepy Pasta on 9/27/2016.
 */
public class Register {

    private EditText fname, lname, username, email, password, cpassword;
    private Context context;
    private TextView alertPassword;

    public Register(Context context, EditText fname, EditText lname, EditText username, EditText email, EditText password, EditText cpassword, TextView alertPassword){
        this.context = context;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.cpassword = cpassword;
        this.alertPassword = alertPassword;
    }

    public boolean isRegistrationSuccessful(){
        User user = new User();
        UsersDataSource usersDataSource = new UsersDataSource(context);
        usersDataSource.open();

        if(fname.getText().toString().equals("")){
            fname.setError(String.format("%s","Please enter first name"));
        }else{
            user.setFname(fname.getText().toString());
        }

        if(lname.getText().toString().equals("")){
            lname.setText("");
            lname.setError(String.format("%s","Please enter last name"));
        }else{
            user.setLname(lname.getText().toString());
        }

        if(username.getText().toString().equals("")){
            username.setError(String.format("%s","Please enter username"));
        }else {
            if(usersDataSource.ifUsernameIsAvailable(username.getText().toString()))
                user.setUname(username.getText().toString());
            else {
                username.setError(String.format("%s", "Username not available"));
            }
        }

        if(email.getText().toString().equals("")){
            email.setError(String.format("%s","Please enter email address"));
        }else{
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                email.setError(String.format("%s", "Invalid email format"));
            }else{
                if(usersDataSource.ifEmailIsAvailable(email.getText().toString()))
                    user.setEmail(email.getText().toString());
                else {
                    email.setError(String.format("%s", "Try another email"));
                }
            }

        }

        if(password.getText().toString().equals("") || password.getText().toString().length() < 8){
            alertPassword.setText(String.format("%s","Password must be more than 7 characters"));
        }else{
            if(cpassword.getText().toString().equals("") || !password.getText().toString().equals(cpassword.getText().toString())){
                alertPassword.setText(String.format("%s","Passwords do not match"));
            }else{
                user.setPassword(password.getText().toString());
            }
        }

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        user.setDate(formattedDate);
        boolean success = false;
        try{
            if(!user.getFname().equals("") && !user.getLname().equals("") && !user.getUname().equals("") && !user.getEmail().equals("") && !user.getPassword().equals("")) {
                usersDataSource.createUser(user.getFname(), user.getLname(), user.getUname(), user.getEmail(), user.getPassword(), user.getDate());
                usersDataSource.close();
                success = true;
            }
        }catch (NullPointerException npe){
            Toast.makeText(context, ""+String.format("%s","Please complete the registration form"), Toast.LENGTH_SHORT).show();
        }

        return success;
    }
}
