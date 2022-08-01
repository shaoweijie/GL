package com.hancher.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hancher.common.R;
import com.hancher.common.base.mvp01.BaseActivity;
import com.hancher.common.databinding.SearchActivityBinding;
import com.hancher.common.utils.android.DensityUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.endurance.PreferenceUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.common.view.flowlayout.FlowLayout;
import com.hancher.common.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/4/3 9:24 <br/>
 * 描述 : 搜索界面
 */
public class SearchActivity extends BaseActivity<SearchActivityBinding> {

    /**
     * 查询列表显示数量
     */
    private final static int MAX_SHOW_COUNT = 10;
    /**
     * 标签显示数量
     */
    private final static int MAX_HISTORY_COUNT = 12;
    public final static String DATA = "data";
    public final static String RESUlT_DATA = "data";
    public final static String HISTORY_PREFIX = "history_prefix_";
    private ArrayList<CharSequence> dataList = null;
    private Queue<String> historyQueue = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        dataList = intent.getCharSequenceArrayListExtra(DATA);
        LogUtil.d("data list :", dataList);
        initSearchEdit();
        initHistory();
        initParent();
    }

    private void initParent() {
        binding.container.setOnClickListener(v -> setResultAndFinish(null));
    }

    private void initHistory() {
        //历史标签
        List<String> recordList = new ArrayList<>();
        for (int i = 0; i < MAX_HISTORY_COUNT; i++) {
            String value = PreferenceUtil.getString(HISTORY_PREFIX + i, "");
            if (!TextUtil.isEmpty(value)) {
                recordList.add(value);
            }
        }
        TagAdapter<String> mRecordsAdapter = new TagAdapter<String>(recordList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(
                        R.layout.tv_history, binding.flSearchRecords, false);
                //为标签设置对应的内容
                tv.setText(s);
                return tv;
            }
        };
        binding.flSearchRecords.setAdapter(mRecordsAdapter);
        binding.flSearchRecords.setOnTagClickListener((view, position, parent) -> {
            //清空editText之前的数据
            binding.editQuery.setText("");
            //将获取到的字符串传到搜索结果界面,点击后搜索对应条目内容
            binding.editQuery.setText(recordList.get(position));
            binding.editQuery.setSelection(binding.editQuery.length());
        });

        //清除所有历史标签
        binding.clearAllRecords.setOnClickListener(v -> {
            HashMap<String, String> map = new HashMap<>();
            for (int i = 0; i < MAX_HISTORY_COUNT; i++) {
                map.put(HISTORY_PREFIX + i, "");
            }
            PreferenceUtil.setValue(map);
            binding.flSearchRecords.getAdapter().setData(new ArrayList());
            binding.flSearchRecords.getAdapter().notifyDataChanged();
        });

    }

    private void initSearchEdit() {
        //删除输入内容
        binding.ivClearSearch.setOnClickListener(v -> binding.editQuery.setText(""));
        //点击取消按钮
        binding.ivBack.setOnClickListener(v -> setResultAndFinish(null));
        //输入文本
        binding.editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                queryByFilter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.txtOk.setOnClickListener(v -> {
            String searchChar = binding.editQuery.getText().toString();
            saveSearchChar(searchChar);
            setResultAndFinish(searchChar);
        });
    }

    public void queryByFilter(CharSequence s) {
        ArrayList<CharSequence> resultList = new ArrayList<>();

        if (TextUtil.isEmpty(s)) {
            updateList(resultList);
            return;
        }

        if (dataList == null || dataList.isEmpty()) {
            return;
        }

        String tmp;
        for (CharSequence item : dataList) {
            tmp = item.toString();
            if (tmp.contains(s)) {
                resultList.add(tmp);
                if (resultList.size() >= MAX_SHOW_COUNT) {
                    break;
                }
            }
        }
        updateList(resultList);
    }

    private void updateList(ArrayList<CharSequence> resultList) {
        //根据内容设置ListView高度
        int avaiableHeight = binding.container.getHeight() - binding.clToolbar.getHeight() - binding.llHistoryContent.getHeight();
        int contentHeight = resultList.size() * DensityUtil.dp2px(this, 50 + 1);
        ViewGroup.LayoutParams param = binding.listView.getLayoutParams();
        param.height = Math.min(avaiableHeight, contentHeight);
        binding.listView.setLayoutParams(param);
        //设置数据以及监听
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,
                R.layout.search_list_item, resultList);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener((parent, view, position, id) -> {
            String clickStr = resultList.get(position).toString();
            saveSearchChar(clickStr);
            setResultAndFinish(clickStr);
        });
    }

    private void saveSearchChar(String searchChar) {
        //获取并转为队列
        Queue<String> historySer = new LinkedList<>();
        for (int i = 0; i < MAX_HISTORY_COUNT; i++) {
            String value = PreferenceUtil.getString(HISTORY_PREFIX + i, "");
            if (!TextUtil.isEmpty(value)) {
                if (value.equals(searchChar)) {
                    LogUtil.d("已存在不需要修改");
                    return;
                }
                historySer.offer(value);
            }
        }
        while (historySer.size() >= MAX_HISTORY_COUNT) {
            historySer.poll();
        }
        historySer.offer(searchChar);
        //转为字典并保存
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < MAX_HISTORY_COUNT; i++) {
            if (historySer.size() > 0) {
                map.put(HISTORY_PREFIX + i, historySer.poll());
            } else {
                map.put(HISTORY_PREFIX + i, "");
            }
        }
        PreferenceUtil.setValue(map);
    }

    private void setResultAndFinish(CharSequence charSequence) {
        Intent intent = new Intent();
        if (TextUtil.isEmpty(charSequence)) {
            setResult(RESULT_CANCELED);
        } else {
            LogUtil.d("result data:", charSequence);
            intent.putExtra(RESUlT_DATA, charSequence.toString());
            setResult(RESULT_OK, intent);
        }
        finish();
    }

}
