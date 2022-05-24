package com.abdelhakim.metoapp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    TextView ville;
    TextView tmp;
    TextView tmpmin;
    TextView tmpmax;
    TextView txtpression;
    TextView txthumidite;
    TextView txtdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imgview=findViewById(R.id.img);
        imgview.setImageResource(R.drawable.meteo);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        final Context co = this;

        ville=findViewById(R.id.txtville);
        tmp=findViewById(R.id.temp);
        tmpmin=findViewById(R.id.tempmin);
        tmpmax=findViewById(R.id.tempmax);
        txtpression=findViewById(R.id.pression);
        txthumidite=findViewById(R.id.humid);
        txtdate=findViewById(R.id.date);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ville.setText(query);
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                String url="https://api.openweathermap.org/data/2.5/weather?q="
                        +query+"&appid=e457293228d5e1465f30bcbe1aea456b";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("MyLog","----------------------------------------------");
                            Log.i("MyLog",response);

                            JSONObject jsonObject=new JSONObject(response);

                            Date date=new Date(jsonObject.getLong("dt")*1000);
                            SimpleDateFormat simpleDateFormat=
                                    new SimpleDateFormat("dd-MMM-yyyy' T 'HH:mm");
                            String dateString=simpleDateFormat.format(date);

                            JSONObject coords = jsonObject.getJSONObject("coord");
                            double lng = coords.getDouble("lon");
                            double lat = coords.getDouble("lat");
                            // set marker on the map
                            LatLng position = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions().position(position).title(query));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 12));

                            JSONObject main=jsonObject.getJSONObject("main");
                            int Temp=(int)(main.getDouble("temp")-273.15);
                            int TempMin=(int)(main.getDouble("temp_min")-273.15);
                            int TempMax=(int)(main.getDouble("temp_max")-273.15);
                            int Pression=(int)(main.getDouble("pressure"));
                            int Humidite=(int)(main.getDouble("humidity"));

                            JSONArray weather=jsonObject.getJSONArray("weather");
                            String meteo=weather.getJSONObject(0).getString("main");

                            txtdate.setText(dateString);
                            tmp.setText(String.valueOf(Temp+"°C"));
                            tmpmin.setText(String.valueOf(TempMin)+"°C");
                            tmpmax.setText(String.valueOf(TempMax)+"°C");
                            txtpression.setText(String.valueOf(Pression+" hPa"));
                            txthumidite.setText(String.valueOf(Humidite)+ "%");

                            Log.i("Weather","----------------------------------------------");
                            Log.i("Meteo",meteo);
                            setImage(meteo);
                            Toast.makeText(co,meteo, Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext( ), response, Toast.LENGTH_LONG).show( );


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Log.d("MyLog","-------Connection problem-------------------");
                                Log.d("MyLog", error.getMessage());
                                Log.d("MyLog", "--------------------------------");
                                Toast.makeText(MainActivity.this,
                                        "City not fond ",Toast.LENGTH_LONG).show();
                            }
                        });

                queue.add(stringRequest);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    public void setImage(String s)
    {
        ImageView imgview=findViewById(R.id.img);
        if(s.equals("Rain")) {
            imgview.setImageResource(R.drawable.rainy);
        }
        else if(s.equals("Clear")){
            imgview.setImageResource(R.drawable.clear);}
        else if(s.equals("Thunderstorm")) {
            imgview.setImageResource(R.drawable.thunderstorm);}
        else if(s.equals("Clouds"))
        {
            imgview.setImageResource(R.drawable.cloudy);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


}