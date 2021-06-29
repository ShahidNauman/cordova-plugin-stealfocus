# cordova-plugin-stealfocus
A Cordova plugin enables a Cordova application to steal focus when sent to the background.

To steal focus send a silent push message with a payload containing the string "StealFocus=true".

## API
Forcefully awakes the device.
```javascript
forceAwake: function (success, failure)
```

Undo forceful awake of the device. Resets to default.
```javascript
undoForceAwake: function (success, failure)
```

## Classes

### Constants.java
This class contains all the constants.

### MyConnectionService.java
This class extends ConnectionService to provide the native Phone Call experience.

### PushService.java
The class is responsible for receiving silent Push Messages, and verifying if it is meant to steal the focus.

On receiving the Push Message, this class checks for the string "StealFocus=true".
If it is found, it asks to initiate the StealFocus Call.

### ScreenHelper.java
This class is responsible for enabling/disabling Force Awake of the device, and managing WakeLocks.

### StealFocus.java
This class is the entry point of the plugin.

### StealFocusCall.java
This class is responsible for showing the incoming call banner to accepting/rejecting a call.