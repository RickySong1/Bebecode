package com.kaist.supersong.bebecode;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import DataStructure.AndroidVersion;
import DataStructure.BabyListCard;
import DataStructure.BabyListData;
import DataStructure.ImageSlideAdapter;
import DataStructure.Notification_Adapter;
import DataStructure.Notification_msg;
import mymanager.MyFileManager;
import mymanager.MySocketManager;
import questions.MonthQuestions;

import static com.kaist.supersong.bebecode.CheckFragment.fileM;
import static com.kaist.supersong.bebecode.CheckFragment.recyclerView;
import static com.kaist.supersong.bebecode.CheckFragment.sp_year;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link CheckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ProgressStatus extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ProgressBar [] progress_father = new ProgressBar[6] ;
    ProgressBar [] progress_mother = new ProgressBar[6];
    ProgressBar [] compare_progress = new ProgressBar[2];

    ProgressBar progressbar_sum_upload;
    ProgressBar progressbar_sum_problem;

    public static String IMG_FATHER="FATHER";
    public static String IMG_MOTHER="MOTHER";
    public static String IMG_BABY="BABY";
    public static String IMG_LOVE="LOVE";
    public static String IMG_IMG="IMAGE";
    public static String IMG_OTHER="OTHER";
    public static String IMG_TIMEOUT="TIMEOUT";

    ScrollView childScrollView,parentScrollView;

    ProgressBar progressbar_birth;
    TextView birth_left;
    TextView birth_right;
    TextView progressbar_dday;

    String childid;

    Notification_Adapter notification_adapter;
    ListView notification_listview;

    private TimerTask mTask;
    private Timer mTimer;

    List<BabyListCard> _items;

    public ProgressStatus() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgressStatus.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressStatus newInstance(String param1, String param2) {
        ProgressStatus fragment = new ProgressStatus();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;
        rootView = inflater.inflate(R.layout.progress_status, container, false);

        _items = new ArrayList<>();

        parentScrollView = (ScrollView) rootView.findViewById(R.id.parentScrollView);
        childScrollView = (ScrollView) rootView.findViewById(R.id.childScrollView);

        progress_father[0] = (ProgressBar) rootView.findViewById(R.id.progress_big_father);
        progress_father[1] = (ProgressBar) rootView.findViewById(R.id.progress_small_father);
        progress_father[2] = (ProgressBar) rootView.findViewById(R.id.progress_recognition_father);
        progress_father[3] = (ProgressBar) rootView.findViewById(R.id.progress_language_father);
        progress_father[4] = (ProgressBar) rootView.findViewById(R.id.progress_social_father);
        progress_father[5] = (ProgressBar) rootView.findViewById(R.id.progress_self_father);

        progress_mother[0] = (ProgressBar) rootView.findViewById(R.id.progress_big_mother);
        progress_mother[1] = (ProgressBar) rootView.findViewById(R.id.progress_small_mother);
        progress_mother[2] = (ProgressBar) rootView.findViewById(R.id.progress_recognition_mother);
        progress_mother[3] = (ProgressBar) rootView.findViewById(R.id.progress_language_mother);
        progress_mother[4] = (ProgressBar) rootView.findViewById(R.id.progress_social_mother);
        progress_mother[5] = (ProgressBar) rootView.findViewById(R.id.progress_self_mother);
        TextView textJajo = (TextView) rootView.findViewById(R.id.textJajo);

        progressbar_sum_upload = (ProgressBar) rootView.findViewById(R.id.progrssbar_sum_upload);
        progressbar_sum_problem = (ProgressBar) rootView.findViewById(R.id.progressbar_sum_problem);

        progressbar_dday = (TextView) rootView.findViewById(R.id.progressbar_dday);
        progressbar_birth = (ProgressBar) rootView.findViewById(R.id.progressbar_birth);
        birth_left = (TextView) rootView.findViewById(R.id.birth_progressbar_lefttext);
        birth_right = (TextView) rootView.findViewById(R.id.birth_progressbar_righttext);

        notification_listview = (ListView) rootView.findViewById(R.id.notification_list);


        parentScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event)
            {
                childScrollView.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        childScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event)
            {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        notification_adapter = new Notification_Adapter(getContext());
        notification_listview.setAdapter(notification_adapter);

        AdapterView.OnItemClickListener listViewClickListener = new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id)
            {
                String section = notification_adapter.getItem(position).getmSection();
                int _noti_position = notification_adapter.getItem(position).getmScrollPoint();

                if(section.contains("NOTHING")){
                    // move
                }
                else{
                    MainActivity.mViewPager.setCurrentItem(0);

                    if(section.contains("대근육운동")) sp_year.setSelection(3);
                    else if(section.contains("소근육운동")) sp_year.setSelection(4);
                    else if(section.contains("인지")) sp_year.setSelection(5);
                    else if(section.contains("언어")) sp_year.setSelection(6);
                    else if(section.contains("사회성")) sp_year.setSelection(7);
                    else if(section.contains("자조")) sp_year.setSelection(8);
                    else sp_year.setSelection(10);

                    if(_noti_position < 0){
                        // move to the first

                    }
                    else{
                        recyclerView.scrollToPosition(_noti_position-1);
                        /*
                        final Dialog dialog;
                        dialog = new DialogForAgree(getContext(),_noti_position);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.show();
                        */
                    }
                }
            }
        };

        notification_listview.setOnItemClickListener(listViewClickListener);

        for(int i=0 ; i< progress_father.length ; i++){
            progress_father[i].setMax(8);
            progress_mother[i].setMax(8);
        }

        String birth_s = fileM.getMonthString(CheckFragment.CHILD_MONTH);
        final boolean isShort = ((MonthQuestions)fileM.getMonthQuestions(CheckFragment.CHILD_MONTH)).isShortMonth();
        if(isShort){ // hide jajo
            progress_mother[5].setVisibility(ProgressBar.GONE);
            progress_father[5].setVisibility(ProgressBar.GONE);
            textJajo.setVisibility(TextView.GONE);
        }
        String my_birth = CheckFragment.CHILD_BIRTH;
        String text_dday="0";

        childid = CheckFragment.CHILDID;

        if( birth_s.compareTo("none") !=0){
            birth_left.setText(birth_s.split("_")[0]+ "개월");
            birth_right.setText(birth_s.split("_")[1]+ "개월");
        }

        try {
            text_dday = Integer.toString((int) Math.ceil(Integer.parseInt(birth_s.split("_")[1]) * 30.416) - Integer.parseInt(diffOfDate(my_birth.split("/")[0] + my_birth.split("/")[1] + my_birth.split("/")[2])));
        }catch (Exception e){
            Log.e("Counting error",e.toString());
        }


        Log.e("zzzbirth_s",birth_s);
        int progressbar_max =  (((int)    Math.ceil((Integer.parseInt(birth_s.split("_")[1]) - Integer.parseInt(birth_s.split("_")[0])) * 30.416)))  ;
        int progressbar_progress =  ((int) Math.ceil((Integer.parseInt(birth_s.split("_")[1]) - Integer.parseInt(birth_s.split("_")[0])) * 30.416)) - Integer.parseInt(text_dday);

        progressbar_birth.setMax(progressbar_max);
        progressbar_birth.setProgress(progressbar_progress);

        progressbar_dday.setText("다음 발달 문제지로 변경까지 남은 일자 ("+text_dday+"일)" );

        mTask = new TimerTask() {
            @Override
            public void run() {
                if(MainActivity.mCurrentPosition == 1 && CheckFragment.mItemsDown == 1) {
                    _items.clear();
                    for(int i=0; i< CheckFragment._items.size() ; i++){
                        _items.add(CheckFragment._items.get(i));
                    }

                    if(isShort){
                        if (_items.size() == 40)
                            calcuateProgressBar();
                    }
                    {
                        if (_items.size() == 48)
                            calcuateProgressBar();
                    }

                }
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, 1 , 3000);

        new TaskImageLoading((RecyclerView)rootView.findViewById(R.id.image_card_recycler_view)).execute();
        new TaskNotificationTextLoad(notification_adapter).execute();
        return rootView;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    */

    @Override
    public void onDetach() {
        super.onDetach();
        mTimer.cancel();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class TaskImageLoading extends AsyncTask<String, Integer, String> {

        RecyclerView recyclerView;
        WindowManager windowManager;
        DisplayMetrics metrics;
        ArrayList android_version;
        String recent_image_list;

        public TaskImageLoading(RecyclerView _recyclerView){
            this.recyclerView = _recyclerView;
            android_version = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            metrics = new DisplayMetrics();
            windowManager = (WindowManager) getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        }

        @Override
        protected String doInBackground(String... params) {
            Thread mThread = new Thread(){
                @Override
                public void run(){

                        MySocketManager a= new MySocketManager(MainActivity.USERTYPE);
                        recent_image_list = a.getTcpIpResult(MainActivity.USERTYPE, CheckFragment.CHILDID, MySocketManager.GET_RECENT_PICTURE);

                        String imgURL = null;
                        String question = null;

                        for(int i=recent_image_list.split("@@").length-1 ; i > 0 ; i--){
                            if(recent_image_list.split("@@")[i].split(" ").length>1) {
                                imgURL = recent_image_list.split("@@")[i].split(" ")[1];
                                question = recent_image_list.split("@@")[i].split(" ")[2];
                                android_version.add(new AndroidVersion(imgURL, question));
                            }
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
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false);
            recyclerView.setLayoutManager(layoutManager);
            ImageSlideAdapter adapter = new ImageSlideAdapter(getContext(),android_version , metrics);
            recyclerView.setAdapter(adapter);
        }
    }


    public void calcuateProgressBar(){
        int father_done = 0;
        int mother_done = 0;
        int together_done = 0;
        int total_conflict = 0;
        int total_upload = 0;
        int [] father_progress = new int[6];
        int [] mother_progress = new int[6];
        int [] conflict_progress = new int[6];

        for(int i=0 ; i< _items.size() ; i++){
            father_done = 0;
            mother_done = 0;

            if ( _items.get(i).getClicked_radio() >= 0 && _items.get(i).getClicked_radio() < 4 && _items.get(i).getSpouse_cliccked() >= 0 && _items.get(i).getSpouse_cliccked() < 4){
                together_done++;
            }
            if (_items.get(i).isConflict() ){
                total_conflict++;
            }

            if ( _items.get(i).getPicture_source() <= 5)
                total_upload++;

            if(amIfather()) {
                if (_items.get(i).getClicked_radio() >= 0 && _items.get(i).getClicked_radio() < 4) {
                    father_done++;
                }
                if (_items.get(i).getSpouse_cliccked() >= 0 && _items.get(i).getSpouse_cliccked() < 4) {
                    mother_done++;
                }
            }
            else if(amImother()){
                if (_items.get(i).getClicked_radio() >= 0 && _items.get(i).getClicked_radio() < 4) {
                    mother_done++;
                }
                if (_items.get(i).getSpouse_cliccked() >= 0 && _items.get(i).getSpouse_cliccked() < 4) {
                    father_done++;
                }
            }
            father_progress[_items.get(i).getQuestion_type()] += father_done;
            mother_progress[_items.get(i).getQuestion_type()] += mother_done;
            conflict_progress[_items.get(i).getQuestion_type()] += (_items.get(i).isConflict()? 1 : 0);
        }

        for(int i=0 ; i< progress_father.length ; i++){
            progress_father[i].setSecondaryProgress( father_progress[i]);
            progress_father[i].setProgress(father_progress[i] - conflict_progress[i]);

            progress_mother[i].setSecondaryProgress(mother_progress[i]);
            progress_mother[i].setProgress(mother_progress[i] - conflict_progress[i]);
         }

        progressbar_sum_problem.setSecondaryProgress(together_done);
        progressbar_sum_problem.setProgress(together_done - total_conflict);

        progressbar_sum_upload.setProgress(total_upload);
    }


    private boolean amIfather(){
        if(fileM.getUserType().contains(MyFileManager.FATHER_STRING)){
            return true;
        }
        else return false;
    }
    private boolean amImother(){
        if(fileM.getUserType().contains(MyFileManager.MOTHER_STRING)){
            return true;
        }
        else return false;
    }
    private boolean amIteacher(){
        if(fileM.getUserType().contains(MyFileManager.TEACHER_STRING)){
            return true;
        }
        else return false;
    }

    public String diffOfDate(String begin) throws Exception
    {
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String end =  formatter.format(cal.getTime());

        Date beginDate = formatter.parse(begin);
        Date endDate = formatter.parse(end);

        long diff = endDate.getTime() - beginDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return Long.toString(diffDays);
    }



    class TaskNotificationTextLoad extends AsyncTask<String, Integer, String> {

        Notification_Adapter notification_array;
        String noti_text;

        public TaskNotificationTextLoad (Notification_Adapter _notification_array){
            notification_array = _notification_array;

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            MySocketManager a= new MySocketManager(MainActivity.USERTYPE);
            noti_text = a.getTcpIpResult(MainActivity.USERTYPE, CheckFragment.CHILDID, MySocketManager.GET_NOTI);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if(noti_text.split("@@").length< 2 || noti_text.contains("NO FILE :")){
                notification_array.add(new Notification_msg("BABY","NOTHING","NOTHING","알림 메세지를 보여줍니다."));
            }else {

                for (int i = noti_text.split("@@").length - 1; i > 0; i--) {
                    if(noti_text.split("@@")[i].split("::").length > 1) {

                        notification_array.add(new Notification_msg(noti_text.split("@@")[i].split("::")[0],noti_text.split("@@")[i].split("::")[1],noti_text.split("@@")[i].split("::")[2],noti_text.split("@@")[i].split("::")[3]));
                    }
                }
            }
            notification_array.notifyDataSetChanged();
        }
    }

}
