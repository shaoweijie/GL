package com.hancher.gamelife.bak.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CallLog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hancher.gamelife.R;

public class IntentTestActivity extends AppCompatActivity {
    public IntentTestActivity() {
        super(R.layout.intent_test_activity);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.btn_test).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType(CallLog.Calls.CONTENT_TYPE);
            intent.putExtra(CallLog.Calls.EXTRA_CALL_TYPE_FILTER, CallLog.Calls.MISSED_TYPE);
            startActivity(intent);
        });
    }
}