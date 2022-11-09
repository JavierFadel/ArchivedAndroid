package com.example.firestorebasic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firestorebasic.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Encapsulation
    private static final String TAG = "MainActivity";
    private EditText inputUsername, inputEmail;
    private Button buttonSubmit, buttonUpdate, buttonDelete, buttonShow, buttonDocument;
    private TextView displayUsername, displayEmail;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference = db.collection("user").document("id");
    private CollectionReference collectionReference = db.collection("user");

    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayUsername = findViewById(R.id.display_username);
        displayEmail = findViewById(R.id.display_email);

        inputUsername = findViewById(R.id.input_username);
        inputEmail = findViewById(R.id.input_email);

        buttonSubmit = findViewById(R.id.submit_button);
        buttonSubmit.setOnClickListener(this);

        buttonDelete = findViewById(R.id.delete_button);
        buttonDelete.setOnClickListener(this);

        buttonUpdate = findViewById(R.id.update_button);
        buttonUpdate.setOnClickListener(this);

        buttonShow = findViewById(R.id.show_button);
        buttonShow.setOnClickListener(this);

        buttonDocument = findViewById(R.id.submit_document);
        buttonDocument.setOnClickListener(this);
    }

    // Polymorphism
    @Override
    public void onClick(View v) {
        String username = inputUsername.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

//        Map<String, Object> data = new HashMap<>();
//        data.put(KEY_USERNAME, username);
//        data.put(KEY_EMAIL, email);

        switch (v.getId()) {
            // Upload data to Firestore.
            case R.id.submit_button:
                db.collection("user")
                        .document("id")
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Data added.", Toast.LENGTH_LONG)
                                        .show();
                                clearData();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Failed to fetch data.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                break;
            case R.id.delete_button:
                documentReference
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Data deleted.", Toast.LENGTH_LONG)
                                        .show();
                                displayUsername.setText("");
                                displayEmail.setText("");
//                                clearData();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Failed to delete data.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                break;
            case R.id.update_button:
                // TODO: update one data without affecting other data.
                documentReference
                        .update(
                                KEY_USERNAME, user.getUsername(),
                                KEY_EMAIL, user.getEmail()
                        )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Data updated.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Failed to update data.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                break;
            case R.id.show_button:
                // Get data from database.
                getAll();
                break;
            case R.id.submit_document:
                addDocument();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
            documentReference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG)
                                .show();
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);

//                        String username = documentSnapshot.getString(KEY_USERNAME);
//                        String email = documentSnapshot.getString(KEY_EMAIL);

                        clearData();
                        if (user != null) {
                            displayUsername.setText(user.getUsername());
                            displayEmail.setText(user.getEmail());
                        }
                    }
                }
            });
    }

    private void addDocument() {
        String username = inputUsername.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();

        User user = new User(username, email);

        collectionReference
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Data added to document.", Toast.LENGTH_LONG)
                                .show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
    }

    // Get data from all document.
    private void getAll() {
        collectionReference
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Iterate through document.
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            Log.d(TAG, "onSuccess: " + snapshot.getString(KEY_USERNAME));
                        }
                    }
                });
    }

    private void clearData() {
        inputUsername.setText("");
        inputEmail.setText("");
    }
}
