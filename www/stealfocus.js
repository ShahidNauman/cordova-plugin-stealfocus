const exec = require('cordova/exec');

module.exports = {
    enable: function (success, failure) {
        exec(success, failure, "StealFocus", "enable", []);
    },
    disable: function (success, failure) {
        exec(success, failure, "StealFocus", "disable", []);
    },
    onPushReceived: function (success, failure) {
        exec(success, failure, "StealFocus", "onPushReceived", []);
    },
    isAppInForeground: function (success, failure) {
        exec(success, failure, "StealFocus", "isAppInForeground", []);
    },
    isScreenLocked: function (success, failure) {
        exec(success, failure, "StealFocus", "isScreenLocked", []);
    },
    bringAppToForeground: function (success, failure) {
        exec(success, failure, "StealFocus", "bringAppToForeground", []);
    },
    unlockScreen: function (success, failure) {
        exec(success, failure, "StealFocus", "unlockScreen", []);
    },
    lockScreen: function (success, failure) {
        exec(success, failure, "StealFocus", "lockScreen", []);
    },
    onScreenLocked: function (success, failure) {
        exec(success, failure, "StealFocus", "onScreenLocked", []);
    },
};
