package com.kaist.supersong.bebecode;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import DataStructure.BabyListCard;
import DataStructure.BabyListData;
import DataStructure.CustomSpinnerAdapter;
import DataStructure.FlingRecyclerView;
import mymanager.MyFileManager;
import mymanager.MySocketManager;
import questions.MonthQuestions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final int QUESTION_ANSWER_CHECKED = 1;
    public static final int QUESTION_ANSWER_UNCHECKED = 2;
    public static String CHILDID;
    public static String CHILD_NAME;
    public static String CHILD_MONTH;
    public static String CHILD_BIRTH;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar progressbar; ProgressBar progressbar_mother; ProgressBar progressbar_father;
    TextView txtName; TextView txtStatus;
    TextView rightArrow; TextView leftArrow; TextView counterText;
    TextView progressbar_father_text; TextView progressbar_mother_text;
    TextView progressbar_title;
    TextView progress_father_name;
    TextView progress_mother_name;

    public static FlingRecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;

    static MyFileManager fileM;

    ArrayList<String> languages;

    static int mItemsDown;
    static List<BabyListCard> _items;
    static List<BabyListCard> _items_shown;

    int selected_position;
    CustomSpinnerAdapter customSpinnerAdapter;
    public static Spinner sp_year;
    public static boolean isShortProblem = false;

    public CheckFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckFragment newInstance(String param1, String param2) {
        CheckFragment fragment = new CheckFragment();
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

        _items = new ArrayList<>();
        _items_shown = new ArrayList<>();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=null;

        rootView = inflater.inflate(R.layout.devel_input_form_hori, container, false);
        progressbar  = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressbar_father = (ProgressBar) rootView.findViewById(R.id.progress_father);
        progressbar_mother = (ProgressBar) rootView.findViewById(R.id.progress_mother);
        progressbar_father_text = (TextView) rootView.findViewById(R.id.progress_father_text);
        progressbar_mother_text = (TextView) rootView.findViewById(R.id.progress_mother_text);
        progressbar_title = (TextView) rootView.findViewById(R.id.text_title_partial_progress);
        progress_father_name = (TextView) rootView.findViewById(R.id.progress_text_father);
        progress_mother_name = (TextView) rootView.findViewById(R.id.progress_text_mother);

        new ProgressTask().execute();

        recyclerView = (FlingRecyclerView) rootView.findViewById(R.id.devel_input_recycleview);
        final LinearLayoutManager layoutmanager = new LinearLayoutManager(rootView.getContext() , LinearLayoutManager.HORIZONTAL, false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                counterText.setText(Integer.toString(Math.max(layoutmanager.findFirstVisibleItemPosition(),layoutmanager.findLastVisibleItemPosition())+1)+"/"+Integer.toString(_items_shown.size()));

                if(Math.max(layoutmanager.findFirstVisibleItemPosition(),layoutmanager.findLastVisibleItemPosition())+1 == 1){
                    leftArrow.setVisibility(TextView.INVISIBLE);
                }
                else
                    leftArrow.setVisibility(TextView.VISIBLE);

                if(Math.max(layoutmanager.findFirstVisibleItemPosition(),layoutmanager.findLastVisibleItemPosition())+1 == _items_shown.size()){
                    rightArrow.setVisibility(TextView.INVISIBLE);
                }
                else
                    rightArrow.setVisibility(TextView.VISIBLE);

            }
        });

        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setHasFixedSize(true);

        txtName = (TextView) rootView.findViewById(R.id.develinput_name);
        txtStatus = (TextView) rootView.findViewById(R.id.develinput_status);

        rightArrow = (TextView) rootView.findViewById(R.id.right_arrow);
        leftArrow = (TextView) rootView.findViewById(R.id.left_arrow);
        counterText = (TextView) rootView.findViewById(R.id.counter_text);

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        languages = new ArrayList<String>();
        sp_year = (Spinner) rootView.findViewById(R.id.selection_spinner);

        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText( getContext(),  Integer.toString(position), Toast.LENGTH_SHORT).show();

                selected_position = position;
                // <item>풀지 않은 문제</item>
                // <item>의견차이</item>
                //<item>대근육운동</item>
                //<item>소근육운동</item>
                //<item>인지</item>
                //<item>언어</item>
                //<item>사회성</item>
                //<item>자조</item>
                //<item>모두</item>

                switch(position){
                    case 0: // 풀지 않은 문제
                        for(int i=0 ; i < _items.size() ; i++){
                            if (_items.get(i).getClicked_radio() < 4  && _items.get(i).getClicked_radio() >= 0){
                                _items.get(i).setHide(true);
                            }
                            else
                                _items.get(i).setHide(false);
                        }
                        break;
                    case 1: // 의견차이
                        for(int i=0 ; i < _items.size() ; i++){
                            if (_items.get(i).isConflict() ){
                                boolean canbe_seen = true;
                                for(int z=0 ; z<_items.size() ; z++) {
                                    if (_items.get(i).getQuestion_type() == _items.get(z).getQuestion_type()) {
                                        if (_items.get(z).getClicked_radio() >= 0 && _items.get(z).getClicked_radio() < 4) {
                                        } else
                                            canbe_seen = false;
                                    }
                                }
                                // Conflict 이면서 ,  같은 유형의 문제를 다 풀었어야지,  의견차이라고 표시됨.
                                if(canbe_seen)
                                    _items.get(i).setHide(false);
                            }
                            else
                                _items.get(i).setHide(true);
                        }
                        break;
                    case 3: case 4:case 5: case 6: case 7:  // 대,소,인지
                        for(int i=0 ; i < _items.size() ; i++) {
                            if ( _items.get(i).getQuestion_type() == position-3 ){
                                _items.get(i).setHide(false);
                            }
                            else
                                _items.get(i).setHide(true);
                        }
                        break;
                    case 8: // 자조
                        for(int i=0 ; i < _items.size() ; i++) {
                            if ( _items.get(i).getQuestion_type() == position-3  && !isShortProblem){
                                _items.get(i).setHide(false);
                            }
                            else
                                _items.get(i).setHide(true);
                        }
                        break;
                    case 10: // 모든 문제
                        for(int i=0 ; i < _items.size() ; i++) {
                            _items.get(i).setHide(false);
                        }
                        break;
                }
                _items_shown.clear();
                for(int i=0 ; i< _items.size() ; i++){
                    if(!_items.get(i).isHide()){
                        _items_shown.add(_items.get(i));
                    }
                }

                updateProgressBar(position);

                if(mAdapter !=null)
                    mAdapter.notifyDataSetChanged();

                //layoutmanager.scrollToPosition(0);

                counterText.setText(Integer.toString(Math.max(layoutmanager.findFirstVisibleItemPosition(),layoutmanager.findLastVisibleItemPosition())+1)+"/"+Integer.toString(_items_shown.size()));

                if(Math.max(layoutmanager.findFirstVisibleItemPosition(),layoutmanager.findLastVisibleItemPosition())+1 == 1){
                    leftArrow.setVisibility(TextView.INVISIBLE);
                } else leftArrow.setVisibility(TextView.VISIBLE);
                if(Math.max(layoutmanager.findFirstVisibleItemPosition(),layoutmanager.findLastVisibleItemPosition())+1 == _items_shown.size()){
                    rightArrow.setVisibility(TextView.INVISIBLE);
                } else rightArrow.setVisibility(TextView.VISIBLE);

                recyclerView.scrollToPosition(0);

                //recyclerView.smoothScrollToPosition(7);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return rootView;
    }


    public void updateProgressBar(int position){
        int father_done = 0;
        int mother_done = 0;

        switch(position){
            case 0: progressbar_title.setText("풀지 않은 문제"); break;
            case 1: progressbar_title.setText("의견차이가 있는 문제"); break;
            //case 2: progressbar_title.setText("풀지 않은 문제"); break;
            case 3: progressbar_title.setText("대근육운동 문제풀이 개수"); break;
            case 4: progressbar_title.setText("소근육운동 문제풀이 개수"); break;
            case 5: progressbar_title.setText("인지 문제풀이 개수"); break;
            case 6: progressbar_title.setText("언어 문제풀이 개수"); break;
            case 7: progressbar_title.setText("사회성 문제풀이 개수"); break;
            case 8: progressbar_title.setText("자조 문제풀이 개수"); break;
            //case 9: progressbar_title.setText("풀지 않은 문제"); break;
            case 10: progressbar_title.setText("모든 문제"); break;
        }

        progress_father_name.setVisibility(ProgressBar.VISIBLE);
        progress_mother_name.setVisibility(ProgressBar.VISIBLE);
        progressbar_father.setVisibility(ProgressBar.VISIBLE);
        progressbar_mother.setVisibility(ProgressBar.VISIBLE);
        progressbar_mother_text.setVisibility(TextView.VISIBLE);

        switch(position){
            case 0: // 풀지 않은 문제
            case 1: // 의견차이
            case 10: // 모든 문제
                for (int i = 0; i < _items.size(); i++) {
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
                }
                progressbar_father.setMax(_items.size());
                progressbar_mother.setMax(_items.size());
                progressbar_father.setProgress(father_done);
                progressbar_father.getProgressDrawable().setColorFilter(Color.parseColor("#FFFFC637"), PorterDuff.Mode.SRC_IN);
                progressbar_mother.getProgressDrawable().setColorFilter(Color.parseColor("#FFFFC637"), PorterDuff.Mode.SRC_IN);

                progressbar_mother.setProgress(mother_done);
                progressbar_father_text.setText(Integer.toString(father_done) +"/"+ Integer.toString(_items.size())   );
                progressbar_mother_text.setText(Integer.toString(mother_done) +"/"+ Integer.toString(_items.size())   );
                // spinner update
                if(position == 0){  // 풀지 않은 문제
                    if(languages.size() > 0)
                        languages.remove(position);

                    if( amIfather() ){
                        languages.add(position,"풀지않은 문제 ("+ Integer.toString(father_done)+"/"+Integer.toString(_items.size())+")");
                    }else if (amImother()) {
                        languages.add(position,"풀지않은 문제 ("+ Integer.toString(mother_done)+"/"+Integer.toString(_items.size())+")");
                    }else {
                    }
                }else if(position == 1){ // 의견차이
                    int conflict_sum = 0;
                    for(int i=0 ; i<_items_shown.size() ; i++){
                        if( _items_shown.get(i).isConflict())
                            conflict_sum += 1;
                    }
                    progressbar_father.setProgress(conflict_sum);
                    progressbar_father.setMax(_items.size());
                    progressbar_father_text.setText(Integer.toString(conflict_sum) +"/"+ Integer.toString( _items.size())   );

                    progressbar_mother_text.setVisibility(TextView.INVISIBLE);
                    progressbar_mother.setVisibility(ProgressBar.INVISIBLE);
                    progress_father_name.setVisibility(ProgressBar.INVISIBLE);
                    progress_mother_name.setVisibility(ProgressBar.INVISIBLE);
                    languages.remove(position);
                    if( amIfather() ){
                        languages.add(position,"의견차이 확인 ("+ Integer.toString(_items_shown.size())+")");
                    }else if (amImother()) {
                        languages.add(position,"의견차이 확인 ("+ Integer.toString(_items_shown.size())+")");
                    }else {

                    }
                }else if(position == 10){ // 모든 문제
                    languages.remove(position);
                    if( amIfather() ){
                        languages.add(position,"모든 문제 ("+ Integer.toString(father_done)+"/"+Integer.toString(_items.size())+")");
                    }else if (amImother()) {
                        languages.add(position,"모든 문제 ("+ Integer.toString(mother_done)+"/"+Integer.toString(_items.size())+")");
                    }else {
                    }
                }
                break;

            case 2:case 9:
                languages.remove(position);
                languages.add(position,"─────");
                break;

            case 3: case 4:case 5: case 6: case 7:case 8:  // 대,소,인지
                for (int i = 0; i < _items_shown.size(); i++) {
                    if(amIfather()) {
                        if (_items_shown.get(i).getClicked_radio() >= 0 && _items_shown.get(i).getClicked_radio() < 4) {
                            father_done++;
                        }
                        if (_items_shown.get(i).getSpouse_cliccked() >= 0 && _items_shown.get(i).getSpouse_cliccked() < 4) {
                            mother_done++;
                        }
                    }
                    else if(amImother()){
                        if (_items_shown.get(i).getClicked_radio() >= 0 && _items_shown.get(i).getClicked_radio() < 4) {
                            mother_done++;
                        }
                        if (_items_shown.get(i).getSpouse_cliccked() >= 0 && _items_shown.get(i).getSpouse_cliccked() < 4) {
                            father_done++;
                        }
                    }
                }
                progressbar_father.setMax(_items_shown.size());
                progressbar_mother.setMax(_items_shown.size());
                progressbar_father.setProgress(father_done);
                progressbar_mother.setProgress(mother_done);
                progressbar_father_text.setText(Integer.toString(father_done) +"/"+ Integer.toString(_items_shown.size())   );
                progressbar_mother_text.setText(Integer.toString(mother_done) +"/"+ Integer.toString(_items_shown.size())   );

                if(position == 3){  // 풀지 않은 문제
                    languages.remove(position);
                    if( amIfather() ){
                        languages.add(position,"1. 대근육운동 ("+ Integer.toString(father_done)+"/"+Integer.toString(_items_shown.size()) +")");
                    }else if (amImother()) {
                        languages.add(position,"1. 대근육운동 ("+ Integer.toString(mother_done)+"/"+Integer.toString(_items_shown.size())+")");
                    }else {
                    }
                }else if(position == 4){ //
                    languages.remove(position);
                    if( amIfather() ){
                        languages.add(position,"2. 소근육운동 ("+ Integer.toString(father_done)+"/"+Integer.toString(_items_shown.size())+")");
                    }else if (amImother()) {
                        languages.add(position,"2. 소근육운동 ("+ Integer.toString(mother_done)+"/"+Integer.toString(_items_shown.size())+")");
                    }else {

                    }
                }else if(position == 5){ //
                    languages.remove(position);
                    if( amIfather() ){
                        languages.add(position,"3. 인지 ("+ Integer.toString(father_done)+"/"+Integer.toString(_items_shown.size())+")");
                    }else if (amImother()) {
                        languages.add(position,"3. 인지 ("+ Integer.toString(mother_done)+"/"+Integer.toString(_items_shown.size())+")");
                    }else {
                    }
                }else if(position == 6){ //
                    languages.remove(position);
                    if( amIfather() ){
                        languages.add(position,"4. 언어 ("+ Integer.toString(father_done)+"/"+Integer.toString(_items_shown.size())+")");
                    }else if (amImother()) {
                        languages.add(position,"4. 언어 ("+ Integer.toString(mother_done)+"/"+Integer.toString(_items_shown.size())+")");
                    }else {
                    }
                }else if(position == 7){ //
                    languages.remove(position);
                    if( amIfather() ){
                        languages.add(position,"5. 사회성 ("+ Integer.toString(father_done)+"/"+Integer.toString(_items_shown.size())+")");
                    }else if (amImother()) {
                        languages.add(position,"5. 사회성 ("+ Integer.toString(mother_done)+"/"+Integer.toString(_items_shown.size())+")");
                    }else {
                    }
                }else if(position == 8){ //
                    languages.remove(position);
                    if(isShortProblem){
                        languages.add("─────");
                    }
                    else {
                        if (amIfather()) {
                            languages.add(position, "6. 자조 (" + Integer.toString(father_done) + "/" + Integer.toString(_items_shown.size()) + ")");
                        } else if (amImother()) {
                            languages.add(position, "6. 자조 (" + Integer.toString(mother_done) + "/" + Integer.toString(_items_shown.size()) + ")");
                        } else {
                        }
                    }
                }

                break;
        }

    }

     class DateHandler extends Handler {

         public void handleMessage(Message msg)
         {
             switch (msg.what)
             {
                 case QUESTION_ANSWER_CHECKED:
                     updateProgressBar(selected_position);
                     break;

                 default:
                     break;
             }
         }
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


    class ProgressTask extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;
        ArrayList<BabyListData> list_itemArrayList = new ArrayList<BabyListData>();
        String month,_id;
        MonthQuestions myMonthQuestion;
        ArrayAdapter yearAdapter;
        Activity my_activity;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            fileM = new MyFileManager();
            fileM.getChildList(list_itemArrayList);
            list_itemArrayList.add(new BabyListData(R.drawable.father," ","아동 추가","0000","10")); // the last item is automatically added

            if(list_itemArrayList.size() > 1 ) {
                month = list_itemArrayList.get(0).getMonth();
                CHILD_MONTH = month;
                myMonthQuestion = fileM.getMonthQuestions(month);
                isShortProblem = myMonthQuestion.isShortMonth();
                _id = list_itemArrayList.get(0).getId();
                CHILDID = _id;
                CHILD_NAME = list_itemArrayList.get(0).getName();
                CHILD_BIRTH = list_itemArrayList.get(0).getStatus();

                MySocketManager socketM = new MySocketManager(MainActivity.USERTYPE);
                socketM.setMyCode(CheckFragment.CHILDID, MainActivity.USERTYPE, FirebaseInstanceId.getInstance().getToken());

            }
            my_activity = getActivity();
             yearAdapter = ArrayAdapter.createFromResource(my_activity, R.array.section, android.R.layout.simple_spinner_dropdown_item);
        }

        @Override
        protected String doInBackground(String... params) {
                if(list_itemArrayList.size() > 1 ) {
                    mItemsDown = 0 ;
                    CheckFragment._items = DevelInputParent.getBabyListCard(_id, myMonthQuestion , false, MainActivity.USERTYPE , false);
                    // delete 자조 problem
                    mItemsDown = 1;
                }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            progressbar.setVisibility(ProgressBar.GONE);
            txtName.setText(list_itemArrayList.get(0).getName());
            txtStatus.setText(CHILD_BIRTH);
            DateHandler aa = new DateHandler();

            if(isShortProblem && CheckFragment._items.size() > 40){
                while(CheckFragment._items.size() > 40){
                    _items.remove(40);
                }
            }

            mAdapter = new RecyclerAdapterHorizontal(getContext(), CheckFragment._items, CheckFragment. _items_shown,  R.layout.devel_input_form_hori , aa);
            recyclerView.setAdapter(mAdapter);

            // initialize spinner
            languages.add("풀지않은 문제"); languages.add("의견차이 확인"); languages.add("─────"); languages.add("1. 대근육운동"); languages.add("2. 소근육운동");
            languages.add("3. 인지"); languages.add("4. 언어"); languages.add("5. 사회성");
            if(isShortProblem)
                languages.add("─────");
            else
                languages.add("6. 자조");
            languages.add("─────"); languages.add("─────");

            for( int ttt = 0 ; ttt< languages.size() ; ttt++){
                switch(ttt){
                    case 0: // 풀지 않은 문제
                        for(int i=0 ; i < _items.size() ; i++){
                            if (_items.get(i).getClicked_radio() < 4  && _items.get(i).getClicked_radio() >= 0){
                                _items.get(i).setHide(true);
                            }
                            else
                                _items.get(i).setHide(false);
                        }
                        break;
                    case 1: // 의견차이
                        for(int i=0 ; i < _items.size() ; i++){

                            if (_items.get(i).isConflict()){
                                boolean canbe_seen = true;
                                for(int z=0 ; z<_items.size() ; z++) {
                                    if (_items.get(i).getQuestion_type() == _items.get(z).getQuestion_type()) {
                                        if (_items.get(z).getClicked_radio() >= 0 && _items.get(z).getClicked_radio() < 4) {
                                        } else
                                            canbe_seen = false;
                                    }
                                }
                                // Conflict 이면서 ,  같은 유형의 문제를 다 풀었어야지,  의견차이라고 표시됨.
                                if(canbe_seen)
                                    _items.get(i).setHide(false);
                            }
                            else
                                _items.get(i).setHide(true);
                        }
                        break;
                    case 3: case 4:case 5: case 6: case 7:  // 대,소,인지
                        for(int i=0 ; i < _items.size() ; i++) {
                            if ( _items.get(i).getQuestion_type() == ttt-3){
                                _items.get(i).setHide(false);
                            }
                            else
                                _items.get(i).setHide(true);
                        }
                        break;
                    case 8:
                        for(int i=0 ; i < _items.size() ; i++) {
                            if ( _items.get(i).getQuestion_type() == ttt-3 && !isShortProblem ){
                                _items.get(i).setHide(false);
                            }
                            else
                                _items.get(i).setHide(true);
                        }
                        break;
                    case 10: // 모든 문제
                        for(int i=0 ; i < _items.size() ; i++) {
                                _items.get(i).setHide(false);
                        }
                        break;
                }
                _items_shown.clear();
                for(int i=0 ; i< _items.size() ; i++){
                    if(!_items.get(i).isHide()){
                        _items_shown.add(_items.get(i));
                    }
                }
                updateProgressBar(ttt);
            }
            // initialize spinner
               yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                customSpinnerAdapter = new CustomSpinnerAdapter(my_activity, languages);
                sp_year.setAdapter(customSpinnerAdapter);


        }
    }

}
