package com.dorvis.activityrecognition;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

public class DetectedActivitiesReceiver extends BroadcastReceiver {

    protected static final String TAG = DetectedActivitiesReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() triggered with intent: " + intent);
        if (intent == null) return;
        
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        if (result == null) {
            Log.d(TAG, "onReceive() NO ActivityRecognitionResult in intent");
            return;
        }
        Log.d(TAG, "onReceive() extracted result: " + result);

        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();

        for (DetectedActivity activity : detectedActivities) {
            Log.i(TAG, "Detected activity: " + activity.getType() + ", " + activity.getConfidence());
            broadcastActivity(context, activity);
        }
    }

    private void broadcastActivity(Context context, DetectedActivity activity) {
        Intent intent = new Intent(Constants.BROADCAST_DETECTED_ACTIVITY);
        intent.setPackage(context.getPackageName());
        intent.putExtra("type", activity.getType());
        intent.putExtra("confidence", activity.getConfidence());
        context.sendBroadcast(intent);
    }
}
