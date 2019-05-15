package com.example.my_app;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.my_app.MH_view_admin_work.DownloadFileAsync;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class View_mh_work_details extends Activity implements OnItemClickListener{
	ListView l1;
	  SharedPreferences sh;
	    JSONParser jsonParser = new JSONParser();
	    String url="",ip="",hid="",eid="";
	    JSONArray ar=new JSONArray();
	    ArrayList<String> title,work_id,work,date,location;
	    	
	    ProgressDialog mProgressDialog;
	    private PowerManager.WakeLock mWakeLock;

	    static final int DIALOG_DOWNLOAD_PROGRESS = 2;
	    int pos=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_mh_work_details);
		ListView l1= (ListView)findViewById(R.id.listview5);
		 sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	        ip=sh.getString("ip","");
	        hid=sh.getString("id","");
	        eid=sh.getString("id","");
	        
	        
	        url="http://"+ip+":5000/employee_view_work";
	        
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("eid",eid));
	        
	        try {
	            JSONArray json=(JSONArray) jsonParser.makeHttpRequest(url ,"GET", params);
	            String s=null;

	          title = new ArrayList<String>(ar.length());
	            work_id = new ArrayList<String>(ar.length());
	           work = new ArrayList<String>(ar.length());
	            date= new ArrayList<String>(ar.length());
	            location= new ArrayList<String>(ar.length());

	            

	            for (int i1 = 0; i1 < json.length(); i1++) {
	                JSONObject c = json.getJSONObject(i1);
	                title.add(c.getString("title"));
	                work_id.add(c.getString("work_id"));
	                work.add(c.getString("work"));
	                date.add(c.getString("assign_date"));
	                location.add(c.getString("location"));

	                

	            }
	            l1.setAdapter(new Custom3(this,title,location,work));
	            l1.setOnItemClickListener(this);





	        }catch(Exception e)
	        {
	           Log.d("=============",e+"");

	        }

	        
		}
	 private void startDownload() {
	        String url = "http://"+sh.getString("ip","")+":5000/static/employee_work/"+work.get(pos);
	       Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
	        new DownloadFileAsync().execute(url);
	    }
	 @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	            case DIALOG_DOWNLOAD_PROGRESS:
	                mProgressDialog = new ProgressDialog(this);
	                mProgressDialog.setMessage("Downloading File...");
	                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	                mProgressDialog.setCancelable(false);
	                mProgressDialog.show();
	                return mProgressDialog;
	        }
	        return null;
	    }
	 
	  class DownloadFileAsync extends AsyncTask<String, String, String> {

	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
	                    getClass().getName());
	            mWakeLock.acquire();
	            showDialog(DIALOG_DOWNLOAD_PROGRESS);
	        }
	       	            @Override protected String doInBackground(String... aurl) {
	                int count;
	                try {

	                    URL url = new URL(aurl[0]);
	                    URLConnection conexion = url.openConnection();
	                    conexion.connect();

	                    int lenghtOfFile = conexion.getContentLength();
	                    Log.d("ANDRO_ASYNC", "Length of file: " + lenghtOfFile);

	                    String filename = work.get(pos);
	                    InputStream input = new BufferedInputStream(url.openStream());
	                    OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + filename);

	                    byte data[] = new byte[1024];

	                    long total = 0;

	                    while ((count = input.read(data)) != -1) {
	                        total += count;
	                        publishProgress(""+(int)((total*100)/lenghtOfFile));
	                        output.write(data, 0, count);
	                    }

	                    output.flush();
	                    output.close();
	                    input.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                return null;
	            }

	            protected void onProgressUpdate(String... progress) {
	                Log.d("ANDRO_ASYNC",progress[0]);
	                mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	            }
	            @Override
	            protected void onPostExecute(String unused) {
	                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
	            }
	        }

		
	

	  @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
		  getMenuInflater().inflate(R.menu.mh_view_admin_work, menu);
			return true;
		}
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
		   // pos=arg2;
	        //startDownload();
			
			 pos=arg2;
		//	 reports=report.get(arg2);
			  AlertDialog.Builder alert = new AlertDialog.Builder(View_mh_work_details.this);

		        alert.setTitle("Select Options");


		        alert.setPositiveButton("Download", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		          
		                // Do something with value!
		           	 startDownload();
		            }
		        });

		        alert.setNegativeButton("Send report", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		                // Canceled.
		            	
		            	

		            	Intent i=new Intent(getApplicationContext(),Employee_send_report.class);
		            	i.putExtra("wid", work_id.get(pos));
		            	
		            	startActivity(i);

	               

						
						
					}
				});
		    

		                    

		        alert.show();
	        
	        
	        
		}

		

			
		
	}



		