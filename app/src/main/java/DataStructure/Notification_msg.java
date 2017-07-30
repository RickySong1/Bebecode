package DataStructure;

import android.graphics.drawable.Drawable;

/**
 * Created by SuperSong on 2017-07-28.
 */

public class Notification_msg {

    public String print(){
        return "name :"+mImgType+" mMsg:"+mMsg;
    }

    public String getmImgType() {
        return mImgType;
    }

    public String getmMsg() {
        return mMsg;
    }

    public String getmSection() {
        return mSection;
    }

    public int getmScrollPoint() {
        return mScrollPoint;
    }

    String mImgType;
    String mMsg;
    String mSection;
    int mScrollPoint;

    public Notification_msg(String imgType, String section, String scroll, String msg){
        mImgType = imgType;
        mMsg = msg;
        mSection = section;
        mScrollPoint = Integer.parseInt(scroll);
    }


}
