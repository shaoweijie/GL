//package com.hancher.gamelife.main.note.bak;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.afollestad.materialdialogs.MaterialDialog;
//import com.hancher.common.activity.SearchActivity;
//import com.hancher.common.base.BaseFragment;
//import com.hancher.common.utils.android.BaseAdapterUtil;
//import com.hancher.common.utils.android.DialogUtil;
//import com.hancher.common.utils.android.LogUtil;
//import com.hancher.common.utils.java.TextUtil;
//import com.hancher.gamelife.R;
//import com.hancher.gamelife.databinding.TodoFragmentBinding;
//import com.hancher.gamelife.greendao.Diary;
//import com.hancher.gamelife.greendao.DiaryDao;
//import com.hancher.gamelife.main.note.NoteAdapter;
//import com.hancher.gamelife.main.note.NoteRichEditActivity;
//
///**
// * 作者 : Hancher ytu_shaoweijie@163.com <br/>
// * 时间 : 2021/4/22 8:54 <br/>
// * 描述 : 主界面中待处理记录
// */
//public class TodoFragment extends BaseFragment<TodoFragmentBinding, TodoViewModel> {
//    public static final int SEARCH_REQUEST = 422;
//    private NoteAdapter adapter = new NoteAdapter(null);
//    private MaterialDialog dialog;
//
//    @Override
//    protected void beforeOnCreateView() {
//        initList();
//        initSearch();
//    }
//
//    private void initSearch() {
//        binding.clToolbar.setOnClickListener(v -> {
//            if (getActivity() == null) {
//                LogUtil.w("activity null");
//                return;
//            }
//
//            Intent intent = new Intent(getActivity(), SearchActivity.class);
//            startActivityForResult(intent, TodoFragment.SEARCH_REQUEST);
//        });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SEARCH_REQUEST) {
//            if (resultCode == Activity.RESULT_OK) {
//                CharSequence searchChar = data.getCharSequenceExtra(SearchActivity.RESUlT_DATA);
//                if (TextUtil.isEmpty(searchChar)) {
//                    LogUtil.w("查询到的文本不应该为空");
//                    return;
//                }
//                viewModel.queryTodoList(searchChar);
//            }
//        }
//    }
//
//    private void initList() {
//        if (getActivity() == null) {
//            LogUtil.w("activity null");
//            return;
//        }
//
//        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
//        View notDataView = getLayoutInflater().inflate(R.layout.recyclerview_empty,
//                binding.recyclerview, false);
//        adapter.setEmptyView(notDataView);
//        adapter.setUseEmpty(true);
//        adapter.setOnItemClickListener((adapter, view, position) -> {
//            Diary data = (Diary) adapter.getData().get(position);
//            Intent intent = new Intent(getActivity(), NoteRichEditActivity.class);
//            intent.putExtra(DiaryDao.Properties.Uuid.columnName, data.getUuid());
//            getActivity().startActivity(intent);
//        });
//        adapter.setOnItemLongClickListener((adapter, view, position) -> {
//            dialog = DialogUtil.build(getActivity()).title("标记位已完成").positiveText("确定").negativeText("取消")
//                    .onPositive((dialog, which) -> {
//                        Diary item = (Diary) adapter.getData().get(position);
//                        viewModel.setTodoItemFinish(item);
//                        dialog.dismiss();
//                    }).show();
//            return false;
//        });
//        adapter.setAnimationEnable(true);
//        binding.recyclerview.setAdapter(adapter);
//
//        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
//        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.queryTodoList(null));
//
//        viewModel.getTodoList().observe(this, noteEntities -> {
//            LogUtil.i("查询到todo 条数：", noteEntities.size());
//            if (binding.swipeRefresh.isRefreshing()) {
//                binding.swipeRefresh.setRefreshing(false);
//            }
//            BaseAdapterUtil.update(adapter, noteEntities);
//        });
//        viewModel.queryTodoList(null);
//    }
//
//    @Override
//    public void onPause() {
//        if (dialog != null) {
//            dialog.dismiss();
//        }
//        super.onPause();
//    }
//}
