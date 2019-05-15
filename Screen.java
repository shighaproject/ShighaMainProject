package com.example.thirdeye;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.example.thirdeye.Manage.mytask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class Screen extends Activity {
	  SharedPreferences sp;
	    ImageView iv;
	    mytask mt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen);
		iv=(ImageView)findViewById(R.id.imageView1);
        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mt=new mytask();
        mt.execute("screen-");
 
	}
	public class mytask extends AsyncTask<String, byte[], String> {
    final int port=9999;
    byte[]buff;
    protected String doInBackground(String...str)
    {
        while(true)
        {
            try
            {
                if(isCancelled())
                    break;
                Log.d("*********","-----------------------");
                String ip=sp.getString("sid", "");
               // String ip="192.168.43.18";
                Socket client=new Socket(ip,port);
                Log.d("*******"+ip, "----------------------------------"+port);
                Log.d("*******", "-----------2-----------------------");//connect to server
                PrintWriter printwriter = new PrintWriter(client.getOutputStream(),true);
                printwriter.println("screen-");
                printwriter.println("\r\n");
                //write the message to output stream
                printwriter.flush();
                Log.d("*******", "-------------3---------------------");
                //printwriter.close();

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));

                //client = new Socket(ip,port);
                buff=new byte[8888888];

                int br=0;
                String tl="";
                String ima="";

                int cnt=0;

                Log.d("iam here","***********************");
                // cnt=client.getInputStream().read(buff);


                while((tl=inFromClient.readLine())!=null )
                {

                    ima+=tl;
                    if(tl.contains("end"))
                        break;

                }
                client.close();
                Log.d("data"+ima,buff.length+"************");
                buff=Base64.decode(ima, Base64.DEFAULT);
                publishProgress(buff);


            } catch (Exception e)
            {
                android.util.Log.d("11111111111", ""+e.getMessage());
            }

        }
        return null;
    }
    protected void onProgressUpdate(byte[]...  result) {
        // setProgressPercent(progress[0]);
        android.util.Log.d("image result", result[0].length+"-=-=-=");
        try {

            Bitmap bmp=BitmapFactory.decodeByteArray(result[0], 0, result[0].length);

            iv.setImageBitmap(bmp);



        } catch (Exception e) {
            // TODO: handle exception

            Log.d("image error", e.getMessage());
        }


    }
	
    protected void onPostExecute(byte[] result) {

        Log.d("jjj", "iiiii..."+result.length);
    }

}

public void onBackPressed(){
    super.onBackPressed();
    mt.cancel(true);
}
}
