package cordova.plugins.stealfocus;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;

import static cordova.plugins.stealfocus.PushService.*;
import static cordova.plugins.stealfocus.ScreenHelper.*;

public class StealFocus extends CordovaPlugin {
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
        if ("enable".equals(action)) {
            enable(callbackContext);
            return true;
        } else if ("disable".equals(action)) {
            disable(callbackContext);
            return true;
        } else if ("onPushReceived".equals(action)) {
            onPushReceived(callbackContext);
            return true;
        } else if ("isAppInForeground".equals(action)) {
            isAppInForeground(callbackContext);
            return true;
        } else if ("isScreenLocked".equals(action)) {
            isScreenLocked(callbackContext);
            return true;
        } else if ("bringAppToForeground".equals(action)) {
            bringAppToForeground(callbackContext);
            return true;
        } else if ("unlockScreen".equals(action)) {
            unlockScreen(callbackContext);
            return true;
        } else if ("lockScreen".equals(action)) {
            lockScreen(callbackContext);
            return true;
        } else if ("onScreenLocked".equals(action)) {
            onScreenLocked(callbackContext);
            return true;
        }

        return false;  // Returning false results in a "MethodNotFound" error.
    }

    private void disable(CallbackContext callbackContext) {
        if (callbackContext != null) callbackContext.success();
    }

    private void enable(CallbackContext callbackContext) {
        if (callbackContext != null) callbackContext.success();
    }
}
