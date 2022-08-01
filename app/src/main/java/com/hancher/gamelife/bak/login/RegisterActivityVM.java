package com.hancher.gamelife.bak.login;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.SoftKeyboardUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.RegisterActivityBinding;

public class RegisterActivityVM extends VMBaseActivity<RegisterActivityBinding, RegisterViewModel>
        implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.loginBtn.setOnClickListener(this);
        binding.delUsernameBtn.setOnClickListener(this);

        binding.loginInputUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length()!=0){
                    binding.delUsernameBtn.setVisibility(View.VISIBLE);
                }else{
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

        viewModel.getState().observe(this, loginState -> {
            switch (loginState) {
                case EMPTY_USERNAME:
                    binding.loginInputUsername.setError(getString(R.string.error_field_required));
                    binding.loginInputUsername.requestFocus();
                    break;
                case INCALID_PASSWORD:
                    binding.loginInputPassword.setError(getString(R.string.error_invalid_password));
                    binding.loginInputPassword.requestFocus();
                    break;
                case INCALID_REPASSWORD:
                    binding.reloginInputPassword.setError("两次密码输入不一致");
                    binding.reloginInputPassword.requestFocus();
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
                    setResult(Activity.RESULT_OK);
                    RegisterActivityVM.this.finish();
                    break;
            }
        });
        viewModel.getStateMessage().observe(this, ToastUtil::show);
    }

//    @Override
//    protected boolean isNeedLogin() {
//        return false;
//    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login_btn) {
            viewModel.attemptRegister(binding.loginInputUsername.getText().toString(),
                    binding.loginInputPassword.getText().toString(),
                    binding.reloginInputPassword.getText().toString());
        } else if (id == R.id.del_username_btn) {
            binding.loginInputUsername.setText("");
        } else if (id == R.id.del_passwd_btn) {
            binding.loginInputPassword.setText("");
        }
    }

    public void showProgress(final boolean show) {
        binding.loginBtn.animate().setDuration(500).alpha(show ? 0 : 1)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (binding.loginBtn != null) {
                            binding.loginBtn.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    }
                });

        binding.progressBar.animate().setDuration(500).alpha(show ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public String[] getPermission() {
        return new String[]{Manifest.permission.READ_PHONE_STATE};
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding.loginBtn != null) {
            binding.loginBtn.clearAnimation();
        }
    }
}