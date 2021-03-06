package com.example.chitnchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.Toast;

import com.example.chitnchat.Model.Users;
import com.example.chitnchat.databinding.ActivitySignUpBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding ;
    private FirebaseAuth auth ;
    FirebaseDatabase database ;
    //Loading showoff
    ProgressDialog progressDialog ;
    //sign in client
    GoogleSignInClient mGoogleSignInClient ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance() ;
        database = FirebaseDatabase.getInstance();
        //progressDia setting
        progressDialog = new ProgressDialog(SignUp.this) ;
        progressDialog.setTitle("Creating your account");
        progressDialog.setMessage("Hurray! Creating your account");
        //setting sign up button
        //google sign in option

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this , gso) ;


        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.createUserWithEmailAndPassword(binding.idemailSi.getText().toString() , binding.idpasswordSi.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                     if(task.isSuccessful()){

                         Users user = new Users(binding.idname.getText().toString() , binding.idemailSi.getText().toString() , binding.idpasswordSi.getText().toString());
                         String id = task.getResult().getUser().getUid() ;

                         database.getReference().child("Users").child(id).setValue(user) ;
                         Toast.makeText(SignUp.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                     }
                     else
                        {
                            Toast.makeText(SignUp.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        binding.alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this , SignInActivity.class);
                startActivity(intent);
            }
        });

    }
}