package com.kaist.supersong.bebecode;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import DataStructure.ChatAdapter;
import mymanager.MySocketManager;

/**
 * Created by SuperSong on 2017-07-24.
 */

public class DialogForAgree extends Dialog {

    private int position;
    Activity p_act;
    private TimerTask mTask;
    private Timer mTimer;
    private boolean learning;
    private boolean running_one_task;
    private int recent_chat_count;
    String print_date = "initial";
    int date_count = 0;


    public DialogForAgree(Context context , int _position) {
        super(context);
        p_act = (Activity)context;
        position = _position;
        learning = true;
    }

    @Override
    protected void onStop(){
        super.onStop();
        dismiss();
        learning = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ListView m_ListView;
        setContentView(R.layout.dialog_comment);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ViewGroup.LayoutParams params = getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        final TextView dialog_title;
        final EditText input;
        Button send_btn, kakao_btn;
        final ProgressBar loading;
        final ChatAdapter m_Adapter;
        final ScrollView srcollViewB,srcollViewA;
        final ProgressBar progress1 , progress2;
        TextView m_answer, f_answer;
        Button image_upload;
        ImageView imageView;
        VideoView videoView;

        //android:id="@+id/dialog_comment_progress2"

        m_answer = (TextView) findViewById(R.id.m_answer);
        f_answer = (TextView) findViewById(R.id.f_answer);
        progress1 = (ProgressBar) findViewById(R.id.dialog_comment_progress1);
        dialog_title = (TextView) findViewById(R.id.dialog_comment_title);
        input = (EditText) findViewById(R.id.dialog_comment_input);
        send_btn = (Button) findViewById(R.id.dialog_comment_send);
        imageView = (ImageView) findViewById(R.id.imageView);
        image_upload = (Button) findViewById(R.id.img_upload_btn);
        videoView = (VideoView) findViewById(R.id.videoView);
        kakao_btn = (Button)findViewById(R.id.img_message_btn);

        progress1.setVisibility(ProgressBar.VISIBLE);
        dialog_title.setText(RecyclerAdapterHorizontal.items.get(position).getQuestion_number() +" : "+ RecyclerAdapterHorizontal.items.get(position).getQuestion());

        if(MainActivity.USERTYPE.contains("FATHER")){
            switch(RecyclerAdapterHorizontal.items.get(position).getClicked_radio()){
                case 0: f_answer.setText("전혀 못함"); break;
                case 1: f_answer.setText("못하는 편"); break;
                case 2: f_answer.setText("할 수 있는 편"); break;
                case 3: f_answer.setText("잘하는 편"); break;
                default : f_answer.setText("응답안함"); break;
            }
            switch(RecyclerAdapterHorizontal.items.get(position).getSpouse_cliccked()){
                case 0: m_answer.setText("전혀 못함"); break;
                case 1: m_answer.setText("못하는 편"); break;
                case 2: m_answer.setText("할 수 있는 편"); break;
                case 3: m_answer.setText("잘하는 편"); break;
                default : m_answer.setText("응답안함"); break;
            }
        }else if(MainActivity.USERTYPE.contains("MOTHER")){
            switch(RecyclerAdapterHorizontal.items.get(position).getClicked_radio()){
                case 0: m_answer.setText("전혀 못함"); break;
                case 1: m_answer.setText("못하는 편"); break;
                case 2: m_answer.setText("할 수 있는 편"); break;
                case 3: m_answer.setText("잘하는 편"); break;
                default : m_answer.setText("응답안함"); break;
            }
            switch(RecyclerAdapterHorizontal.items.get(position).getSpouse_cliccked()){
                case 0: f_answer.setText("전혀 못함"); break;
                case 1: f_answer.setText("못하는 편"); break;
                case 2: f_answer.setText("할 수 있는 편"); break;
                case 3: f_answer.setText("잘하는 편"); break;
                default : f_answer.setText("응답안함"); break;
            }
        }

        //srcollViewB = (ScrollView) findViewById(R.id.scrollB);

        /*
        srcollViewB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    srcollViewA.requestDisallowInterceptTouchEvent(false);
                else srcollViewA.requestDisallowInterceptTouchEvent(true);
                return false;
            }}
            );
            */

        kakao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog kakao_dialog;
                final EditText copyText;
                Button btn;

                btn = new Button(getContext());
                kakao_dialog = new Dialog(getContext());
                kakao_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                kakao_dialog.setContentView(R.layout.send_kakaotalk);
                copyText = (EditText) kakao_dialog.findViewById(R.id.copyText);
                btn = (Button) kakao_dialog.findViewById(R.id.img_message_btn);

                copyText.setText("우리 아이 발달검사 문항을 풀어보고 있습니다. 어떻게 생각하시나요?' "+RecyclerAdapterHorizontal.items.get(position).getQuestion()+"' (1-못하는 편, 2-할 수 있는편 )" );

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.setPackage("com.kakao.talk");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "[아기발달검사]");
                        intent.putExtra(Intent.EXTRA_TEXT, copyText.getText().toString());
                        try {
                            getContext().startActivity(intent);
                            MySocketManager socc = new MySocketManager(MainActivity.USERTYPE);
                            socc.setLog(MainActivity.USERTYPE ,RecyclerAdapterHorizontal.items.get(position).getChildID(),"KAKAO ASK");
                        } catch (Exception e) {
                            Toast.makeText(p_act, "카카오톡 어플을 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                kakao_dialog.show();
            }
        });


        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.upload_image_video);
                final ProgressBar _pp = (ProgressBar) dialog.findViewById(R.id.upload_image_video_progressbar);
                final ImageView img = (ImageView) dialog.findViewById(R.id.imageView);
                final VideoView video = (VideoView) dialog.findViewById(R.id.videoView);
                final FrameLayout videoWrapper = (FrameLayout) dialog.findViewById(R.id.videoViewWrapper);

                // Get Screen Size
                /*
                DisplayMetrics metrics = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                windowManager.getDefaultDisplay().getMetrics(metrics);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
                params.width = metrics.widthPixels;
                params.height = metrics.heightPixels / 2;
                img.setLayoutParams(params);
                */
                Button btn = (Button) dialog.findViewById(R.id.upload_image_video_btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "업로드 할 자료를 선택해 주세요.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/* video/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        for(int i=0 ; i< RecyclerAdapterHorizontal.items_all.size() ; i++){
                            if(RecyclerAdapterHorizontal.items.get(position).getQuestion_id() == RecyclerAdapterHorizontal.items_all.get(i).getQuestion_id()){
                                MainActivity.intent_childid = RecyclerAdapterHorizontal.items_all.get(i).getChildID();
                                MainActivity.intent_position = i;
                                MainActivity.relative_position = position;
                                break;
                            }
                        }
                        p_act.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                new TaskImageLoading(position, img  , video, _pp , videoWrapper , getContext() ).execute();
            }
        });


        m_ListView = (ListView) findViewById(R.id.listView1);
        m_Adapter = new ChatAdapter();
        m_ListView.setAdapter(m_Adapter);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = input.getText().toString();
                if (msg.length() > 0 ) {

                    String new_dialog = null;
                    Calendar time = Calendar.getInstance();
                    new_dialog = MainActivity.USERTYPE+" ("+(time.get(Calendar.YEAR))+ "/" +(time.get(Calendar.MONTH)+1) +"/" + time.get(Calendar.DAY_OF_MONTH) +" " +time.get(Calendar.HOUR_OF_DAY) +":"+ time.get(Calendar.MINUTE)+") " + msg;
                    m_Adapter.add(input.getText().toString(),1);
                    MySocketManager socc = new MySocketManager(MainActivity.USERTYPE);
                    socc.setComment(RecyclerAdapterHorizontal.items.get(position).getChildID(), Integer.toString(position), RecyclerAdapterHorizontal.items.get(position).getQuestion_number().split(" ")[1] , input.getText().toString());
                    m_Adapter.notifyDataSetChanged();
                    m_ListView.setSelection( m_Adapter.getCount() - 1);
                    input.setText("");
                    recent_chat_count++;
                }
            }
        });

        mTask = new TimerTask() {
            @Override
            public void run() {
                if(MainActivity.mCurrentPosition == 0 && learning == true && !running_one_task) {
                    new TaskCommentLoading(RecyclerAdapterHorizontal.items.get(position).getChildID(), RecyclerAdapterHorizontal.items.get(position).getQuestion_number() , m_Adapter , progress1, m_ListView).execute();
                }
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, 1 , 4000);
        //mTimer.schedule(mTask, 1);  // one_time execute
    }

    class TaskCommentLoading extends AsyncTask<String, Integer, String> {

        String childid;
        String question;
        ChatAdapter dialog_comment;
        ProgressBar pp;
        String result_comment;
        ListView listv;

        public TaskCommentLoading (String _childid, String _question_number, ChatAdapter _dialog_comment, ProgressBar _pp, ListView _listv){
            childid = _childid;
            question = _question_number.split(" ")[1];
            dialog_comment = _dialog_comment;
            pp = _pp;
            listv = _listv;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            running_one_task = true;
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
                //Log.e("zz111",Integer.toString(i)+" "+Integer.toString(recent_chat_count) + " " +result_comment.split("@@").length);
                if(i==1){
                    if(result_comment.split("@@")[i].contains("NO FILE :"))
                        break;
                }

                if(i >  recent_chat_count){
                    //Log.e("zz222",Integer.toString(i)+" "+Integer.toString(recent_chat_count) + " " +result_comment.split("@@").length);

                    Log.e("zz_result",result_comment);

                    if( result_comment.split("@@")[i].split(" ")[1].substring(1).compareTo(print_date) == 0){ // the same date nothing
                    }
                    else{
                        print_date = result_comment.split("@@")[i].split(" ")[1].substring(1);  // print date
                        dialog_comment.add( result_comment.split("@@")[i].split(" ")[1].substring(1) , 2);
                        date_count++;
                    }

                    if(result_comment.split("@@")[i].split(" ")[0].contains(MainActivity.USERTYPE)){
                        //Log.e("zz333",Integer.toString(i)+" "+Integer.toString(recent_chat_count) + " " +result_comment.split("@@").length);

                        if(i > recent_chat_count) {
                            Log.e("zz22", result_comment.split("@@")[i].split(" : ")[1]);
                            dialog_comment.add(result_comment.split("@@")[i].split(" : ")[1], 1);
                            //Log.e("zz444",Integer.toString(i)+" "+Integer.toString(recent_chat_count) + " " +result_comment.split("@@").length+" "+result_comment.split("@@")[i].split(" : ")[1]);
                        }
                    }
                    else {
                        Log.e("zz33", result_comment.split("@@")[i].split(" : ")[1]);
                        dialog_comment.add(result_comment.split("@@")[i].split(" : ")[1], 0);
                        Log.e("zz44", result_comment.split("@@")[i].split(" : ")[1]);
                        //Log.e("zz555",Integer.toString(i)+" "+Integer.toString(recent_chat_count) + " " +result_comment.split("@@").length +" "+result_comment.split("@@")[i].split(" : ")[1]);
                    }
                }
            }

            //Log.e("zz666",Integer.toString(recent_chat_count) + " " +result_comment.split("@@").length + " " +Integer.toString(dialog_comment.getCount() - date_count));

            if(dialog_comment.getCount() < 1){
                dialog_comment.add("대화창을 이용해서 의견을 나누어보세요",2);
            }

            if ( recent_chat_count < dialog_comment.getCount() - date_count){
                dialog_comment.notifyDataSetChanged();
                listv.setSelection( dialog_comment.getCount() );
                recent_chat_count = dialog_comment.getCount() - date_count;
                //Log.e("zz777",Integer.toString(recent_chat_count) + " " +result_comment.split("@@").length + " " +Integer.toString(dialog_comment.getCount()));
            }

            running_one_task = false;
            pp.setVisibility(ProgressBar.GONE);
        }
    }

    class TaskImageLoading extends AsyncTask<String, Integer, String> {

        ImageView img;
        VideoView vv;
        ProgressBar pro;
        int position;
        Bitmap bitmap;
        Boolean isImage, noData;
        Context context;
        FrameLayout fl;

        public TaskImageLoading(int _p, ImageView _img, VideoView _vv , ProgressBar _pp ,  FrameLayout _fl, Context _c){
            position = _p;
            img = _img;
            pro = _pp;
            vv = _vv;
            context = _c;
            isImage = true;
            noData = false;
            fl = _fl;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            if(RecyclerAdapterHorizontal.items.get(position).getPicture_source()>=0 && RecyclerAdapterHorizontal.items.get(position).getPicture_source() <=2) {  // image
                isImage = true;
                final String baseShoppingURL =  RecyclerAdapterHorizontal.uploadURL + RecyclerAdapterHorizontal.items.get(position).getSourceName();
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
            }
            else if(RecyclerAdapterHorizontal.items.get(position).getPicture_source()>=3 && RecyclerAdapterHorizontal.items.get(position).getPicture_source() <=5){  // video
                isImage = false;
            } else{
                noData = true;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(noData){
                img.setVisibility(ImageView.VISIBLE);
                vv.setVisibility(VideoView.GONE);
            }
            else if(isImage) {
                img.setVisibility(ImageView.VISIBLE);
                img.setImageBitmap(bitmap);

                vv.setVisibility(VideoView.GONE);
            }
            else{
                String videourl = RecyclerAdapterHorizontal.uploadURL + RecyclerAdapterHorizontal.items.get(position).getSourceName();
                Uri urr = Uri.parse(videourl);
                vv.setVisibility(VideoView.VISIBLE);
                img.setVisibility(ImageView.GONE);

                final MediaController controller = new MediaController(context);
                vv.setVideoURI(urr);

                vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                vv.setMediaController(controller);
                                controller.setAnchorView(vv);

                                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                lp.gravity = Gravity.BOTTOM;
                                controller.setLayoutParams(lp);
                                ((ViewGroup) controller.getParent()).removeView(controller);
                                fl.addView(controller);

                            }
                        });
                    }
                });
                vv.start();
            }
            pro.setVisibility(ProgressBar.GONE);
        }

    }


}
