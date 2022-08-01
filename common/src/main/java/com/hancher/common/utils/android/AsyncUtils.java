package com.hancher.common.utils.android;

import android.app.ProgressDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：Hancher
 * 时间：2020/2/1.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <p>
 * 说明：
 */
public class AsyncUtils {
    private static final String runStart = "异步任务开始>>>";
    private static final String runEnd = "异步任务完成<<<";

    /**
     * 异步执行任务
     *
     * @param observable 被观察者
     * @param observer   观察者（内涵四个方法）
     */
    public static <T> void run(ObservableOnSubscribe<T> observable, Observer<T> observer) {
        Observable.create(observable)
                .compose(getThreadTransformer())
                .subscribe(observer);
    }

    public static <T> Disposable run(ObservableOnSubscribe<T> observable,
                                     Consumer<? super T> onNext){
        return run(observable,
                (disposable) -> LogUtil.v(runStart),
                onNext,
                () -> LogUtil.v(runEnd),
                LogUtil::e);
    }
    public static <T> Disposable run(ObservableOnSubscribe<T> observable,
                                     Consumer<? super T> onNext,
                                     Consumer<? super Throwable> onError){
        return run(observable,
                (disposable) -> LogUtil.v(runStart),
                onNext,
                () -> LogUtil.v(runEnd),
                onError);
    }
    public static <T> Disposable runWithWaitDialog(ObservableOnSubscribe<T> observable,
                                     Consumer<? super T> onNext,
                                    ProgressDialog waitingDialog){
        return run(observable,
                (disposable) -> {
                    LogUtil.v(runStart);
                    waitingDialog.setTitle("任务进行中");
                    waitingDialog.setMessage("请稍后...");
                    waitingDialog.setIndeterminate(true);
                    waitingDialog.setCancelable(false);
                    waitingDialog.show();
                },
                onNext,
                () -> {
                    LogUtil.v(runEnd);
                    waitingDialog.dismiss();
                },
                throwable -> {
                    waitingDialog.dismiss();
                    LogUtil.e(throwable);
                });
    }
    /**
     * 异步执行任务
     * @param observable
     * @param onNext
     * @param onError
     * @param onCompletes
     * @param onSubscribe
     * @param <T>
     */
    public static <T> Disposable run(ObservableOnSubscribe<T> observable,
                               Consumer<? super Disposable> onSubscribe,
                               Consumer<? super T> onNext,
                               Action onCompletes,
                               Consumer<? super Throwable> onError){
        return Observable.create(observable)
                .compose(getThreadTransformer())
                .subscribe(onNext, onError, onCompletes, onSubscribe);
    }

    /**
     * 异步执行倒计时任务
     * @param millis
     * @param obsever
     */
    public static void runCountdown(Long millis, Observer<Long> obsever){
        /**
         * start：起始数值
         * count：发射数量
         * initialDelay：延迟执行时间
         * period：发射周期时间
         * unit：时间单位
         */
        Observable.intervalRange(0, 1, millis, millis, TimeUnit.MILLISECONDS)
                .compose(getThreadTransformer())
                .subscribe(obsever);
    }

    /**
     * 倒计时
     * @param start 开始值
     * @param count 个数
     * @param delay 延迟
     * @param period 间隔周期
     * @param onNext 下一步
     * @param onCompletes 完成
     * @return 取消
     */
    public static Disposable runCountdown(Long start,
                                          Long count,
                                          Long delay,
                                          Long period,
                                          Consumer<? super Long> onNext,
                                          Action onCompletes) {
        /**
         * start：起始数值
         * count：发射数量
         * initialDelay：延迟执行时间
         * period：发射周期时间
         * unit：时间单位
         */
        return Observable.intervalRange(start, count, delay, period, TimeUnit.MILLISECONDS)
                .compose(getThreadTransformer())
                .subscribe(onNext, LogUtil::e, onCompletes, (disposable) -> LogUtil.v(runStart));
    }
    public static Disposable runCountdown(Long start,
                                          Long count,
                                          Long delay,
                                          Long period,
                                          Action onCompletes) {
        /**
         * start：起始数值
         * count：发射数量
         * initialDelay：延迟执行时间
         * period：发射周期时间
         * unit：时间单位
         */
        return Observable.intervalRange(start, count, delay, period, TimeUnit.MILLISECONDS)
                .compose(getThreadTransformer())
                .subscribe(aLong -> LogUtil.d("onNext:"+aLong), LogUtil::e, onCompletes, (disposable) -> LogUtil.v(runStart));
    }
    /**
     * 异步执行心跳任务
     * @param millis
     * @param obsever
     */
    public static void runHeartbeat(Long millis, Observer<Long> obsever) {
        Observable.interval(0, millis, TimeUnit.MILLISECONDS)
                .compose(getThreadTransformer())
                .subscribe(obsever);
    }

    /**
     * 用来代替 <br/>
     * .subscribeOn(Schedulers.io()) <br/>
     * .observeOn(AndroidSchedulers.mainThread()) <br/>
     *
     * @param <T> 实体类内容
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> getThreadTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
