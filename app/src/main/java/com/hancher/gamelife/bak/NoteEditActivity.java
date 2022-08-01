//package com.hancher.gamelife.main.note;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.baidu.location.BDLocation;
//import com.hancher.common.CommonConfig;
//import com.hancher.common.base.BaseActivity;
//import com.hancher.mapbaidu.BaiduMapHelper;
//import com.hancher.common.utils.android.IntentUtil;
//import com.hancher.common.utils.android.LogUtil;
//import com.hancher.common.utils.android.ToastUtil;
//import com.hancher.common.utils.android.UriUtil;
//import com.hancher.common.utils.java.DateUtil;
//import com.hancher.common.utils.java.TextUtil;
//import com.hancher.common.utils.java.UuidUtil;
//import com.hancher.gamelife.R;
//import com.hancher.gamelife.bak.activity.ImageActivity;
//import com.hancher.gamelife.databinding.ActivityNoteEditBinding;
//import com.hancher.gamelife.greendao.NoteEntity;
//import com.hancher.gamelife.greendao.DiaryDao;
//
//import java.util.List;
//
//import interfaces.heweather.com.interfacesmodule.bean.weather.WeatherNowBean;
//import me.zhouzhuo.zzimagebox.ZzImageBox;
//
//public class NoteEditActivity extends BaseActivity<ActivityNoteEditBinding, NoteEditPresenter>
//        implements NoteEditContract.View {
//
//    private NoteEntity itemData;
//    public boolean isUpdate;
//    private boolean hadAutoUpdate = false;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setSupportActionBar(binding.superToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//
//        String uuid = getIntent().getStringExtra(DiaryDao.Properties.Uuid.columnName);
//        binding.bmapView.onCreate(this,savedInstanceState);
//        isUpdate = !TextUtil.isEmptyOrNullStr(uuid);
//        if (isUpdate){
//            LogUtil.i("更新内容uuid:"+uuid);
//            presenter.queryCurrentItem(uuid);
//        } else {
//            NoteEntity itemData = new NoteEntity();
//            itemData.setUuid(UuidUtil.getUuidNoLine());
//            LogUtil.i("新增内容uuid:" + itemData.getUuid());
//            itemData.setCreatetime(String.valueOf(System.currentTimeMillis()));
//            itemData.setDeleteflag("0");
//            itemData.setEquipment(Build.MODEL);
//            updateData(itemData);
//        }
//
//        binding.zzImageBox.setOnImageClickListener(new ZzImageBox.OnImageClickListener() {
//
//            @Override
//            public void onImageClick(int position, String url, String realPath, int realType, ImageView iv) {
//                LogUtil.d("ZzImageBox", "image clicked:" + position + "," + realPath);
//                Intent intent = new Intent(NoteEditActivity.this, ImageActivity.class);
//                intent.putExtra(ImageActivity.PATH, realPath);
//                NoteEditActivity.this.startActivity(intent);
//            }
//
//            @Override
//            public void onDeleteClick(int position, String url, String realPath, int realType) {
//                LogUtil.d("ZzImageBox", "delete clicked:" + position + "," + realPath);
//                LogUtil.d("ZzImageBox", "all images\n" + binding.zzImageBox.getAllImages().toString());
//                binding.zzImageBox.removeImage(position);
//            }
//
//            @Override
//            public void onAddClick() {
//                Intent intent = IntentUtil.getSelectFileIntent();
//                LogUtil.d("ZzImageBox", "add clicked");
//                LogUtil.d("ZzImageBox", "all images\n" + binding.zzImageBox.getAllImages().toString());
//                NoteEditActivity.this.startActivityForResult(intent, CommonConfig.REQUEST_CODE_FILE_PIC_SELECT);
////                binding.zzImageBox.addImage(null);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != Activity.RESULT_OK) {
//            return;
//        }
//        switch (requestCode) {
//            case CommonConfig.REQUEST_CODE_FILE_PIC_SELECT:
//                String selectFilePath = UriUtil.getPathByUri(this, data.getData());
//                binding.zzImageBox.addImage(selectFilePath);
//                break;
//        }
//    }
//
//    /**
//     * 若是更新，从数据库中查询后调用
//     * 若是插入，直接创建后调用
//     *
//     * @param o
//     */
//    @Override
//    public void updateData(NoteEntity o) {
//        itemData = o;
//
//        binding.noteeditTime.setText(DateUtil.changeDate(itemData.getCreatetime(), DateUtil.Type.STRING_STAMP, DateUtil.Type.STRING_YMD_HMS));
//        binding.noteUpdatetime.setText(DateUtil.changeDate(itemData.getUpdatetime(), DateUtil.Type.STRING_STAMP, DateUtil.Type.STRING_YMD_HMS));
//
//        binding.noteeditTitle.setText(itemData.getTitle());
//        binding.noteeditContent.setText(itemData.getContent());
//        binding.noteeditWeather.setText(itemData.getWeather());
//        if (!TextUtil.isEmptyOrNullStr(itemData.getTemperature())) {
//            binding.noteeditTemperature.setText(itemData.getTemperature());
//        }
//        binding.noteeditWind.setText(itemData.getWind());
//        binding.noteeditWindrank.setText(itemData.getWindrank());
//        binding.noteeditLongitude.setText(itemData.getLongitude());
//        binding.noteeditLatitude.setText(itemData.getLatitude());
//        binding.noteeditPosition.setText(itemData.getPosition());
//        binding.noteeditTag.setText(itemData.getTag());
//        binding.noteeditEquipment.setText(itemData.getEquipment());
//        try {
//            if (itemData.getLatitude()!=null && itemData.getLongitude()!=null) {
//                LogUtil.d("显示百度地图");
//                binding.bmapView.setVisibility(View.VISIBLE);
//                BaiduMapHelper.getInstance().addMapMarker(binding.bmapView, Double.parseDouble(itemData.getLatitude()), Double.parseDouble(itemData.getLongitude()));
//            } else {
//                LogUtil.d("隐藏百度地图");
//                binding.bmapView.setVisibility(View.GONE);
//            }
//        } catch (Exception e){
//            LogUtil.e(e);
//        }
//    }
//
//    @Override
//    public void onDataSaveSuccess(Long o) {
//        if (o < 1){
//            ToastUtil.show("保存失败");
//            return;
//        }
//        LogUtil.i("保存成功");
//        finish();
//    }
//
//    @Override
//    public void updatePosition(BDLocation bdLocation) {
//        LogUtil.i("位置更新:",
//                TextUtil.double2String(bdLocation.getLongitude()), ",",
//                TextUtil.double2String(bdLocation.getLatitude())
//        );
//        binding.noteeditLongitude.setText(TextUtil.double2String(bdLocation.getLongitude()));
//        binding.noteeditLatitude.setText(TextUtil.double2String(bdLocation.getLatitude()));
//        binding.noteeditPosition.setText(BaiduMapHelper.getFormatAddress(bdLocation));
//
//        LogUtil.d("百度地图显示，并增加标记点");
//        binding.bmapView.setVisibility(View.VISIBLE);
//        BaiduMapHelper.getInstance().addMapMarker(binding.bmapView,bdLocation.getLatitude(), bdLocation.getLongitude());
//    }
//
//    @Override
//    public void updateWeather(WeatherNowBean weatherNowBean) {
//        LogUtil.i("更新天气成功：",weatherNowBean.getNow().getText());
//        binding.noteeditWeather.setText(weatherNowBean.getNow().getText());
//        binding.noteeditTemperature.setText(weatherNowBean.getNow().getTemp());
//        binding.noteeditWind.setText(weatherNowBean.getNow().getWindDir());
//        binding.noteeditWindrank.setText(weatherNowBean.getNow().getWindScale());
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_note_edit, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.menu_save:
//                itemData.setTitle(binding.noteeditTitle.getText().toString());
//                itemData.setContent(binding.noteeditContent.getText().toString());
//                itemData.setUpdatetime(String.valueOf(System.currentTimeMillis()));
//                itemData.setLongitude(binding.noteeditLongitude.getText().toString());
//                itemData.setLatitude(binding.noteeditLatitude.getText().toString());
//                itemData.setPosition(binding.noteeditPosition.getText().toString());
//                itemData.setWeather(binding.noteeditWeather.getText().toString());
//                itemData.setTemperature(binding.noteeditTemperature.getText().toString());
//                itemData.setWind(binding.noteeditWind.getText().toString());
//                itemData.setWindrank(binding.noteeditWindrank.getText().toString());
//                itemData.setTag(binding.noteeditTag.getText().toString());
//                StringBuffer stringBuffer = new StringBuffer();
//                List<String> images = binding.zzImageBox.getAllImages();
//                for (String itemImages :
//                        images) {
//                    stringBuffer.append(itemImages).append(" ");
//                }
//                itemData.setPicture(stringBuffer.toString().trim());
//                presenter.saveItemData(itemData, isUpdate);
//                break;
//            case R.id.menu_position:
//                presenter.getPositionAsync();
//                break;
//            case R.id.menu_weather:
//                presenter.getWeatherAsync(binding.noteeditLongitude.getText().toString(), binding.noteeditLatitude.getText().toString());
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public String[] getPermission() {
//        return new String[]{
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//        };
//    }
//
//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//        LogUtil.d();
//        setResult(Activity.RESULT_CANCELED);
//        finish();
//    }
//
//    @Override
//    protected void onResume() {
//        binding.bmapView.onResume();
//        super.onResume();
//        if (!isUpdate && !hadAutoUpdate) {
//            LogUtil.i("插入逻辑, 自动更新位置与天气");
//            hadAutoUpdate = true;
//            presenter.autoUpdateLocationAndWeather();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        binding.bmapView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        binding.bmapView.onDestroy();
//        super.onDestroy();
//    }
//}