package com.hancher.gamelife.bak.login;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.hancher.common.base.mvvm02.BaseViewModel;
import com.hancher.common.utils.endurance.PreferenceUtil;
import com.hancher.common.utils.java.DecryptUtil;
import com.hancher.gamelife.api.HancherUserApi;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/11 9:17 <br/>
 * 描述 : 登录操作
 */
public class LoginViewModel extends BaseViewModel {

    public enum LoginState {
        INCALID_PASSWORD, EMPTY_USERNAME, REQUEST_SUCCESS, REQUEST_ERROR, REQUEST_FAIL, START_REQUEST
    }

    public class LoginEvent {
        public LoginEvent(LoginState loginState, String message) {
            this.message = message;
            this.loginState = loginState;
        }

        String message;
        LoginState loginState;
    }

    private MutableLiveData<LoginEvent> state = new MutableLiveData<>();

    public MutableLiveData<LoginEvent> getState() {
        return state;
    }

    public void attemptLogin(String userName, String password) {
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            EventBus.getDefault().post(new LoginEvent(LoginState.INCALID_PASSWORD,
                    "密码为空，或者小于6位"));
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            EventBus.getDefault().post(new LoginEvent(LoginState.INCALID_PASSWORD,
                    "用户名为空"));
            return;
        }
        EventBus.getDefault().post(new LoginEvent(LoginState.START_REQUEST, "验证中"));
        Disposable d = HancherUserApi.login(userName, DecryptUtil.encrypt2(password))
                .subscribe(userResultBean -> {
                    if (userResultBean.isOK()) {
                        PreferenceUtil.setSerializable("SETTING_USER", userResultBean.getData());
                        state.setValue(new LoginEvent(LoginState.REQUEST_SUCCESS,
                                "登陆成功"));
                    } else {
                        state.setValue(new LoginEvent(LoginState.REQUEST_FAIL, "登录失败:"
                                + userResultBean.getMessage()));
                    }
                }, throwable -> state.setValue(new LoginEvent(LoginState.REQUEST_ERROR,
                        "登录错误:" + throwable.getMessage())));
        addDisposable(d);
    }

}
