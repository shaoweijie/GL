采用 mvvm 第二种方案（实际上是一种mvp模式）

android/app/src/main/java/com/hancher/gamelife/main/me/BackupActivity.java
继承
android/common/src/main/java/com/hancher/common/base/BaseActivity.java
反射泛型 ViewBinding（因为布局必须要写）

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