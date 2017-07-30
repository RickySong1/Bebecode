package com.kaist.supersong.bebecode;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import DataStructure.BabyListCard;
import mymanager.MySocketManager;

import static com.kaist.supersong.bebecode.RecyclerAdapterParent.do_hide;

/**
 * Created by SuperSong on 2017-03-27.
 */

public class RecyclerAdapterTeacher extends RecyclerView.Adapter<RecyclerAdapterTeacher.ViewHolder> {

    Context context;
    List<BabyListCard> items;
    int item_layout;

    final static int numOfResultRows = 6;
    final static int normal =0;
    final static int additional =1;
    MySocketManager socketM;

    public RecyclerAdapterTeacher(Context context, Object items, int item_layout) {
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
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.devel_input_teacher_cardview, null, false);
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


         //holder.q_mark.setVisibility(ImageView.INVISIBLE);

        if (items.get(position).getTeacher_request()>0) {
            holder.q_mark.setVisibility(ImageView.VISIBLE); // parents request
        } else if( teacher_parent_conflict( items.get(position).getTeacher_clicked() , items.get(position).getSpouse_cliccked()) ){  // check parent's conflict
            holder.q_mark.setVisibility(ImageView.VISIBLE);
        }

        holder.camera_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "그림파일 upload", Toast.LENGTH_SHORT).show();
            }
        });

        holder.camera_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "그림파일이 존재합니다.", Toast.LENGTH_SHORT).show();
            }
        });

            holder.q_mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "부모님이 궁금해 하고 있는 문제입니다.", Toast.LENGTH_SHORT).show();
                }
            });

                holder.q_number.setText(items.get(position).getQuestion_number());
                holder.q_question.setText(items.get(position).getQuestion());

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

        View.OnClickListener first_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {

                if(viewholderfinal.myRadioGroup.getCheckedRadioButtonId() <0 ) { // if unchecked , value is 9
                    items.get(position).setClicked_radio(9);
                }
                else{
                    for(int i=0 ; i< 2 ; i++){
                        if(viewholderfinal.normalButtons[i].isChecked()){
                            items.get(position).setClicked_radio(i);
                            //socketM.setTimer(position , items.get(position).getChildID() , i , MainActivity.USERTYPE);
                        }
                    }
                }
                //socketM.setTcpIpResult(MainActivity.USERTYPE, MySocketManager.SET_DATA, items, MySocketManager.SET_DATA_MY_RESULT);
            }
        };

        holder.normalButtons[0].setOnClickListener(first_radio_listener);
        holder.normalButtons[1].setOnClickListener(first_radio_listener);

        if (items.get(position).getClicked_radio() < 4) {
            holder.normalButtons[items.get(position).getClicked_radio()].setChecked(true);
        } else
            holder.myRadioGroup.clearCheck();


    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView q_number;
        TextView q_question;
        ImageView q_section;
        ImageView q_mark;
        ImageView camera_on;
        ImageView camera_upload;
        CardView cardview;
        CheckBox q_ask_teacher;
        CheckBox q_ask_spouse;

        TableRow[] resultRows = new TableRow[numOfResultRows];
        TableRow scoretablerow;
        TextView result_graph_title;

        RadioButton [] normalButtons = new RadioButton[2];
        RadioGroup myRadioGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            q_number=(TextView)itemView.findViewById(R.id.text_number);
            q_question=(TextView)itemView.findViewById(R.id.text_question);
            q_section=(ImageView)itemView.findViewById(R.id.img_question_section);
            q_mark=(ImageView)itemView.findViewById(R.id.img_question_mark);
            cardview=(CardView)itemView.findViewById(R.id.cardview);

            camera_on = (ImageView)itemView.findViewById(R.id.cameraImg);
            camera_upload = (ImageView) itemView.findViewById(R.id.camera_upload);

            normalButtons[0] = (RadioButton)itemView.findViewById(R.id.teacherinput_radio0);
            normalButtons[1] = (RadioButton)itemView.findViewById(R.id.teacher_inputradio1);
            myRadioGroup = (RadioGroup) itemView.findViewById(R.id.teacher_radiogroup);

        }
    }

    public boolean teacher_parent_conflict( int i, int j){

        if( i <= 1 && (j == 2 || j == 3))
            return true;
        if( j <= 1 && (i == 2 || i == 3))
            return true;

        // and then,  if  RESULT is almost delay...??
        return false;
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