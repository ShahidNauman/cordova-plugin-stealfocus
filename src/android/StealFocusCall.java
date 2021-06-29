package cordova.plugins.stealfocus;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.telecom.Connection;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;

import java.util.Map;

public class StealFocusCall {

    private static final String TAG = "LM:StealFocusCall";
    private int permissionCounter = 0;
    private String pendingAction;
    private final TelecomManager tm;
    private final PhoneAccountHandle handle;
    private static StealFocusCall instance;

    private static Context appContext;
    private Bundle currentCallExtras;

    StealFocusCall(Context context) {
        appContext = context;
        String appName = getApplicationName(appContext);

        handle = new PhoneAccountHandle(new ComponentName(appContext, MyConnectionService.class), appName);

        tm = (TelecomManager) appContext.getSystemService(Context.TELECOM_SERVICE);

        PhoneAccount phoneAccount;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            phoneAccount = new PhoneAccount.Builder(handle, appName)
                    .setCapabilities(PhoneAccount.CAPABILITY_SELF_MANAGED)
                    .build();
            tm.registerPhoneAccount(phoneAccount);
        }
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            phoneAccount = new PhoneAccount.Builder(handle, appName)
                    .build();
            tm.registerPhoneAccount(phoneAccount);
        }

        instance = this;
    }

    public static StealFocusCall getInstance(Context context) {
        if (instance == null) {
            instance = new StealFocusCall(context);
        }
        return instance;
    }

    public static Context getApplicationContext() {
        return appContext;
    }

    void incomingCall(Map<String, String> messagePayload) {
        Connection conn = MyConnectionService.getConnection();
        if (conn != null) {
            if (conn.getState() == Connection.STATE_ACTIVE) {
                System.out.println(TAG + " You can't receive a call right now because you're already in a call");
            } else {
                System.out.println(TAG + " You can't receive a call right now");
            }
        } else {
            currentCallExtras = new Bundle();
            for (Map.Entry<String, String> entry : messagePayload.entrySet()) {
                currentCallExtras.putString(entry.getKey(), entry.getValue());
            }
            permissionCounter = 2;
            pendingAction = "receiveCall";
            this.checkCallPermission();
        }
    }

    private void checkCallPermission() {
        if (permissionCounter >= 1) {
            PhoneAccount currentPhoneAccount = tm.getPhoneAccount(handle);
            if (currentPhoneAccount.isEnabled()) {
                if (pendingAction.equals("receiveCall")) {
                    this.receiveCall();
                }
            } else {
                if (permissionCounter == 2) {
                    Intent phoneIntent = new Intent(TelecomManager.ACTION_CHANGE_PHONE_ACCOUNTS);
                    phoneIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    appContext.startActivity(phoneIntent);
                } else {
                    System.out.println(TAG + " You need to accept phone account permissions in order to send and receive calls");
                }
            }
        }
        permissionCounter--;
    }

    private void receiveCall() {
        tm.addNewIncomingCall(handle, currentCallExtras);
        permissionCounter = 0;
        System.out.println(TAG + " Incoming call successful");
    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }
}
