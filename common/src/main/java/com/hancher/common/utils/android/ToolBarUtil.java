package com.hancher.common.utils.android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/12 0012 21:55 <br/>
 * 描述 : Toolbar工具，复用Activity中的代码
 */
public class ToolBarUtil {
    public static void initToolbar(Toolbar toolbar, AppCompatActivity activity, boolean showBack){
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(showBack);
        activity.getSupportActionBar().setHomeButtonEnabled(showBack);
    }
}
