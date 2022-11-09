package com.example.activitiesintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private Button showName;
    private TextView name;
    private final int REQUEST_CODE = 2;

    // Receiving data from 'B to A'
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                String message = data.getStringExtra("message_back");
                Toast.makeText(MainActivity.this, message,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.enter_name);

        showName = findViewById(R.id.show_button);
        showName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String personName = name.getText().toString().trim();

                if (!personName.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, AnotherActivity.class);
                    intent.putExtra("name", personName);
                    intent.putExtra("age", 16);
                    intent.putExtra("weight", 51);
                    // Using startActivityForResult so the data can returned back from another activity
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    Toast.makeText(MainActivity.this, "Enter name",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
