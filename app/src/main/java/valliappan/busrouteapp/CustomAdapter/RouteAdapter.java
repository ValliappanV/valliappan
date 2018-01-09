package valliappan.busrouteapp.CustomAdapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.ArrayList;
import valliappan.busrouteapp.Activity.DetailRouteActivity;
import valliappan.busrouteapp.Mvp.ModelList;
import valliappan.busrouteapp.R;

/**
 * Created by valliappan on 6/1/18.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    private static final String TAG = "CustomViewAdapter";
    private ArrayList<ModelList> mDataSet;
    private ImageLoader imageLoader;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewRouteName;
        private final NetworkImageView imageViewRoute;

        private ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Elemesnt " + getAdapterPosition() + " clicked.");
                    Intent in = new Intent(v.getContext(), DetailRouteActivity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("position", getAdapterPosition());
                    v.getContext().startActivity(in);
                }
            });
            textViewRouteName = v.findViewById(R.id.route_name);
            imageViewRoute = v.findViewById(R.id.route_image);
        }

        private TextView getTextViewRouteName() {
            return textViewRouteName;
        }
    }

    public RouteAdapter(ArrayList<ModelList> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_route_layout, viewGroup, false);
        imageLoader = CustomVolleyRequest.getInstance(viewGroup.getContext()).getImageLoader();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        viewHolder.getTextViewRouteName().setText(mDataSet.get(position).getName());
        imageLoader.get(mDataSet.get(position).getImage(), ImageLoader
                .getImageListener(viewHolder.imageViewRoute,
                R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        viewHolder.imageViewRoute.setImageUrl(mDataSet.get(position).getImage(), imageLoader);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}

