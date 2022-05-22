package com.example.chitnchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chitnchat.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding ;
    ProgressDialog progressDialog ;
    FirebaseAuth auth ;
    GoogleSignInClient mGoogleSignInClient ;
    BeginSignInRequest signInRequest ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance() ;
        progressDialog = new ProgressDialog(SignInActivity.this) ;
        progressDialog.setTitle("logIn");
        progressDialog.setMessage("Hurray! Creating your account");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
               mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        signInRequest = BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId(getString(R.string.default_web_client_id))
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build())
//                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        binding.signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              progressDialog.show();
              auth.signInWithEmailAndPassword(binding.etEmail.getText().toString() , binding.etPassword.getText().toString())
                 .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         progressDialog.dismiss();
                         if(task.isSuccessful()){
                             Intent intent =  new Intent(SignInActivity.this , MainActivity.class);
                             startActivity(intent);
                         }
                         else{
                             Toast.makeText(SignInActivity.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                         }

                     }
                 });
                    binding.textView4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(SignInActivity.this  , SignUp.class);
                            startActivity(intent) ;

                        }
                    });
                    if(auth.getCurrentUser()!=null)
                    {
                        Intent intent = new Intent(SignInActivity.this  , MainActivity.class);
                        startActivity(intent) ;
                    }
            }

             class YourActivity extends AppCompatActivity {

                // ...
                private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
                private boolean showOneTapUI = true;
                // ...

                @Override
                protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                    super.onActivityResult(requestCode, resultCode, data);

                    switch (requestCode) {
                        case REQ_ONE_TAP:
                            try {
                                SignInClient oneTapClient = null;
                                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                                String idToken = credential.getGoogleIdToken();
                                if (idToken !=  null) {
                                    // Got an ID token from Google. Use it to authenticate
                                    // with Firebase.
                                    Log.d("TAG", "Got ID token.");
                                }
                            } catch (ApiException e) {
                                // ...
                            }
                            break;
                    }
                }
            }
        });


    }
    int RC_SIGN_IN = 65 ;

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

        public void onActivityResult(int requestCode, int resultCode, Intent
        android.content.Intent data;
        data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }
    }
}