package valliappan.busrouteapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.ArrayList;
import valliappan.busrouteapp.CustomAdapter.CustomViewAdapter;
import valliappan.busrouteapp.CustomAdapter.CustomVolleyRequest;
import valliappan.busrouteapp.Mvp.ModelList;
import valliappan.busrouteapp.Mvp.Presenter;
import valliappan.busrouteapp.R;

public class DetailRouteActivity extends AppCompatActivity {
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_route);
        position = getIntent().getExtras().getInt("position");
        Log.e("position", "" + position);
        ArrayList<ModelList> modelist = Presenter.mRoutelist;
        ModelList data = modelist.get(position);
        ListView stoplist = findViewById(R.id.stoplist);
        NetworkImageView route_image = findViewById(R.id.route_image);
        TextView route_description = findViewById(R.id.route_description);
        TextView route_name = findViewById(R.id.route_name);
        ImageView accessible_icon = findViewById(R.id.accessible_icon);
        ImageLoader imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();
        imageLoader.get(data.getImage(), ImageLoader.getImageListener(route_image,
                R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        route_image.setImageUrl(data.getImage(), imageLoader);
        if (Boolean.valueOf(data.getAccessible())) {
            accessible_icon.setVisibility(View.VISIBLE);
        } else {
            accessible_icon.setVisibility(View.INVISIBLE);
        }
        route_description.setText(data.getDescription());
        route_name.setText(data.getName());
        ArrayList<String> stop_list = new ArrayList<>();
        stop_list.addAll(data.getStops());
        CustomViewAdapter customViewAdapter = new CustomViewAdapter(stop_list, getApplicationContext());
        stoplist.setAdapter(customViewAdapter);
    }
}
