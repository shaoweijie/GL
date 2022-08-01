//package com.hancher.gamelife.bak.activity;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.GridLayoutManager;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.listener.OnItemClickListener;
//import com.chad.library.adapter.base.listener.OnItemLongClickListener;
//import com.hancher.common.base.BaseActivity;
//import com.hancher.common.utils.android.LogUtil;
//import com.hancher.common.utils.android.ToastUtil;
//import com.hancher.gamelife.R;
//import com.hancher.gamelife.bak.contract.NoteContract;
//import com.hancher.gamelife.databinding.ActivityNoteBinding;
//import com.hancher.gamelife.greendao.Diary;
//import com.hancher.gamelife.greendao.DiaryDao;
//import com.hancher.gamelife.main.note.NoteAdapter;
//import com.hancher.gamelife.main.note.NoteAdapter.NoteItem;
//import com.hancher.gamelife.main.note.NotePresenter;
//import com.hancher.gamelife.main.note.NoteRichEditActivity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TableNoteActivity extends BaseActivity<ActivityNoteBinding, NotePresenter>
//        implements NoteContract.View,
//        OnItemClickListener,
////        SearchView.OnQueryTextListener,
//        OnItemLongClickListener {
//
//    public int page = 0;
//    private NoteAdapter adapter;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initList();
////        binding.textSearch.setOnQueryTextListener(this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        presenter.queryData(null, page++);
//    }
//
//    private void initList() {
//        binding.recyclerview.setLayoutManager(new GridLayoutManager(this,4));
//        adapter = new NoteAdapter(new ArrayList<>());
//        adapter.setEmptyView(R.layout.empty);
//        adapter.setAnimationEnable(true);
//        binding.recyclerview.setAdapter(adapter);
//        binding.recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
//        binding.recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
//        adapter.setOnItemClickListener(this);
//        adapter.setOnItemLongClickListener(this);
//    }
//
//    @Override
//    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//        LogUtil.i(position, "/",adapter.getData().size());
//        Diary data = (Diary) adapter.getData().get(position);
//        Intent intent = new Intent(TableNoteActivity.this, NoteRichEditActivity.class);
//        intent.putExtra(DiaryDao.Properties.Uuid.columnName, data.getUuid());
//        TableNoteActivity.this.startActivity(intent);
//    }
//
//    @Override
//    public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//        LogUtil.i(position, "/",adapter.getData().size());
//        Diary data = (Diary) adapter.getData().get(position);
//        StringBuffer content = new StringBuffer();
//        content.append(data.getTitle()).append( "\n");
//        if (data.getContent().length()>10){
//            content.append(data.getContent().substring(0,10));
//        } else {
//            content.append(data.getContent());
//        }
//        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setMessage(content)
//                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        LogUtil.i("当前删除项:",data.get);
//                        presenter.deleteItem(data, position);
//                    }
//                })
//                .create();
//        dialog.show();
//        return true;
//    }
//
////    @Override
////    public boolean onQueryTextSubmit(String query) {
////        return false;
////    }
////
////    @Override
////    public boolean onQueryTextChange(String newText) {
////        presenter.updateList(newText);
////        return false;
////    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_account, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.menu_settings:
//                LogUtil.i("开始同步服务器与本地数据库");
//                presenter.startSyncNote();
//                break;
//            case R.id.menu_add:
//                Intent intent = new Intent(this, NoteRichEditActivity.class);
//                startActivity(intent);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onDataQueried(List<NoteItem> datas) {
//        ToastUtil.show("成功更新，总条数："+ datas.size());
//        adapter.setNewData(datas);
//        adapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onDataDeleted(Diary s, int position) {
//        ToastUtil.show("删除数据成功");
//        presenter.queryData(null, page++);
//    }
//}