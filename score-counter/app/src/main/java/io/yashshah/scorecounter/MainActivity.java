package io.yashshah.scorecounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int foulsTeamA = 0;
    int foulsTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayTeamA(int score, int fouls) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        TextView foulsView = (TextView) findViewById(R.id.team_a_fouls);
        scoreView.setText(String.valueOf(score));
        foulsView.setText(String.valueOf(fouls));
    }

    public void displayTeamB(int score, int fouls) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        TextView foulsView = (TextView) findViewById(R.id.team_b_fouls);
        scoreView.setText(String.valueOf(score));
        foulsView.setText(String.valueOf(fouls));
    }

    public void addTeamAGoal(View view) {
        scoreTeamA += 1;
        displayTeamA(scoreTeamA, foulsTeamA);
    }

    public void addTeamAFoul(View view) {
        foulsTeamA += 1;
        displayTeamA(scoreTeamA, foulsTeamA);
    }

    public void addTeamBGoal(View view) {
        scoreTeamB += 1;
        displayTeamB(scoreTeamB, foulsTeamB);
    }

    public void addTeamBFoul(View view) {
        foulsTeamB += 1;
        displayTeamB(scoreTeamB, foulsTeamB);
    }

    public void reset(View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        foulsTeamA = 0;
        foulsTeamB = 0;
        displayTeamA(scoreTeamA, foulsTeamA);
        displayTeamB(scoreTeamB, foulsTeamB);
    }
}
