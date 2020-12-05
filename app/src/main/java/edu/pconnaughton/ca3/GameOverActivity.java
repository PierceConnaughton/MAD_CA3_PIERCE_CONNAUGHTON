package edu.pconnaughton.ca3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }

    public void doHighScores(View view) {
        Intent hiScoresActivity = new Intent(view.getContext(), HiScoresActivity.class);

        startActivity(hiScoresActivity);
        finish();
    }

    public void doPlayAgain(View view) {
        finish();
    }
}