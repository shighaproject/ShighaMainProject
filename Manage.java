package com.example.thirdeye;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;



import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.PrintWriter;
import java.net.Socket;

public class Manage extends Activity {

    Button b1,b2,b3,b4;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);

        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    mytask m =new mytask();
                    m.execute("shutdown-");
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    mytask m = new mytask();
                    m.execute("restart-");
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "error"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    mytask m = new mytask();
                    m.execute("logoff-");
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    mytask m = new mytask();
                    m.execute("sleep-");
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    class mytask extends AsyncTask<String, Void, Void> {
        final int port=9898;
        protected Void doInBackground(String... str)
        {
            try{

                String ip=sp.getString("sid", "");
                Socket client = new Socket(ip,port);
                PrintWriter printWriter= new PrintWriter(client.getOutputStream(),true);
                printWriter.write(str[0]);

                printWriter.flush();

                client.close();
            }
            catch(Exception ex)
            {
                Log.d("11111111",ex+"");
            }
            return null;
        }
    }
}
