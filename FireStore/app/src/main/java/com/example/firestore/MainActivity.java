package com.example.firestore;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private EditText nameInput, fullnameInput;
    private TextView displayName, displayFullname;
    private Button submitButton, showButton, updateButton, deleteButton;

    // Key value for database.
    public static final String KEY_NAME = "name";
    public static final String KEY_FULLNAME = "fullname";

    // Connect to Firestore.
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Referencing document.
    private DocumentReference documentReference = db.collection("user").document("id");
    // Referencing collection.
    private CollectionReference collectionReference = db.collection("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.name_input);
        fullnameInput = findViewById(R.id.fullname_input);
        displayName = findViewById(R.id.display_name);
        displayFullname = findViewById(R.id.display_fullname);

        updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(this);

        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(this);

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addDocument();
                // Getting value from EditText.
                String name = nameInput.getText().toString().trim();
                String fullName = fullnameInput.getText().toString().trim();

                // Setting Key-Value pair.
                Map<String, Object> data = new HashMap<>();
                data.put(KEY_NAME, name);
                data.put(KEY_FULLNAME, fullName);
                Log.d(TAG, "onClick: " + data.get(KEY_NAME));

                // Accessing direct path of databse.
                db.collection("user")
                        .document("id")
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Identity added.", Toast.LENGTH_LONG)
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
        });

        showButton = findViewById(R.id.show_button);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String name = documentSnapshot.getString(KEY_NAME);
                                    String fullname = documentSnapshot.getString(KEY_FULLNAME);

                                    displayName.setText(name);
                                    displayFullname.setText(fullname);

                                } else {
                                    Toast.makeText(MainActivity.this, "Data not found.", Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.toString());
                            }
                        });
            }
        });
    }

    // Real-time getter.
    @Override
    protected void onStart() {
        super.onStart();
        documentReference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    String name = documentSnapshot.getString(KEY_NAME);
                    String fullname = documentSnapshot.getString(KEY_FULLNAME);

                    displayName.setText(name);
                    displayFullname.setText(fullname);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String name = nameInput.getText().toString().trim();
        String fullname = fullnameInput.getText().toString().trim();

        Map<String, Object> data = new HashMap<>();
        data.put(KEY_NAME, name);
        data.put(KEY_FULLNAME, fullname);

        switch (v.getId()) {
            case R.id.update_button:
                documentReference.update(data)
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
                                Toast.makeText(MainActivity.this, "Can't proceed data.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                break;
            case R.id.delete_button:
                // Delete all field.
                documentReference.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                nameInput.setText("");
                                fullnameInput.setText("");

                                displayName.setText("");
                                displayFullname.setText("");
                                Toast.makeText(MainActivity.this, "Data deleted.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Can't proceed data.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                break;
        }
    }

    private void addDocument() {
        String name = nameInput.getText().toString().trim();
        String fullname = fullnameInput.getText().toString().trim();

        User user = new User(name, fullname);

        collectionReference.add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Collection added.", Toast.LENGTH_LONG)
                                .show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Upload failed.", Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }
}
