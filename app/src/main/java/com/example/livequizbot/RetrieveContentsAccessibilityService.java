package com.example.livequizbot;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class RetrieveContentsAccessibilityService extends AccessibilityService {
    private final static String TAG = "DUMP";


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return;
        }
        Log.i("Event", "+++++++++++++++"+ event.toString());
        Log.i("Source", source.toString());
/*
        try {
            runShellCommand("chmod +777 adb");
            Log.i(TAG, "Command executed adb");

            Toast.makeText(this, "Executed adb", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed adb", Toast.LENGTH_SHORT).show();
        }
        try {
            runShellCommand("chmod +777 exec-out");
            Log.i(TAG, "Command executed exec-out");

            Toast.makeText(this, "Executed exec-out", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed exec-out", Toast.LENGTH_SHORT).show();
        }
*/
        try {
            runShellCommand("uiautomator dump /storage/emulated/0/LQB/xmls");
            Log.i(TAG, "Command executed");

            Toast.makeText(this, "Executed", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed adb", Toast.LENGTH_SHORT).show();
        }

    }

    private void runShellCommand(String command) throws Exception {
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;

        info.packageNames = new String[]
                {"com.bendingspoons.live.quiz"};

        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

        info.flags = AccessibilityServiceInfo.DEFAULT;
        setServiceInfo(info);

    }

    @Override
    public void onInterrupt() {

    }
}
