package edu.pconnaughton.ca3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.ListActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class HiScoresActivity extends ListActivity {

    private DatabaseHandler datasource;
    TextView tvListHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_scores);

        tvListHeader = findViewById(R.id.tvListHeader);

        String result = String.format("%-30s%-30s%-30s\n","Player Name","Score","Date Played");

        tvListHeader.setText(result);
        datasource = new DatabaseHandler(this);

        List<HiScore> values = datasource.getTopFiveScores();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<HiScore> adapter = new ArrayAdapter<HiScore>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    public void doPlayAgain(View view) {

        Intent mainActivity = new Intent(view.getContext(), MainActivity.class);
        startActivity(mainActivity);

        finish();
    }
}