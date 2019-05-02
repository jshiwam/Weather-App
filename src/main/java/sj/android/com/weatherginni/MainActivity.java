package sj.android.com.weatherginni;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

import sj.android.com.weatherginni.Common.Common;
import sj.android.com.weatherginni.Helper.Helper;
import sj.android.com.weatherginni.Model.OpenWeatherMap;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtTime, txtCelsius;
    ImageView imageView;
    ProgressBar pd;

    LocationManager locationManager;
    String provider;
    static double lat, lng;
    OpenWeatherMap openweathermap = new OpenWeatherMap();

    int MY_PERMISSION = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCity = findViewById(R.id.txtCity);
        txtLastUpdate = findViewById(R.id.txtLastUpdate);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtTime = findViewById(R.id.txtTime);
        txtDescription = findViewById(R.id.txtDescription);
        txtCelsius = findViewById(R.id.txtCelsius);
        imageView = findViewById(R.id.imageView);
        pd=findViewById(R.id.progressbar);


        //GEt Coordinates
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            }, MY_PERMISSION);
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null)
            Log.e("TAG", "NO Location");

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            }, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat=location.getLatitude();
        lng=location.getLongitude();

        new GetWeather().execute(Common.apiRequest(String.valueOf(lat),String.valueOf(lng)));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class GetWeather extends AsyncTask<String,Void,String>{
        ProgressBar pd=new ProgressBar(MainActivity.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pd.setTitle("Please Wait...");
            pd.setVisibility(View.INVISIBLE );
        }


        @Override
        protected String doInBackground(String... params) {
            String stream=null;
            String urlString=params[0];

            Helper http=new Helper();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.contains("Error: Not found city")){
                pd.setVisibility(View.GONE);
                return ;
            }
            Gson gson = new Gson();
            Type mType=new TypeToken<OpenWeatherMap>(){}.getType();
            openweathermap=gson.fromJson(s,mType);
            pd.setVisibility(View.GONE);

            txtCity.setText(String.format("Location: %s, Country: %s",openweathermap.getName(),openweathermap.getSys().getCountry()));
            txtLastUpdate.setText(String.format("Last Updated: %s", Common.getDateNow()));
            txtDescription.setText(String.format("Description: %s",openweathermap.getWeather().get(0).getDescription()));
            txtHumidity.setText(String.format("Humidity: %d%%",openweathermap.getMain().getHumidity()));
            txtTime.setText(String.format("Sunrise/Sunset: %s/%s",Common.unixTimeStampToDateTime(openweathermap.getSys().getSunrise()),Common.unixTimeStampToDateTime(openweathermap.getSys().getSunset())));
            txtCelsius.setText(String.format("Temprature: %.2f °C",openweathermap.getMain().getTemp()));
            Picasso.get()
                    .load(Common.getImage(openweathermap.getWeather().get(0).getIcon()))
                    .into(imageView);
        }
    }
}
