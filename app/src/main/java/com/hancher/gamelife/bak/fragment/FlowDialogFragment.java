package com.hancher.gamelife.bak.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.hancher.common.utils.android.DpiUtil;
import com.hancher.gamelife.R;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class FlowDialogFragment extends DialogFragment {

    private TagAdapter tagAdapter;
    private TagFlowLayout.OnTagClickListener onTagClickListener;

    public FlowDialogFragment() {}

    public void setTagAdapter(TagAdapter tagAdapter) {
        this.tagAdapter = tagAdapter;
    }

    public void setOnTagClickListener(TagFlowLayout.OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = super.onCreateView(inflater, container, savedInstanceState);
        if(mRootView==null){
            mRootView = inflater.inflate(R.layout.flow_dialog_fragment, container,false);
            TagFlowLayout idFlowlayout = mRootView.findViewById(R.id.id_flowlayout);

            List<String> datas= new ArrayList<>();
            datas.add("孙悟空");
            datas.add("猪八戒");
            datas.add("沙和尚");
            datas.add("小白龙");
            idFlowlayout.setAdapter(tagAdapter);
            idFlowlayout.setOnTagClickListener(onTagClickListener);
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initWindowSize();
    }

    private void initWindowSize() {
        if (getContext()==null) {
            return;
        }
        Dialog dialog = getDialog();
        if (dialog == null){
            return;
        }
        DisplayMetrics size = DpiUtil.getWindowSize(getContext());
        dialog.getWindow().setLayout((int)(size.widthPixels*0.95f), (int)(size.heightPixels*0.9f));
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        tagAdapter = null;
        onTagClickListener = null;
    }
}
