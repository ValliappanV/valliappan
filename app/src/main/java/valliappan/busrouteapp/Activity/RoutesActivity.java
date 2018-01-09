package valliappan.busrouteapp.Activity;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import valliappan.busrouteapp.CustomAdapter.RouteAdapter;
import valliappan.busrouteapp.Mvp.ModelList;
import valliappan.busrouteapp.Mvp.Presenter;
import valliappan.busrouteapp.R;
import static android.content.ContentValues.TAG;

public class RoutesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RouteAdapter routeAdapter;
    private SwipeRefreshLayout refreshlayout;
    private ArrayList<ModelList> modelist = new ArrayList<>();
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        RecyclerView recyclerView = findViewById(R.id.routes_recycler_view);
        refreshlayout = findViewById(R.id.refreshlayout);
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please wait");
        progressBar.show();
        refreshlayout.setOnRefreshListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        routeAdapter = new RouteAdapter(modelist);
        recyclerView.setAdapter(routeAdapter);
        if (isConnectedToInternet(getApplicationContext())) {
            getData(getApplication());
        } else {
            progressBar.dismiss();
            showToast("Check Internet Connection!");
        }
    }

    private void getData(final Application application) {
        modelist.clear();
        Presenter.mRoutelist = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(application);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Presenter.URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public synchronized void onResponse(JSONObject response) {
                        Log.d("json response", response.toString());
                        try {
                            JSONObject jsonObj = new JSONObject(response.toString());
                            JSONArray stops_p, routes;
                            routes = jsonObj.getJSONArray("routes");
                            for (int i = 0; i < routes.length(); i++) {
                                String id, name, image, description, accessible;
                                ArrayList stops = new ArrayList();
                                JSONObject json_obj = routes.getJSONObject(i);
                                id = json_obj.getString("id");
                                name = json_obj.getString("name");
                                description = json_obj.getString("description");
                                accessible = json_obj.getString("accessible");
                                image = json_obj.getString("image");
                                stops_p = json_obj.getJSONArray("stops");
                                for (int j = 0; j < stops_p.length(); j++) {
                                    JSONObject json_obj_stops = stops_p.getJSONObject(j);
                                    stops.add(json_obj_stops.getString("name"));
                                }
                                final ModelList list = new ModelList(id, name, description, accessible, image, stops);
                                modelist.add(list);
                                Presenter.mRoutelist.add(list);
                                progressBar.dismiss();
                                Log.d("RouteList", id + "....." + name + "....." + description + "....." + accessible + "....." + image + "....." + stops);
                            }
                            if (refreshlayout.isRefreshing())
                                refreshlayout.setRefreshing(false);
                            if (modelist.size() > 0) {
                                routeAdapter.notifyDataSetChanged();
                            } else {
                                showToast("No Data Found");
                            }
                        } catch (final JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                            if (refreshlayout.isRefreshing())
                                refreshlayout.setRefreshing(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Error Response", Toast.LENGTH_SHORT).show();
                        if (refreshlayout.isRefreshing())
                            refreshlayout.setRefreshing(false);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public boolean isConnectedToInternet(Context context) {
        boolean connected;
        try {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            connected = (conMgr.getActiveNetworkInfo() != null &&
                    conMgr.getActiveNetworkInfo().isAvailable() &&
                    conMgr.getActiveNetworkInfo().isConnected());
        } catch (Exception e) {
            return false;
        }
        return connected;
    }

    @Override
    public void onRefresh() {
        if (isConnectedToInternet(getApplicationContext())) {
            getData(getApplication());
        } else {
            if (refreshlayout.isRefreshing())
                refreshlayout.setRefreshing(false);
            showToast("Check Internet Connection!");
        }
    }
}
