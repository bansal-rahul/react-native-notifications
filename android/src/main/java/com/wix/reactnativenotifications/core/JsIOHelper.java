package com.wix.reactnativenotifications.core;

import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import static com.wix.reactnativenotifications.Defs.LOGTAG;

public class JsIOHelper {
    public boolean sendEventToJS(String eventName, Bundle data, ReactContext reactContext) {
        if (reactContext != null) {
            Log.v(LOGTAG, "event- " + eventName);
            sendEventToJS(eventName, Arguments.fromBundle(data), reactContext);
            return true;
        }
        return false;
    }

    public boolean sendEventToJS(String eventName, WritableMap data, ReactContext reactContext) {
        if (reactContext != null) {
            Log.v(LOGTAG, "event: " + eventName);
            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, data);
            return true;
        }
        return false;
    }
}
