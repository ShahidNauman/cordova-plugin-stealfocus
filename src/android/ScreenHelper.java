package cordova.plugins.stealfocus;

import org.apache.cordova.CallbackContext;

public class ScreenHelper {
    private static CallbackContext screenLockedCallbackContext;

    protected static void bringAppToForeground(CallbackContext callbackContext) {
        if (callbackContext != null) callbackContext.success();
    }

    protected static boolean isAppInForeground(CallbackContext callbackContext) {
        if (callbackContext != null) callbackContext.success();
        return true;
    }

    protected static boolean isScreenLocked(CallbackContext callbackContext) {
        if (callbackContext != null) callbackContext.success();
        return false;
    }

    protected static boolean unlockScreen(CallbackContext callbackContext) {
        if (callbackContext != null) callbackContext.success();
        return false;
    }

    protected static boolean lockScreen(CallbackContext callbackContext) {
        screenLockedCallbackContext.success();
        if (callbackContext != null) callbackContext.success();
        return false;
    }

    protected static void onScreenLocked(CallbackContext callbackContext) {
        if (callbackContext != null) callbackContext.success();
        screenLockedCallbackContext = callbackContext;
    }
}
