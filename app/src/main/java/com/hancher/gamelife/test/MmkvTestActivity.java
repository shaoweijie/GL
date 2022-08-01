package com.hancher.gamelife.test;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hancher.gamelife.R;
import com.tencent.mmkv.MMKV;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2022/2/8 0008 21:55 <br/>
 * 描述 : MMKV
 * https://github.com/Tencent/MMKV
 * implementation 'com.tencent:mmkv:1.0.10'
 *
 * String rootDir = MMKV.initialize(this);
 * System.out.println("mmkv root: " + rootDir);
 *
 * import com.tencent.mmkv.MMKV;
 * MMKV kv = MMKV.defaultMMKV();
 * kv.encode("bool", true);                 boolean bValue = kv.decodeBool("bool");
 * kv.encode("int", Integer.MIN_VALUE);     int iValue = kv.decodeInt("int");
 * kv.encode("string", "Hello from mmkv");  String str = kv.decodeString("string");
 */
public class MmkvTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmkv_test);
        MMKV kv = MMKV.defaultMMKV();
        kv.encode("test", "Hello from mmkv");
        String str = kv.decodeString("test");
        TextView textView = findViewById(R.id.textView15);
        textView.setText(str);
    }
}