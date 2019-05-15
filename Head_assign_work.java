package com.example.thirdeye;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Head_assign_work extends Activity implements OnItemClickListener, OnItemSelectedListener {
	Spinner s1,s2;
	EditText e1,e2;
	Button b1,b2;
	SharedPreferences sp;
	String url="",ip="",hid="",url1="",ename="",titles="",wrid;
	JSONParser parser=new JSONParser();
	  JSONArray ar = new JSONArray();
	  private static final int FILE_SELECT_CODE1 = 2800;
	    String path1="",fileName="";

	    ArrayList<String> eid,name,title,work,wid;
	    String  wr_id,date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_head_assign_work);
		s1=(Spinner)findViewById(R.id.spinner1);
		s2=(Spinner)findViewById(R.id.spinner2);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ip=sp.getString("ip", "");
		 hid=sp.getString("id","");
		
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
		
		  try {
			  
		 url="http://"+ip+":5000/view_name";

	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("lid", hid));
	        JSONArray ar=(JSONArray)parser.makeHttpRequest(url ,"GET", params);
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
	        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();	
	        }
	
	        	try {

	                url1 = "http://" + ip + ":5000/view_title";

	                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	                params1.add(new BasicNameValuePair("lid", hid));
		            JSONArray ar=(JSONArray)parser.makeHttpRequest(url1 ,"GET", params1);
	                wid = new ArrayList<String>(ar.length());
		            title = new ArrayList<String>(ar.length());


		            for (int i1 = 0; i1 < ar.length(); i1++) {
		                JSONObject c = ar.getJSONObject(i1);
		               wid.add(c.getString("wid"));
		               title.add(c.getString("title"));

		            }
		        
	            ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,title);
	            s2.setAdapter(ad);
	            s2.setOnItemSelectedListener(this);


	        }
	        	  catch(Exception e)
	  	        {
	  	        	
	  	        }

	        
	        
	        
	        
	        
	        
           
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	                intent.setType("*/*");
	                intent.addCategory(Intent.CATEGORY_OPENABLE);
	                try
	                {
	                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"),FILE_SELECT_CODE1);
	                }
	                catch (android.content.ActivityNotFoundException ex) { // Potentially direct the user to the Market with a Dialog
	                    Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
	                }

	            }
	        });
				
			
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			


                String work=e1.getText().toString();
                 date=e2.getText().toString();



                int res=uploadFile(path1);
                if(res==1)
                {
                    Toast.makeText(getApplicationContext(), "Work Added Successfully", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getApplicationContext(),Head_Home.class
                    ));

                }
			}
			});
	}
	        
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.head_assign_work, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		  if(arg0==s1)
	        {
	           ename =eid.get(arg2);
	        }
	        if(arg0==s2)
	        {
	            titles=title.get(arg2);
	            wrid=wid.get(arg2);
	            Toast.makeText(getApplicationContext(), wrid, Toast.LENGTH_LONG).show();
	            
	        }
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	

		
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        switch (requestCode) {
	            case FILE_SELECT_CODE1:
	                if (resultCode == RESULT_OK) {
	                    // Get the Uri of the selected file
	                    Uri uri = data.getData();
	                    // Get the path
	                    try {
	                        path1 = FileUtils.getPath(this, uri);
	                        e1.setText(path1);
	                        File fil = new File(path1);
	                        int fln=(int) fil.length();

//							File file = new File(path);

	                        byte[] byteArray = null;
	                        try
	                        {
	                            InputStream inputStream = new FileInputStream(fil);
	                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                            byte[] b = new byte[fln];
	                            int bytesRead =0;

	                            while ((bytesRead = inputStream.read(b)) != -1)
	                            {
	                                bos.write(b, 0, bytesRead);
	                            }

	                            byteArray = bos.toByteArray();
	                            inputStream.close();
	                            Bitmap bmp= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	                            if(bmp!=null){


//	                                iv1.setVisibility(View.VISIBLE);
//	                                iv1.setImageBitmap(bmp);
	                            }
	                        }
	                        catch (Exception e) {
	                            // TODO: handle exception
	                        }
	                    }

	                    catch (URISyntaxException e) {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                    }
	                }
	                else{
	                    Toast.makeText(this,"Please select suitable file", Toast.LENGTH_LONG).show();
	                }
	                break;




	        }
	    }


	    public int uploadFile(String sourceFileUri ) {

	        fileName = sourceFileUri;
	        String upLoadServerUri = "http://"+sp.getString("ip", "")+":5000/fileuploadServlet";
	        Toast.makeText(getApplicationContext(), ename, Toast.LENGTH_LONG).show();
	        FileUpload fp = new FileUpload(fileName);
	        Map mp = new HashMap<String,String>();
	    
	        mp.put("eid", ename);
	        mp.put("title", titles);
	        mp.put("wid", wrid);

	        mp.put("hid", sp.getString("id",""));
	        mp.put("sdate",date);
	        fp.multipartRequest(upLoadServerUri, mp, fileName, "files", "application/octet-stream");
	        return 1;
	    }

	}
