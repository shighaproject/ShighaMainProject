package com.example.my_app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes.Name;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;



import android.net.Uri;
import android.os.Bundle;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MH_register_employee extends Activity  implements OnItemSelectedListener{
	Spinner s1;
    EditText e1,e2,e3,e4,e5,e6,e7;
    RadioButton r1,r2;
    Button b1,b2;
    SharedPreferences sp;
	String url="";
	String ar[]={"Marketing employee"};
	ImageView img1;
	String hid="";
	JSONParser jsonParser = new JSONParser();


	
	
	String path1="";
	String id="",ip="",ur="",caption="";
	private static final int FILE_SELECT_CODE = 0;
    String path,fileName,attach,type,fname;
    String em_type,name,place,dob,gender,mobile,email,quali,exp,photo;
    

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mh_register_employee);
		s1=(Spinner)findViewById(R.id.spinner1);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		e5=(EditText)findViewById(R.id.editText5);
		e6=(EditText)findViewById(R.id.editText6);
		e7=(EditText)findViewById(R.id.editText7);
		//e8=(EditText)findViewById(R.id.editText8);
		img1=(ImageView)findViewById(R.id.imageView1);
		r1=(RadioButton)findViewById(R.id.radio0);
		r2=(RadioButton)findViewById(R.id.radio1);
	
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		ip=sp.getString("ip", "");
		hid=sp.getString("id","");
		ArrayAdapter<String>ad=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,ar);
		s1.setAdapter(ad);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				 Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //getting all types of files
			        intent.setType("*/*");
			        intent.addCategory(Intent.CATEGORY_OPENABLE);

			        try {
			            startActivityForResult(Intent.createChooser(intent, ""),FILE_SELECT_CODE);
			        } catch (android.content.ActivityNotFoundException ex) {

			            Toast.makeText(getApplicationContext(), "Please install a File Manager.",Toast.LENGTH_SHORT).show();
			        }
				
				
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 em_type=s1.getSelectedItem().toString();
				 name=e1.getText().toString();
				 place=e2.getText().toString();
				 dob=e3.getText().toString();
				 gender="";
				if(r1.isChecked())
				{
					gender=r1.getText().toString();
			
				}
				else 
				{
					gender=r2.getText().toString();
				}
				
				
				mobile=e4.getText().toString();
				email=e5.getText().toString();
				quali=e6.getText().toString();
				exp=e7.getText().toString();
			//	photo=e8.getText().toString();
				
				
				int res=uploadFile(path1);
				if(res==1)
				{
					Toast.makeText(getApplicationContext(), "uploaded", Toast.LENGTH_LONG).show();
					Intent i=new Intent(getApplicationContext(),MH_home.class);
					startActivity(i);
				}
				else
				{Toast.makeText(getApplicationContext(), " error", Toast.LENGTH_LONG).show();
				}
				
//				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mh_register_employee, menu);
		return true;
	}
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		switch (requestCode) {
//	        case FILE_SELECT_CODE:
//		        if (resultCode == RESULT_OK) {
//		            // Get the Uri of the selected file 
//		            Uri uri = data.getData();
//		            Log.d("File Uri", "File Uri: " + uri.toString());
//		            // Get the path
//		            try {
//						path = FileUtils.getPath(this, uri);
//						//e4.setText(path1);
//						Log.d("path", path);
//						File fil = new File(path);
//						int fln=(int) fil.length();
//						//e2.setText(path1);
//				
////						File file = new File(path);
//			        
//						byte[] byteArray = null;
//			        try
//			        {
//				        InputStream inputStream = new FileInputStream(fil);
//				        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//				        byte[] b = new byte[fln];
//				        int bytesRead =0;
//				        
//				        while ((bytesRead = inputStream.read(b)) != -1)
//				        {
//				        	bos.write(b, 0, bytesRead);
//				        }
//				        
//				        byteArray = bos.toByteArray();
//				        inputStream.close();
//				        Bitmap bmp=BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//				        if(bmp!=null){
////				        	
////				    
//						img1.setVisibility(View.VISIBLE);
//			        	 img1.setImageBitmap(bmp);
//			        }
//			        }
//				        catch (Exception e) {
//							// TODO: handle exception
//						}
//				        }		
//					
//					 catch (URISyntaxException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}	     
//		        }
//				else{
//						Toast.makeText(this,"Please select suitable file", Toast.LENGTH_LONG).show();
//				}        
//		        break;
//		        
//	        
//	        
//		}
//	
//		
//    }
//	public int uploadFile(String sourceFileUri) {
//		
//		 fileName = sourceFileUri;
//		 String upLoadServerUri ="http://"+sp.getString("ip", "")+":5000/mh_register";
//		 
//      Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();
//		FileUpload fp = new FileUpload(fileName);
//		Map mp = new HashMap<String,String>();
//		mp.put("em_type", em_type);
//		mp.put("headid", hid);
//		mp.put("name", name);
//		mp.put("place", place);
//		mp.put("dob", dob);
//		mp.put("gender", gender);
//		mp.put("mobile", mobile);
//		mp.put("email", email);
//		mp.put("qualification", quali);
//		mp.put("experience", exp);
//	
//		fp.multipartRequest(upLoadServerUri, mp, fileName, "files", "application/octet-stream");
//		return 1;
//	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
	        case FILE_SELECT_CODE:
		        if (resultCode == RESULT_OK) {
		            // Get the Uri of the selected file 
		            Uri uri = data.getData();
		            Log.d("File Uri", "File Uri: " + uri.toString());
		            // Get the path
		            try {
						path1 = FileUtils.getPath(this, uri);
						//e4.setText(path1);
						Log.d("path", path1);
						File fil = new File(path1);
						int fln=(int) fil.length();
				//	e2.setText(path1);
				
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
				        Bitmap bmp=BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
				        if(bmp!=null){
				        	
				    
							img1.setVisibility(View.VISIBLE);
				        	 img1.setImageBitmap(bmp);
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
	public int uploadFile(String sourceFileUri) {
		
		 fileName = sourceFileUri;
		String upLoadServerUri ="http://"+sp.getString("ip", "")+":5000/mh_register"; 
      // Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();
		FileUpload fp = new FileUpload(fileName);
		Map mp = new HashMap<String,String>();
		
		mp.put("em_type", em_type);
		mp.put("headid", hid);
		mp.put("name", name);
		mp.put("place", place);
		mp.put("dob", dob);
		mp.put("gender", gender);
		mp.put("mobile", mobile);
		mp.put("email", email);
		mp.put("qualification", quali);
		mp.put("experience", exp);
		fp.multipartRequest(upLoadServerUri, mp, fileName, "files", "application/octet-stream");
		return 1;
	}

	public boolean isVaalidname(String value)
	{
	 Pattern ps=Pattern.compile("^[a-zA-Z]+$");
	 Matcher ms=ps.matcher(value);
	 boolean bs=ms.matches();
	 return bs;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		em_type=ar[arg2];		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
