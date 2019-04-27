package com.example.livequizbot;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private Button btn;
    private Switch swcActivatePermission;

    private Switch swcActivateService;
    private TextView tvAllowSystemAlertWindow;

    private final int OVERLAY_PERMISSION_REQ_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        //  Seek Settings.ACTION_MANAGE_OVERLAY_PERMISSION - required on Marshmallow & above
        swcActivatePermission = findViewById(R.id.swc_activate_permission);
        swcActivatePermission.setChecked(canShowOverlays());
        swcActivatePermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allowSystemAlertWindow();
            }
        });

        tvAllowSystemAlertWindow = findViewById(R.id.tv_allow_system_alert_window);

        //  Takes the user to the Accessibility Settings activity.
        //  Here, you can enable/disable EditMyNavbar service
        //  swcActivateService = findViewById(R.id.sw)
        swcActivateService = findViewById(R.id.swc_activate_service);
        swcActivateService.setChecked(Utils.serviceActive);
        swcActivateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 63342);
        }


        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i;
                for (i = 0; i < recentTasks.size(); i++)
                {
                    Log.i("Executed app", "Application executed : " +recentTasks.get(i).baseActivity.toShortString()+ "\t\t ID: "+recentTasks.get(i).id+"");
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean canShowOverlays() {
        Log.i(TAG, "------------------------------->Entrato1");
        return Settings.canDrawOverlays(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void allowSystemAlertWindow() {
        Log.i(TAG, "------------------------------->Entrato1");
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
    }
}
