package myservice;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.kaist.supersong.bebecode.LockScreen;

public class ServiceReceiver extends Service {
    private KeyguardManager km = null;
    private KeyguardManager.KeyguardLock keylock = null;
    private BootReceiver pReceiver;

    public static long ttime;

    public static final int LOCK_SCREEN_PERIOD = 10;  // seconds


    private BroadcastReceiver mReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "BroadcastReceiver_onReceive!", Toast.LENGTH_LONG).show();

            String action = intent.getAction();
            if(action.equals("android.intent.action.SCREEN_OFF")){
                if( System.currentTimeMillis() - ttime >= LOCK_SCREEN_PERIOD * 1000) {
                    Intent i = new Intent(context, LockScreen.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    context.startActivity(i);
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        Log.e("ServiceReceiver","onCreate()");


        km=(KeyguardManager) this.getSystemService(Activity.KEYGUARD_SERVICE);
        if(km!=null){
            keylock = km.newKeyguardLock("test");
            keylock.disableKeyguard();
        }

        pReceiver = new BootReceiver();
        IntentFilter pFilter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        pFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        pFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        pFilter.addDataScheme("package");
        registerReceiver(pReceiver, pFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Log.e("ServiceReceiver","onStartCommand()");

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        Notification.Builder builder = new Notification.Builder(getApplicationContext())
                .setContentTitle("BabyTrackers")
                .setContentText("ScreenLock이 실행중입니다.")
                .setPriority(Notification.PRIORITY_MIN)
                .setTicker("BabyTrackers가 실행되었습니다.");

        notification = builder.build();
        startForeground(1,notification);

        nm.notify(startId, notification);
        nm.cancel(startId);

        Toast.makeText(this, "LockScreen ON", Toast.LENGTH_LONG).show();
        IntentFilter filter = new IntentFilter("com.androidhuman.action.isAlive");
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        if(keylock!=null){
            keylock.reenableKeyguard();
        }

        Toast.makeText(this, "LockScreen OFF", Toast.LENGTH_LONG).show();

        if(mReceiver != null)
            unregisterReceiver(mReceiver);

        if(pReceiver != null)
            unregisterReceiver(pReceiver);
    }



}
