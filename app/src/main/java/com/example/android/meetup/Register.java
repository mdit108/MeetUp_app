package com.example.android.meetup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText mFullname,mEmail,mPassword,mconfpassword;
    Button mregisterbtn;
    TextView mloginbtn;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    ProgressBar progressBar;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullname=findViewById(R.id.fullname);
        mEmail=findViewById(R.id.editTextTextEmailAddress);
        mPassword=findViewById(R.id.editTextTextPassword);
        mconfpassword=findViewById(R.id.editTextTextPassword2);
        mregisterbtn=findViewById(R.id.Registerbtn);
        mloginbtn=findViewById(R.id.textView3);
        fAuth= FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);


//        if (fAuth.getCurrentUser()!=null){
//            startActivity(new Intent(getApplicationContext(),Login.class));
//            finish();
//        }

        mregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname=mFullname.getText().toString();
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String confirmpassword=mconfpassword.getText().toString().trim();
                if (!password.equals(confirmpassword)){
                    mPassword.setError("Entered passwords must be the same");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }
                if (password.length()<6){
                    mPassword.setError("Password length must be atleast 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = fAuth.getCurrentUser();
                            assert firebaseUser != null;
                            userID = firebaseUser.getUid();
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            //userID = fAuth.getCurrentUser().getUid();
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);


                            HashMap<String, String> hashMap = new HashMap<>();
//                            List<String> friends = new ArrayList<>();
//                            friends.add("John");
//                            friends.add("Steve");
//                            friends.add("Anna");
//                            user.put("friends",friends);
                            hashMap.put("id", userID);
                            hashMap.put("username", fullname);
                            hashMap.put("email", email);


                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(Register.this, Interests.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(Register.this, "Error!"+task.getException(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
