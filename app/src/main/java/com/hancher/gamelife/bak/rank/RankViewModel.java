package com.hancher.gamelife.bak.rank;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.java.HtmlUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.common.utils.java.UuidUtil;
import com.hancher.contribution.ContributionItem;
import com.hancher.gamelife.MainApplication;
import com.hancher.gamelife.greendao.Diary;
import com.hancher.gamelife.greendao.DiaryDao;
import com.hancher.gamelife.greendao.Rank;
import com.hancher.gamelife.greendao.RankDao;
import com.hancher.gamelife.greendao.RankType;

import org.greenrobot.greendao.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/11 9:17 <br/>
 * 描述 : 登录操作
 */
public class RankViewModel extends ViewModel {
    private Disposable d;
    private MutableLiveData<List<Rank>> data = new MutableLiveData<>();
    private MutableLiveData<Rank> currentRank = new MutableLiveData<>();
    private MutableLiveData<String> currentExperience = new MutableLiveData<>();
    private MutableLiveData<String> fontSizeCount = new MutableLiveData<>();
    private MutableLiveData<String> listSizeCount = new MutableLiveData<>();
    private MutableLiveData<Integer> currentProgress = new MutableLiveData<>();
    private MutableLiveData<List<ContributionItem>> contributionList = new MutableLiveData<>();

    public MutableLiveData<String> getListSizeCount() {
        return listSizeCount;
    }

    public MutableLiveData<String> getFontSizeCount() {
        return fontSizeCount;
    }

    public MutableLiveData<List<Rank>> getData() {
        return data;
    }

    public MutableLiveData<Rank> getCurrentRank() {
        return currentRank;
    }

    public MutableLiveData<String> getCurrentExperience() {
        return currentExperience;
    }

    public MutableLiveData<Integer> getCurrentProgress() {
        return currentProgress;
    }

    public MutableLiveData<List<ContributionItem>> getContributionList() {
        return contributionList;
    }

    public static String[] rankNames = new String[]{
            "炼气", "筑基", "结丹", "金丹", "元婴", "出窍", "分神", "化神", "合体", "炼虚", "洞虚", "洞真", "大乘", "飞升",
            "散仙 ", "游仙", "地仙", "天仙",
            "金仙", "太乙金仙", "大罗金仙", "罗天上仙",
            "玄仙", "太乙玄仙", "九天玄仙", "玄天上仙",
            "准圣", "圣人", "大圣", "天圣",
            "准帝", "大帝", "天帝", "荒帝",
            "仙君", "仙尊", "仙帝",
            "修罗", "真神"
    };

    public void queryAllRank() {
        d = AsyncUtils.run(emitter -> {
            QueryBuilder<Rank> qb = MainApplication.getInstance().getDaoSession().getRankDao().queryBuilder();
            qb.where(qb.or(RankDao.Properties.Deleteflag.isNull(), RankDao.Properties.Deleteflag.notEq(1)));
            List<Rank> rankList = qb.list();
            emitter.onNext(rankList);
            emitter.onComplete();
        }, (Consumer<List<Rank>>) ranks -> {
            data.setValue(ranks);
        });
    }


    @Override
    protected void onCleared() {
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
        super.onCleared();
    }

    /**
     * 当前经验大于当前最大经验，单击后升级
     * 重新查询当前经验，等级
     */
    public void upgradle() {
        LogUtil.d();
        AsyncUtils.run(emitter -> {
            String currentString = currentExperience.getValue();
            if (TextUtil.isEmpty(currentString)) {
                emitter.onComplete();
                return;
            }
            Rank rank = currentRank.getValue();
            if (rank == null) {
                emitter.onComplete();
                return;
            }
            String maxString = getMaxExperienceByRank(rank.getRank());
            if (new BigDecimal(currentString).compareTo(new BigDecimal(maxString)) > 0) {
                emitter.onNext("进阶为:" + getRankName(rank.getRank() + 1));
            } else {
                emitter.onNext("仙气不足，请继续努力");
                emitter.onComplete();
                return;
            }

            Rank startRank = new Rank();
            startRank.setAllExperience(currentExperience.getValue());
            startRank.setDeleteflag(0);
            startRank.setEndtime(null);
            startRank.setRank(rank.getRank() + 1);
            startRank.setRankType(RankType.practice);
            startRank.setStarttime(System.currentTimeMillis());
            startRank.setUuid(UuidUtil.getUuidNoLine());
            startRank.setUnitExperience(getUnitExperienceByRank(rank.getRank() + 1));
            MainApplication.getInstance().getDaoSession().getRankDao().insert(startRank);
            queryCurrentRank();
            emitter.onComplete();
        }, (Consumer<String>) ToastUtil::show);
    }

    /**
     * 140级前 单位经验为 （上等级最大经验-下等级最大经验）/24
     * 140级后 单位经验为 （上等级最大经验-下等级最大经验）/（7 x 24） + 40
     *
     * @param rank
     * @return
     */
    public static String getUnitExperienceByRank(int rank) {
        if (rank < 140) {
            return new BigDecimal(getMaxExperienceByRank(rank + 1))
                    .subtract(new BigDecimal(getMaxExperienceByRank(rank)))
                    .divide(BigDecimal.valueOf(24), BigDecimal.ROUND_CEILING).toString();
        } else {
            return new BigDecimal(getMaxExperienceByRank(rank + 1))
                    .subtract(new BigDecimal(getMaxExperienceByRank(rank)))
                    .divide(BigDecimal.valueOf(7 * 24), BigDecimal.ROUND_CEILING)
                    .add(BigDecimal.valueOf(40)).toString();
        }
    }

    /**
     * 将10等级的平均间隔转为直线，获取对应1等级的最大经验
     *
     * @param rank
     * @return
     */
    public static String getMaxExperienceByRank(int rank) {
        int ext = rank % 10;
        if (ext == 0) {
            return getMaxExperienceByRank2(rank / 10);
        }
        String start = getMaxExperienceByRank2(rank / 10);
        String end = getMaxExperienceByRank2(rank / 10 + 1);
        BigDecimal startBig = new BigDecimal(start);
        BigDecimal detla = new BigDecimal(end).subtract(startBig);
        return detla.divide(BigDecimal.valueOf(10), BigDecimal.ROUND_CEILING)
                .multiply(BigDecimal.valueOf(ext))
                .add(startBig).toString();
    }

    /**
     * 按10级划分经验
     *
     * @param rank10 等级/10
     * @return 2 ^ 10等级 + 100 * 10等级
     */
    public static String getMaxExperienceByRank2(int rank10) {
        BigDecimal d = BigDecimal.valueOf(2).pow(rank10);
        d = d.add(BigDecimal.valueOf(100 * rank10));
        return d.toEngineeringString();
    }

    /**
     * 获取境界名称，包含小境界
     *
     * @param rank 11
     * @return 筑基1级
     */
    public static String getRankName(int rank) {
        int remainder = rank % 10;
        int bigRank = rank / 10;
        if (bigRank >= rankNames.length) {
            return "未知生命体";
        }
        return rankNames[bigRank] + remainder + "级";
    }

    /**
     * 当前等级为：最后一条等级记录
     */
    public void queryCurrentRank() {
        LogUtil.d();
        AsyncUtils.run((ObservableOnSubscribe<Rank>) emitter -> {
            List<Rank> list = MainApplication.getInstance().getDaoSession().getRankDao().queryBuilder()
                    .where(RankDao.Properties.RankType.eq(RankType.practice))
                    .orderDesc(RankDao.Properties.Starttime)
                    .limit(1).list();
            if (list.size() == 1) {
                emitter.onNext(list.get(0));
            } else if (list.size() == 0) {
                Rank startRank = new Rank();
                startRank.setAllExperience("0");
                startRank.setDeleteflag(0);
                startRank.setEndtime(null);
                startRank.setRank(0);
                startRank.setRankType(RankType.practice);
                startRank.setStarttime(System.currentTimeMillis());
                startRank.setUuid(UuidUtil.getUuidNoLine());
                startRank.setUnitExperience(getUnitExperienceByRank(0));
                MainApplication.getInstance().getDaoSession().getRankDao().insert(startRank);
                emitter.onNext(startRank);
            }
            emitter.onComplete();
        }, rank -> {
            currentRank.setValue(rank);
            queryCurrentExperience();
        });
    }

    /**
     * 当前经验为：最后一条经验记录的全部经验+至今时间 x 单位时间经验
     */
    public void queryCurrentExperience() {
        LogUtil.d();
        AsyncUtils.run(emitter -> {

            QueryBuilder<Rank> qb = MainApplication.getInstance().getDaoSession().getRankDao().queryBuilder();
            List<Rank> lastItem = qb.orderDesc(RankDao.Properties.Starttime)
                    .limit(1).list();
            if (lastItem.size() < 1) {
                LogUtil.w("last item rank size 0");
                emitter.onComplete();
                return;
            }
            Rank lastItemRank = lastItem.get(0);

            if (currentRank.getValue() == null) {
                LogUtil.w("last item rank value null");
                emitter.onComplete();
                return;
            }
            Rank currentItemRank = currentRank.getValue();

            BigDecimal allExperience = new BigDecimal(currentItemRank.getUnitExperience())
                    .multiply(new BigDecimal((int) (System.currentTimeMillis() - lastItemRank.getStarttime()) / 1000 / 60 / 60))
                    .add(new BigDecimal(lastItemRank.getAllExperience()));
            emitter.onNext(allExperience.toString());
            emitter.onComplete();
        }, (Consumer<String>) s -> {
            currentExperience.setValue(s);
            BigDecimal current = new BigDecimal(s);
            BigDecimal min = new BigDecimal(getMaxExperienceByRank(currentRank.getValue().getRank() - 1));
            BigDecimal max = new BigDecimal(getMaxExperienceByRank(currentRank.getValue().getRank()));
            if (current.compareTo(max) > 0) {
                currentProgress.setValue(10000);
            } else {
                BigDecimal ext = current.subtract(min).multiply(new BigDecimal(10000))
                        .divide(max.subtract(min), BigDecimal.ROUND_CEILING);
                currentProgress.setValue(ext.intValue());
            }
        });
    }

    public void queryNoteContribution() {
        AsyncUtils.run((ObservableOnSubscribe<List<ContributionItem>>) emitter -> {
            //获取开始、结束等日期
            int days = 150;
            Calendar calendar = Calendar.getInstance();
            long currentTime = System.currentTimeMillis();
            calendar.setTimeInMillis(currentTime);
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            Date currentDate = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, -days);
            Date startDate = calendar.getTime();

            //获取所有笔记
            QueryBuilder<Diary> qb = MainApplication.getInstance().getDaoSession().getDiaryDao().queryBuilder();
            qb.where(qb.or(DiaryDao.Properties.Deleteflag.isNull(),
                    DiaryDao.Properties.Deleteflag.notEq("1")),
                    DiaryDao.Properties.Createtime.ge(startDate.getTime()));
            List<Diary> list = qb.list();

            //获取递增日期的贡献列表
            ArrayList<ContributionItem> contributionItems = new ArrayList<>();
            for (int i = 0; i < days + 1; i++) {
                contributionItems.add(i, new ContributionItem(calendar.getTime(), 0));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            for (Diary item : list) {
                Long noteTime = System.currentTimeMillis();
                int index = (int) ((noteTime - startDate.getTime()) / 24 / 3600 / 1000);
                ContributionItem existItem = contributionItems.get(index);
                existItem.setNumber(existItem.getNumber() + 1);
            }

            emitter.onNext(contributionItems);
            emitter.onComplete();
        }, contributionItems -> {
            contributionList.setValue(contributionItems);
        });
    }

    public void queryNoteCount() {
        AsyncUtils.run((ObservableOnSubscribe<Map<String, String>>) emitter -> {
            QueryBuilder<Diary> qb = MainApplication.getInstance().getDaoSession().getDiaryDao().queryBuilder();
            qb.where(qb.or(DiaryDao.Properties.Deleteflag.isNull(),
                    DiaryDao.Properties.Deleteflag.notEq("1")));
            List<Diary> list = qb.list();
            BigDecimal bigDecimal = new BigDecimal("0");
            for (Diary item : list) {
                if (!TextUtil.isEmpty(item.getTitle())) {
                    bigDecimal = bigDecimal.add(new BigDecimal(item.getTitle().length()));
                }
                if (!TextUtil.isEmpty(item.getContent())) {
                    bigDecimal = bigDecimal.add(new BigDecimal(HtmlUtil.delHTMLTag(item.getContent()).length()));
                }
            }
            Map<String, String> map = new HashMap<>();
            map.put("list", String.valueOf(list.size()));
            map.put("font", bigDecimal.toString());
            emitter.onNext(map);
            emitter.onComplete();
        }, map -> {
            fontSizeCount.setValue(map.get("font"));
            listSizeCount.setValue(map.get("list"));
        });
    }
}
