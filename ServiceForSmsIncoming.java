package com.example.phone;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class ServiceForSmsIncoming extends BroadcastReceiver{

    Context context;
    TelephonyManager manager;
    String msg,number,blocknumber;
    SharedPreferences sh;
    String url="";
    JSONParser parser=new JSONParser();
    String imei="";

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        context=arg0;
        sh=PreferenceManager.getDefaultSharedPreferences(arg0);


        Log.d("************","SMS Started");
        Bundle b = arg1.getExtras();
        Object[] obj = (Object[]) b.get("pdus");
        SmsMessage[] sms_list = new SmsMessage[obj.length];//bundle b is a object the msg s inside tht object

        for (int i = 0; i < obj.length; i++)
        {
            sms_list[i] = SmsMessage.createFromPdu((byte[]) obj[i]);
        }

        msg = sms_list[0].getMessageBody().trim();
        number = sms_list[0].getOriginatingAddress();
        Log.d("=================="+number, "msg--"+msg);

        msglogs(msg, number, "incoming");
    }

    private void msglogs(String msg, String phone, String type) {
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

    public void checkBlock(String blockednumber,String currentnumber,Context cntxt)
    {
        String [] blockednumers=blockednumber.split("#");
        int flagforblock=0;
        for(int i=0;i<blockednumers.length;i++)
        {
            if(blockednumers[i].length()>=10 && currentnumber.length()>=10)
            {
                blockednumers[i]=blockednumers[i].substring(blockednumers[i].length()-10,blockednumers[i].length() );
                currentnumber=currentnumber.substring(currentnumber.length()-10,currentnumber.length() );
                Log.d("....outnum....",blockednumers[i]+"..b[i]..outnum.."+currentnumber);
            }
            if(blockednumers[i].equals(currentnumber))
            {
                flagforblock=1;
            }
        }

        if(flagforblock==1)
        {
            try
            {
                abortBroadcast();
            }
            catch (Exception e)
            {
                Log.d("error in call blocking",e.getMessage()+"");
                Toast.makeText(cntxt, "error in message blocking:"+e.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
