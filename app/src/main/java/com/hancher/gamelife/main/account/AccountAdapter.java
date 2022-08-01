package com.hancher.gamelife.main.account;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.gamelife.R;
import com.hancher.gamelife.greendao.Account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AccountAdapter extends BaseMultiItemQuickAdapter<Account, BaseViewHolder> {

    public static final int ACCOUNT = 0;

    public AccountAdapter(@Nullable List<Account> data) {
        super(data);
        addItemType(ACCOUNT, R.layout.recycleritem_account);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Account item) {
        switch (baseViewHolder.getItemViewType()){
            case ACCOUNT:
                baseViewHolder.setText(R.id.item_account, item.getAccount());
                baseViewHolder.setText(R.id.item_user, item.getUser());
                if (TextUtils.isEmpty(item.getAccount())){
                    baseViewHolder.setText(R.id.item_icon, "@");
                } else {
                    baseViewHolder.setText(R.id.item_icon, item.getAccount().substring(0,1));
                }
                break;
        }
    }
}
