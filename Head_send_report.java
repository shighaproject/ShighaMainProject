package com.example.thirdeye;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Head_send_report extends Activity {
	EditText e1,e2;
	Button b1,b2;
	String ip="",url="",id=" ",hid="",wid="";
	SharedPreferences sh;
	JSONParser jsonParser = new JSONParser();
	private static final int FILE_SELECT_CODE1 = 2800;
    String path1="",fileName="";

	String date,report;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_head_send_report);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ip=sh.getString("ip", "");
		 hid=sh.getString("id","");
		 wid=sh.getString("id","");
		 
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
				
				
				 String report=e2.getText().toString();
                 date=e1.getText().toString();



                int res=uploadFile(path1);
                if(res==1)
                {
                    Toast.makeText(getApplicationContext(), "report Added Successfully", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getApplicationContext(),Head_Home.class
                    ));

                }
			}
			});
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.head_send_report, menu);
		return true;
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
                        e2.setText(path1);
                        File fil = new File(path1);
                        int fln=(int) fil.length();

//						File file = new File(path);

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


//                                iv1.setVisibility(View.VISIBLE);
//                                iv1.setImageBitmap(bmp);
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
	        String upLoadServerUri = "http://"+sh.getString("ip", "")+":5000/fileuploadServlet1";
	        //Toast.makeText(getApplicationContext(), Toast.LENGTH_LONG).show();
	        FileUpload fp = new FileUpload(fileName);
	        Map mp = new HashMap<String,String>();
	    
	       
	        mp.put("hid", sh.getString("id",""));
	        mp.put("date",date);
	        fp.multipartRequest(upLoadServerUri, mp, fileName, "files", "application/octet-stream");
	        return 1;
	    }

	}


