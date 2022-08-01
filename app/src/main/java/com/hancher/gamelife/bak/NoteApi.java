//package com.hancher.gamelife.api;
//
//import android.text.TextUtils;
//
//import com.hancher.common.net.NetUtil;
//import com.hancher.common.net.bean.ResultBean;
//import com.hancher.common.utils.android.AsyncUtils;
//import com.hancher.common.utils.android.LogUtil;
//import com.hancher.gamelife.greendao.NoteEntity;
//
//import java.util.List;
//
//import io.reactivex.Observable;
//import retrofit2.http.Field;
//import retrofit2.http.FormUrlEncoded;
//import retrofit2.http.POST;
//
///**
// * 作者: Hancher
// * 日期: 2020/9/6 10:21
// * 描述: 账户密码记录表
// * ID,content,equipment,title,picture,tag
// * ,uuid,temperature,weather
// * ,wind,windrank,latitude,longitude,createtime,updatetime,deleteflag
// */
//public class NoteApi {
//    public static Observable<ResultBean<List<NoteEntity>>> queryNote() {
//        return new NetUtil<>(EasyService.class).build()
//                .query("note2", "ID,content,equipment,title,picture,tag,uuid,temperature,weather,wind,windrank,latitude,longitude,createtime,updatetime,deleteflag,position9")
//                .compose(AsyncUtils.getThreadTransformer());
//    }
//
//    public static Observable<ResultBean<List<NoteEntity>>> add(NoteEntity item) {
//        String values = (TextUtils.isEmpty(item.getContent()) ? "null" : item.getContent()) + "," +
//                item.getEquipment() + "," +
//                (TextUtils.isEmpty(item.getTitle()) ? "null" : item.getTitle()) + "," +
//                item.getPicture() + "," +
//                item.getTag() + "," +
//                item.getUuid() + "," +
//                item.getTemperature() + "," +
//                item.getWeather() + "," +
//                item.getWind() + "," +
//                item.getWindrank() + "," +
//                item.getLatitude() + "," +
//                item.getLongitude() + "," +
//                item.getCreatetime() + "," +
//                item.getUpdatetime() + "," +
//                item.getDeleteflag() + "," +
//                item.getPosition();
////        values = HancherGb2312Utf8Util.utf8ToGb2312(values);
//        LogUtil.v("add values=", values);
//        return new NetUtil<>(NoteApi.EasyService.class).build()
//                .add("note2", "content,equipment,title,picture,tag,uuid,temperature,weather,wind,windrank,latitude,longitude,createtime,updatetime,deleteflag,position9", values)
//                .compose(AsyncUtils.getThreadTransformer());
//    }
//
//    public static Observable<ResultBean<List<NoteEntity>>> edit(NoteEntity item) {
//        String fields = "content=" + item.getContent() + "," +
//                "equipment=" + item.getEquipment() + "," +
//                "title=" + item.getTitle() + "," +
//                "prcture=" + item.getPicture() + "," +
//                "tag=" + item.getTag() + "," +
//                "temperature=" + item.getTemperature() + "," +
//                "weather=" + item.getWeather() + "," +
//                "wind=" + item.getWind() + "," +
//                "windrank=" + item.getWindrank() + "," +
//                "latitude=" + item.getLatitude() + "," +
//                "longitude=" + item.getLongitude() + "," +
//                "createtime=" + item.getCreatetime() + "," +
//                "updatetime=" + item.getUpdatetime() + "," +
//                "deleteflag=" + item.getDeleteflag() + "," +
//                "position9=" + item.getPosition();
//        return new NetUtil<>(NoteApi.EasyService.class).build()
//                .edit("note2", fields, "uuid=" + item.getUuid())
//                .compose(AsyncUtils.getThreadTransformer());
//    }
//    interface EasyService {
//
//        @POST("api/query.asp")
//        @FormUrlEncoded
//        Observable<ResultBean<List<NoteEntity>>> query(@Field("table") String table, @Field("fields") String fields);
//
//        @POST("api/add.asp")
//        @FormUrlEncoded
//        Observable<ResultBean<List<NoteEntity>>> add(@Field("table") String table, @Field("fields") String fields, @Field("values") String values);
//
//        @POST("api/edit.asp")
//        @FormUrlEncoded
//        Observable<ResultBean<List<NoteEntity>>> edit(@Field("table") String table, @Field("fields") String fields, @Field("wheres") String wheres);
//
//        @POST("api/delete.asp")
//        @FormUrlEncoded
//        Observable<ResultBean<List<NoteEntity>>> delete(@Field("table") String table, @Field("wheres") String wheres);
//
//    }
//
//}
