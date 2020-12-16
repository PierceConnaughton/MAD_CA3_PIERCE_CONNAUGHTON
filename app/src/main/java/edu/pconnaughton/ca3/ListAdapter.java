package edu.pconnaughton.ca3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.pconnaughton.ca3.R;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private HiScore hiScores[] = new HiScore[] {};

    public ListAdapter (Context context, HiScore hiScores[]) {

        this.context = context;
        this.hiScores = hiScores;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return hiScores.length;
    }

    @Override
    public Object getItem(int position) {
        return hiScores[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, null, true);

            holder.tvProduct = (TextView) convertView.findViewById(R.id.tv);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        String result = String.format("%-30s%-30s%-30s\n",hiScores[position].player_name,hiScores[position].score,hiScores[position].game_date);

        holder.tvProduct.setText(result);

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvProduct;

    }

}