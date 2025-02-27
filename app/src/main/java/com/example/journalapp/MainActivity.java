package com.example.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    // Widgets
    Button loginBtn, createAccountBtn;
    private EditText emailEt, passEt;

    // Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        createAccountBtn = findViewById(R.id.creat_account);

        createAccountBtn.setOnClickListener(v -> {
            // Onclick()
            Intent i = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(i);

        });

        //LOGIN
        loginBtn =findViewById(R.id.email_signin);
        emailEt=findViewById(R.id.email);
        passEt=findViewById(R.id.password);

        //firebase auth

        firebaseAuth=FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(v->{
            loginEmailPassUser(emailEt.getText().toString().trim(),
                    passEt.getText().toString().trim());
        });
    }
    private void loginEmailPassUser(String email, String pwd){
        // Check for empty text
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)) {
            // Only attempt login if both fields are NOT empty
            firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Intent i = new Intent(MainActivity.this, JournalListActivity.class);
                    startActivity(i);
                }
            });
        } else {
            // Handle the case when fields are empty
            // You might want to show an error message to the user
        }


    }
}