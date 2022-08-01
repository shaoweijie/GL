package com.hancher.gamelife.main;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hancher.common.base.mvp01.BaseActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.SnackBarUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.api.LastestApkBean;
import com.hancher.gamelife.api.PgyerApi;
import com.hancher.gamelife.databinding.ActivityMainBinding;
import com.hancher.gamelife.main.home.HomeFragment;
import com.hancher.gamelife.main.list.AllListFragment;
import com.hancher.gamelife.main.me.MeFragmentVM;
import com.hancher.gamelife.main.note.NoteRichEditActivityVM;
import com.hancher.gamelife.main.setting.AboutActivity;
import com.hancher.gamelife.main.setting.SettingListActivity;
import com.hancher.gamelife.main.test.TestListFragment;

import java.util.ArrayList;

public class GameLifeMvpActivity extends BaseActivity<ActivityMainBinding> {
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
//    private String notificationTimeKey = "notificationTime";
//    CharactersViewModel charactersViewModel;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initBirthdayNotification();
//    }

    @Override
    protected void initListener() {

        binding.btnFloat.setOnClickListener(v -> {
            Intent intent = new Intent(GameLifeMvpActivity.this, NoteRichEditActivityVM.class);
            startActivity(intent);
        });

        binding.viewpagertab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (binding.viewpagertab.getCurrentTab()!=binding.viewpager.getCurrentItem()){
                    binding.viewpager.setCurrentItem(binding.viewpagertab.getCurrentTab());
                    binding.superToolbar.setTitle(mTabEntities.get(position).getTabTitle());
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (binding.viewpagertab.getCurrentTab()!=binding.viewpager.getCurrentItem()){
                    binding.viewpagertab.setCurrentTab(binding.viewpager.getCurrentItem());
                    binding.superToolbar.setTitle(mTabEntities.get(position).getTabTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initView() {
        ToolBarUtil.initToolbar(binding.superToolbar, this, false);

        //ViewPager 100ms
        mTabEntities.add(new TabViewPagerAdapter.TabEntity("测试", new TestListFragment(),R.drawable.tab_message_active,  R.drawable.tab_message_active));
        mTabEntities.add(new TabViewPagerAdapter.TabEntity("列表", new AllListFragment(),R.drawable.tab_list_active,  R.drawable.tab_list));
        mTabEntities.add(new TabViewPagerAdapter.TabEntity("主页", new HomeFragment(), R.drawable.tab_home_active, R.drawable.tab_home));
//        mTabEntities.add(new TabEntity("任务", new TodoFragment(), R.drawable.tab_four_active, R.drawable.tab_four));
//        mTabEntities.add(new TabEntity("修炼", new RankFragment(), R.drawable.tab_message_active, R.drawable.tab_message));
//        mTabEntities.add(new TabEntity("四象图", new ScrollingFragment(), R.drawable.tab_four_active,  R.drawable.tab_four));
//        mTabEntities.add(new TabEntity("设置", new SettingsFragment(), R.drawable.tab_setting_active,  R.drawable.tab_settings));
        mTabEntities.add(new TabViewPagerAdapter.TabEntity("我", new MeFragmentVM(), R.drawable.tab_me_active, R.drawable.tab_me));
        binding.viewpagertab.setTabData(mTabEntities);
        binding.viewpager.setAdapter(new TabViewPagerAdapter(mTabEntities, getSupportFragmentManager()));
        binding.viewpagertab.setCurrentTab(0);
        binding.viewpager.setCurrentItem(0);



        PgyerApi.getLastest().subscribe(bean -> {
            LogUtil.d("检查版本成功");
            LastestApkBean lastestApk = bean.getData();
            if (lastestApk == null) {
                LogUtil.e("未知异常");
                return;
            }
            if (lastestApk.getBuildHaveNewVersion()) {
                SnackBarUtil.show(binding.container,
                        "新版本:" + lastestApk.getBuildVersion(),
                        "前去更新",
                        v -> {
                            Intent intent = new Intent(GameLifeMvpActivity.this, AboutActivity.class);
                            GameLifeMvpActivity.this.startActivity(intent);
                        });
            }
        }, throwable -> ToastUtil.show(throwable.getMessage()));
    }

//    private void initBirthdayNotification() {
//        charactersViewModel.getBirthday().observe(this, birthdayItems -> {
//            long notificationTime = PreferenceUtil.getLong(notificationTimeKey, 0);
//            if (System.currentTimeMillis() - notificationTime < 8 * 60 * 60 * 1000L) {
//                LogUtil.d("固定时间间隔内，不需要重复发送生日通知");
//                return;
//            }
//            NotificationUtil.createChannels(GameLifeMvpActivity.this);
//            for (int i = 0; i < birthdayItems.size(); i++) {
//                StringBuffer message = new StringBuffer();
//                if (birthdayItems.get(i).getCoutdown() == 0) {
//                    message.append("今天");
//                } else if (birthdayItems.get(i).getCoutdown() == 1) {
//                    message.append("明天");
//                } else {
//                    message.append(birthdayItems.get(i).getCoutdown()).append("天后");
//                }
//                message.append("过");
//                message.append(birthdayItems.get(i).getType() == BirthdayAdapter.TYPE_SOLAR ? "阳历" : "阴历");
//                message.append("生日");
//                String itemPicture = birthdayItems.get(i).getPicture();
//                String itemName = birthdayItems.get(i).getName();
//                int notificationId = i + 100;
//                LogUtil.d("图片地址:", itemPicture);
//                if (!TextUtil.isEmpty(birthdayItems.get(i).getPicture())) {
//                    AsyncUtils.run((ObservableOnSubscribe<Bitmap>) emitter -> {
//                        try {
//                            Bitmap pic = Glide.with(GameLifeMvpActivity.this).asBitmap()
//                                    .load(itemPicture)
//                                    .submit().get();
//                            emitter.onNext(pic);
//                        } catch (ExecutionException | InterruptedException e) {
//                            ToastUtil.showErr("加载通知大图标失败:" + e);
//                        }
//                        emitter.onComplete();
//                    }, bitmap -> NotificationUtil.sendNotification(GameLifeMvpActivity.this,
//                            itemName, message.toString(), notificationId, bitmap));
//                } else {
//                    NotificationUtil.sendNotification(GameLifeMvpActivity.this,
//                            itemName, message.toString(), notificationId);
//                }
//            }
//            PreferenceUtil.setValue(notificationTimeKey, System.currentTimeMillis());
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingListActivity.class));
                // TODO: swj: 2021/10/10 0010 toolbar应当放到各个fragment中
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}