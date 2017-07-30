package com.kaist.supersong.bebecode;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import DataStructure.CustomViewPager;
import mymanager.MyFileManager;
import mymanager.MySocketManager;

import com.google.firebase.messaging.FirebaseMessaging;

import static com.kaist.supersong.bebecode.RecyclerAdapterHorizontal.items_all;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public static CustomViewPager mViewPager;
    private static MyFileManager fileM;
    private BabyDataAdapter babyAdapterList;
    public static String USERTYPE;
    public static String USERKOREAN;
    public static String SPOUSEKOREAN;

    public static int mCurrentPosition;

    public static final int numOfSections = 6; // without additional problems
    public static final int numOfProblemsinSection = 8;

    public static String intent_childid;
    public static int intent_position;
    public static int relative_position;

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        int where;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            if(position == 0){
                Fragment fragment = new CheckFragment();
                Bundle args = new Bundle();
                //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                fragment.setArguments(args);
                return fragment;

            }else if(position == 1){
                Fragment fragment = new ProgressStatus();
                Bundle args = new Bundle();
                //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                fragment.setArguments(args);
                return fragment;
            }
            else if(position == 2){
                Fragment fragment = new TrackGraph();
                Bundle args = new Bundle();
                //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                fragment.setArguments(args);
                return fragment;
            }
            else if(position == 3){
                Fragment fragment = new ResultFragment();
                Bundle args = new Bundle();
                //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                fragment.setArguments(args);
                return fragment;
            }
            return PlaceholderFragment.newInstance(position);
        }

        public void notifyDataSetChanged(int where) {
            this.where = where;
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);

            /*
            if (where   ==   1 ){
                return POSITION_NONE;
            }
            else {
                return super.getItemPosition(object);
            }
            */

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            if(USERTYPE.contains("TEACHER"))
                return 2;
            else
                return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "문제풀기";
                case 1:
                    if (USERTYPE.contains("TEACHER"))
                        return "오늘의 문제";
                    else
                        return "진행 현황";
                case 2:
                    return "변화보기";
                case 3:
                    return "결과";
            }
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseMessaging.getInstance().subscribeToTopic("notice");
        fileM = new MyFileManager();
        // THIS IS FOR MAIN_THREAD SOCKET
        if(android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.BLACK);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_toolbar);
        USERTYPE = fileM.getUserType();

        if(USERTYPE.contains("MOTHER")){
            USERKOREAN = "어머니";
            SPOUSEKOREAN = "아버지";
        } else{
            USERKOREAN = "아버지";
            SPOUSEKOREAN = "어머니";
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BebeCODE - "+USERTYPE);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //mViewPager.setPagingDisabled();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;

                //mSectionsPagerAdapter.notifyDataSetChanged(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
            alert_confirm.setMessage("내 정보를 초기화 하시겠습니까? (아이의 기록데이터는 지워지지 않습니다)").setCancelable(false).setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fileM.deleteUserInfo();
                            Intent intent = new Intent(MainActivity.this , InitialActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'No'
                            return;
                        }
                    });
            AlertDialog alert = alert_confirm.create();
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedMediaUri = data.getData();

                final String selectedImagePath = getPath(selectedMediaUri);
                Log.e("ssw : selected path:",selectedImagePath);
                Log.e("ssw : selectedmediauri:",selectedMediaUri.toString());

                // handle images
                if (selectedMediaUri.toString().contains("images")) {
                    final Dialog dialog = new Dialog(this);

                    dialog.setContentView(R.layout.dialog_image);

                    TextView tv = (TextView) dialog.findViewById(R.id.dialog_image_text);
                    tv.setText("위 사진을 업로드 하시겠습니까?");

                    ProgressBar a = (ProgressBar) dialog.findViewById(R.id.image_loading_progress_bar);
                    a.setVisibility(ProgressBar.GONE);

                    ImageView img = (ImageView) dialog.findViewById(R.id.image);

                    // Get Screen Size
                    DisplayMetrics metrics = new DisplayMetrics();
                    WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                    windowManager.getDefaultDisplay().getMetrics(metrics);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
                    params.width = metrics.widthPixels;
                    params.height = metrics.heightPixels / 2;
                    img.setLayoutParams(params);

                    img.setImageURI(selectedMediaUri );

                    Button btn_yes = (Button) dialog.findViewById(R.id.dialog_image_yes);
                    Button btn_no = (Button) dialog.findViewById(R.id.dialog_image_no);

                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //CheckYes();
                            Toast.makeText( getApplicationContext(), "파일 Upload를 시작합니다.", Toast.LENGTH_SHORT).show();
                            MySocketManager socketM = new MySocketManager(MainActivity.USERTYPE);
                            socketM.setPicture(intent_position, intent_childid , intent_childid+"_"+Integer.toString(intent_position)+"_."+selectedImagePath.split("\\.")[1] , MainActivity.USERTYPE, false);  // picture upload
                            uploadThread(selectedImagePath, intent_childid+"_"+Integer.toString(intent_position)+"_."+selectedImagePath.split("\\.")[1] );
                            items_all.get(intent_position).setSource_name(intent_childid+"_"+Integer.toString(intent_position)+"_."+selectedImagePath.split("\\.")[1]);
                            items_all.get(intent_position).setPicture_source(0);
                            dialog.dismiss();
                        }
                    });
                    btn_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } //handle video
                else  if (selectedMediaUri.toString().contains("video")) {

                    if(selectedImagePath.toString().contains("mp4") || selectedImagePath.toString().contains("MP4")){
                        Intent intent = new Intent( this , VideoTimmer.class);
                        intent.putExtra("fileuri",selectedImagePath);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "동영상은 MP4 확장자만 사용 할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                /*
                VideoView videoView = (VideoView)dialog.findViewById(R.id.videoView);
                videoView.setVideoPath(Environment.getExternalStorageDirectory() + "/BabyDevelopment/video.mp4");
                final MediaController mediaController =  new MediaController(context);
                videoView.setMediaController(mediaController);
                videoView.start();
                */
            }
        }
    }

    public void uploadThread(final String imagePath, final String uploadFileName){

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.baby_boy_icon);
        builder.setContentTitle("BebeCODE");
        builder.setContentText("Uploading file...");
        builder.setTicker("File Upload");
        builder.setContentIntent(resultPendingIntent);//required
        builder.setAutoCancel(false);

        final NotificationManager notificationManager1 = notificationManager;
        final NotificationCompat.Builder builder1 = builder;
        String fileName = imagePath;


        final int ID = 1;
        final int MAX = 100;

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        String lineEnd = "\r\n";
                        String twoHyphens = "--";
                        String boundary = "*****";
                        int bytesRead, bytesAvailable, bufferSize;
                        byte[] buffer;
                        int maxBufferSize = 1 * 1024 * 1024;
                        File sourceFile = new File(imagePath);
                        int serverResponseCode;

                        HttpURLConnection conn = null;
                        DataOutputStream dos = null;

                        try {
                            // open a URL connection to the Servlet
                            FileInputStream fileInputStream = new FileInputStream(sourceFile);
                            URL url = new URL("http://143.248.134.177/UploadToServer.php");

                            // Open a HTTP  connection to  the URL
                            conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true); // Allow Inputs
                            conn.setDoOutput(true); // Allow Outputs
                            conn.setUseCaches(false); // Don't use a Cached Copy
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Connection", "Keep-Alive");
                            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                            conn.setRequestProperty("uploaded_file", imagePath);

                            int filesize = Integer.parseInt(Long.toString(sourceFile.length() / 1024));

                            //conn.setFixedLengthStreamingMode(  );  // progress accuracy  with this function

                            dos = new DataOutputStream(conn.getOutputStream());

                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + uploadFileName + " \"" + lineEnd);

                            dos.writeBytes(lineEnd);
                            // create a buffer of  maximum size
                            bytesAvailable = fileInputStream.available();

                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            buffer = new byte[bufferSize];

                            // read file and write it into form...
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                            int sum = 0;

                            while (bytesRead > 0) {
                                dos.write(buffer, 0, bufferSize);
                                bytesAvailable = fileInputStream.available();
                                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                                sum += bytesRead;

                                builder1.setProgress(MAX, (sum / 1024 * 100) / filesize, false);
                                notificationManager1.notify(ID, builder1.build());
                            }

                            // send multipart form data necesssary after file data...
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                            // Responses from the server (code and message)
                            serverResponseCode = conn.getResponseCode();
                            String serverResponseMessage = conn.getResponseMessage();

                            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                            if (serverResponseCode == 200) {
                                builder1.setContentText("Upload Complete");
                                builder1.setProgress(0, 0, false);//removes the progress bar
                                builder1.setAutoCancel(true);
                                notificationManager1.notify(ID, builder1.build());
                                // RecyclerAdapterHorizontal.items.get(relative_position).set
                            }

                            //close the streams //
                            fileInputStream.close();
                            dos.flush();
                            dos.close();
                        }catch (MalformedURLException ex) {
                            ex.printStackTrace();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(MainActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.e("Upload file Exception", "Exception : " + e.getMessage() );
                        }
                    }
                }
        ).start();
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        int pagen;

        public PlaceholderFragment() {

        }

        public void setpage(int n){
            pagen = n;
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setpage(sectionNumber);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onResume(){
            super.onResume();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
            View rootView=null;

            /*
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 0){ // TEACHER - BABIES , PARENTS - BABIES

                ArrayList<BabyListData> list_itemArrayList = new ArrayList<BabyListData>();
                fileM.getChildList(list_itemArrayList);
                list_itemArrayList.add(new BabyListData(R.drawable.father," ","아동 추가","0000","10")); // the last item is automatically added

                if(list_itemArrayList.size() == 1){
                    rootView = inflater.inflate(R.layout.viewpager_babylistview, container, false);
                    // Listview
                    ListView listView = (ListView) rootView.findViewById(R.id.viewpager_babylist);
                    //list_itemArrayList.add(new BabyListData(R.drawable.teacher,"어린이1","상태1"));
                    //list_itemArrayList.add(new BabyListData(R.drawable.mother,"어린이2","상태2"));

                    BabyDataAdapter ddd = new BabyDataAdapter(inflater,list_itemArrayList);
                    listView.setAdapter(ddd);
                }
                else{
                    if(USERTYPE.contains("TEACHER")){
                        rootView = inflater.inflate(R.layout.devel_input_form, container, false);
                        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.devel_input_recycleview);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);

                        List<BabyListCard> items = new ArrayList<>();
                        if(list_itemArrayList.size() > 0 ) {
                            String month = list_itemArrayList.get(0).getMonth();
                            MonthQuestions myMonthQuestion = fileM.getMonthQuestions(month);
                            String _id = list_itemArrayList.get(0).getId();

                            TextView aa = (TextView) rootView.findViewById(R.id.develinput_name);
                            aa.setText(list_itemArrayList.get(0).getName());
                            aa = (TextView) rootView.findViewById(R.id.develinput_status);
                            aa.setText(list_itemArrayList.get(0).getStatus());

                            items= DevelInputParent.getBabyListCard(_id, myMonthQuestion, false, MainActivity.USERTYPE , true);
                            recyclerView.setAdapter(new RecyclerAdapterTeacher(rootView.getContext(), items, R.layout.devel_input_form));

                        }
                    }
                    else{
                        rootView = inflater.inflate(R.layout.devel_input_form, container, false);
                        final View aaa = rootView;
                        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.devel_input_recycleview);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);
                        List<BabyListCard> items = new ArrayList<>();
                        if(list_itemArrayList.size() > 0 ) {
                            String month = list_itemArrayList.get(0).getMonth();
                            MonthQuestions myMonthQuestion = fileM.getMonthQuestions(month);
                            String _id = list_itemArrayList.get(0).getId();

                            TextView aa = (TextView) rootView.findViewById(R.id.develinput_name);
                            aa.setText(list_itemArrayList.get(0).getName());
                            aa = (TextView) rootView.findViewById(R.id.develinput_status);
                            aa.setText(list_itemArrayList.get(0).getStatus());

                            items = DevelInputParent.getBabyListCard(_id,myMonthQuestion , false, MainActivity.USERTYPE , false);

                            SnapHelper helper = new LinearSnapHelper();
                            helper.attachToRecyclerView(recyclerView);
                            recyclerView.setAdapter(new RecyclerAdapterParent(rootView.getContext(), items, R.layout.devel_input_form));

                            Spinner sp_year = (Spinner) rootView.findViewById(R.id.selection_spinner);
                            ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.section, android.R.layout.simple_spinner_item);
                            yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_year.setAdapter(yearAdapter);

                            sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position, long id) {
                                    Toast.makeText( aaa.getContext(),  Integer.toString(position), Toast.LENGTH_SHORT).show();

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {}
                            });
                        }
                    }
                }
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 1){  // TEACHER - TODAY'S PROBLEMS ,  PARENTS - important questions

                if(USERTYPE.contains("TEACHER")) {
                    rootView = inflater.inflate(R.layout.devel_input_form, container, false);
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.devel_input_recycleview);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);

                    List<BabyListCard> items = new ArrayList<>();
                    ArrayList<BabyListData> list_itemArrayList = new ArrayList<BabyListData>();
                    fileM.getChildList(list_itemArrayList);

                    if(list_itemArrayList.size() > 0 ) {
                        String month = list_itemArrayList.get(0).getMonth();
                        MonthQuestions myMonthQuestion = fileM.getMonthQuestions(month);
                        String _id = list_itemArrayList.get(0).getId();

                        TextView aa = (TextView) rootView.findViewById(R.id.develinput_name);
                        aa.setText(list_itemArrayList.get(0).getName());
                        aa = (TextView) rootView.findViewById(R.id.develinput_status);
                        aa.setText(list_itemArrayList.get(0).getStatus());

                        items = DevelInputParent.getBabyListCard(_id, myMonthQuestion, true, USERTYPE , true);
                        recyclerView.setAdapter(new RecyclerAdapterTeacher(rootView.getContext(), items, R.layout.devel_input_form));
                    }
                }
                else if(USERTYPE.contains("FATHER") || USERTYPE.contains("MOTHER")){

                    rootView = inflater.inflate(R.layout.devel_input_form, container, false);
                    final View aaa = rootView;
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.devel_input_recycleview);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);

                    List<BabyListCard> items = new ArrayList<>();

                    ArrayList<BabyListData> list_itemArrayList = new ArrayList<BabyListData>();
                    fileM.getChildList(list_itemArrayList);

                    if(list_itemArrayList.size() > 0 ) {
                        String month = list_itemArrayList.get(0).getMonth();
                        MonthQuestions myMonthQuestion = fileM.getMonthQuestions(month);
                        String _id = list_itemArrayList.get(0).getId();

                        TextView aa = (TextView) rootView.findViewById(R.id.develinput_name);
                        aa.setText(list_itemArrayList.get(0).getName());
                        aa = (TextView) rootView.findViewById(R.id.develinput_status);
                        aa.setText(list_itemArrayList.get(0).getStatus());

                        items = DevelInputParent.getBabyListCard(_id,myMonthQuestion , true , USERTYPE ,false);

                        int sum_visible = 0;
                        for(int i=0 ; i<items.size() ; i++){
                            if( !items.get(i).isHide()){
                                sum_visible++;
                            }
                        }
                        if(sum_visible== 0){
                            items.add(new BabyListCard(0," ","중요문제가 남아있지 않습니다.",0,0,0,0,0,0,"00",false,false,false,10,"none.jpg"));
                        }
                       recyclerView.setAdapter(new RecyclerAdapterParent(rootView.getContext(), items, R.layout.devel_input_form));

                        Spinner sp_year = (Spinner) rootView.findViewById(R.id.selection_spinner);
                        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.important, android.R.layout.simple_spinner_item);
                        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_year.setAdapter(yearAdapter);

                        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                Toast.makeText( aaa.getContext(),  Integer.toString(position), Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });

                    }
                }
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2){  //  PARENTS - RESULTS , teacher also can see the results
                if(USERTYPE.contains("FATHER") || USERTYPE.contains("MOTHER") || USERTYPE.contains("TEACHER")) {
                    rootView = inflater.inflate(R.layout.result_show_form, container, false);

                    RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.devel_input_recycleview);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(rootView.getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);

                    List<BabyResultCard> items=new ArrayList<>();
                    ArrayList<BabyListData> list_itemArrayList = new ArrayList<BabyListData>();
                    fileM.getChildList(list_itemArrayList);

                    if(list_itemArrayList.size() >0) {
                        String month = list_itemArrayList.get(0).getMonth();
                        MonthQuestions myMonthQuestion = fileM.getMonthQuestions(month);
                        String _id = list_itemArrayList.get(0).getId();
                        MySocketManager socketM = new MySocketManager(USERTYPE);

                        TextView aa = (TextView) rootView.findViewById(R.id.develinput_name);
                        aa.setText(list_itemArrayList.get(0).getName());
                        aa = (TextView) rootView.findViewById(R.id.develinput_status);
                        aa.setText(list_itemArrayList.get(0).getStatus());

                        int [][] myQuestionAnswers;
                        int [][] spouseQuestionAnswers;

                        myQuestionAnswers = DevelInputParent.parsingResultIntegerData(socketM.getTcpIpResult(MainActivity.USERTYPE , _id , MySocketManager.GET_MY_DATA));
                        spouseQuestionAnswers = DevelInputParent.parsingResultIntegerData(socketM.getTcpIpResult(MainActivity.USERTYPE , _id , MySocketManager.GET_SPOUSE_DATA));

                        items.add(new BabyResultCard(month+"개월", countResultSum(myQuestionAnswers,spouseQuestionAnswers) , myMonthQuestion ) );
                    }

                    // need month , result , month object
                    items.add(new BabyResultCard("결과Sample A",new int[]{14,15,14,16,18,20} , new Month27_29()) );
                    items.add(new BabyResultCard("결과Sample B",new int[]{15,12,11,8,18,21} , new Month24_26()) );

                    recyclerView.setAdapter(new RecyclerAdapterResult(rootView.getContext(),items,R.layout.result_show_form));
                }
            }else{

            }
            */
            return rootView;
        }
    }

    public static int[] countResultSum(int[][] myResult , int[][] spouseResult){
        int [] _result = new int[MainActivity.numOfSections];

        for(int i=0 ; i<MainActivity.numOfSections ; i++){
            int section_sum = 0;
            for (int j=0 ; j<MainActivity.numOfProblemsinSection ; j++){

                if (myResult[i][j] > 4 || myResult[i][j] < 0 )  // this means , i'vent done all
                    return new int[] {-1,-1,-1,-1,-1,-1};

                if (myResult[i][j] <=1 && ((spouseResult[i][j] ==2) || (spouseResult[i][j] == 3)))
                    return new int[] {-1,-1,-1,-1,-1,-1};

                if (spouseResult[i][j] <=1 && ((myResult[i][j] ==2) || (myResult[i][j] == 3)))
                    return new int[] {-1,-1,-1,-1,-1,-1};

                section_sum = section_sum + Math.min(myResult[i][j] , spouseResult[i][j]);  // count MINIMUM VALUE between the parents
            }
            _result[i] = section_sum;
        }

        return _result;
    }

    public void textViewClick(View a){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
        alert_confirm.setMessage("내 정보를 초기화 하시겠습니까? (아이의 기록데이터는 지워지지 않습니다)").setCancelable(false).setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fileM.deleteUserInfo();
                        Intent intent = new Intent(MainActivity.this , InitialActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'No'
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

}