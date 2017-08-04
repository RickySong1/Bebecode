package myservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import mymanager.MyFileManager;

/**
 * Created by SuperSong on 2017-03-25.
 */


public class BootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent){

        // This package name is "com.example.supersong.babydeveltracker"

        Log.e("BootReceiver :",intent.getAction());
        Log.e("BootReceiver :",intent.getData().getSchemeSpecificPart());

        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent i  = new Intent(context, ServiceReceiver.class );
            Log.e("BOOTED :",intent.getData().getSchemeSpecificPart());
            //context.startService(i);
        }
        else if(intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)){  // app is installed
            Intent i  = new Intent(context, ServiceReceiver.class );
            Log.e("INSTALLED :",intent.getData().getSchemeSpecificPart());
            //context.startService(i);
        }
        else if(intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)){  // when the app is removed
            Intent i  = new Intent(context, ServiceReceiver.class );
            Log.e("REMOVED :",intent.getData().getSchemeSpecificPart());

            MyFileManager fileM = new MyFileManager();
            fileM.deleteUserInfo();

        }
        else if(intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)){  // when the app is updated
            Intent i  = new Intent(context, ServiceReceiver.class );
            Log.e("REPLACED :",intent.getData().getSchemeSpecificPart());

            //context.startService(i);
        }

    }
}