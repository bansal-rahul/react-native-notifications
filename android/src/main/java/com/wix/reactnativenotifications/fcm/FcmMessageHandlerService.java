package com.wix.reactnativenotifications.fcm;

import android.os.Bundle;
import android.util.Log;
import java.util.Map;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wix.reactnativenotifications.core.notification.IPushNotification;
import com.wix.reactnativenotifications.core.notification.PushNotification;

import static com.wix.reactnativenotifications.Defs.LOGTAG;

public class FcmMessageHandlerService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Log.v(LOGTAG, "onMessageReceived called");
        Bundle bundle = remoteMessageToBundle(message);
        Log.v(LOGTAG, "New message from FCM:");

        try {
            final IPushNotification notification = PushNotification.get(getApplicationContext(), bundle);
            notification.onReceived();
        } catch (IPushNotification.InvalidNotificationException e) {
            // A GCM message, yes - but not the kind we know how to work with.
            Log.v(LOGTAG, "FCM message handling aborted", e);
        }
    }

    // helper method
    public Bundle remoteMessageToBundle(RemoteMessage remoteMessage) {
        Log.v(LOGTAG, "remoteMessageToBundle called");
        final Bundle properties = new Bundle();
        final RemoteMessage.Notification notification = remoteMessage.getNotification();

        if (notification != null) {
            properties.putString("title", notification.getTitle());
            properties.putString("body", notification.getBody());
        }

        final Map<String, String> data = remoteMessage.getData();

        if (data != null) {
            final Bundle dataBundle = new Bundle();

            for (final Map.Entry<String, String> entry : data.entrySet()) {
                dataBundle.putString(entry.getKey(), entry.getValue());
            }

            properties.putBundle("data", dataBundle);
        }

        return properties;
    }
}
