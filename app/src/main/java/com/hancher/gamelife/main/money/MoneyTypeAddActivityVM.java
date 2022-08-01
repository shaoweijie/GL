package com.hancher.gamelife.main.money;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.AssetUtil;
import com.hancher.common.utils.android.ImageUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.databinding.ColockInTypeAddActivityBinding;
import com.hancher.gamelife.greendao.ColockInType;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/17 0017 21:09 <br/>
 * 描述 : 添加打卡类型
 */
public class MoneyTypeAddActivityVM extends VMBaseActivity<ColockInTypeAddActivityBinding, MoneyViewModel> {
    ColockInType type;
    private static final String TITLE = "title";
    private static final String IMAGE = "image";
    private MaterialDialog progress;
    private MoneyTypeAdapter adapter = new MoneyTypeAdapter();

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE, type.getTitle());
        outState.putString(IMAGE, type.getImage());
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = new ColockInType();
        if (savedInstanceState!=null) {
            type.setTitle(savedInstanceState.getString(TITLE, ""));
            type.setImage(savedInstanceState.getString(IMAGE, ""));
        }
        if (TextUtil.isEmpty(type.getImage())){
            Bitmap bitmap = AssetUtil.getImage(this, "colockintype"+ File.separator+"colock_in.png");
            String image = ImageUtil.bitmap2String(bitmap);
            type.setImage(image);
            LogUtil.d("设置默认image");
        }
        RecyclerViewUtil.initGrid(this, binding.recyclerview, adapter, binding.swipeRefresh, false, 5);
        ToolBarUtil.initToolbar(binding.superToolbar,this,true);
        initObseve();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.readAllTypeIcons();
    }

    private void initObseve() {
        binding.btnFloat.setOnClickListener(v -> {
            progress = new MaterialDialog.Builder(MoneyTypeAddActivityVM.this)
                    .content("保存中...")
                    .show();
            // 检查标题是否为空
            if (TextUtil.isEmpty(type.getTitle())){
                LogUtil.w("设置默认打卡标题失败");
                ToastUtil.show("设置默认打卡标题失败");
                progress.dismiss();
                return;
            }
            viewModel.saveType(type);
        });
        binding.editType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                type.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewModel.getState().observe(this, state -> {
            if (state == MoneyViewModel.STATE.SAVE_FINISH) {
                if (progress != null && progress.isShowing()) {
                    progress.dismiss();
                }
                finish();
            }
        });
        viewModel.getIcons().observe(this,bitmaps -> {
            RecyclerViewUtil.update(adapter,bitmaps);
        });
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Bitmap bitmap = (Bitmap) adapter.getItem(position);
            String image = ImageUtil.bitmap2String(bitmap);
            type.setImage(image);
            binding.imageSelect.setImageBitmap(bitmap);
        });
    }

    @Override
    protected void onDestroy() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
        super.onDestroy();
    }
}
