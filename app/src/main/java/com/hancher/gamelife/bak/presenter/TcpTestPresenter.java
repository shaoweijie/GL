package com.hancher.gamelife.bak.presenter;

import com.hancher.common.base.mvp01.BasePresenter;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.gamelife.bak.activity.TcpTestMvpActivity;
import com.hancher.gamelife.bak.contract.TcpTestContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class TcpTestPresenter extends BasePresenter<TcpTestMvpActivity> implements TcpTestContract.Presenter {

    @Override
    public void tcpConnect(String serverIp, File file) {
        AsyncUtils.run(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
//                String fileMd5 = HancherMd5Util.getFileMd5(file);
//                String header = file.getName() + "\t" + file.length() + "\t" + fileMd5;
                emitter.onNext("开始连接"+serverIp+":10002");
                Socket socket = new Socket(serverIp, 10002);
                emitter.onNext("连接服务器成功");
                OutputStream os = socket.getOutputStream();
//                emitter.onNext("开始发送文件信息");
//                os.write(header.getBytes(),0,header.getBytes().length);
//                os.write(String.valueOf(file.length()).getBytes(),0,String.valueOf(file.length()).getBytes().length);
                emitter.onNext("开始发送文件");
                FileInputStream fis = new FileInputStream(file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = fis.read(buf)) != -1){
                    os.write(buf,0,len);
                }
                os.flush();
                socket.shutdownOutput();
                os.close();
                fis.close();
                emitter.onNext("发送结束");


//                emitter.onNext("获取服务器返回信息");
//                BufferedReader br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String resultMd5 = br.readLine();
//                emitter.onNext("获取服务器返回信息成功");
//
//
//                emitter.onNext("睡眠60秒");
//                Thread.sleep(60 * 1000);
//
//                emitter.onNext("关闭客户端");
//
//                String fileMd5 = HancherMd5Util.getFileMd5(file);
//                HancherLogUtil.d("server md5:",resultMd5);
//                HancherLogUtil.d("file md5:",fileMd5);
//                if (fileMd5.equals(resultMd5)){
//                    emitter.onNext(fileMd5);
//                } else {
//                    emitter.onNext("");
//                }
                socket.close();
            }
        }, new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                LogUtil.d("链接信息:",s);
                mView.changeLogText(s.length() > 0 ? s : "发送失败");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                ToastUtil.showErr(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
