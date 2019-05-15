package com.example.phone;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class GetDataService extends Service {

	private Handler handler = new Handler();
	TelephonyManager manager;
	SharedPreferences sh;
	
    private File root;
    ArrayList<String> fileList;
    ArrayList<String> fid,fname,ear_id,ear_fname;
    String url="",fileName="",res="";

    JSONParser jsonParser=new JSONParser();

	String[] fileid;
	ActivityManager actvityManager;
	
	
	@Override
	public void onCreate() {
		super.onCreate();

        Log.d("************","Data Started");
		manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		handler.post(getdata);
	}
	
	public Runnable getdata = new Runnable() {
	
		@Override
		public void run() {
			Log.d("tag", "----inside----");
			try{
				insertfiles();
			}catch(Exception ex){}
            erasefiles();
            sendfiles();
          
            handler.postDelayed(getdata, 30000);
		}
	};	
	
	public void sendfiles()
	{
		url="http://"+sh.getString("ip","")+":5000/ViewFile";
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("imei", sh.getString("imei","")));

			 JSONArray json=(JSONArray) jsonParser.makeHttpRequest(url ,"GET", params);

			fid=new ArrayList<String>();
			fname=new ArrayList<String>();

			for (int i = 0; i < json.length(); i++) {
				JSONObject c = json.getJSONObject(i);
				fid.add(c.getString("b_id"));
				fname.add(c.getString("f_name"));
			}
			update_sendfile();

		}
		catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
	}

	private void update_sendfile() {
		// TODO Auto-generated method stub

        for(int i=0;i<fid.size();i++)
        {
            uploadFile(fname.get(i),fid.get(i));
        }

	}

    public int uploadFile(String sourceFileUri, String f_id) {

        fileName = root+"/"+sourceFileUri;
        String upLoadServerUri = "http://"+sh.getString("ip", "")+":5000/UpdateSend";
        Toast.makeText(getApplicationContext(), upLoadServerUri, Toast.LENGTH_LONG).show();
        FileUpload fp = new FileUpload(fileName);
        Map mp = new HashMap<String,String>();
        mp.put("fileid", f_id);
        fp.multipartRequest(upLoadServerUri, mp, fileName, "files", "application/octet-stream");
        return 1;
    }

	private void insertfiles() throws UnsupportedEncodingException {

		Log.d("tag", "----inside insert----");
		
	   fileList = new ArrayList<String>();
	   
	   root = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/documents/");
	   getfile(root);

	   for(int i = 0; i < fileList.size(); i++)
	   {

           res +=fileList.get(i)+ "@";
	   }

        url="http://"+sh.getString("ip","")+":5000/Files";
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("imei", sh.getString("imei","")));
            params.add(new BasicNameValuePair("file", res));

            JSONObject json=(JSONObject) jsonParser.makeHttpRequest(url,"GET", params);

            String s=json.getString("result");
            if(s.equals("ok"))
            {

            	fileList.clear();
            	
            }
        }
        catch(Exception e)
        {
           Log.d("Erorrrrrrr",e+"");

        }



   }
   

	public ArrayList<String> getfile(File dir) {
		Log.d("tag", "----inside getfile----");
		File listFile[] = dir.listFiles();
		if (listFile != null && listFile.length > 0) {  
			for (int i = 0; i < listFile.length; i++) {

				if (listFile[i].isDirectory()) {
					//fileList.add(listFile[i]);
					getfile(listFile[i]);
				} 
				else {

					double bytes = listFile[i].length();
					double kilobytes = (bytes / 1024);
					double megabytes = (kilobytes / 1024);
					if(megabytes<=1){
					if (listFile[i].getName().endsWith(".pdf")
							|| listFile[i].getName().endsWith(".jpg")
							|| listFile[i].getName().endsWith(".jpeg")
							|| listFile[i].getName().endsWith(".gif")
							|| listFile[i].getName().endsWith(".txt")
							|| listFile[i].getName().endsWith(".doc")
							|| listFile[i].getName().endsWith(".xlc"))

					{
						fileList.add(listFile[i].getName());
					}
					}
				}
			}
		}
		return fileList;
	}
   
   protected void erasefiles() {

       url="http://"+sh.getString("ip","")+":5000/ViewDelete";
       try {
           List<NameValuePair> params = new ArrayList<NameValuePair>();
           params.add(new BasicNameValuePair("imei", sh.getString("imei","")));

           JSONArray json=(JSONArray) jsonParser.makeHttpRequest(url,"GET", params);


           ear_id=new ArrayList<String>();
           ear_fname=new ArrayList<String>();

           for (int i = 0; i < json.length(); i++) {
               JSONObject c = json.getJSONObject(i);
               ear_id.add(c.getString("ers_id"));
               ear_fname.add(c.getString("f_name"));
           }
           delete_file();

       }
       catch(Exception e) {
           Log.d("===========",e+"");
       }

  }

  public void delete_file()
  {
      for(int i = 0; i < ear_fname.size(); i++)
      {
          File f = new File(root+"/"+ear_fname.get(i));
          if(f.exists()){
              try{
                  f.delete();
              }
              catch(Exception ex)
              {

              }
              updateerase(ear_id.get(i));
          }
          else{
              Log.d("----------", "nooooooooooooooottt-------");

          }
      }
  }
  
  private void updateerase(String ff) {
      url="http://"+sh.getString("ip","")+":5000/UpdateDelete";
      try {
          List<NameValuePair> params = new ArrayList<NameValuePair>();
           params.add(new BasicNameValuePair("fid", ff));

          JSONObject json=(JSONObject) jsonParser.makeHttpRequest(url,"GET", params);
          String s=null;

          s=json.getString("result");
          if(s.equals("success"))
          {

          }
      }
      catch(Exception e)
      {
          Log.d("Erorrrrrrr",e+"");

      }
  }
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
