package com.hancher.gamelife.bak.login;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hancher.common.utils.android.PhoneInfoUtil;
import com.hancher.common.utils.endurance.PreferenceUtil;
import com.hancher.common.utils.java.DecryptUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.api.HancherUserApi;

import io.reactivex.disposables.Disposable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/11 9:17 <br/>
 * 描述 : 登录操作
 */
public class RegisterViewModel extends ViewModel {
    private Disposable d;

    public enum LoginState {
        INCALID_PASSWORD, EMPTY_USERNAME, REQUEST_SUCCESS, REQUEST_ERROR, REQUEST_FAIL, INCALID_REPASSWORD, START_REQUEST
    }

    private MutableLiveData<LoginState> state = new MutableLiveData<>();
    private MutableLiveData<String> stateMessage = new MutableLiveData<>();

    public MutableLiveData<String> getStateMessage() {
        return stateMessage;
    }

    public MutableLiveData<LoginState> getState() {
        return state;
    }

    public void attemptRegister(String userName, String password, String rePassword) {
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            state.setValue(LoginState.INCALID_PASSWORD);
            return;
        }
        if (!password.equals(rePassword)) {
            state.setValue(LoginState.INCALID_REPASSWORD);
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            state.setValue(LoginState.EMPTY_USERNAME);
            return;
        }
        String imei = PhoneInfoUtil.getDeviceImei();
        if (TextUtil.isEmpty(imei)) {
            stateMessage.setValue("获取IMEI失败，请保证SIM卡可用，并赋予读取权限");
            return;
        }
        state.setValue(LoginState.START_REQUEST);
        d = HancherUserApi.register(userName, DecryptUtil.encrypt2(password), imei)
                .subscribe(userResultBean -> {
                    if (userResultBean.isOK()) {
                        state.setValue(LoginState.REQUEST_SUCCESS);
                        PreferenceUtil.setSerializable("SETTING_USER", userResultBean.getData());
                        stateMessage.setValue("注册成功");
                    } else {
                        state.setValue(LoginState.REQUEST_FAIL);
                        stateMessage.setValue("注册失败:" + userResultBean.getMessage());
                    }
                }, throwable -> {
                    state.setValue(LoginState.REQUEST_ERROR);
                    stateMessage.setValue("注册错误:" + throwable.getMessage());
                });
    }

    @Override
    protected void onCleared() {
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
        super.onCleared();
    }
}
