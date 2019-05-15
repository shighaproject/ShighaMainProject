//package com.example.phone;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONObject;
//
//import android.Manifest;
//import android.os.Bundle;
//import android.os.Handler;
//import android.preference.PreferenceManager;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//
//import android.support.v4.app.ActivityCompat;
//
//import android.telephony.SmsManager;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//import android.view.Menu;
//import android.widget.Toast;
//
//public class Main extends Activity {
//	
//	
//	Handler hd;
//    TelephonyManager manager;
//    String imei = "", phoneno = "",simno="";
//    SharedPreferences sp;
//
//    String url="",ip="";
//    JSONParser jParser = new JSONParser();
//
//    public static boolean hasPermissions(Context context, String... permissions) {
//        if (context != null && permissions != null) {
//            for (String permission : permissions) {
//                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		
//		
//
//
//        hd = new Handler();
//        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//        manager = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
//
//        int PERMISSION_ALL = 1;
//        String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG, Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE};
//
//        if (!hasPermissions(this, PERMISSIONS)) {
//            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
//        }
//
//        ip="192.168.43.238";
//        SharedPreferences.Editor ed=sp.edit();
//        ed.putString("ip",ip);
//        ed.commit();
//
//
//        String no = sp.getString("simno", "");
//
//
//        try {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            imei = manager.getDeviceId().toString();
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            simno = manager.getSimSerialNumber().toString();
//
//        }
//        catch (Exception e)
//        {
//          Log.d("Erorrrrrrr",e+"");
//        }
//        if(no.equals(""))
//        {
//
//            Toast.makeText(getApplicationContext(), "New User...!!",Toast.LENGTH_SHORT).show();
//            SharedPreferences.Editor ed1=sp.edit();
//            ed1.putString("simno",simno);
//            ed1.commit();
//            hd.postDelayed(r,3000);
//        }
//        else if (no.equals(simno)){
//
//            SharedPreferences.Editor ed1=sp.edit();
//            ed1.putString("simno",simno);
//            ed1.commit();
//            hd.postDelayed(r,3000);
//        }
//
//        else
//        {
//
//            SmsManager sm=SmsManager.getDefault();
//            sm.sendTextMessage("9539263076", null,"The Sim is changed" +"...!!" , null, null);
//
//            hd.postDelayed(r,3000);
//
//        }
//
//    }
//
//       Runnable r=new Runnable() {
//        @Override
//        public void run() {
//
//
//            try
//            {
//
//                url="http://"+ip+":5000/PhoneLogin";
//               
//                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("imei",imei));
//
//
//                JSONObject json= (JSONObject)jParser.makeHttpRequest(url, "GET", params);
//                String ss = json.getString("result");
//
//                if(!ss.equals("no")) {
//                    Intent in = new Intent(getApplicationContext(), ServiceForSmsOutgoing.class);
//                    startService(in);
//
//                    Intent il = new Intent(getApplicationContext(), LocationService.class);
//                    startService(il);
//                    Intent ig = new Intent(getApplicationContext(), GetDataService.class);
//                    startService(ig);
//
//
//                    Intent igg = new Intent(getApplicationContext(), CallDetails.class);
//                    startService(igg);
//
//
//
//                    SharedPreferences.Editor ed1 = sp.edit();
//                    ed1.putString("imei", ss);
//                    ed1.commit();
//
//                    startActivity(new Intent(getApplicationContext(),Phone.class));
//                }
//
//
//            }
//            catch (Exception e)
//            {
//                Toast.makeText(getApplicationContext(),"Error_rr "+e.getMessage()+"", Toast.LENGTH_LONG).show();
//                e.printStackTrace();
//            }
//
//        }
//    };
//	
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//}
