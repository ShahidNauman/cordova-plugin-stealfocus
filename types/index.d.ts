interface StealFocus {
    // enable(success: () => void, error: () => void);
    // disable(success: () => void, error: () => void);
    // onPushReceived(success: (pushMessage: any) => void, error: () => void);
    // isAppInForeground(success: () => void, error: () => void);
    // isScreenLocked(success: () => void, error: () => void);
    // bringAppToForeground(success: () => void, error: () => void);
    forceAwake(success: () => void, error: () => void);
    undoForceAwake(success: () => void, error: () => void);
    // onScreenLocked(success: () => void, error: () => void);
}
