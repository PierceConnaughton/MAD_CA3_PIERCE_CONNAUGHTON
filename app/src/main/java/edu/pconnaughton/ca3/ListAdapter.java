package edu.pconnaughton.ca3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.pconnaughton.ca3.HiScore;

class UsersAdapter extends ArrayAdapter<HiScore> {
    public UsersAdapter(Context context, ArrayList<HiScore> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        HiScore user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvScore = (TextView) convertView.findViewById(R.id.tvScore);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        // Populate the data into the template view using the data object
        tvName.setText(user.player_name);
        tvScore.setText(user.score);
        tvDate.setText(user.game_date);
        // Return the completed view to render on screen
        return convertView;
    }
}