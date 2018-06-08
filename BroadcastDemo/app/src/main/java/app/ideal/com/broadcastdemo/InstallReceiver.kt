package app.ideal.com.broadcastdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class InstallReceiver : BroadcastReceiver() {

    var context: Context? = null

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("AppInstall", "inside here")
        this.context = context

        // when package removed
        if (intent?.action.equals("android.intent.action.PACKAGE_REMOVED")) {
            Log.d("AppInstall", "onReceive called "
                    + " PACKAGE_REMOVED ")


        }
        // when package installed
        else if (intent?.action.equals(
                        "android.intent.action.PACKAGE_ADDED")) {

            Log.d("AppInstall", "onReceive called " + "PACKAGE_ADDED")


        }
    }


}