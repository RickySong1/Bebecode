package mymanager;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Calendar;
import java.util.List;

import DataStructure.BabyListCard;

/**
 * Created by SuperSong on 2017-03-04.
 */

public class MySocketManager {

    public static final int PORT = 33333;
    public static final String IP_ADDRESS = "143.248.134.177";
    public static final String SET_DATA = "SET_DATA";
    public static final String SET_REQUEST = "SET_REQUEST";

    public static final int SET_REQEUST_MY_ASKING = 0;
    public static final int SET_REQEUST_ASKING_TEACHER = 1;
    public static final int SET_DATA_MY_RESULT = 2;
    public static final int SET_PICTURE = 3;
    //public static final int TARGET_ASKING_SPOUSE = 2;

    public static final int GET_MY_DATA = 0;
    public static final int GET_SPOUSE_DATA = 1;
    public static final int GET_TEACHER_DATA = 2;
    public static final int GET_MY_REQUEST = 3;
    public static final int GET_SPOUSE_REQUEST = 4;
    public static final int GET_TEACHER_REQUEST = 5;
    public static final int GET_PICTURE = 6;
    public static final int GET_PIC_NAME = 7;
    public static final int GET_LOG = 8;
    public static final int GET_TRACK = 9;
    public static final int GET_COMMENT_ON = 10;
    public static final int GET_NOTI = 11;
    public static final int GET_RECENT_PICTURE = 12;

    BufferedReader br;
    PrintWriter out;
    Socket socket;
    int [][] checkAnswers = null;

    String userType;
    String pre_message;

    public MySocketManager(String _userType) {
        br = null;
        out = null;
        socket = null;
        this.userType = _userType;

        pre_message = "%%"+ userType+"%%";
    }

    public String getChildInfo(String _id){

        String send_message = pre_message+"##INFO##"+_id+"##";
        String get_message = "The Server is not running.";
        int [][] result = null;

        try {
            socket = new Socket(IP_ADDRESS , PORT);
            br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream())), true);

            out.println(send_message);
            out.flush();

            String _temp;

            get_message = br.readLine();
            while( (_temp = br.readLine()) != null) {
                get_message = get_message.concat(_temp);
            }

        }
        catch(Exception e) {
            Log.e("So_excp",e.toString());
        } finally {
            try {
                if(socket!=null)
                    socket.close();
                if(out !=null)
                    out.close();
                if (br !=null)
                    br.close();
            } catch(Exception er) {
                Log.e("Soc_excp",er.toString());
            }
        }

        return get_message;
    }

    public String serverPingTest(){

        String send_message = "##UNKNOWN##UNKNOWN##PING_TEST##";
        String get_message = "Server is dead";

        int [][] result = null;

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(IP_ADDRESS, PORT),10000);

            br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream())), true);

            out.println(send_message);
            out.flush();

            String _temp;
            get_message = br.readLine();

            while( (_temp = br.readLine()) != null ) {
                get_message = get_message.concat(_temp);
            }
            //get_meesage have the results

        }catch (SocketTimeoutException e){
            return "TIME_OUT";
        }

        catch(Exception e) {
            Log.e("So_excp",e.toString());
        } finally {
            try {
                if(socket!=null)
                    socket.close();
                if(out !=null)
                    out.close();
                if (br !=null)
                    br.close();
            } catch(Exception er) {
                Log.e("Soc_excp",er.toString());
            }
        }

        return get_message;

    }

    public String getTcpIpResultTrack(String childID , int get_target_data,String birth){
        String send_message = null;

        switch(get_target_data){
            case GET_TRACK:
                send_message = pre_message+"##GET_TRACK##"+childID+"##"+birth+"##";
        }

        String get_message = "Cannot Get The Data From Server";
        int [][] result = null;

        try {
            socket = new Socket(IP_ADDRESS , PORT);
            br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream())), true);

            out.println(send_message);
            out.flush();

            String _temp;
            get_message = br.readLine();

            while( (_temp = br.readLine()) != null){ // && get_message.length()<116) {
                get_message = get_message.concat(_temp);
            }
            //get_meesage have the results

        } catch(Exception e) {
            Log.e("So_excp",e.toString());
        } finally {
            try {
                if(socket!=null)
                    socket.close();
                if(out !=null)
                    out.close();
                if (br !=null)
                    br.close();
            } catch(Exception er) {
                Log.e("Soc_excp",er.toString());
            }
        }

        return get_message;
    }


    public String getTcpIpComment(String childID , String question_number){

        String send_message = pre_message+"##GET_COMMENT##"+childID+"##"+question_number+"##"+"None";
        String get_message = "Cannot Get The Data From Server";

        try {
            socket = new Socket(IP_ADDRESS , PORT);
            br = new BufferedReader(new InputStreamReader( socket.getInputStream() , "UTF-8" ) );
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream() , "UTF-8")), true);

            out.println(send_message);
            out.flush();

            String _temp;
            get_message = br.readLine();

            while( (_temp = br.readLine()) != null){ // && get_message.length()<116) {
                get_message = get_message.concat(_temp);
            }
            //get_meesage have the results

        } catch(Exception e) {
            Log.e("So_excp",e.toString());
        } finally {
            try {
                if(socket!=null)
                    socket.close();
                if(out !=null)
                    out.close();
                if (br !=null)
                    br.close();
            } catch(Exception er) {
                Log.e("Soc_excp",er.toString());
            }
        }

        return get_message;
    }

    public String getTcpIpResult(String userType, String childID , int get_target_data){
        String send_message = null;

        switch(get_target_data){
            case GET_MY_DATA:
                send_message = pre_message+"##GET_DATA##"+childID+"##"+userType+"##";
                break;
            case GET_SPOUSE_DATA:  // TEACHER -> GET : MOTHER
                if(userType.contains("MOTHER"))
                    send_message = pre_message+"##GET_DATA##"+childID+"##"+"FATHER"+"##";
                else if(userType.contains("FATHER"))
                    send_message = pre_message+"##GET_DATA##"+childID+"##"+"MOTHER"+"##";
                else
                    send_message = pre_message+"##GET_DATA##"+childID+"##"+"MOTHER"+"##";
                break;

            case GET_TEACHER_DATA: // TEACHER -> GET : FATHER
                if(userType.contains("TEACHER")){
                    send_message = pre_message+"##GET_DATA##"+childID+"##"+"FATHER"+"##";
                }else
                    send_message = pre_message+"##GET_DATA##"+childID+"##"+"TEACHER"+"##";
                break;

            case GET_MY_REQUEST:
                send_message = pre_message+"##GET_REQUEST##"+childID+"##"+userType+"##";
                break;
            case GET_SPOUSE_REQUEST:
                if(userType.contains("MOTHER"))
                    send_message = pre_message+"##GET_REQUEST##"+childID+"##"+"FATHER"+"##";
                else
                    send_message = pre_message+"##GET_REQUEST##"+childID+"##"+"MOTHER"+"##";
                break;
            case GET_TEACHER_REQUEST: // TEACHER -> GET : QUESTIONS ASKED ME
                send_message = pre_message+"##GET_REQUEST##"+childID+"##"+"TEACHER"+"##";
                break;
            case GET_PICTURE:
                send_message = pre_message+"##GET_PICTURE##"+childID+"##"+"NONE"+"##";
                break;
            case GET_PIC_NAME:
                send_message = pre_message+"##GET_PIC_NAME##"+childID+"##"+"NONE"+"##";
                break;
            case GET_LOG:
                send_message = pre_message+"##GET_LOG##"+childID+"##"+"NONE"+"##";
                break;
            case GET_COMMENT_ON:
                send_message = pre_message+"##GET_COMMNET_ON##"+childID+"##"+"NONE"+"##";
                break;
            case GET_NOTI:
                send_message = pre_message+"##GET_NOTI##"+childID+"##"+userType+"##";
                break;
            case GET_RECENT_PICTURE:
                send_message = pre_message+"##GET_RECENT_PICTURE##"+childID+"##"+userType+"##";
                break;
        }


        String get_message = "Cannot Get The Data From Server";
        int [][] result = null;

        try {
            socket = new Socket(IP_ADDRESS , PORT);
            br = new BufferedReader(new InputStreamReader( socket.getInputStream(), "UTF-8" ));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream())), true);

            out.println(send_message);
            out.flush();

            String _temp;
            get_message = br.readLine();

            while( (_temp = br.readLine()) != null){ // && get_message.length()<116) {
                get_message = get_message.concat(_temp);
            }
            //get_meesage have the results

        } catch(Exception e) {

        } finally {
            try {
                if(socket!=null)
                    socket.close();
                if(out !=null)
                    out.close();
                if (br !=null)
                    br.close();
            } catch(Exception er) {

            }
        }

        return get_message;
    }

    public void setLog(String userType, String childID , String log ){
        try {
            socket = new Socket(IP_ADDRESS , PORT);
            br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream())), true);

            String send_message = "%%" + userType +"%%" + "##" + "SET_CODE" + "##" + childID + "##" + log +"##";
            out.println(send_message);
        } catch(Exception e) {
            Log.e("Socket",e.toString());
        } finally {
            try {
                if(socket!=null)
                    socket.close();
                if(out !=null)
                    out.close();
                if (br !=null)
                    br.close();
            } catch(Exception er) {
                Log.e("Socket",er.toString());
            }
        }
    }

    public void setPicture(int position , String childID , String selected_value , String userType, boolean video){

        try {
            socket = new Socket(IP_ADDRESS , PORT);
            br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream())), true);

            String send_message = "";

            if (video)
                send_message = "%%" + userType +"%%" + "##" + "SET_VIDEO" + "##" + childID + "##" + Integer.toString(position) +"##"+selected_value+"##";
            else
                send_message = "%%" + userType +"%%" + "##" + "SET_PICTURE" + "##" + childID + "##" + Integer.toString(position) +"##"+selected_value+"##";

            out.println(send_message);

        } catch(Exception e) {
            Log.e("Socket",e.toString());
        } finally {
            try {
                if(socket!=null)
                    socket.close();
                if(out !=null)
                    out.close();
                if (br !=null)
                    br.close();
            } catch(Exception er) {
                Log.e("Socket",er.toString());
            }
        }
    }

    public void setComment(String childID , String position , String question_number, String comment){
        try {
            socket = new Socket(IP_ADDRESS , PORT);
            br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream())), true);

            // e.g., %%FATHER%%##SET_COMMENT##3312##2##1-2##"SENTENCE"##

            Calendar time = Calendar.getInstance();
            comment = userType+" ("+(time.get(Calendar.YEAR))+"/"+(time.get(Calendar.MONTH)+1) +"/" + time.get(Calendar.DAY_OF_MONTH) +" " +time.get(Calendar.HOUR_OF_DAY) +":"+ time.get(Calendar.MINUTE)+") : " + comment;
            String send_message = pre_message + "##" + "SET_COMMENT" + "##" + childID + "##" + position +"##" + question_number + "##" + comment +"##";
            out.println(send_message);

        } catch(Exception e) {
            Log.e("Socket",e.toString());
        } finally {
            try {
                if(socket!=null)
                    socket.close();
                if(out !=null)
                    out.close();
                if (br !=null)
                    br.close();
            } catch(Exception er) {
                Log.e("Socket",er.toString());
            }
        }
    }


    public void setMyCode(String childID , String userType, String code){
        try {
            socket = new Socket(IP_ADDRESS , PORT);
            br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream())), true);

            String send_message = "%%" + userType +"%%" + "##" + "SET_MYCODE" + "##" + childID + "##" +userType  +"##" + code +"##";
            out.println(send_message);

        } catch(Exception e) {
            Log.e("Socket",e.toString());
        } finally {
            try {
                if(socket!=null)
                    socket.close();
                if(out !=null)
                    out.close();
                if (br !=null)
                    br.close();
            } catch(Exception er) {
                Log.e("Socket",er.toString());
            }
        }
    }


    public void setTimer(int position , String childID , String selected_value , String userType){


        try {
            socket = new Socket(IP_ADDRESS , PORT);
            br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream())), true);

            String send_message = "%%" + userType +"%%" + "##" + "SET_TIMER" + "##" + childID + "##" + Integer.toString(position) +"##"+selected_value+"##";

            out.println(send_message);

        } catch(Exception e) {
            Log.e("Socket",e.toString());
        } finally {
            try {
                if(socket!=null)
                    socket.close();
                if(out !=null)
                    out.close();
                if (br !=null)
                    br.close();
            } catch(Exception er) {
                Log.e("Socket",er.toString());
            }
        }

    }

    public void setTcpIpResult(String userType, String msgType, List<BabyListCard> items , List<BabyListCard> items_shown , int target){

        String result_data = "@@";
        String target_user = null;

        for(int i=0 ; i<items.size() ; i++){
            for(int j=0 ; j<items_shown.size() ; j++){
                if ( items.get(i).getQuestion_number().compareTo( items_shown.get(j).getQuestion_number()) == 0){
                    items.get(i).setNewCard(items_shown.get(j));
                    break;
                }
            }
        }

        switch(target){
            case SET_DATA_MY_RESULT:
                target_user = userType;
                for(int i=0 ; i<items.size() ; i++){
                    result_data = result_data.concat(Integer.toString(items.get(i).getClicked_radio()));
                    if(  (i+1)%8 ==0){ result_data =  result_data.concat("@@"); }
                    else  result_data = result_data.concat(" ");
                }
                break;

            case SET_REQEUST_MY_ASKING:
                target_user = userType;
                for(int i=0 ; i<items.size() ; i++){
                    result_data =  result_data.concat(Integer.toString(items.get(i).getS_request()));
                    if(  (i+1)%8 ==0){ result_data =  result_data.concat("@@"); }
                    else  result_data = result_data.concat(" ");
                }
                break;

            case SET_REQEUST_ASKING_TEACHER:
                target_user = "TEACHER";
                for(int i=0 ; i<items.size() ; i++){
                    result_data = result_data.concat(Integer.toString(items.get(i).getTeacher_request()));
                    if(  (i+1)%8 ==0){ result_data =  result_data.concat("@@"); }
                    else  result_data = result_data.concat(" ");
                }
                break;
        }

        // provide insufficient probleams  for "additional problems"
            if( result_data.split("@@")[result_data.split("@@").length-1].length() < result_data.split("@@")[1].length()){
                result_data = result_data.concat("9");
                while( result_data.split("@@")[result_data.split("@@").length-1].length() <result_data.split("@@")[1].length()){
                    result_data = result_data.concat(" 9");
                }
                result_data = result_data.concat("@@");
            }

        try {
            socket = new Socket(IP_ADDRESS , PORT);
            br = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( socket.getOutputStream())), true);

            String send_message = "%%" + userType +"%%" + "##" + msgType + "##" + items.get(0).getChildID() + "##" + target_user + "##" + result_data;

            out.println(send_message);

        } catch(Exception e) {
            Log.e("Socket",e.toString());
        } finally {
            try {
                if(socket!=null)
                    socket.close();
                if(out !=null)
                    out.close();
                if (br !=null)
                    br.close();
            } catch(Exception er) {
                Log.e("Socket",er.toString());
            }
        }
    }


}
