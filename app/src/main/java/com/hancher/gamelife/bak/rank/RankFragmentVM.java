package com.hancher.gamelife.bak.rank;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hancher.common.base.mvvm01.VMBaseFragment;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.contribution.ContributionConfig;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.RankFragmentBinding;
import com.race604.drawable.wave.WaveDrawable;

import java.util.Calendar;
import java.util.Date;

public class RankFragmentVM extends VMBaseFragment<RankFragmentBinding, RankViewModel> {

    private WaveDrawable mWaveDrawable;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initClickListener();
        initRank();
        initContribution();
        initCount();
    }

    private void initCount() {
        viewModel.getFontSizeCount().observe(getActivity(), s -> binding.txtFontSize.setText(s));
        viewModel.getListSizeCount().observe(getActivity(), s -> binding.txtListSize.setText(s));
        viewModel.queryNoteCount();
    }

    private void initContribution() {
        viewModel.getContributionList().observe(getActivity(), contributionItems -> {
            ContributionConfig config = new ContributionConfig()
                    .setBorderWidth(2)//边框宽度
                    .setBorderColor(0xFF9E9E9E)//边框颜色
                    .setItemRound(5)//圆角矩形圆半径
                    .setMonths(new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"})//月份字符串
                    .setPadding(4)//单个框宽度
                    .setRank(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16})//颜色等级范围，大于等于2小于5则为第二个颜色范围
                    .setRankColor(new int[]{0xFFEBEDF0, 0xFFa5d6a7, 0xFF66bb6a, 0xFF388e3c, 0xFF1b5e20,
                            0xFF81d4fa, 0xFF29b6f6, 0xFF0288d1, 0xFF01579b
                            , 0xFFb39ddb, 0xFF7e57c2, 0xFF512da8, 0xFF311b92
                            , 0xFFef9a9a, 0xFFef5350, 0xFFd32f2f, 0xFFb71c1c})//填充的等级颜色
                    .setWeeks(new String[]{"周一", "", "周三", "", "周五", "", "周天"})//周名称
                    .setStartOfWeek(Calendar.MONDAY)//配合setWeeks一起使用，可以实现第一行为周日，默认第一行周一
                    .setTxtColor(0xFF9E9E9E);//设置文字颜色
            Calendar calendar = Calendar.getInstance();
            for (int i = 0; i < contributionItems.size(); i++) {
                calendar.setTime(contributionItems.get(i).getTime());
            }
            Date startTime = contributionItems.get(0).getTime();
            calendar.setTime(startTime);
            LogUtil.d("startTime :", calendar.get(Calendar.YEAR), "-", calendar.get(Calendar.MONTH) + 1, "-", calendar.get(Calendar.DAY_OF_MONTH));
            binding.contributionView.setData(startTime, contributionItems, config);
        });
        viewModel.queryNoteContribution();
    }

    private void initClickListener() {
        binding.btnUpgrade.setOnClickListener(v -> {
            viewModel.upgradle();
        });
    }

    private void initRank() {

        if (getActivity() != null) {
            mWaveDrawable = new WaveDrawable(getActivity(), R.drawable.ic_launcher2);
            binding.imgProgress.setImageDrawable(mWaveDrawable);
        }

//        升级回调
        viewModel.getCurrentRank().observe(getActivity(), rank -> {
            binding.txtRank.setText(RankViewModel.getRankName(rank.getRank()));
            binding.txtPerProgress.setText(RankViewModel.getUnitExperienceByRank(rank.getRank()));
            binding.txtProgressMax.setText(RankViewModel.getMaxExperienceByRank(rank.getRank()));
        });

//        当前等级
        viewModel.getCurrentExperience().observe(getActivity(), s -> {
            binding.txtProgressCurrent.setText(s);
        });


//        当前进度
        viewModel.getCurrentProgress().observe(getActivity(), integer -> {
            if (mWaveDrawable != null) {
                LogUtil.d("progress:", integer);
                mWaveDrawable.setLevel(integer);
            }
        });
        viewModel.queryCurrentRank();

    }
}