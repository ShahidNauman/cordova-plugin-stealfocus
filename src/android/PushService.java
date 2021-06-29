package cordova.plugins.stealfocus;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PushService extends FirebaseMessagingService {
    private static final String TAG = "LM:PushService";

    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> messagePayload = remoteMessage.getData();

        System.out.println(TAG + " isAppClosed = " + ScreenHelper.isAppKilled(this));
        boolean isBackgroundCall = messagePayload.get("Type").equalsIgnoreCase(Constants.PN_EXTRA_TYPE_BACKGROUND_CALL);
        System.out.println(TAG + " isBackgroundCall = " + isBackgroundCall);
        boolean shouldStealFocus = Boolean.parseBoolean(messagePayload.get(Constants.PN_EXTRA_STEAL_FOCUS_KEY));
        System.out.println(TAG + " shouldStealFocus = " + shouldStealFocus);

        // TODO: These two route-paths must came from Push Notification Payload.
        messagePayload.put("answerRoutePath", "#/module/call/answer/:callId");
        messagePayload.put("declineRoutePath", "#/module/call/decline/:callId");

        if (ScreenHelper.isAppKilled(this.getApplicationContext()) && isBackgroundCall && shouldStealFocus) {
            StealFocusCall.getInstance(this).incomingCall(messagePayload);
            // incomingCallNotification(this.getApplicationContext(), messagePayload.get(Constants.PN_EXTRA_CALLER_NAME));
        } else {
            System.out.println(TAG + " The application doesn't need to steal focus.");
            // ScreenHelper.bringAppToForeground(this.getApplicationContext());
        }
    }

    // private void incomingCallNotification(Context appContext, String callerName) {
    //    Intent incomingCallIntent = new Intent(appContext, getMainActivityClass());
    //    incomingCallIntent.setAction(Constants.PN_ACTION_ANSWER);
    //    incomingCallIntent.setAction(Constants.PN_ACTION_IGNORE);
    //    // incomingCallIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
    //    incomingCallIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    //
    //    PendingIntent incomingCallPendingIntent =
    //            PendingIntent.getBroadcast(appContext, 0, incomingCallIntent, 0);
    //
    //    NotificationCompat.Builder builder =
    //            new NotificationCompat.Builder(appContext, Constants.PN_CHANNEL_ID)
    //                    .setSmallIcon(R.mipmap.ic_launcher)
    //                    .setContentTitle(Constants.PN_TITLE_INCOMING_CALL)
    //                    .setAutoCancel(false)
    //                    .setOngoing(false)
    //                    .setContentText(String.format(Constants.PN_TEXT_INCOMING_CALL, callerName))
    //                    .setPriority(NotificationCompat.PRIORITY_HIGH)
    //                    .setFullScreenIntent(getFullScreenIntent(appContext), true)
    //                    .addAction(R.drawable.ic_action_next_item, Constants.PN_ACTION_ANSWER,
    //                            incomingCallPendingIntent)
    //                    .addAction(R.drawable.ic_action_remove, Constants.PN_ACTION_IGNORE,
    //                            incomingCallPendingIntent);
    //
    //    NotificationManager notificationManager =
    //            (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
    //
    //    buildChannel(notificationManager);
    //
    //    Notification notification = builder.build();
    //
    //    notificationManager.notify(Constants.PN_NOTIFICATION_ID, notification);
    // }
    //
    // private Class getMainActivityClass() {
    //    String packageName = getPackageName();
    //    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
    //    return launchIntent.getComponent().getClass();
    // }
    //
    // private PendingIntent getFullScreenIntent(Context context) {
    //    Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
    //    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
    //            Intent.FLAG_ACTIVITY_SINGLE_TOP);
    //
    //    return PendingIntent.getActivity(context, 0, launchIntent, 0);
    // }
    //
    // private void buildChannel(NotificationManager notificationManager) {
    //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    //        NotificationChannel channel = new NotificationChannel(Constants.PN_CHANNEL_ID,
    //                Constants.PN_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
    //        channel.setDescription(Constants.PN_CHANNEL_DESCRIPTION);
    //        notificationManager.createNotificationChannel(channel);
    //    }
    // }
}