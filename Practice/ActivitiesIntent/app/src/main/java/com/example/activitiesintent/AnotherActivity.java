package com.example.activitiesintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        Bundle data = getIntent().getExtras();
        TextView displayName = findViewById(R.id.display_name);

        displayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicked: ", "True");

                Intent intent = getIntent();
                intent.putExtra("message_back", "From second activity");
                // Sending data from this activity to home activity
                setResult(RESULT_OK, intent);
                // Destroying this activity
                finish();
            }
        });

        if (data != null) {
            displayName.setText(data.getString("name"));
            Log.d("Int out: ", "onCreate: " + data.getInt("age"));
        } else {
            Toast.makeText(AnotherActivity.this, "Nothing to show",
                    Toast.LENGTH_SHORT).show();
        }

//        if (getIntent().getStringExtra("name") != null) {
//            displayName.setText(getIntent().getStringExtra("name"));
//        } else {
//            Toast.makeText(AnotherActivity.this, "Nothing to show",
//                    Toast.LENGTH_SHORT).show();
//        }
    }
}
