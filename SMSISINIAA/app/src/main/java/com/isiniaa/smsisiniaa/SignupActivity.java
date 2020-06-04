package com.isiniaa.smsisiniaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText editTextFullName, editTextUsername, editTextEmail, editTextPhone, editTextPassword, editTextConfirmPwd;
    Button registerBtn;
    TextView loginBtn;
    FirebaseAuth fAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String phonePattern = "(\\d{3}-){1,2}\\d{4}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextFullName = findViewById(R.id.id_FullName);
        editTextUsername = findViewById(R.id.id_Username);
        editTextEmail = findViewById(R.id.id_Mail);
        editTextPhone = findViewById(R.id.id_PhoneNumber);
        editTextPassword = findViewById(R.id.id_password);
        editTextConfirmPwd = findViewById(R.id.id_ConfirmPassword);
        registerBtn = findViewById(R.id.id_signupBtn);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        registerBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String email = editTextEmail.getText().toString().trim();
                String phonenumber = editTextPhone.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String passwordConf = editTextConfirmPwd.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    editTextEmail.setError("Email is Required.");
                    return;
                }

                if (email.matches(emailPattern)) {

                    //editTextEmail.setError("valid email address");
                    //Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                } else {
                    editTextEmail.setError("Invalid email address.");
                    //Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(phonenumber)){
                    editTextPhone.setError("Phone number is required.");
                }
                if(TextUtils.isEmpty(password)){
                    editTextPassword.setError("Password is Required.");
                    return;
                }

                if(TextUtils.isEmpty(passwordConf)){
                    editTextConfirmPwd.setError("Password Confirm is Required.");
                    return;
                }

                if (password.length() < 6){
                    editTextPassword.setError("The Password field must be at least 6 Characters.");
                    return;
                }

                if(password.equals(passwordConf)){

                }else {
                    editTextConfirmPwd.setError("The Confirm Password confirmation does not match.");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(SignupActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }


  //  public void onClick(View v) {

    //s}


}
