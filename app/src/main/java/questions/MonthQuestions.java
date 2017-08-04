package questions;

import java.util.ArrayList;

/**
 * Created by SuperSong on 2017-03-03.
 */

 public class  MonthQuestions {

    private String filename;

    public MonthQuestions() {
    }

    MonthQuestions( String _filename){
        filename = _filename;
    }
    public String getFileName() {
        return filename;
    }
    private ArrayList<String[]> questionList;
    private ArrayList<int[]> answerList;
    private  ArrayList<Integer> teacherImportantPosition;

    public ArrayList<String[]> getQuestionList(){
        return questionList;
    }
    public ArrayList<int[]> getAnswerList(){
        return answerList;
    }
    public ArrayList<Integer> getTeacherImportantPosition() { return teacherImportantPosition;}

    public boolean isShortMonth(){
        return false;
    }

}
