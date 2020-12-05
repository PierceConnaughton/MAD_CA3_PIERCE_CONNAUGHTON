package edu.pconnaughton.ca3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
    }

    public void doThirdColor(View view) {
        Intent gameOverStartActivity = new Intent(view.getContext(), GameOverActivity.class);

        startActivity(gameOverStartActivity);
        finish();
    }
}