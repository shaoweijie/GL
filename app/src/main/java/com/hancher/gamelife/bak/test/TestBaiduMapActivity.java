package com.hancher.gamelife.bak.test;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hancher.common.third.mapbaidu.BdMapManager;
import com.hancher.common.third.mapbaidu.BdMapUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.greendao.PositionDbUtil;
import com.permissionx.guolindev.PermissionX;

public class TestBaiduMapActivity extends AppCompatActivity {
    private static final String TAG = "TestBaiduMapActivity";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_baidu_map);
        LogUtil.d("start");
        tv = findViewById(R.id.textView13);
        tv.setOnClickListener(v -> PermissionX.init(this)
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted){
                        tv.setText("");
                        BdMapManager.getInstance().start(getApplicationContext(),
                                BdMapManager.START_TYPE.ONCE, location -> {
                                    tv.append(BdMapUtil.getDetailInfo(location) +"\n");
                                    PositionDbUtil.save2Db(location);
                                });
                    } else {
                        Log.d(TAG, "onCreate: no permission");
                    }
                }));
    }
}