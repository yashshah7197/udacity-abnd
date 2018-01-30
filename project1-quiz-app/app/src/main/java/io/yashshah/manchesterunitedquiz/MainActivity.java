package io.yashshah.manchesterunitedquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int QUESTION_COUNT = 10;
    int score = 0;
    int correctQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void incrementScore() {
        score++;
        correctQuestions++;
    }

    public void gradeQuiz() {
        EditText answer1 = (EditText) findViewById(R.id.answer1_edittext);
        EditText answer9 = (EditText) findViewById(R.id.answer9_edittext);
        RadioButton answer2 = (RadioButton) findViewById(R.id.answer2_radiobutton);
        RadioButton answer3 = (RadioButton) findViewById(R.id.answer3_radiobutton);
        RadioButton answer5 = (RadioButton) findViewById(R.id.answer5_radiobutton);
        RadioButton answer7 = (RadioButton) findViewById(R.id.answer7_radiobutton);
        RadioButton answer8 = (RadioButton) findViewById(R.id.answer8_radiobutton);
        RadioButton answer10 = (RadioButton) findViewById(R.id.answer10_radiobutton);
        CheckBox answer4_1 = (CheckBox) findViewById(R.id.answer4_checkbox1);
        CheckBox answer4_2 = (CheckBox) findViewById(R.id.answer4_checkbox2);
        CheckBox answer4_3 = (CheckBox) findViewById(R.id.answer4_checkbox3);
        CheckBox answer4_4 = (CheckBox) findViewById(R.id.answer4_checkbox4);
        CheckBox answer6_1 = (CheckBox) findViewById(R.id.answer6_checkbox1);
        CheckBox answer6_2 = (CheckBox) findViewById(R.id.answer6_checkbox2);
        CheckBox answer6_3 = (CheckBox) findViewById(R.id.answer6_checkbox3);
        CheckBox answer6_4 = (CheckBox) findViewById(R.id.answer6_checkbox4);

        if (answer1.getText().toString().trim().toLowerCase().equals(getResources().getString(R.string.answer1)))
            incrementScore();

        if (answer2.isChecked())
            incrementScore();

        if (answer3.isChecked())
            incrementScore();

        if (answer4_1.isChecked() && answer4_2.isChecked() && answer4_3.isChecked() && !(answer4_4.isChecked()))
            incrementScore();

        if (answer5.isChecked())
            incrementScore();

        if (answer6_1.isChecked() && answer6_2.isChecked() && answer6_3.isChecked() && !(answer6_4.isChecked()))
            incrementScore();

        if (answer7.isChecked())
            incrementScore();

        if (answer8.isChecked())
            incrementScore();

        if (answer9.getText().toString().trim().toLowerCase().equals(getResources().getString(R.string.fred)))
            incrementScore();

        if (answer10.isChecked())
            incrementScore();
    }

    public void displayScore() {
        String message = "You scored " + score + " points in the quiz! You got " + correctQuestions + "/" + QUESTION_COUNT + " questions correct! :D";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void resetScore() {
        score = 0;
        correctQuestions = 0;
    }

    public void submit(View view) {
        gradeQuiz();
        displayScore();
        resetScore();
    }
}
