采用 mvvm 第三种方案

android/app/src/main/java/com/hancher/gamelife/test/MVVM2TestActivity.java
继承
android/common/src/main/java/com/hancher/common/base/mvvm03/BaseVmActivity.java
如果写多个vm或者不写vm建议继承以下
android/common/src/main/java/com/hancher/common/base/mvvm03/BaseActivity.java

## BaseVmActivity && BaseActivity
反射泛型 ViewDataBinding（因为布局必须要写）
反射泛型 ViewModel 自动创建，Google将单例修改为了对象属性，切记因此发生的改变

## create预留覆写方法
initViewModel();
initView();
initListener();
## resume预留覆写方法
initPermission();
initData();

考虑permission是否需要移除，因为有Splash界面；
但是有些危险权限，需要跳转到设置，不方便第一次登陆就去授权。

## viewmodel调用
```java
    @Override
    protected void initViewModel() {
        backupViewModel = ViewModelProviders.of(this).get(BackupViewModel.class);
    }
```