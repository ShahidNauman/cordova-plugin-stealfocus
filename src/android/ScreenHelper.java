package cordova.plugins.stealfocus;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.view.WindowManager;

import org.apache.cordova.CallbackContext;

import java.util.List;

class ScreenHelper {
    Activity cordovaActivity;
    PowerManager.WakeLock wakeLock;

    ScreenHelper(Activity activity) {
        this.cordovaActivity = activity;
    }

    static boolean isAppKilled(Context appContext) {
        boolean appProcessRunning = false;

        ActivityManager activityManager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses != null) {
            String packageName = appContext.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                System.out.println("LM:appProcess: " + appProcess.processName);
                System.out.println("LM:appProcess: " + appProcess.importance);
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE &&
                        appProcess.processName.equals(packageName)) {
                    appProcessRunning = true;
                    break;
                }
            }
        }

        return appProcessRunning;
    }

    void forceAwake(CallbackContext callbackContext) {
        cordovaActivity.runOnUiThread(() -> {
            cordovaActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

            if (isScreenLocked()) {
                acquireWakeLock();
            }
        });

        callbackContext.success();
    }

    void undoForceAwake(CallbackContext callbackContext) {
        cordovaActivity.runOnUiThread(() -> {
            cordovaActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

            if (wakeLock != null && wakeLock.isHeld()) {
                wakeLock.release();
            }

            // if (isScreenLocked()) {
            //    simulateHomePress();
            // }
        });

        callbackContext.success();
    }

    boolean isScreenLocked() {
        KeyguardManager keyguardManager = (KeyguardManager) cordovaActivity.getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    void acquireWakeLock() {
        PowerManager powerManager = (PowerManager) cordovaActivity.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP, "Life-Manager::Wakelock");

        wakeLock.acquire(60 * 60 * 1000L); // The device will stay on for 60 minutes
    }

    // private void simulateHomePress() {
    //     Intent i = new Intent(Intent.ACTION_MAIN);
    //     i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //     i.addCategory(Intent.CATEGORY_HOME);
    //     cordovaActivity.getApplicationContext().startActivity(i);
    // }
}