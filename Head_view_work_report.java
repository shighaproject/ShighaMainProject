package com.example.thirdeye;

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

import com.example.thirdeye.Head_view_admin_work.DownloadFileAsync;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Head_view_work_report extends Activity implements OnItemSelectedListener,OnItemClickListener {
	   Spinner s1;
	    Button b1;
	    ListView lv;
	  
	    
	    String url="",ip="",hid="",url1="",titles="",url2="";
	    SharedPreferences sh;
	    JSONParser jsonParser = new JSONParser();

		  JSONArray ar = new JSONArray();
	    
	    ArrayList<String> eid,name,wr_id,title,report,date,wid;
	    String ename,wrk_id,reports;
	    
	    
	    ProgressDialog mProgressDialog;
	    private PowerManager.WakeLock mWakeLock;

	    static final int DIALOG_DOWNLOAD_PROGRESS = 2;
	    int pos=0;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_head_view_work_report);
		
		 s1=(Spinner) findViewById(R.id.spinner4);
	      //  s2=(Spinner) findViewById(R.id.spinner5);
	        b1=(Button)findViewById(R.id.button16);
	        lv=(ListView)findViewById(R.id.listView);
	        lv.setOnItemClickListener(this);
	        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	        ip=sh.getString("ip","");
	        hid=sh.getString("id","");
	        
	        
	        try {


	        url="http://"+ip+":5000/view_name";

	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("lid", hid));
	        JSONArray ar=(JSONArray)jsonParser.makeHttpRequest(url ,"GET", params);
	         // String s=null;
	            
	            eid = new ArrayList<String>(ar.length());
	            name = new ArrayList<String>(ar.length());




	            for (int i1 = 0; i1 < ar.length(); i1++) {
	                JSONObject c = ar.getJSONObject(i1);
	               eid.add(c.getString("eid"));
	               name.add(c.getString("name"));

	               Log.d("ress", ""+c);

	            }
	        
	           
            ArrayAdapter<String> ad1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
            s1.setAdapter(ad1);
            s1.setOnItemSelectedListener(this);
	        }
            catch(Exception e)
  	        {
  	        	
  	        }
	        

	        
	        
	       
	        b1.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {

	                try {

	                    url = "http://" + ip + ":5000/view_report_head";

	                    List<NameValuePair> params = new ArrayList<NameValuePair>();
	                    
	                   
	                    params.add(new BasicNameValuePair("eid",ename));
	                    
	                    JSONArray ar =(JSONArray) jsonParser.makeHttpRequest(url, "GET", params);
	                 //   String s = null;


	                    report = new ArrayList<String>(ar.length());
	                    date = new ArrayList<String>(ar.length());
	                    title = new ArrayList<String>(ar.length());
	                    wid = new ArrayList<String>(ar.length());

	                    for (int i1 = 0; i1 < ar.length(); i1++) {
	                        JSONObject c = ar.getJSONObject(i1);
	                        
	                        report.add(c.getString("report"));
	                        date.add(c.getString("date"));
	                        title.add(c.getString("title"));
	     	               	wid.add(c.getString("wr_id"));

	                    }

	                    lv.setAdapter(new Custom3(getApplicationContext(),title,date,report));

	                }
	                catch (Exception e)
	                {
           Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	                }
	            }
	        });

	}    

	 private void startDownload() {
	        String url = "http://"+sh.getString("ip","")+":5000/static/report/"+reports;
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
	       	            @Override
	            protected String doInBackground(String... aurl) {
	                int count;

	                try {

	                    URL url = new URL(aurl[0]);
	                    URLConnection conexion = url.openConnection();
	                    conexion.connect();

	                    int lenghtOfFile = conexion.getContentLength();
	                    Log.d("ANDRO_ASYNC", "Length of file: " + lenghtOfFile);

	                    String filename = reports;
	                    
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
		getMenuInflater().inflate(R.menu.head_view_work_report, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		ename=eid.get(arg2);
		pos=arg2;
		
		 
//		  if(arg0==s1)
//	        {
//	           ename =eid.get(arg2);
//	        }
//	        if(arg0==s2)
//	        {
//	            titles=title.get(arg2);
//	        }
	}

	 @Override
	    public void onNothingSelected(AdapterView<?> adapterView) {

	    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		 pos=arg2;
		 reports=report.get(arg2);
		  AlertDialog.Builder alert = new AlertDialog.Builder(Head_view_work_report.this);

	        alert.setTitle("Select Options");


	        alert.setPositiveButton("Download", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	          
	                // Do something with value!
	           	 startDownload();
	            }
	        });

	        alert.setNegativeButton("Send", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                // Canceled.
	            	
	            	
	            	try
					{
						url="http://" + ip + ":5000/fileuploadServlet1";
						Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

						List<NameValuePair> params=new ArrayList<NameValuePair>();
						
						params.add(new BasicNameValuePair("hid",sh.getString("id", "")));
						params.add(new BasicNameValuePair("wr_id",wid.get(pos)));
						params.add(new BasicNameValuePair("report",report.get(pos)));

						JSONObject json=(JSONObject)jsonParser.makeHttpRequest(url,"GET",params);
						String res=json.getString("task");
						Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();

						if(res.equals("success"))
						{
							Toast.makeText(getApplicationContext(),"okokook",Toast.LENGTH_LONG).show();
						}
						else
						{

							
							Toast.makeText(getApplicationContext(),"errrorr",Toast.LENGTH_LONG).show();
	
						}

					}
					catch (Exception e1)
					{
						Toast.makeText(getApplicationContext(),e1.toString(),Toast.LENGTH_LONG).show();

					}
	            	
	            	

               

					
					
				}
			});
	    

	                    

	        alert.show();



		
	}
	}