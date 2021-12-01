package com.nareshgediya.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    View daySky, nightSky;
    Switch sw;
   TextView textView,signUp;
   Button login;
    EditText email, pass;
    ImageView googleBtn;


    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity);

        getSupportActionBar().hide();

        ProgressDialog progressDialog;


        login = findViewById(R.id.loginbtn2);

        daySky = findViewById(R.id.day_bg);
        nightSky = findViewById(R.id.night_bg);
        sw = findViewById(R.id.switch1);
        textView = findViewById(R.id.editText3);


        email = findViewById(R.id.email2);
        pass = findViewById(R.id.pass2);

        signUp = findViewById(R.id.signUpText);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login in");
        progressDialog.setMessage("Login in to your Account...");

//        googleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               signIn();
//            }
//        });

// Configure Google Sign In

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);



        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sw.isChecked()){

                    daySky.animate().alpha(0).setDuration(2500);
                    textView.setText("Night");
                }
                else {
                    daySky.animate().alpha(1).setDuration(2500);
                    textView.setText("Day");
                }
            }
        });

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog.show();
//
//                auth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString() ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressDialog.dismiss();
//                        if (task.isSuccessful()){
//                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }else {
//                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (auth.getCurrentUser()!= null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }



//    int RC_SIGN_IN = 86;
//    public void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private void firebaseAuthWithGoogle(String idToken) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("TAG", "signInWithCredential:success");
//
//
//                            FirebaseUser user = auth.getCurrentUser();
//
//                            User users = new User();
//                            users.setUserId(user.getUid());
//                            users.setUserName(user.getDisplayName());
//                            users.setPorfilePic(user.getPhotoUrl().toString());
//                            users.setEmail(user.getEmail());
//
//                            database.getReference().child("Users").child(user.getUid()).setValue(users);
//
//                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                          //  updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                            //updateUI(null);
//                        }
//                    }
//                });
//    }


        login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String txt_email = email.getText().toString();
            String txt_password = pass.getText().toString();

            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                Toast.makeText(LoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(txt_email , txt_password);
            }
        }
    });
}

    private void loginUser(String email, String password) {

        auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}