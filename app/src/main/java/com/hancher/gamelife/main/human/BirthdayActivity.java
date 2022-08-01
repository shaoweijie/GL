package com.hancher.gamelife.main.human;

import android.content.Intent;

import androidx.lifecycle.ViewModelProviders;

import com.hancher.common.base.mvvm02.BaseActivity;
import com.hancher.common.utils.android.BaseAdapterUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.gamelife.databinding.BirthdayActivityBinding;

/**
 * 生日列表
 */
public class BirthdayActivity extends BaseActivity<BirthdayActivityBinding> {

    private BirthdayAdapter adapter = new BirthdayAdapter();
    CharactersViewModel viewModel ;

    @Override
    protected void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CharactersViewModel.class);
    }

    @Override
    protected void initView() {
        ToolBarUtil.initToolbar(binding.superToolbar, this, true);
        RecyclerViewUtil.initLine(this, binding.recyclerview, adapter, null, false);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener((adapter, view, position) -> {
            LogUtil.i(position, "/", adapter.getData().size());
            BirthdayAdapter.BirthdayItem data = (BirthdayAdapter.BirthdayItem) adapter.getData().get(position);
            Intent intent = new Intent(BirthdayActivity.this, CharacterEditActivity.class);
            intent.putExtra(CharacterEditActivity.RECORD_UUID, data.getUuid());
            BirthdayActivity.this.startActivity(intent);
        });
        viewModel.getCharacterList().observe(this,
                characters -> BaseAdapterUtil.update(adapter, characters));
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.queryCharacterList();
    }

}