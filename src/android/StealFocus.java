package cordova.plugins.stealfocus;

import android.app.Activity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;

public class StealFocus extends CordovaPlugin {
    ScreenHelper screenHelper;
    Activity cordovaActivity;

    /**
     * Called after plugin construction and fields have been initialized.
     * Prefer to use pluginInitialize instead since there is no value in
     * having parameters on the initialize() function.
     *
     * @param cordova The Cordova Interface
     * @param webView The Cordova WebView
     */
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        cordovaActivity = cordova.getActivity();
        screenHelper = new ScreenHelper(cordovaActivity);
    }

    /**
     * Executes the request.
     * <p>
     * This method is called from the WebView thread. To do a non-trivial amount of work, use:
     * cordova.getThreadPool().execute(runnable);
     * <p>
     * To run on the UI thread, use:
     * cordova.getActivity().runOnUiThread(runnable);
     *
     * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return Whether the action was valid.
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        // if ("enable".equals(action)) {
        //    enable(callbackContext);
        //    return true;
        // } else if ("disable".equals(action)) {
        //    disable(callbackContext);
        //    return true;
        // } else if ("onPushReceived".equals(action)) {
        //    PushService.onPushReceived(callbackContext);
        //    return true;
        // } else if ("isAppInForeground".equals(action)) {
        //    ScreenHelper.isAppInForeground(callbackContext);
        //    return true;
        // } else if ("isScreenLocked".equals(action)) {
        //    ScreenHelper.isScreenLocked(callbackContext);
        //    return true;
        // } else if ("bringAppToForeground".equals(action)) {
        //    ScreenHelper.bringAppToForeground(cordovaActivity.getApplicationContext(), callbackContext);
        //    return true;
        // } else
        if ("forceAwake".equals(action)) {
            screenHelper.forceAwake(callbackContext);
            return true;
        } else if ("undoForceAwake".equals(action)) {
            screenHelper.undoForceAwake(callbackContext);
            return true;
        }
        // else if ("onScreenLocked".equals(action)) {
        //    ScreenHelper.onScreenLocked(callbackContext);
        //    callbackContext.error("");
        //    return true;
        // }

        return false;  // Returning false results in a "MethodNotFound" error.
    }

    // private void enable(CallbackContext callbackContext) {
    //    if (callbackContext != null) callbackContext.success();
    // }

    // private void disable(CallbackContext callbackContext) {
    //    if (callbackContext != null) callbackContext.success();
    // }
}
