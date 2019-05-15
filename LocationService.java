package com.example.phone;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class LocationService extends Service{

    private LocationManager locationManager;
    private Boolean locationChanged;
    private Handler handler = new Handler();

    public static Location curLocation;
    public static String place="";

     public static String latitude,longitude,lati="",logi="";

    SharedPreferences sh;

    String imei = "",url="";
    JSONParser parser=new JSONParser();


    LocationListener locationListener = new LocationListener()
    {
        public void onLocationChanged(Location location)
        {
            if (curLocation == null)
            {
                curLocation = location;
                locationChanged = true;
            }
            else if (curLocation.getLatitude() == location.getLatitude() && curLocation.getLongitude() == location.getLongitude())
            {
                locationChanged = false;
                return;
            }
            else
                locationChanged = true;

            curLocation = location;
            latitude=String.valueOf(curLocation.getLatitude());
            longitude=String.valueOf(curLocation.getLongitude());

            updatelocation(latitude,longitude);

            if (locationChanged)
                locationManager.removeUpdates(locationListener);
        }
        public void onProviderDisabled(String provider)
        {
        }
        public void onProviderEnabled(String provider)
        {
        }
        public void onStatusChanged(String provider, int status,Bundle extras) {
        }
    };

    @Override
    public void onCreate()
    {
        super.onCreate();
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        try
        {
            if(android.os.Build.VERSION.SDK_INT>9)
            {
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        }
        catch(Exception e)
        {

        }

        curLocation = getBestLocation();
        if (curLocation == null)
        {
            Toast.makeText(this, "GPS problem..........", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart(Intent i, int startId)
    {
        Toast.makeText(this, "Start services", Toast.LENGTH_SHORT).show();
        handler.post(GpsFinder);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onDestroy()
    {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(provider.contains("gps"))
        { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
        handler.removeCallbacks(GpsFinder);
        handler = null;
        Toast.makeText(this, "Service Stopped..!!", Toast.LENGTH_SHORT).show();
    }

    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    public Runnable GpsFinder = new Runnable(){

        @SuppressWarnings("deprecation")
        public void run(){
            try
            {

                String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if(!provider.contains("gps")){ //if gps is disabled
                    final Intent poke = new Intent();
                    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    poke.setData(Uri.parse("3"));
                    sendBroadcast(poke);
                }
            }
            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(), "Error in gps on:"+e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            Location tempLoc = getBestLocation();
            if(tempLoc!=null)
            {
                curLocation = tempLoc;
                lati=Double.toString(curLocation.getLatitude());
                logi=Double.toString(curLocation.getLongitude());
                Toast.makeText(getApplicationContext(), Double.toString(curLocation.getLatitude()), Toast.LENGTH_SHORT).show();

                updatelocation(lati,logi);

            }
            else
            {
                Toast.makeText(getBaseContext(), "Temploc null", Toast.LENGTH_SHORT).show();
            }
            handler.postDelayed(GpsFinder,20000);// register again to start after 20 seconds...
        }
    };

    @SuppressLint("MissingPermission")
    private Location getBestLocation()
    {
        Location gpslocation = null;
        Location networkLocation = null;

        if(locationManager==null){
            locationManager = (LocationManager) getApplicationContext() .getSystemService(Context.LOCATION_SERVICE);
        }
        try
        {
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000, 0, locationListener);// here you can set the 2nd argument time interval also that after how much time it will get the gps location
                gpslocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000, 0, locationListener);
                networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        catch (IllegalArgumentException e)
        {
            Log.e("error", e.toString());
        }
        if(gpslocation==null && networkLocation==null)
            return null;

        if(gpslocation!=null && networkLocation!=null)
        {
            if(gpslocation.getTime() < networkLocation.getTime())
            {
                gpslocation = null;
                return networkLocation;
            }
            else
            {
                networkLocation = null;
                return gpslocation;
            }
        }
        if (gpslocation == null)
        {
            return networkLocation;
        }
        if (networkLocation == null)
        {
            return gpslocation;
        }
        return null;
    }


    protected void updatelocation(String lat,String lng)
    {
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url="http://"+sh.getString("ip","")+":5000/Location";
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("imei", sh.getString("imei","")));
            params.add(new BasicNameValuePair("lati", lat));
            params.add(new BasicNameValuePair("longi", lng));

            JSONObject json=(JSONObject) parser.makeHttpRequest(url,"GET", params);
            String s=null;

            s=json.getString("result");
            if(s.equals("success"))
            {
                Log.d("*******","Success");
            }
            else
            {
                Log.d("*******","Not success");
            }
        }
        catch(Exception e)
        {
            Log.d("111111111",e+"");
        }



    }




}




