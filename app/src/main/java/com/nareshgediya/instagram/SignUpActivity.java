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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mRootRef;
    ProgressDialog progressDialog;



    View daySky, nightSky;
    Switch sw;
   TextView textView, loginText;
   EditText email, pass, userName, name;
   Button signUp;
   ImageView gbBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signup_activity);

     getSupportActionBar().hide();

        progressDialog = new ProgressDialog(SignUpActivity.this);


        userName = findViewById(R.id.us1);
        pass = findViewById(R.id.pass1);
        email = findViewById(R.id.email1);
        signUp = findViewById(R.id.signUp1);
        name = findViewById(R.id.name);



        daySky = findViewById(R.id.day_bg);
        nightSky = findViewById(R.id.night_bg);
        sw = findViewById(R.id.switch1);
        textView = findViewById(R.id.editText3);
        loginText = findViewById(R.id.loginText);


        //FireBase Authentication;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername = userName.getText().toString();
                String txtName = name.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPassword = pass.getText().toString();

                if (TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtName)
                        || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(SignUpActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (txtPassword.length() < 6){
                    Toast.makeText(SignUpActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txtUsername , txtName , txtEmail , txtPassword);
                }
            }
        });



        // Here this is Signup Method (Differenet)

//        signUp.setOnClickListener(v -> {
//            progressDialog.show();
//
//            Toast.makeText(SignUpActivity.this, "Procesing...", Toast.LENGTH_SHORT).show();
//
//
//            mAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    progressDialog.dismiss();
//                    if (task.isSuccessful()){
//
//                        User users = new User(userName.getText().toString(),email.getText().toString(), pass.getText().toString());
//                        String id = task.getResult().getUser().getUid();
//                        database.getReference().child("Users").child(id).setValue(users);
//                        userName.setText("");
//                        email.setText("");
//                        pass.setText("");
//
//                        Toast.makeText(SignUpActivity.this, "Your Account Has been Created ", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//        });

        //Animation of Day And Night mode;


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

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser(final String username, final String name, final String email, String password) {

        progressDialog.setTitle("Create Account");
        progressDialog.setMessage("Creating your Account...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                HashMap<String , Object> map = new HashMap<>();
                map.put("name" , name);
                map.put("email", email);
                map.put("username" , username);
                map.put("id" , mAuth.getCurrentUser().getUid());
                map.put("bio" , "");
                map.put("imageurl" , "default");

                mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();

                            Intent intent = new Intent(SignUpActivity.this , MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}