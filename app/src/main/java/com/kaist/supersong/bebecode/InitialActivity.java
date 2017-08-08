package com.kaist.supersong.bebecode;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import DataStructure.BabyListData;
import mymanager.MyFileManager;
import mymanager.MySocketManager;

/**
 * Created by SuperSong on 2017-03-25.
 */

public class InitialActivity extends Activity {

    MyFileManager fileM;
    ImageButton father;
    ImageButton mother;
    ImageButton teacher;


    final String uploadFilePath = Environment.getExternalStorageDirectory() + "/Download/";
    final String uploadFileName = "zzz.wav";

    final String upLoadServerUri = "http://143.248.134.177/UploadToServer.php";

    ProgressDialog dialog = null;
    int serverResponseCode = 0;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_page);

        new PingTask().execute();

        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        int permissionCheckResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionCheckResult != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        fileM = new MyFileManager();

        //fileM.writeMonthQuestiontoFile();

        father = (ImageButton)findViewById(R.id.imageButtonFather);
        mother = (ImageButton)findViewById(R.id.imageButtonMother);
        //teacher = (ImageButton)findViewById(R.id.imageButtonTeacher);

        father.setOnClickListener(imageButtonListener1);
        mother.setOnClickListener(imageButtonListener2);
        //teacher.setOnClickListener(imageButtonListener3);

    }

    View.OnClickListener imageButtonListener1 = new View.OnClickListener(){
        public void onClick(View v) {

            if(fileM.initNewFile("FATHER")) {
                showChildAddDialog("FATHER" , v.getContext());
            }
            else
                Toast.makeText(v.getContext(), "파일을 만들 수 없습니다.", Toast.LENGTH_SHORT).show();

        }
    };

    View.OnClickListener imageButtonListener2 = new View.OnClickListener(){
        public void onClick(View v) {
            if(fileM.initNewFile("MOTHER")) {
                showChildAddDialog("MOTHER" , v.getContext());
            }
            else
                Toast.makeText(v.getContext(), "파일을 만들 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);
            }
        }
    }
    public String getPath(Uri uri) {
        // uri가 null일경우 null반환
        if( uri == null ) {
            return null;
        }

        // 미디어스토어에서 유저가 선택한 사진의 URI를 받아온다.
        String[] projection = { MediaStore.Images.Media.DATA };
        //Cursor cursor = managedQuery(uri, projection, null, null, null);
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor.moveToFirst() ){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            return cursor.getString(column_index);
        }
        // URI경로를 반환한다.
        return uri.getPath();
    }



    public void startMain(){
        Intent intent = new Intent(InitialActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case 1:
                if (grantResults.length >0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext() , "파일쓰기 권한이 있어야 진행이 가능합니다.", Toast.LENGTH_SHORT).show();
                }

        }
    }

    public void showChildAddDialog(final String userstring , Context context){

        final AlertDialog.Builder alert_confirm = new AlertDialog.Builder(InitialActivity.this);
        alert_confirm.setMessage("아동 ID를 입력해주세요.");
        final EditText et = new EditText(context);
        alert_confirm.setView(et);
        alert_confirm.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if(et.getText().toString().length() < 1)
                    return ;

                String value = et.getText().toString();
                MySocketManager socketM = new MySocketManager(userstring);
                String get_message = socketM.getChildInfo(value);

                if(get_message != null && get_message.contains("##ERROR")) {  // if child is selected well
                    Toast.makeText(getApplicationContext() , "아이 정보를 찾을 수 없습니다. ID를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return ;
                }
                String _new_info = fileM.addChildList(value.toString() , null , get_message);

                if(!get_message.contains("SERVER_ERROR")){  // if child is selected well
                    socketM.setMyCode(value, userstring, FirebaseInstanceId.getInstance().getToken());
                    Intent intent;
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext() , "파일쓰기 권한이 있어야 진행이 가능합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert_confirm.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        AlertDialog alert = alert_confirm.create();
        alert.show();

    }


    class PingTask extends AsyncTask<String, Integer, String> {

        MySocketManager socketM;
        String get_msg;

        ProgressDialog checking;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            socketM = new MySocketManager("TEST");

            checking= new ProgressDialog(InitialActivity.this);
            checking.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            checking.setMessage("Loading...");
            checking.setCancelable(false);
            checking.show();

            get_msg = "Waiting...";
        }

        @Override
        protected String doInBackground(String... params) {

            PackageInfo pi = null;

            try {
                pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String version = pi.versionName;
            get_msg = socketM.serverPingTest(version);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (get_msg !=null && get_msg.contains("ALIVE")){
                checking.dismiss();

                if(fileM.getMyInfo() && fileM.isChildAdded() ){

                    ArrayList<BabyListData> list_itemArrayList = new ArrayList<BabyListData>();
                    fileM.getChildList(list_itemArrayList);
                    fileM.deleteChildLIst( list_itemArrayList.get(0).getId());
                    MySocketManager socketM = new MySocketManager(fileM.getUserType());
                    String get_message = socketM.getChildInfo(list_itemArrayList.get(0).getId());
                    fileM.addChildList(list_itemArrayList.get(0).getId() , null , get_message);

                    startMain();
                }
            }
            else if(get_msg.contains("VERSION")){
                Toast.makeText(getApplicationContext() , "프로그램을 최신버젼으로 업데이트 해주세요.", Toast.LENGTH_LONG).show();
                Uri uri = Uri.parse("market://details?id=com.kaist.supersong.bebecode");
                Intent intent = new Intent(Intent.ACTION_VIEW , uri);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext() , "서버로 부터 응답이 없습니다. 잠시 후 다시 시도해주세요. 상황이 지속되면 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }

}