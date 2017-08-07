package mymanager;

import android.os.Environment;
import android.util.Log;

import com.kaist.supersong.bebecode.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import DataStructure.BabyListData;
import questions.Month10_11;
import questions.Month12_13;
import questions.Month14_15;
import questions.Month16_17;
import questions.Month18_19;
import questions.Month20_21;
import questions.Month22_23;
import questions.Month24_26;
import questions.Month27_29;
import questions.Month30_32;
import questions.Month33_35;
import questions.Month36_41;
import questions.Month42_47;
import questions.Month48_53;
import questions.Month4_5;
import questions.Month54_59;
import questions.Month60_65;
import questions.Month66_71;
import questions.Month6_7;
import questions.Month8_9;
import questions.MonthQuestions;

/**
 * Created by SuperSong on 2017-03-02.
 */

public class MyFileManager {

    public final static String MOTHER_STRING = "MOTHER";
    public final static String FATHER_STRING = "FATHER";
    public final static String TEACHER_STRING = "TEACHER";

    public static String saveFolderLocation = Environment.getExternalStorageDirectory() + "/BabyDevelopment";
    //private String saveFolderLocation = Environment.getRootDirectory() + "/BabyDevelopment";
    private String userInfoFile = "userInformation.txt";
    private String childListFile = "childList.txt";
    private String [] saveFiles;

    private String questionsMonth1 = null;
    private String questionsMonth2 = null;

    private MonthQuestions monthQuestions[];
    private MySocketManager socketM;

    public MyFileManager(){
        saveFiles = new String[20];
        saveFiles[0] = "month_4_5.txt"; saveFiles[1] = "month_6_7.txt"; saveFiles[2] = "month_8_9.txt"; saveFiles[3] = "month_10_11.txt"; saveFiles[4] = "month_12_13.txt"; saveFiles[5] = "month_14_15.txt";
        saveFiles[6] = "month_16_17.txt"; saveFiles[7] = "month_18_19.txt"; saveFiles[8] = "month_20_21.txt"; saveFiles[9] = "month_22_23.txt"; saveFiles[10] = "month_24_26.txt"; saveFiles[11] = "month_27_29.txt";
        saveFiles[12] = "month_30_32.txt"; saveFiles[13] = "month_33_35.txt"; saveFiles[14] = "month_36_41.txt"; saveFiles[15] = "month_42_47.txt"; saveFiles[16] = "month_48_53.txt"; saveFiles[17] = "month_54_59.txt";
        saveFiles[18] = "month_60_65.txt"; saveFiles[19] = "month_66_71.txt";

        monthQuestions = new MonthQuestions[20];
        monthQuestions[0] = new Month4_5(saveFiles[0]); monthQuestions[1] = new Month6_7(saveFiles[1]); monthQuestions[2] = new Month8_9(saveFiles[2]); monthQuestions[3] = new Month10_11(saveFiles[3]);
        monthQuestions[4] = new Month12_13(saveFiles[4]); monthQuestions[5] = new Month14_15(saveFiles[5]);
        monthQuestions[6] = new Month16_17(saveFiles[6]); monthQuestions[7] = new Month18_19(saveFiles[7]); monthQuestions[8] = new Month20_21(saveFiles[8]); monthQuestions[9] = new Month22_23(saveFiles[9]);
        monthQuestions[10] = new Month24_26(saveFiles[10]); monthQuestions[11] = new Month27_29(saveFiles[11]); monthQuestions[12] = new Month30_32(saveFiles[12]);
        monthQuestions[13] = new Month33_35(saveFiles[13]); monthQuestions[14] = new Month36_41(saveFiles[14]); monthQuestions[15] = new Month42_47(saveFiles[15]);
        monthQuestions[16] = new Month48_53(saveFiles[16]); monthQuestions[17] = new Month54_59(saveFiles[17]); monthQuestions[18] = new Month60_65(saveFiles[18]); monthQuestions[19] = new Month66_71(saveFiles[19]);

        //initNewFile();
        socketM = new MySocketManager(getUserType());
    }

    public void writeMonthQuestiontoFile(){

        // MonthQuestion
        for(int i=0 ; i< monthQuestions.length ; i++){
            if(monthQuestions[i].getAnswerList() != null){
                // section
                for(int j=0 ; j< 6 ; j++){
                    // each question
                    for(int z=0 ; z<8 ; z++){
                        String tempFileString = saveFolderLocation +"/"+saveFiles[i];
                        File tempFile = new File(tempFileString);

                        String get_message = monthQuestions[i].getQuestionList().get(j)[z];

                        PrintWriter pw = null;
                        try{  // line add
                            pw = new PrintWriter(new FileWriter(tempFileString,true));

                            pw.write(get_message);
                            pw.write("\n");

                            pw.close();
                        }
                        catch(Exception e){

                        }
                    }
                }
            }
        }
    }


    public MonthQuestions getMonthQuestions(String user_month){return monthQuestions[getMyMonthIndex(user_month)];}

    public boolean getMyInfo() {
        String tempFileString = saveFolderLocation +"/"+userInfoFile;
        File tempFile = new File(tempFileString);
        if (tempFile.exists()) {
            return true;
        }
        else return false;
    }

    public String getUserType(){
        String tempFileString = saveFolderLocation +"/"+userInfoFile;
        FileInputStream fileReader=null;
        String type="FAIL";
        try {
            fileReader = new FileInputStream(tempFileString);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileReader));
            type = br.readLine().split(":")[1];
            fileReader.close();
            br.close();
        }catch (Exception e){
            Log.e("MSG",e.toString());
        }

        return type;
    }

    public boolean isChildAdded(){

        FileInputStream fileReader=null;
        String tempFileString = saveFolderLocation +"/"+childListFile;
        File tempFile = new File(tempFileString);

        if (!tempFile.exists()){ try { return false; } catch (Exception e){ Log.e("MSG",e.toString()); }}

        try {
            fileReader = new FileInputStream(tempFileString);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileReader));
            String s = null;
            while ( (s=br.readLine()) != null){
                // int img, String name, String status,String id , String month
               return true;
            }
            fileReader.close();
            br.close();
        }catch (Exception e){ Log.e("MSG",e.toString()); }

        return false;
    }

    public ArrayList<BabyListData> getChildList(ArrayList<BabyListData> _result){

        FileInputStream fileReader=null;
        String tempFileString = saveFolderLocation +"/"+childListFile;
        File tempFile = new File(tempFileString);

        if (!tempFile.exists()){ try { return null; } catch (Exception e){ Log.e("MSG",e.toString()); }}

        try {
            fileReader = new FileInputStream(tempFileString);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileReader));
            String s = null;
            while ( (s=br.readLine()) != null){
                // int img, String name, String status,String id , String month
                _result.add(new BabyListData(R.drawable.teacher,s.split("##")[2],s.split("##")[1] , s.split("##")[4] , s.split("##")[3]));
            }
            fileReader.close();
            br.close();
        }catch (Exception e){ Log.e("MSG",e.toString()); }

        return _result;
    }

    public void deleteChildLIst(String _id) {
        FileInputStream fileReader=null;
        String tempFileString = saveFolderLocation +"/"+childListFile;
        String tempFileString2 = saveFolderLocation +"/"+childListFile+"_temp";
        File tempFile = new File(tempFileString);

        if (!tempFile.exists()){ try { return ; } catch (Exception e){ Log.e("MSG",e.toString()); }}

        try {
            BufferedReader in = new BufferedReader(new FileReader(tempFileString));
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(tempFileString2)));
            String line;
            int count = 1;
            while ((line = in.readLine()) != null) {
                if (line.contains("##"+_id+"##")) {
                    // do nothing
                } else {
                    out.println(line);
                }
            }
            File input = new File(tempFileString);
            File output = new File(tempFileString2);

            input.delete();
            output.renameTo(new File(tempFileString));

            in.close();
            out.close();
        }catch (Exception e){ Log.e("MSG",e.toString()); }

    }

    public String addChildList(String _id, ArrayList<BabyListData> items , String get_message){
        String tempFileString = saveFolderLocation +"/"+childListFile;
        File tempFile = new File(tempFileString);

        if (!tempFile.exists()){
           try {
               tempFile.createNewFile();
           }
           catch (Exception e){
               Log.e("MSG",e.toString());
           }
        }
        PrintWriter pw = null;
        try{  // line add
            pw = new PrintWriter(new FileWriter(tempFileString,true));

            if(!get_message.contains(_id)){
                get_message = "##2000/01/01##SERVER_ERROR##18##0000##";
            }
            else {
                pw.write(get_message);
                if(items !=null)
                    items.add(items.size() - 1, new BabyListData(R.drawable.teacher, get_message.split("##")[2], get_message.split("##")[1], get_message.split("##")[4] , get_message.split("##")[3]));
                pw.write("\n");
            }
        }
        catch (Exception e){
            Log.e("MSG",e.toString());
        }
        finally{
            if(pw!=null) pw.close();
        }
        return get_message;
    }

    public void setQuestionsAnswers(int [][] _answers){

       String tempFileString = saveFolderLocation +"/"+"month_"+questionsMonth1+"_"+questionsMonth2+".txt";
        File tempFile = new File(tempFileString);
        FileOutputStream fileWriter = null;
        BufferedWriter bw = null;

        if (tempFile.exists()) tempFile.delete();

        if ( !tempFile.exists()) {
            try {
                fileWriter = new FileOutputStream(tempFileString);
                bw = new BufferedWriter(new OutputStreamWriter(fileWriter));

                for(int i=0 ; i<_answers.length ; i++){
                    String line1 = null;

                    for(int j=0 ; j<_answers[i].length ; j++){
                        if (j==0) line1 = Integer.toString(_answers[i][j])+" ";
                        else line1 =line1.concat(Integer.toString(_answers[i][j])+" ");
                    }
                    line1 = line1.concat("\n");
                    bw.write(line1);
                    bw.flush();
                }
                fileWriter.close();
                bw.close();
            }catch (Exception e){
                Log.e("setQuestionsAnswer :",e.toString());
            }
        }
    }

    public int [][] getQuestionAnswers(){

        FileInputStream fileReader=null;
        String tempFileString = saveFolderLocation +"/"+"month_"+questionsMonth1+"_"+questionsMonth2+".txt";
        File tempFile = new File(tempFileString);

        int [][] questionAnswers = new int [7][8];
        try {
            fileReader = new FileInputStream(tempFileString);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileReader));

            String s = null;
            int count = 0;
            while ( count < 7  && ( s = br.readLine()) != null){
                questionAnswers[count][0] = Integer.parseInt(s.split(" ")[0].trim());
                questionAnswers[count][1] = Integer.parseInt(s.split(" ")[1].trim());
                questionAnswers[count][2] = Integer.parseInt(s.split(" ")[2].trim());
                questionAnswers[count][3] = Integer.parseInt(s.split(" ")[3].trim());
                questionAnswers[count][4] = Integer.parseInt(s.split(" ")[4].trim());
                questionAnswers[count][5] = Integer.parseInt(s.split(" ")[5].trim());
                questionAnswers[count][6] = Integer.parseInt(s.split(" ")[6].trim());
                questionAnswers[count][7] = Integer.parseInt(s.split(" ")[7].trim());
                count++;
            }
            fileReader.close();
            br.close();
        }catch (Exception e){
            Log.e("getQuestionAnswers() : ",e.toString());
        }
        return questionAnswers;
    }

    public String getMonthString(String _thisMonth){
        for (int i=0 ; i<saveFiles.length-1 ; i++){
            String check_month1 = saveFiles[i].split("_")[1].trim();
            String check_month2 = saveFiles[i+1].split("_")[1].trim();

            if (  Integer.parseInt(check_month1) <= Integer.parseInt(_thisMonth) && Integer.parseInt(_thisMonth) < Integer.parseInt(check_month2)){

                return check_month1+"_"+check_month2;
            }
        }
        return "66_71";
    }

    public int getMyMonthIndex(String _thisMonth){  // Return Month Index
        for (int i=0 ; i<saveFiles.length-1 ; i++){
            String check_month1 = saveFiles[i].split("_")[1].trim();
            String check_month2 = saveFiles[i+1].split("_")[1].trim();

            Log.e("zz111",check_month1);
            Log.e("zz222",check_month2);

            if (  Integer.parseInt(check_month1) <= Integer.parseInt(_thisMonth) && Integer.parseInt(_thisMonth) < Integer.parseInt(check_month2)){

                Log.e("zz333",Integer.toString(i));
                return i;
            }
        }
        return saveFiles.length-1;
    }

    public String getMyMonthTestRange(String _thisMonth){  // Return Month Index
        for (int i=0 ; i<saveFiles.length-1 ; i++){
            String check_month1 = saveFiles[i].split("_")[1].trim();
            String check_month2 = saveFiles[i+1].split("_")[1].trim();

            if (  Integer.parseInt(check_month1) <= Integer.parseInt(_thisMonth) && Integer.parseInt(_thisMonth) < Integer.parseInt(check_month2)){
                return check_month1 +" ~ "+ Integer.toString(Integer.parseInt(check_month2)-1)+"개월용";
            }
        }
        return "범위내 없음";
    }


    public void deleteUserInfo(){
        File folder = new File(saveFolderLocation);
        if ( folder.exists() ) {
            deleteAllFiles(saveFolderLocation);
        }
    }
    public  void deleteAllFiles(String path){ File file = new File(path);  File[] tempFile = file.listFiles(); if(tempFile.length >0){ for (int i = 0; i < tempFile.length; i++) { if(tempFile[i].isFile()){ tempFile[i].delete(); }else{  deleteAllFiles(tempFile[i].getPath()); } tempFile[i].delete(); } file.delete(); } }

    public boolean initNewFile(String user_type){

        File folder = new File(saveFolderLocation);
        if ( !folder.exists() ) {
            if (!folder.mkdirs()) { Log.e("TravellerLog :: ", "Problem creating folder: "+saveFolderLocation); }
        }

        FileOutputStream fileWriter = null;
        BufferedWriter bw = null;

            String tempFileString = saveFolderLocation +"/"+userInfoFile;
            try {

                    fileWriter = new FileOutputStream(tempFileString);
                    bw = new BufferedWriter(new OutputStreamWriter(fileWriter));
                    bw.write("##USER:"+user_type);
                    bw.flush();
                    fileWriter.close();
                    bw.close();
                return true;
                }catch (Exception e ){
                    Log.e("MSG",tempFileString +" writing error");
                }
        return false;
    }
}
