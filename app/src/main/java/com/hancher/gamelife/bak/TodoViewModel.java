//package com.hancher.gamelife.main.note.bak;
//
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.hancher.common.utils.android.AsyncUtils;
//import com.hancher.common.utils.java.TextUtil;
//import com.hancher.gamelife.MainApplication;
//import com.hancher.gamelife.greendao.Diary;
//import com.hancher.gamelife.greendao.DiaryDao;
//
//import org.apache.commons.lang.StringEscapeUtils;
//import org.greenrobot.greendao.query.QueryBuilder;
//import org.greenrobot.greendao.query.WhereCondition;
//
//import java.util.List;
//
//import io.reactivex.ObservableOnSubscribe;
//
///**
// * 作者 : Hancher ytu_shaoweijie@163.com <br/>
// * 时间 : 2021/4/22 9:18 <br/>
// * 描述 : 待处理事项异步操作
// */
//public class TodoViewModel extends ViewModel {
//    private MutableLiveData<List<Diary>> todoList = new MutableLiveData<>();
//
//    public MutableLiveData<List<Diary>> getTodoList() {
//        return todoList;
//    }
//
//    public void queryTodoList(CharSequence searchChar) {
//        AsyncUtils.run((ObservableOnSubscribe<List<Diary>>) emitter -> {
//            QueryBuilder<Diary> qb = MainApplication.getInstance().getDaoSession()
//                    .getDiaryDao().queryBuilder();
//            WhereCondition wc1 = DiaryDao.Properties.Tag.eq("todo");
//            WhereCondition wc3 = qb.or(DiaryDao.Properties.Deleteflag.isNull(),
//                    DiaryDao.Properties.Deleteflag.notEq(1));
//            if (!TextUtil.isEmpty(searchChar)) {
//                String searchStr = searchChar.toString();
//                WhereCondition wc2 = qb.or(DiaryDao.Properties.Title.like("%" + searchStr + "%"),
//                        DiaryDao.Properties.Content.like("%" + searchStr + "%"),
//                        DiaryDao.Properties.Content.like("%" + StringEscapeUtils.escapeHtml(searchStr) + "%"));
//                qb.where(wc1, wc2, wc3);
//            } else {
//                qb.where(wc1, wc3);
//            }
//            List<Diary> todoLists = qb.orderDesc(DiaryDao.Properties.Createtime).list();
//            emitter.onNext(todoLists);
//            emitter.onComplete();
//        }, noteEntities -> todoList.setValue(noteEntities));
//    }
//
//    public void setTodoItemFinish(Diary item) {
//        item.setUpdatetime(System.currentTimeMillis());
//        item.setTag("已完成");
//        MainApplication.getInstance().getDaoSession().getDiaryDao().update(item);
//        queryTodoList(null);
//    }
//}
