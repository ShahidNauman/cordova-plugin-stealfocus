package cordova.plugins.stealfocus;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.apache.cordova.CallbackContext;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

public class PushService extends FirebaseMessagingService {
    private static CallbackContext pushReceivedCallbackContext;

    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Timber.d("onMessageReceived: %s", remoteMessage.toIntent().getExtras().getString("CallerName"));

        JSONObject pushBundle = null;
        try {
            pushBundle = new JSONObject(remoteMessage.toIntent().getExtras().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        pushReceivedCallbackContext.success(pushBundle);
    }

    public static void onPushReceived(CallbackContext callbackContext) {
        pushReceivedCallbackContext = callbackContext;
    }
}