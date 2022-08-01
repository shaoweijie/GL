package com.hancher.gamelife.main.note;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hancher.common.activity.WebActivity;
import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.third.weatherhe.HeWeatherUtil;
import com.hancher.common.utils.android.DialogUtil;
import com.hancher.common.utils.android.IntentUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.SnackBarUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.android.UriUtil;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.common.utils.java.HtmlUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.common.utils.java.UuidUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.bak.ImageSaveUtil;
import com.hancher.gamelife.databinding.NoteRichEditActivityBinding;
import com.hancher.gamelife.greendao.Diary;
import com.hancher.gamelife.greendao.DiaryDao;
import com.hancher.gamelife.greendao.PositionDbUtil;
import com.permissionx.guolindev.PermissionX;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/15 8:56 <br/>
 * 描述 : 富文本编辑笔记
 */
public class NoteRichEditActivityVM extends VMBaseActivity<NoteRichEditActivityBinding, NoteViewModel> {

    private static final int PIC_SELECT_RESULT_CODE = 123;
    private boolean isUpdate;
    private boolean showPreview = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        initEdit();
        initClipboard();

        initNote();
        initPosition();
        initWeather();

        initPermission();
    }

    private void initPermission() {
        PermissionX.init(this).permissions(Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted){
                        LogUtil.d("permission allow");
                    }
                });
    }


    private void initWeather() {
        HeWeatherUtil.init();
        viewModel.getWeatherCnLiveData().observe(this, weather -> {
            //显示Ui
            StringBuffer stringBuffer = new StringBuffer()
                    .append(weather.getText())
                    .append(" ")
                    .append(weather.getWindDir())
                    .append(weather.getWindScale())
                    .append(" ")
                    .append(weather.getTemp())
                    .append("℃");
            binding.txtWeather.setText(stringBuffer);
            if (isUpdate){
                return;
            }
            //保存到实体类
            viewModel.getDiary().getValue().setWeatherUuid(weather.getUuid());
        });
    }

    private void initPosition() {
        viewModel.getBaiduMapLiveData().observe(this, position -> {
            //显示到ui
            binding.txtPosition.setText(PositionDbUtil.getFormatAddress(position));

            if (isUpdate){
                return;
            }
            //保存到实体类
            viewModel.getDiary().getValue().setPositionUuid(position.getUuid());

            //开始获取天气
            viewModel.startNowWeather(NoteRichEditActivityVM.this,
                    PositionDbUtil.getLongiLat2(position));
        });
        if (!isUpdate) {
            viewModel.startBdLocation(getApplicationContext());
        }
    }

    private void initNote() {

        viewModel.getDiary().observe(this, diary -> {
            LogUtil.i("日记初始化ui");
            String time = DateUtil.changeDate(diary.getCreatetime(), DateUtil.Type.LONG_STAMP,
                    DateUtil.Type.STRING_YMD_HMS);
            binding.txtTime.setText(time);
            binding.editTitle.setText(diary.getTitle());
            binding.editTag.setText(diary.getTag());
            String content = StringEscapeUtils.unescapeHtml(diary.getContent());
            binding.editor.setHtml(content);
            if (!TextUtil.isEmpty(content)) {
                String contentNoHtmlTag = HtmlUtil.delHTMLTag(content);
                if (!TextUtil.isEmpty(contentNoHtmlTag)) {
                    binding.txtWords.setText(String.valueOf(contentNoHtmlTag.length()));
                }
            }
            if (!isUpdate){
                return;
            }
            try {
                viewModel.queryLocationInDb(diary.getPositionUuid());
            } catch (Exception e){
                LogUtil.e("query logcation err:",e);
            }
            try {
                viewModel.queryWeatherInDb(diary.getWeatherUuid());
            } catch (Exception e){
                LogUtil.e("query weather err:",e);
            }
        });

        String uuid = getIntent().getStringExtra(DiaryDao.Properties.Uuid.columnName);
        isUpdate = !TextUtil.isEmptyOrNullStr(uuid);
        if (isUpdate) {
            LogUtil.i("更新内容uuid:" + uuid);
            viewModel.queryCurrentItem(uuid);
        } else {
            Diary itemData = new Diary();
            itemData.setUuid(UuidUtil.getUuidNoLine());
            LogUtil.i("新增内容uuid:" + itemData.getUuid());
            itemData.setCreatetime(System.currentTimeMillis());
            itemData.setEquipment(Build.MODEL);
            viewModel.getDiary().setValue(itemData);
        }

    }

    private void initClipboard() {
        viewModel.getClipboard().observe(this, s -> {
            SnackBarUtil.show(binding.container, s, "点击打开链接", v -> {
                if (TextUtil.isEmpty(s)) {
                    LogUtil.d("不是连接，放弃显示");
                    return;
                }
                Intent intent = WebActivity.getIntent(NoteRichEditActivityVM.this, s, "");
                NoteRichEditActivityVM.this.startActivity(intent);
            });
        });
    }

    private void initEdit() {

        binding.editor.setEditorHeight(200);
        binding.editor.setEditorFontSize(18);
//        binding.editor.setBackgroundColor();
//        binding.editor.setEditorFontColor(Color.RED);
        //binding.editor.setEditorBackgroundColor(Color.BLUE);
        //binding.editor.setBackgroundColor(Color.BLUE);
        //binding.editor.setBackgroundResource(R.drawable.bg);
//        binding.editor.setPadding(10, 10, 10, 10);
        //binding.editor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        binding.editor.setPlaceholder(" 开始添加内容吧");
        //binding.editor.setInputEnabled(false);
        binding.editor.setOnTextChangeListener(text -> {
            binding.txtWords.setText(String.valueOf(HtmlUtil.delHTMLTag(text).length()));
            if (showPreview) {
                binding.preview.setText(text);
            }
        });
//        binding.actionUndo.setOnClickListener(v -> binding.editor.undo());
//        binding.actionRedo.setOnClickListener(v -> binding.editor.redo());
        binding.actionBold.setOnClickListener(v -> binding.editor.setBold());
        binding.actionItalic.setOnClickListener(v -> binding.editor.setItalic());
        binding.actionSubscript.setOnClickListener(v -> binding.editor.setSubscript());
        binding.actionSubscript.setOnClickListener(v -> binding.editor.setSuperscript());
        binding.actionStrikethrough.setOnClickListener(v -> binding.editor.setStrikeThrough());
        binding.actionUnderline.setOnClickListener(v -> binding.editor.setUnderline());
        binding.actionHeading1.setOnClickListener(v -> binding.editor.setHeading(1));
        binding.actionHeading2.setOnClickListener(v -> binding.editor.setHeading(2));
        binding.actionHeading3.setOnClickListener(v -> binding.editor.setHeading(3));
        binding.actionHeading4.setOnClickListener(v -> binding.editor.setHeading(4));
        binding.actionHeading5.setOnClickListener(v -> binding.editor.setHeading(5));
        binding.actionHeading6.setOnClickListener(v -> binding.editor.setHeading(6));
        binding.actionTxtColor.setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                binding.editor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });
        binding.actionBgColor.setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                binding.editor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });
        binding.actionIndent.setOnClickListener(v -> binding.editor.setIndent());
        binding.actionOutdent.setOnClickListener(v -> binding.editor.setOutdent());
        binding.actionAlignLeft.setOnClickListener(v -> binding.editor.setAlignLeft());
        binding.actionAlignCenter.setOnClickListener(v -> binding.editor.setAlignCenter());
        binding.actionAlignRight.setOnClickListener(v -> binding.editor.setAlignRight());
        binding.actionBlockquote.setOnClickListener(v -> binding.editor.setBlockquote());
        binding.actionInsertBullets.setOnClickListener(v -> binding.editor.setBullets());
        binding.actionInsertNumbers.setOnClickListener(v -> binding.editor.setNumbers());
        binding.actionInsertImage.setOnClickListener(v -> {
            DialogUtil.build(NoteRichEditActivityVM.this).title("添加图片")
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("图片链接", "https://cdn-app-icon.pgyer.com/c/a/a/5/7/caa576b6cc7e7aabd995548605d5b76f?x-oss-process=image/resize,m_lfit,h_120,w_120/format,jpg",
                            (dialog, input) -> {
                                binding.editor.insertImage(input.toString(), "image", 320);
                                dialog.dismiss();
                            }
                    )
                    .positiveText("确定")
                    .negativeText("从图库中选取")
                    .onNegative((dialog, which) -> {
                        startActivityForResult(IntentUtil.getSelectFileIntent(), PIC_SELECT_RESULT_CODE);
                        dialog.dismiss();
                    })
                    .show();
        });
//        binding.actionInsertYoutube.setOnClickListener(v -> binding.editor.insertYoutubeVideo("https://www.youtube.com/embed/pS5peqApgUA"));
        binding.actionInsertAudio.setOnClickListener(v -> {
            DialogUtil.build(NoteRichEditActivityVM.this).title("添加音频")
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("音频链接", null, (dialog, input) -> {
                        binding.editor.insertAudio(input.toString());
                        dialog.dismiss();
                    })
                    .positiveText("确定")
                    .show();
        });
        binding.actionInsertVideo.setOnClickListener(v -> {
            DialogUtil.build(NoteRichEditActivityVM.this).title("添加视频")
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("视频链接", null, (dialog, input) -> {
                        binding.editor.insertVideo(input.toString(), 320);
                        dialog.dismiss();
                    })
                    .positiveText("确定")
                    .show();
        });
        binding.actionInsertLink.setOnClickListener(v -> {
            DialogUtil.build(NoteRichEditActivityVM.this).title("添加链接")
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("链接", null, (dialog, input) -> {
                        binding.editor.insertLink(input.toString(), "链接");
                        dialog.dismiss();
                    })
                    .positiveText("确定")
                    .show();
        });
        binding.actionInsertCheckbox.setOnClickListener(v -> binding.editor.insertTodo());
        binding.actionSrc.setOnClickListener(v -> {
            binding.previewContainer.setVisibility(showPreview ? View.GONE : View.VISIBLE);
            showPreview = !showPreview;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("requestCode:", requestCode, ", resultCode:", resultCode, "data:", data);
        if (PIC_SELECT_RESULT_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                String selectFilePath = UriUtil.getPathByUri(this, data.getData());
                ToastUtil.show("选择文件：" + selectFilePath);

                ImageSaveUtil.saveImageAsync(selectFilePath, s -> {
                    binding.editor.insertImage(s, s, 320);
                    LogUtil.d("存储位置：", s);
                });
            }
        }
    }

    private void initActionBar() {
        setSupportActionBar(binding.superToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        viewModel.getState().observe(this, state -> NoteRichEditActivityVM.this.finish());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back_next_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_back:
                binding.editor.undo();
                break;
            case R.id.menu_item_next:
                binding.editor.redo();
                break;
            case R.id.menu_item_save:
                if (TextUtil.isEmpty(binding.editTitle.getText().toString())) {
                    ToastUtil.show("请填写标题");
                    return false;
                }
                Diary diary = viewModel.getDiary().getValue();
                diary.setTitle(binding.editTitle.getText().toString());
                diary.setTag(binding.editTag.getText().toString());
                String ehtml = StringEscapeUtils.escapeHtml(binding.editor.getHtml());
                LogUtil.d("ehtml:", ehtml);
                diary.setContent(ehtml);
                diary.setUpdatetime(System.currentTimeMillis());

                viewModel.saveItemData(diary, isUpdate);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String[] getPermission() {
        return new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
    }
}
