package com.example.phone;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;





import android.provider.CallLog;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Build;
import android.os.IBinder;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CallDetails extends Service {
	
	
	
	SharedPreferences sh;
	Editor ed;
	Cursor callDetailCursor;
	String blocknumber="";

	String opn;
	String dt="",tm="";

	TelephonyManager telman;
	int flag2=0;

	 public static int flg=0;	
	 String phnop="",imei,url="";
	 JSONParser parser=new JSONParser();
	 @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onCreate() 
	 {		
		 
		// TODO Auto-generated method stub
		super.onCreate();
		 Toast.makeText(getApplicationContext(), "cserv service started", Toast.LENGTH_SHORT).show();
			
		if (android.os.Build.VERSION.SDK_INT > 9) 
		{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
		
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ed=sh.edit();

	

		 SimpleDateFormat tet=new SimpleDateFormat("hh:mm:ss");
		 tm=tet.format(new Date());
		 
		 telman=(TelephonyManager)getApplicationContext().getSystemService(TELEPHONY_SERVICE);
		 imei=telman.getDeviceId().toString();
		
		 telman.listen(phlist,PhoneStateListener.LISTEN_CALL_STATE);
		 Log.d("....old...", ".....00");
		 
		 
		
	}
  
	 public PhoneStateListener phlist=new PhoneStateListener()
   {
	   public void onCallStateChanged(int state, String inNum) 
	   {
		   Log.d("====================="+inNum,"====================================================");
		  switch (state) 
		  {
		     case TelephonyManager.CALL_STATE_IDLE:
				 String duration="";
		    	 try 
					{
		    		 Log.d("kkkkkkkkkkkkkkkkk","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
					 callDetailCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null,null,null,android.provider.CallLog.Calls.DATE + " DESC limit 1");
					int callDuration=callDetailCursor.getColumnIndex(CallLog.Calls.DURATION);
					if(callDetailCursor.getCount()>0)
				    {		
						Toast.makeText(getApplicationContext(), "inside", Toast.LENGTH_SHORT).show();
						 while(callDetailCursor.moveToNext())
					     {
							duration=callDetailCursor.getString(callDuration);
						 }
						 
				    			SharedPreferences shp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
								Editor edit=shp.edit();
								edit.putString("duration", duration);
								edit.commit();
					}
					callDetailCursor.close();
					Log.d("....1...."+duration, "..ooooooooooooooooo..");
					}
						 
						catch (Exception e) 
						{
							// TODO Auto-generated catch block
							//Toast.makeText(getApplicationContext(), "error1 in call:"+e.getMessage(), Toast.LENGTH_SHORT).show();
							Log.d("error1",e.getMessage());
						}
						
						
						SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
						String name = sh.getString("callstatus", "hi");
				
						if(name.equalsIgnoreCase("incoming"))
						{
							String ss=sh.getString("incoming number", "");
							String dur=sh.getString("duration", "")+" sec";
							
							inscall(imei,ss,"incoming",dur);
							flg=0;
						 }						
						 else if(flg==1)
						 {
							 Log.d("....1...."+duration, "..outcall..");
							 try 
							 {
								opn=sh.getString("num", "");

								String dur=sh.getString("duration", "")+" sec";
								inscall(imei,opn+"","outgoing",dur);
								
							 } 
							 catch (Exception e) 
							 {
								Log.d("error3",e.getMessage());
							 }
							 flg=0;
						 }
						 						
				         SharedPreferences.Editor editor = sh.edit();
				         editor.putString("callstatus","idle");
				         editor.commit();
				         
				         break;
			
		     case TelephonyManager.CALL_STATE_OFFHOOK:
					
	    	 		SimpleDateFormat sm=new SimpleDateFormat("dd/MM/yyyy");
	    	 		SimpleDateFormat sn=new SimpleDateFormat("hh:mm:ss");
		
	    	 		flg=1;
	    	 		flag2=0;
	    	 		
	    	 		dt=sm.format(new Date());
	    	 		tm=sn.format(new Date());
	    	 		
	    	 		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    	 		String opn=pref.getString("num", "");
		
	    	 		if(opn.equalsIgnoreCase(""))
	    	 		{
	    	 				opn=phnop;
	    	 		}
		
	    	 		SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    	 		String blknum=sp.getString("block","");
	    	 		
	    	 		int xy=0;
	    	 		Log.d("...outn..",blknum+"..outn.."+opn);
	    	 		if(!blknum.equalsIgnoreCase("@"))
	    	 		{
		
	    	 			String b[]=blknum.split("@");
	    	 			for(int i=0;i<b.length;i++)
	    	 			{
	    	 				if(b[i].length()>=10 && opn.length()>=10)
	    	 				{
	    	 					b[i]=b[i].substring(b[i].length()-10,b[i].length() );
	    	 					opn=opn.substring(opn.length()-10,opn.length() );
	    	 					Log.d("....outnum....",b[i]+"..b[i]..outnum.."+opn);
	    	 				}				
	    	 				if(b[i].equals(opn))
	    	 				{
	    	 					
	    	 					xy=1;
	    	 					//Toast.makeText(getApplicationContext(), "Equal", Toast.LENGTH_LONG).show();
	    	 				}
	    	 			}		
	    	 		}
		
	    	 		if(xy==1)
	    	 		{
//	    	 			Toast.makeText(getApplicationContext(), "BLOCKED", Toast.LENGTH_SHORT).show();
	    	 			////call reject					
//	    	 			try 
//	    	 			{		
//	    	 				
//	    	 				telman = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//							Class c = Class.forName(telman.getClass().getName());
//							Method m = c.getDeclaredMethod("getITelephony");
//							m.setAccessible(true);
//						 ITelephony telephonyService = (ITelephony)m.invoke(telman);
//							telephonyService.endCall();
//	 			} 
//	    	 			catch (Exception e)
//	    	 			{
//	    	 				// TODO: handle exception
//	    	 				Toast.makeText(getApplicationContext(), "error4 in call:"+e.getMessage(), Toast.LENGTH_SHORT).show();
//	    	 				Log.d("error4",e.getMessage());
//	    	 			}
	    	 		}
	    	 		
	    	 		
	    	 		break;
	    	 		
	    	 		
	    	 		
	    	 		 
	    	 		

	     case TelephonyManager.CALL_STATE_RINGING:

	     			phnop=inNum;
	     			flag2=1;
	     			//Toast.makeText(getApplicationContext(), phnop, Toast.LENGTH_LONG).show();
					sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					Editor edd=sh.edit();
					edd.putString("incoming number", phnop);
					edd.commit();
					
					blknum=sh.getString("block","");
				
					// saving the incoming number	
					
					SharedPreferences.Editor ed= sh.edit();
					ed.putString("callstatus","incoming");
					ed.putString("num",inNum);
					ed.commit();
					//ends
								
					int xyz=0;	
					Log.d("...rnggg..",blknum+"..innum.."+inNum);
					if(!blknum.equalsIgnoreCase("@"))
					{        
						String bb[]=blknum.split("@");
						for(int i=0;i<bb.length;i++)
						{
							if(bb[i].length()>=10)
							{
								bb[i]=bb[i].substring(bb[i].length()-10,bb[i].length() );
								inNum=inNum.substring(inNum.length()-10,inNum.length() );
								Log.d("...rnggg..substring..",bb[i]+"..innum.."+inNum);
							}
			
							if(bb[i].equals(inNum))
							{		
									xyz=1;
							}
						}
					}		////call reject 
					if(xyz==1)
					{		
						try
						{
							Log.d("...rnggg..","cutng........");  
		      		
							telman = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
							Class c = Class.forName(telman.getClass().getName());
							Method m = c.getDeclaredMethod("getITelephony");
							m.setAccessible(true);
//							ITelephony telephonyService = (ITelephony)m.invoke(telman);
//							telephonyService.endCall();
							
							
						 				 
						}
						catch (Exception e) 
						{
							// TODO: handle exception
							Toast.makeText(getApplicationContext(), "error5 in call:"+e.getMessage(), Toast.LENGTH_SHORT).show();
							Log.d("error5",e.getMessage());
							
						}
					}	
					
				
					break;
	  }	
	 
}

	   private void inscall(String imei, String ss, String type, String dur) {
			
			SimpleDateFormat sdate=new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
			String dt=sdate.format(new Date());

	        url="http://"+sh.getString("ip","")+":5000/InsertCall";

	        try {
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("imei", sh.getString("imei","")));
	            params.add(new BasicNameValuePair("type", type));
	            params.add(new BasicNameValuePair("number", ss));
	            params.add(new BasicNameValuePair("duration", dur));

	            JSONObject json=(JSONObject)parser.makeHttpRequest(url,"GET", params);
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
   };
  
	
	public IBinder onBind(Intent arg0) {
		
		return null;
	}



	
  

}
