package com.hancher.gamelife.main.note;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.BaseAdapterUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.ActivityNoteBinding;
import com.hancher.gamelife.greendao.DiaryDao;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/28 0028 20:16 <br/>
 * 描述 : 笔记列表
 */
public class NoteListActivityVM extends VMBaseActivity<ActivityNoteBinding, NoteViewModel> {

    private NoteAdapter adapter = new NoteAdapter(null);
    public int page = 1;
    public static final int NUMBER_OF_PAGE= 30;
    public String filiter = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initListener() {
        binding.btnFloat.setOnClickListener(v -> {
            Intent intent = new Intent(NoteListActivityVM.this, NoteRichEditActivityVM.class);
            startActivity(intent);
        });
        adapter.setOnItemClickListener(((adapter1, view, position) -> {
            NoteAdapter.NoteItem data = adapter.getData().get(position);
            LogUtil.i(position, "/",adapter.getData().size(),", uuid="+data.getUuid());
            Intent intent = new Intent(NoteListActivityVM.this, NoteRichEditActivityVM.class);
            intent.putExtra(DiaryDao.Properties.Uuid.columnName, data.getUuid());
            NoteListActivityVM.this.startActivity(intent);
        }));
        adapter.setOnItemLongClickListener(((adapter1, view, position) -> {
            LogUtil.i(position, "/",adapter.getData().size());
            NoteAdapter.NoteItem data = adapter.getData().get(position);
            StringBuffer content = new StringBuffer();
            content.append(data.getTitle()).append( "\n");
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage(content)
                    .setPositiveButton("删除", (dialog1, which) -> {
                        LogUtil.i("当前删除项:",data.getUuid());
                        viewModel.deleteItem(data.getUuid(), page);
                    })
                    .create();
            dialog.show();
            return true;
        }));
        adapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            if (adapter.getData()== null || adapter.getData().size()<NUMBER_OF_PAGE){
                adapter.getLoadMoreModule().loadMoreEnd();
                LogUtil.d("列表到底，不满一页不在加载");
                return;
            }
            LogUtil.i("列表到底，开始加载更多");
            viewModel.queryData(filiter, page++);
        });
        binding.swipeRefresh.setOnRefreshListener(() -> {
            page = 1;
            viewModel.queryData(filiter, page++);
        });
        viewModel.getNotes().observe(this,noteItems -> {
            BaseAdapterUtil.update(adapter, noteItems);
            if (binding.swipeRefresh.isRefreshing()) {
                binding.swipeRefresh.setRefreshing(false);
            }
        });
        binding.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filiter = query;
                LogUtil.d("filiter:"+filiter);
                viewModel.queryData(filiter, page++);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length()<3){
                    return false;
                }
                filiter = newText;
                LogUtil.d("filiter:"+filiter);
                viewModel.queryData(filiter, page++);
                return true;
            }
        });
    }

    private void initView() {
        ToolBarUtil.initToolbar(binding.superToolbar, this, true);
        RecyclerViewUtil.initLine(this,binding.recyclerview,adapter,binding.swipeRefresh,true);
        binding.searchView.setVoiceSearch(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.queryData(filiter, page++);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        binding.searchView.setMenuItem(menu.findItem(R.id.menu_search));
        return true;
    }
}