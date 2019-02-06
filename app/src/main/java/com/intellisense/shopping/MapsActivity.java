package com.intellisense.shopping;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Habiba Abbasi on 30/01/2017.
 */
public class MapsActivity extends FragmentActivity  {


    private static final String LOG_TAG = "ShoppingApp";
    private static final String SERVICE_URL = "http://icoderslab.com/Api/ShoppingApp/getStoresLocation.php";
    private GoogleMap map;

    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_maps);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        if (connected) {
            Toast.makeText(getApplicationContext(), "Kindly Turn on your Internet!", Toast.LENGTH_LONG).show();

        }

        // Font styling
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"Roboto-Thin.ttf",true);

        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.maps_overlay));
        }



        setUpMapIfNeeded();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        // Retrieve the store data from the web service
        // In a worker thread since it's a network operation.
        new Thread(new Runnable() {
            public void run() {
                try {
                    retrieveAndAddCities();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Unable to fetch data from server", e);
                    return;
                }
            }
        }).start();
    }

    protected void retrieveAndAddCities() throws IOException {
        HttpURLConnection conn = null;
        final StringBuilder json = new StringBuilder();
        try {
            // Connect to the web service
            URL url = new URL(SERVICE_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Read the JSON data into the StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to service", e);
            throw new IOException("Error connecting to service", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Create markers from the store data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    createMarkersFromJson(json.toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });
    }

    void createMarkersFromJson(String json) throws JSONException {


        JSONObject jsonobj = new JSONObject(json);
        JSONArray stores = jsonobj.getJSONArray("stores");
        Marker marker;
        // De-serialize the JSON string into an array of stores objects

        for (int i = 0; i < stores.length(); i++) {
            // Create a marker for each store in the JSON data.
            JSONObject s = stores.getJSONObject(i);

            marker = map.addMarker(new MarkerOptions()
                    .title(s.getString("store_name"))
                    .snippet(s.getString("Store_Address"))
                    .position(new LatLng(
                            Double.valueOf(s.getString("store_latitude")),
                            Double.valueOf(s.getString("store_longitude"))
                    )).icon(BitmapDescriptorFactory.fromResource(R.drawable.store_icon))
            );
            marker.setSnippet(s.getString("Store_Phone"));
            marker.setTitle(s.getString("Store_Address"));
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {


                @Override
                public boolean onMarkerClick(Marker marker) {

                    setContentView(R.layout.address_display);
                   // binding =DataBindingUtil.setContentView(MapsActivity.this,R.layout.address_display);
                            // Font styling

                    Calligrapher calligrapher=new Calligrapher(MapsActivity.this);
                    calligrapher.setFont(MapsActivity.this,"Roboto-Thin.ttf",true);

                    TextView addressView = (TextView) findViewById(R.id.addressView);
                    TextView phoneView = (TextView) findViewById(R.id.phoneView);
                    addressView.setText(marker.getTitle());
                    phoneView.setText(marker.getSnippet());


                    return true;
                }

            });
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(24.872736, 67.060441), 11.0f));
        }
    }



}

