package com.example.my_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Erase_files extends Activity {
    public static Button btsave;
    ListView l1;
    SharedPreferences sp;
    ArrayList<String> file,filename,fid;
    public static boolean at[];
    String s="";

    JSONParser jsonParser = new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erase_files);

        l1=(ListView)findViewById(R.id.listView1);

        btsave = (Button) findViewById(R.id.button2);


        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String imei=Phone_details.imei.get(Phone_details.pos);



        try {
            String url = "http://" + sp.getString("ip","") + ":5000/EraseFile";
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("imei", imei));
            JSONArray json = (JSONArray) jsonParser.makeHttpRequest(url, "GET", params);

            fid = new ArrayList<String>();
            file = new ArrayList<String>();
            filename = new ArrayList<String>();
            at = new boolean[json.length()];

            for (int j = 0; j < json.length(); j++) {
                JSONObject c1 = json.getJSONObject(j);
                fid.add(c1.getString("f_id"));
                filename.add(c1.getString("file"));
                String fname=c1.getString("file");
                String[] ff=fname.split("/");
                file.add(ff[ff.length-1]);
                at[j]=false;
            }
            l1.setAdapter(new CustomAdapter(getApplicationContext(), file));




        } catch (JSONException e) {


        }

        btsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                s="";

                for(int i=0;i<filename.size();i++)
                {
                    if(at[i]==true)
                    {
                        s=s+filename.get(i)+"@";
                    }

                }

              //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();


                InsertRequest(s);

                s="";

            }
        });




    }

    protected void InsertRequest(String s)
    {
        try {

            String imei=Phone_details.imei.get(Phone_details.pos);



            String url="http://"+sp.getString("ip","")+":5000/InsertErase";
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("res", s));
            params.add(new BasicNameValuePair("imei", imei));
            JSONObject json=(JSONObject)jsonParser.makeHttpRequest(url,"GET", params);

            String res=json.getString("result");

            if(res.equals("success"))
            {

                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Phone_home.class));
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Not Success", Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_LONG).show();
        }


    }
}
