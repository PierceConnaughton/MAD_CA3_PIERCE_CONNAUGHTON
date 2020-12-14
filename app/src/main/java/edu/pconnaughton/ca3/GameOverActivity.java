package edu.pconnaughton.ca3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView tvUserScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        tvUserScore = findViewById(R.id.tvUserScore);

        int uscore;

        uscore = getIntent().getIntExtra("userScore",0);

        tvUserScore.setText("Your Score was " + uscore);
    }

    public void doHighScores(View view) {
        Intent hiScoresActivity = new Intent(view.getContext(), HiScoresActivity.class);

        startActivity(hiScoresActivity);
        finish();
    }

    public void doPlayAgain(View view) {
        Intent mainActivity = new Intent(view.getContext(), MainActivity.class);

        startActivity(mainActivity);

        finish();
    }
}