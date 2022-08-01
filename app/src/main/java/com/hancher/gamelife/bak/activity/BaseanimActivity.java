package com.hancher.gamelife.bak.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hancher.common.utils.android.ToastUtil;
import com.hancher.gamelife.R;

public class BaseanimActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseanim);
        getSupportActionBar().setTitle("测试动画");
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        imageView10 = findViewById(R.id.imageView10);
        imageView10.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scaleanim);
                imageView10.startAnimation(scaleAnimation);
                break;
            case R.id.button2:
                doAnimation();
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
            case R.id.imageView10:
                ToastUtil.show("点击");
                break;
        }
    }
    private void doAnimation(){
        ValueAnimator animator = ValueAnimator.ofInt(0,400);
        animator.setDuration(1000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                imageView10.layout(curValue,curValue,curValue+imageView10.getWidth(),curValue+imageView10.getHeight());
            }
        });
        animator.start();
    }
}