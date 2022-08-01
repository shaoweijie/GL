package com.hancher.gamelife.bak.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.databinding.SplashActivityBinding;
import com.hancher.gamelife.main.GameLifeMvpActivity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/11 15:33 <br/>
 * 描述 : 开屏界面
 */
public class SplashActivityVM extends VMBaseActivity<SplashActivityBinding, RegisterViewModel> {

    private Disposable d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        d = AsyncUtils.run(emitter -> {
            for (int i = 3; i >= 0; i--) {
                emitter.onNext(i);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    LogUtil.e("sleep e:", e);
                }
            }
            emitter.onComplete();
        }, (Consumer<Integer>) integer -> {
            binding.splashCountdown.setText(integer + "秒后关闭");
            if (integer == 0) {
                SplashActivityVM.this.startActivity(new Intent(SplashActivityVM.this, GameLifeMvpActivity.class));
                SplashActivityVM.this.finish();
            }
        });
    }

//    @Override
//    protected boolean isNeedLogin() {
//        return false;
//    }

    @Override
    protected void onDestroy() {
        d.dispose();
        super.onDestroy();
    }
}
