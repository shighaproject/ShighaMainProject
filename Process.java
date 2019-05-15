package com.example.thirdeye;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Process extends Activity implements OnItemClickListener{
	  	ListView lv;
	    SharedPreferences sp;
	    int port=9898;
	    String clientsentence="";
	    String systems[];
	    String[] process;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process);
		 	lv=(ListView)findViewById(R.id.listView1);
	        lv.setOnItemClickListener(this);
	        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

	        try{
	            String ip=sp.getString("sid","");
	            Socket client=new Socket(ip, port);
	            PrintWriter printwriter=new PrintWriter(client.getOutputStream(),true);
	            printwriter.write("process");
	            printwriter.println("\r\n");
	            //write the message to output stream
	            printwriter.flush();

	            BufferedReader inFromClient=new BufferedReader(new InputStreamReader(client.getInputStream()));

	            clientsentence = inFromClient.readLine();
	            client.close();
	            systems=clientsentence.split("\\-");
	            ArrayAdapter<String> ad=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,systems);
	            lv.setAdapter(ad);
	        }
	        catch(Exception e)
	        {
	            Toast.makeText(getApplicationContext(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();

	        }

	    }


	 

	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		 process = systems[arg2].split(" ");

	        AlertDialog.Builder ald=new AlertDialog.Builder(Process.this);
	        ald.setTitle("Kill Process??")
	                .setPositiveButton(" YES ", new DialogInterface.OnClickListener() {

	                    @Override
	                    public void onClick(DialogInterface arg0, int arg1) {
	                        try{
	                            String ip=sp.getString("sid","");
	                            Socket client=new Socket(ip, port);
	                            PrintWriter printwriter=new PrintWriter(client.getOutputStream(),true);
	                            printwriter.write("kill-"+process[0]);
	                            printwriter.println("\r\n");
	                            client.close();
	                        }
	                        catch(Exception e)
	                        {
	                            Toast.makeText(getApplicationContext(),"error"+e.getMessage(),Toast.LENGTH_LONG).show();
	                        }
	                    }
	                })
	                .setNegativeButton(" NO ", new DialogInterface.OnClickListener() {

	                    @Override
	                    public void onClick(DialogInterface arg0, int arg1) {

	                    }
	                });

	        AlertDialog al=ald.create();
	        al.show();


	    }
	

		
	}

	
