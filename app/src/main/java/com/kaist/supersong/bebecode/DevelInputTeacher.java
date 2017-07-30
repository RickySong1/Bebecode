package com.kaist.supersong.bebecode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DataStructure.BabyListCard;
import DataStructure.BabyListData;
import mymanager.MyFileManager;
import mymanager.MySocketManager;
import questions.MonthQuestions;

/**
 * Created by SuperSong on 2017-03-27.
 */

public class DevelInputTeacher extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devel_input_form);
        Intent intent = getIntent();

        String name = intent.getExtras().getString("NAME");
        String id = intent.getExtras().getString("ID");
        String status = intent.getExtras().getString("STATUS");
        String month = intent.getExtras().getString("MONTH");

        MyFileManager fileM = new MyFileManager();
        MonthQuestions myMonthQuestion = fileM.getMonthQuestions(month);
        String tester = fileM.getMyMonthTestRange(month);

        MySocketManager socketM = new MySocketManager(MainActivity.USERTYPE);

        TextView t_name = (TextView)findViewById(R.id.develinput_name);
        TextView t_status = (TextView)findViewById(R.id.develinput_status);

        t_name.setText(name);
        t_status.setText(month+"개월 ("+tester+")");

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.devel_input_recycleview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<BabyListData> list_itemArrayList = new ArrayList<BabyListData>();
        fileM.getChildList(list_itemArrayList);

        List<BabyListCard> items= DevelInputParent.getBabyListCard(id, myMonthQuestion, false, MainActivity.USERTYPE , true);
        recyclerView.setAdapter(new RecyclerAdapterTeacher(getApplicationContext(),items,R.layout.devel_input_form));
    }


}


