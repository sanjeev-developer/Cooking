package com.saavor.user.activity;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;


import com.saavor.user.R;
import com.saavor.user.adapter.ListAdapter;

import java.util.ArrayList;

public class Testing extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewlist;
    ListAdapter listAdapter;
    ArrayList<String >listt= new ArrayList<>();
    ImageView backlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        String cusinelist= getIntent().getStringExtra("cusinelist");
        backlist= (ImageView) findViewById(R.id.tool_back_list);
        backlist.setOnClickListener(this);


        String myString = ""+cusinelist;

        String[] aSplit = myString.split(",");

        for(int i=0; i<aSplit.length;i++)
        {
            listt.add(aSplit[i]);

        }

        try
        {
            listt.add(aSplit[aSplit.length -1]);
        }catch(Exception e)
        {

        }

        listt.size();


        listAdapter = new ListAdapter(Testing.this, listt);
        recyclerViewlist = (RecyclerView) findViewById(R.id.recycle_cusine_show);
        recyclerViewlist.setLayoutManager(new LinearLayoutManager(Testing.this, LinearLayoutManager.VERTICAL, false));
        recyclerViewlist.setAdapter(listAdapter);


    }

    @Override
    public void onClick(View v) {
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
