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
import mymanager.MyFileManager;
import mymanager.MySocketManager;
import questions.MonthQuestions;

/**
 * Created by SuperSong on 2017-03-28.
 */

public class DevelInputParent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devel_input_form);
        Intent intent = getIntent();

        String name = intent.getExtras().getString("NAME");
        final String  id = intent.getExtras().getString("ID");
        final String  status = intent.getExtras().getString("STATUS");
        String month = intent.getExtras().getString("MONTH");

        MyFileManager fileM = new MyFileManager();
        final MonthQuestions myMonthQuestion = fileM.getMonthQuestions(month);
        String tester = fileM.getMyMonthTestRange(month);

        TextView t_name = (TextView)findViewById(R.id.develinput_name);
        TextView t_status = (TextView)findViewById(R.id.develinput_status);

        t_name.setText(name);
        t_status.setText(month+"개월 ("+tester+")");

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.devel_input_recycleview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

         List<BabyListCard> items=new ArrayList<>();

        items = getBabyListCard(id,myMonthQuestion , false, MainActivity.USERTYPE , false);
        //recyclerView.setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_DEFAULT);
        recyclerView.setAdapter(new RecyclerAdapterParent(getApplicationContext(),items,R.layout.devel_input_form));

    }


    public static boolean checkConflict(int [][] myQuestion  , int[][] spouseQuestion, int i, int j){

        if(myQuestion[i][j] <= 1 && (spouseQuestion[i][j] == 2 || spouseQuestion[i][j] == 3))
                    return true;
        if(spouseQuestion[i][j] <= 1 && (myQuestion[i][j] == 2 || myQuestion[i][j] == 3))
                    return true;

        // and then,  if  RESULT is almost delay...??
        return false;
    }

    public static int[][] parsingResultIntegerData(String msg){

        int[][] _result = new int [MainActivity.numOfSections][MainActivity.numOfProblemsinSection];

        if(msg.split("@@").length > 0) {
            for (int xx = 0; xx < MainActivity.numOfSections; xx++) {
                for (int yy = 0; yy < MainActivity.numOfProblemsinSection; yy++) {
                    _result[xx][yy] = Integer.parseInt(msg.split("@@")[xx + 1].split(" ")[yy].trim());
                }
            }
            return _result;
        }
        return null;
    }
    public static String [][] parsingResultStringData(String msg){

        String[][] _result = new String [MainActivity.numOfSections][MainActivity.numOfProblemsinSection];

        if(msg.split("@@").length > 0) {
            for (int xx = 0; xx < MainActivity.numOfSections; xx++) {
                for (int yy = 0; yy < MainActivity.numOfProblemsinSection; yy++) {
                    _result[xx][yy] = msg.split("@@")[xx + 1].split(" ")[yy].trim();
                }
            }
            return _result;
        }
        return null;
    }

    public static List<BabyListCard> getBabyListCard(String id, MonthQuestions myMonthQuestion , boolean checkHide, String userType , boolean from_teacher_load) {
        String question_n;
        List<BabyListCard> _result=new ArrayList<>();
        MySocketManager socketM = new MySocketManager(userType);

        int [][] myQuestionAnswers = parsingResultIntegerData(socketM.getTcpIpResult(userType , id , MySocketManager.GET_MY_DATA));
        int [][] spouseQuestionAnswers = parsingResultIntegerData(socketM.getTcpIpResult(userType , id , MySocketManager.GET_SPOUSE_DATA));
        int [][] teacherQuestionAnswers = parsingResultIntegerData(socketM.getTcpIpResult(userType , id , MySocketManager.GET_TEACHER_DATA));
        int [][] askSpouse = parsingResultIntegerData(socketM.getTcpIpResult(userType , id , MySocketManager.GET_MY_REQUEST));
        int [][] askedFromSpouse = parsingResultIntegerData(socketM.getTcpIpResult(userType , id , MySocketManager.GET_SPOUSE_REQUEST));
        int [][] askTeacher = parsingResultIntegerData(socketM.getTcpIpResult(userType , id , MySocketManager.GET_TEACHER_REQUEST));
        int [][] picturecheck = parsingResultIntegerData(socketM.getTcpIpResult(userType , id , MySocketManager.GET_PICTURE));
        String [][] pictureName = parsingResultStringData(socketM.getTcpIpResult(userType , id , MySocketManager.GET_PIC_NAME));
        int [][] commentcheck = parsingResultIntegerData(socketM.getTcpIpResult(userType , id , MySocketManager.GET_COMMENT_ON));
        int [][] newcheck = parsingResultIntegerData(socketM.getTcpIpResult(userType , id , MySocketManager.GET_OPEN_CHAT));

        for(int i=0; i< MainActivity.numOfSections ; i++){
            for(int j=0 ; j< MainActivity.numOfProblemsinSection ; j++){
                question_n = "질문 "+ Integer.toString(i+1)+"-"+Integer.toString(j+1);

                // important_problems are checked only when they are required
                if(from_teacher_load){
                    boolean teacher_important = false;
                    for(int z=0 ; z<myMonthQuestion.getTeacherImportantPosition().size();z++){
                        if ( myMonthQuestion.getTeacherImportantPosition().get(z) ==  (i* MainActivity.numOfProblemsinSection) + j){
                            teacher_important = true;
                            break;
                        }
                    }
                    if (checkHide && !teacher_important && (checkConflict(teacherQuestionAnswers, spouseQuestionAnswers, i, j) == false) &&  askTeacher[i][j] < 1) {
                        _result.add(new BabyListCard((i*MainActivity.numOfProblemsinSection) + j, question_n, myMonthQuestion.getQuestionList().get(i)[j], i, askTeacher[i][j], askSpouse[i][j], myQuestionAnswers[i][j], spouseQuestionAnswers[i][j], teacherQuestionAnswers[i][j], id, askedFromSpouse[i][j] > 0, checkConflict(myQuestionAnswers, spouseQuestionAnswers, i, j), true,picturecheck[i][j], pictureName[i][j], commentcheck[i][j] , newcheck[i][j]));
                    } else
                        _result.add(new BabyListCard((i*MainActivity.numOfProblemsinSection) + j, question_n, myMonthQuestion.getQuestionList().get(i)[j], i, askTeacher[i][j], askSpouse[i][j], myQuestionAnswers[i][j], spouseQuestionAnswers[i][j], teacherQuestionAnswers[i][j], id, askedFromSpouse[i][j] > 0, checkConflict(myQuestionAnswers, spouseQuestionAnswers, i, j), false,picturecheck[i][j], pictureName[i][j], commentcheck[i][j], newcheck[i][j]));
                }
                else {
                    if (checkHide && ((checkConflict(myQuestionAnswers, spouseQuestionAnswers, i, j) == false) && (myQuestionAnswers[i][j] < 4 && myQuestionAnswers[i][j] >= 0)) && askedFromSpouse[i][j] < 1) {
                        _result.add(new BabyListCard((i*MainActivity.numOfProblemsinSection) + j, question_n, myMonthQuestion.getQuestionList().get(i)[j], i, askTeacher[i][j], askSpouse[i][j], myQuestionAnswers[i][j], spouseQuestionAnswers[i][j], teacherQuestionAnswers[i][j], id, askedFromSpouse[i][j] > 0, checkConflict(myQuestionAnswers, spouseQuestionAnswers, i, j), true,picturecheck[i][j], pictureName[i][j], commentcheck[i][j], newcheck[i][j]));
                    } else
                        _result.add(new BabyListCard((i*MainActivity.numOfProblemsinSection) + j, question_n, myMonthQuestion.getQuestionList().get(i)[j], i, askTeacher[i][j], askSpouse[i][j], myQuestionAnswers[i][j], spouseQuestionAnswers[i][j], teacherQuestionAnswers[i][j], id, askedFromSpouse[i][j] > 0, checkConflict(myQuestionAnswers, spouseQuestionAnswers, i, j), false,picturecheck[i][j], pictureName[i][j], commentcheck[i][j], newcheck[i][j]));
                }
            }
        }



        return _result;
    }



}
