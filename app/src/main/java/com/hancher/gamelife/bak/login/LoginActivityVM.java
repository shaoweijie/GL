package com.hancher.gamelife.bak.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.SoftKeyboardUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.LoginActivityBinding;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/23 10:04 <br/>
 * 描述 : 登录界面
 */
public class LoginActivityVM extends VMBaseActivity<LoginActivityBinding, LoginViewModel> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOnClick();
        initViewModel();
        binding.loginInputUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() != 0) {
                    binding.delUsernameBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.delUsernameBtn.setVisibility(View.GONE);
                }
            }
        });
        binding.loginInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() != 0) {
                    binding.delPasswdBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.delPasswdBtn.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initViewModel() {
        viewModel.getState().observe(this, loginEvent -> {
            switch (loginEvent.loginState) {
                case EMPTY_USERNAME:
                    binding.loginInputUsername.setError(getString(R.string.error_field_required));
                    binding.loginInputUsername.requestFocus();
                    break;
                case INCALID_PASSWORD:
                    binding.loginInputPassword.setError(getString(R.string.error_invalid_password));
                    binding.loginInputPassword.requestFocus();
                    break;
                case START_REQUEST:
                    showProgress(true);
                    SoftKeyboardUtil.hideSoftInput(this);
                    break;
                case REQUEST_FAIL:
                case REQUEST_ERROR:
                    showProgress(false);
                    break;
                case REQUEST_SUCCESS:
//                    setLogin(binding.rememberPwd.isChecked());
                    setResult(Activity.RESULT_OK);
                    LoginActivityVM.this.finish();
                    break;
            }
            ToastUtil.show(loginEvent.message);
        });
    }

//    @Override
//    protected boolean isNeedLogin() {
//        return false;
//    }

    public void initOnClick() {
        binding.loginBtn.setOnClickListener(v -> viewModel.attemptLogin(
                binding.loginInputUsername.getText().toString(),
                binding.loginInputPassword.getText().toString()));
        binding.delUsernameBtn.setOnClickListener(v -> binding.loginInputUsername.setText(""));
        binding.delPasswdBtn.setOnClickListener(v -> binding.loginInputPassword.setText(""));
        binding.forgetPwd.setOnClickListener(v -> ToastUtil.show(R.string.contact_admin));
        binding.registerPwd.setOnClickListener(v -> LoginActivityVM.this.startActivity(
                new Intent(this, RegisterActivityVM.class)));
    }

    public void showProgress(final boolean show) {
        binding.loginBtn.animate().setDuration(500).alpha(show ? 0 : 1).setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.loginBtn.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });

        binding.progressBar.animate().setDuration(500).alpha(show ? 1 : 0).setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        binding.loginBtn.clearAnimation();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title("确认退出登录？")
                .positiveText("退出")
                .negativeText("取消")
                .onPositive((dialog, which) -> {
                    dialog.dismiss();
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                })
                .show();
    }
}