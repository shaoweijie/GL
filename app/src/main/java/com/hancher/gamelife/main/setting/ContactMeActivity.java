package com.hancher.gamelife.main.setting;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.hancher.common.base.mvvm02.BaseActivity;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.ImageUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.PathUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.endurance.PreferenceUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.ContactMeActivityBinding;
import com.permissionx.guolindev.PermissionX;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import io.reactivex.functions.Consumer;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/18 0018 18:16 <br/>
 * 描述 : 邮件联系我
 */
public class ContactMeActivity extends BaseActivity<ContactMeActivityBinding> {
    private static final String MAIL_TIME = "mail_send_tile";
    private static final long DAY = 24 * 60 * 60 * 1000L;

    @Override
    protected void initListener() {
        binding.imageReward.setOnClickListener(v -> {
            // 读写权限
            PermissionX.init(this)
                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                    .onExplainRequestReason((scope, deniedList) -> scope.showRequestReasonDialog(
                            deniedList, "即将重新申请的权限是程序必须依赖的权限",
                            "我已明白", "取消"))
                    .request((allGranted, grantedList, deniedList) -> {
                        if (!allGranted) {
                            ToastUtil.show("您拒绝了如下权限："+ deniedList);
                            return;
                        }
                        try {
                            Drawable drawable = getResources().getDrawable(R.drawable.reward);
                            Bitmap bitmap = ImageUtil.drawable2Bitmap(drawable);
                            String path = ImageUtil.saveBitmapPng(bitmap, PathUtil.sdDownload);
                            ImageUtil.notifyImage(ContactMeActivity.this, path);
                            ToastUtil.show("打赏图片保存成功");
                        } catch (Exception exception){
                            LogUtil.e("保存打赏图片失败：", exception);
                        }
                    });

        });
        binding.btnFloat.setOnClickListener(v -> {
            if (System.currentTimeMillis()-PreferenceUtil.getLong(MAIL_TIME,0)< DAY){
                ToastUtil.show("每天只能发送一次，谢谢支持！");
                return;
            }
            LogUtil.d("时间差为："+(System.currentTimeMillis()-PreferenceUtil.getLong(MAIL_TIME,0)));
            String title = binding.editTitle.getText().toString();
            String content = binding.editContent.getText().toString();
            if (TextUtil.isEmpty(title) || TextUtil.isEmpty(content)){
                ToastUtil.show("标题或者内容为空，请检查后再发送");
                return;
            }
            title="【GameLife问题反馈】"+title;
            content="\n\n"+content+"\n\n";
            String finalTitle = title;
            String finalContent = content;
            AsyncUtils.run(emitter -> {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.163.com"); //从哪个服务器发出去的, key不变
                Session session = Session.getInstance(props, null);
                try {
                    MimeMessage msg = new MimeMessage(session);
                    msg.setFrom("ytu_shaoweijie@163.com");//发送的邮箱账号
                    msg.setRecipients(Message.RecipientType.TO, "ytu_shaoweijie@163.com");//接受的邮箱账号
                    msg.setSubject(finalTitle);//标题
                    msg.setSentDate(new Date());//时间
                    msg.setText(finalContent);//内容
                    Transport.send(msg, "ytu_shaoweijie@163.com", "CGYQWKJWDIJNKXYS");
                    emitter.onNext(true);
                } catch (MessagingException mex) {
                    LogUtil.e("发送邮件失败", mex);
                    emitter.onNext(false);
                }
                emitter.onComplete();
            }, (Consumer<Boolean>) result -> {
                ToastUtil.show("发送邮件"+(result?"成功":"失败"));
                if (result){
                    LogUtil.d("保存发送时间");
                    PreferenceUtil.setValue(MAIL_TIME,System.currentTimeMillis());
                }
            });
        });
    }
}
