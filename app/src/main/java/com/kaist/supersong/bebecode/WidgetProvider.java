package com.kaist.supersong.bebecode;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import DataStructure.BabyListCard;
import DataStructure.BabyListData;
import mymanager.MyFileManager;
import mymanager.MySocketManager;
import questions.MonthQuestions;

/**
 * Created by SuperSong on 2017-04-04.
 */

/*
public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_layout);


        MyFileManager fileM = new MyFileManager();
        ArrayList<BabyListData> childList = new ArrayList<BabyListData>();
        fileM.getChildList(childList);
        List<BabyListCard> items = null;
        String usertype = fileM.getUserType();
        String childid = null;

        if(childList.size() > 0 ) {
            String month = childList.get(0).getMonth();
            MonthQuestions myMonthQuestion = fileM.getMonthQuestions(month);
            childid = childList.get(0).getId();

            items = DevelInputParent.getBabyListCard(childid, myMonthQuestion, true, usertype , false);
        }else{  // no child imformation

        }

        if (intent.getAction().contains("button")){
            String msg = intent.getAction();
            String position = msg.split("##")[1];

            if(Integer.parseInt(position) >= 0) {
                MySocketManager socketM = new MySocketManager(usertype);

                String message = null;
                if (intent.getAction().contains("button0")) {
                    message = checkAnswerState(items.get(Integer.parseInt(position)).getSpouse_cliccked() , 0);
                    items.get(Integer.parseInt(position)).setClicked_radio(3);
                    //socketM.setTcpIpResult(usertype, MySocketManager.SET_DATA, items, MySocketManager.SET_DATA_MY_RESULT);
                    //socketM.setTimer(Integer.parseInt(position), childid , 0 , usertype);
                } else if (intent.getAction().contains("button1")) {
                    message = checkAnswerState(items.get(Integer.parseInt(position)).getSpouse_cliccked() , 1);
                    items.get(Integer.parseInt(position)).setClicked_radio(3);
                    //socketM.setTcpIpResult(usertype, MySocketManager.SET_DATA, items, MySocketManager.SET_DATA_MY_RESULT);
                    //socketM.setTimer(Integer.parseInt(position), childid , 1 , usertype);
                } else if (intent.getAction().contains("button2")) {
                    message = checkAnswerState(items.get(Integer.parseInt(position)).getSpouse_cliccked() , 2);
                    items.get(Integer.parseInt(position)).setClicked_radio(3);
                    //socketM.setTcpIpResult(usertype, MySocketManager.SET_DATA, items, MySocketManager.SET_DATA_MY_RESULT);
                    //socketM.setTimer(Integer.parseInt(position), childid , 2 , usertype);
                } else if (intent.getAction().contains("button3")) {
                    message = checkAnswerState(items.get(Integer.parseInt(position)).getSpouse_cliccked() , 3);
                    items.get(Integer.parseInt(position)).setClicked_radio(3);
                    //socketM.setTcpIpResult(usertype, MySocketManager.SET_DATA, items, MySocketManager.SET_DATA_MY_RESULT);
                    //socketM.setTimer(Integer.parseInt(position), childid , 3 , usertype);
                }

                Toast.makeText(context,message, Toast.LENGTH_LONG).show();
            }
        }

        updateAppWidget(context , appWidgetManager,items);
    }

    public String checkAnswerState(int spouse , int my){

        if(spouse > 3){
            return "아직 배우자가 응답하지 않은 문제입니다.";
        }
        else if(spouse == my){
            return "배우자와 결과가 정확히 일치합니다." + " (자신 : "+Integer.toString(my)+" 배우자 : "+Integer.toString(spouse)+")";
        }
        else if( (spouse==0 || spouse==1 ) &&  (my==0 || my==1)){
            return "배우자와 결과가 비슷합니다." + " (자신 : "+Integer.toString(my)+" 배우자 : "+Integer.toString(spouse)+")";
        }
        else if( (spouse==2 || spouse==3 ) &&  (my==2 || my==3)){
            return "배우자와 결과가 비슷합니다."  + " (자신 : "+Integer.toString(my)+" 배우자 : "+Integer.toString(spouse)+")";
        }
        else
            return "배우자와 의견차이를 보이고 있습니다. 상의해서 답을 수정해주세요." + "자신 : "+Integer.toString(my)+" 배우자 : "+Integer.toString(spouse)+")";
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, InitialActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.widget_linearlayout, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }


    }

    public  void updateAppWidget(Context context, AppWidgetManager appWidgetManager, List<BabyListCard> items ){

        RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.widget_layout);

        ArrayList<Integer> important_item_position = new ArrayList<>();

        int selected_position = -1;

        if(items.size() > 0 ) {
            for(int i=0 ;i<items.size() ; i++){
                if(!items.get(i).isHide()){
                    important_item_position.add(i);
                }
            }
            if(important_item_position.size() >0){

                Random randomint = new Random();
                selected_position = important_item_position.get(randomint.nextInt(important_item_position.size()));

                updateViews.setTextViewText(R.id.widget_questionNumber, items.get(selected_position).getQuestion_number());
                updateViews.setTextViewText(R.id.widget_textQuestion, items.get(selected_position).getQuestion());

                switch (items.get(selected_position).getQuestion_type()) {
                    case 0: updateViews.setImageViewResource(R.id.widget_img_question_section, R.drawable.bigmuscle_icon); break;
                    case 1: updateViews.setImageViewResource(R.id.widget_img_question_section, R.drawable.smallmuscle_icon); break;
                    case 2: updateViews.setImageViewResource(R.id.widget_img_question_section, R.drawable.recog_icon); break;
                    case 3: updateViews.setImageViewResource(R.id.widget_img_question_section, R.drawable.lan_icon); break;
                    case 4: updateViews.setImageViewResource(R.id.widget_img_question_section, R.drawable.social_icon); break;
                    case 5: updateViews.setImageViewResource(R.id.widget_img_question_section, R.drawable.self_icon); break;
                    case 6: updateViews.setImageViewResource(R.id.widget_img_question_section, R.drawable.addq_icon); break;
                }

                if(items.get(selected_position).isConflict())
                    updateViews.setViewVisibility(R.id.widget_conflictText , TextView.GONE);
                else
                    updateViews.setViewVisibility(R.id.widget_conflictText , TextView.GONE);

            }
            else{ // there are no important problems

                updateViews.setTextViewText(R.id.widget_questionNumber, " ");
                updateViews.setTextViewText(R.id.widget_textQuestion, "모든 문제에 기록을 마쳤습니다.");

            }
        }
        else{ // there are no child information

            updateViews.setTextViewText(R.id.widget_questionNumber, " ");
            updateViews.setTextViewText(R.id.widget_textQuestion, "아동이 등록되지 않았습니다.");

        }

        updateViews.setOnClickPendingIntent(R.id.widget_button0,getPendingSelfIntent(context,"button0##"+Integer.toString(selected_position) ));
        updateViews.setOnClickPendingIntent(R.id.widget_button1,getPendingSelfIntent(context,"button1##"+Integer.toString(selected_position) ));
        updateViews.setOnClickPendingIntent(R.id.widget_button2,getPendingSelfIntent(context,"button2##"+Integer.toString(selected_position) ));
        updateViews.setOnClickPendingIntent(R.id.widget_button3,getPendingSelfIntent(context,"button3##"+Integer.toString(selected_position) ));

        updateViews.setViewVisibility(R.id.widget_conflictText, TextView.GONE);
        for(int i=0; i<items.size(); i++){
            if ( items.get(i).isConflict() || items.get(i).isSpouse_request()){
                updateViews.setViewVisibility(R.id.widget_conflictText, TextView.GONE);
                break;
            }
        }

        ComponentName comp = new ComponentName(context.getPackageName(), WidgetProvider.class.getName());
        appWidgetManager.updateAppWidget(comp, updateViews);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {  // deleted by user
        super.onDeleted(context, appWidgetIds);
    }


    protected PendingIntent getPendingSelfIntent(Context context, String action){
        Intent intent = new Intent(context,getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context,0,intent,0);
    }

}
*/
public class WidgetProvider extends AppWidgetProvider {

    MyFileManager fileM;
    RemoteViews views;
    List<BabyListCard> _items;
    AppWidgetManager g_appWidgetManager;
    int widget_id;
    ImageView refresh_img;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        fileM = new MyFileManager();
        _items = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, InitialActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.widget_linearlayout, pendingIntent);

            //views.setProgressBar(R.id.widget_progress_big_father,8,1,false);
            g_appWidgetManager = appWidgetManager;
            widget_id = appWidgetIds[0];

            new ProgressTask().execute();

            //appWidgetManager.updateAppWidget(appWidgetIds[0],views);
        }
    }

    public void calcuateProgressBar(RemoteViews views, List<BabyListCard> _items) {
        int father_done = 0;
        int mother_done = 0;
        int together_done = 0;
        int total_conflict = 0;
        int total_upload = 0;
        int[] father_progress = new int[6];
        int[] mother_progress = new int[6];
        int[] conflict_progress = new int[6];

        for (int i = 0; i < _items.size(); i++) {
            father_done = 0;
            mother_done = 0;

            if (_items.get(i).getClicked_radio() >= 0 && _items.get(i).getClicked_radio() < 4 && _items.get(i).getSpouse_cliccked() >= 0 && _items.get(i).getSpouse_cliccked() < 4) {
                together_done++;
            }
            if (_items.get(i).isConflict()) {
                total_conflict++;
            }

            if (_items.get(i).getPicture_source() <= 5)
                total_upload++;

            if (amIfather()) {
                if (_items.get(i).getClicked_radio() >= 0 && _items.get(i).getClicked_radio() < 4) {
                    father_done++;
                }
                if (_items.get(i).getSpouse_cliccked() >= 0 && _items.get(i).getSpouse_cliccked() < 4) {
                    mother_done++;
                }
            } else if (amImother()) {
                if (_items.get(i).getClicked_radio() >= 0 && _items.get(i).getClicked_radio() < 4) {
                    mother_done++;
                }
                if (_items.get(i).getSpouse_cliccked() >= 0 && _items.get(i).getSpouse_cliccked() < 4) {
                    father_done++;
                }
            }
            father_progress[_items.get(i).getQuestion_type()] += father_done;
            mother_progress[_items.get(i).getQuestion_type()] += mother_done;
            conflict_progress[_items.get(i).getQuestion_type()] += (_items.get(i).isConflict() ? 1 : 0);
        }

        views.setProgressBar(R.id.widget_progress_big_father, 8, father_progress[0], false);
        views.setProgressBar(R.id.widget_progress_big_mother, 8, mother_progress[0], false);
        views.setProgressBar(R.id.progress_small_father, 8, father_progress[1], false);
        views.setProgressBar(R.id.progress_small_mother, 8, mother_progress[1], false);
        views.setProgressBar(R.id.progress_recognition_father, 8, father_progress[2], false);
        views.setProgressBar(R.id.progress_recognition_mother, 8, mother_progress[2], false);
        views.setProgressBar(R.id.progress_language_father, 8, father_progress[3], false);
        views.setProgressBar(R.id.progress_language_mother, 8, mother_progress[3], false);
        views.setProgressBar(R.id.progress_social_father, 8, father_progress[4], false);
        views.setProgressBar(R.id.progress_social_mother, 8, mother_progress[4], false);
        views.setProgressBar(R.id.progress_self_father, 8, father_progress[5], false);
        views.setProgressBar(R.id.progress_self_mother, 8, mother_progress[5], false);

    }


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {  // deleted by user
        super.onDeleted(context, appWidgetIds);
    }


    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private boolean amIfather() {
        if (fileM.getUserType().contains(MyFileManager.FATHER_STRING)) {
            return true;
        } else return false;
    }

    private boolean amImother() {
        if (fileM.getUserType().contains(MyFileManager.MOTHER_STRING)) {
            return true;
        } else return false;
    }

    public String diffOfDate(String begin) throws Exception
    {
        begin = Integer.toString(Integer.parseInt(begin) -1);
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String end =  formatter.format(cal.getTime());

        Date beginDate = formatter.parse(begin);
        Date endDate = formatter.parse(end);

        long diff = endDate.getTime() - beginDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return Long.toString(diffDays);
    }

    class ProgressTask extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;
        ArrayList<BabyListData> list_itemArrayList = new ArrayList<BabyListData>();
        String month, _id;
        MonthQuestions myMonthQuestion;
        String usertype;
        int remain_day;
        String dday_month;
        String my_birth;
        String text_dday;
        MySocketManager socketM;
        String result_log;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            fileM.getChildList(list_itemArrayList);
            list_itemArrayList.add(new BabyListData(R.drawable.father, " ", "아동 추가", "0000", "10")); // the last item is automatically added
            result_log = "Initial";

            if (list_itemArrayList.size() > 1) {
                my_birth = list_itemArrayList.get(0).getStatus();
                month = list_itemArrayList.get(0).getMonth();
                myMonthQuestion = fileM.getMonthQuestions(month);
                _id = list_itemArrayList.get(0).getId();
                usertype = fileM.getUserType();
                dday_month = (fileM.getMonthString(list_itemArrayList.get(0).getMonth())).split("_")[1];

                try {
                    text_dday = Integer.toString((int) Math.ceil(Integer.parseInt(dday_month) * 30.416) - Integer.parseInt(diffOfDate(my_birth.split("/")[0] + my_birth.split("/")[1] + my_birth.split("/")[2])) );
                }
                catch(Exception e){

                }

                socketM = new MySocketManager(usertype);

            }
            else{
                Log.e("ERROR","CANNOT READ CHILD INFORMATION");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (list_itemArrayList.size() > 1) {
                _items = DevelInputParent.getBabyListCard(_id, myMonthQuestion, false, usertype, false);
                result_log = socketM.getTcpIpResult(usertype , _id , MySocketManager.GET_LOG);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            calcuateProgressBar(views, _items);
            views.setTextViewText(R.id.widget_dday , text_dday+ "일");


            // ssw , null point error
            if (result_log !=null && result_log.contains("@@") && result_log.split("@@").length > 0 && result_log.split("@@")[1].split(" ").length > 3) {
                views.setTextViewText(R.id.widget_yesterday_father, result_log.split("@@")[1].split(" ")[0]);
                views.setTextViewText(R.id.widget_today_father, result_log.split("@@")[1].split(" ")[1]);
                views.setTextViewText(R.id.widget_yesterday_mother, result_log.split("@@")[1].split(" ")[2]);
                views.setTextViewText(R.id.widget_today_mother, result_log.split("@@")[1].split(" ")[3]);

                Calendar time = Calendar.getInstance();
                String recent_update = (time.get(Calendar.MONTH)+1) +"/" + time.get(Calendar.DAY_OF_MONTH) +" " +time.get(Calendar.HOUR_OF_DAY) +":"+ time.get(Calendar.MINUTE);
                views.setTextViewText(R.id.recent, "1시간마다 업데이트됩니다\n"+"최근 : "+recent_update);
            }

            g_appWidgetManager.updateAppWidget(widget_id,views);
        }

    }
}
