package DataStructure;

/**
 * Created by SuperSong on 2017-07-30.
 */

public class AndroidVersion {

    private String android_question_name;
    private String android_image_url;

    public AndroidVersion(String a, String b){
        android_image_url = a;
        android_question_name = b;
    }
    public String getAndroid_version_name() {
        return android_question_name;
    }

    public void setAndroid_version_name(String android_version_name) {
        this.android_question_name = android_version_name;
    }

    public String getAndroid_image_url() {
        return android_image_url;
    }

    public void setAndroid_image_url(String android_image_url) {
        this.android_image_url = android_image_url;
    }
}