package com.hancher.gamelife.main.tv.app;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hancher.common.net.bean.ResultBean;
import com.hancher.common.utils.android.AssetUtil;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/21 0021 21:13 <br/>
 * 描述 :
 */
public class TvListViewModel extends ViewModel {
    private MutableLiveData<List<TvListAdapter.TvListItem>> tvs = new MutableLiveData<>();
    private MutableLiveData<List<TvListAdapter.TvListItem>> yts = new MutableLiveData<>();

    public MutableLiveData<List<TvListAdapter.TvListItem>> getYts() {
        return yts;
    }

    public MutableLiveData<List<TvListAdapter.TvListItem>> getTvs() {
        return tvs;
    }

    /**
     * 读取asset中保存的电视台
     *
     * @param context
     * @param spanCount
     */
    public void readTvList(Context context, int spanCount) {
        if (tvs.getValue()!=null && tvs.getValue().size()>0){
            tvs.setValue(tvs.getValue());
            return;
        }
        LogUtil.i("开始读取asset电视资源");
        try {
            // asset 读取
            String jsonStr = AssetUtil.getJson(context, "tvlist.json");
//            LogUtil.d("jsonStr:"+jsonStr);
            AsyncUtils.run(emitter -> {
                //gson 字符串转实体类
                Gson gson = new Gson();
                Type type = new TypeToken<ResultBean<TvBean>>() {
                }.getType();
                ResultBean<TvBean> tvBeanResultBean = gson.fromJson(jsonStr, type);
                if (tvBeanResultBean == null) {
                    LogUtil.w("读取失败, json格式化后为null");
                    emitter.onComplete();
                    return;
                }
                TvBean data = tvBeanResultBean.getData();
                //开始解析
                List<TvListAdapter.TvListItem> result = new ArrayList<>();
                for (int i = 0; i < data.getGroups().size(); i++) {
                    //遍历出每个组
                    TvBean.Group group = data.getGroups().get(i);
                    TvListAdapter.TvListItem title = new TvListAdapter.TvListItem()
                            .setTitle(group.getListname())
                            .setType(TvListAdapter.TYPE_TITLE);
                    result.add(title);
                    //生成组后面的空白
                    for (int j = 0; j < (spanCount - 1); j++) {
                        TvListAdapter.TvListItem empty = new TvListAdapter.TvListItem()
                                .setType(TvListAdapter.TYPE_TITLE_EMPTY);
                        result.add(empty);
                    }
                    //填写组内所有链接
                    for (int j = 0; j < group.getUrllist().size(); j++) {
                        TvBean.Group.Url url = group.getUrllist().get(j);
                        TvListAdapter.TvListItem tv = new TvListAdapter.TvListItem()
                                .setTitle(url.getItemname())
                                .setUrl(url.getUrl())
                                .setType(TvListAdapter.TYPE_ITEM);
                        result.add(tv);
                    }
                    //填写组外空格
                    for (int j = 0; j < (spanCount - (group.getUrllist().size() % spanCount)) % spanCount; j++) {
                        TvListAdapter.TvListItem empty = new TvListAdapter.TvListItem()
                                .setType(TvListAdapter.TYPE_ITEM_EMPTY);
                        result.add(empty);
                    }
                }
                emitter.onNext(result);
                emitter.onComplete();
            }, (Consumer<List<TvListAdapter.TvListItem>>) tvListItems -> {
                tvs.setValue(tvListItems);
            });

        } catch (Exception exception) {
            LogUtil.e("assets 读取失败：", exception);
        }
    }

    public void crawlingYtTvList(int spanCount) {
        if (yts.getValue()!=null && yts.getValue().size()>0){
            yts.setValue(yts.getValue());
            return;
        }
        LogUtil.i("开始抓取电视资源");
        AsyncUtils.run(emitter -> {
            List<TvListAdapter.TvListItem> result = new ArrayList<>();
            //创建title
            TvListAdapter.TvListItem title = new TvListAdapter.TvListItem()
                    .setTitle("烟台电视台")
                    .setType(TvListAdapter.TYPE_TITLE);
            result.add(title);
            //创建title后空白
            for (int i = 0; i < spanCount - 1; i++) {
                TvListAdapter.TvListItem empty = new TvListAdapter.TvListItem()
                        .setType(TvListAdapter.TYPE_TITLE_EMPTY);
                result.add(empty);
            }
            //抓取烟台电视台
            try {
                result.add(getYtItem(1, "新闻频道"));
                result.add(getYtItem(2, "公共频道"));
                result.add(getYtItem(3, "影视频道"));
                result.add(getYtItem(4, "经济频道"));
            } catch (IOException exception) {
                LogUtil.e("jsoup get err:", exception);
            }
            emitter.onNext(result);
            emitter.onComplete();
        }, (Consumer<List<TvListAdapter.TvListItem>>) tvListItems -> {
            yts.setValue(tvListItems);
        });
    }

    private TvListAdapter.TvListItem getYtItem(int id, String title) throws IOException {
        TvListAdapter.TvListItem tv = new TvListAdapter.TvListItem()
                .setTitle(title)
                .setUrl(getYtTvById(id))
                .setType(TvListAdapter.TYPE_ITEM);
        return tv;
    }

    /**
     * 爬虫抓取
     *
     * @param id
     * @return
     * @throws IOException
     */
    private String getYtTvById(int id) throws IOException {
        String url = String.format("http://www.yantaitv.cn/zhibo/ch%d/", id);
        Document document = Jsoup.connect(url).get();
        String data = document.html();
        int start = data.indexOf("http://live.yantaitv.cn/live/");
        int end = data.indexOf(".m3u8");
        String video = data.substring(start, end + ".m3u8".length());
        return video;
    }
}
