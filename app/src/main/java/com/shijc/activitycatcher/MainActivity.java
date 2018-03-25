package com.shijc.activitycatcher;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shijc.activitycatcher.util.AccessibilityUtils;

public class MainActivity extends AppCompatActivity {
    private Activity context;
    private Button btnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        btnOpen = findViewById(R.id.btn_open);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessibilityUtils.checkAccessibility(MainActivity.this)) {
                    Intent intent = new Intent(context, ActivityCatcherService.class);
                    intent.putExtra(ActivityCatcherService.COMMAND, ActivityCatcherService.COMMAND_OPEN);
                    startService(intent);
                    finish();
                }

            }
        });

    }
}
