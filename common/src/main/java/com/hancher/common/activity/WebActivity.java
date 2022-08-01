package com.hancher.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvp01.BaseActivity;
import com.hancher.common.databinding.WebActivityBinding;
import com.hancher.common.utils.java.TextUtil;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;

/**
 * 使用AgentWeb显示网页
 */
public class WebActivity extends BaseActivity<WebActivityBinding> {

    public static final String URL = "URL";
    public static final String TITLE = "TITLE";
    private AgentWeb mAgentWeb;

    public static Intent getIntent(Activity activity, String url, String title) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(URL);
        if (TextUtil.isEmpty(url)) {
            url = "http://hancher57.3vhost.net";
        }

        String title = getIntent().getStringExtra(TITLE);
        if (!TextUtil.isEmpty(title)){
            binding.superToolbar.setTitle(title);
        }

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) binding.container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);

        setSupportActionBar(binding.superToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (TextUtil.isEmpty(title)){
            //获取网页的标题 , 获取标题可能导致进度条无法达到100%
            mAgentWeb.getWebCreator().getWebView().setWebChromeClient(new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    if (!TextUtils.isEmpty(title)) {
                        binding.superToolbar.setTitle(title);
                    }
                    super.onReceivedTitle(view, title);
                }
            });
        }

    }
}