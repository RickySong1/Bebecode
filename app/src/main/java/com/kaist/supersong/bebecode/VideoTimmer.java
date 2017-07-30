package com.kaist.supersong.bebecode;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnK4LVideoListener;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;
import mymanager.MySocketManager;

import static com.kaist.supersong.bebecode.RecyclerAdapterHorizontal.items_all;

/**
 * Created by SuperSong on 2017-07-22.
 */

/*

 */

public class VideoTimmer extends AppCompatActivity implements OnTrimVideoListener, OnK4LVideoListener {

    private K4LVideoTrimmer mVideoTrimmer;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_trimmer);

        Intent extraIntent = getIntent();
        String filepath = extraIntent.getStringExtra("fileuri");

        //setting progressbar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Video Processing");

        mVideoTrimmer = ((K4LVideoTrimmer) findViewById(R.id.timeLine));
        if (mVideoTrimmer != null) {
            mVideoTrimmer.setMaxDuration(15);
            mVideoTrimmer.setOnTrimVideoListener(this);
            mVideoTrimmer.setOnK4LVideoListener(this);
            //mVideoTrimmer.setDestinationPath(MyFileManager.saveFolderLocation+"/");

            Uri url = Uri.parse(filepath);
            mVideoTrimmer.setVideoURI(url);
            mVideoTrimmer.setVideoInformationVisibility(true);
        }
    }

    @Override
    public void onTrimStarted() {
        mProgressDialog.show();
    }

    @Override
    public void getResult(final Uri uri) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoTimmer.this,  "파일 Upload를 시작합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //intent.setDataAndType(uri, "video/mp4");
        //startActivity(intent);

        MySocketManager socketM = new MySocketManager(MainActivity.USERTYPE);
        socketM.setPicture(MainActivity.intent_position, MainActivity.intent_childid , MainActivity.intent_childid+"_"+Integer.toString(MainActivity.intent_position)+"_."+uri.toString().split("\\.")[1] , MainActivity.USERTYPE, true); // video upload
        uploadThread(uri.toString(), MainActivity.intent_childid+"_"+Integer.toString(MainActivity.intent_position)+"_."+uri.toString().split("\\.")[1] );
        items_all.get(MainActivity.intent_position).setSource_name(MainActivity.intent_childid+"_"+Integer.toString(MainActivity.intent_position)+"_."+uri.toString().split("\\.")[1]);
        items_all.get(MainActivity.intent_position).setPicture_source(3);

        finish();
    }

    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        mVideoTrimmer.destroy();
        finish();
    }

    @Override
    public void onError(final String message) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoTimmer.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onVideoPrepared() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoTimmer.this, "onVideoPrepared", Toast.LENGTH_SHORT).show();
            }
        });
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

                                // delete temporal file
                                File a = new File(imagePath);
                                a.delete();
                            }

                            //close the streams //
                            fileInputStream.close();
                            dos.flush();
                            dos.close();
                        }catch (MalformedURLException ex) {
                            ex.printStackTrace();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(VideoTimmer.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(VideoTimmer.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.e("Upload file Exception", "Exception : " + e.getMessage() );
                        }
                    }
                }
        ).start();
    }
}
