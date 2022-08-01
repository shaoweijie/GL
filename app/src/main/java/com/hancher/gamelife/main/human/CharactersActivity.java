package com.hancher.gamelife.main.human;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.lifecycle.ViewModelProviders;

import com.hancher.common.base.mvvm02.BaseActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.CharactersActivityBinding;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/26 0026 9:30 <br/>
 * 描述 : 人物列表
 */
public class CharactersActivity extends BaseActivity<CharactersActivityBinding> {

    CharactersAdapter adapter = new CharactersAdapter(new ArrayList<>(), this);
    CharactersViewModel viewModel;
    final static int COL = 5;

    @Override
    protected void initView() {
        ToolBarUtil.initToolbar(binding.superToolbar, this, true);
        RecyclerViewUtil.initGrid(this, binding.recyclerview, adapter, null, false, COL);
        binding.searchView.setVoiceSearch(false);
    }

    @Override
    protected void initListener() {
        binding.btnFloat.setOnClickListener(v -> {
            Intent edit = new Intent(this, CharacterEditActivity.class);
            Bundle ani = ActivityOptions.makeSceneTransitionAnimation(this, binding.btnFloat,
                    "btn_float").toBundle();
            startActivity(edit, ani);
        });
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(this, CharacterEditActivity.class);
            CharactersAdapter.CharacterItem character = adapter.getData().get(position);
            if (character.getItemType() == CharactersAdapter.FOOTER){
                return;
            }
            intent.putExtra(CharacterEditActivity.RECORD_UUID, character.getUuid());
            startActivity(intent);
        });
        binding.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.queryCharacters(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() < 3) {
                    return false;
                }
                viewModel.queryCharacters(newText);
                return true;
            }
        });
        viewModel.getCharacters().observe(this, characters -> {
            LogUtil.d("更新:" + characters.size());
            if (characters.size() % COL == 0){
                characters.add(new CharactersAdapter.CharacterItem().setItemType(CharactersAdapter.FOOTER));
            }
            RecyclerViewUtil.update(adapter, characters);
        });
    }

    @Override
    protected void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CharactersViewModel.class);
    }

    @Override
    protected void initData() {
        viewModel.queryCharacters("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MaterialSearchView searchView = findViewById(R.id.search_view);
        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchView.setMenuItem(menu.findItem(R.id.menu_search));
        return true;
    }

}
