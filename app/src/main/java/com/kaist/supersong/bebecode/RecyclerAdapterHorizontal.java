package com.kaist.supersong.bebecode;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import DataStructure.BabyListCard;
import mymanager.MySocketManager;

/**
 * Created by SuperSong on 2017-03-27.
 */

public class RecyclerAdapterHorizontal extends RecyclerView.Adapter<RecyclerAdapterHorizontal.ViewHolder> {

    Context context;
    static List<BabyListCard> items;
    static List<BabyListCard> items_all;
    int item_layout;
    final static int numOfResultRows = 6;
    public final static String uploadURL = "http://143.248.134.177/uploads/";
    final static int normal =0;
    final static int additional =1;
    final static int do_hide  =2;
    MySocketManager socketM;
    CheckFragment.DateHandler msgHandler;

    public RecyclerAdapterHorizontal(Context context, Object items11, Object items22, int item_layout , CheckFragment.DateHandler Handler) {
        this.context=context;
        this.item_layout=item_layout;
        this.items=  (List<BabyListCard>) items22;
        this.items_all=  (List<BabyListCard>) items11;
        socketM = new MySocketManager(MainActivity.USERTYPE);
        msgHandler = Handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int _viewType) {
        View v=null;

        switch(_viewType) {
            case normal:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.devel_input_parent_cardview_hori, null, false);
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

    public void updateCardview(ViewHolder holder, final int position){

        if(items.get(position).getClicked_radio() <= 1 &&  ( items.get(position).getSpouse_cliccked() == 2 || items.get(position).getSpouse_cliccked() == 3 )){
            items.get(position).setConflict(true);
        } else if (items.get(position).getSpouse_cliccked() <= 1 &&  ( items.get(position).getClicked_radio() == 2 || items.get(position).getClicked_radio() == 3 ) ){
            items.get(position).setConflict(true);
        }else items.get(position).setConflict(false);

        // check other's result can be seen
        boolean canbe_seen = true;
        for(int i=0 ; i<items_all.size() ; i++){
            if( items_all.get(i).getQuestion_type() == items.get(position).getQuestion_type()){
                String question_type=" ";
                switch(items_all.get(i).getQuestion_type()){
                    case 0:question_type = "대근육운동"; break;
                    case 1:question_type = "소근육운동"; break;
                    case 2:question_type = "인지"; break;
                    case 3:question_type = "언어"; break;
                    case 4:question_type = "사회성"; break;
                    case 5:question_type = "자조";break;
                }

                if (items_all.get(i).getClicked_radio() >= 0 && items_all.get(i).getClicked_radio() < 4  && items_all.get(i).getSpouse_cliccked() >=0 && items_all.get(i).getSpouse_cliccked() < 4){
                    // can_be_seen
                }
                // I'm not solved all
                else if(!(items_all.get(i).getClicked_radio() >= 0 && items_all.get(i).getClicked_radio() < 4) ){
                    holder.hidden_message.setText(question_type + " 문제를 다 풀어야 다른사람 의견을 볼 수 있습니다.");
                    canbe_seen = false;
                    break;
                }
                // I solved all, but my partnert didn't
                else if(items_all.get(i).getClicked_radio() >= 0 && items_all.get(i).getClicked_radio() < 4 && !(items_all.get(i).getSpouse_cliccked() >=0 && items_all.get(i).getSpouse_cliccked() < 4)){
                    holder.hidden_message.setText("배우자가 " +question_type + "문제를 다 풀지 않았습니다.");
                    canbe_seen = false;
                }
            }
        }

        holder.myanswer_tablerow.setBackgroundColor(Color.TRANSPARENT);
        if( (items.get(position).getClicked_radio() >= 0 && items.get(position).getClicked_radio() < 4)){
            if ( !items.get(position).isConflict())
                holder.myanswer_tablerow.setBackgroundColor(Color.parseColor("#AEAAA9"));
            else
                holder.myanswer_tablerow.setBackgroundColor(Color.parseColor("#ffaa95"));
        }

        if(canbe_seen) {
            //holder.spouse_tablerow.setForeground(null);
            //holder.teacher_tablerow.setForeground(null);
            holder.spouse_tablerow.setVisibility(TableRow.VISIBLE);
            holder.teacher_tablerow.setVisibility(TableRow.VISIBLE);
            holder.card_hide_text.setVisibility(LinearLayout.GONE);
            holder.conflictImage.setVisibility(ImageView.VISIBLE);
            holder.conflictImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog;
                    dialog = new DialogForAgree(context,position);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                    /*
                    final Dialog dialog;
                    final TextView dialog_title, dialog_context;
                    final EditText input;
                    Button send_btn;
                    final ProgressBar loading;

                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_comment);

                    dialog_title = (TextView) dialog.findViewById(R.id.dialog_comment_title);
                    dialog_context = (TextView) dialog.findViewById(R.id.dialog_comment_context);
                    input = (EditText) dialog.findViewById(R.id.dialog_comment_input);
                    send_btn = (Button) dialog.findViewById(R.id.dialog_comment_send);
                    loading = (ProgressBar) dialog.findViewById(R.id.dialog_comment_progress);
                    dialog_title.setText(items.get(position).getQuestion_number() +" : "+ items.get(position).getQuestion());

                    send_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String msg = input.getText().toString();
                            if (msg.length() > 0 ) {
                                String new_dialog = null;

                                Calendar time = Calendar.getInstance();
                                new_dialog = MainActivity.USERTYPE+" ["+(time.get(Calendar.MONTH)+1) +"/" + time.get(Calendar.DAY_OF_MONTH) +" " +time.get(Calendar.HOUR_OF_DAY) +":"+ time.get(Calendar.MINUTE)+"] " + msg;
                                dialog_context.setText(dialog_context.getText()+new_dialog+"\n");
                                MySocketManager socc = new MySocketManager(MainActivity.USERTYPE);
                                socc.setComment(items.get(position).getChildID(), Integer.toString(position), items.get(position).getQuestion_number().split(" ")[1] , input.getText().toString());
                                input.setText("");
                            }
                        }
                    });

                    ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                    new TaskCommentLoading(items.get(position).getChildID(), items.get(position).getQuestion_number() , dialog_context , loading).execute();

                    dialog.show();
                    */
                }
            });

            if(items.get(position).isConflict()) {
                holder.q_question.setTextColor(Color.parseColor("#FFFF0000"));
                Animation mAnimation = new AlphaAnimation(1, 0);

                mAnimation.setDuration(500);
                mAnimation.setInterpolator(new LinearInterpolator());
                mAnimation.setRepeatCount(Animation.INFINITE);
                mAnimation.setRepeatMode(Animation.REVERSE);

                //애니메이션 시작
                holder.conflictImage.startAnimation(mAnimation);
            }
            else {
                holder.q_question.setTextColor(Color.parseColor("#000000"));
            }

            // if camera_image on
            if( items.get(position).getPicture_source() > 5)
                holder.camera_on.setVisibility(ImageView.INVISIBLE);
            else
                holder.camera_on.setVisibility(ImageView.VISIBLE);

            holder.camera_upload.setVisibility(ImageView.VISIBLE);
        }
        else{
            //holder.spouse_tablerow.setForeground(context.getDrawable(R.drawable.hide2));
            //holder.teacher_tablerow.setForeground(context.getDrawable(R.drawable.hide1));
            holder.spouse_tablerow.setVisibility(TableRow.GONE);
            holder.teacher_tablerow.setVisibility(TableRow.GONE);
            holder.card_hide_text.setVisibility(LinearLayout.VISIBLE);

            holder.camera_on.setVisibility(ImageView.INVISIBLE);
            holder.camera_upload.setVisibility(ImageView.INVISIBLE);
            holder.conflictImage.setVisibility(ImageView.INVISIBLE);
            holder.q_question.setTextColor(Color.parseColor("#000000"));
            holder.myanswer_tablerow.setBackgroundColor(Color.TRANSPARENT);
        }

        // always invisible
        holder.camera_on.setVisibility(ImageView.INVISIBLE);
        holder.camera_upload.setVisibility(ImageView.INVISIBLE);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //final String item=items.get(position);
        //Drawable drawable=context.getResources().getDrawable(item.getImage());
        //holder.image.setBackground(drawable);
        final ViewHolder viewholderfinal= holder;

        if(items.get(position).isHide()) {
            holder.cardview.setVisibility(CardView.GONE);
            //holder.linearLayout.setVisibility(LinearLayout.GONE);
        }
        else {
            holder.cardview.setVisibility(CardView.VISIBLE);
            //holder.linearLayout.setVisibility(LinearLayout.VISIBLE);
        }

        holder.q_number.setText(items.get(position).getQuestion_number());
        holder.q_question.setText(items.get(position).getQuestion());
        holder.q_ask_teacher.setChecked(items.get(position).getTeacher_request()==1);

        updateCardview(holder,position);

        /*
        if(items.get(position).getQuestion_number().equals(" ")){ // there is no remain problem
            holder.myRadioGroup.setVisibility(RadioGroup.INVISIBLE);
            holder.spouse_tablerow.setVisibility(TableRow.GONE);
            holder.teacher_tablerow.setVisibility(TableRow.GONE);
            holder.q_ask_teacher.setVisibility(CheckBox.INVISIBLE);
            holder.q_section.setVisibility(ImageView.INVISIBLE);
            return ;
        }
        */

        holder.camera_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "그림파일 upload", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/* video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                for(int i=0 ; i< items_all.size() ; i++){
                    if(items.get(position).getQuestion_id() == items_all.get(i).getQuestion_id()){
                        MainActivity.intent_childid = items_all.get(i).getChildID();
                        MainActivity.intent_position = i;
                        MainActivity.relative_position = position;
                        break;
                    }
                }

                ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                //Log.e("zz",intent.getData().getPath());
            }
        });


        holder.camera_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "그림파일이 존재합니다.", Toast.LENGTH_SHORT).show();
                // 0 : father - image
                // 1 : mother - image
                // 2 : teacher - image
                if(items.get(position).getPicture_source()>=0 && items.get(position).getPicture_source() <=2) {  // image
                    final Dialog dialog;
                    TextView tv;
                    ImageView iv;

                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_image);

                    dialog.setTitle("Custom Dialog");

                    tv = (TextView) dialog.findViewById(R.id.dialog_image_text);
                    //tv.setText("위 사진을 업로드 하시겠습니까?");
                    tv.setVisibility(TextView.INVISIBLE);

                    ProgressBar image_loading_progress_bar = (ProgressBar) dialog.findViewById(R.id.image_loading_progress_bar);
                    iv = (ImageView) dialog.findViewById(R.id.image);

                    image_loading_progress_bar.setVisibility(ProgressBar.VISIBLE);
                    dialog.show();

                    Button btn_yes = (Button) dialog.findViewById(R.id.dialog_image_yes);
                    Button btn_no = (Button) dialog.findViewById(R.id.dialog_image_no);

                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            //CheckYes();
                        }
                    });

                    btn_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    btn_yes.setText("확인");
                    btn_no.setVisibility(Button.GONE);

                    new ProgressTask(position,iv,image_loading_progress_bar).execute();
                }
                else if(items.get(position).getPicture_source()>=3 && items.get(position).getPicture_source() <=5){  // video

                    final Dialog dialog;
                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_video);

                    VideoView vv = (VideoView) dialog.findViewById(R.id.videoView);
                    String videourl = uploadURL + items.get(position).getSourceName();
                    Uri urr = Uri.parse(videourl);
                    vv.setVideoURI(urr);
                    vv.start();

                    MediaController controller = new MediaController(context);
                    vv.setMediaController(controller);
                    dialog.show();
                }

            }
        });


        if(items.get(position).isSpouse_request()){
            //holder.requestText.setVisibility(TextView.VISIBLE);
            //holder.requestText.setText(MainActivity.SPOUSEKOREAN+"의 요청");
            //holder.requestText.setVisibility(TextView.VISIBLE);
            //holder.q_ask_spouse.setTextColor(Color.BLUE);
        }  else {
            //holder.requestText.setVisibility(TextView.INVISIBLE);
            //holder.q_ask_spouse.setText(MainActivity.SPOUSEKOREAN+"에게 묻기");
            //holder.q_ask_spouse.setTextColor(Color.BLACK);
            //holder.q_ask_spouse.setEnabled(true);
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

        switch(getItemViewType(position)){
            case normal:
                //holder.normalButtons[0].setButtonDrawable(R.drawable.radio3);
                View.OnClickListener first_radio_listener = new View.OnClickListener(){
                    public void onClick(View v) {
                        //viewholderfinal.teacher_tablerow.setVisibility(TableRow.VISIBLE);
                        //viewholderfinal.spouse_tablerow.setVisibility(TableRow.VISIBLE);

                        if(viewholderfinal.myRadioGroup.getCheckedRadioButtonId() <0 ) { // if unchecked , value is 9
                            items.get(position).setClicked_radio(9);
                        }
                        else{
                            for(int i=0 ; i< 4 ; i++){
                                if(viewholderfinal.normalButtons[i].isChecked()){
                                    items.get(position).setClicked_radio(i);
                                    //socketM.setTimer(position, items_all.get(position).getChildID() , i , MainActivity.USERTYPE);
                                }
                            }
                        }

                        updateCardview(viewholderfinal,position);
                        notifyDataSetChanged();

                        Message msg = msgHandler.obtainMessage(CheckFragment.QUESTION_ANSWER_CHECKED);
                        msgHandler.handleMessage(msg); // change progressbar
                        socketM.setTcpIpResult(MainActivity.USERTYPE, MySocketManager.SET_DATA, items_all , items, MySocketManager.SET_DATA_MY_RESULT);
                    }
                };

                /*
                View.OnClickListener checked_listener_asking_spouse = new View.OnClickListener(){
                    public void onClick(View v) {
                        if(viewholderfinal.q_ask_spouse.isChecked()){
                            items.get(position).setS_request(1);
                        }
                        else items.get(position).setS_request(0);
                        socketM.setTcpIpResult(MainActivity.USERTYPE, MySocketManager.SET_REQUEST, items_all , MySocketManager.SET_REQEUST_MY_ASKING);
                    }
                };
                */

                holder.normalButtons[0].setOnClickListener(first_radio_listener);
                holder.normalButtons[1].setOnClickListener(first_radio_listener);
                holder.normalButtons[2].setOnClickListener(first_radio_listener);
                holder.normalButtons[3].setOnClickListener(first_radio_listener);

                View.OnClickListener checked_listener_asking_teacher = new View.OnClickListener(){
                    public void onClick(View v) {
                        if(viewholderfinal.q_ask_teacher.isChecked()){
                            items.get(position).setTeacher_request(1);
                        }
                        else items.get(position).setTeacher_request(0);
                        socketM.setTcpIpResult(MainActivity.USERTYPE, MySocketManager.SET_REQUEST, items_all , items, MySocketManager.SET_REQEUST_ASKING_TEACHER);
                    }
                };
                holder.q_ask_teacher.setOnClickListener(checked_listener_asking_teacher);

                // set radio button based on Clicked value
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

        LinearLayout linearLayout;
        CardView cardview;
        CheckBox q_ask_teacher; CheckBox q_ask_spouse;
        RadioButton[] normalButtons = new RadioButton[4]; RadioButton[] additionButtons = new RadioButton[2];
        RadioButton[] spouseButtons = new RadioButton[4]; RadioButton[] spouseAdditionButtons = new RadioButton[2];
        RadioButton[] teacherButtons = new RadioButton[2]; RadioButton[] teacherAdditionButtons = new RadioButton[2];
        TableRow myanswer_tablerow;
        RadioGroup myRadioGroup; RadioGroup teacherRadioGroup; RadioGroup spouseRadioGroup;

        TextView hidden_message;
        LinearLayout card_hide_text;
        TableRow[] resultRows = new TableRow[numOfResultRows];
        TableLayout score_table;

        TableRow spouse_tablerow; TableRow teacher_tablerow;
        Bitmap bitmap;

        public ViewHolder(View itemView) {
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.cardview_layout);

            q_number = (TextView) itemView.findViewById(R.id.text_number);
            q_question = (TextView) itemView.findViewById(R.id.text_question);
            q_section = (ImageView) itemView.findViewById(R.id.img_question_section);
            hidden_message =(TextView) itemView.findViewById(R.id.hidden_message);

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
            myanswer_tablerow = (TableRow) itemView.findViewById(R.id.myanswer_radio);

            card_hide_text = (LinearLayout) itemView.findViewById(R.id.card_hide_text);
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
        //if(items.get(position).isHide()){
        //    return do_hide;
        //}
        if(position >= numOfResultRows*8) return additional;
        else return normal;
    }


    class ProgressTask extends AsyncTask<String, Integer, String> {

        ImageView img;
        ProgressBar pro;
        int position;

        Bitmap bitmap;

        public ProgressTask(int _p, ImageView _img, ProgressBar _pp){
            position = _p;
            img = _img;
            pro = _pp;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

                final String baseShoppingURL =  uploadURL + items.get(position).getSourceName();
                Thread mThread = new Thread(){
                    @Override
                    public void run(){
                        try{

                            URL url = new URL(baseShoppingURL);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true);
                            conn.connect();
                            InputStream is = conn.getInputStream();
                             bitmap = BitmapFactory.decodeStream(is);
                            is.close();

                        }catch(IOException ex){

                        }
                    }
                };

                mThread.start();
                try{
                    mThread.join();
                }
                catch (InterruptedException e){

                }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            img.setImageBitmap(bitmap);
            pro.setVisibility(ProgressBar.GONE);
        }

    }


    class TaskCommentLoading extends AsyncTask<String, Integer, String> {

        String childid;
        String question;
        TextView dialog_comment;
        ProgressBar pp;
        String result_comment;

        public TaskCommentLoading (String _childid, String _question_number, TextView _dialog_comment, ProgressBar _pp){
            childid = _childid;
            question = _question_number.split(" ")[1];
            dialog_comment = _dialog_comment;
            pp = _pp;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            Thread mThread = new Thread(){
                @Override
                public void run() {
                    MySocketManager socketM = new MySocketManager(MainActivity.USERTYPE);
                    result_comment = socketM.getTcpIpComment(childid, question);
                }
            };
            mThread.start();
            try {
                mThread.join();
            }catch (Exception e){
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String result_c = null;

            for(int i=1 ; i < result_comment.split("@@").length ; i++){
                if(i==1){
                    if(!result_comment.split("@@")[i].contains("NO FILE :"))
                        result_c = result_comment.split("@@")[i]+"\n";
                }
                else{
                    result_c = result_c + result_comment.split("@@")[i] + "\n";
                }
            }

            dialog_comment.setText(result_c);
            pp.setVisibility(ProgressBar.GONE);
        }

    }
}