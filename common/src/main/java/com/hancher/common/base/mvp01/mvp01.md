# 简介
采用mvp + binding
# view
android/app/src/main/java/com/hancher/gamelife/bak/activity/ImageActivity.java
继承
android/common/src/main/java/com/hancher/common/base/v01/BaseActivity.java
反射两个class ImageActivityBinding, ImagePresenter
实现接口 ImageContract.View

## 布局
android/app/src/main/res/layout/image_activity.xml
自动生成 ImageActivityBinding

## BaseActivity
1. 检查权限
2. 统一状态栏


# presenter
android/app/src/main/java/com/hancher/gamelife/bak/presenter/ImagePresenter.java
继承
android/common/src/main/java/com/hancher/common/base/v01/BasePresenter.java
反射 ImageActivity
实现接口 ImageContract.Presenter

1. 拿到 mView 即 ImageActivity

# 接口
```java
public interface ImageContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
    }
}
```
为实现同一类界面的统一调用，例如所有的列表页都需要实现的接口，宁可无用，不能缺失。