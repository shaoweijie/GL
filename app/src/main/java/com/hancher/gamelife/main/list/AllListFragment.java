package com.hancher.gamelife.main.list;

import android.content.Intent;

import androidx.lifecycle.ViewModelProviders;

import com.hancher.common.base.mvvm02.BaseFragment;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.gamelife.databinding.AllListFragmentBinding;
import com.hancher.gamelife.greendao.DiaryDao;
import com.hancher.gamelife.main.colockin.ColockInListActivityVM;
import com.hancher.gamelife.main.human.BirthdayActivity;
import com.hancher.gamelife.main.note.NoteRichEditActivityVM;

import java.util.ArrayList;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/28 0028 20:16 <br/>
 * 描述 : 笔记列表
 */
public class AllListFragment extends BaseFragment<AllListFragmentBinding> {

    AllListViewModel allListViewModel;
    private AllListAdapter adapter = new AllListAdapter(new ArrayList<>());
// TODO: swj: 2021/9/29 0029 人物vm导入

    @Override
    protected void beforeOnCreateView() {
        allListViewModel = ViewModelProviders.of(this).get(AllListViewModel.class);
        RecyclerViewUtil.initLine(getActivity(),binding.recyclerview,adapter, binding.swipeRefresh, false);
        allListViewModel.getAllList().observe(this, allListData -> RecyclerViewUtil.update(adapter, allListData));
        adapter.setOnItemClickListener((adapter, view, position) -> {
            AllListAdapter.AllListItem item = (AllListAdapter.AllListItem) adapter.getItem(position);
            Intent intent = null;
            if (item.getItemType() == AllListAdapter.DIARY){
                intent = new Intent(getActivity(), NoteRichEditActivityVM.class);
                intent.putExtra(DiaryDao.Properties.Uuid.columnName, item.getDiary().getUuid());
            } else if (item.getItemType() == AllListAdapter.COLOCKIN){
                intent = new Intent(getActivity(), ColockInListActivityVM.class);
            } else if (item.getItemType() == AllListAdapter.BIRTHDAY){
                intent = new Intent(getActivity(), BirthdayActivity.class);
            }
            startActivity(intent);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        allListViewModel.queryAllList();
    }
}