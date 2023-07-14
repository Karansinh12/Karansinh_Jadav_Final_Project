package com.example.karansinh_jadav_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    private FirebaseAuth auth;
    private EditText signupUsername,signupEmail,signupPassword;
    private Button signupbtn;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth=FirebaseAuth.getInstance();
        signupUsername=findViewById(R.id.username);
        signupEmail=findViewById(R.id.email);
        signupPassword=findViewById(R.id.password);
        signupbtn = findViewById(R.id.signup);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = signupUsername.getText().toString().trim();
                String useremail = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();

                if(username.isEmpty()){
                    signupUsername.setError("Username is Required");
                }
                if(useremail.isEmpty()){
                    signupEmail.setError("Email is Required");
                }

                if(pass.isEmpty()){
                    signupPassword.setError("Password is Required");
                }
                else{
                    auth.createUserWithEmailAndPassword(useremail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this,"Signup Sucessful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                        }
                        else{
                            Toast.makeText(SignupActivity.this,"Signup Failed"+task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                }
            }
        });

        login = findViewById(R.id.logintxt);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });

    }
}