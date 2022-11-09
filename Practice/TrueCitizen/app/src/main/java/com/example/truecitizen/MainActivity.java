package com.example.truecitizen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button trueButton;
    private Button falseButton;
    private TextView questionText;
    private ImageButton nextButton;
    private int currentIndex = 0;

    Question[] questionBank = new Question[] {
            new Question(R.string.question_something, false),
            new Question(R.string.question_another, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        questionText = findViewById(R.id.question_text);
        nextButton = findViewById(R.id.next_button);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.true_button:
                Toast.makeText(MainActivity.this, "True", Toast.LENGTH_SHORT).show();
                break;
            case R.id.false_button:
                Toast.makeText(MainActivity.this, "False", Toast.LENGTH_SHORT).show();
                break;
            case R.id.next_button:
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
        }
    }

    private void updateQuestion() {
        questionText.setText(questionBank[currentIndex].getQuestionResId());
    }
}
