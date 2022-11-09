package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import util.NoteApi;

public class SignupActivity extends AppCompatActivity {
    private Button signupButton;
    private EditText usernameInput, emailInput, passwordInput;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference = db.collection("user");
    private String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Entry point of Firebase Author SDK.
        firebaseAuth = FirebaseAuth.getInstance();

        usernameInput = findViewById(R.id.username_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        progressBar = findViewById(R.id.progress_signup);

        signupButton = findViewById(R.id.sign_up_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check value availability.
                if (!TextUtils.isEmpty(emailInput.getText().toString())
                        && !TextUtils.isEmpty(passwordInput.getText().toString())
                        && !TextUtils.isEmpty(usernameInput.getText().toString())) {

                    // Get data from field.
                    String email = emailInput.getText().toString().trim();
                    String password = passwordInput.getText().toString().trim();
                    String username = usernameInput.getText().toString().trim();

                    // Create user data.
                    createUser(email, password, username);
                } else {
                    // If something goes wrong.
                    Toast.makeText(SignupActivity.this, "Field cannot be empty.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {
                    Toast.makeText(SignupActivity.this, "User already Sign-in.", Toast.LENGTH_LONG)
                            .show();
                } else {
                    Log.d(TAG, "onAuthStateChanged: ");
                }
            }
        };
    }

    private void createUser(String email, String password, final String username) {
        // Check value availability again.
        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(username)) {

            // Enabling progress bar.
            progressBar.setVisibility(View.VISIBLE);

            // Setting user data.
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // Getting user id.
                                currentUser = firebaseAuth.getCurrentUser();
                                assert currentUser != null;
                                final String currentUserId = currentUser.getUid();

                                // Create Map for collection.
                                Map<String, String> userObj = new HashMap<>();
                                userObj.put("userId", currentUserId);
                                userObj.put("username", username);

                                // Adding data to collection.
                                collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                // Retrieve data from previously-added document.
                                                                if (Objects.requireNonNull(task.getResult()).exists()) {
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                    String name = task.getResult()
                                                                            .getString("username");

//                                                                    // Global API.
                                                                    NoteApi noteApi = NoteApi.getInstance();
                                                                    noteApi.setUserId(currentUserId);
                                                                    noteApi.setUsername(name);

                                                                    // Passing data to intent.
                                                                    Intent intent = new Intent(SignupActivity.this,
                                                                            CreateNoteActivity.class);
                                                                    intent.putExtra("username", name);
                                                                    intent.putExtra("userId", currentUserId);
                                                                    startActivity(intent);
                                                                } else {
                                                                    Toast.makeText(SignupActivity.this,
                                                                            "Something is wrong.",
                                                                            Toast.LENGTH_LONG).show();
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                }
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignupActivity.this, "Unable to add data.",
                                                        Toast.LENGTH_LONG).show();
                                                Log.d(TAG, "onFailure: " + e.toString());
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, "Email is already used.",
                                    Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onFailure: " + e.toString());
                        }
                    });
        } else {
            Toast.makeText(SignupActivity.this, "Found missing data.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user currently signed in.
        // Setting Firebase User interface, contains information about the signed-in user.
        currentUser = firebaseAuth.getCurrentUser();
        // Register a listener.
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
