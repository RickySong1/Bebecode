package com.kaist.supersong.bebecode;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import DataStructure.BabyListData;
import mymanager.MyFileManager;
import mymanager.MySocketManager;


/**
 * Created by SuperSong on 2017-03-26.
 */

public class BabyDataAdapter extends BaseAdapter {

    ArrayList<BabyListData> items;
    LayoutInflater inflater;
    MyFileManager fileM;

    public BabyDataAdapter(LayoutInflater inflater, ArrayList<BabyListData> datas) {
        fileM = new MyFileManager();
        this.items = datas;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }

    public class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view){

        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.babylist_item, null);
        }
        //de.hdodenhof.circleimageview.CircleImageView
        //de.hdodenhof.circleimageview.CircleImageView pic = (CircleImageView) convertView.findViewById(R.id.profile_image);
        //de.hdodenhof.circleimageview.R.styleable

        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(position < items.size() - 1) {
                    Log.v("listView", Integer.toString(position));
                    Intent intent = null;

                    if ( MainActivity.USERTYPE.contains("TEACHER")){
                        intent = new Intent(view.getContext(), DevelInputTeacher.class);
                    }
                    else if (MainActivity.USERTYPE.contains("FATHER") || MainActivity.USERTYPE.contains("MOTHER")){
                        intent = new Intent(view.getContext(), DevelInputParent.class);
                    }
                    intent.putExtra("NAME", items.get(position).getName());
                    intent.putExtra("STATUS",items.get(position).getStatus());
                    intent.putExtra("ID", items.get(position).getId());
                    intent.putExtra("MONTH", items.get(position).getMonth());
                    view.getContext().startActivity(intent);
                }
            }
        });

        TextView text_name = (TextView) convertView.findViewById(R.id.baby_name);
        TextView text_status = (TextView) convertView.findViewById(R.id.baby_status);
        text_name.setText(items.get(position).getName());
        text_status.setText(items.get(position).getStatus());

        Button dbtn = (Button) convertView.findViewById(R.id.delete_button);
        //dbtn.setId(position);

        if(position < items.size() - 1) {
            dbtn.setText("-");
            dbtn.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            Log.e("btn", Integer.toString(position));

                            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(inflater.getContext());
                            alert_confirm.setMessage(items.get(position).getName() + "를 삭제 하시겠습니까?").setCancelable(false).setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            fileM.deleteChildLIst(items.get(position).getId());
                                            items.remove(position);
                                            notifyDataSetChanged();
                                            return;
                                        }
                                    }).setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 'No'
                                            return;
                                        }
                                    });
                            AlertDialog alert = alert_confirm.create();
                            alert.show();
                        }
                    }
            );
        }
        else{
            dbtn.setText("+");
            dbtn.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {

                            final AlertDialog.Builder alert_confirm = new AlertDialog.Builder(inflater.getContext());
                            alert_confirm.setMessage("추가 할 아동 ID를 입력해주세요.");
                            final EditText et = new EditText(inflater.getContext());
                            alert_confirm.setView(et);

                            alert_confirm.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String value = et.getText().toString();
                                    MySocketManager socketM = new MySocketManager(MainActivity.USERTYPE);
                                    String get_message = socketM.getChildInfo(value);
                                    String _new_info = fileM.addChildList(value.toString() , items, get_message);
                                    // Do something with value!

                                    notifyDataSetChanged();

                                    if(!get_message.contains("SERVER_ERROR")){  // if child is selected well
                                        Intent intent;
                                        intent = new Intent(inflater.getContext(), InitialActivity.class);
                                        inflater.getContext().startActivity(intent);
                                        ((Activity)inflater.getContext()).finish();
                                    }
                                }
                            });

                            alert_confirm.setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                    // Canceled.
                                        }
                                    });

                            AlertDialog alert = alert_confirm.create();
                            alert.show();
                        }
                    }
            );
        }
        //img_flag.setImageResource( datas.get(position).getImgId() );

        return convertView;
    }

}
