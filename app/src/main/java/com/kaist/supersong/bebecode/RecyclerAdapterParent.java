package com.kaist.supersong.bebecode;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import DataStructure.BabyListCard;
import mymanager.MySocketManager;

/**
 * Created by SuperSong on 2017-03-27.
 */

public class RecyclerAdapterParent extends RecyclerView.Adapter<RecyclerAdapterParent.ViewHolder> {

    Context context;
    List<BabyListCard> items;
    int item_layout;
    final static int numOfResultRows = 6;

    final static int normal =0;
    final static int additional =1;
    final static int do_hide  =2;
    MySocketManager socketM;

    public RecyclerAdapterParent(Context context, Object items, int item_layout) {
        this.context=context;
        this.item_layout=item_layout;
        this.items=  (List<BabyListCard>) items;
        socketM = new MySocketManager(MainActivity.USERTYPE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int _viewType) {
        View v=null;

        switch(_viewType) {
            case normal:
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.devel_input_parent_cardview, null, false);
                break;
            case additional:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.devel_input_parent_cardview_addition, null, false);
                break;
            case do_hide:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.emptylayout, null, false);
                break;
        }
        v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(v);
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //final String item=items.get(position);
        //Drawable drawable=context.getResources().getDrawable(item.getImage());
        //holder.image.setBackground(drawable);

        if(items.get(position).isHide())
            return;


        holder.q_number.setText(items.get(position).getQuestion_number());
        holder.q_question.setText(items.get(position).getQuestion());

        holder.q_ask_spouse.setChecked(items.get(position).getS_request()==1);
        holder.q_ask_teacher.setChecked(items.get(position).getTeacher_request()==1);

        if(items.get(position).getQuestion_number().equals(" ")){ // there is no remain problem
            holder.myRadioGroup.setVisibility(RadioGroup.INVISIBLE);
            holder.spouse_tablerow.setVisibility(TableRow.GONE);
            holder.teacher_tablerow.setVisibility(TableRow.GONE);
            holder.q_ask_spouse.setVisibility(CheckBox.INVISIBLE);
            holder.q_ask_teacher.setVisibility(CheckBox.INVISIBLE);
            holder.q_section.setVisibility(ImageView.INVISIBLE);
            return ;
        }

        holder.camera_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "그림파일 upload", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        holder.camera_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "그림파일이 존재합니다.", Toast.LENGTH_SHORT).show();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_image);
                dialog.setTitle("Custom Dialog");

                TextView tv = (TextView) dialog.findViewById(R.id.text);
                tv.setText("올린이 : 아빠 (2017/4/12)");

                ImageView iv = (ImageView) dialog.findViewById(R.id.image);
                iv.setImageResource(R.drawable.sample1);

                /*
                VideoView videoView = (VideoView)dialog.findViewById(R.id.videoView);
                videoView.setVideoPath(Environment.getExternalStorageDirectory() + "/BabyDevelopment/video.mp4");
                final MediaController mediaController =  new MediaController(context);
                videoView.setMediaController(mediaController);
                videoView.start();
                */

                dialog.show();

            }
        });


        if(items.get(position).isConflict()){
            holder.conflictImage.setVisibility(ImageView.VISIBLE);

            holder.conflictImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "배우자와 의견차이를 보이고 있는 문제입니다.", Toast.LENGTH_SHORT).show();
                }
            });

        }  else {
            holder.conflictImage.setVisibility(ImageView.INVISIBLE);
        }

        if(items.get(position).isSpouse_request()){
            //holder.requestText.setVisibility(TextView.VISIBLE);
            //holder.requestText.setText(MainActivity.SPOUSEKOREAN+"의 요청");
            //holder.requestText.setVisibility(TextView.VISIBLE);
            if(items.get(position).getS_request()==1)  // if I'm asking too, setEnabled
                holder.q_ask_spouse.setEnabled(true);
            else
                holder.q_ask_spouse.setEnabled(false);
            holder.q_ask_spouse.setText(MainActivity.SPOUSEKOREAN+"의 요청");
            holder.q_ask_spouse.setTextColor(Color.BLUE);
        }  else {
            //holder.requestText.setVisibility(TextView.INVISIBLE);
            holder.q_ask_spouse.setText(MainActivity.SPOUSEKOREAN+"에게 묻기");
            holder.q_ask_spouse.setTextColor(Color.BLACK);
            holder.q_ask_spouse.setEnabled(true);
        }

            holder.myText.setText(MainActivity.USERKOREAN);
            holder.spouseText.setText(MainActivity.SPOUSEKOREAN);

        switch (items.get(position).getQuestion_type()) {
                    case 0: holder.q_section.setImageResource(R.drawable.bigmuscle_icon); break;
                    case 1: holder.q_section.setImageResource(R.drawable.smallmuscle_icon); break;
                    case 2: holder.q_section.setImageResource(R.drawable.recog_icon); break;
                    case 3: holder.q_section.setImageResource(R.drawable.lan_icon); break;
                    case 4: holder.q_section.setImageResource(R.drawable.social_icon); break;
                    case 5: holder.q_section.setImageResource(R.drawable.self_icon); break;
                    case 6: holder.q_section.setImageResource(R.drawable.addq_icon); break;
        }

        final ViewHolder viewholderfinal= holder;

        switch(getItemViewType(position)){
            case normal:
                //holder.normalButtons[0].setButtonDrawable(R.drawable.radio3);

                View.OnClickListener first_radio_listener = new View.OnClickListener(){
                    public void onClick(View v) {
                        viewholderfinal.teacher_tablerow.setVisibility(TableRow.VISIBLE);
                        viewholderfinal.spouse_tablerow.setVisibility(TableRow.VISIBLE);

                        if(viewholderfinal.myRadioGroup.getCheckedRadioButtonId() <0 ) { // if unchecked , value is 9
                            items.get(position).setClicked_radio(9);
                        }
                        else{
                            for(int i=0 ; i< 4 ; i++){
                                if(viewholderfinal.normalButtons[i].isChecked()){
                                    items.get(position).setClicked_radio(i);
                                    //socketM.setTimer(position , items.get(position).getChildID() , i , MainActivity.USERTYPE);
                                }
                            }
                        }
                        //socketM.setTcpIpResult(MainActivity.USERTYPE, MySocketManager.SET_DATA, items , MySocketManager.SET_DATA_MY_RESULT);
                    }
                };

                View.OnClickListener checked_listener_asking_spouse = new View.OnClickListener(){
                    public void onClick(View v) {
                        if(viewholderfinal.q_ask_spouse.isChecked()){
                            items.get(position).setS_request(1);
                        }
                        else items.get(position).setS_request(0);
                        //.setTcpIpResult(MainActivity.USERTYPE, MySocketManager.SET_REQUEST, items , MySocketManager.SET_REQEUST_MY_ASKING);
                    }
                };

                View.OnClickListener checked_listener_asking_teacher = new View.OnClickListener(){
                    public void onClick(View v) {
                        if(viewholderfinal.q_ask_teacher.isChecked()){
                            items.get(position).setTeacher_request(1);
                        }
                        else items.get(position).setTeacher_request(0);
                        //socketM.setTcpIpResult(MainActivity.USERTYPE, MySocketManager.SET_REQUEST, items , MySocketManager.SET_REQEUST_ASKING_TEACHER);
                    }
                };

                holder.normalButtons[0].setOnClickListener(first_radio_listener);
                holder.normalButtons[1].setOnClickListener(first_radio_listener);
                holder.normalButtons[2].setOnClickListener(first_radio_listener);
                holder.normalButtons[3].setOnClickListener(first_radio_listener);

                holder.q_ask_spouse.setOnClickListener(checked_listener_asking_spouse);
                holder.q_ask_teacher.setOnClickListener(checked_listener_asking_teacher);


                if (items.get(position).getClicked_radio() < 4) {
                    holder.normalButtons[items.get(position).getClicked_radio()].setChecked(true);
                } else
                    holder.myRadioGroup.clearCheck();

                if(items.get(position).getTeacher_clicked() <2) {
                    holder.teacherButtons[items.get(position).getTeacher_clicked()].setChecked(true);
                }
                else  holder.teacherRadioGroup.clearCheck();


                if(items.get(position).getSpouse_cliccked() <4) {
                    holder.spouseButtons[items.get(position).getSpouse_cliccked()].setChecked(true);
                }else
                    holder.spouseRadioGroup.clearCheck();


                if(holder.myRadioGroup.getCheckedRadioButtonId() < 0){
                    viewholderfinal.teacher_tablerow.setVisibility(TableRow.GONE);
                    viewholderfinal.spouse_tablerow.setVisibility(TableRow.GONE);
                }
                else {
                    viewholderfinal.teacher_tablerow.setVisibility(TableRow.VISIBLE);
                    viewholderfinal.spouse_tablerow.setVisibility(TableRow.VISIBLE);
                }

                break;

            case additional:

                /*
                View.OnClickListener first_radio_listener2 = new View.OnClickListener(){
                    public void onClick(View v) {
                        viewholderfinal.teacher_tablerow.setVisibility(TableRow.VISIBLE);
                        viewholderfinal.spouse_tablerow.setVisibility(TableRow.VISIBLE);

                        for(int i=0 ; i< 2 ; i++){
                            if(viewholderfinal.additionButtons[i].isChecked()){
                                items.get(position).setClicked_radio(i);
                            }
                        }

                        if(viewholderfinal.q_ask_spouse.isChecked()){
                            items.get(position).setS_request(1);
                        }
                        else
                            items.get(position).setS_request(0);

                        if(viewholderfinal.q_ask_teacher.isChecked()){
                            items.get(position).setTeacher_request(1);
                        }
                        else
                            items.get(position).setTeacher_request(0);
                    }
                };

                holder.additionButtons[0].setOnClickListener(first_radio_listener2);
                holder.additionButtons[1].setOnClickListener(first_radio_listener2);

                if (items.get(position).getClicked_radio() < 2)
                    holder.additionButtons[items.get(position).getClicked_radio()].setChecked(true);
                else
                    holder.myRadioGroup.clearCheck();

                if(items.get(position).getSpouse_cliccked() < 2)
                    holder.spouseAdditionButtons[items.get(position).getSpouse_cliccked()].setChecked(true);
                else
                    holder.spouseRadioGroup.clearCheck();

                if(items.get(position).getTeacher_clicked() < 2)
                    holder.teacherAdditionButtons[items.get(position).getTeacher_clicked()].setChecked(true);
                else
                    holder.teacherRadioGroup.clearCheck();

                if(holder.myRadioGroup.getCheckedRadioButtonId() < 0){
                    viewholderfinal.teacher_tablerow.setVisibility(TableRow.GONE);
                    viewholderfinal.spouse_tablerow.setVisibility(TableRow.GONE);
                }  else {
                    viewholderfinal.teacher_tablerow.setVisibility(TableRow.VISIBLE);
                    viewholderfinal.spouse_tablerow.setVisibility(TableRow.VISIBLE);
                }
                */
                break;
        }

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView q_number; TextView q_question; TextView result_graph_title; TextView myText; TextView spouseText;
        ImageView q_section; ImageView q_mark; ; ImageView conflictImage;   ImageView camera_on; ImageView camera_upload;

        CardView cardview;
        CheckBox q_ask_teacher; CheckBox q_ask_spouse;
        RadioButton[] normalButtons = new RadioButton[4]; RadioButton[] additionButtons = new RadioButton[2];
        RadioButton[] spouseButtons = new RadioButton[4]; RadioButton[] spouseAdditionButtons = new RadioButton[2];
        RadioButton[] teacherButtons = new RadioButton[2]; RadioButton[] teacherAdditionButtons = new RadioButton[2];

        RadioGroup myRadioGroup; RadioGroup teacherRadioGroup; RadioGroup spouseRadioGroup;

        TableRow[] resultRows = new TableRow[numOfResultRows];
        TableLayout score_table;

        TableRow spouse_tablerow; TableRow teacher_tablerow;
        FrameLayout framelayout;

        public ViewHolder(View itemView) {
            super(itemView);

            q_number = (TextView) itemView.findViewById(R.id.text_number);
            q_question = (TextView) itemView.findViewById(R.id.text_question);
            q_section = (ImageView) itemView.findViewById(R.id.img_question_section);

            myText =(TextView) itemView.findViewById(R.id.my_text);
            spouseText=(TextView) itemView.findViewById(R.id.spouse_text);
            //q_mark=(ImageView)itemView.findViewById(R.id.img_question_mark);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
            q_ask_spouse = (CheckBox) itemView.findViewById(R.id.spouse_checkbox);
            q_ask_teacher = (CheckBox) itemView.findViewById(R.id.teacher_checkbox);

            normalButtons[0] = (RadioButton) itemView.findViewById(R.id.my_radio0); normalButtons[1] = (RadioButton) itemView.findViewById(R.id.my_radio1); normalButtons[2] = (RadioButton) itemView.findViewById(R.id.my_radio2);normalButtons[3] = (RadioButton) itemView.findViewById(R.id.my_radio3);
            spouseButtons[0] = (RadioButton) itemView.findViewById(R.id.spouse_radio0); spouseButtons[1] = (RadioButton) itemView.findViewById(R.id.spouse_radio1); spouseButtons[2] = (RadioButton) itemView.findViewById(R.id.spouse_radio2);spouseButtons[3] = (RadioButton) itemView.findViewById(R.id.spouse_radio3);
            teacherButtons[0] = (RadioButton) itemView.findViewById(R.id.teacher_radio1); teacherButtons[1] = (RadioButton) itemView.findViewById(R.id.teacher_radio2);

            additionButtons[0] = (RadioButton) itemView.findViewById(R.id.self_myradio_0); additionButtons[1] = (RadioButton) itemView.findViewById(R.id.self_myradio_1);
            spouseAdditionButtons[0] = (RadioButton) itemView.findViewById(R.id.self_spouse_radio0); spouseAdditionButtons[1] = (RadioButton) itemView.findViewById(R.id.self_spouse_radio1);
            teacherAdditionButtons[0] = (RadioButton) itemView.findViewById(R.id.self_teacher_radio0); teacherAdditionButtons[1] = (RadioButton) itemView.findViewById(R.id.self_teacher_radio1);

            spouse_tablerow = (TableRow)itemView.findViewById(R.id.spouse_tablerow);
            teacher_tablerow = (TableRow)itemView.findViewById(R.id.teacher_tablerow);

            myRadioGroup = (RadioGroup) itemView.findViewById(R.id.myradiogroup);
            teacherRadioGroup = (RadioGroup) itemView.findViewById(R.id.teacher_radiogroup);
            spouseRadioGroup = (RadioGroup) itemView.findViewById(R.id.spouse_radiogroup);
            conflictImage= (ImageView) itemView.findViewById(R.id.conflictImg);
            camera_on = (ImageView)itemView.findViewById(R.id.cameraImg);
            camera_upload = (ImageView) itemView.findViewById(R.id.camera_upload);


        }
    }


    @Override
    public int getItemViewType(int position) {

        if(items.get(position).isHide()){
            return do_hide;
        }

        if(position >= numOfResultRows*8)
            return additional;
        else
            return normal;
    }
}