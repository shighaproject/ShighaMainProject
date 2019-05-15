package com.example.my_app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;



public class UnmarkallAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<String> a;

    public static CheckBox checkBox;

    public UnmarkallAdapter(Context applicationContext, ArrayList<String> a) {

        this.context = applicationContext;
        this.a = a;


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return a.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent)
    {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.activity_unmarkall_adapter, null);
        }
        else
        {
            gridView=(View)convertview;
        }

        TextView tv1=(TextView)gridView.findViewById(R.id.textView1);

        checkBox=(CheckBox)gridView.findViewById(R.id.checkBox1);

        checkBox.setChecked(false);
        Backup_files.at[position]=false;

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//				SearchDonors.at[position]=arg1;

            	 Backup_files.at[position]=true;

            	 Backup_files.btunmarkall.setVisibility(View.VISIBLE);
            	 Backup_files.btmarkall.setVisibility(View.VISIBLE);
            }
        });


        tv1.setText(a.get(position));


        tv1.setTextColor(Color.BLACK);

        return gridView;
    }
}