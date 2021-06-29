package cordova.plugins.stealfocus;

import android.content.Intent;
import android.telecom.Connection;
import android.telecom.ConnectionRequest;
import android.telecom.ConnectionService;
import android.telecom.DisconnectCause;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.net.Uri;

import org.apache.cordova.ConfigXmlParser;

public class MyConnectionService extends ConnectionService {

    private static final String TAG = "LM:MyConnectionService";
    private static Connection conn;

    public static Connection getConnection() {
        return conn;
    }

    public static void deinitConnection() {
        conn = null;
    }

    @Override
    public Connection onCreateIncomingConnection(final PhoneAccountHandle connectionManagerPhoneAccount, final ConnectionRequest request) {
        final Connection connection = new Connection() {
            @Override
            public void onAnswer() {
                this.setActive();

                Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                String baseUrl = new ConfigXmlParser().getLaunchUrl();
                String redirectUrl = request.getExtras().getString("answerRoutePath")
                        .replace(":callId", request.getExtras().getString("CallId"));
                intent.putExtra("redirectUrl", baseUrl + redirectUrl);
                StealFocusCall.getApplicationContext().startActivity(intent);
                System.out.println(TAG + " answer event called successfully");

                /* Close System UI for Call in the background. */
                DisconnectCause cause = new DisconnectCause(DisconnectCause.LOCAL);
                conn.setDisconnected(cause);
                conn.destroy();
                deinitConnection();
                System.out.println(TAG + " hangup event called successfully");
            }

            @Override
            public void onReject() {
                Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                String baseUrl = new ConfigXmlParser().getLaunchUrl();
                String redirectUrl = request.getExtras().getString("declineRoutePath")
                        .replace(":callId", request.getExtras().getString("CallId"));
                intent.putExtra("redirectUrl", baseUrl + redirectUrl);
                StealFocusCall.getApplicationContext().startActivity(intent);
                System.out.println(TAG + " decline event called successfully");

                DisconnectCause cause = new DisconnectCause(DisconnectCause.REJECTED);
                this.setDisconnected(cause);
                this.destroy();
                conn = null;
                System.out.println(TAG + " reject event called successfully");
            }

            @Override
            public void onAbort() {
                super.onAbort();
            }

            @Override
            public void onDisconnect() {
                DisconnectCause cause = new DisconnectCause(DisconnectCause.LOCAL);
                this.setDisconnected(cause);
                this.destroy();
                conn = null;
                System.out.println(TAG + " hangup event called successfully");
            }
        };

        connection.setAddress(Uri.parse(request.getExtras().getString("CallerName")), TelecomManager.PRESENTATION_ALLOWED);

        conn = connection;
        System.out.println(TAG + " receiveCall event called successfully");

        return connection;
    }
}
