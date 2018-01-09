package valliappan.busrouteapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import valliappan.busrouteapp.R;

/**
 * Created by valliappan on 6/1/18.
 */

public class CustomViewAdapter extends ArrayAdapter<String> {

    private ArrayList<String> dataSet;

    private static class ViewHolder {
        TextView textViewStopName;
        ImageView info;
        ImageView line;
    }

    public CustomViewAdapter(ArrayList<String> data, Context context) {
        super(context, R.layout.list_detail_route_layout, data);
        this.dataSet = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String dataModel = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_detail_route_layout, parent, false);
            viewHolder.textViewStopName = convertView.findViewById(R.id.stop_name);
            viewHolder.info = convertView.findViewById(R.id.centerimage);
            viewHolder.line = convertView.findViewById(R.id.line);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textViewStopName.setText(dataModel);
        if (position == (dataSet.size() - 1)) {
            viewHolder.info.setImageResource(R.drawable.check_mark);
            viewHolder.line.setVisibility(View.GONE);
        } else {
            viewHolder.info.setImageResource(R.drawable.whitedot);
            viewHolder.line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}
