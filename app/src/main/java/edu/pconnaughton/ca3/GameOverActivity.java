package edu.pconnaughton.ca3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class GameOverActivity extends AppCompatActivity {

    TextView tvUserScore,tvRound;
    int userScore,userRound;

    private DatabaseHandler datasource;
    public EditText userText = null;

    public DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        tvUserScore = findViewById(R.id.tvUserScore);
        tvRound = findViewById(R.id.tvRound);

        userScore = getIntent().getIntExtra("userScore", 0);
        userRound = getIntent().getIntExtra("userRound", 0);

        tvUserScore.setText("Your Score was " + userScore);
        tvRound.setText("Got to round " + userRound);

        userText = (EditText) findViewById(R.id.input1);

        db = new DatabaseHandler(this);

        db.emptyHiScores();     // empty table if required
        InsertingData();

        // Reading all scores
        Log.i("Reading: ", "Reading all scores..");
        List<HiScore> hiScores = db.getAllHiScores();


        for (HiScore hs : hiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }

        Log.i("divider", "====================");

        HiScore singleScore = db.getHiScore(5);
        Log.i("High Score 5 is by ", singleScore.getPlayer_name() + " with a score of " + singleScore.getScore());

        Log.i("divider", "====================");

        // Calling SQL statement
        List<HiScore> top5HiScores = db.getTopFiveScores();
        for (HiScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();
            // Writing HiScore to log
            Log.i("Score: ", log);
        }

        HiScore lastScore = top5HiScores.get(top5HiScores.size() - 1);
        if (userScore > lastScore.score) {
            Toast.makeText(this,"Enter Score", Toast.LENGTH_LONG).show();
        }
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

    public void onClick(View view) {
        List<HiScore> top5HiScores = db.getTopFiveScores();
        HiScore lastScore = top5HiScores.get(top5HiScores.size() - 1);

        if(userScore > lastScore.score){
            ArrayAdapter<HiScore> adapter = new ArrayAdapter<HiScore>(this,
                    android.R.layout.simple_list_item_1, top5HiScores);

            datasource.deleteHiScore(lastScore);
            adapter.remove(lastScore);

            HiScore hiScore = null;
            String userName = userText.getText().toString();

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            hiScore.score = userScore;
            hiScore.player_name = userName;
            hiScore.game_date = date;

            datasource.addHiScore(hiScore);
            adapter.add(hiScore);



        }
        else{
            Toast.makeText(this,"Score not high enough",Toast.LENGTH_SHORT).show();
        }
    }

    public void InsertingData(){
        // Inserting hi scores
        Log.i("Insert: ", "Inserting ..");
        db.addHiScore(new HiScore("20 OCT 2020", "Frodo", 1));
        db.addHiScore(new HiScore("28 OCT 2020", "Dobby", 1));
        db.addHiScore(new HiScore("20 NOV 2020", "DarthV", 1));
        db.addHiScore(new HiScore("20 NOV 2020", "Bob", 1));
        db.addHiScore(new HiScore("22 NOV 2020", "Gemma", 1));
        db.addHiScore(new HiScore("30 NOV 2020", "Joe", 9));
        db.addHiScore(new HiScore("01 DEC 2020", "DarthV", 1));
        db.addHiScore(new HiScore("02 DEC 2020", "Gandalf", 132));

    }
}