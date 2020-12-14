package edu.pconnaughton.ca3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class HiScoresActivity extends ListActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_scores);

        DatabaseHandler db = new DatabaseHandler(this);

        //Intent i = getIntent();
        //db = (DatabaseHandler) i.getParcelableExtra("database");

//        ArrayAdapter adapter = new ArrayAdapter<String>(this,
//                R.layout.activity_hi_scores, logs);
//
//
//       setListAdapter(adapter);



        List<HiScore> top5HiScores = db.getTopFiveScores();

        ArrayAdapter<HiScore> adapter = new ArrayAdapter<HiScore>(this,
                android.R.layout.simple_list_item_1, top5HiScores);
        setListAdapter(adapter);
    }

    public void doPlayAgain(View view) {

        Intent mainActivity = new Intent(view.getContext(), MainActivity.class);
        startActivity(mainActivity);

        finish();
    }
}