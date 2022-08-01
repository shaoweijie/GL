//package com.hancher.gamelife.main.note;
//
//import com.hancher.common.base.v01.BasePresenter;
//import com.hancher.common.utils.android.AsyncUtils;
//import com.hancher.common.utils.android.LogUtil;
//import com.hancher.common.utils.java.TextUtil;
//import com.hancher.gamelife.MainApplication;
//import com.hancher.gamelife.main.note.NoteUtil;
//import com.hancher.gamelife.bak.contract.NoteContract;
//import com.hancher.gamelife.greendao.Diary;
//import com.hancher.gamelife.greendao.DiaryDao;
//import com.hancher.gamelife.bak.list.FieldFilterAdapter;
//import com.hancher.gamelife.main.note.NoteAdapter;
//
//import org.greenrobot.greendao.query.QueryBuilder;
//import org.greenrobot.greendao.query.WhereCondition;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import io.reactivex.functions.Consumer;
//
///**
// * 作者：Hancher
// * 时间：2020/10/11 0011 下午 3:40
// * 邮箱：ytu_shaoweijie@163.com
// * <p>
// * 说明：
// */
//public class NotePresenter extends BasePresenter<NoteListActivity> implements NoteContract.Presenter {
//    public final static int NUMBER_OF_PAGE = 30;
//    @Override
//    public void startSyncNote() {
//        LogUtil.d("开始拉取服务器数据");
////        Disposable d = NoteApi.queryNote().subscribe(result -> {
////            if (result.isOK()) {
////                LogUtil.d("服务器数据拉取成功，从服务器获取条数："+result.getData().size());
////                LogUtil.v("服务器数据拉取成功，第一条数据：",result.getData().get(0));
////                updateLocalAndServerDb(result.getData());
////            } else {
////                ToastUtil.showErr("服务器数据拉取同步失败，服务器报错："+result.getMessage());
////            }
////        }, error -> {
////            ToastUtil.showErr("服务器数据拉取同步失败，网络报错："+error.getMessage());
////        });
//    }
//
//
//
//    @Override
//    public void deleteItem(String uuid, int position) {
//        AsyncUtils.run(emitter -> {
//            LogUtil.d("数据库删除开始,item=" + uuid);
//            List<Diary> diarys = MainApplication.getInstance().getDaoSession().getDiaryDao()
//                    .queryBuilder().where(DiaryDao.Properties.Uuid.eq(uuid)).list();
//            if (diarys==null || diarys.size()==0){
//                return;
//            }
//            diarys.get(0).setDeleteflag(1);
//            diarys.get(0).setUpdatetime(System.currentTimeMillis());
//            MainApplication.getInstance().getDaoSession().getDiaryDao().update(diarys.get(0));
//            LogUtil.d("数据库删除结束");
//            emitter.onNext(diarys.get(0));
//            emitter.onComplete();
//        }, (Consumer<Diary>) s -> mView.onDataDeleted(s,position));
//    }
//
//    @Override
//    public List<FieldFilterAdapter.FieldFilterItem> getFilterData() {
//        List<FieldFilterAdapter.FieldFilterItem> datas = new ArrayList<>();
//        datas.add(new FieldFilterAdapter.FieldFilterItem("标题", DiaryDao.Properties.Title));
//        datas.add(new FieldFilterAdapter.FieldFilterItem("内容", DiaryDao.Properties.Content));
//        datas.add(new FieldFilterAdapter.FieldFilterItem("标签", DiaryDao.Properties.Tag));
//        return datas;
//    }
//
//    public void queryData(List<FieldFilterAdapter.FieldFilterItem> fieldDatas, int page) {
//        LogUtil.d("开始查询日记");
//        AsyncUtils.run(emitter -> {
//            emitter.onNext(queryDataInternal(fieldDatas,page));
//            emitter.onComplete();
//        }, (Consumer<List<NoteAdapter.NoteItem>>) noteEntities -> {
//            LogUtil.d("日记查询成功:"+noteEntities.size());
//            mView.onDataQueried(noteEntities);
//        });
//    }
//
//
//    private List<NoteAdapter.NoteItem> queryDataInternal(List<FieldFilterAdapter.FieldFilterItem> fieldDatas, int page) {
//        QueryBuilder<Diary> queryBuilder = MainApplication.getInstance()
//                .getDaoSession().getDiaryDao().queryBuilder();
//
////            where
//        if (fieldDatas == null || fieldDatas.size() == 0){
//            queryBuilder.where(DiaryDao.Properties.Deleteflag.eq(0));
//        } else {
//            List<WhereCondition> whereConditions = new ArrayList<>();
//            for (FieldFilterAdapter.FieldFilterItem item : fieldDatas) {
//                if (TextUtil.isEmpty(item.getValue())) {
//                    continue;
//                }
//                whereConditions.add(item.getFieldProperty().like("%" + item.getValue() + "%"));
//            }
//            queryBuilder.where(DiaryDao.Properties.Deleteflag.eq(0),
//                    whereConditions.toArray(new WhereCondition[whereConditions.size()]));
//        }
////            order
//        queryBuilder.orderDesc(DiaryDao.Properties.Createtime);
//        queryBuilder.limit(page*NUMBER_OF_PAGE).offset(0);
//
//        List<Diary> list = queryBuilder.build().list();
//        LogUtil.d("日记从数据库中查询成功:"+list.size());
//        return NoteUtil.db2list(list);
//    }
//
////    private void updateLocalAndServerDb(List<Diary> serverData) {
////
////        LogUtil.d("开始比对本地与服务器数据");
////        AsyncUtils.run(emitter -> {
////                    LogUtil.d("开始获取本地服务器数据");
////                    List<Diary> dbData = MainApplication.getInstance().getDaoSession().getDiaryDao().queryBuilder().build().list();
////                    LogUtil.d("本地数据成功获取条数：" + dbData.size());
////                    int[] localResult = updateLocalDb(serverData, dbData);
////                    int[] serverResult = updateServerDb(serverData, dbData);
////                    ArrayList<Long> result = new ArrayList<>();
////                    result.add((long) localResult[0]);
////                    result.add((long) localResult[1]);
////                    result.add((long) serverResult[0]);
////                    result.add((long) serverResult[1]);
////                    emitter.onNext(result);
////                    emitter.onComplete();
////                },
////                (Consumer<ArrayList<Long>>) result -> ToastUtil.show("本地插入："+result.get(0)+"、更新："+result.get(1)+"，远程插入："+result.get(2)+"、更新："+result.get(3)));
////    }
//
////    /**
////     * 比对服务器与本地数据库，更新服务器数据库
////     * @param serverData
////     * @param dbData
////     * @return 返回插入、更新个数
////     */
////    private int[] updateServerDb(List<Diary> serverData, List<Diary> dbData) {
////        LogUtil.d("开始比对并更新服务器数据");
////        int[] result2 = {0,0};
//////        int compareResult = -1;
//////        for (NoteEntity dbItem : dbData){
//////            try {
//////                compareResult = compareItem(serverData, dbItem);
//////                if (compareResult == 1){
//////                    Disposable d = NoteApi.add(dbItem).subscribe(result -> {
//////                        if (result.isOK()) {
//////                        } else {
//////                            ToastUtil.showErr(result.getMessage());
//////                        }
//////                    }, error -> {
//////                        ToastUtil.showErr(error.getMessage());
//////                    });
//////                    result2[0] += 1;
//////                } else if (compareResult == 2){
//////                    Disposable d = NoteApi.edit(dbItem).subscribe(result -> {
//////                        if (result.isOK()) {
//////                        } else {
//////                            ToastUtil.showErr(result.getMessage());
//////                        }
//////                    }, error -> {
//////                        ToastUtil.showErr(error.getMessage());
//////                    });
//////                    result2[1] += 1;
//////                }
//////            } catch (Exception e){
//////                LogUtil.e("当前item上传服务器数据库失败，compareResult=",compareResult,",",dbItem);
//////                LogUtil.e(e);
//////            }
//////        }
////        return result2;
////    }
////
////    /**
////     * 比对服务器与本地数据库，更新本地数据库
////     * @param serverData
////     * @param dbData
////     * @return 返回插入、更新个数
////     */
////    private int[] updateLocalDb(List<Diary> serverData, List<Diary> dbData) {
////        LogUtil.d("开始比对并更新本地数据");
////        int[] result = {0,0};
////        int compareResult = -1;
////        for (Diary serverItem : serverData){
////            try {
////                compareResult = compareItem(dbData, serverItem);
////                if (compareResult == 1) {
////                    MainApplication.getInstance().getDaoSession().getDiaryDao().insert(serverItem);
////                    result[0] += 1;
////                } else if (compareResult == 2) {
////                    MainApplication.getInstance().getDaoSession().getDiaryDao().update(serverItem);
////                    result[1] += 1;
////                }
////            } catch (Exception e){
////                LogUtil.e("当前item导入数据库失败，compareResult=",compareResult,",",serverItem);
////                LogUtil.e(e);
////            }
////        }
////        return result;
////    }
//
////    /**
////     * 检测当前数据是否需要插入或更新
////     * @param existData
////     * @param newItem
////     * @return 0 无需操作，1 插入， 2 更新
////     */
////    private int compareItem(List<Diary> existData, Diary newItem) {
//////        for (Diary existItem : existData){
//////            if (newItem.getUuid().equals(existItem.getUuid())){
//////                //找到相同uuid，检查是否更新
//////                if (TextUtil.isEmptyOrNullStr(newItem.getUpdatetime())){
//////                    //服务器更新时间为空不需要本地更新
//////                    return 0;
//////                } else if (TextUtil.isEmptyOrNullStr(existItem.getUpdatetime())){
//////                    //服务器更新时间不为空，本地时间为空，需要更新
//////                    return 2;
//////                } else {
//////                    //服务器更新时间，本地更新时间，均不为空，则比较是否需要更新
//////                    if (Long.parseLong(newItem.getUpdatetime()) > Long.parseLong(existItem.getUpdatetime())){
//////                        return 2;
//////                    } else {
//////                        return 0;
//////                    }
//////                }
//////            }
//////        }
////        // 未找到，执行插入
////        return 1;
////    }
//
//}
