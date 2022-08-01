package com.hancher.common.base.mvvm01;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewbinding.ViewBinding;

import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.PermissionUtil;
import com.hancher.common.utils.android.StateBarUtil;
import com.hancher.common.utils.android.ToastUtil;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/11 9:05 <br/>
 * 描述 : vmc
 * @deprecated
 */
public abstract class VMBaseActivity<B extends ViewBinding, M extends ViewModel> extends AppCompatActivity {

    public final static int PERMISSION_REQUEST_CODE = 1127;
//    public static final int LOGIN_REQUEST_CODE = 1128;
//    private static final String LOGIN_PERFERENCE_ISLOGIN = "LOGIN_PERFERENCE_ISLOGIN";
//    private static final String LOGIN_PERFERENCE_LOGINETIME = "LOGIN_PERFERENCE_LOGINETIME";
//    private static final String LOGIN_PERFERENCE_ISREMEMBER = "LOGIN_PERFERENCE_ISREMEMBER";
    protected B binding;
    protected M viewModel;
    private static List<Activity> activities = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StateBarUtil.white(this);
//        checkLoginAndJump();

        //bing 反射 200ms
        try {
            Type superclass = getClass().getGenericSuperclass();
            Class<B> aClass = (Class<B>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (B) method.invoke(null, getLayoutInflater());
            setContentView(binding.getRoot());
        } catch (Exception e) {
            LogUtil.e(e);
        }

        //viewModel 反射
        try {
            Type superclass = getClass().getGenericSuperclass();
            Class<M> mClass = (Class<M>) ((ParameterizedType) superclass).getActualTypeArguments()[1];
            viewModel = ViewModelProviders.of(this).get(mClass);
        } catch (Exception e) {
            LogUtil.e(e);
        }
//        EventBus.getDefault().register(this);
        checkAndRequestPermission();
        activities.add(this);
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        activities.remove(this);
        super.onDestroy();
    }

    public void exitApp() {
        for (Activity item : activities) {
            if (!item.equals(this)) {
                item.finish();
            }
        }
        finish();
    }

//    /**
//     * 需要登录 & 没登录 <br/>
//     * 需要登录 & 登录 & 过期 <br/>
//     * 跳转到登录页面
//     */
//    private void checkLoginAndJump() {
//        boolean isLogin = PreferenceUtil.getBoolean(LOGIN_PERFERENCE_ISLOGIN, false);
//        boolean isRemember = PreferenceUtil.getBoolean(LOGIN_PERFERENCE_ISREMEMBER, false);
//        long loginTime = PreferenceUtil.getLong(LOGIN_PERFERENCE_LOGINETIME, 0);
//        boolean isLoginExpire = !isRemember && System.currentTimeMillis() - loginTime > 8 * 60 * 60 * 1000;
//
//        Class loginActivity = BaseApplication.getInstance().getLoginActivityClass();
//        if (loginActivity == null) {
//            return;
//        }
//
//        if ((isNeedLogin() && !isLogin) || (isNeedLogin() && isLogin && isLoginExpire)) {
//            Intent intent = new Intent(this, loginActivity);
//            startActivityForResult(intent, LOGIN_REQUEST_CODE);
//        }
//    }
//
//    private void setLogin(boolean isLogin, boolean isRememberPassword) {
//        PreferenceUtil.setValue(LOGIN_PERFERENCE_ISLOGIN, isLogin);
//        PreferenceUtil.setValue(LOGIN_PERFERENCE_LOGINETIME, System.currentTimeMillis());
//        PreferenceUtil.setValue(LOGIN_PERFERENCE_ISREMEMBER, isRememberPassword);
//    }
//
//    public void setLogout() {
//        setLogin(false, false);
//    }
//
//    public void setLogin(boolean isRememberPassword) {
//        setLogin(true, isRememberPassword);
//    }
//
//    /**
//     * 可以覆写，让某些界面不需要登录
//     *
//     * @return isNeedLogin
//     */
//    protected boolean isNeedLogin() {
//        return true;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == LOGIN_REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_CANCELED) {
//                finish();
//            }
//            return;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    /**
     * 检查并请求权限
     */
    public void checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            LogUtil.w("android 6 以下不需要检验权限");
            return;
        }
        String[] permissions = getPermission();
        if (permissions == null || permissions.length == 0) {
            LogUtil.d("无权限需要验证");
            return;
        }

        for (int i = 0; i < permissions.length; i++) {
            //无权限则申请权限
            if (checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
                return;
            }
        }
    }

    /**
     * 需请求的权限<br/>
     * 例如： new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}
     *
     * @return
     */
    public String[] getPermission() {
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length == 0) {
            return;
        }
        if (requestCode == PermissionUtil.PERMISSION_REQUEST_CODE) {
            LogUtil.i("所有授权检查开始=================");
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i].startsWith("android.permission") ?
                        permissions[i].substring(19) : permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    LogUtil.i("权限 " + permission + " 授权成功！");
                } else {
                    ToastUtil.showErr("权限 " + permission + " 授权失败！");
                }
            }
            LogUtil.i("所有授权检查结束=================");
        }
    }


}
