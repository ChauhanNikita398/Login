package com.example.login;

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
import com.google.firebase.auth.FirebaseUser;

public class logActivity extends AppCompatActivity {
    private EditText emailID,password;
    private Button btnSignIn;
    private TextView tvSignUp;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        mFirebaseAuth=FirebaseAuth.getInstance();
        emailID=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        btnSignIn=findViewById(R.id.button);
        tvSignUp=findViewById(R.id.textView);

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser=mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser!=null){
                    Toast.makeText(logActivity.this,"You are logged in",Toast.LENGTH_SHORT);
                    Intent i=new Intent(logActivity.this,homeActivity.class);

                    startActivity(i);
                }
                else{
                    Toast.makeText(logActivity.this,"Please LogIn",Toast.LENGTH_SHORT);
                }
            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    String email=emailID.getText().toString();
                    String pwd=password.getText().toString();
                    if(email.isEmpty()){
                        emailID.setError("Please enter email id");
                        emailID.requestFocus();
                    }
                    else if(pwd.isEmpty()){
                        password.setError("Please enter your password");
                        password.requestFocus();
                    }
                    else  if(email.isEmpty() && pwd.isEmpty()){
                        Toast.makeText(logActivity.this,"Feilds Are Empty",Toast.LENGTH_SHORT);

                    }
                    else if(!(email.isEmpty() && pwd.isEmpty())){
                        mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(logActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(logActivity.this,"Login Error,Please Login Again",Toast.LENGTH_SHORT);
                                }
                                else{
                                    Intent intToHOme=new Intent(logActivity.this,homeActivity.class);
                                    startActivity(intToHOme);
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(logActivity.this,"Error Ocurred",Toast.LENGTH_SHORT);
                    }
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp=new Intent(logActivity.this,MainActivity.class);
                startActivity(intSignUp);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
